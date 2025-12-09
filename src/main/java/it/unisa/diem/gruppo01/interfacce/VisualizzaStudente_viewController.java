/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package it.unisa.diem.gruppo01.interfacce;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author macucc
 */
public class VisualizzaStudente_viewController implements Initializable {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField matricolaField;
    @FXML
    private Label libro1Label;
    @FXML
    private Label inizio1Label;
    @FXML
    private Label fine1Label;
    @FXML
    private Label libro2Label;
    @FXML
    private Label inizio2Label;
    @FXML
    private Label fine2Label;
    @FXML
    private Label libro3Label;
    @FXML
    private Label inizio3Label;
    @FXML
    private Label fine3Label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void chiudiFinestra(ActionEvent event) {
    }
    
}
