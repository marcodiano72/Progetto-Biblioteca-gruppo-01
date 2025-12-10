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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utente
 */
public class GestioneStudenteController implements Initializable {

    @FXML
    private Button addSButton;
    @FXML
    private Button modSButton;
    @FXML
    private Button searchSButton;
    @FXML
    private Button deleteSButton;
    @FXML
    private Button exitSButton;
    @FXML
    private Button saveSButton;
    @FXML
    private Button menuSButton;
    @FXML
    private TextField tfCognome;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfMatricola;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField insMatricola;
    @FXML
    private TextField insCognome;
    @FXML
    private TableView<?> listaStudenti;
    @FXML
    private TableColumn<?, ?> colCognome;
    @FXML
    private TableColumn<?, ?> colNome;
    @FXML
    private TableColumn<?, ?> colMatr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addStudent(ActionEvent event) {
    }

    @FXML
    private void modStudent(ActionEvent event) {
    }

    @FXML
    private void searchStudent(ActionEvent event) {
    }

    @FXML
    private void deleteStudent(ActionEvent event) {
    }

    @FXML
    private void exitS(ActionEvent event) {
        Node exit = (Node) event.getSource();
        
        Stage stageExit = (Stage) exit.getScene().getWindow();
        
        stageExit.close();
    }

    @FXML
    private void saveSFile(ActionEvent event) {
    }
    
    @FXML
    private void menuSReturn(ActionEvent event) {
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
