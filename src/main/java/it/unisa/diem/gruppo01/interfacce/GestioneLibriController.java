/**
*@file GestioneLibriController.java
*@brief file per gestire le interazioni utente al fine di aggiungere, modificare, cercare, eliminare e salvare
*
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
    private Label lblMessaggioricerca;
   

    // Dichiariamo un'istanza del Catalogo.
    private Catalogo catalogo ;
    
    // Lista che punterà ai dati del Catalogo per la TableView
    private ObservableList<Libro> datiTabella;
  
    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
        // Carica la TableView solo DOPO aver ricevuto l'istanza corretta
        this.datiTabella = FXCollections.observableArrayList(catalogo.getInventarioLibri()); 
        tableViewLibri.setItems(datiTabella); ///< Collegamento Lista -> Tabella (usa la lista del Controller)
        
        System.out.println("Catalogo iniettato in GestioneLibriController. Dati caricati nella tabella.");
    }
   
    /*
    * Metodo chiamato per inizializzare un controller dopo che il suo elemento radice è stato completamente elaborato.
     * Viene utilizzato per setup iniziali, come l'impostazione dei listener o il caricamento dei dati di default.
     *
     * @param url L'ubicazione relativa o assoluta del file FXML.
     * @param rb Le risorse utilizzate per localizzare l'oggetto radice.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Collegamento Colonne -> attributi classe libro
        //(new PropertyValueFactory(")) questo metodo per ogni libro passato cerca l'attributo che si specifica 
        //all'interno delle parentesi, o meglio cerca il metodo getAttributo(), prende qel valore e lo inserisce nella casella.
        colonnaTitolo.setCellValueFactory(new PropertyValueFactory("titolo"));
        colonnaAutore.setCellValueFactory(new PropertyValueFactory("autore"));
        colonnaIsbn.setCellValueFactory(new PropertyValueFactory("isbn"));
        
                //Aggiungiamo un gestore di eventi del mouse alla tabella
        
        tableViewLibri.setOnMouseClicked(event ->{
            //Controlla se è un doppio click (lick count == 2) e se è una riga selezionata
            
            if(event.getClickCount() == 2 && tableViewLibri.getSelectionModel().getSelectedItem() != null){
            Libro libroSelezionato = tableViewLibri.getSelectionModel().getSelectedItem();
            
            apriDettagliLibro(libroSelezionato);
        }
        });
    }
    
    
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
    * Gestisce l'evento di clic sul pulsante Aggiungi Libro.
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
            //bisogna convertire anno, perchè Libro richiede una data LocalDate, ma noi inseriamo una Stringa nel textfield
            int anno = Integer.parseInt(annoP); //converte in Int
            LocalDate annoPubblicazione = LocalDate.of(anno,1,1); //converte nel formato 01/01/anno;
            
            Libro nuovoLibro = new Libro(isbn,titolo,autore,annoPubblicazione,1);
            
            boolean aggiunto = catalogo.aggiungiLibro(nuovoLibro);
            
            //se il catalogo ha modificato l'inventario, aggiorno la TableView
            //ricarico i dati
            datiTabella.clear();
            datiTabella.addAll(catalogo.getInventarioLibri());
            
            
            //Messaggio di conferma di libro aggiunto
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if(aggiunto == true)
            {
               alert.setContentText("Libro aggiunto al catalogo con successo");
               
            }else
            {
                alert.setContentText("Libro già esistente. Numero copie incrementato");
            }
            alert.showAndWait();
            pulisciCampi();
            
           
        }catch(NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("L'anno deve essere un numero valido");
            alert.showAndWait();
            
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
        tfTitolo.clear();
        tfAutore.clear();
        tfIsbn.clear();
        tfAnno.clear();
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
    // 1. Libro selezionato dalla TableView
    Libro libroSelezionato = tableViewLibri.getSelectionModel().getSelectedItem();

    if (libroSelezionato == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Seleziona un libro da modificare.");
        alert.showAndWait();
        return;
    }

    // ISBN chiave (non lo modifichi nel catalogo)
    String isbnDaModificare = libroSelezionato.getIsbn();

    // 2. Recupera i dati dai campi di input
    String nuovoTitolo = tfTitolo.getText();
    String nuovoAutore = tfAutore.getText();
    String nuovoAnnoPb = tfAnno.getText();

    // 3. Se un campo è vuoto o solo spazi, mantieni il valore esistente
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

    // 4. Chiamata al catalogo
    boolean successo = catalogo.modificaLibro(
            isbnDaModificare,
            nuovoTitolo,
            nuovoAutore,
            nuovoAnnoPubblicazione,
            copieEsistenti
    );

    if (successo) {
        // 5. Aggiorna la TableView
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
            
            
            //Scorriamo la lista originale (listaLibri) per trovare il libri desiderato.
            
            for(Libro libro: datiTabella){
                //Controlliamo se il campo di ricerca è vuoto oppure se il libro ha quel Titolo,Autore e Isbn
                
                boolean titoloTrovato = titoloCercato.isEmpty() || libro.getTitolo().toLowerCase().contains(titoloCercato);
                boolean autoreTrovato = autoreCercato.isEmpty() || libro.getAutore().toLowerCase().contains(autoreCercato);
                boolean isbnTrovato = isbnCercato.isEmpty() || libro.getIsbn().toLowerCase().contains(isbnCercato);
                
                 if(titoloTrovato && autoreTrovato && isbnTrovato){
                risultatiRicerca.add(libro);
                
            }
            }
            
            //A questo punto mostriamo nella tabella i risultati trovati
            tableViewLibri.setItems(risultatiRicerca);
           
            //Gestione MESSAGGIO
            if(risultatiRicerca.isEmpty()){
                
                lblMessaggioricerca.setText("Nessun libro Trovato.");
                lblMessaggioricerca.setStyle("-fx-text-fill: red;");
            }
                        
            
        }
    

    
    /*
    
    */

    @FXML
    private void deleteLibri(ActionEvent event) {
        
        //Recuperiamo il libro selezionato nella tabella
        //il metodo getSelectionModel() gestisce i click sulle righe
        //il metodo getSelectedItem() restituisce l'oggetto libro corrispondente
        
        Libro libroSelezionato = tableViewLibri.getSelectionModel().getSelectedItem();
        
        
        //Controlla che l'utente abbia selezionato un libro
        if(libroSelezionato == null) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nessuna selezione");
            alert.setHeaderText(null);
            alert.setContentText("Per eliminare un libro devi prima selezionarlo cliccandoci sopra");
            alert.showAndWait();
            return;
            
          }
        
        //Conferma per l'eliminazione di un libro dalla lista
        
        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.setTitle("Conferma eliminazione");
        conferma.setHeaderText(null);
        conferma.setContentText("Sei sicuro di voler eliminare il libro " + libroSelezionato.getTitolo() + " ?");
        conferma.showAndWait();
        
         Optional<ButtonType> result = conferma.showAndWait();
         
         //Verifica se c'è un risultato e se quel risultato è il tasto OK
         if(result.isPresent() && result.get() == ButtonType.OK);
         {
            // 1. Chiama il Model Manager per eseguire l'azione
             boolean rimosso = catalogo.eliminaLibro(libroSelezionato.getIsbn());
             
             if (rimosso) {
                 // 2. Aggiorna la TableView per riflettere lo stato corrente del Catalogo
                 datiTabella.clear();
                 datiTabella.addAll(catalogo.getInventarioLibri());
                 
                 Alert successo = new Alert(Alert.AlertType.INFORMATION);
                 successo.setHeaderText(null);
                 successo.setContentText("Libro eliminato con successo.");
                 successo.showAndWait();
             } else {
                 // Questo caso non dovrebbe succedere se il libro è stato selezionato dalla tabella
                 Alert errore = new Alert(Alert.AlertType.ERROR);
                 errore.setHeaderText(null);
                 errore.setContentText("Errore durante l'eliminazione: libro non trovato nel catalogo.");
                 errore.showAndWait();
             }
        }
         
        
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
    private void saveLFile(ActionEvent event) throws IOException {
        //Salvataggio con CSV: salva manualmente in file csv
        catalogo.salvaCSV();  // creo un file e salvo i dati 
        System.out.println("\nLista libri salvata su file Lista_libri.csv\n");
    }
    
    
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