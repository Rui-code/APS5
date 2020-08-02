/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aps6.greenmail.chat;

import com.aps6.greenmail.ChatAndMailController;
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
    
    private Socket client;
    private PrintWriter writer;
    private BufferedReader reader;
    private String terminalMessage;
    
    public void init () {
        try {
            this.client = new Socket("127.0.0.1", 9999);
            writer = new PrintWriter(
                                client.getOutputStream(), true);
            reader = new BufferedReader(
                                new InputStreamReader(client.getInputStream()));
            
            new Thread () {
                @Override
                public void run() {
                    try {               
                        while (true) {
                            String message = reader.readLine();
                            
                            if (message == null || message.isEmpty()) {
                                new ChatAndMailController().setMessage("Erro ao receber a mensagem do servidor.");
                            } else {
                                new ChatAndMailController().setMessage(message);                         
                            }
                        }
                        
                    } catch (IOException ex) {
                        System.err.println("Reading server's message was not possible.");
                    }
                }
              
            }.start();
            
            
            
        } catch (UnknownHostException ex) {
            System.err.println("Invalid address.");
        } catch (IOException ex) {
            System.err.println("Server may be disconnected.");
        }
    }
    
    public void send () {
        if (this.terminalMessage.isEmpty())
            return;

        if (this.terminalMessage.equalsIgnoreCase("!q"))
            System.exit(0);
            
        writer.println(this.terminalMessage);
    }

    /**
     * @param terminalMessage the terminalMessage to set
     */
    public void setTerminalMessage(String terminalMessage) {
        this.terminalMessage = terminalMessage;
    }
    
    
}
