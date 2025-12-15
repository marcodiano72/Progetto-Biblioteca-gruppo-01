/**
 * @file CatalogoTest.java
 * @brief Classe di test unitario per la classe Catalogo.
 *
 * Questa classe è progettata per testare la classe Catalogo,
 * vhe gestisce l'inventario dei libri,inclusa la persistenza su file CSV
 * e l'ordinamento tramite TreeSet.
 *@author gruppo01
 *@version 1.0
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
 * @brief Classe di test per la classe Catalogo.
 */

public class CatalogoTest {
    
    private final static String NOME_FILE_CSV = "Lista_Libri.csv"; ///< Nome del file CSV utilizzato per la persistenza del catalogo nei test. 
    
    /**
     * @brief Metodo ausiliario che utilizza la Reflection per resettare
     * l'istanza di Catalogo.
     * Questo è essenziale per garantire che ogni test ottenga una nuova istanza
     * di Catalogo, permettendo di testare il caricamento iniziale (caricaCSV)
    */
    
    private static void resetSingleton() {
        try {
            //Accede al campo statico 'istanza' (private) nella classe Catalogo
            java.lang.reflect.Field field = Catalogo.class.getDeclaredField("istanza");
            field.setAccessible(true);
            field.set(null, null); ///<// Imposta il campo 'istanza' a null
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Impossibile resettare l'istanza Singleton di Catalogo.");
        }
    }
    
    
    /**
     * @brief Metodo ausiliario per scrivere contenuti specifici nel file CSV di test.
     * Utililizzato per simulare file malformati e testare di caricaCSV.
     * @param[in] contenuto
     */
    
    private void scriviContenutoCSV(String contenuto) {
        try (java.io.FileWriter fw = new java.io.FileWriter(NOME_FILE_CSV)) {
            fw.write(contenuto);
        } catch (java.io.IOException e) {
            fail("Errore durante la scrittura del file CSV di test: " + e.getMessage());
        }
    }
    
    /**
    * @brief Metodo che prepara l'ambiente prima di eseguire tutti i test: elimina il file CSV.
    */
    
    @BeforeAll
    public static void setUpClass() {
        new File(NOME_FILE_CSV).delete();
    }
    
    
    /**
    * @brief Metodo che pulisce l'ambiente dopo l'esecuzione di tutti i test: elimina il file CSV.
    */
    
    @AfterAll
    public static void tearDownClass() {
        new File(NOME_FILE_CSV).delete();
    }
    
    
    /**
    *@brief Metodo che pulisce l'ambiente prima di ogni test: resetta il Singleton ed elimina il file CSV.
    */
    
    @BeforeEach
    public void setUp() {
        resetSingleton();
        new File(NOME_FILE_CSV).delete();
    }
    
    /**
    *@brief Metodo che pulisce l'ambiente dopo ogni test: resetta il Singleton ed elimina il file CSV.
    */
    
    @AfterEach
    public void tearDown() {
        resetSingleton();
        new File(NOME_FILE_CSV).delete();
    }

    /**
     * @brief Metodo che effettua il test del metodo getIstanza(),
     */
    
    @Test
    public void testGetIstanza_Singleton() {
        Catalogo istanza1 = Catalogo.getIstanza();
        Catalogo istanza2 = Catalogo.getIstanza();
        
        // Verifica che siano lo stesso oggetto (stesso indirizzo di memoria)
        assertSame(istanza1, istanza2);
        assertNotNull(istanza1);
    }

    /**
     * @brief Metodo che testa  getInventarioLibri(), verificando l'ordinamento primario per titolo
     * fornito dalla struttura del treeSet
     */
    
    @Test
    public void testGetInventarioLibri_StrutturaDatiEOrdinamentoTitolo() {
        Catalogo istanza = Catalogo.getIstanza();
        TreeSet<Libro> inventario = istanza.getInventarioLibri();
        
        Libro libroB = new Libro("ISBN-B", "B-Titolo", "Autore", LocalDate.of(2000, 1, 1), 1);
        Libro libroA = new Libro("ISBN-A", "A-Titolo", "Autore", LocalDate.of(2000, 1, 1), 1);
        
        istanza.aggiungiLibro(libroB);
        istanza.aggiungiLibro(libroA);
        
        // Verifica che il libro con titolo che inizia per 'A' sia il primo (ordinamento alfabetico)
        assertEquals(libroA, inventario.first());
    }
    
    /**
    * @brief Metodo che fa il test del comparatore (Libro.compareTo) in caso di titoli identici, dove l'ordinamento
    * secondario dovrebbe essere basato sull'ISBN
    */
    
    @Test
    public void testLibroComparator_TitoliIdentici_OrdinamentoISBN() {
        Catalogo istanza = Catalogo.getIstanza();
        TreeSet<Libro> inventario = istanza.getInventarioLibri();
        
        // Stesso Titolo, ISBN diversi (Z prima di A alfabeticamente)
        Libro libroC1 = new Libro("ISBN-Z", "Titolo Comune", "Autore", LocalDate.of(2000, 1, 1), 1);
        Libro libroC2 = new Libro("ISBN-A", "Titolo Comune", "Autore", LocalDate.of(2000, 1, 1), 1);
        
        istanza.aggiungiLibro(libroC1); 
        istanza.aggiungiLibro(libroC2); 
        
        // Verifica che il libro con ISBN 'A' sia il primo
        assertEquals(libroC2, inventario.first()); // ISBN-A prima di ISBN-Z
    }

    /**
     * @brief Metodo che fa il test di aggiungiLibro(), verificando il caso in cui il libro
     * esista già (stesso ISBN), e debbano essere incrementate le copie
     */
    
    @Test
    public void testAggiungiLibro_IncrementaCopie() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libroIniziale = new Libro("978-002", "Fantasy", "Bianchi", LocalDate.of(2022, 1, 1), 5);
        Libro libroAggiornato = new Libro("978-002", "Fantasy", "Bianchi", LocalDate.of(2022, 1, 1), 3);
        
        istanza.aggiungiLibro(libroIniziale);
        // L'aggiunta restituisce false se il libro esisteva già
        assertFalse(istanza.aggiungiLibro(libroAggiornato));
        
        Libro libroRisultato = istanza.getInventarioLibri().first();
        // Verifica che il numero di copie sia la somma (5 + 3 = 8)
        assertEquals(8, libroRisultato.getNumCopie());
    }

    /**
     *  @brief Metodo che fa il test del metodo eliminaLibro(), verificando la rimozione di un elemento esistente.
     */
    @Test
    public void testEliminaLibro_Esistente() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro = new Libro("978-003", "Science", "Verdi", LocalDate.of(2021, 1, 1), 1);
        istanza.aggiungiLibro(libro);
        
        // Verifica che l'eliminazione abbia avuto success
        assertTrue(istanza.eliminaLibro("978-003"));
        assertTrue(istanza.getInventarioLibri().isEmpty());
    }
    
    /**
     *@brief Metodo che fa il test del metodo eliminaLibro(),
     * verificando il caso in cui il libro non esista.
    */
    
    @Test
    public void testEliminaLibro_NonEsistente() {
        Catalogo istanza = Catalogo.getIstanza();
        assertFalse(istanza.eliminaLibro("978-999"));
    }

    
    /**
     * @brief Metodo di test su modifica libro. 
     *Copre il caso in cui non c'è bisogno di rimuovere/riaggiungere. 
     */
    
    @Test
    public void testModificaLibro_SoloCopie_NoRiordinamento_CoperturaIF() {
        System.out.println("Test modificaLibro: Modifica solo copie (copertura del salto IF).");

        Catalogo istanza = Catalogo.getIstanza();
        Libro libro = new Libro("978-004", "Romance", "Neri", LocalDate.of(2020, 1, 1), 5);
        istanza.aggiungiLibro(libro);
        
        // Modifica SOLO copie (Titolo e Autore restano Romance e Neri)
        assertTrue(istanza.modificaLibro("978-004", "Romance", "Neri", LocalDate.of(2020, 1, 1), 15));
        
        Libro libroModificato = istanza.getInventarioLibri().first();
        // Verifica che il Titolo e l'Autore siano rimasti identici
        assertEquals("Romance", libroModificato.getTitolo()); 
        assertEquals("Neri", libroModificato.getAutore());
        // Verifica che le copie siano cambiate
        assertEquals(15, libroModificato.getNumCopie()); 
       
    }
    
    /**
    *@brief Metodo che fa il test di modificaLibro(): 
    * caso in cui viene modificato il titolo. Il TreeSet deve riordinare l'elemento.
    */
    
    @Test
    public void testModificaLibro_TitoloCambiato_Riordinamento() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro1 = new Libro("978-005", "Zebra", "Autore", LocalDate.of(2000, 1, 1), 1);
        Libro libro2 = new Libro("978-006", "Alpha", "Autore", LocalDate.of(2000, 1, 1), 1);
        istanza.aggiungiLibro(libro1);
        istanza.aggiungiLibro(libro2); 
        
        // Modifica il titolo da "Zebra" a "Apple" (cambia l'ordinamento)
        assertTrue(istanza.modificaLibro("978-005", "Apple", "Autore", LocalDate.of(2000, 1, 1), 1));
        
        // L'elemento con titolo "Alpha" (prima di "Apple") dovrebbe restare il primo
        assertEquals("Alpha", istanza.getInventarioLibri().first().getTitolo(), "Dopo la modifica (Zebra -> Apple), 'Alpha' dovrebbe restare il primo.");
    }
    
    
    /**
     * @brief Metodo che fa il test di modificaLibro(): caso in cui viene modificato l'autore.
     * Poiché l'Autore non è il campo di ordinamento primario (è secondario in caso di stesso titolo/ISBN),
     * questo assicura che il blocco di riordinamento venga attivato correttamente se l'Autore è cambiato.
    */
    
    @Test
    public void testModificaLibro_AutoreCambiato_Riaggiunta() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro1 = new Libro("978-A", "Titolo", "Autore-Z", LocalDate.of(2000, 1, 1), 1);
        istanza.aggiungiLibro(libro1);
        
        // Modifica Autore (Autore Cambiato)
        assertTrue(istanza.modificaLibro("978-A", "Titolo", "Autore-A", LocalDate.of(2000, 1, 1), 1));

        assertEquals("Autore-A", istanza.getInventarioLibri().first().getAutore());
    }

    /**
     * @brief Metodo che fa il test di modificaLibro(): caso in cui si tenta di impostare un numero di copie negativo,
     * verificando che venga lanciata l'eccezione corretta (IllegalArgumentException).
     */
    
    @Test
    public void testModificaLibro_CopieNegative_Eccezione() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro = new Libro("978-007", "Exception", "Test", LocalDate.of(2020, 1, 1), 5);
        istanza.aggiungiLibro(libro);
        
        // Ci si aspetta IllegalArgumentException quando le copie sono -1
        assertThrows(IllegalArgumentException.class, 
                     () -> istanza.modificaLibro("978-007", "Exception", "Test", LocalDate.of(2020, 1, 1), -1));
    }


    /**
     * @brief Metodo che fa il test di getCatalogoObservableList(), verificando che il contenuto del TreeSet
     * sia correttamente copiato in una ObservableList
     */
    
    @Test
    public void testGetCatalogoObservableList() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro1 = new Libro("ISBN-1", "Titolo 1", "Autore", LocalDate.of(2000, 1, 1), 1);
        istanza.aggiungiLibro(libro1);
        
        ObservableList<Libro> list = istanza.getCatalogoObservableList();
        
        assertEquals(1, list.size());
    }
    
    /**
     * @brief Metodo che fa il test  di salvaCSV() e caricaCSV() per verificare la corretta persistenza e ripristino dei dati.
     */
    
    @Test
    public void testSalvaECaricaCSV_Successo() {
        Catalogo istanzaOriginale = Catalogo.getIstanza();
        Libro libroA = new Libro("978-100", "Z-Test", "Autore A", LocalDate.of(2024, 1, 1), 5);
        istanzaOriginale.aggiungiLibro(libroA);
        
        istanzaOriginale.salvaCSV();
        
        resetSingleton();
        // Distrugge l'istanza originale
        // La nuova istanza tenterà il caricamento da file (caricaCSV)

        Catalogo istanzaCaricata = Catalogo.getIstanza();
        
        // Verifica che i dati siano stati caricati correttamente
        assertEquals(1, istanzaCaricata.getInventarioLibri().size());
        assertEquals("Z-Test", istanzaCaricata.getInventarioLibri().first().getTitolo());
    }
    
    
    /**
    * @brief Test di robustezza: verifica che caricaCSV() gestisca e ignori righe malformate
    * (es. dati mancanti o non numerici), caricando solo i dati validi
    */
    
    @Test
    public void testCaricaCSV_RigheMalformate() {
        // Righe malformate per coprire NumberFormatException e ArrayIndexOutOfBoundsException
        String contenuto = 
              "Elenco Libri\n"
            + "Titolo;Autore;ISBN;Anno pb;Num_Copie\n"
            + "Titolo A;Autore A;978-001;2020-01-01;5\n"      
            + "Titolo B;Autore B;978-002\n"                  // Riga con pochi campi -> ArrayIndexOutOfBoundsException
            + "Titolo C;Autore C;978-003;2022-01-01;XYZ\n"     // Campo non numerico per copie -> NumberFormatException
            + "Titolo D;Autore D;978-004;2023-01-01;1\n";      // Riga valida
        
        scriviContenutoCSV(contenuto);
        
        Catalogo istanza = Catalogo.getIstanza();
        
        // Ci si aspetta che solo le due righe valide (A e D) vengano caricate
        assertEquals(2, istanza.getInventarioLibri().size());
    }

    /**
     *@brief Metodo che testa salvaDati() (chiamabile anche se l'istanza è stata solo creata).
     */
    @Test
    public void testSalvaDati_IstanzaPresente() {
        Catalogo.getIstanza(); //crea l'istanza
        Catalogo.salvaDati();//chiama il salvataggio
        
        File file = new File(NOME_FILE_CSV);
        // Il salvataggio non dovrebbe avvenire, quindi il file non esiste
        assertTrue(file.exists());
    }
    
    /**
    * @brief Metodo che testa salvaDati() quando l'istanza non è mai stata creata.
    */
    
    @Test
    public void testSalvaDati_IstanzaNull() {
        Catalogo.salvaDati();
        
        File file = new File(NOME_FILE_CSV);
        // Il salvataggio non dovrebbe avvenire, quindi il file non esiste
        assertFalse(file.exists());
    }
    

    /**
     * @brief Metodo che testa la toString() per un catalogo vuoto.
     */
    @Test
    public void testToString_CatalogoVuoto() {
        Catalogo istanza = Catalogo.getIstanza();
        assertEquals("Il catalogo è vuoto.", istanza.toString());
    }

    /**
    * @brief Metodo che testa la toString() per un catalogo con elementi
    */
    
    @Test
    public void testToString_CatalogoPieno() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libroA = new Libro("ISBN-A", "Titolo A", "Autore", LocalDate.of(2000, 1, 1), 1);
        istanza.aggiungiLibro(libroA);
        
        String result = istanza.toString();
        
        assertTrue(result.contains("===== CATALOGO LIBRI"));
        assertTrue(result.contains("Titolo A"));
    }
}