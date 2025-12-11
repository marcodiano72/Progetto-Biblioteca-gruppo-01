/**
*@file Catalogo.java
*@brief Questo file gestisce l'inventario dei libri della biblioteca.
*
*@version 1.0
*/


/**
 * Questo package contiene le classi relative alla gestione degli strumenti,
 * contesto: Gestione di una biblioteca.
 */
package it.unisa.diem.gruppo01.classi;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 

/**
 * Classe Catalogo
 * Gestisce l'inventario dei libri della biblioteca. Utilizza un TreeSet
 * con un comparatore personalizzato (LibroComparator) per mantenere i libri
 * ordinati alfabeticamente per titolo*.
 *
 */
public class Catalogo {

   /**
     * Struttura dati che contiene tutti gli oggetti Libro, ordinati
     * per titolo grazie al LibroComparator.
     */
    private TreeSet<Libro> inventarioLibri; // TreeSet per mantenere i libri ordinati alfabeticamente per titolo.

   
    /**
     * Costruttore della classe Catalogo.
     * Inizializza il TreeSet con un'istanza
     * di LibroComparator per definire l'ordinamento.
     */
    public Catalogo() 
    {
        this.inventarioLibri = new TreeSet<>(new LibroComparator());
    }
    
    /**
     * Restituisce la collezione di libri che costituisce l'inventario.
     * @return il TreeSet dei libri.
     */
    public TreeSet<Libro> getInventarioLibri()
    {
        return inventarioLibri;
    }
    
   /*
    * Cerca un libro all'interno del catalogo utilizzando il suo codice ISBN.
    * Questo metodo è un utility interna.
    *
    * @param isbn Il codice ISBN del libro da cercare.
    * @return L'oggetto  trovato, o null se non presente.
    */
    private Libro cercaLibroPerISBN(String isbn) {
        for (Libro libro : inventarioLibri) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
    
    /*
     * Classe Interna: LibroComparator
     * Implementa l'interfaccia Comparator per definire l'ordinamento degli oggetti Libro
     * all'interno del Treeset. L'ordinamento è alfabetico per titolo (insensibile al caso)
     * e, in caso di titoli identici, utilizza l'ISBN per garantire l'unicità.
    */
    public class LibroComparator implements Comparator<Libro> {
        @Override
        public int compare(Libro l1, Libro l2) {
            // Confronta i titoli
            int risultato = l1.getTitolo().compareToIgnoreCase(l2.getTitolo());

            // Se i titoli sono identici, ordina per ISBN per garantire l'unicità
            if (risultato == 0) {
                risultato = l1.getIsbn().compareTo(l2.getIsbn());
            }
            return risultato;
        }
    }

    /*
      Aggiunge un nuovo libro al catalogo. Se il libro (stesso ISBN) esiste già,
      aggiorna il numero di copie e restituisce false.
    * @param nuovoLibro L'oggetto Libro da aggiungere o usare per incrementare le copie.
     * @return true  se il libro è stato aggiunto come nuovo elemento, false se è stato aggiornato un libro esistente.
     */
    public boolean aggiungiLibro(Libro nuovoLibro) {
        
        Libro libroEsistente = cercaLibroPerISBN(nuovoLibro.getIsbn());
        
        if (libroEsistente != null) {
            
            libroEsistente.incrementaCopie(nuovoLibro.getNumCopie());
            
            System.out.println("Libro con ISBN " + nuovoLibro.getIsbn() + " già presente. Incrementate le copie.");
            return false; 
        } else {
            //  aggiungiamo il nuovo libro, se non esiste già.
            return inventarioLibri.add(nuovoLibro);
        }
    }
    

    /*
    *Rimuove un libro dal catalogo utilizzando il suo ISBN.
    * @param isbn Il codice ISBN del libro da rimuovere.
     * @return true se il libro è stato trovato e rimosso, false altrimenti.
     */
    public boolean eliminaLibro(String isbn) {
        
        
        Libro libroDaRimuovere = cercaLibroPerISBN(isbn);
        
        if (libroDaRimuovere != null) {
            inventarioLibri.remove(libroDaRimuovere);
            System.out.println("Libro con ISBN " + isbn + " rimosso dal catalogo.");
            return true;
        }
        System.out.println("Libro con ISBN " + isbn + " non trovato per l'eliminazione.");
        return false;
    }
    
    public boolean modificaLibro(String isbn, String nuovoTitolo, String nuovoAutore, LocalDate nuovoAnnoPb, int nuoveCopie) 
    {
        // 1. Controllo base
    if (nuoveCopie < 0) {
        throw new IllegalArgumentException("Il numero di copie non può essere negativo.");
    }

    // 2. Trova il libro da modificare
    Libro libroDaModificare = cercaLibroPerISBN(isbn);

    if (libroDaModificare == null) {
        return false; // Libro non trovato
    }

    // 3. Gestione del TreeSet per il riordinamento: 
    // Se il Titolo o l'Autore cambiano, la posizione nel TreeSet deve essere aggiornata.
    
    // Per un'efficienza ottimale con TreeSet: rimuovi l'oggetto, aggiorna i campi, e riaggiungilo.
    // Verifichiamo se il titolo o l'autore cambiano, dato che influenzano l'ordinamento.
    boolean titoloCambiato = !libroDaModificare.getTitolo().equals(nuovoTitolo);
    boolean autoreCambiato = !libroDaModificare.getAutore().equals(nuovoAutore);
    
    // Se è cambiata una delle proprietà che definiscono l'ordine (Titolo) o l'unicità (Autore in caso di Titolo uguale)
    if (titoloCambiato || autoreCambiato) {
        // Rimuoviamo il vecchio oggetto per permettere al TreeSet di riorganizzarsi
        inventarioLibri.remove(libroDaModificare);
    }
    
    // 4. Aggiorna i dettagli (Titolo, Autore, AnnoPb, Copie)
    libroDaModificare.setTitolo(nuovoTitolo);
    libroDaModificare.setAutore(nuovoAutore);
    libroDaModificare.setAnnoPb(nuovoAnnoPb); // L'anno non influenza l'ordinamento, ma è un dettaglio
    libroDaModificare.setNumCopia(nuoveCopie);
    
    // 5. Riaggiungi l'oggetto se è stato rimosso (o se è la prima volta che viene modificato)
    if (titoloCambiato || autoreCambiato) {
        // Aggiungiamo nuovamente il libro, forzando il TreeSet a ricalcolare la posizione corretta
        inventarioLibri.add(libroDaModificare);
    }
    
    // Se né il titolo né l'autore sono cambiati, l'oggetto è rimasto nello stesso posto nel TreeSet,
    // e la modifica delle sole copie è riflessa immediatamente.

    return true; // Modifica riuscita
    
    }
    
    /*
    * Fornisce una vista osservabile dell'inventario.
    * Questo è il metodo che il Controller DEVE usare per popolare la TableView.
    * @return ObservableList<Libro> contenente tutti i libri dal TreeSet.
    */
    public ObservableList<Libro> getCatalogoObservableList() {
   
    ObservableList<Libro> observableList = FXCollections.observableArrayList();
    observableList.addAll(inventarioLibri);
    
    return observableList;
}
   
    /*il metodo salvaDOS crea un file di tipo binario (.bin) con i dati relativi al catalogo dei libri
    in particolare viene scritto titolo, autore, codice isbn e numero di copie del libro
    */
    public void salvaDOS(String nomeFile) throws FileNotFoundException, IOException {
        
        // apre file
        FileOutputStream fos = new FileOutputStream(nomeFile); 
        
        // aggiunge buffer
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
        // stream per i dati primitivi
        DataOutputStream dos = new DataOutputStream(bos);
        
        //descrizione anagrafica
        dos.writeUTF("Elenco Libri");
        dos.writeUTF("Titolo|Autore|ISBN|Num_Copie");
        //per ogni studente memorizzo attributi sul file
        for(Libro l : inventarioLibri) {
            dos.writeUTF("\n");
            dos.writeUTF(l.getTitolo());
            dos.writeUTF(l.getAutore());
            dos.writeUTF(l.getIsbn());
            dos.write(l.getNumCopie());
    }
          
        dos.close(); //chiudo stream
        
        
    }
    
    
    // scrive il contenuto della struttura dati in un file CSV
    public void salvaCSV(String nomeFile){
        
        //printwriter scrive testo in modo semplice
        try( PrintWriter pw = new PrintWriter(new FileWriter(nomeFile)) ){
            
            pw.println("Elenco Libri\n");
            // Scrive la riga di intestazione del file CSV
            pw.println("Titolo;Autore;ISBN;Num_Copie");
            
            for( Libro l : this.inventarioLibri ){
                
                pw.append(l.getTitolo());
                pw.append(";");
                pw.append(l.getAutore());
                pw.append(";");
                pw.append(l.getIsbn());
                pw.append(";");
                pw.println(l.getNumCopie()); //va a capo automaticamente 
                
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Catalogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /*
    ** Restituisce una rappresentazione in formato stringa dell'intero catalogo.
     * I libri vengono elencati in ordine alfabetico per titolo, grazie all'uso del Treeset.
     * @return Una stringa che elenca tutti i libri nel catalogo o un messaggio se il catalogo è vuoto.
    */
    @Override
    public String toString() {
        if (inventarioLibri.isEmpty()) {
            return "Il catalogo è vuoto.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== CATALOGO LIBRI (Ordinato per Titolo) =====\n");
        
        for (Libro libro : inventarioLibri) {
            sb.append(libro.toString()).append("\n");
        }
        sb.append("================================================");
        return sb.toString();
    }
    
   
    
}