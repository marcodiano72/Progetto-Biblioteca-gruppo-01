/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * FXML Controller class
 *
 * @author Utente
 */
public class VisualizzaLibro_viewController implements Initializable {

    @FXML
    private Label titolo;
    @FXML
    private Label autore;
    @FXML
    private Label isbn;
    @FXML
    private Label anno;
    @FXML
    private Label copie;

    /**
     * Initializes the controller class.
     */
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Puliamo le label dei prestiti all'avvio
        resetLabels();
    }    

    /**
     * Metodo fondamentale: riceve lo studente selezionato e popola la vista.
     * @param s Lo studente da visualizzare.
     */
    public void setDatiLibro(Libro l) {
        if (l == null) return;

        // 1. Popola i dati anagrafici
        titolo.setText(l.getTitolo());
        autore.setText(l.getAutore());
        isbn.setText(l.getIsbn());
        anno.setText(l.getAnnoPb().toString());
        copie.setText(String.valueOf(l.getNumCopie()));

    }

    private void resetLabels() {
        titolo.setText("-"); autore.setText("-"); isbn.setText("-");
        anno.setText("-"); copie.setText("-"); 
    }


    @FXML
    private void chiudiFinestra(ActionEvent event) {
             
            try {
                Parent menuParent = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/GestioneLibro_view.fxml"));
                Scene menuScene = new Scene(menuParent);
                
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(menuScene);
                window.setTitle("Gestione Biblioteca - Gestione Studente");
                window.show();
                
                
            } catch (IOException ex) {
               System.out.println("ERRORE:impossibile trovare GestioneLibro_view.fxml");
               ex.printStackTrace();
            }
        }
    
}
