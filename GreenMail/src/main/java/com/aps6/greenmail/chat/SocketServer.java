/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aps6.greenmail.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ruiran
 */
public class SocketServer {
    
    public static void init() {
        
        ServerSocket server = null;
        
        try {
            server = new ServerSocket(9999);
            System.out.println("server running...");
            
            while (true) {
                Socket client = server.accept();
                new ClientHandler(client);
            }
            
        } catch (IOException ex) {
            try {
                if (server != null)
                    server.close();
            } catch (IOException ex1) {}
            System.err.println("Server closed.");
        }
    }
}
