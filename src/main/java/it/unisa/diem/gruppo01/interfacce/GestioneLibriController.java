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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import it.unisa.diem.gruppo01.strumenti.Libro;
import java.time.LocalDate;
import javafx.scene.control.Label;

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
    private TableView<Libro> tableViewLibri;
    @FXML
    private TableColumn<Libro, String> colonnaTitolo;  
    @FXML
    private TableColumn<Libro, String> colonnaAutore;
    @FXML
    private TableColumn<Libro, String> colonnaIsbn;
    @FXML
    private TextField tfTitolo;
    @FXML
    private TextField tfAutore;
    @FXML
    private TextField tfIsbn;
    @FXML
    private TextField tfAnno;
    @FXML
    private Button addLButton;
    @FXML
    private ImageView addButton;
    @FXML
    private Button modLButton;
    @FXML
    private TextField tfTitoloRicerca;
    @FXML
    private TextField tfAutoreRicerca;
    @FXML
    private TextField tfIsbnRicerca;
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
    @FXML
    private Label errorMessageLabel; 

    

    

    
   
   
   
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
        errorMessageLabel.setText("");  //resetta l'errore, quando il campo viene compilato la scritta scompare
        
        //Recupero dei dati dalle caselle di testo
        String titolo = tfTitolo.getText();
        String autore = tfAutore.getText();
        String Isbn = tfIsbn.getText();
        String annoP = tfAnno.getText();
        
        //Controllo campi vuoti (in caso di mancata compilazione mostra avviso)
        if(titolo.isEmpty() || autore.isEmpty() || Isbn.isEmpty() || anno.isEmpty()){
            errorMessageLabel.setText("Attenzione: devi compilare tutti i campi!");
            errorMessageLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        
        try{
            //bisogna convertire anno, perchè Libro richiede una data LocalDate, ma noi inseriamo una Stringa nel textfield
            int anno = Integer.parseInt(annoP); //converte in Int
            LocalDate annoPubblicazione = LocalDate.of(anno,1,1); //converte nel formato 01/01/anno;
            
            //Creazione oggetto Libro
            Libro nuovoLibro = new Libro(Isbn, titolo, autore, annoPubblicazione , 1);
            
        }catch(IllegalargumentException ex){
        
        
        
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