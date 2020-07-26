/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aps6.greenmail;

import com.aps6.greenmail.mailsend.MailSender;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 * FXML Controller class
 *
 * @author ruiran
 */
public class ChatAndMailController implements Initializable {
    
    //Chat
    @FXML
    private TextField messageText;

    @FXML
    private TextArea chatText;

    @FXML
    private Label errorLabel2;

    
    // Mail
    @FXML
    private TextArea contentText;

    @FXML
    private TextField toText;

    @FXML
    private TextField copiedText;

    @FXML
    private TextField subjectText;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField emailText;

    @FXML
    private PasswordField passwordText;

    @FXML
    void sendMail(ActionEvent event) throws IOException {
        if (areFieldsFilled()) {
            errorLabel.setText("Enviando email!");
            
            String[] recipients = null;
            
            recipients = toText.getText().split(";");
            
            MailSender sender = new MailSender();
            sender.setMessage(emailText.getText(), passwordText.getText(), 
                    recipients, subjectText.getText(), contentText.getText());
            
        } else {
            errorLabel.setText("Todos os campos precisam estar preenchidos.");
        }
    }

    @FXML
    void sendMessage(ActionEvent event) {
        if (messageText.getText().equals("")) {
            errorLabel2.setText("Campos de mensagem precisa estar preenchido.");
        } else {
            // mandar mensagem para o server
            chatText.setText(chatText.getText() + "Você >> " + messageText.getText() + "\n");
            messageText.setText("");
        }
    }
    
    @FXML
    void clearErrorLabel(MouseEvent event) {
        errorLabel.setText("");
    }

    @FXML
    void clearErrorLabel2(MouseEvent event) {
        errorLabel2.setText("");
    }
    
    private boolean areFieldsFilled() {
        return !toText.getText().equals("")
                && !copiedText.getText().equals("") 
                && !subjectText.getText().equals("") 
                && !contentText.getText().equals("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chatText.setEditable(false);
    }    
    
}
