/*
*@file Menu_BibliotecaController.java
 *@brief Controller FXML per la schermata del Menu Principale della Biblioteca.
 * Gestisce la navigazione verso le diverse sezioni di gestione (Libri, Studenti, Prestiti)
 * e inizializza i modelli dati principali (Catalogo e Elenco) per l'intera applicazione.
 *
 *@author Gruppo01
 *@version 1.0
*/

package it.unisa.diem.gruppo01.interfacce;

import it.unisa.diem.gruppo01.classi.Catalogo;
import it.unisa.diem.gruppo01.classi.Elenco; // IMPORT NECESSARIO
import java.io.IOException;
import java.net.URL;
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
import javafx.util.Duration;

/*
*@brief CONTROLLER FXML: Menu_BibliotecaController
 * Responsabile dell'inizializzazione dei dati globali (Catalogo Libri e Elenco Studenti)
 * e della transizione verso le interfacce di Gestione.
*/
public class Menu_BibliotecaController implements Initializable {

    @FXML
    private Button gestioneLButton; ///< Pulsante per accedere all'interfaccia di gestione dei libri.
    @FXML
    private Button gestioneSButton;  ///< Pulsante per accedere all'interfaccia di gestione dei studenti.
    @FXML
    private Button gestionePButton; ///< Pulsante per accedere all'interfaccia di gestione dei prestiti.
    @FXML
    private Button gestioneEButton; ///< Pulsante per accedere all'interfaccia di login
    @FXML
    private DatePicker data;  ///< Campo per visualizzare la data corrente
    @FXML
    private Label orario; ///< Etichetta per visualizzare l'orario corrente in tempo reale.
    
    
    
    private Catalogo catalogo;
    private Elenco elenco; 
  
    

    /*
     * @brief Metodo che imposta l'istanza del Catalogo.
     * @param catalogo L'istanza del Catalogo da utilizzare.
    */
   public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
        System.out.println("Catalogo ricevuto con successo nel Menu' Principale");
    }
    
    
    /*
     * @brief Metodo che imposta l'istanza dell'Elenco Studenti.
     * @param elenco L'istanza dell'Elenco Studenti da utilizzare.
    */
    public void setElenco(Elenco elenco) {
        this.elenco = elenco;
    }
    

    /*
     * @brief Metodo che inizializza il controller dopo che l'elemento radice FXML è stato elaborato.
     * Configura i Tooltip per i pulsanti.
     * Inizializza i Modelli Dati: Ottiene l'istanza del Catalogo e carica l'Elenco Studenti.
     * @param url L'ubicazione relativa o assoluta del file FXML.
     * @param rb Le risorse utilizzate per localizzare l'oggetto radice
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ... (Codice Tooltip e Data invariato) ...
        gestioneLButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione dei libri"));
        gestioneSButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione degli studenti"));
        gestionePButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione dei prestiti"));
        gestioneEButton.setTooltip(new Tooltip("Torna al login"));
        
        
        //Setup Data e ora
        data.setValue(LocalDate.now());
        data.setDisable(true); 

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), 
                event -> orario.setText(LocalTime.now().format(timeFormatter))
            ),
            new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
      
        
        this.catalogo = Catalogo.getIstanza();
        
        // Inizializziamo l'elenco e carichiamo i dati dal file CSV
        this.elenco = new Elenco();
        this.elenco.caricaDati(); 
    }  
    
    

    /*
     * @brief Metofo che gestisce l'evento di clic sul pulsante Gestione Libri (gestioneLButton).
     * Carica la vista GestioneLibri_view.fxml e passa l'istanza del Catalogo
     * al relativo controller.
     *
     * @param event L'evento di azione generato dal clic.
    */
    @FXML
    private void openLibriInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/GestioneLibri_view.fxml"));
            Parent libriParent = loader.load();

            GestioneLibriController libriController = loader.getController();
            libriController.setCatalogo(this.catalogo);
            Scene libriScene = new Scene(libriParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(libriScene);
            window.setTitle("Menù Principale - Gestione Libri");
            window.show();
        
        } catch (IOException ex) {
            System.out.println("ERRORE: impossibile trovare GestioneLibri_view.fxml");
            ex.printStackTrace();
        }
    }
    
    
    /*
     * @brief Metodo che gestisce l'evento di clic sul pulsante Gestione Studenti (gestioneSButton).
     * Carica la vista GestioneStudente_view.fxml.
     *
     * @param event L'evento di azione generato dal clic.
    */
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
            System.out.println("ERRORE: impossibile trovare GestioneStudente_view.fxml");
            ex.printStackTrace();
        }
    }

    

    /*
     * @brief Metodo che gestisce l'evento di clic sul pulsante Gestione Prestiti (gestionePButton).
     * Carica la vista nterfaccia_nuovoPrestito.fxml e, 
     * passa sia l'istanza di Elenco (studenti) che l'istanza di Catalogo (libri)
     * al controller dei prestiti affinché possa operare sui dati.
     *
     * @param event L'evento di azione generato dal clic.
    */
    @FXML
    private void openPrestitiInterface(ActionEvent event) {
        try {
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Interfaccia_nuovoPrestito.fxml"));
            Parent prestitiParent = loader.load(); 
            
            Interfaccia_nuovoPrestitoController prestitiController = loader.getController();
            
            prestitiController.setDati(this.elenco, this.catalogo);
            
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

    
    /*
     *@brief Metodo che gestisce l'evento di clic sul pulsante Esci (gestioneEButton).
     * Ritorna all'interfaccia di login Interfaccia1View.fxm.
     * @param event L'evento di azione generato dal clic.
    */
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
