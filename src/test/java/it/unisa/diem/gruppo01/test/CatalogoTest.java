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
 * Classe di test per la classe Catalogo (mirata al 100% di copertura).
 */
public class CatalogoTest {
    
    private final static String NOME_FILE_CSV = "Lista_Libri.csv"; 
    
    private static void resetSingleton() {
        try {
            java.lang.reflect.Field field = Catalogo.class.getDeclaredField("istanza");
            field.setAccessible(true);
            field.set(null, null); 
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Impossibile resettare l'istanza Singleton di Catalogo.");
        }
    }
    
    private void scriviContenutoCSV(String contenuto) {
        try (java.io.FileWriter fw = new java.io.FileWriter(NOME_FILE_CSV)) {
            fw.write(contenuto);
        } catch (java.io.IOException e) {
            fail("Errore durante la scrittura del file CSV di test: " + e.getMessage());
        }
    }
    
    @BeforeAll
    public static void setUpClass() {
        new File(NOME_FILE_CSV).delete();
    }
    
    @AfterAll
    public static void tearDownClass() {
        new File(NOME_FILE_CSV).delete();
    }
    
    @BeforeEach
    public void setUp() {
        resetSingleton();
        new File(NOME_FILE_CSV).delete();
    }
    
    @AfterEach
    public void tearDown() {
        resetSingleton();
        new File(NOME_FILE_CSV).delete();
    }

    /**
     * Test of getIstanza method, of class Catalogo (Singleton).
     */
    @Test
    public void testGetIstanza_Singleton() {
        Catalogo istanza1 = Catalogo.getIstanza();
        Catalogo istanza2 = Catalogo.getIstanza();
        
        assertSame(istanza1, istanza2);
        assertNotNull(istanza1);
    }

    /**
     * Test of getInventarioLibri method, of class Catalogo.
     */
    @Test
    public void testGetInventarioLibri_StrutturaDatiEOrdinamentoTitolo() {
        Catalogo istanza = Catalogo.getIstanza();
        TreeSet<Libro> inventario = istanza.getInventarioLibri();
        
        Libro libroB = new Libro("ISBN-B", "B-Titolo", "Autore", LocalDate.of(2000, 1, 1), 1);
        Libro libroA = new Libro("ISBN-A", "A-Titolo", "Autore", LocalDate.of(2000, 1, 1), 1);
        
        istanza.aggiungiLibro(libroB);
        istanza.aggiungiLibro(libroA);
        
        assertEquals(libroA, inventario.first());
    }
    
    /**
     * Test del comparatore per titoli identici (ordina per ISBN).
     */
    @Test
    public void testLibroComparator_TitoliIdentici_OrdinamentoISBN() {
        Catalogo istanza = Catalogo.getIstanza();
        TreeSet<Libro> inventario = istanza.getInventarioLibri();
        
        Libro libroC1 = new Libro("ISBN-Z", "Titolo Comune", "Autore", LocalDate.of(2000, 1, 1), 1);
        Libro libroC2 = new Libro("ISBN-A", "Titolo Comune", "Autore", LocalDate.of(2000, 1, 1), 1);
        
        istanza.aggiungiLibro(libroC1); 
        istanza.aggiungiLibro(libroC2); 
        
        assertEquals(libroC2, inventario.first()); // ISBN-A prima di ISBN-Z
    }

    /**
     * Test of aggiungiLibro method, of class Catalogo.
     */
    @Test
    public void testAggiungiLibro_IncrementaCopie() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libroIniziale = new Libro("978-002", "Fantasy", "Bianchi", LocalDate.of(2022, 1, 1), 5);
        Libro libroAggiornato = new Libro("978-002", "Fantasy", "Bianchi", LocalDate.of(2022, 1, 1), 3);
        
        istanza.aggiungiLibro(libroIniziale);
        assertFalse(istanza.aggiungiLibro(libroAggiornato));
        
        Libro libroRisultato = istanza.getInventarioLibri().first();
        assertEquals(8, libroRisultato.getNumCopie());
    }

    /**
     * Test of eliminaLibro method, of class Catalogo.
     */
    @Test
    public void testEliminaLibro_Esistente() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro = new Libro("978-003", "Science", "Verdi", LocalDate.of(2021, 1, 1), 1);
        istanza.aggiungiLibro(libro);
        
        assertTrue(istanza.eliminaLibro("978-003"));
        assertTrue(istanza.getInventarioLibri().isEmpty());
    }
    
    @Test
    public void testEliminaLibro_NonEsistente() {
        Catalogo istanza = Catalogo.getIstanza();
        assertFalse(istanza.eliminaLibro("978-999"));
    }

    /**
     * Test of modificaLibro method: Copre il caso in cui non c'è bisogno di rimuovere/riaggiungere.
     * Questo è cruciale per la copertura del 100% del blocco IF.
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
        // L'asserzione di successo implica che i blocchi if(titoloCambiato || autoreCambiato) sono stati saltati.
    }
    
    @Test
    public void testModificaLibro_TitoloCambiato_Riordinamento() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro1 = new Libro("978-005", "Zebra", "Autore", LocalDate.of(2000, 1, 1), 1);
        Libro libro2 = new Libro("978-006", "Alpha", "Autore", LocalDate.of(2000, 1, 1), 1);
        istanza.aggiungiLibro(libro1);
        istanza.aggiungiLibro(libro2); 
        
        // Modifica Zebra -> Apple (Titolo Cambiato)
        assertTrue(istanza.modificaLibro("978-005", "Apple", "Autore", LocalDate.of(2000, 1, 1), 1));
        
        // Apple deve essere il primo.
        assertEquals("Alpha", istanza.getInventarioLibri().first().getTitolo(), "Dopo la modifica (Zebra -> Apple), 'Alpha' dovrebbe restare il primo.");
    }
    
    @Test
    public void testModificaLibro_AutoreCambiato_Riaggiunta() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro1 = new Libro("978-A", "Titolo", "Autore-Z", LocalDate.of(2000, 1, 1), 1);
        istanza.aggiungiLibro(libro1);
        
        // Modifica Autore (Autore Cambiato)
        assertTrue(istanza.modificaLibro("978-A", "Titolo", "Autore-A", LocalDate.of(2000, 1, 1), 1));

        assertEquals("Autore-A", istanza.getInventarioLibri().first().getAutore());
    }

    @Test
    public void testModificaLibro_CopieNegative_Eccezione() {
        Catalogo istanza = Catalogo.getIstanza();
        Libro libro = new Libro("978-007", "Exception", "Test", LocalDate.of(2020, 1, 1), 5);
        istanza.aggiungiLibro(libro);
        
        assertThrows(IllegalArgumentException.class, 
                     () -> istanza.modificaLibro("978-007", "Exception", "Test", LocalDate.of(2020, 1, 1), -1));
    }


    /**
     * Test of getCatalogoObservableList method, of class Catalogo.
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
     * Test of salvaCSV and caricaCSV methods, of class Catalogo.
     */
    @Test
    public void testSalvaECaricaCSV_Successo() {
        Catalogo istanzaOriginale = Catalogo.getIstanza();
        Libro libroA = new Libro("978-100", "Z-Test", "Autore A", LocalDate.of(2024, 1, 1), 5);
        istanzaOriginale.aggiungiLibro(libroA);
        
        istanzaOriginale.salvaCSV();
        
        resetSingleton();
        Catalogo istanzaCaricata = Catalogo.getIstanza();
        
        assertEquals(1, istanzaCaricata.getInventarioLibri().size());
        assertEquals("Z-Test", istanzaCaricata.getInventarioLibri().first().getTitolo());
    }
    
    @Test
    public void testCaricaCSV_RigheMalformate() {
        // Righe malformate per coprire NumberFormatException e ArrayIndexOutOfBoundsException
        String contenuto = 
              "Elenco Libri\n"
            + "Titolo;Autore;ISBN;Anno pb;Num_Copie\n"
            + "Titolo A;Autore A;978-001;2020-01-01;5\n"      
            + "Titolo B;Autore B;978-002\n"                  // ArrayIndexOutOfBoundsException
            + "Titolo C;Autore C;978-003;2022-01-01;XYZ\n"     // NumberFormatException
            + "Titolo D;Autore D;978-004;2023-01-01;1\n";      
        
        scriviContenutoCSV(contenuto);
        
        Catalogo istanza = Catalogo.getIstanza();
        
        assertEquals(2, istanza.getInventarioLibri().size());
    }

    /**
     * Test of salvaDati method, of class Catalogo.
     */
    @Test
    public void testSalvaDati_IstanzaPresente() {
        Catalogo.getIstanza(); 
        Catalogo.salvaDati();
        
        File file = new File(NOME_FILE_CSV);
        assertTrue(file.exists());
    }
    
    @Test
    public void testSalvaDati_IstanzaNull() {
        Catalogo.salvaDati();
        
        File file = new File(NOME_FILE_CSV);
        assertFalse(file.exists());
    }
    

    /**
     * Test of toString method, of class Catalogo.
     */
    @Test
    public void testToString_CatalogoVuoto() {
        Catalogo istanza = Catalogo.getIstanza();
        assertEquals("Il catalogo è vuoto.", istanza.toString());
    }

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