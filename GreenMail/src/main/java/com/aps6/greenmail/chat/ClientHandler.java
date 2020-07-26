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
    private String message;

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
            setMessage(getReader().readLine());
            this.clientName = getMessage().toLowerCase().replaceAll(",", "");
            getWriter().println("Olá " + this.getClientName());
            clients.put(getMessage(), this);
            setMessage(getClientName() + " entrou");
            broadcast2();
            
            while (true) {
                setMessage(getReader().readLine());
                if (getMessage().equalsIgnoreCase("!q")) {
                    this.client.close();

                } else if (getMessage().toLowerCase().startsWith("!m")) {
                    String messageToName = getMessage().substring(2, getMessage().length());
                    
                    clients.get(getMessage().subSequence(2, getMessage().length()));
                    
                    ClientHandler messageTo = clients.get(messageToName);
                    
                    if (messageTo == null) {
                        writer.println("O cliente informado nao existe.");
                    } else {
                        writer.println("Escreva uma mensagem para " + 
                                messageTo.getClientName());
                        messageTo.getWriter().println(this.clientName + 
                                " disse: " + reader.readLine());
                    }
                    
                    
                } else if (getMessage().equals("!lc")) {
                    StringBuffer str = new StringBuffer();
                    for (String c : clients.keySet()) {
                        str.append(c);
                        str.append(", ");
                    }
                    
                    str.delete(str.length() - 2, str.length());
                    writer.println(str.toString());
                } else {                 
                    broadcast();
                }
            }
            
        } catch (IOException ex) {
            setMessage(getClientName() + " saiu");
            broadcast2();
            System.err.println(getClientName() + " fechou a conexão");
        }
    }
    
    private void broadcast () {
        for (ClientHandler client : clients.values()) {
            client.getWriter().println(this.getClientName() + 
                " : " + getMessage());                        
        }    
    }
    
    private void broadcast2 () {
        for (ClientHandler client : clients.values()) {
            client.getWriter().println(getMessage());                        
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

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
