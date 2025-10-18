/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.service.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.ptit.na.ltm.baitap.requests.BaseRequest;
import vn.ptit.na.ltm.baitap.service.service.BusinessService;

/**
 *
 * @author Windows
 */
public class ClientHandler implements Runnable {

    private final Socket socket;

    ClientHandler(Socket socket) {
        this.socket = Objects.requireNonNull(socket);
    }

    @Override
    public void run() {
        final String who = socket.getRemoteSocketAddress().toString();
        System.out.println("[server] client connected " + who);
        BusinessService busines = new BusinessService();
        try (socket;
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream()); ) {
    
            out.writeObject(busines.business((BaseRequest)in.readObject()));
            out.flush();
        } catch (SocketTimeoutException te) {
            te.printStackTrace();
            System.out.println("[server] client idle timeout " + who);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("[server] client closed " + who + ": " + ioe.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("[server] client disconnected " + who);
        }
    }

}
