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

/**
 * FXML Controller class
 *
 * @author nicolaazzato
 */
public class Interfaccia1Controller implements Initializable {

    @FXML
    private Button gestioneLButton;
    @FXML
    private Button gestioneSButton;
    @FXML
    private Button gestionePButton;
    @FXML
    private Button gestioneEButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void openLibriInterface(ActionEvent event) {
    }

    @FXML
    private void openStudentInterface(ActionEvent event) {
    }

    @FXML
    private void openPrestitiInterface(ActionEvent event) {
    }

    @FXML
    private void openExitInterface(ActionEvent event) {
    }
    
}
