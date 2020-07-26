/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aps6.greenmail.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author ruiran
 */
public class SocketClient {
    public static void init () {
        try {
            final Socket client = new Socket("127.0.0.1", 9999);

            new Thread () {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(client.getInputStream()));
                        
                      
                        while (true) {
                            String message = reader.readLine();
                            
                            if (message == null || message.isEmpty())
                                continue;
                            
                            System.out.println("O servidor disse: " + message);
                        }
                        
                    } catch (IOException ex) {
                        System.err.println("Reading server's message was not possible.");
                    }
                }
              
            }.start();
            
            PrintWriter writer = new PrintWriter(
                                client.getOutputStream(), true);
            BufferedReader terminalReader = new BufferedReader(
                                new InputStreamReader(System.in));
            String terminalMessage = "";
            
            
            while (true) {
                terminalMessage = terminalReader.readLine();
                
                if (terminalMessage == null || terminalMessage.length() == 0)
                    continue;
                
                writer.println(terminalMessage);
                if (terminalMessage.equalsIgnoreCase("!q"))
                    System.exit(0);
            }
            
        } catch (UnknownHostException ex) {
            System.err.println("Invalid address.");
        } catch (IOException ex) {
            System.err.println("Server may be disconnected.");
        }
    }
}
