/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.ptit.na.ltm.baitap.client.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Windows
 */
public class ClientTCP {
    
    private final String host = "localhost";
    private final int port = 5550;

    public<T> T sendServer(Object obj) {
       ObjectInputStream in = null ;
       ObjectOutputStream out = null;
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(host, port), 30000);
            s.setSoTimeout(50_0000);
            try {
                in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
                 out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
                // flush đầy header
//                out.flush();
                out.writeObject(obj);
                out.flush();
                return (T)in.readObject();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

}
