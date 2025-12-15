/**
 *@file ElencoTest.java
 *@brief Classe di test unitario per la classe Elenco.
 *
 * Questa classe testa le funzionalità di creaziona/modifica/lettura/rimozione
 * e di persistenza (salvataggio/caricamento) della classe Elenco, che gestisce
 * un insieme di oggetti Studente, utilizzando un TreeSet
 * per mantenere l'ordinamento.
 *
 *@author gruppo01
 *@version 1.0
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
 * @brief Classe di test per la classe Elenco.
 */
public class ElencoTest {
    
    // Variabili di istanza per i test
    private Elenco instance;
    private Studente s1;
    private Studente s2;
    private Studente s3;
    private Studente s4;
    private Studente s5;
    private Studente s6;
    private Studente s7;
    private Studente s8;
    private Studente s9;
    private Studente s10;
    
    private final String FILE_CSV = "Lista_studenti.csv"; //Nome del file CSV utilizzato per la persistenza del catalogo.
    private final String FILE_DOS = "test_export.dat"; //Nome del file per l'esportazione DOS.

    
    /**
    * @brief Setup eseguito prima di ogni test. Inizializza l'istanza di Elenco e crea due studenti di prova.
    */
    
    @BeforeEach
    public void setUp() {
        // Inizializza l'elenco e crea studenti di prova prima di ogni test
        instance = new Elenco();
        // Assumo il costruttore: Nome, Cognome, Matricola, Email, Sanzione, Ritardo
        s2 = new Studente("Verdi", "Luigi", "1002", "l.verdi@studenti.unisa.it", "Nessuna", false);
        s1 = new Studente("Rossi", "Mario", "1001", "m.rossi@studenti.unisa.it", "Nessuna", false);
        s3 = new Studente("Bianchi", "Luca", "1003", "l.bianchi@studenti.unisa.it", "Nessuna", false);
        s4 = new Studente("Lombardi", "Viola", "1004", "v.lombardi@studenti.unisa.it", "Nessuna", false);
        s5 = new Studente("Galli", "Sofia", "1005", "s.galli@studenti.unisa.it", "Nessuna", false);
        s6 = new Studente("Marini", "Leonardo", "1006", "l.marini@studenti.uni.it", "Nessuna", false);
        s7 = new Studente("Serra", "Federica", "1007", "f.serra@studenti.unisa.it", "Nessuna", false);
        s8 = new Studente("Colombo", "Alessandra", "1008", "a.colombo@studenti.unisa.it", "Nessuna", false);
        s9 = new Studente("Conti", "Luigia", "1009", "l.conti@studenti.uni.it", "Nessuna", false);
        s10 = new Studente("Pellegrini", "Luca", "1010", "l.pellegrini@studenti.unisa.it", "Nessuna", false);
    }
    /**
    *@brief Pulizia eseguita dopo ogni test. Elimina i file di persistenza creati dal test.
    */
    
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
     * @brief Test del metodo getElencoStudenti(), verifica che restituisca il TreeSet corretto.
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
     * @brief Test del metodo cercaStudenteperMatricola(), verifica la ricerca per chiave (Matricola).
     */
    
    @Test
    public void testCercaStudenteperMatricola() {
        System.out.println("cercaStudenteperMatricola");
        
        instance.aggiungiStudente(s1);
        
        // Caso 1:Matricola esistente
        String matricola = "1001";
        Studente result = instance.cercaStudenteperMatricola(matricola);
        assertNotNull(result);
        assertEquals(s1, result);
        
        // Caso 2: Matricola inesistente
        String matricolaAssente = "999";
        Studente resultNull = instance.cercaStudenteperMatricola(matricolaAssente);
        assertNull(resultNull);
    }

    /**
     * @brief Test del metodo aggiungiStudente(), verifica l'inserimento e la gestione dei duplicati.
     */
    
    @Test
    public void testAggiungiStudente() {
        System.out.println("aggiungiStudente");
        
        // Caso 1: Aggiunta con successo
        boolean result = instance.aggiungiStudente(s1);
        assertTrue(result);
        assertEquals(1, instance.getElencoStudenti().size());
        
        boolean result2 = instance.aggiungiStudente(s3);
        assertTrue(result2);
        assertEquals(2, instance.getElencoStudenti().size());        
        
        boolean result3 = instance.aggiungiStudente(s4);
        assertTrue(result3);
        assertEquals(3, instance.getElencoStudenti().size());        
        
        boolean result4 = instance.aggiungiStudente(s5);
        assertTrue(result4);
        assertEquals(4, instance.getElencoStudenti().size());        
        
        boolean result5 = instance.aggiungiStudente(s6);
        assertTrue(result5);
        assertEquals(5, instance.getElencoStudenti().size());        
        
        boolean result6 = instance.aggiungiStudente(s7);
        assertTrue(result6);
        assertEquals(6, instance.getElencoStudenti().size());        
        
        boolean result7 = instance.aggiungiStudente(s8);
        assertTrue(result7);
        assertEquals(7, instance.getElencoStudenti().size());        
        
        boolean result8 = instance.aggiungiStudente(s9);
        assertTrue(result8);
        assertEquals(8, instance.getElencoStudenti().size());
        
        boolean result9 = instance.aggiungiStudente(s10);
        assertTrue(result9);
        assertEquals(9, instance.getElencoStudenti().size());
        
        
        
        
        
        
        
        // Caso 2: Tentativo di aggiunta duplicato (stessa matricola)
        Studente sDuplicato = new Studente("Anna", "Bianchi", "1001", "a.bianchi@studenti.unisa.it", "Nessuna", false);
        boolean resultDuplicato = instance.aggiungiStudente(sDuplicato);
        assertFalse(resultDuplicato, "Non dovrebbe aggiungere studenti con matricola duplicata");
        assertEquals(9, instance.getElencoStudenti().size()); // La dimensione rimane 1
    }

    /**
     * @brief Test del metodo modificaStudente(), verifica l'aggiornamento dei dati e il potenziale riordinamento.
     */
    
    @Test
    public void testModificaStudente() {
        System.out.println("modificaStudente");
        
        instance.aggiungiStudente(s1); // Matricola 001, Rossi
        
        // Caso 1: Modifica valida
        String matricola = "1001";
        String nuovoNome = "MarioMod";
        String nuovoCognome = "RossiMod"; // Cambia cognome per testare riordinamento
        String nuovaEmail = "nuova@email.it";
        
        boolean result = instance.modificaStudente(matricola, nuovoNome, nuovoCognome, nuovaEmail);
        assertTrue(result);
        
        Studente sModificato = instance.cercaStudenteperMatricola("1001");
        assertEquals("RossiMod", sModificato.getCognome());
        assertEquals("nuova@email.it", sModificato.getEmail());
        
        // Caso 2: Studente non trovato
        boolean resultFail = instance.modificaStudente("999", "A", "B", "C");
        assertFalse(resultFail);
    }

    /**
     * @brief est del metodo eliminaStudente(), verifica la rimozione di un elemento.
     */
    
    @Test
    public void testEliminaStudente() {
        System.out.println("eliminaStudente");
        
        instance.aggiungiStudente(s1);
        
        // Caso 1: Eliminazione riuscita
        String matricola = "1001";
        boolean result = instance.eliminaStudente(matricola);
        assertTrue(result);
        assertTrue(instance.getElencoStudenti().isEmpty());
        
        // Caso 2: Eliminazione fallita (già eliminato o mai esistito)
        boolean resultFail = instance.eliminaStudente("1001");
        assertFalse(resultFail);
    }

    /**
     * @brief Test del metodo salvaDOS(), verifica il salvataggio dei dati in formato DOS (binario o serializzazione).
     * Si verifica l'esistenxa e la non vuotezza del file
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
     * @brief Test del metodo salvaCSV(), verifica il salvataggio dei dati in formato CSV.
     *Si usa il nome file "Lista_studenti.csv"
    */
    
    @Test
    public void testSalvaCSV() {
        System.out.println("salvaCSV");
        
        instance.aggiungiStudente(s1);
        instance.aggiungiStudente(s2);
        instance.aggiungiStudente(s3);
        instance.aggiungiStudente(s4);
        instance.aggiungiStudente(s5);
        instance.aggiungiStudente(s6);
        instance.aggiungiStudente(s7);
        instance.aggiungiStudente(s8);
        instance.aggiungiStudente(s9);
        instance.aggiungiStudente(s10);
        
        
        // Il metodo salvaCSV usa un nome file hardcoded "Lista_studenti.csv" nella classe Elenco
        instance.salvaCSV();
        
        File f = new File(FILE_CSV);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
    }

    /**
     * @brief Test del metodo caricaDati(), verifica il caricamento dei dati da CSV.
     */
    
    @Test
    public void testCaricaDati() {
        System.out.println("caricaDati");
        
        // Prepara dati e salva su file
        instance.aggiungiStudente(s1);
        instance.aggiungiStudente(s2);
        instance.aggiungiStudente(s3);
        instance.aggiungiStudente(s4);
        instance.aggiungiStudente(s5);
        instance.aggiungiStudente(s6);
        instance.aggiungiStudente(s7);
        instance.aggiungiStudente(s8);
        instance.aggiungiStudente(s9);
        instance.aggiungiStudente(s10);
        instance.salvaCSV();
        
        // Crea una nuova istanza pulita
        Elenco nuovaIstanza = new Elenco();
        
        // Carica i dati
        nuovaIstanza.caricaDati();
        
        // Verifica che entrambi gli studenti siano stati caricati
        assertEquals(10, nuovaIstanza.getElencoStudenti().size());
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1001"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1002"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1003"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1004"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1005"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1006"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1007"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1008"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1009"));
        assertNotNull(nuovaIstanza.cercaStudenteperMatricola("1010"));
    }

    /**
     * @brief Test del metodo toString(), verifica la rappresentazione stringa dell'elenco.
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
        // Verifica la presenza di dati chiave nell'output
        assertTrue(resultFull.contains("Rossi")); // Controlla che ci sia il cognome
        assertTrue(resultFull.contains("1001"));   // Controlla che ci sia la matricola
    }
}