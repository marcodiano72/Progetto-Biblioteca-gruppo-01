package it.unisa.diem.gruppo01.interfacce;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Marco Diano'
 */
public class Interfaccia1Controller implements Initializable {

    @FXML
    private Button accessButton;
    @FXML
    private PasswordField passwordFile;
    @FXML
    private TextField usernameField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openMenu(ActionEvent event) {
    }
    
}
