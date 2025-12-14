/**
 * @file VisualizzaStudente_viewController.java
 * @brief Controller per la visualizzazione dei dettagli di uno studente.
 * Gestisce l'interfaccia che mostra i dati anagrafici, lo stato delle sanzioni e i dettagli
 * dei prestiti attivi di uno specifico studente.
 * @author Gruppo01
 * @version 1.0
 */
package it.unisa.diem.gruppo01.interfacce;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @brief CONTROLLER FXML: VisualizzaStudente_viewController
 * Classe controller per la finestra di visualizzazione  dettagli studente.
 * Riceve un oggetto Studente dal controller principale (GestioneStudenteController) e popola le label 
 * con le informazioni corrispondenti.
 * 
 */

public class VisualizzaStudente_viewController implements Initializable {

    @FXML
    private Label nomeField;    ///< Label per visualizzare il nome dello studente.
    @FXML
    private Label cognomeField; ///< Label per visualizzare il cognome dello studente.
    @FXML
    private Label emailField;   ///< Label per visualizzare l'email dello studente.
    @FXML
    private Label matricolaField;  ///< Label per visualizzare la matricola dello studente.
    
    //Label per il primo prestito
    
    @FXML
    private Label libro1Label;    ///< Titolo del primo libro in prestito.
    @FXML
    private Label inizio1Label;   ///< Data d'inizio del primo prestito.
    @FXML
    private Label fine1Label;    ///< Data di scadenza del primo prestito.
    
    //Label per il secondo prestito
    @FXML
    private Label libro2Label;   ///< Titolo del secondo libro in prestito.
    @FXML
    private Label inizio2Label;  ///< Data d'inizio del secondo prestito.
    @FXML
    private Label fine2Label;   ///< Data di scadenza del secondo prestito.
    
    //Label per il terzo prestito
    @FXML
    private Label libro3Label;  ///< Titolo del terzo libro in prestito. 
    @FXML
    private Label inizio3Label;  ///< Data d'inizio del terzo prestito
    @FXML  
    private Label fine3Label;   ///< Data di scadenza del terzo prestito.
    
    
    @FXML
    private Label sanzioneFIeld;  ///< Label per visualizzare lo stato della sanzione.
    @FXML
    private Label titolo1;    ///< 
    @FXML
    private Label titolo2;
    @FXML
    private Label titolo3;
    @FXML
    private Label inizio2;
    @FXML
    private Label inizio1;
    @FXML
    private Label fine1;
    @FXML
    private Label fine2;
    @FXML
    private Label inizio3;
    @FXML
    private Label fine3;

    /**
     * @brief Inizializza il controller
     * Metodo chiamato in automatico dopo il caricamento del file FXML.
     * Controlla che le label dei prestiti siano resettate a uno stato iniziale/vuoto.
     * @param[in] url La posizione usata come base per i percorsi relativi dell’oggetto radice, o null se sconosciuta.
     * @param[in] rb Le risorse utilizzate per localizzare l'oggetto radice, o null se non localizzato.
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Rendiamo i campi non modificabili, visto che è solo una visualizzazione
        /*nomeField.setEditable(true);
        cognomeField.setEditable(true);
        emailField.setEditable(true);
        matricolaField.setEditable(true);
        */
        // Puliamo le label dei prestiti all'avvio
        resetLabels();
    }    

    /**
     * @brief Imposta i dati dello studente nell'interfaccia.
     * Questo metodo deve essere chiamato dal controller chiamante per passare l'oggetto
     * Studente selezionato.
     * Riempie i campi anagrafici e itera sulla lista dei prestiti attivi 
     * per riempire gli slot disponibili. (Nel nostro caso possono arrivare fino a 3).
     * @param[in] s L'oggetto Studente da visualizzare. Se null, il metodo non fa nulla.
     */
    public void setDatiStudente(Studente s) {
        if (s == null) return;

        //  Popola i dati anagrafici
        nomeField.setText(s.getNome());
        cognomeField.setText(s.getCognome());
        emailField.setText(s.getEmail());
        matricolaField.setText(s.getMatricola());
        sanzioneFIeld.setText(s.getSanzione());
        //  Popola i prestiti attivi
        List<Prestito> prestiti = s.getPrestitiAttivi();
        
        // Gestione Primo Prestito
        if (prestiti.size() > 0) {
            Prestito p1 = prestiti.get(0);
            // Assumo che Prestito abbia getLibro().getTitolo(), getDataInizio(), getDataFine()
            // Se i metodi si chiamano diversamente, correggi qui sotto:
            libro1Label.setText(p1.getLibro().getTitolo()); 
            inizio1Label.setText(p1.getDataInizio().toString());
            fine1Label.setText(p1.getDataScadenza().toString());
        }

        // Gestione Secondo Prestito
        if (prestiti.size() > 1) {
            Prestito p2 = prestiti.get(1);
            libro2Label.setText(p2.getLibro().getTitolo());
            inizio2Label.setText(p2.getDataInizio().toString());
            fine2Label.setText(p2.getDataScadenza().toString());
        }

        // Gestione Terzo Prestito
        if (prestiti.size() > 2) {
            Prestito p3 = prestiti.get(2);
            libro3Label.setText(p3.getLibro().getTitolo());
            inizio3Label.setText(p3.getDataInizio().toString());
            fine3Label.setText(p3.getDataScadenza().toString());
        }
    }

    /**
     * @brief Resetta le label relative ai prestiti.
     * Imposta il testo dei campi a un trattino "-" per indicare
     * che non ci sono dati presenti.
     */
    private void resetLabels() {
        libro1Label.setText("-"); inizio1Label.setText("-"); fine1Label.setText("-");
        libro2Label.setText("-"); inizio2Label.setText("-"); fine2Label.setText("-");
        libro3Label.setText("-"); inizio3Label.setText("-"); fine3Label.setText("-");
    }

/**
 * @brief Gestisce la chiusura della finestra.
 * Recupera lo Stage corrente e lo chiude.
 * @param[in] event L'evento generato dal click sul pulsante di chiusura.
 */
    @FXML
    private void chiudiFinestra(ActionEvent event) {
    
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
    //Chiudi la finestra
    stage.close();
}
    
}
