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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ruiran
 */
public class ClientHandler extends Thread {
    
    private final Socket client;
    private String clientName;
    private static final Map<String, ClientHandler> clients = 
            new HashMap<String, ClientHandler>();
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket client) {
        this.client = client;
        start();
    }

    /**
     * 
     */
    @Override
    public void run() {
        try {
            reader = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(
                    client.getOutputStream(), true);
            getWriter().println("Escreva o seu nome: ");            
            String message = getReader().readLine();
            this.clientName = message;
            getWriter().println("Olá " + this.getClientName());
            clients.put(message, this);
            
            while (true) {
                message = getReader().readLine();
                if (message.equalsIgnoreCase("!q")) {
                    this.client.close();

                } else if (message.toLowerCase().startsWith("!m")) {
                    String messageToName = message.substring(2, message.length());
                    
                    clients.get(message.subSequence(2, message.length()));
                    
                    ClientHandler messageTo = clients.get(messageToName);
                    
                    if (messageTo == null) {
                        writer.println("O cliente informado nao existe.");
                    } else {
                        writer.println("Escreva uma mensagem para " + 
                                messageTo.getClientName());
                        messageTo.getWriter().println(this.clientName + 
                                " disse: " + reader.readLine());
                    }
                    
                    
                } else {
                    writer.println(this.getClientName() + 
                            ", você disse: " + message);
                }
            }
            
        } catch (IOException ex) {
            System.err.println(getClientName() + " fechou a conexão");
        }
    }

    /**
     * @return the reader
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * @return the writer
     */
    public PrintWriter getWriter() {
        return writer;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }
    
    
    
}
