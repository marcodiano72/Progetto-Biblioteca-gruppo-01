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
    
    // --- CAMBIAMENTO 1: Aggiungi le variabili di istanza per i dati ---
    private Catalogo catalogo;
    private Elenco elenco; // Manca la lista studenti nel menu!
    // -----------------------------------------------------------------

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
        System.out.println("Catalogo ricevuto con successo nel Menu' Principale");
    }
    
    // (Opzionale) Se vuoi passare l'elenco già caricato da altre parti
    public void setElenco(Elenco elenco) {
        this.elenco = elenco;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ... (Codice Tooltip e Data invariato) ...
        gestioneLButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione dei libri"));
        gestioneSButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione degli studenti"));
        gestionePButton.setTooltip(new Tooltip("Accede alla sezione dedicata alla gestione dei prestiti"));
        gestioneEButton.setTooltip(new Tooltip("Torna al login"));
        
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
        
        // --- CAMBIAMENTO 2: Inizializzazione Dati ---
        // Assicuriamoci che i dati esistano quando il menu parte
        this.catalogo = Catalogo.getIstanza();
        
        // Inizializziamo l'elenco e carichiamo i dati dal file CSV
        // Così il Menu ha la lista studenti pronta da passare alla schermata Prestiti
        this.elenco = new Elenco();
        this.elenco.caricaDati(); 
    }    

    @FXML
    private void openLibriInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/GestioneLibri_view.fxml"));
            Parent libriParent = loader.load();

            GestioneLibriController libriController = loader.getController();
            // Passiamo il catalogo (Singleton)
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

    @FXML
    private void openStudentInterface(ActionEvent event){
        try {
            // Nota: Qui potresti voler passare 'this.elenco' al controller studenti 
            // invece di farglielo ricaricare da zero, per mantenere coerenza nei dati.
            // Ma per ora lasciamo come avevi fatto tu, funziona lo stesso (anche se meno efficiente).
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

    // --- CAMBIAMENTO 3: IL METODO CRITICO ---
    @FXML
    private void openPrestitiInterface(ActionEvent event) {
        try {
            // 1. Usa FXMLLoader classico, NON il metodo statico load()
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Interfaccia_nuovoPrestito.fxml"));
            Parent prestitiParent = loader.load(); // Carica la grafica
            
            // 2. Ottieni il controller
            Interfaccia_nuovoPrestitoController prestitiController = loader.getController();
            
            // 3. PASSA I DATI! Questo è il passaggio che mancava.
            // Passiamo l'elenco studenti (che abbiamo caricato nell'initialize) e il catalogo
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
