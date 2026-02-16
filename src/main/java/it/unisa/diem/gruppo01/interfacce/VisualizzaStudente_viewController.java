/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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
 * FXML Controller class
 *
 * @author macucc
 */
public class VisualizzaStudente_viewController implements Initializable {

    @FXML
    private Label nomeField;
    @FXML
    private Label cognomeField;
    @FXML
    private Label emailField;
    @FXML
    private Label matricolaField;
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
    @FXML
    private Label sanzioneFIeld;
    @FXML
    private Label titolo1;
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
     * Initializes the controller class.
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
     * Metodo fondamentale: riceve lo studente selezionato e popola la vista.
     * @param s Lo studente da visualizzare.
     */
    public void setDatiStudente(Studente s) {
        if (s == null) return;

        // 1. Popola i dati anagrafici
        nomeField.setText(s.getNome());
        cognomeField.setText(s.getCognome());
        emailField.setText(s.getEmail());
        matricolaField.setText(s.getMatricola());
        sanzioneFIeld.setText(s.getSanzione());
        // 2. Popola i prestiti attivi
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

    private void resetLabels() {
        libro1Label.setText("-"); inizio1Label.setText("-"); fine1Label.setText("-");
        libro2Label.setText("-"); inizio2Label.setText("-"); fine2Label.setText("-");
        libro3Label.setText("-"); inizio3Label.setText("-"); fine3Label.setText("-");
    }


    @FXML
    private void chiudiFinestra(ActionEvent event) {
    // 1. Ottieni lo Stage (la finestra) corrente partendo dal pulsante che ha scatenato l'evento
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
    // 2. Chiudi la finestra
    stage.close();
}
    
}
