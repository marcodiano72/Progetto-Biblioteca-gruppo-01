package it.unisa.diem.gruppo01.interfacce;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marco Diano'
 */
public class Menu_BibliotecaController implements Initializable {

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
        
        try{
            
        Parent libriParent = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/GestioneLibri_view.fxml"));
        Scene libriScene = new Scene(libriParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(libriScene);
        window.setTitle("Menù Principale - Gestione Libri");
        window.show();
        
    }catch (IOException ex){
        System.out.println("ERRORE critico nel caricamento di Gestione Libri");
        ex.printStackTrace();
      }
    }
    
    

    @FXML
    private void openStudentInterface(ActionEvent event){
        
        
            
            try {
                Parent studentiParent = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/GestioneStudente_view.fxml"));
                Scene studentiScene = new Scene(studentiParent);
                
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(studentiScene);
                window.setTitle("Menù Principale - Gestione Studenti");
                window.show();
                
                
            } catch (IOException ex) {
               System.out.println("ERRORE critico nel caricamento di Gestione Studenti");
               ex.printStackTrace();
            }
        }
    

    @FXML
    private void openPrestitiInterface(ActionEvent event) {
        
        
                try {
                Parent prestitiParent = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Interfaccia_nuovoPrestito.fxml"));
                Scene prestitiScene = new Scene(prestitiParent);
                
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(prestitiScene);
                window.setTitle("Menù Principale - Gestione Prestiti");
                window.show();
                
                
            } catch (IOException ex) {
               System.out.println("ERRORE critico nel caricamento di Gestione Prestiti");
               ex.printStackTrace();
            }
    }

    @FXML
    private void openExitInterface(ActionEvent event) {
        
                try {
                Parent loginParent = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Interfaccia1View.fxml"));
                Scene loginScene = new Scene(loginParent);
                
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(loginScene);
                window.setTitle("Menù Principale - Login");
                window.show();
                
                
            } catch (IOException ex) {
               System.out.println("ERRORE critico nel ritorno al Login");
               ex.printStackTrace();
            }
    }
    
}
