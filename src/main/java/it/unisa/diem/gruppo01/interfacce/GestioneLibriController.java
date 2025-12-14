/**
*@file GestioneLibriController.java
*@brief Controller FXML per la schermata di gestione dei libri nel sistema bibliotecario.
*Gestisce le interazioni utente per aggiungere, modificare, cercare, eliminare e salvare
*i dati dell'inventario.
*@author Gruppo01
*@version 1.0
*/

package it.unisa.diem.gruppo01.interfacce;

import it.unisa.diem.gruppo01.classi.Catalogo;
import it.unisa.diem.gruppo01.classi.Libro;
import it.unisa.diem.gruppo01.classi.Studente;

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
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @brief Controller FXML: GestioneLibriController
 * Controllore FXML per la schermata di gestione dei libri nel sistema bibliotecario.
 * Gestisce le interazioni utente per aggiungere, modificare, cercare, eliminare e salvare
 * l'inventario dei libri.
 *
 */
public class GestioneLibriController implements Initializable {

    // CAMPI FXML
    @FXML
    private TableView<Libro> tableViewLibri; ///<Tabella per la visualizzazione dell'inventario dei libri.
    @FXML
    private TableColumn<Libro, String> colonnaTitolo; ///< Colonna per visualizzare il titolo del libro
    @FXML
    private TableColumn<Libro, String> colonnaAutore;///<Colonna per visualizzare l'autore del libro.
    @FXML
    private TableColumn<Libro, String> colonnaIsbn;///<Colonna per visualizzare l'ISBN del libro.
   
    // Campi di input per Aggiunta/Modifica
    @FXML
    private TextField tfTitolo;
    @FXML
    private TextField tfAutore;
    @FXML
    private TextField tfIsbn;
    @FXML
    private TextField tfAnno;
    
    // Pulsanti di Azione
    @FXML
    private Button addLButton;
    @FXML
    private ImageView addButton;
    @FXML
    private Button modLButton;
    
    // Campi di input per Ricerca
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
    private Button saveLButton; ///< Pulsante per salvare l'inventario su file (CSV).
    @FXML
    private Button menuButton; ///< Pulsante per tornare al menu principale.
   @FXML
    private Label lblMessaggioricerca; ///< Etichetta per visualizzare messaggi di stato o di errore relativi alla ricerca.
   

    
    private Catalogo catalogo ; ///< Istanza del Catalogo, il modello dati che contiene tutti i libri.
    
    private ObservableList<Libro> datiTabella; ///< Lista che punterà ai dati del Catalogo per la TableView
  
    
    /*
     * @brief Metodo per inserire l'istanza del Catalogo nel controller.
     * È cruciale per l'interazione con il modello di dati. Inizializza anche l'ObservableList
     * e la collega alla TableView.
     * @param catalogo L'istanza del Catalogo da utilizzare
    */
    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
        // Carica la TableView solo DOPO aver ricevuto l'istanza corretta
        this.datiTabella = FXCollections.observableArrayList(catalogo.getInventarioLibri()); 
        tableViewLibri.setItems(datiTabella); ///< Collegamento Lista -> Tabella (usa la lista del Controller)
        
        System.out.println("Catalogo inserito in GestioneLibriController. Dati caricati nella tabella.");
    }
   
    
    /*
    * @brief Metodo che aggiorna l' ObservableList  e di conseguenza la TableView
    * con i dati  presenti nel Catalogo.
    */
    private void aggiornaInterfaccia(){
        datiTabella.clear();
        datiTabella.addAll(catalogo.getInventarioLibri());
    }
    
    
    /*
    * @brief Metodo chiamato per inizializzare un controller dopo che il suo elemento radice è stato completamente elaborato.
     * Configura il binding delle colonne della tabella con gli attributi dell'oggetto Libro
     * e imposta il gestore eventi per il doppio clic sulla tabella.
     * @param url L'ubicazione relativa o assoluta del file FXML.
     * @param rb Le risorse utilizzate per localizzare l'oggetto radice.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       // Configurazione del PropertyValueFactory (binding colonna -> attributo Libro)
        colonnaTitolo.setCellValueFactory(new PropertyValueFactory("titolo"));
        colonnaAutore.setCellValueFactory(new PropertyValueFactory("autore"));
        colonnaIsbn.setCellValueFactory(new PropertyValueFactory("isbn"));
         
        
        tableViewLibri.setOnMouseClicked(event ->{
            //Controlla se è un doppio click (lick count == 2) e se è una riga selezionata
            
            if(event.getClickCount() == 2 && tableViewLibri.getSelectionModel().getSelectedItem() != null){
            Libro libroSelezionato = tableViewLibri.getSelectionModel().getSelectedItem();
            
            apriDettagliLibro(libroSelezionato);
        }
        });
    }
    
    
    /*
    * @brief Metodo che carica la vista dettagliata di un libro selezionato in una nuova finestra.
    * Trasferisce i dati del libro selezionato al controller VisualizzaLibro_viewController.
    *
    */
    private void apriDettagliLibro(Libro l){
        
        try{
         
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/VisualizzaLibro_view.fxml"));
            Parent visualizzaL = loader.load();
            
            //Ottieni il controller della nuova schermata
            VisualizzaLibro_viewController controller = loader.getController();
            
            //Passa i dati dello studente al controller
            
            controller.setDatiLibro(l);
            
            
            Stage visualizzaSscene = new Stage();
            visualizzaSscene.setTitle("Dettagli Libro");
            visualizzaSscene.setScene(new Scene(visualizzaL));
            
            visualizzaSscene.show();
           
           
           
           
        }catch(IOException e){
            System.out.println("Errore nel caricamento della vista dettagli.");
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Impossibile aprire la schermata libro.");
            alert.show();
        }
    }
     

    /*
    * @brief Metodo che gestisce l'evento di clic sul pulsante Aggiungi Libro.
     * Funzionalità: Raccoglie i dati dai campi di testo, crea un nuovo
     * oggetto Libro e lo aggiunge al Catalogo.
     * @param event L'evento di azione generato dal clic.
     */
    @FXML
    private void addLibri(ActionEvent event) {
  
        //Recupero dei dati dalle caselle di testo
        String titolo = tfTitolo.getText();
        String autore = tfAutore.getText();
        String isbn = tfIsbn.getText();
        String annoP = tfAnno.getText();
        
        //Controllo campi vuoti (in caso di mancata compilazione mostra avviso)
        if(titolo.isEmpty() || autore.isEmpty() || isbn.isEmpty() || annoP.isEmpty()){
            
            
            
            Alert alert = new Alert(Alert.AlertType.WARNING);   ///< AlertType.Warning mostra un'icona triangolo gialla (Avviso)
            alert.setTitle("Dati mancanti");  ///< Titolo che compare nella parte alta della finestra
            alert.setHeaderText(null); 
            alert.setContentText("Attenzione: devi compilare tutti i campi"); 
            alert.showAndWait(); ///< show() mostra la finestra e ...AndWait() significa che il codice si interrompe una volta mostrata
            return;
        }
        
    try {
    int anno = Integer.parseInt(annoP);
    LocalDate annoPubblicazione = LocalDate.of(anno, 1, 1);

    // Controllo per libro identico (incrementare copie anziché aggiungere un nuovo elemento)
    boolean libroIdenticoTrovato = false;
    for (Libro libroEsistente : catalogo.getInventarioLibri()) {
        if (libroEsistente.getTitolo().equalsIgnoreCase(titolo) &&
            libroEsistente.getAutore().equalsIgnoreCase(autore) &&
            libroEsistente.getIsbn().equals(isbn) &&
            libroEsistente.getAnnoPb().getYear() == anno) {

            catalogo.incrementaCopie(libroEsistente.getIsbn());
            libroIdenticoTrovato = true;
            break;
        }
    }

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText(null);

    if (!libroIdenticoTrovato) {
        // aggiunta di un nuovo elemento
        Libro nuovoLibro = new Libro(isbn, titolo, autore, annoPubblicazione, 1);
        catalogo.aggiungiLibro(nuovoLibro);
        alert.setContentText("Libro aggiunto al catalogo con successo");
    } else {
        alert.setContentText("Libro già esistente. Numero copie incrementato");
    }

    alert.showAndWait();

    // aggiorna tabella e pulisce i campi
    datiTabella.clear();
    datiTabella.addAll(catalogo.getInventarioLibri());

    pulisciCampi();

} catch (NumberFormatException ex) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText("L'anno deve essere un numero valido");
    alert.showAndWait();
} catch (IllegalArgumentException ex) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText("Errore nei dati inseriti");
    alert.showAndWait();
}
        
        
    }
    /**
     * Pulisce le caselle di testo
     */
    
    private void pulisciCampi(){
        tfTitolo.clear();
        tfAutore.clear();
        tfIsbn.clear();
        tfAnno.clear();
    }

    
     /*
     * @brief Metodo che gestisce l'evento di clic sul pulsante Modifica Libro.
     *  Funzionalità: Cerca un libro tramite un identificatore e aggiorna
     * i suoi dettagli (es. titolo, autore o numero di copie) con i valori presenti nei campi di testo.
     * Richiede che un libro sia selezionato.
     * Non consente la modifica dell'ISBN.
     * Se un campo è lasciato vuoto, mantiene il valore esistente del libro.
     * @param event L'evento di azione generato dal clic.
      */
  @FXML
private void modLibri(ActionEvent event) {
    // 1. Libro selezionato dalla TableView
    Libro libroSelezionato = tableViewLibri.getSelectionModel().getSelectedItem();

    if (libroSelezionato == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Seleziona un libro da modificare.");
        alert.showAndWait();
        return;
    }

    // L'ISBN è la chiave nel Catalogo e non può essere modificato
    String isbnDaModificare = libroSelezionato.getIsbn();

    // Recupera i dati dai campi di input
    String nuovoTitolo = tfTitolo.getText();
    String nuovoAutore = tfAutore.getText();

// Controllo esplicito per l'ISBN (non deve cambiare)
String nuovoIsbn = tfIsbn.getText().trim();
if (!nuovoIsbn.isEmpty() && !nuovoIsbn.equals(libroSelezionato.getIsbn())) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Modifica non consentita");
    alert.setHeaderText(null);
    alert.setContentText("Attenzione. Non è possibile modificare l'isbn del libro selezionato.");
    alert.showAndWait();
    return;  // Blocca la modifica
}
    String nuovoAnnoPb = tfAnno.getText();

    //Se un campo è vuoto o solo spazi, mantieni il valore esistente
    if (nuovoTitolo == null || nuovoTitolo.isEmpty()) {
        nuovoTitolo = libroSelezionato.getTitolo();
    }

    if (nuovoAutore == null || nuovoAutore.isEmpty()) {
        nuovoAutore = libroSelezionato.getAutore();
    }

    int anno;
    try {
        if (nuovoAnnoPb == null || nuovoAnnoPb.isEmpty()) {
            // mantieni l'anno già presente
            anno = libroSelezionato.getAnnoPb().getYear();
        } else {
            // converte solo se l'utente ha inserito qualcosa
            anno = Integer.parseInt(nuovoAnnoPb);
        }
    } catch (NumberFormatException ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("L'Anno di pubblicazione deve essere un numero intero valido (es. 2024).");
        alert.showAndWait();
        return;
    }

    LocalDate nuovoAnnoPubblicazione = LocalDate.of(anno, 1, 1);

    // numero copie invariato
    int copieEsistenti = libroSelezionato.getNumCopie();

    // Chiamata al catalogo per modificare i dati
    boolean successo = catalogo.modificaLibro(
            isbnDaModificare,
            nuovoTitolo,
            nuovoAutore,
            nuovoAnnoPubblicazione,
            copieEsistenti
    );

    if (successo) {
        //Aggiorna la TableView
        datiTabella.clear();
        datiTabella.addAll(catalogo.getInventarioLibri());
        tableViewLibri.refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Libro modificato con successo nel catalogo.");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Errore: Libro con ISBN " + isbnDaModificare + " non trovato per la modifica.");
        alert.showAndWait();
    }

    pulisciCampi();
}
    
    /*
    * @brief Metodo che gestisce l'evento di clic sul pulsante Cerca Libro (searchLButton).
    * Filtra l'inventario dei libri visualizzati nella TableView in base
    * ai criteri inseriti nei campi di ricerca (Titolo, Autore, ISBN).
    * @param event L'evento di azione generato dal clic.
    
    */

    @FXML
    private void searchLibri(ActionEvent event) {
        
        lblMessaggioricerca.setText("");
        
        //Recupero dei dati dalle caselle di testo
        String titoloCercato = tfTitoloRicerca.getText().toLowerCase();
        String autoreCercato = tfAutoreRicerca.getText().toLowerCase();
        String isbnCercato = tfIsbnRicerca.getText().toLowerCase();
        
        //Se tutti i campi sono vuoti si resetta la tabella e vengono mostrati tutti i libri
        if(titoloCercato.isEmpty() && autoreCercato.isEmpty() && isbnCercato.isEmpty()){
            
            tableViewLibri.setItems(datiTabella);
            return;
        }
            //Creazione di una lista osservabile temporanea per i risultati della ricerca
            ObservableList<Libro> risultatiRicerca = FXCollections.observableArrayList();
            
            
            //Scorro la lista originale (listaLibri) per trovare il libri desiderato.
            
            for(Libro libro: datiTabella){
                //Controll0 se il campo di ricerca è vuoto oppure se il libro ha quel Titolo,Autore e Isbn
                
                boolean titoloTrovato = titoloCercato.isEmpty() || libro.getTitolo().toLowerCase().contains(titoloCercato);
                boolean autoreTrovato = autoreCercato.isEmpty() || libro.getAutore().toLowerCase().contains(autoreCercato);
                boolean isbnTrovato = isbnCercato.isEmpty() || libro.getIsbn().toLowerCase().contains(isbnCercato);
                
                 if(titoloTrovato && autoreTrovato && isbnTrovato){
                risultatiRicerca.add(libro);
                
            }
            }
            
            //Aggiorna la TableView con i risultati
            tableViewLibri.setItems(risultatiRicerca);
           
            //Gestione MESSAGGIO
            if(risultatiRicerca.isEmpty()){
                
                lblMessaggioricerca.setText("Nessun libro Trovato.");
                lblMessaggioricerca.setStyle("-fx-text-fill: red;");
            }
                        
            
        }
    

    
    /*
    
    */

   
   /**
     * @brief Metodo che gestisce l'eliminazione di un libro selezionato dalla TableView.
     * Chiede conferma all'utente prima di procedere.
     * @param event L'evento di azione (es. click sul pulsante Elimina).
     */
    @FXML
    private void deleteLibri(ActionEvent event) {
        
       
        // TEST INIZIALIZZAZIONE CATALOGO: Se appare l'alert, il Catalogo non è stato caricato.
        if (catalogo == null) {
             Alert alertDebug = new Alert(Alert.AlertType.ERROR);
             alertDebug.setTitle("Errore Interno");
             alertDebug.setHeaderText("Catalogo non inizializzato");
             alertDebug.setContentText("Impossibile procedere: l'oggetto Catalogo è NULL. Verifica la chiamata a setCatalogo().");
             alertDebug.showAndWait();
             return;
        }
        
        // 1. Recupera il libro selezionato nella tabella
        Libro libroSelezionato = tableViewLibri.getSelectionModel().getSelectedItem();
        
        // 2. Controlla che l'utente abbia selezionato un libro
        if(libroSelezionato == null) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText(null);
            alert.setContentText("Per eliminare un libro devi prima selezionarlo cliccandoci sopra.");
            alert.showAndWait();
            return;
            
        }
        
            Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
            conferma.setTitle("Conferma eliminazione");
            conferma.setHeaderText(null);
            conferma.setContentText("Sei sicuro di voler eliminare il libro " + libroSelezionato.getTitolo() + " ?");
            conferma.showAndWait();
        
         Optional<ButtonType> result = conferma.showAndWait();

        
        //  Esegue l'eliminazione solo se l'utente ha premuto OK
        if (result.isPresent() && result.get() == ButtonType.OK) 
        {
            // Esegue l'azione sul modello (Catalogo)
            boolean rimosso = catalogo.eliminaLibro(libroSelezionato.getIsbn());
            
            if (rimosso) {
                // Aggiorna l'interfaccia rimuovendo il libro dall'ObservableList
               aggiornaInterfaccia();
            } else {
                // Logica di fallback se il libro era in tabella ma non nel catalogo (improbabile)
                Alert errore = new Alert(Alert.AlertType.ERROR);
                errore.setHeaderText("Errore Eliminazione");
                errore.setContentText("Il libro non è stato trovato nel catalogo per l'eliminazione.");
                errore.showAndWait();
            }
        }
        
    }
    
 
    
    /*
    * @brief Metodo che gestisce l'evento di clic sul pulsante Esci (exitLButton).
     * Chiude la finestra corrente.
     * @param event L'evento di azione generato dal clic.
    
    */
    @FXML
    private void exitL(ActionEvent event) {
        
        Node exit = (Node) event.getSource();
        Stage stageExit = (Stage) exit.getScene().getWindow();
        stageExit.close();
    }
    
    
    /*
    * @brief Metodo ch gestisce l'evento di clic sul pulsante Salva (saveLButton).
     * Invocando catalogo.salvaCSV(), persiste lo stato attuale del Catalogo
     * su un file in formato CSV.
     *
     * @param event L'evento di azione generato dal clic.
     * @throws IOException Se si verifica un errore durante la scrittura del file.
     */
    @FXML
    private void saveLFile(ActionEvent event) throws IOException {
        //Salvataggio con CSV: salva manualmente in file csv
        catalogo.salvaCSV();  // creo un file e salvo i dati 
        System.out.println("\nLista libri salvata su file Lista_libri.csv\n");
    }
    
    
    /*
     * @brief Metodo che gestisce l'evento di clic sul pulsante Menu' (menuButton).
     * Carica la vista del Menu Principale e trasferisce l'istanza del Catalogo
     * al nuovo controller (Menu_BibliotecaController) prima di mostrare la scena.
     *
     * @param event L'evento di azione generato dal clic.
    */
    @FXML
    private void menuReturn(ActionEvent event) {
          
          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Menu_Biblioteca.fxml"));
            Parent menuParent = loader.load();
            
            // Passa il catalogo al controller di destinazione (Menu_BibliotecaController)
            Menu_BibliotecaController menuController = loader.getController();
            menuController.setCatalogo(this.catalogo);
            
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