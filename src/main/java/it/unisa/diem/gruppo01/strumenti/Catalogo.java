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
package it.unisa.diem.gruppo01.strumenti;

import java.util.TreeSet;
import java.util.Comparator;
 

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
    *Modifica il numero di copie disponibili di un libro esistente, cercando per ISBN.
     * @param isbn Il codice ISBN del libro da modificare.
     * @param nuoveCopie Il nuovo numero totale di copie disponibili da impostare.
     * @return true se il libro è stato trovato e modificato, false altrimenti.
     * @throws IllegalArgumentException Se il numero di nuove copie fornito è negativo.
     */
    public boolean modificaLibro(String isbn, int nuoveCopie) {
        if (nuoveCopie < 0) {
            throw new IllegalArgumentException("Il numero di copie non può essere negativo.");
        }
        
        Libro libroDaModificare = cercaLibroPerISBN(isbn);
        
        if (libroDaModificare != null) {
        
            libroDaModificare.setNumCopia(nuoveCopie); 
            System.out.println("Libro con ISBN " + isbn + " modificato. Nuove copie: " + nuoveCopie);
            return true;
        }
        System.out.println("Libro con ISBN " + isbn + " non trovato per la modifica.");
        return false;
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