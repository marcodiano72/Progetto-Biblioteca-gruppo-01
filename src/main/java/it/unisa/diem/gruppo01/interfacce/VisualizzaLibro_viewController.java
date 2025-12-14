/**
 * @file VisualizzaLibro_viewController.java
 * @brief Controller per la visualizzazione dei dettagli di un libro.
 * Gestisce l'interfaccia che mostra i dati (titolo, autore, Isbn, anno,numcopie)
 * e la disponibilità di un libro specifico selezionato da catalogo.
 * @author Gruppo01
 * @version 1.0
 */


package it.unisa.diem.gruppo01.interfacce;

import it.unisa.diem.gruppo01.classi.Libro;
import it.unisa.diem.gruppo01.classi.Prestito;
import it.unisa.diem.gruppo01.classi.Studente;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @brief CONTROLLER FXML: VisualizzaLibro_viewController
 * Classe controller per la finestra di visualizzazione dettagli libro.
 * Riceve un oggetto Libro dal controller principale (GestioneLibriController)
 * e popola le label con le informazioni corrispondenti.
 */

public class VisualizzaLibro_viewController implements Initializable {

    @FXML
    private Label titolo; ///< Label per visualizzare il titolo del libro.
    @FXML
    private Label autore; ///< Label per visualizzare l'autore del libro.
    @FXML
    private Label isbn;   ///< Label per visualizzare l'ISBN del libro.
    @FXML
    private Label anno;   ///< Label per visualizzare l'anno di pubblicazione del libro. 
    @FXML
    private Label copie;  ///< Label per visualizzare il numero di copie disponibili.

   /**
     * @brief Inizializza il controller.
     * Metodo chiamato in automatico dopo il caricamento del file FXML.
     * Imposta le label a uno stato di default (trattino) prima del caricamento dei dati.
     * @param url La posizione usata come base per i percorsi relativi dell’oggetto radice, o null se sconosciuta.
     * @param rb Le risorse utilizzate per localizzare l'oggetto radice.
     */
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Puliamo le label dei prestiti all'avvio
        resetLabels();
    }    

    /**
     * @brief Imposta i dati del libro nell'interfaccia.
     * Questo metodo deve essere chiamato dal controller chiamante per passare
     * l'oggetto Libro selezionato. Aggiorna le label della vista con i dettagli del libro.
     * @param[in] l L'oggetto Libro da visualizzare. Se null, il metodo interrompe l'esecuzione.
     */
    
    public void setDatiLibro(Libro l) {
        if (l == null) return;

        // Popola i dati anagrafici
        titolo.setText(l.getTitolo());
        autore.setText(l.getAutore());
        isbn.setText(l.getIsbn());
        anno.setText(String.valueOf(l.getAnnoPb().getYear()));
        copie.setText(String.valueOf(l.getNumCopie()));

    }

    /**
     * @brief Resetta le label dell'interfaccia.
     * Imposta il testo di tutti i campi a un trattino "-" per indicare
     * l'assenza di dati o lo stato iniziale.
     */
    
    private void resetLabels() {
        titolo.setText("-"); autore.setText("-"); isbn.setText("-");
        anno.setText("-"); copie.setText("-"); 
    }

    /**
     * @brief Gestisce la chiusura della finestra.
     * Recupera lo Stage corrente e lo chiude,
     * riportando l'utente alla schermata precedente.
     * @param[in] event L'evento generato dal click sul pulsante di chiusura.
     */

    @FXML
    private void chiudiFinestra(ActionEvent event) {
    
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
    //  Chiudi la finestra
    stage.close();
}
    
}
