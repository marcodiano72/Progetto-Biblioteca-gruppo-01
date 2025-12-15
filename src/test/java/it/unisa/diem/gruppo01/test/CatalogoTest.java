/**
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



public class CatalogoTest {
    
    // Variabili di istanza per i test
    private Libro libro1;
    private Libro libro2;
    private Libro libro3;
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
        libro1 = new Libro("978-8861928090", "Il Signore degli Anelli", "J.R.R. Tolkien", data, 5);
        libro2 = new Libro("978-0743273565", "Il Codice Da Vinci", "Dan Brown", data, 3);
        libro3 = new Libro("978-1503254245", "1984", "George Orwell", data, 1);
        
        // Otteniamo l'istanza Singleton e la puliamo
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
        
        // Esegui il salvataggio
        Catalogo.salvaDati();
        
        File file = new File(NOME_FILE_TEST);
        assertTrue(file.exists(), "Il file CSV deve essere creato dopo salvaDati().");
        
        // Puliamo l'inventario in memoria per testare il caricamento
        istanzaCatalogo.getInventarioLibri().clear();
        
        // Ricarichiamo per verificare che il salvataggio sia andato a buon fine
        istanzaCatalogo.caricaCSV();
        
        assertEquals(1, istanzaCatalogo.getInventarioLibri().size(), "Dopo il salvataggio e ricaricamento, ci deve essere 1 libro.");
        
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
        boolean aggiuntoNuovo = istanzaCatalogo.aggiungiLibro(libro1);
        assertTrue(aggiuntoNuovo, "Dovrebbe ritornare true per un nuovo libro.");
        assertEquals(1, istanzaCatalogo.getInventarioLibri().size(), "Ci deve essere 1 libro.");
        
        // Tentativo di aggiungere un libro con lo stesso ISBN (dovrebbe incrementare le copie)
        Libro libro1b = new Libro("978-8861928090", "Titolo Diverso", "Autore Diverso", LocalDate.of(2001, 1, 1), 2);
        boolean aggiornato = istanzaCatalogo.aggiungiLibro(libro1b);
        assertFalse(aggiornato, "Dovrebbe ritornare false per un libro esistente.");
        assertEquals(1, istanzaCatalogo.getInventarioLibri().size(), "Il numero di elementi deve rimanere 1.");
        
        Libro libroTrovato = istanzaCatalogo.getCatalogoObservableList().get(0);
        assertEquals(7, libroTrovato.getNumCopie(), "Il numero di copie deve essere incrementato (5 + 2 = 7).");
    }

    /**
     * @brief Test del metodo incrementaCopie(), della classe Catalogo.
     * Verifica l'incremento delle copie di un libro dato l'ISBN e la gestione del caso di libro non trovato.
     */
    @Test
    public void testIncrementaCopie() {
        System.out.println("testIncrementaCopie");
        
        istanzaCatalogo.aggiungiLibro(libro1); // Copie iniziali: 5
        
        //  Incremento riuscito
        boolean incrementato = istanzaCatalogo.incrementaCopie(libro1.getIsbn());
        assertTrue(incrementato, "Dovrebbe ritornare true per l'incremento riuscito.");
        assertEquals(6, istanzaCatalogo.getCatalogoObservableList().get(0).getNumCopie(), "Il numero di copie deve essere 6.");
        
        //  Incremento fallito (ISBN non esistente)
        boolean nonIncrementato = istanzaCatalogo.incrementaCopie("ISBN-INESISTENTE");
        assertFalse(nonIncrementato, "Dovrebbe ritornare false se l'ISBN non esiste.");
        assertEquals(6, istanzaCatalogo.getCatalogoObservableList().get(0).getNumCopie(), "Il numero di copie deve rimanere 6.");
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
        assertEquals(2, istanzaCatalogo.getInventarioLibri().size(), "Dovrebbero esserci 2 libri.");
        
        // 1. Eliminazione riuscita
        boolean eliminato = istanzaCatalogo.eliminaLibro(libro1.getIsbn());
        assertTrue(eliminato, "Dovrebbe ritornare true per l'eliminazione riuscita.");
        assertEquals(1, istanzaCatalogo.getInventarioLibri().size(), "Dovrebbe esserci 1 libro rimasto.");
        
        // 2. Eliminazione fallita (ISBN non esistente)
        boolean nonEliminato = istanzaCatalogo.eliminaLibro("ISBN-INESISTENTE");
        assertFalse(nonEliminato, "Dovrebbe ritornare false se l'ISBN non esiste.");
        assertEquals(1, istanzaCatalogo.getInventarioLibri().size(), "Il numero di libri deve rimanere 1.");
    }

    /**
     * @brief Test del metodo modificaLibro(), della classe Catalogo.
     * Verifica la modifica dei dettagli di un libro e la gestione del riordinamento nel TreeSet.
     */
   /**
     * @brief Test del metodo modificaLibro(), della classe Catalogo.
     * Verifica la modifica dei dettagli di un libro, la gestione del riordinamento nel TreeSet e la
     * non-modifica dell'ordinamento quando si aggiornano solo campi non chiave (es. copie).
     */
    @Test
    public void testModificaLibro() {
        System.out.println("testModificaLibro");
        
        // Aggiungo i libri in un ordine noto per controllare l'ordinamento (libro2 viene prima di libro1 per titolo)
        istanzaCatalogo.aggiungiLibro(libro1); // "Il Signore degli Anelli"
        istanzaCatalogo.aggiungiLibro(libro2); // "Il Codice Da Vinci"
        
        // La lista ordinata è: libro2, libro1
        assertEquals(libro2.getTitolo(), istanzaCatalogo.getCatalogoObservableList().get(0).getTitolo());
        
        // 1. Modifica riuscita SENZA cambiare il titolo o l'autore (non si altera l'ordinamento)
        LocalDate nuovaData = LocalDate.of(2010, 1, 1);
        String isbnLibro2 = libro2.getIsbn();
        
        // Modifico solo copie e anno di pubblicazione
        boolean modificatoSoloDati = istanzaCatalogo.modificaLibro(isbnLibro2, libro2.getTitolo(), libro2.getAutore(), nuovaData, 10);
        assertTrue(modificatoSoloDati, "La modifica di sole copie/anno dovrebbe riuscire.");
        
        Libro libroModificato = istanzaCatalogo.getCatalogoObservableList().get(0); // Deve essere ancora in posizione 0
        assertEquals(10, libroModificato.getNumCopie(), "Il numero di copie deve essere aggiornato a 10.");
        assertEquals(nuovaData, libroModificato.getAnnoPb(), "L'anno di pubblicazione deve essere aggiornato.");
        
        // VERIFICA: L'ordinamento deve essere rimasto invariato (libro2 in posizione 0)
        assertEquals(libro2.getTitolo(), istanzaCatalogo.getCatalogoObservableList().get(0).getTitolo(), "Il libro deve mantenere la posizione se il titolo non cambia.");
        assertEquals(2, istanzaCatalogo.getInventarioLibri().size(), "Il numero di libri non deve cambiare.");

        // 2. Modifica con CAMBIO titolo (forza il riordinamento: da "Il Codice Da Vinci" a "Zoro...")
        String nuovoTitolo = "Zoro: L'inizio";
        
        istanzaCatalogo.modificaLibro(isbnLibro2, nuovoTitolo, libroModificato.getAutore(), nuovaData, 10);
        
        // VERIFICA: Ora la lista ordinata è: libro1 ("Il Signore degli Anelli"), libro2 ("Zoro: L'inizio")
        Libro libroRimodificato = istanzaCatalogo.getCatalogoObservableList().get(1); // Ora è l'ultimo
        assertEquals(nuovoTitolo, libroRimodificato.getTitolo(), "Il titolo deve essere aggiornato.");
        assertEquals(libro1.getTitolo(), istanzaCatalogo.getCatalogoObservableList().get(0).getTitolo(), "Il libro che era secondo ora deve essere il primo.");

        // 3. Modifica fallita (ISBN non esistente)
        boolean nonModificato = istanzaCatalogo.modificaLibro("ISBN-INESISTENTE", "Titolo Fittizio", "Autore Fittizio", nuovaData, 5);
        assertFalse(nonModificato, "Dovrebbe ritornare false se l'ISBN non esiste.");
        
        // 4. Test con eccezione (nuoveCopie negative)
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
        
        ObservableList<Libro> result = istanzaCatalogo.getCatalogoObservableList();
        
        assertNotNull(result, "La lista osservabile non deve essere nulla.");
        assertEquals(2, result.size(), "La lista deve contenere 2 elementi.");
        assertTrue(result.contains(libro1), "La lista deve contenere libro1.");
        assertTrue(result.contains(libro2), "La lista deve contenere libro2.");
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
        istanzaCatalogo.salvaCSV();
        istanzaCatalogo.getInventarioLibri().clear();
        assertTrue(istanzaCatalogo.getInventarioLibri().isEmpty(), "Il catalogo è stato pulito prima del caricamento.");
        
        // Test di caricamento da file esistente
        istanzaCatalogo.caricaCSV();
        assertEquals(1, istanzaCatalogo.getInventarioLibri().size(), "Il catalogo deve contenere 1 libro dopo il caricamento.");
        
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
        istanzaCatalogo.aggiungiLibro(libro2); // Titolo: Il Codice Da Vinci
        istanzaCatalogo.aggiungiLibro(libro1); // Titolo: Il Signore degli Anelli
        
        String resultPieno = istanzaCatalogo.toString();
        assertFalse(resultPieno.isEmpty(), "La stringa non deve essere vuota.");
        assertTrue(resultPieno.contains("===== CATALOGO LIBRI"), "Dovrebbe contenere l'header.");
        
        // Il TreeSet ordina per titolo, quindi "Il Codice Da Vinci" dovrebbe venire prima di "Il Signore degli Anelli"
        int indiceLibro2 = resultPieno.indexOf(libro2.getTitolo());
        int indiceLibro1 = resultPieno.indexOf(libro1.getTitolo());
        
        assertTrue(indiceLibro2 < indiceLibro1, "L'ordinamento per titolo non è rispettato.");
    }
    
}