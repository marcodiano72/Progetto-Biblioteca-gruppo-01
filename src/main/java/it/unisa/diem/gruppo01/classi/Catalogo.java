/**
*@file Catalogo.java
*@brief Questo file gestisce l'inventario dei libri della biblioteca.
* Contiene la logica per aggiungere, rimuovere, modificare e cercare libri.
* 
* @author Gruppo01
*@version 1.0
*/


/**
 * Questo package contiene le classi relative alla gestione degli strumenti,
 * contesto: Gestione di una biblioteca.
 */
package it.unisa.diem.gruppo01.classi;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
 * @brief Classe Singleton che gestisce l'inventario dei libri della biblioteca.
 * Utilizza un TreeSet con un comparatore personalizzato (LibroComparator) 
 * per mantenere i libri ordinati alfabeticamente per titolo.
 * Gestisce inoltre il caricamento e il salvataggio dei dati su file CSV.
 */

public class Catalogo {

   /**
     * Struttura dati che contiene tutti gli oggetti Libro, ordinati
     * per titolo grazie al LibroComparator.
     */
    private static Catalogo istanza;
    
    
    private TreeSet<Libro> inventarioLibri; ///< Collezione ordinata dei libri (ordinamento per Titolo, poi Isbn).

    private final static String NOME_FILE_CSV = "Lista_Libri.csv"; ///< Nome del file utilizzato per la persistenza dei dati.
    
    private final static String DIR = NOME_FILE_CSV; 
    
    
    /**
     * @brief Costruttore della classe Catalogo.
     * Inizializza il TreeSet con un'istanza di LibroComparator per definire l'ordinamento.
     * 
     */
    
    public Catalogo() 
    {
        this.inventarioLibri = new TreeSet<>(new LibroComparator());
    }
    
    /**
     * @brief Restituisce la collezione di libri che costituisce l'inventario.
     * @return il TreeSet contenente gli oggetti Libro.
     */
    
    public TreeSet<Libro> getInventarioLibri()
    {
        return inventarioLibri;
    }
    
    /**
     * Metodo statico per accedere all'unica istanza del Catalogo (Singleton).
     * Carica i dati dal disco la prima volta che viene chiamato se l'istanza non esiste.
     * @return L'unica istanza esistente di Catalogo.
     */
    public static Catalogo getIstanza() {
        if (istanza == null) {
            istanza = new Catalogo();
            // CHIAMATA AL CARICAMENTO AUTOMATICO ALLA PRIMA CREAZIONE
            istanza.caricaCSV(); 
        }
        return istanza;
    }
        
     /**
     * @brief Metodo statico per forzare il salvataggio dei dati.
     * Deve essere chiamato da Main.stop() alla chiusura dell'applicazione per persistere le modifiche.
     * @post i dati attuali in memoria vengono scritti nel file CSV
     */
    
    public static void salvaDati()
    {
        if (istanza != null)
        {
            istanza.salvaCSV(); // Usa il metodo non statico interno
            System.out.println("Salvataggio automatico dei dati eseguito in: " + new File(NOME_FILE_CSV).getAbsolutePath());
        }
    }
    
    
   /**
    * @brief Cerca un libro all'interno del catalogo utilizzando il suo codice ISBN.
    * Questo metodo è un utility interna.
    * @param[in] isbn Il codice ISBN del libro da cercare.
    * @return L'oggetto Libro trovato, oppure null se non presente.
    */
    private Libro cercaLibroPerISBN(String isbn) {
        for (Libro libro : inventarioLibri) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
    
    /**
     * @brief Implementa l'interfaccia Comparator per definire l'ordinamento dei libri.
     * L'ordinamento è alfabetico per titolo (case-insensitive).
     * In caso di titoli identici, utilizza l'ISBN per garantire l'unicità nel TreeSet.
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

    /**
     * @brief Aggiunge un nuovo libro al catalogo. Se il libro (stesso ISBN) esiste già,
     * aggiorna il numero di copie e restituisce false.
     * @param[in] nuovoLibro L'oggetto Libro da aggiungere o usare per incrementare le copie.
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
    
    /**
     * @brief Incrementa di una unità le copie di un libro dato il suo ISBN.
     * @param[in] isbn Il codice ISBN del libro.
     * @return true se il libro esiste ed è stato incrementato, false altrimenti.
     */
    
 public boolean incrementaCopie(String isbn) {
    Libro libro = cercaLibroPerISBN(isbn);   // questo metodo esiste già
    if (libro != null) {
        libro.incrementaCopie(1);            // metodo esistente in Libro
        return true;
    }
    return false;
}

    /**
    *@brief Rimuove un libro dal catalogo utilizzando il suo ISBN.
    * @param[in] isbn Il codice ISBN del libro da rimuovere.
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
    
    /**
     * @brief Modifica i dettagli di un libro esistente.
     * Gestisce la rimozione e il reinserimento nel TreeSet se i campi che influenzano
     * l'ordinamento (Titolo o Autore) vengono modificati.
     * @pre nuoveCopie >= 0
     * @param[in] isbn L'ISBN del libro da modificare (chiave di ricerca).
     * @param[in] nuovoTitolo Il nuovo titolo.
     * @param[in] nuovoAutore Il nuovo autore.
     * @param[in] nuovoAnnoPb La nuova data di pubblicazione.
     * @param[in] nuoveCopie Il nuovo numero totale di copie.
     * @return true se la modifica è avvenuta con successo, false se il libro non è stato trovato.
     * @throws IllegalArgumentException Se nuoveCopie è negativo.
     */
    
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
    
    /**
    * @brief Fornisce una vista osservabile dell'inventario.
    * Questo è il metodo che il Controller DEVE usare per popolare la TableView.
    * @return ObservableList<Libro> contenente tutti i libri dal TreeSet.
    */
    
    public ObservableList<Libro> getCatalogoObservableList() {
   
    ObservableList<Libro> observableList = FXCollections.observableArrayList();
    observableList.addAll(inventarioLibri);
    
    return observableList;
}
   
    
    /**
     * @brief Salva l'intero inventario su file CSV.
     * Scrive i dati nel formato: Titolo;Autore;ISBN;Anno pb;Num_Copie.
     * Gestisce le eccezioni di I/O loggando l'errore.
     */
    
    public void salvaCSV()
    { 
    
    // Ora usiamo direttamente la costante statica NOME_FILE_CSV definita in Catalogo
    try( PrintWriter pw = new PrintWriter(new FileWriter(NOME_FILE_CSV)) ){
        
        pw.println("Elenco Libri");
        pw.println("Titolo;Autore;ISBN;Anno pb;Num_Copie"); 
        
        for( Libro l : this.inventarioLibri ){
            
            pw.append(l.getTitolo()).append(";");
            pw.append(l.getAutore()).append(";");
            pw.append(l.getIsbn()).append(";");
            pw.append(l.getAnnoPb().toString()).append(";");
            pw.println(l.getNumCopie()); 
            
        }
    } catch (IOException ex) {
        Logger.getLogger(Catalogo.class.getName()).log(Level.SEVERE, "Errore durante il salvataggio CSV", ex);
    }
}
    
    
    /**
     * @brief Carica i dati del catalogo dal file CSV specificato.
     * In caso di errore (file non trovato), il catalogo viene inizializzato vuoto.
     * Il file viene cercato nella working directory (solitamente la root del progetto).
     */
    
    public void caricaCSV() {
        
        // 1. Pulisce la struttura dati prima del caricamento
        this.inventarioLibri.clear(); 
        
        // La costante statica (che devi avere in Catalogo.java)
        // private final static String NOME_FILE_CSV = "Lista_Libri.csv"; 
        
        File file = new File(NOME_FILE_CSV);

        // Controlla se il file esiste
        if (!file.exists()) {
             // Se il file non esiste, stampiamo dove lo cercava (utile per il debug)
             System.err.println("CARICAMENTO INIZIALE FALLITO: File dati NON trovato in: " + file.getAbsolutePath()); 
             System.err.println("Inizio con catalogo vuoto.");
             return;
        }
        
        // 2. Tenta la lettura del file
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_FILE_CSV))) {
            
            // Ignora le prime due righe (Header: "Elenco Libri" e "Titolo;Autore;ISBN;Num_Copie")
            br.readLine(); 
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                // Il tuo separatore è il punto e virgola
                String[] dati = line.split(";");
                
                // Assicurati che la riga abbia 4 campi (Titolo, Autore, ISBN, Num_Copie)
                if (dati.length == 5) {
                    try {
                        String titolo = dati[0].trim();
                        String autore = dati[1].trim();
                        String isbn = dati[2].trim();
                        LocalDate annoPb = LocalDate.parse(dati[3].trim());
                        int numCopie = Integer.parseInt(dati[4].trim());
                        
                        // Per il LocalDate, usiamo un anno fittizio,
                        // dato che non è presente nel salvataggio/caricamento del CSV
                     
                        
                        Libro libro = new Libro(isbn, titolo, autore, annoPb, numCopie);
                        this.inventarioLibri.add(libro);
                        
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                        System.err.println("Attenzione: Riga CSV non valida e saltata: " + line);
                    }
                }
            }
            System.out.println("CARICAMENTO: Dati letti con successo da: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Errore I/O durante la lettura del file: " + e.getMessage());
        }
    }
    
    /**
    * @brief Restituisce una rappresentazione in formato stringa dell'intero catalogo.
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