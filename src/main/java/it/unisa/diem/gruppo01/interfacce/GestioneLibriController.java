/**
*@file GestioneLibriController.java
*@brief file per gestire le interazioni utente al fine di aggiungere, modificare, cercare, eliminare e salvare
*
*@version 1.0
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller FXML: GestioneLibriController
 * Controllore FXML per la schermata di gestione dei libri nel sistema bibliotecario.
 * Gestisce le interazioni utente per aggiungere, modificare, cercare, eliminare e salvare
 * l'inventario dei libri.
 *
 * @author Utente
 */
public class GestioneLibriController implements Initializable {

    @FXML
    private Button addLButton;
    @FXML
    private ImageView addButton;
    @FXML
    private Button modLButton;
    @FXML
    private TextField codeField;
    @FXML
    private TextField codeField1;
    @FXML
    private TextField codeField11;
    @FXML
    private Button searchLButton;
    @FXML
    private Button deleteLButton;
    @FXML
    private Button exitLButton;
    @FXML
    private Button saveLButton;
    @FXML
    private Button menuButton;
   
    /*
    * Metodo chiamato per inizializzare un controller dopo che il suo elemento radice è stato completamente elaborato.
     * Viene utilizzato per setup iniziali, come l'impostazione dei listener o il caricamento dei dati di default.
     *
     * @param url L'ubicazione relativa o assoluta del file FXML.
     * @param rb Le risorse utilizzate per localizzare l'oggetto radice.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /*
    * Gestisce l'evento di clic sul pulsante Aggiungi Libro.
     * Funzionalità: Raccoglie i dati dai campi di testo, crea un nuovo
     * oggetto Libro e lo aggiunge al Catalogo.
     * @param event L'evento di azione generato dal clic.
     */
    @FXML
    private void addLibri(ActionEvent event) {
    }

    /*
    * Gestisce l'evento di clic sul pulsante Modifica Libro.
     *  Funzionalità: Cerca un libro tramite un identificatore (es. ISBN) e aggiorna
     * i suoi dettagli (es. titolo, autore o numero di copie) con i valori presenti nei campi di testo.
     *
     * @param event L'evento di azione generato dal clic.
    */
    @FXML
    private void modLibri(ActionEvent event) {
    }
    
    /*
    
    */

    @FXML
    private void searchLibri(ActionEvent event) {
    }
    
    /*
    
    */

    @FXML
    private void deleteLibri(ActionEvent event) {
    }
    
    /*
    
    */

    @FXML
    private void exitL(ActionEvent event) {
        Node exit = (Node) event.getSource();
        
        Stage stageExit = (Stage) exit.getScene().getWindow();
        
        stageExit.close();
    }
    
    /*
    
    */

    @FXML
    private void saveLFile(ActionEvent event) {
    }
    
    
    @FXML
    private void menuReturn(ActionEvent event) {
          
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