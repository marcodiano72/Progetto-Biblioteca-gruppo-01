/**
 * @file CatalogoTest.java
 * @brief Classe di test per l'implementazione della classe Catalogo.
 * @author gruppo01
 * @version 1.0
 */
package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.classi.Catalogo;
import it.unisa.diem.gruppo01.classi.Libro;
import java.io.File;
import java.time.LocalDate;
import java.util.TreeSet;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @brief Classe di test per la classe Catalogo.
 */

public class CatalogoTest {
    
    // Variabili di istanza per i test
    private Libro libro1;
    private Libro libro2;
    private Libro libro3;
    private Libro libro4;
    private Libro libro5;
    private Libro libro6;
    private Libro libro7;
    private Libro libro8;
    private Libro libro9;
    private Libro libro10;

    private final static String NOME_FILE_TEST = "Lista_Libri.csv";
    
    // L'istanza Ssarà ottenuta tramite getIstanza() in setUp()
    private Catalogo istanzaCatalogo;
    
    /**
     * @brief Costruttore di default.
     */
    public CatalogoTest() {
    }
    
    /**
     * @brief Configurazione eseguita una sola volta prima di tutti i test.
     * Non utilizzato in questo caso.
     */
    @BeforeAll
    public static void setUpClass() {
    }
    
    /**
     * @brief Pulizia eseguita una sola volta dopo tutti i test.
     * Tenta di eliminare il file CSV creato durante i test per garantire l'isolamento.
     */
    @AfterAll
    public static void tearDownClass() {
        // Pulizia finale del file di test
        File file = new File(NOME_FILE_TEST);
        if (file.exists()) {
            file.delete();
        }
    }
    
    /**
     * @brief Configurazione eseguita prima di ogni metodo di test.
     * Inizializza i dati di prova e ottiene l'istanza Singleton di Catalogo.
     */
    @BeforeEach
    public void setUp() {
        // Inizializzazione dei dati di prova
        LocalDate data = LocalDate.of(2000, 1, 1);
        libro1 = new Libro("1111", "Prova", "Gruppo", LocalDate.of(2025, 1, 1), 1);
        libro2 = new Libro("1462", "Il Nome della Rosa", "Umberto Eco", LocalDate.of(1980, 1, 1), 2);
        libro3 = new Libro("8947", "Il Piccolo Principe", "Antoine de Saint-Exupéry", LocalDate.of(1943, 1, 1), 4);
        libro4 = new Libro("8846", "Il Signore degli Anelli", "J.R.R. Tolkien", LocalDate.of(1954, 1, 1), 1);
        libro5 = new Libro("4125", "Orgoglio e Pregiudizio", "Jane Austen", LocalDate.of(1813, 1, 1), 3);
        libro6 = new Libro("8624", "Niente di nuovo sul fronte occidentale", "Erich Maria Remarque", LocalDate.of(1929, 1, 1), 5);
        libro7 = new Libro("8561", "Se questo è un uomo", "Primo Levi", LocalDate.of(1947, 1, 1), 3);
        libro8 = new Libro("4568", "Frankenstein", "Mary Shelley", LocalDate.of(1818, 1, 1), 2);
        libro9 = new Libro("7954", "L'esorcista", "William Peter Blatty", LocalDate.of(1971, 1, 1), 7);
        libro10 = new Libro("7012", "Critica della ragione artificiale. Una difesa dell'umanità", "Éric Sadin", LocalDate.of(2017, 1, 1), 5);
        
        
        // Otteniamo l'istanza  la puliamo
        istanzaCatalogo = Catalogo.getIstanza();
        istanzaCatalogo.getInventarioLibri().clear();
        
        // Assicuriamoci che il file di persistenza non esista prima di molti test
        new File(NOME_FILE_TEST).delete();
    }
    
    /**
     * @brief Pulizia eseguita dopo ogni metodo di test.
     * Non utilizzato in questo caso.
     */
    @AfterEach
    public void tearDown() {
        // Eventuale pulizia post-test
    }

    /**
     * @brief Test del metodo getInventarioLibri(), della classe Catalogo.
     * Verifica che il TreeSet restituito sia non nullo e inizialmente vuoto dopo setUp().
     */
    @Test
    public void testGetInventarioLibri() {
        System.out.println("testGetInventarioLibri");
        TreeSet<Libro> result = istanzaCatalogo.getInventarioLibri();
        assertNotNull(result, "Il TreeSet non deve essere nullo.");
        assertTrue(result.isEmpty(), "Il TreeSet dovrebbe essere vuoto dopo setUp().");
    }

    /**
     * @brief Test del metodo getIstanza(), della classe Catalogo (Singleton pattern).
     * Verifica che due chiamate al metodo restituiscano la stessa istanza.
     */
    @Test
    public void testGetIstanza() {
        System.out.println("testGetIstanza");
        Catalogo result = Catalogo.getIstanza();
        Catalogo result2 = Catalogo.getIstanza();
        assertNotNull(result, "L'istanza non deve essere nulla.");
        assertSame(result, result2, "Le istanze dovrebbero essere le stesse (Singleton).");
    }

    /**
     * @brief Test del metodo salvaDati(), della classe Catalogo.
     * Verifica che i dati in memoria vengano scritti correttamente sul file CSV.
     * Nota: Testa l'effettiva creazione del file e il contenuto tramite caricaCSV().
     */
    @Test
    public void testSalvaDati() {
        System.out.println("testSalvaDati");
        
        istanzaCatalogo.aggiungiLibro(libro1);
        istanzaCatalogo.aggiungiLibro(libro2);
        istanzaCatalogo.aggiungiLibro(libro3);
        istanzaCatalogo.aggiungiLibro(libro4);
        istanzaCatalogo.aggiungiLibro(libro5);
        istanzaCatalogo.aggiungiLibro(libro6);
        istanzaCatalogo.aggiungiLibro(libro7);
        istanzaCatalogo.aggiungiLibro(libro8);
        istanzaCatalogo.aggiungiLibro(libro9);
        istanzaCatalogo.aggiungiLibro(libro10);
        
        // Esegui il salvataggio
        Catalogo.salvaDati();
        
        File file = new File(NOME_FILE_TEST);
        assertTrue(file.exists(), "Il file CSV deve essere creato dopo salvaDati().");
        
        // Puliamo l'inventario in memoria per testare il caricamento
        istanzaCatalogo.getInventarioLibri().clear();
        
        // Ricarichiamo per verificare che il salvataggio sia andato a buon fine
        istanzaCatalogo.caricaCSV();
        
        assertEquals(10, istanzaCatalogo.getInventarioLibri().size(), "Dopo il salvataggio e ricaricamento, ci deve essere 1 libro.");
        
        // Pulizia
        file.delete();
    }

    /**
     * @brief Test del metodo aggiungiLibro(), della classe Catalogo.
     * Verifica l'aggiunta di un nuovo libro e l'incremento delle copie per un libro esistente.
     */
    @Test
    public void testAggiungiLibro() {
        System.out.println("testAggiungiLibro");
        
        // Aggiunta di un nuovo libro
       boolean aggiuntoNuovo = istanzaCatalogo.aggiungiLibro(libro2); // ISBN: 978-0743273565, Copie: 3
    assertTrue(aggiuntoNuovo, "Dovrebbe ritornare true per un nuovo libro.");
    assertEquals(1, istanzaCatalogo.getInventarioLibri().size(), "Ci deve essere 9 libro.");
    
    // Tentativo di aggiungere un libro con lo STESSO ISBN (dovrebbe incrementare le copie)
    // Usiamo lo stesso ISBN di libro2, ma copie e dettagli diversi
    Libro libro2b = new Libro(libro2.getIsbn(), "Titolo Nuovo", "Autore Nuovo", LocalDate.of(2010, 1, 1), 2);
    
    // Il metodo cercaLibroPerISBN lo troverà. Dovrebbe restituire false.
    boolean aggiornato = istanzaCatalogo.aggiungiLibro(libro2b);
    
    // ASSERZIONE CORRETTA
    assertFalse(aggiornato, "Dovrebbe ritornare false per un libro esistente."); // Risolve expected:<false> but was:<true>
    assertEquals(1, istanzaCatalogo.getInventarioLibri().size(), "Il numero di elementi deve rimanere 9.");
    
    // Verifica l'incremento delle copie (2+2=4)
    Libro libroTrovato = istanzaCatalogo.getCatalogoObservableList().get(0);
    assertEquals(4, libroTrovato.getNumCopie(), "Il numero di copie deve essere incrementato (3 + 2 = 5).");
    }

    /**
     * @brief Test del metodo incrementaCopie(), della classe Catalogo.
     * Verifica l'incremento delle copie di un libro dato l'ISBN e la gestione del caso di libro non trovato.
     */
    @Test
    public void testIncrementaCopie() {
        System.out.println("testIncrementaCopie");
        
        istanzaCatalogo.aggiungiLibro(libro1); // Copie iniziali: 5
        
       //  Incremento riuscito
        boolean incrementato = istanzaCatalogo.incrementaCopie(libro1.getIsbn()); // Catalogo ha 1 libro con 1 copia
        assertTrue(incrementato, "Dovrebbe ritornare true per l'incremento riuscito."); // +1 copia
        // Correzione: 1 + 1 = 2
        assertEquals(2, istanzaCatalogo.getCatalogoObservableList().get(0).getNumCopie(), "Il numero di copie deve essere 2.");
        
        //  Incremento fallito (ISBN non esistente)
        boolean nonIncrementato = istanzaCatalogo.incrementaCopie("ISBN-INESISTENTE");
        assertFalse(nonIncrementato, "Dovrebbe ritornare false se l'ISBN non esiste.");
        // Correzione: il valore deve rimanere 2
        assertEquals(2, istanzaCatalogo.getCatalogoObservableList().get(0).getNumCopie(), "Il numero di copie deve rimanere 2.");
    }

    /**
     * @brief Test del metodo eliminaLibro(), della classe Catalogo.
     * Verifica la corretta eliminazione di un libro e la gestione del caso in cui non venga trovato.
     */
    @Test
    public void testEliminaLibro() {
        System.out.println("testEliminaLibro");
        
        istanzaCatalogo.aggiungiLibro(libro1);
        istanzaCatalogo.aggiungiLibro(libro2);
        istanzaCatalogo.aggiungiLibro(libro3);
        istanzaCatalogo.aggiungiLibro(libro4);
        istanzaCatalogo.aggiungiLibro(libro5);
        istanzaCatalogo.aggiungiLibro(libro6);
        istanzaCatalogo.aggiungiLibro(libro7);
        istanzaCatalogo.aggiungiLibro(libro8);
        istanzaCatalogo.aggiungiLibro(libro9);
        istanzaCatalogo.aggiungiLibro(libro10);

        assertEquals(10, istanzaCatalogo.getInventarioLibri().size(), "Dovrebbero esserci 10 libri.");
        
        //  Eliminazione riuscita
        boolean eliminato = istanzaCatalogo.eliminaLibro(libro1.getIsbn());
        assertTrue(eliminato, "Dovrebbe ritornare true per l'eliminazione riuscita.");
        assertEquals(9, istanzaCatalogo.getInventarioLibri().size(), "Dovrebbe esserci 1 libro rimasto.");
        
        //  Eliminazione fallita (ISBN non esistente)
        boolean nonEliminato = istanzaCatalogo.eliminaLibro("ISBN-INESISTENTE");
        assertFalse(nonEliminato, "Dovrebbe ritornare false se l'ISBN non esiste.");
        assertEquals(9, istanzaCatalogo.getInventarioLibri().size(), "Il numero di libri deve rimanere 1.");
    }

   
   /**
     * @brief Test del metodo modificaLibro(), della classe Catalogo.
     * Verifica la modifica dei dettagli di un libro, la gestione del riordinamento nel TreeSet e la
     * non-modifica dell'ordinamento quando si aggiornano solo campi non chiave (es. copie).
     */
    @Test
    public void testModificaLibro() {
        System.out.println("testModificaLibro");
        
        // Aggiungo i libri in un ordine noto per controllare l'ordinamento (libro2 viene prima di libro1 per titolo)
        istanzaCatalogo.aggiungiLibro(libro1); 
        istanzaCatalogo.aggiungiLibro(libro2); 
        istanzaCatalogo.aggiungiLibro(libro3);
        istanzaCatalogo.aggiungiLibro(libro4);
        istanzaCatalogo.aggiungiLibro(libro5);
        istanzaCatalogo.aggiungiLibro(libro6);
        istanzaCatalogo.aggiungiLibro(libro7);
        istanzaCatalogo.aggiungiLibro(libro8);
        istanzaCatalogo.aggiungiLibro(libro9);
        istanzaCatalogo.aggiungiLibro(libro10);
       
        assertEquals(libro10.getTitolo(), istanzaCatalogo.getCatalogoObservableList().get(0).getTitolo());
        
        //  Modifica riuscita SENZA cambiare il titolo o l'autore (non si altera l'ordinamento)
        LocalDate nuovaData = LocalDate.of(2010, 1, 1);
        String isbnLibro2 = libro2.getIsbn();
        
        // Modifico solo copie e anno di pubblicazione per libro2 ("Il Nome della Rosa")
        boolean modificatoSoloDati = istanzaCatalogo.modificaLibro(isbnLibro2, libro2.getTitolo(), libro2.getAutore(), nuovaData, 10);
        assertTrue(modificatoSoloDati, "La modifica di sole copie/anno dovrebbe riuscire.");
        
        // Trova libro2 in base al suo ISBN per asserire sui suoi campi modificati
        Libro libroModificato = null;
        for (Libro l : istanzaCatalogo.getCatalogoObservableList()) {
            if (l.getIsbn().equals(isbnLibro2)) {
                libroModificato = l;
                break;
            }
        }
        assertNotNull(libroModificato, "Il libro modificato deve esistere.");
        
        assertEquals(10, libroModificato.getNumCopie(), "Il numero di copie deve essere aggiornato a 10.");
        assertEquals(nuovaData, libroModificato.getAnnoPb(), "L'anno di pubblicazione deve essere aggiornato.");
        
        // VERIFICA: L'ordinamento deve essere rimasto invariato (libro10 in posizione 0)
        assertEquals(libro10.getTitolo(), istanzaCatalogo.getCatalogoObservableList().get(0).getTitolo(), "Il libro con il titolo minore deve rimanere in posizione 0.");
        assertEquals(10, istanzaCatalogo.getInventarioLibri().size(), "Il numero di libri non deve cambiare.");

        // 2. Modifica con CAMBIO titolo (forza il riordinamento: da "Il Nome della Rosa" a "Zoro...")
        String nuovoTitolo = "Zoro: L'inizio";
        
        istanzaCatalogo.modificaLibro(isbnLibro2, nuovoTitolo, libroModificato.getAutore(), nuovaData, 10);
        
        // VERIFICA: Ora libro2 ("Zoro: L'inizio") è l'ultimo. Il libro10 ("Critica...") è il primo.
        Libro libroRimodificato = null;
        for (Libro l : istanzaCatalogo.getCatalogoObservableList()) {
            if (l.getIsbn().equals(isbnLibro2)) {
                libroRimodificato = l;
                break;
            }
        }
        
        assertEquals(nuovoTitolo, libroRimodificato.getTitolo(), "Il titolo deve essere aggiornato.");
        assertEquals(libro10.getTitolo(), istanzaCatalogo.getCatalogoObservableList().get(0).getTitolo(), "Il libro con il titolo minore deve essere il primo.");

        //Controllo che la dimensione sia ancora 10
    assertEquals(10, istanzaCatalogo.getInventarioLibri().size(), "Il numero di libri deve rimanere 10 dopo il riordinamento.");
    
        // Modifica fallita (ISBN non esistente)
        boolean nonModificato = istanzaCatalogo.modificaLibro("ISBN-INESISTENTE", "Titolo Fittizio", "Autore Fittizio", nuovaData, 5);
        assertFalse(nonModificato, "Dovrebbe ritornare false se l'ISBN non esiste.");
        
        //  Test con eccezione (nuoveCopie negative)
        assertThrows(IllegalArgumentException.class, () -> {
            istanzaCatalogo.modificaLibro(libro1.getIsbn(), "Titolo", "Autore", nuovaData, -1);
        }, "Dovrebbe lanciare IllegalArgumentException per copie negative.");
    }

    /**
     * @brief Test del metodo getCatalogoObservableList(), della classe Catalogo.
     * Verifica che la lista osservabile restituita contenga tutti gli elementi del TreeSet.
     */
    @Test
    public void testGetCatalogoObservableList() {
        System.out.println("testGetCatalogoObservableList");
        
        istanzaCatalogo.aggiungiLibro(libro1);
        istanzaCatalogo.aggiungiLibro(libro2);
        istanzaCatalogo.aggiungiLibro(libro3);
        istanzaCatalogo.aggiungiLibro(libro4);
        istanzaCatalogo.aggiungiLibro(libro5);
        istanzaCatalogo.aggiungiLibro(libro6);
        istanzaCatalogo.aggiungiLibro(libro7);
        istanzaCatalogo.aggiungiLibro(libro8);
        istanzaCatalogo.aggiungiLibro(libro9);
        istanzaCatalogo.aggiungiLibro(libro10);

        
        ObservableList<Libro> result = istanzaCatalogo.getCatalogoObservableList();
        
        assertNotNull(result, "La lista osservabile non deve essere nulla.");
        assertEquals(10, result.size(), "La lista deve contenere 10 elementi.");
        assertTrue(result.contains(libro1), "La lista deve contenere libro1.");
        assertTrue(result.contains(libro2), "La lista deve contenere libro2.");
        assertTrue(result.contains(libro3), "La lista deve contenere libro3.");
       assertTrue(result.contains(libro4), "La lista deve contenere libro4.");
       assertTrue(result.contains(libro5), "La lista deve contenere libro5.");
       assertTrue(result.contains(libro6), "La lista deve contenere libro6.");
       assertTrue(result.contains(libro7), "La lista deve contenere libro7.");
       assertTrue(result.contains(libro8), "La lista deve contenere libro8.");
       assertTrue(result.contains(libro9), "La lista deve contenere libro9.");
       assertTrue(result.contains(libro10), "La lista deve contenere libro10.");

    }

    /**
     * @brief Test del metodo salvaCSV(), della classe Catalogo.
     * Verifica l'effettiva creazione del file CSV.
     */
    @Test
    public void testSalvaCSV() {
        System.out.println("testSalvaCSV");
        
        istanzaCatalogo.aggiungiLibro(libro3);
        istanzaCatalogo.salvaCSV();
        
        File file = new File(NOME_FILE_TEST);
        assertTrue(file.exists(), "Il file CSV deve esistere dopo salvaCSV().");
        
        // Pulizia
        file.delete();
    }

    /**
     * @brief Test del metodo caricaCSV(), della classe Catalogo.
     * Verifica il caricamento dei dati da un file CSV esistente e la gestione del file non trovato.
     */
    @Test
    public void testCaricaCSV() {
        System.out.println("testCaricaCSV");
        
        File file = new File(NOME_FILE_TEST);
        
        //  Test di caricamento da file non esistente
        istanzaCatalogo.caricaCSV();
        assertTrue(istanzaCatalogo.getInventarioLibri().isEmpty(), "Il catalogo deve essere vuoto se il file non esiste.");
        
        //  Creiamo e salviamo un file di prova
        istanzaCatalogo.aggiungiLibro(libro1); // 1 libro
        istanzaCatalogo.aggiungiLibro(libro2);
        istanzaCatalogo.aggiungiLibro(libro3);
        istanzaCatalogo.aggiungiLibro(libro4);
        istanzaCatalogo.aggiungiLibro(libro5);
        istanzaCatalogo.aggiungiLibro(libro6);
        istanzaCatalogo.aggiungiLibro(libro7);
        istanzaCatalogo.aggiungiLibro(libro8);
        istanzaCatalogo.aggiungiLibro(libro9);
        istanzaCatalogo.aggiungiLibro(libro10);

        istanzaCatalogo.salvaCSV();
        istanzaCatalogo.getInventarioLibri().clear();
        assertTrue(istanzaCatalogo.getInventarioLibri().isEmpty(), "Il catalogo è stato pulito prima del caricamento.");
        
        // Test di caricamento da file esistente
        istanzaCatalogo.caricaCSV();
        assertEquals(10, istanzaCatalogo.getInventarioLibri().size(), "Il catalogo deve contenere 10 libro dopo il caricamento.");
        
        // Pulizia
        file.delete();
    }

    /**
     * @brief Test del metodo toString(), della classe Catalogo.
     * Verifica che la stringa generata sia non vuota e che contenga i dati dei libri inseriti.
     */
    @Test
    public void testToString() {
        System.out.println("testToString");
        
        //  Catalogo vuoto
        String resultVuoto = istanzaCatalogo.toString();
        assertEquals("Il catalogo è vuoto.", resultVuoto, "Il messaggio per il catalogo vuoto non è corretto.");
        
        //  Catalogo con elementi (aggiunti in un ordine diverso per testare l'ordinamento del TreeSet)
        istanzaCatalogo.aggiungiLibro(libro2); 
        istanzaCatalogo.aggiungiLibro(libro1); 
        istanzaCatalogo.aggiungiLibro(libro3);
        istanzaCatalogo.aggiungiLibro(libro4); 
        istanzaCatalogo.aggiungiLibro(libro5);
        istanzaCatalogo.aggiungiLibro(libro7); 
        istanzaCatalogo.aggiungiLibro(libro6);
        istanzaCatalogo.aggiungiLibro(libro8); 
        istanzaCatalogo.aggiungiLibro(libro9); 
        istanzaCatalogo.aggiungiLibro(libro10); 
        
        String resultPieno = istanzaCatalogo.toString();
        assertFalse(resultPieno.isEmpty(), "La stringa non deve essere vuota.");
        assertTrue(resultPieno.contains("===== CATALOGO LIBRI"), "Dovrebbe contenere l'header.");
        
        // Il TreeSet ordina per titolo
        int indiceLibro2 = resultPieno.indexOf(libro2.getTitolo());
        int indiceLibro3 = resultPieno.indexOf(libro3.getTitolo());
        int indiceLibro1 = resultPieno.indexOf(libro1.getTitolo()); 
        int indiceLibro4 = resultPieno.indexOf(libro4.getTitolo());
        int indiceLibro5 = resultPieno.indexOf(libro5.getTitolo());
        int indiceLibro7 = resultPieno.indexOf(libro7.getTitolo());
        int indiceLibro6 = resultPieno.indexOf(libro6.getTitolo());
        int indiceLibro8 = resultPieno.indexOf(libro8.getTitolo());
        int indiceLibro9 = resultPieno.indexOf(libro9.getTitolo());
        int indiceLibro10 = resultPieno.indexOf(libro10.getTitolo());
        

        
        assertTrue(indiceLibro2 < indiceLibro1, "L'ordinamento per titolo non è rispettato.");
    }
}