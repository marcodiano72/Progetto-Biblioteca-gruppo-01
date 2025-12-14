/**
*@file Interfaccia_nuovoPrestitoController.java
 *@brief Controller per la gestione dei prestiti e delle restituzioni.
 * Gestisce l'interfaccia utente per registrare un nuovo prestito (dopo aver
 * verificato l'abilitazione dello studente e la disponibilità del libro)
 * e per registrare una restituzione (applicando eventuali sanzioni).
 *@author Gruppo01
 *@version 1.0
*/
package it.unisa.diem.gruppo01.interfacce;

import it.unisa.diem.gruppo01.classi.Catalogo;
import it.unisa.diem.gruppo01.classi.Elenco;
import it.unisa.diem.gruppo01.classi.Libro;
import it.unisa.diem.gruppo01.classi.Prestito;
import it.unisa.diem.gruppo01.classi.Studente;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @brief CONTROLLER FXML:Interfaccia_nuovoPrestitoController
 * Controllore FXML per l'interfaccia di gestione Prestiti.
 * Questa classe è responsabile di:
 * Ricevere le istanze dei modelli Elenco e Catalogo.
 * Gestire la ricerca dello studente per matricola e verificarne l'abilitazione.
 * Gestire la ricerca del libro per titolo/autore e verificarne la disponibilità.
 * Registrare un nuovo prestito, aggiornando lo stato del libro e dello studente.
 * Registrare una restituzione, aggiornando lo stato del libro e applicando sanzioni.
 */
public class Interfaccia_nuovoPrestitoController implements Initializable {

    @FXML
    private TextField inserisciM;   ///< Campo input matricola per nuovo prestito.
    @FXML
    private TextField inserisciN;   ///< Campo visualizzazione Nome studente.
    @FXML
    private TextField inserisciC;   ///< Campo visualizzazione Cognome studente.
    @FXML
    private TextField inserisciE;   ///< Campo visualizzazione Email studente.
    @FXML 
    private CheckBox spuntaU;    ///< CheckBox (disabilitata) che indica se lo studente è abilitato
    @FXML
    private TextField inserisciT;  ///< Campo input titolo Libro
    @FXML
    private TextField inserisciA;  ///< Campo input autore Libro
    @FXML
    private CheckBox spuntaL;    ///< CheckBox (disabilitata) che indica se il libro è disponibile.
    @FXML
    private Button salvaP;   ///< Pulsante per confermare il nuovo prestito.
    @FXML
    private TextField inserisciMa;   ///< Campo input matricola per restituzioni.
    @FXML 
    private TextField inserisciISBN;  ///< Campo input ISBN per restituzioni.
    @FXML
    private Button salvaRes;    ///< Pulsante per confermare la restituzione.
    @FXML
    private Button exitApp;     ///< Pulsante per chiudere l'applicazione.
    @FXML
    private Button menuPButton;   ///< Pulsante per tornare al menù principale.
    @FXML
    private ComboBox<String> statoLibroBox;   ///< Menù a tendina per indicare lo stato del libro restituito.
    
    
    
    private Elenco elencoStudenti;   ///< Riferimento all'elenco studenti.
    private Catalogo catalogoLibri;  ///< Riferimento al catalogo libri.
    
    
    //variabile per tenere traccia dello studente corrente
    private Studente studenteCorrente;   ///< Lo studente attualmente identificato per un nuovo prestito.
    
    //variabile per memorizzare il liro trovato
    private Libro libroCorrente = null;   ///< Il libro attualmente identificato per un nuovo prestito.
 
    
    
    /**
     * @brief Imposta le dipendenze del controller.
     * Permette il passaggio delle istanze condivise di Elenco e di Catalogo dal Menù Principale.
     * @param[in] elencoStudenti L'istanza dell'elenco degli studenti.
     * @param[in] catalogoLibri L'istanza del catalogo dei libri. 
     */
    
    public void setDati(Elenco elencoStudenti, Catalogo catalogoLibri){
        
        this.elencoStudenti = elencoStudenti;
        this.catalogoLibri = catalogoLibri;
    }
    
    
   /**
    * @brief Inizializza il controller e lo stato della GUI.
    * Configura i listener per i campi di testo e inizializza la ComboBox per lo stato del libro.
    * @param[in] url La posizione usata come base per i percorsi relativi dell’oggetto radice
    * @param[in] rb Le risorse utilizzate per localizzare l'oggetto radice.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //inizializziamo lo stato della checkbox
        spuntaU.setDisable(true);
        spuntaU.setSelected(false);
        gestisciCampiLibro(false);
        
        //Aggiungiamo opzioni alla comboBox
        if(statoLibroBox != null){
            statoLibroBox.getItems().addAll("Ottimo", "Buono", "Danneggiato");
            statoLibroBox.setValue("Ottimo");
        }
        
        
        inserisciT.setOnAction(this::ricercaAutomaticaLibro);
        inserisciA.setOnAction(this::ricercaAutomaticaLibro);
        
        
        inserisciT.textProperty().addListener((obs, oldV, newV) -> resetLibro());
        inserisciA.textProperty().addListener((obs, oldV, newV) -> resetLibro());
        
       
    }   
    
    /**
     * @brief Resetta lo stato di selezione del libro.
     * Viene chiamato quando l'utente modifica i campi di ricerca libro.
     */
    
    private void resetLibro(){
        spuntaL.setSelected(false);
        libroCorrente = null;
        salvaP.setDisable(true);
    }

    /**
     * @brief Gestisce la ricerca dello studente tramite matricola.
     * Verifica se lo studente esiste e controlla i requisiti per il prestito (abilitazione e ritardi).
     * Se idoneo, sblocca i campi per la selezione del libro.      
     * @param[in] event L'evento generato dall'interazione utente.
     */
    
   @FXML
    private void inserisciMatricola(ActionEvent event) {
        String matricola = inserisciM.getText();
        
        // Controlla se l'elenco è stato caricato
        if(elencoStudenti == null) return;
        
        //Cerca lo studente nell'elenco
        studenteCorrente = elencoStudenti.cercaStudenteperMatricola(matricola);
        
        if(studenteCorrente != null){
            // 2. Se trovato, riempio i campi anagrafici
            inserisciN.setText(studenteCorrente.getNome());
            inserisciC.setText(studenteCorrente.getCognome());
            inserisciE.setText(studenteCorrente.getEmail());
            
            
            // Controlla sùbito se lo studente può prendere libri
            if(studenteCorrente.isAbilitato()){
                
                // CASO POSITIVO:
                // Metto la spunta automaticamente (visivo)
                spuntaU.setSelected(true); 
                
                // Disabilito la checkbox così l'utente non può toglierla per sbaglio
                // Diventa un indicatore di stato "verde"
                spuntaU.setDisable(true); 
                
                // Sblocco automaticamente la parte sotto per inserire il libro
                gestisciCampiLibro(true);
                
            } else {
                
                // CASO NEGATIVO (Non abilitato):
                spuntaU.setSelected(false);
                spuntaU.setDisable(true); // Resta bloccata e vuota
                gestisciCampiLibro(false); // Campi libro restano bloccati
                
                // Mostro subito l'errore specifico all'utente
                if(studenteCorrente.isRitardo()){
                    mostraAlert(AlertType.ERROR, "Studente Bloccato", "L'utente non è abilitato perchè sanzionato");
                } else {
                    mostraAlert(AlertType.ERROR, "Studente Bloccato", "Limite prestiti raggiunto (" + studenteCorrente.contaPrestitiAttivi() + "/3).");
                }
            }
            
        } else {
            //Studente non trovato
            studenteCorrente = null;
            pulisciCampiAnagrafici();
            mostraAlert(AlertType.WARNING, "Attenzione", "Nessuno studente trovato con questa matricola.");
        }
    }
    
  /**
   * @brief Ricerca automaticamente un libro nel catalogo in base a Titolo e Autore.
   * Controlla se il libro esiste e se ci sono copie disponibili.
   * @param[in] event L'evento generato dai campi di testo Titolo e Autore.
   */
    
   @FXML
    private void ricercaAutomaticaLibro(ActionEvent event) {
        // 1. Prendo i testi e rimuovo spazi inutili
        String titolo = inserisciT.getText().trim();
        String autore = inserisciA.getText().trim();
        
        // Se i campi sono vuoti, esco subito
        if(titolo.isEmpty() || autore.isEmpty()){
            // Non mostro alert qui per non disturbare mentre si scrive, 
            // ma resetto la spunta
            spuntaL.setSelected(false);
            return;
        }
        
        // 2. Ricerca nel catalogo
        libroCorrente = null;
        if(catalogoLibri != null){
             for(Libro l : catalogoLibri.getInventarioLibri()){
                 if(l.getTitolo().equalsIgnoreCase(titolo) && l.getAutore().equalsIgnoreCase(autore)){
                     libroCorrente = l;
                     break;
                 }
             }
        }
        
        // 3. Logica di visualizzazione (UGUALE A SPUNTA_U)
        if(libroCorrente != null && libroCorrente.isDisponibile()){
             // CASO POSITIVO:
             // Metto la spunta (visivo)
             spuntaL.setSelected(true);
             
             // La blocco (così l'utente non può toglierla, è un indicatore di stato)
             spuntaL.setDisable(true);
             
             // Abilito il salvataggio
             salvaP.setDisable(false);
             
        } else {
             // CASO NEGATIVO (Non trovato o non disponibile):
             spuntaL.setSelected(false);
             spuntaL.setDisable(true); // Resta bloccata e vuota
             salvaP.setDisable(true);
             
             // Mostro l'errore specifico
             if(libroCorrente == null) {
                 mostraAlert(AlertType.ERROR, "Non Trovato", "Libro non presente in catalogo.");
             } else {
                 mostraAlert(AlertType.ERROR, "Non Disponibile", "Tutte le copie sono in prestito.");
             }
        }
    }

    
    /**
     * @brief Salva il nuovo prestito.
     * Esegue le seguenti operazioni:
     * 1. Decrementa le copie disponibili del libro.
     * 2. Crea un nuovo oggetto Prestito.
     * 3. Associa il prestito allo Studente.
     * 4. Salva le modifiche sul file CSV (Studenti e libri).
     * @param[in] event L'evento di click sul pulsante Salva. 
     */
    
    @FXML
    private void salvaPrestito(ActionEvent event) {
        
        //Controllo di sicurezza 
        if(studenteCorrente == null || libroCorrente == null ){
            
            mostraAlert(AlertType.ERROR, "Errore", "Dati incompleti per il prestito.");
            return;
        }
        
        try{
            //Decrementa le copie de libro 
            boolean decremento = libroCorrente.decrementaCopie();
            if(!decremento){
                
            mostraAlert(AlertType.ERROR, "Errore", "Impossibile decrementare le copie (già 0)");
            return;
            
           }
            
            LocalDate oggi = LocalDate.now();
            LocalDate scadenza = oggi.plusDays(Prestito.DURATA_PRESTITO);
            
            Prestito nuovoPrestito = new Prestito(libroCorrente, studenteCorrente, oggi, scadenza, null);
            
            //Aggiunge il prestito allo studente
            
            studenteCorrente.aggiungiPrestito(nuovoPrestito);
            
            elencoStudenti.salvaCSV(); // Salva il prestito nel file studenti
            catalogoLibri.salvaCSV();  // Salva il numero copie aggiornato nel file libri
            
            mostraAlert(AlertType.INFORMATION, "Successo", "Prestito registrato con successo.\n Scadenza: "+ scadenza);
            pulisciCampiAnagrafici();
            inserisciT.clear();
            inserisciA.clear();
            spuntaL.setSelected(false);
            salvaP.setDisable(true);
            studenteCorrente = null;
            libroCorrente = null;
            
            
            
        }catch(Exception e){
            e.printStackTrace();
            mostraAlert(AlertType.ERROR, "Errore critico", "Errore durante il salvataggio.");
        }
    }


    
    /**
     * @brief Gestisce la restituzione di un libro.
     * Identifica il prestito tramite matricola e ISBN, calcola eventuali sanzioni,
     * chiude il prestito impostando la data di restituzione e incrementa le copie disponibili. 
     * @param[in] event L'evento di click sul pulsante Salva Restituzioe.
     */
    
    @FXML
    private void salvaRestituzione(ActionEvent event) {
       
       String matricola = inserisciMa.getText();
       String isbn = inserisciISBN.getText();
       String statoLibro = statoLibroBox.getValue(); 
       
       if(matricola.isEmpty() || isbn.isEmpty()){
           mostraAlert(AlertType.ERROR, "Dati mancanti", "Inserisci matricola e isbn per procedere.");
           return;
       }
       if(statoLibro == null){
           mostraAlert(AlertType.ERROR, "Dati mancanti", "Seleziona lo stato del libro.");
           return;
       }
       
       if(elencoStudenti == null){
           mostraAlert(AlertType.ERROR, "Non trovato", "Archivio studenti non trovato");
           return;
       }
       
       Studente studente = elencoStudenti.cercaStudenteperMatricola(matricola);
       
       if(studente == null){
           mostraAlert(AlertType.ERROR, "Non trovato", "Nessuno studente trovato con matricola: " + matricola);
           return;
       }
       
       Prestito prestitoDaChiudere = null;
       for(Prestito p : studente.getPrestitiAttivi()){
           if(p.getLibro().getIsbn().equals(isbn)){
               prestitoDaChiudere = p;
               break; 
           }
       }
       
       if(prestitoDaChiudere == null){
           mostraAlert(AlertType.WARNING,"Nessun prestito", "Lo studente "+ studente.getCognome() + " non ha in prestito il libro con ISBN:" +isbn);
           return;
       }
       
       try{
           // Imposta la data di restituzione ad oggi
           prestitoDaChiudere.setDatarestituzione(LocalDate.now());
           
           // --- MODIFICA IMPORTANTE: AGGIORNA IL CATALOGO ---
           String isbnLibro = prestitoDaChiudere.getLibro().getIsbn();
           
           if(catalogoLibri != null) {
               for(Libro l : catalogoLibri.getInventarioLibri()){
                   if(l.getIsbn().equals(isbnLibro)){
                       l.incrementaCopie(1); // Incrementa il libro nel catalogo
                       break;
                   }
               }
           }
          
           
           String esitoSanzione = prestitoDaChiudere.gestioneSanzioni();
           studente.setSanzione(prestitoDaChiudere.gestioneSanzioni());
           studente.rimuoviPrestito(prestitoDaChiudere);
           
           // Salva le modifiche su file
           elencoStudenti.salvaCSV(); 
           catalogoLibri.salvaCSV();
           
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Restituzione completata");
           alert.setHeaderText("Libro restituito con successo");
           alert.setContentText(esitoSanzione); 
           alert.showAndWait();
           
           pulisciCampiRestituzione();
           
       }catch(Exception e){
           e.printStackTrace();
           mostraAlert(AlertType.ERROR, "Errore", "Si è verificato un problema durante la restituzione.");
       }     
    }
   
    /**
     * @brief Gestisce la chiusura della finestra corrente.
     * @param[in] event L'evento di click sul pulsante di uscita.
     */
     @FXML
    private void exitPFile(ActionEvent event) {
        
        Node exit = (Node) event.getSource();
        
        Stage stageExit = (Stage) exit.getScene().getWindow();
        
        stageExit.close();
    }
    
  /**
   * @brief Abilita o disabilita i campi di testo per la ricerca del libro.
   * Utilizzato per bloccare l'interfaccia finché non viene validato uno studente.
   * @param[in] stato true per abilitare i campi, false per disabilitarli. 
   */
    
   public void gestisciCampiLibro(boolean stato){
        inserisciT.setDisable(!stato);
        inserisciA.setDisable(!stato);
        
        // La checkbox spuntaL deve restare SEMPRE disabilitata all'interazione manuale
        spuntaL.setDisable(true); 
        
        if(!stato){
            salvaP.setDisable(true);
            spuntaL.setSelected(false);
            inserisciT.clear(); // Opzionale: pulisce i campi se lo studente cambia
            inserisciA.clear();
        }
    }
    
   /**
    * @brief Mostra un alert all'utente.
    * @param[in] type Il tipo di alert.
    * @param[in] titolo Il titolo della finestra.
    * @param[in] contenuto Il messaggio da visualizzare.
    */
   
    public void mostraAlert(AlertType type, String titolo, String contenuto ){
        
        Alert alert = new Alert(type);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(contenuto);
        alert.showAndWait();
        
        
    }
    
    /**
     * @brief Resetta i campi anagrafici e blocca la sezione libri.
     */
    
    public void pulisciCampiAnagrafici(){
        inserisciN.clear();
        inserisciC.clear();
        inserisciE.clear();
        spuntaU.setSelected(false);
        spuntaU.setDisable(true);
        gestisciCampiLibro(false);
    }
    
    /**
     * @brief Pulisce i campi della sezione restituzioni.
     */
    
    public void pulisciCampiRestituzione(){
        inserisciMa.clear();
        inserisciISBN.clear();
        statoLibroBox.setValue(null);
        
    }
    
    /**
     * @brief Gestisce il ritorno al Menu Principale.
     * Carica il file FXML del menu, imposta la nuova scena e chiude quella corrente.
     * @param[in] event L'evento di click sul pulsante.
     */
    
     @FXML
    private void menuPReturn(ActionEvent event) {
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