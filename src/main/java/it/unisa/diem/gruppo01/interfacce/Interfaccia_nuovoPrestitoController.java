/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.interfacce;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nicolaazzato
 */
public class Interfaccia_nuovoPrestitoController implements Initializable {

    @FXML
    private TextField inserisciM;
    @FXML
    private TextField inserisciN;
    @FXML
    private TextField inserisciC;
    @FXML
    private TextField inserisciE;
    @FXML
    private CheckBox spuntaU;
    @FXML
    private TextField inserisciT;
    @FXML
    private TextField inserisciA;
    @FXML
    private CheckBox spuntaL;
    @FXML
    private Button salvaP;
    @FXML
    private TextField inserisciMa;
    @FXML
    private TextField inserisciISBN;
    @FXML
    private Button salvaRes;
    @FXML
    private Button exitApp;
    @FXML
    private Button menuPButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void inserisciMatricola(ActionEvent event) {
    }

    @FXML
    private void inserisciNome(ActionEvent event) {
    }

    @FXML
    private void inserisciCognome(ActionEvent event) {
    }

    @FXML
    private void inserisciEmail(ActionEvent event) {
    }

    @FXML
    private void spuntaUtente(ActionEvent event) {
    }

    @FXML
    private void inserisciTitolo(ActionEvent event) {
    }

    @FXML
    private void inserisciAutore(ActionEvent event) {
    }

    @FXML
    private void spuntaLibro(ActionEvent event) {
    }

    @FXML
    private void salvaPrestito(ActionEvent event) {
    }

    @FXML
    private void inserisceMatricola(ActionEvent event) {
    }

    @FXML
    private void inserisceISBN(ActionEvent event) {
    }

    @FXML
    private void salvaRestituzione(ActionEvent event) {
    }
    
     @FXML
    private void exitPFile(ActionEvent event) {
        
        Node exit = (Node) event.getSource();
        
        Stage stageExit = (Stage) exit.getScene().getWindow();
        
        stageExit.close();
    }
    
     @FXML
    private void menuPReturn(ActionEvent event) {
           try {
                Parent menuParent = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Menu_Biblioteca.fxml"));
                Scene menuScene = new Scene(menuParent);
                
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(menuScene);
                window.setTitle("Gestione Biblioteca - Menu Principale");
                window.show();
                
                
            } catch (IOException ex) {
               System.out.println("ERRORE:impossibile trovare Menu_Biblioteca_view.fxml");
               ex.printStackTrace();
            }
    }
    
    
}
