package it.unisa.diem.gruppo01.interfacce;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.unisa.diem.gruppo01.classi.Catalogo;
import java.io.IOException;

import java.net.URL;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
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
    
    @FXML
    private DatePicker data; 
    @FXML
    private Label orario;
    
    private Catalogo catalogo;
    
    public void setCatalogo(Catalogo catalogo)
    {
        this.catalogo=catalogo;
        System.out.println("Catalogo ricevuto con succeso nel Menu' Principale");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Appare un suggerimento quando il mouse si ferma sul bottone
        gestioneLButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione dei libri"));
        gestioneSButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione degli studenti"));
        gestionePButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione dei prestiti"));
        gestioneEButton.setTooltip(new Tooltip("Torna al login"));
        
        //Data e ora
        // Imposta la data corrente nel DatePicker
        data.setValue(LocalDate.now());
        data.setDisable(true); // Lo rendiamo non modificabile

        // Crea il formatter per l'ora (HH:mm:ss)
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Configura la Timeline per aggiornare l'ora ogni secondo
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), 
                event -> orario.setText(LocalTime.now().format(timeFormatter))
            ),
            new KeyFrame(Duration.seconds(1)) // Aggiorna ogni 1 secondo
        );
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
       
    }    

    @FXML
    private void openLibriInterface(ActionEvent event) {
        
        if (catalogo == null) {
            System.err.println("ERRORE: Catalogo non iniettato correttamente nel Menu.");
            this.catalogo = Catalogo.getIstanza(); 
        }
        
        try{
            
        // 1. CARICAMENTO TRAMITE FXMLLoader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/GestioneLibri_view.fxml"));
            Parent libriParent = loader.load();

            // 2. RECUPERO E INIEZIONE DEL CONTROLLER
            GestioneLibriController libriController = loader.getController();
            
            // --- INIEZIONE DI DIPENDENZA ---
            // Passiamo l'istanza del Catalogo al controller di Gestione Libri
            libriController.setCatalogo(this.catalogo); 
            // -------------------------------
            
            Scene libriScene = new Scene(libriParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(libriScene);
            window.setTitle("Menù Principale - Gestione Libri");
            window.show();
        
    }catch (IOException ex){
        System.out.println("ERRORE:impossibile trovare GestioneLibri_view.fxml");
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
               System.out.println("ERRORE:impossibile trovare GestioneStudente_view.fxml");
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
