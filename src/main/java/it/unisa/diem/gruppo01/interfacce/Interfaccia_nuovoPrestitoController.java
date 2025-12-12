/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * FXML Controller class
 *
 * @author nicolaazzato
 */
public class Interfaccia_nuovoPrestitoController implements Initializable {

    @FXML
    private TextField inserisciM;
    @FXML
    private TextField inserisciN;
    @FXML
    private TextField inserisciC;
    @FXML
    private TextField inserisciE;
    @FXML
    private CheckBox spuntaU;
    @FXML
    private TextField inserisciT;
    @FXML
    private TextField inserisciA;
    @FXML
    private CheckBox spuntaL;
    @FXML
    private Button salvaP;
    @FXML
    private TextField inserisciMa;
    @FXML
    private TextField inserisciISBN;
    @FXML
    private Button salvaRes;
    @FXML
    private Button exitApp;
    @FXML
    private Button menuPButton;
    @FXML
    private ComboBox<String> statoLibroBox;
    
    
    
    private Elenco elencoStudenti;
    private Catalogo catalogoLibri;
    
    
    //variabile per tenere traccia dello studente corrente
    private Studente studenteCorrente;
    
    //variabile per memorizzare il liro trovato
    private Libro libroCorrente = null;
 
    
    
    /**
     * Metodo per passare i dati al controller
     */
    
    public void setDati(Elenco elencoStudenti, Catalogo catalogoLibri){
        
        this.elencoStudenti = elencoStudenti;
        this.catalogoLibri = catalogoLibri;
    }
    
    
    /**
     * Initializes the controller class.
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
       
    }    

    /**
     * Gestisce l'inserimento della matricola per la ricerca dello studente
     * @param event 
     */
    @FXML
    private void inserisciMatricola(ActionEvent event) {
        String matricola = inserisciM.getText();
        
        if(elencoStudenti == null)return;
        
        //Ricerca dello studente
        studenteCorrente = elencoStudenti.cercaStudenteperMatricola(matricola);
        
        if(studenteCorrente != null){
         inserisciN.setText(studenteCorrente.getNome());
         inserisciC.setText(studenteCorrente.getCognome());
         inserisciE.setText(studenteCorrente.getEmail());
         
         spuntaU.setSelected(false);
         gestisciCampiLibro(false);
         
         //sblocca la checkBox e la rende cliccabile
         spuntaU.setDisable(false);
         
        }else{
            
            //studente non trovato
            studenteCorrente = null;
            pulisciCampiAnagrafici();
            mostraAlert(AlertType.WARNING, "Attenzione", "Nessuno studente trovato con questa matricola.");
           
           
        }
    }


    
    /**
     * Metodo che gestisce il click sulla CheckBox (Utente abilitato)
     * @param event 
     */
    
@FXML
private void spuntaUtente(ActionEvent event) {
    System.out.println("--- DEBUG INIZIO ---");
    
    if(spuntaU.isSelected()){
        System.out.println("1. Checkbox selezionata");

        if(studenteCorrente == null){
             System.out.println("ERRORE: Variabile studenteCorrente è NULL!");
             mostraAlert(AlertType.ERROR, "Errore", "Rifare la ricerca matricola");
             return;
        }

        System.out.println("2. Studente trovato: " + studenteCorrente.getCognome());
        
        // VERIFICA CRITICA DEI DATI
        System.out.println("   - Prestiti attivi: " + studenteCorrente.contaPrestitiAttivi());
        System.out.println("   - È in ritardo? " + studenteCorrente.isRitardo());
        System.out.println("   - ESITO isAbilitato(): " + studenteCorrente.isAbilitato());

        if(!studenteCorrente.isAbilitato()){
            System.out.println("3. BLOCCO: Lo studente non è abilitato. Entro nell'IF di errore.");
            
            // Mostriamo l'errore per capire il motivo
            if(studenteCorrente.isRitardo()) {
                mostraAlert(AlertType.ERROR, "Blocco", "Studente in ritardo!");
            } else {
                mostraAlert(AlertType.ERROR, "Blocco", "Limite prestiti raggiunto (" + studenteCorrente.contaPrestitiAttivi() + ")");
            }

            spuntaU.setSelected(false);
            gestisciCampiLibro(false);
            
        } else {
            System.out.println("3. SUCCESSO: Studente abilitato. Chiamo gestisciCampiLibro(true)");
            gestisciCampiLibro(true); 
        }

    } else {
        System.out.println("Checkbox deselezionata -> Blocco tutto");
        gestisciCampiLibro(false);
    }
    System.out.println("--- DEBUG FINE ---");
}
    
    

    @FXML
    private void inserisciTitolo(ActionEvent event) {
    }

    @FXML
    private void inserisciAutore(ActionEvent event) {
    }

    @FXML
    private void spuntaLibro(ActionEvent event) {
        
        if(spuntaL.isSelected()){
            
            String titolo = inserisciT.getText();
            String autore = inserisciA.getText();
            
            if(titolo.isEmpty() || autore.isEmpty()){
                
                mostraAlert(AlertType.ERROR, "Dati mancanti", "Inserisci Titolo e Autore.");
                spuntaL.setSelected(false);
                return;
            }
            
            //Ricerca del libro nel catalogo
            //Poichè catalogo è un TreeSet iteriamo per trovare una corrispondenza Titolo/Autore
            
            libroCorrente = null;
            
            if(catalogoLibri != null){
                
                for(Libro l : catalogoLibri.getInventarioLibri()){
                    
                    if(l.getTitolo().equalsIgnoreCase(titolo) && l.getAutore().equalsIgnoreCase(autore)){
                        libroCorrente = l;
                        break;
                    }
                }
                
            }
            //Controllo esistenza
            
            if(libroCorrente == null){
                
                mostraAlert(AlertType.ERROR, "Libro non trovato", "Il libro specificato non è presente nel catalogo");
                spuntaL.setSelected(false); //Annulla la spunta
                return; 
            }
            
            //Controllo disponibilità copie
            
            if(!libroCorrente.isDisponibile()){
                
                mostraAlert(AlertType.ERROR, "Libro non disponibile", "Le copie del libro sono esaurite");
                spuntaL.setSelected(false); //Annulla la spunta
                return; 
                
            }
            
            //Tutto OK. Il salvataggio viene abilitato
            
            salvaP.setDisable(false);  //sblocca il bottone salva
            
        }else{ //l'uente sta togliendo la spunta
            salvaP.setDisable(true); //disabilita il salvataggio
            libroCorrente = null; //resetta il libro selezionato
            
        }
        
    }

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

    @FXML
    private void inserisceMatricola(ActionEvent event) {
    }

    @FXML
    private void inserisceISBN(ActionEvent event) {
    }

    @FXML
    private void salvaRestituzione(ActionEvent event) {
        
        String matricola = inserisciMa.getText();
        String isbn = inserisciISBN.getText();
        String statoLibro = statoLibroBox.getValue(); // recupera il valore dalla Combobox
        
        if(matricola.isEmpty() || isbn.isEmpty()){
            mostraAlert(AlertType.ERROR, "Dati mancanti", "Inserisci matricola e isbn per procedere.");
            return;
        }
        if(statoLibro == null){
            mostraAlert(AlertType.ERROR, "Dati mancanti", "Seleziona lo stato del lbro.");
            return;
        }
        
        //ricerca dello studente
        if(elencoStudenti == null){
            mostraAlert(AlertType.ERROR, "Non trovato", "Archivio studenti non trovato");
            return;
        }
        Studente studente = elencoStudenti.cercaStudenteperMatricola(matricola);
        
        if(studente == null){
            mostraAlert(AlertType.ERROR, "Non trovato", "Nessuno studente trovato con matricola: " + matricola);
            return;
        }
        
        //Cerca il prestito attivo tramite isbn
        //Dobbiamo scorrere i prestiti attivi dello studente per trovare quello giusto
        Prestito prestitoDaChiudere = null;
        
        for(Prestito p : studente.getPrestitiAttivi()){
        if(p.getLibro().getIsbn().equals(isbn)){
        prestitoDaChiudere = p;
        break; //trovato
    }
    
    }
        
        if(prestitoDaChiudere == null){
            mostraAlert(AlertType.WARNING,"Nessun prestito", "Lo studente "+ studente.getCognome() + " non ha in prestito il libro con ISBN:" +isbn);
            return;
                  
        }
        
        //Procedura di restituzione
        
        try{
            
            //Imposta la data di restituione ad oggi
            prestitoDaChiudere.setDatarestituzione(LocalDate.now());
            
            //Incrementa le copie disponibili nel catalogo
            prestitoDaChiudere.getLibro().incrementaCopie(1);
            
            //Calcolo sanzioni
            String esitoSanzione = prestitoDaChiudere.gestioneSanzioni();
            
            //Rimuovi il prestito dalla lista prestiti attivi dello studente
            studente.rimuoviPrestito(prestitoDaChiudere);
            
            elencoStudenti.salvaCSV(); 
            catalogoLibri.salvaCSV();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Restituzione completata");
            alert.setHeaderText("Libro restituito con successo");
            alert.setContentText(esitoSanzione);  //mostra se cè una sanzione o è tutto ok
            alert.showAndWait();
            
            pulisciCampiRestituzione();
            
        }catch(Exception e){
            e.printStackTrace();
            mostraAlert(AlertType.ERROR, "Errore", "Si è verificato un problema durante la restituzione.");
        }
                
    }
    
     @FXML
    private void exitPFile(ActionEvent event) {
        
        Node exit = (Node) event.getSource();
        
        Stage stageExit = (Stage) exit.getScene().getWindow();
        
        stageExit.close();
    }
    
    //Metodo per attivare/disattivare i campi del libro
    
    public void gestisciCampiLibro(boolean stato){
        inserisciT.setDisable(!stato);
        inserisciA.setDisable(!stato);
        spuntaL.setDisable(!stato);
        
        //Se sblocchiamo i campi (e quindi stato = True), il bottone salvaP deve restare disabilitato
        //finchè non si spunta la CheckBox ("Libro disponibile").
        //se stato = false, disabilitiamo tutto.
        
        if(!stato){
        salvaP.setDisable(true);
        spuntaL.setSelected(false);
        }
        
    }
    
    public void mostraAlert(AlertType type, String titolo, String contenuto ){
        
        Alert alert = new Alert(type);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(contenuto);
        alert.showAndWait();
        
        
    }
    
    public void pulisciCampiAnagrafici(){
        inserisciN.clear();
        inserisciC.clear();
        inserisciE.clear();
        spuntaU.setSelected(false);
        spuntaU.setDisable(true);
        gestisciCampiLibro(false);
    }
    
    public void pulisciCampiRestituzione(){
        inserisciMa.clear();
        inserisciISBN.clear();
        statoLibroBox.setValue(null);
        
    }
    
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