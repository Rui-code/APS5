/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aps6.greenmail;

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
public class LoginController implements Initializable {

    @FXML
    private TextField userInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label labelError;

    @FXML
    void sendLoginData(ActionEvent event) throws IOException {
        if (passwordInput.getText().equals("") || userInput.getText().equals("")) {
            labelError.setText("Todos os campos precisam estar preenchidos para continuar.");
        } else {
            // Enviar dados para o dao validar..
            labelError.setText("Validação completa!");
            App.setRoot("ChatAndMail");
        }
        
    }
    
    @FXML
    void clearErrorLabel(MouseEvent event) {
        labelError.setText("");
    }
    
    @FXML
    void signUpView(ActionEvent event) throws IOException {
        App.setRoot("signUp");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
