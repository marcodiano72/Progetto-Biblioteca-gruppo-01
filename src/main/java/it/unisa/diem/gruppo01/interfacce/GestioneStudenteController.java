/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.interfacce;

import it.unisa.diem.gruppo01.classi.Elenco;
import it.unisa.diem.gruppo01.classi.Libro;
import it.unisa.diem.gruppo01.classi.Studente;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utente
 */
public class GestioneStudenteController implements Initializable {

    @FXML
    private Button addSButton;
    @FXML
    private Button modSButton;
    @FXML
    private Button searchSButton;
    @FXML
    private Button deleteSButton;
    @FXML
    private Button exitSButton;
    @FXML
    private Button saveSButton;
    @FXML
    private Button menuSButton;
    @FXML
    private TextField tfCognome;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfMatricola;
    @FXML
    private TextField tfEmail;
    @FXML
    private Label ricerca;
    @FXML
    private TextField insMatricola;
    @FXML
    private TextField insCognome;
    @FXML
    private TableView<Studente> tableViewStudenti;
    @FXML
    private TableColumn<Studente, String> colCognome;
    @FXML
    private TableColumn<Studente, String> colNome;
    @FXML
    private TableColumn<Studente, String> colMatr;
    
    
   
    private Elenco elenco;
    private ObservableList<Studente> listaStudenti = FXCollections.observableArrayList();
   /* Metodo chiamato per inizializzare un controller dopo che il suo elemento radice è stato completamente elaborato.
     * Viene utilizzato per setup iniziali, come l'impostazione dei listener o il caricamento dei dati di default.
     *
     * @param url L'ubicazione relativa o assoluta del file FXML.
     * @param rb Le risorse utilizzate per localizzare l'oggetto radice.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        elenco = new Elenco();
        // Collegamento Colonne -> attributi classe libro
        //(new PropertyValueFactory(")) questo metodo per ogni libro passato cerca l'attributo che si specifica 
        //all'interno delle parentesi, o meglio cerca il metodo getAttributo(), prende qel valore e lo inserisce nella casella.
        colCognome.setCellValueFactory(new PropertyValueFactory("cognome"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colMatr.setCellValueFactory(new PropertyValueFactory("matricola"));
        
        //Collegamento Lista -> Tabella
        tableViewStudenti.setItems(listaStudenti);
          
        
        //Aggiungiamo un gestore di eventi del mouse alla tabella
        
        tableViewStudenti.setOnMouseClicked(event ->{
            //Controlla se è un doppio click (lick count == 2) e se è una riga selezionata
            
            if(event.getClickCount() == 2 && tableViewStudenti.getSelectionModel().getSelectedItem() != null){
            Studente studenteSelezionato = tableViewStudenti.getSelectionModel().getSelectedItem();
            
            apriDettagliStudente(studenteSelezionato);
        }
        });
    }
    
    
    private void apriDettagliStudente(Studente s){
        
        try{
         
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/VisualizzaStudente_view.fxml"));
            Parent visualizzaS = loader.load();
            
            //Ottieni il controller della nuova schermata
            VisualizzaStudente_viewController controller = loader.getController();
            
            //Passa i dati dello studente al controller
            
            controller.setDatiStudente(s);
            
            
            Stage visualizzaSscene = new Stage();
            visualizzaSscene.setTitle("Dettagli studente");
            visualizzaSscene.setScene(new Scene(visualizzaS));
            
            visualizzaSscene.show();
           
           
           
           
        }catch(IOException e){
            System.out.println("Errore nel caricamento della vista dettagli.");
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Impossibile aprire la schermata studente.");
            alert.show();
        }
    }
    
    /**
     * Copia i dati da elenco(TreeSet) e li inserisce in listaStudenti (observableList).
     * Assicura che l'ordinamento per cognome definito in elenco venga rispettato.
     */
    
    private void aggiornaInterfaccia(){
        listaStudenti.clear();
        listaStudenti.addAll(elenco.getElencoStudenti());
    }
 /*
    * Gestisce l'evento di clic sul pulsante Aggiungi Studente.
     * Funzionalità: Raccoglie i dati dai campi di testo, crea un nuovo
     * oggetto studente e lo aggiunge all' ELenco.
     * @param event L'evento di azione generato dal clic.
     */
    @FXML
    private void addStudent(ActionEvent event) {
    //Recupero dei dati da caselle di testo
    String cognome = tfCognome.getText();
    String nome = tfNome.getText();
    String matricola = tfMatricola.getText();
    String email = tfEmail.getText();

    //Controllo campi vuoti (in caso di mancata compilazione mostra avviso)
        if(cognome.isEmpty() || nome.isEmpty() || matricola.isEmpty() || email.isEmpty()){
            
            //Alert è una finestra pre-programmata da Java che serve per mostrare messaggi
            //Questa riga costruisce la finestra
            
            Alert alert = new Alert(Alert.AlertType.WARNING);   //AlertType.Warning mostra un'icona triangolo gialla (Avviso)
            alert.setTitle("Dati mancanti");  //Titolo che compare nella parte alta della finestra
            alert.setHeaderText(null); //L'alert è diviso in due parti l'Header e il testo normale. In questo caso non cè Header
            alert.setContentText("Attenzione: devi compilare tutti i campi"); //testo normale
            alert.showAndWait(); //show() mostra la finestra e ...AndWait() signific che il codice si interrompe
            return;
    }

    try{    
     
            //Creazione oggetto Studente
            Studente nuovoStudente = new Studente(cognome, nome, matricola, email, " ", false);
            
            boolean inserito = elenco.aggiungiStudente(nuovoStudente);
            
            if(inserito){
                aggiornaInterfaccia(); //aggiorna la tabella
                pulisciCampi();
            }else{
                //se false, la matricola esiste già
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Errore nei dati inseriti");
                alert.showAndWait();
            
                
            }
            
         
          }catch(IllegalArgumentException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Errore nei dati inseriti");
            alert.showAndWait();
        
        } 
    }
    /**
     * Pulisce le caselle di testo
     */
    
    private void pulisciCampi(){
        tfCognome.clear();
        tfNome.clear();
        tfMatricola.clear();
        tfEmail.clear();
    }

    @FXML
    private void modStudent(ActionEvent event) {
        //Per modificare si assume che l'utente inseisca la matricola
        //e i nuovi dati dello studente nelle caselle di testo
        
        String matricola = tfMatricola.getText();
        String nuovoCognome = tfCognome.getText();
        String nuovoNome = tfNome.getText();
        String nuovaEmail = tfEmail.getText();
        
        if(matricola.isEmpty() || nuovoCognome.isEmpty() || nuovoNome.isEmpty() || nuovaEmail.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Dati mancanti");
            alert.setHeaderText(null);
            alert.setContentText("Attenzione: per effettuare la modifica devi compilare tutti i campi.");
            alert.showAndWait();
            return;
        }
        
        boolean modificato = elenco.modificaStudente(matricola, nuovoNome, nuovoCognome, nuovaEmail);
        
        if(modificato){
            aggiornaInterfaccia();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Studente modificato con successo.");
            alert.show();
            
        }else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Nessuno studente trovato con questa matricola.");
            error.showAndWait();
        
        
    }
    }

    @FXML
    private void searchStudent(ActionEvent event) {
    
    ricerca.setText("");
        
        //Recupero dei dati dalle caselle di testo
        String matricolaCercato = insMatricola.getText().toLowerCase();
        String cognomeCercato = insCognome.getText().toLowerCase();
        
        //Se tutti i campi sono vuoti si resetta la tabella e vengono mostrati tutti gli studenti
        if(matricolaCercato.isEmpty() && cognomeCercato.isEmpty()){
            
            aggiornaInterfaccia();
            return;
        }
            //Creazione di una lista osservabile temporanea per i risultati della ricerca
            ObservableList<Studente> risultatiRicerca = FXCollections.observableArrayList();
            
            
            //Scorriamo l'elenco originale
            
            for(Studente studente: elenco.getElencoStudenti()){
              
                
                boolean matricolaTrovato = matricolaCercato.isEmpty() || studente.getMatricola().toLowerCase().contains(matricolaCercato);
                boolean cognomeTrovato = cognomeCercato.isEmpty() || studente.getCognome().toLowerCase().contains(cognomeCercato);
                
                 if(matricolaTrovato && cognomeTrovato){
                risultatiRicerca.add(studente);
                
            }
            }
            
            //A questo punto mostriamo nella tabella i risultati trovati
            tableViewStudenti.setItems(risultatiRicerca);
           
            //Gestione MESSAGGIO
            if(risultatiRicerca.isEmpty()){
                
                ricerca.setText("Nessuno studente Trovato.");
                ricerca.setStyle("-fx-text-fill: red;");
            }
                        
            
        }
    
/*
    
    */

    @FXML
    private void deleteStudent(ActionEvent event) {
        
        //Recuperiamo lo studente selezionato nella tabella
        //il metodo getSelectionModel() gestisce i click sulle righe
        //il metodo getSelectedItem() restituisce l'oggetto studente corrispondente
        
        Studente studenteSelezionato = tableViewStudenti.getSelectionModel().getSelectedItem();
        
        //Controlla che l'utente abbia selezionato uno studente
        if(studenteSelezionato == null) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText(null);
            alert.setContentText("Per eliminare uno studente devi prima selezionarlo cliccandoci sopra");
            alert.showAndWait();
            return;
            
          }
        
        //Conferma per l'eliminazione di uno studente dalla lista
        
        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.setTitle("Conferma eliminazione");
        conferma.setHeaderText(null);
        conferma.setContentText("Sei sicuro di voler eliminare lo studente " + studenteSelezionato.getNome() + " " + studenteSelezionato.getCognome() + " ?");
        conferma.showAndWait();
        
         Optional<ButtonType> result = conferma.showAndWait();
         
         //Verifica se c'è un risultato e se quel risultato è il tasto OK
         if(result.isPresent() && result.get() == ButtonType.OK){
         
         //Rimozione tramite elenco e matricola
         boolean rimosso = elenco.eliminaStudente(studenteSelezionato.getMatricola());
         
         if(rimosso){
             
             aggiornaInterfaccia();
             
         }else{
             
             Alert error = new Alert(Alert.AlertType.ERROR);
             error.setContentText("Errore durantel'eliminazione.");
             error.show();
       
             
    }
         
       
        
    }
    }
    
 
    
    /*
    
    */

    @FXML
    private void exitS(ActionEvent event) {
        Node exit = (Node) event.getSource();
        
        Stage stageExit = (Stage) exit.getScene().getWindow();
        
        stageExit.close();
    }

    @FXML
    private void saveSFile(ActionEvent event) {
    }
    
    @FXML
    private void menuSReturn(ActionEvent event) {
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
            }}}