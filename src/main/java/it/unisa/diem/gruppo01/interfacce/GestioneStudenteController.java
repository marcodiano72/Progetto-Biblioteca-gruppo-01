/**
*@file GestioneLibriStudente.java
 *@brief Controller FXML per la schermata di gestione degli studenti.
 * Gestisce l'interfaccia utente per l'aggiunta, la modifica, la ricerca e l'eliminazione
 * degli studenti nel sistema bibliotecario, interagendo con l'oggetto  elenco
*@author gruppo01
*@version 1.0
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
 * @brief Controller FXML: GestioneStudenteController
 * Implementa le funzionalità per la gestione
 * degli studenti, garantendo che i dati siano correttamente visualizzati e modificati
 * tramite la TableView e l'oggetto elenco
 */
public class GestioneStudenteController implements Initializable {

    // CAMPI FXML (PULSANTI E INPUT)
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
    private Button saveSButton; ///< Pulsante per salvare l'elenco degli studenti su file (CSV).
    @FXML
    private Button menuSButton; ///< Pulsante per tornare al menu principale.
   
    // Campi di input per Aggiunta/Modifica
    @FXML
    private TextField tfCognome;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfMatricola;
    @FXML
    private TextField tfEmail;
    @FXML
    private Label ricerca;///<Etichetta per visualizzare messaggi di stato o di errore relativi alla ricerca.
    
    // Campi di input per Ricerca
    @FXML
    private TextField insMatricola;
    @FXML
    private TextField insCognome;
    
    // TableView e Colonne
    @FXML
    private TableView<Studente> tableViewStudenti; ///<Tabella per la visualizzazione dell'anagrafica degli studenti
    @FXML
    private TableColumn<Studente, String> colCognome; ///< Colonna per visualizzare il cognome dello studente.
    @FXML
    private TableColumn<Studente, String> colNome; ///< Colonna per visualizzare il nome dello studente
    @FXML
    private TableColumn<Studente, String> colMatr; ///<Colonna per visualizzare la matricola dello studente
    
    
   
    private Elenco elenco; ///< Istanza dell'Elenco, il modello dati che contiene tutti gli studenti.
    private ObservableList<Studente> listaStudenti = FXCollections.observableArrayList(); ///< Lista osservabile per sincronizzare i dati dell'Elenco con la TableView
   
    
    /* @brief Metodo chiamato per inizializzare un controller dopo che il suo elemento radice è stato completamente elaborato.
     * Inizializza l'oggetto Elenco.
     * Configura il binding delle colonne della tabella.
     * Carica i dati iniziali dal file CSV.
     * Imposta il gestore per il doppio clic per la visualizzazione dei dettagli.
     *
     * @param url L'ubicazione relativa o assoluta del file FXML.
     * @param rb Le risorse utilizzate per localizzare l'oggetto radice.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        elenco = new Elenco();
        // Configurazione del PropertyValueFactory (binding colonna -> attributo Studente)
        colCognome.setCellValueFactory(new PropertyValueFactory("cognome"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colMatr.setCellValueFactory(new PropertyValueFactory("matricola"));
        
        // CARICA I DATI DAL FILE CSV
        elenco.caricaDati();
    
        // AGGIORNA LA VIEW con i dati caricati
        listaStudenti.addAll(elenco.getElencoStudenti());
        tableViewStudenti.setItems(listaStudenti);
        
        
        
        //Gestore di eventi per il doppio click sulla riga della tabella
        
        tableViewStudenti.setOnMouseClicked(event ->{
            //Controlla se è un doppio click e se è una riga selezionata
            
            if(event.getClickCount() == 2 && tableViewStudenti.getSelectionModel().getSelectedItem() != null){
            Studente studenteSelezionato = tableViewStudenti.getSelectionModel().getSelectedItem();
            
            apriDettagliStudente(studenteSelezionato);
        }
        });
    }
    
    /*
    * @brief Metodo ch verifica che l'email inserita sia non vuota e termini con il dominio istituzionale "@studenti.unisa.it".
     * @param email La stringa email da validare.
     * @return true se l'email è valida, altrimenti false
    */
     private boolean isValidEmail(String email) {
    return email != null && !email.isEmpty() && email.endsWith("@studenti.unisa.it");
}
 
     
     /*
     * @brief Metodo che carica la vista dettagliata di uno studente selezionato in una nuova finestra .
     * Trasferisce i dati dello studente al controller  VisualizzaStudente_viewController.
     * @param s L'oggetto Studente i cui dettagli devono essere visualizzati.
     */
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
     * @brief Metodo che copia i dati da elenco(TreeSet) e li inserisce in listaStudenti (observableList).
     * Assicura che l'ordinamento per cognome definito in elenco venga rispettato.
     */
    
    private void aggiornaInterfaccia(){
        listaStudenti.clear();
        listaStudenti.addAll(elenco.getElencoStudenti());
    }
    
    
    /*
    * @brief Metodo che gestisce l'evento di clic sul pulsante Aggiungi Studente.
     * Funzionalità: Raccoglie i dati dai campi di testo, crea un nuovo
     * oggetto studente e lo aggiunge all' elenco.
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
            
           
            
            Alert alert = new Alert(Alert.AlertType.WARNING);   
            alert.setTitle("Dati mancanti");  //Titolo che compare nella parte alta della finestra
            alert.setHeaderText(null);
            alert.setContentText("Attenzione: devi compilare tutti i campi");
            alert.showAndWait(); //show() mostra la finestra e ...AndWait() significa che il codice si interrompe quando mostrata
            return;
    }
if (!isValidEmail(email)) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Email non valida");
    alert.setHeaderText(null);
    alert.setContentText("L'email deve terminare con '@studenti.unisa.it'. Inserisci un indirizzo valido.");
    alert.showAndWait();
    tfEmail.clear(); 
    tfEmail.requestFocus(); 
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
     * @brief Metodo che pulisce le caselle di testo
     */
    
    private void pulisciCampi(){
        tfCognome.clear();
        tfNome.clear();
        tfMatricola.clear();
        tfEmail.clear();
    }
    
    

    /*
    * @brief Metodo che gestisce l'evento di clic sul pulsante Modifica Studente (modSButton).
     * Aggiorna i dati dello studente selezionato, ignorando i campi lasciati vuoti.
     * L'aggiornamento viene eseguito sulla base della matricola dello studente selezionato.
     *
     * @param event L'evento di azione generato dal clic.
    */
    @FXML
    private void modStudent(ActionEvent event) {
    // 1. Studente selezionato dalla TableView
    Studente studenteSelezionato = (Studente) tableViewStudenti.getSelectionModel().getSelectedItem();
    if (studenteSelezionato == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Seleziona uno studente da modificare.");
        alert.showAndWait();
        return;
    }

   
    String matricolaDaModificare = studenteSelezionato.getMatricola();

    // Recupera i dati dai campi di input
    String nuovoCognome = tfCognome.getText();
    String nuovoNome    = tfNome.getText();
    String nuovaEmail   = tfEmail.getText();

    //Se un campo è vuoto, mantieni il valore esistente
    if (nuovoCognome == null || nuovoCognome.isEmpty()) {
        nuovoCognome = studenteSelezionato.getCognome();
    }
    if (nuovoNome == null || nuovoNome.isEmpty()) {
        nuovoNome = studenteSelezionato.getNome();
    }
    if (nuovaEmail == null || nuovaEmail.isEmpty()) {
        nuovaEmail = studenteSelezionato.getEmail();
    }

    //  Controllo formato email UNISA
    if (!isValidEmail(nuovaEmail)) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Email non valida");
        alert.setHeaderText(null);
        alert.setContentText("L'email deve terminare con '@studenti.unisa.it'.");
        alert.showAndWait();
        tfEmail.requestFocus();
        return;   // blocca la modifica
    }

    //Chiamata a Elenco
    boolean modificato = elenco.modificaStudente(
            matricolaDaModificare,
            nuovoNome,
            nuovoCognome,
            nuovaEmail
    );

    if (modificato) {
        aggiornaInterfaccia();
        tableViewStudenti.refresh();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Studente modificato con successo.");
        alert.showAndWait();
    } else {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText(null);
        error.setContentText("Nessuno studente trovato con questa matricola.");
        error.showAndWait();
    }

    pulisciCampi();
}

    
    /*
     * @brief Metodo che gestisce l'evento di clic sul pulsante Cerca Studente (searchSButton).
     * Filtra la lista degli studenti in base alla Matricola e/o al Cognome.
     *
     * @param event L'evento di azione generato dal clic.
    */
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
            
            ObservableList<Studente> risultatiRicerca = FXCollections.observableArrayList();
       
            
            for(Studente studente: elenco.getElencoStudenti()){
              
                
                boolean matricolaTrovato = matricolaCercato.isEmpty() || studente.getMatricola().toLowerCase().contains(matricolaCercato);
                boolean cognomeTrovato = cognomeCercato.isEmpty() || studente.getCognome().toLowerCase().contains(cognomeCercato);
                
                 if(matricolaTrovato && cognomeTrovato){
                risultatiRicerca.add(studente);
                
            }
            }
            
            //Mostra nella tabella i risultati trovati
            tableViewStudenti.setItems(risultatiRicerca);
           
            //Gestione MESSAGGIO -->nessuno studente trovato
            if(risultatiRicerca.isEmpty()){
                
                ricerca.setText("Nessuno studente Trovato.");
                ricerca.setStyle("-fx-text-fill: red;");
            }
                        
            
        }
    
    /*
     *@brief Metodo che gestisce l'eliminazione di uno studente selezionato dalla TableView (deleteSButton).
     * Richiede una conferma esplicita all'utente prima di rimuovere lo studente dall'elenco.
     * @param event L'evento di azione generato dal clic.
     */
    @FXML
    private void deleteStudent(ActionEvent event) {
        
        //Recuperiamo lo studente selezionato nella tabella
        //il metodo getSelectionModel() gestisce i click sulle righe
        //il metodo getSelectedItem() restituisce l'oggetto studente corrispondente
        
        Studente studenteSelezionato = tableViewStudenti.getSelectionModel().getSelectedItem();
        
        //Controlla che il gestore abbia selezionato uno studente
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
     * @brief Metodo che gestisce l'evento di clic sul pulsante Esci (exitSButton).
     * Chiude la finestra corrente.
     *
     * @param event L'evento di azione generato dal clic.
    
    */

    @FXML
    private void exitS(ActionEvent event) {
        Node exit = (Node) event.getSource();
        
        Stage stageExit = (Stage) exit.getScene().getWindow();
        
        stageExit.close();
    }

    
    
    /*
     * @brief Metodo che gestisce l'evento di clic sul pulsante Salva (saveSButton).
     * Invocando elenco.salvaCSV(), persiste lo stato attuale dell'Elenco
     * su un file in formato CSV.
     *
     * @param event L'evento di azione generato dal clic.
     * @throws IOException Se si verifica un errore durante la scrittura del file.
    */
    @FXML
    private void saveSFile(ActionEvent event) throws IOException {
        elenco.salvaCSV();
        System.out.println("\nLista libri salvata su file Elenco_studenti.csv\n");

    }
    
    
    /*
     * @brief Metodo che gestisce l'evento di clic sul pulsante *enu (menuSButton).
     * Carica la vista del Menu Principale e mostra la scena.
     *
     * @param event L'evento di azione generato dal clic.
    */
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