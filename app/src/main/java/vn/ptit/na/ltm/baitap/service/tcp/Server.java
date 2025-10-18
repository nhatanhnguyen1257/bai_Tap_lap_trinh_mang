/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.service.tcp;

import java.io.*;
import java.net.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.*;

/**
 * TCP Server sử dụng ThreadPoolExecutor để xử lý nhiều client đồng thời một cách an toàn.
 *
 *
 * Đảm bảo "đúng client":
 * - Mỗi kết nối -> 1 ClientHandler giữ socket & stream của riêng kết nối đó.
 * - Handler ghi response trực tiếp lên OutputStream của chính socket đó, không chia sẻ state.
 * - Không có tranh chấp giữa client khác vì mỗi handler chạy ở thread riêng và chỉ biết socket của mình.
 */
public class Server {

    public static final Map<String, ObjectOutputStream> CLIENTS = new ConcurrentHashMap<>();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        int port = 5550;
        int threads = Math.max(4, Runtime.getRuntime().availableProcessors());
        int queueCap = 1024;
        int acceptTimeoutMs = 50000;    // cho phép vòng accept kiểm tra shutdown định kỳ
        int clientReadTimeoutMs = 1200000; // tránh client treo chiếm thread vô hạn


        // Pool cố định + queue hữu hạn => back-pressure để không bùng nổ bộ nhớ khi spike
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                threads, threads,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueCap),
                namedFactory("worker"),
                // khi quá tải, làm chậm luồng accept (producer) thay vì đẩy thêm vào hàng
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        pool.prestartAllCoreThreads(); // vì sao: giảm độ trễ lô đầu

        try (ServerSocket server = new ServerSocket()) {
            server.setReuseAddress(true);
            server.bind(new InetSocketAddress(port));
            server.setSoTimeout(acceptTimeoutMs);

            // Dọn dẹp gọn khi Ctrl+C/System.exit: đóng cổng trước để accept() thoát
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                safeClose(server);                 // nhận mới
                pool.shutdown();                   //  cho tác vụ đang chạy hoàn tất trật tự
                try {
                    if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
                        pool.shutdownNow();        // tránh treo nếu client không hợp tác
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    pool.shutdownNow();
                }
            }, "shutdown"));

            System.out.printf("[server] listen %s threads=%d queue=%d%n",
                    server.getLocalSocketAddress(), threads, queueCap);

            // Vòng nhận kết nối
            while (!pool.isShutdown()) {
                try {
                    Socket client = server.accept();
                    configureClientSocket(client, clientReadTimeoutMs);
                    // Mỗi client -> 1 task độc lập giữ socket => response đúng client
                    pool.execute(new ClientHandler(client));
                } catch (SocketTimeoutException ignore) {
                    ignore.printStackTrace();
                    //  timeout ngắn để quay lại kiểm tra trạng thái shutdown
                } catch (RejectedExecutionException rex) {
                    rex.printStackTrace();
                    //  báo quá tải có kiểm soát thay vì vỡ hệ thống
                    System.err.println("[server] busy, dropping connection: " + rex.getMessage());
                }
            }
        } catch (BindException be) {
            System.err.println("[server] Port in use: " + be.getMessage());
            // không System.exit(1) để Gradle/runner không fail nếu bạn không muốn
        } catch (IOException ioe) {
            System.err.println("[server] I/O: " + ioe.getMessage());
        } finally {
            
        }

        System.out.println("[server] stopped.");
    }

   
    private static void configureClientSocket(Socket s, int soTimeoutMs) throws SocketException {
        s.setTcpNoDelay(true);
        s.setKeepAlive(true);
        s.setSoTimeout(soTimeoutMs);
    }
    private static void safeClose(ServerSocket s) { try { s.close(); } catch (IOException ignore) {} }
    private static ThreadFactory namedFactory(String base) {
        return r -> {
            Thread t = new Thread(r);
            t.setName(base + "-" + t.getId());
            t.setDaemon(false); // cần sống đến khi hoàn tất xử lý
            return t;
        };
    }

}
