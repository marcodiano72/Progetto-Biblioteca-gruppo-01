/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.classi.Elenco;
import it.unisa.diem.gruppo01.classi.Studente;
import java.io.File;
import java.util.TreeSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author nicolaazzato
 */
public class ElencoTest {
    
    // Variabili di istanza per i test
    private Elenco instance;
    private Studente s1;
    private Studente s2;
    private final String FILE_CSV = "Lista_studenti.csv"; // Nome hardcoded nella classe Elenco
    private final String FILE_DOS = "test_export.dat";

    public ElencoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Inizializza l'elenco e crea studenti di prova prima di ogni test
        instance = new Elenco();
        // Assumo il costruttore: Nome, Cognome, Matricola, Email, Sanzione, Ritardo
        s1 = new Studente("Mario", "Rossi", "001", "m.rossi@studenti.unisa.it", "Nessuna", false);
        s2 = new Studente("Luigi", "Verdi", "002", "l.verdi@studenti.unisa.it", "Nessuna", false);
    }
    
    @AfterEach
    public void tearDown() {
        // Pulizia: elimina i file creati durante i test di salvataggio
        /*
        File fCsv = new File(FILE_CSV);
        if (fCsv.exists()) {
            fCsv.delete();
        }
        */  //Commentiamo altrimenti il file verrebbe eliminato
        
        File fDos = new File(FILE_DOS);
        if (fDos.exists()) {
            fDos.delete();
        }
    }

    /**
     * Test of getElencoStudenti method, of class Elenco.
     */
    @Test
    public void testGetElencoStudenti() {
        System.out.println("getElencoStudenti");
        
        // Verifica iniziale: deve essere vuoto ma non null
        TreeSet<Studente> result = instance.getElencoStudenti();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        // Verifica dopo inserimento
        instance.aggiungiStudente(s1);
        result = instance.getElencoStudenti();
        assertEquals(1, result.size());
        assertTrue(result.contains(s1));
    }

    /**
     * Test of cercaStudenteperMatricola method, of class Elenco.
     */
    @Test
    public void testCercaStudenteperMatricola() {
        System.out.println("cercaStudenteperMatricola");
        
        instance.aggiungiStudente(s1);
        
        // Caso 1: Matricola esistente
        String matricola = "001";
        Studente result = instance.cercaStudenteperMatricola(matricola);
        assertNotNull(result);
        assertEquals(s1, result);
        
        // Caso 2: Matricola inesistente
        String matricolaAssente = "999";
        Studente resultNull = instance.cercaStudenteperMatricola(matricolaAssente);
        assertNull(resultNull);
    }

    /**
     * Test of aggiungiStudente method, of class Elenco.
     */
    @Test
    public void testAggiungiStudente() {
        System.out.println("aggiungiStudente");
        
        // Caso 1: Aggiunta con successo
        boolean result = instance.aggiungiStudente(s1);
        assertTrue(result);
        assertEquals(1, instance.getElencoStudenti().size());
        
        // Caso 2: Tentativo di aggiunta duplicato (stessa matricola)
        Studente sDuplicato = new Studente("Anna", "Bianchi", "001", "a.bianchi@test.it", "Nessuna", false);
        boolean resultDuplicato = instance.aggiungiStudente(sDuplicato);
        assertFalse(resultDuplicato, "Non dovrebbe aggiungere studenti con matricola duplicata");
        assertEquals(1, instance.getElencoStudenti().size());
    }

    /**
     * Test of modificaStudente method, of class Elenco.
     */
    @Test
    public void testModificaStudente() {
        System.out.println("modificaStudente");
        
        instance.aggiungiStudente(s1); // Matricola 001, Rossi
        
        // Caso 1: Modifica valida
        String matricola = "001";
        String nuovoNome = "MarioMod";
        String nuovoCognome = "RossiMod"; // Cambia cognome per testare riordinamento
        String nuovaEmail = "nuova@email.it";
        
        boolean result = instance.modificaStudente(matricola, nuovoNome, nuovoCognome, nuovaEmail);
        assertTrue(result);
        
        Studente sModificato = instance.cercaStudenteperMatricola("001");
        assertEquals("RossiMod", sModificato.getCognome());
        assertEquals("nuova@email.it", sModificato.getEmail());
        
        // Caso 2: Studente non trovato
        boolean resultFail = instance.modificaStudente("999", "A", "B", "C");
        assertFalse(resultFail);
    }

    /**
     * Test of eliminaStudente method, of class Elenco.
     */
    @Test
    public void testEliminaStudente() {
        System.out.println("eliminaStudente");
        
        instance.aggiungiStudente(s1);
        
        // Caso 1: Eliminazione riuscita
        String matricola = "001";
        boolean result = instance.eliminaStudente(matricola);
        assertTrue(result);
        assertTrue(instance.getElencoStudenti().isEmpty());
        
        // Caso 2: Eliminazione fallita (già eliminato o mai esistito)
        boolean resultFail = instance.eliminaStudente("001");
        assertFalse(resultFail);
    }

    /**
     * Test of salvaDOS method, of class Elenco.
     */
    @Test
    public void testSalvaDOS() throws Exception {
        System.out.println("salvaDOS");
        
        instance.aggiungiStudente(s1);
        
        instance.salvaDOS(FILE_DOS);
        
        File f = new File(FILE_DOS);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
    }

    /**
     * Test of salvaCSV method, of class Elenco.
     */
    @Test
    public void testSalvaCSV() {
        System.out.println("salvaCSV");
        
        instance.aggiungiStudente(s1);
        instance.aggiungiStudente(s2);
        
        // Il metodo salvaCSV usa un nome file hardcoded "Lista_studenti.csv" nella classe Elenco
        instance.salvaCSV();
        
        File f = new File(FILE_CSV);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
    }

    /**
     * Test of caricaDati method, of class Elenco.
     */
    @Test
    public void testCaricaDati() {
        System.out.println("caricaDati");
        
        // 1. Prepara dati e salva su file
        instance.aggiungiStudente(s1);
        instance.aggiungiStudente(s2);
        instance.salvaCSV();
        
        // 2. Crea una nuova istanza pulita
        Elenco nuovaIstanza = new Elenco();
        
        // 3. Carica i dati
        nuovaIstanza.caricaDati();
        
        // 4. Verifica
        assertEquals(2, nuovaIstanza.getElencoStudenti().size());
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("001"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("002"));
    }

    /**
     * Test of toString method, of class Elenco.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        // Caso elenco vuoto
        String resultEmpty = instance.toString();
        assertNotNull(resultEmpty);
        assertTrue(resultEmpty.contains("vuoto"));
        
        // Caso elenco pieno
        instance.aggiungiStudente(s1);
        String resultFull = instance.toString();
        assertNotNull(resultFull);
        assertTrue(resultFull.contains("Rossi")); // Controlla che ci sia il cognome
        assertTrue(resultFull.contains("001"));   // Controlla che ci sia la matricola
    }
}