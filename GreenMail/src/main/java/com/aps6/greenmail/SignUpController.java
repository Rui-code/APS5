/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aps6.greenmail;

import com.aps6.greenmail.DAO.UserDAO;
import com.aps6.greenmail.chat.SocketClient;
import com.aps6.greenmail.models.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ruiran
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField newUserText;

    @FXML
    private PasswordField newPasswordText;

    @FXML       
    private PasswordField repeatNewPasswordText;

    @FXML
    private Label errorLabel;

    @FXML
    void clearErrorLabel(MouseEvent event) {
        errorLabel.setText("");
    }

    @FXML
    void loginView(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void sendSignUpData(ActionEvent event) throws IOException {
        if (newUserText.getText().equals("") || repeatNewPasswordText.getText()
                .equals("") || newPasswordText.getText().equals("") ) {           
            errorLabel.setText("Todos os campos precisam estar preenchidos.");
            
        } else if (!newPasswordText.getText()
                .equals(repeatNewPasswordText.getText())) {     
            errorLabel.setText("Os campos de senha precisam ser iguais.");
            newPasswordText.setText("");
            repeatNewPasswordText.setText("");
            
        } else {
            // Enviar os dados para o dao validar           
            User newUser = new User();
            newUser.setUserName(newUserText.getText());
            newUser.setPassword(newPasswordText.getText());
            
            UserDAO newUserDAO = new UserDAO();
            if (!newUserDAO.UserExists(newUser)) {
                newUserDAO.create(newUser);
                App.setRoot("ChatAndMail");
                // startar o cliente do chat
                SocketClient.init();
            } else {
                errorLabel.setText("Usuario já existe.");
            }
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
