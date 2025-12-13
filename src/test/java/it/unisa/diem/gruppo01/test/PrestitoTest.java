package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.classi.Libro;
import it.unisa.diem.gruppo01.classi.Prestito;
import it.unisa.diem.gruppo01.classi.Studente;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per Prestito.
 */
public class PrestitoTest {
    
    // Oggetti di test
    private Libro testLibro;
    private Studente testStudente;
    private Prestito testPrestitoAttivo;
    private Prestito testPrestitoConclusoRitardo;
    
    // Costanti per i giorni di ritardo nel test concluso
    private static final int GIORNI_DI_RITARDO_MOCK = 15;
    
    public PrestitoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        // 1. Oggetti base
        // *** CORREZIONE: Uso del costruttore Studente a 6 parametri (cognome, nome, matricola, email, sanzione, ritardo) ***
        testStudente = new Studente("Rossi", "Marco", "00001", "marco.rossi@unisa.it", "Nessuna", false) {
            
            // Sovrascriviamo il metodo per controllare il conteggio dei prestiti nei test
            @Override
            public int contaPrestitiAttivi() {
                // Ritorna un valore base (1)
                return 1;
            }
        };
        
        // Libro: Libro(isbn, titolo, autore, anno, copie)
        // Necessario che Libro abbia un costruttore adatto
        testLibro = new Libro("978-8860714798", "Il Signore degli Anelli", "J.R.R. Tolkien", LocalDate.of(1954, 7, 29), 5);
        
        // 2. Prestito Attivo (dataRestituzione == null)
        LocalDate dataOggi = LocalDate.now();
        LocalDate dataScadenzaAttivo = dataOggi.plusDays(Prestito.DURATA_PRESTITO);

        // Prestito(Libro, Studente, dataInizio, dataScadenza, dataRestituzione)
        testPrestitoAttivo = new Prestito(
            testLibro, 
            testStudente, 
            dataOggi, 
            dataScadenzaAttivo, 
            null // Prestito Attivo
        );

        // 3. Prestito Concluso in Ritardo (15 giorni di ritardo)
        LocalDate dataInizioConcluso = LocalDate.of(2025, 1, 1);
        LocalDate dataScadenzaConcluso = dataInizioConcluso.plusDays(Prestito.DURATA_PRESTITO);
        LocalDate dataRestituzioneTardiva = dataScadenzaConcluso.plusDays(GIORNI_DI_RITARDO_MOCK); 
        
        testPrestitoConclusoRitardo = new Prestito(
            testLibro,
            testStudente,
            dataInizioConcluso,
            dataScadenzaConcluso,
            dataRestituzioneTardiva
        );
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLibro method, of class Prestito.
     */
    @Test
    public void testGetLibro() {
        assertEquals(testLibro, testPrestitoAttivo.getLibro(), "Il libro restituito deve corrispondere a quello impostato.");
    }

    /**
     * Test of setLibro method, of class Prestito.
     */
    @Test
    public void testSetLibro() {
        Libro nuovoLibro = new Libro("000-0000000000", "Nuovo Titolo", "Nuovo Autore", LocalDate.of(2020, 1, 1), 1);
        testPrestitoAttivo.setLibro(nuovoLibro);
        assertEquals(nuovoLibro, testPrestitoAttivo.getLibro(), "Il libro non è stato aggiornato correttamente.");
    }

    /**
     * Test of getStudente method, of class Prestito.
     */
    @Test
    public void testGetStudente() {
        assertEquals(testStudente, testPrestitoAttivo.getStudente(), "Lo studente restituito deve corrispondere a quello impostato.");
    }

    /**
     * Test of setStudente method, of class Prestito.
     * Corretto per usare il costruttore Studente a 6 parametri.
     */
    @Test
    public void testSetStudente() {
        // Usa il costruttore Studente a 6 parametri: (cognome, nome, matricola, email, sanzione, ritardo)
        Studente nuovoStudente = new Studente("Verdi", "Luca", "99999", "luca.verdi@unisa.it", "Sanzione Iniziale", false);
        testPrestitoAttivo.setStudente(nuovoStudente);
        assertEquals(nuovoStudente, testPrestitoAttivo.getStudente(), "Lo studente non è stato aggiornato correttamente.");
    }

    /**
     * Test of getDataInizio method, of class Prestito.
     */
    @Test
    public void testGetDataInizio() {
        LocalDate dataAttesa = LocalDate.now();
        assertEquals(dataAttesa, testPrestitoAttivo.getDataInizio(), "La data di inizio non corrisponde.");
    }

    /**
     * Test of setDataInizio method, of class Prestito.
     */
    @Test
    public void testSetDataInizio() {
        LocalDate nuovaData = LocalDate.of(2025, 10, 10);
        testPrestitoAttivo.setDataInizio(nuovaData);
        assertEquals(nuovaData, testPrestitoAttivo.getDataInizio(), "La data di inizio non è stata aggiornata correttamente.");
    }

    /**
     * Test of getDataScadenza method, of class Prestito.
     */
    @Test
    public void testGetDataScadenza() {
        LocalDate dataAttesa = LocalDate.now().plusDays(Prestito.DURATA_PRESTITO);
        assertEquals(dataAttesa, testPrestitoAttivo.getDataScadenza(), "La data di scadenza non corrisponde.");
    }

    /**
     * Test of setDataScadenza method, of class Prestito.
     */
    @Test
    public void testSetDataScadenza() {
        LocalDate nuovaData = LocalDate.of(2025, 12, 25);
        testPrestitoAttivo.setDataScadenza(nuovaData);
        assertEquals(nuovaData, testPrestitoAttivo.getDataScadenza(), "La data di scadenza non è stata aggiornata correttamente.");
    }

    /**
     * Test of getDataRestituzione method, of class Prestito.
     */
    @Test
    public void testGetDataRestituzione() {
        // 1. Prestito Attivo
        assertNull(testPrestitoAttivo.getDataRestituzione(), "Data di restituzione per prestito attivo dovrebbe essere null.");
        
        // 2. Prestito Concluso
        LocalDate dataRestituzioneAttesa = LocalDate.of(2025, 1, 1).plusDays(Prestito.DURATA_PRESTITO).plusDays(GIORNI_DI_RITARDO_MOCK);
        assertEquals(dataRestituzioneAttesa, testPrestitoConclusoRitardo.getDataRestituzione(), "La data di restituzione non corrisponde.");
    }

    /**
     * Test of setDatarestituzione method, of class Prestito.
     */
    @Test
    public void testSetDatarestituzione() {
        LocalDate nuovaData = LocalDate.now().minusDays(5);
        testPrestitoAttivo.setDatarestituzione(nuovaData);
        assertEquals(nuovaData, testPrestitoAttivo.getDataRestituzione(), "La data di restituzione non è stata aggiornata correttamente.");
    }

    /**
     * Test of isPrestitoAttivo method, of class Prestito.
     */
    @Test
    public void testIsPrestitoAttivo() {
        // 1. Prestito Attivo (null)
        assertTrue(testPrestitoAttivo.isPrestitoAttivo(), "Il prestito con dataRestituzione null dovrebbe risultare attivo.");
        
        // 2. Prestito Concluso (data non null)
        assertFalse(testPrestitoConclusoRitardo.isPrestitoAttivo(), "Il prestito con dataRestituzione impostata dovrebbe risultare concluso.");
    }

    /**
     * Test of calcolaGiorniRitardo method, of class Prestito.
     */
    @Test
    public void testCalcolaGiorniRitardo() {
        LocalDate dataInizioBase = LocalDate.of(2025, 3, 1);
        LocalDate dataScadenzaBase = dataInizioBase.plusDays(Prestito.DURATA_PRESTITO);
        
        // 1. Prestito restituito in ritardo (15 giorni)
        assertEquals(GIORNI_DI_RITARDO_MOCK, testPrestitoConclusoRitardo.calcolaGiorniRitardo(), "Il calcolo dei giorni di ritardo per 15 giorni non è corretto.");
        
        // 2. Prestito restituito in anticipo (10 giorni) -> -10
        LocalDate dataRestituzioneAnticipo = dataScadenzaBase.minusDays(10);
        Prestito prestitoAnticipo = new Prestito(testLibro, testStudente, dataInizioBase, dataScadenzaBase, dataRestituzioneAnticipo);
        assertEquals(-10, prestitoAnticipo.calcolaGiorniRitardo(), "Il calcolo per una restituzione in anticipo (-10 giorni) non è corretto.");

        // 3. Prestito restituito in data scadenza (0 giorni)
        Prestito prestitoPuntuale = new Prestito(testLibro, testStudente, dataInizioBase, dataScadenzaBase, dataScadenzaBase);
        assertEquals(0, prestitoPuntuale.calcolaGiorniRitardo(), "Il calcolo per una restituzione puntuale (0 giorni) non è corretto.");
    }

    /**
     * Test of gestioneSanzioni method, of class Prestito.
     */
    @Test
    public void testGestioneSanzioni() {
        
        // Mock Studente con conteggio prestiti controllato
        final int PRESTITI_SOTTO_LIMITE = Prestito.LIMITE_PRESTITI - 1; // 2
        final int PRESTITI_SOPRA_LIMITE = Prestito.LIMITE_PRESTITI + 1; // 4
        
        // Studente SOPRA LIMITE 
        Studente studenteSopraLimite = new Studente("Sopra", "Limite", "2", "s@unisa.it", "Nessuna", false) {
            @Override
            public int contaPrestitiAttivi() {
                return PRESTITI_SOPRA_LIMITE;
            }
        };
        
        // Studente SOTTO LIMITE 
        Studente studenteSottoLimite = new Studente("Sotto", "Limite", "1", "s@unisa.it", "Nessuna", false) {
            @Override
            public int contaPrestitiAttivi() {
                return PRESTITI_SOTTO_LIMITE;
            }
        };

        // Preparazione date
        LocalDate dataInizioBase = LocalDate.of(2025, 1, 1);
        LocalDate dataScadenzaBase = dataInizioBase.plusDays(Prestito.DURATA_PRESTITO);

        // 1. Prestito Attivo (Non Applicabile)
        assertEquals("NON APPLICABILE (Prestito Attivo)", testPrestitoAttivo.gestioneSanzioni(), "La sanzione per prestito attivo non è corretta.");

        // 2. Nessun Ritardo (Restituito in tempo/anticipo)
        Prestito prestitoZeroRitardo = new Prestito(testLibro, studenteSopraLimite, dataInizioBase, dataScadenzaBase, dataScadenzaBase);
        assertEquals("Nessun ritardo riscontrato.", prestitoZeroRitardo.gestioneSanzioni(), "La sanzione per zero ritardo non è corretta.");

        // 3. Ritardo di 5 giorni con Studente SOTTO LIMITE (Nessuna Sanzione)
        LocalDate dataRitardo5 = dataScadenzaBase.plusDays(5);
        Prestito prestitoRitardo5Sotto = new Prestito(testLibro, studenteSottoLimite, dataInizioBase, dataScadenzaBase, dataRitardo5);
        assertTrue(prestitoRitardo5Sotto.gestioneSanzioni().contains("NESSUNA SANZIONE APPLICATA"), "Con prestiti sotto il limite non deve esserci sanzione.");

        // 4. Ritardo di 5 giorni con Studente SOPRA LIMITE (Categoria 1: 1-10 giorni)
        Prestito prestitoRitardo5Sopra = new Prestito(testLibro, studenteSopraLimite, dataInizioBase, dataScadenzaBase, dataRitardo5);
        assertTrue(prestitoRitardo5Sopra.gestioneSanzioni().contains("Categoria 1") && prestitoRitardo5Sopra.gestioneSanzioni().contains("5 gg"), "Sanzione per ritardo di 5 giorni non è corretta (Cat 1).");

        // 5. Ritardo di 11 giorni con Studente SOPRA LIMITE (Categoria 2: 11-20 giorni)
        LocalDate dataRitardo11 = dataScadenzaBase.plusDays(11);
        Prestito prestitoRitardo11Sopra = new Prestito(testLibro, studenteSopraLimite, dataInizioBase, dataScadenzaBase, dataRitardo11);
        assertTrue(prestitoRitardo11Sopra.gestioneSanzioni().contains("Categoria 2") && prestitoRitardo11Sopra.gestioneSanzioni().contains("11 gg"), "Sanzione per ritardo di 11 giorni non è corretta (Cat 2).");

        // 6. Ritardo di 21 giorni con Studente SOPRA LIMITE (Categoria 3: > 20 giorni)
        LocalDate dataRitardo21 = dataScadenzaBase.plusDays(21);
        Prestito prestitoRitardo21Sopra = new Prestito(testLibro, studenteSopraLimite, dataInizioBase, dataScadenzaBase, dataRitardo21);
        assertTrue(prestitoRitardo21Sopra.gestioneSanzioni().contains("Categoria 3") && prestitoRitardo21Sopra.gestioneSanzioni().contains("21 gg"), "Sanzione per ritardo di 21 giorni non è corretta (Cat 3).");
    }

    /**
     * Test of toString method, of class Prestito.
     */
   /**
     * Test of toString method, of class Prestito (CORRETTO).
     */
    @Test
    public void testToString() {
        // La variabile testStudente usa un mock che restituisce 1 per contaPrestitiAttivi()
        // assumendo 1 < Prestito.LIMITE_PRESTITI, quindi NESSUNA SANZIONE APPLICATA.
        
        // --- 1. Prestito Attivo (Verifiche OK) ---
        String outputAttivo = testPrestitoAttivo.toString();
        assertTrue(outputAttivo.contains("STATO: ATTIVO"), "toString non riporta lo stato ATTIVO.");
        assertTrue(outputAttivo.contains("Data restituzione: null"), "toString non riporta la data di restituzione null.");
        assertTrue(outputAttivo.contains("NON APPLICABILE (Prestito Attivo)"), "toString non riporta la sanzione 'NON APPLICABILE'.");

        // --- 2. Prestito Concluso (in ritardo di 15 giorni) ---
        // QUI SI TROVAVA L'ERRORE.
        String outputConcluso = testPrestitoConclusoRitardo.toString();
        assertTrue(outputConcluso.contains("STATO: CONCLUSO"), "toString non riporta lo stato CONCLUSO.");
        // Il calcolaGiorniRitardo dovrebbe dare "15 giorni"
        assertTrue(outputConcluso.contains("Giorni di Ritardo: 15 giorni"), "toString non riporta i giorni di ritardo corretti.");
        
        // La sanzione corretta per 15 giorni di ritardo e contaPrestitiAttivi()=1 è:
        assertTrue(outputConcluso.contains("NESSUNA SANZIONE APPLICATA"), "toString non riporta la sanzione corretta per 15gg di ritardo ma sotto il limite."); 

        // --- 3. Prestito Concluso (puntuale) (Verifiche OK) ---
        LocalDate dataInizioPuntuale = LocalDate.of(2025, 4, 1);
        LocalDate dataScadenzaPuntuale = dataInizioPuntuale.plusDays(Prestito.DURATA_PRESTITO);
        Prestito prestitoPuntuale = new Prestito(testLibro, testStudente, dataInizioPuntuale, dataScadenzaPuntuale, dataScadenzaPuntuale);
        
        String outputPuntuale = prestitoPuntuale.toString();
        assertTrue(outputPuntuale.contains("STATO: CONCLUSO"), "toString non riporta lo stato CONCLUSO per prestito puntuale.");
        assertTrue(outputPuntuale.contains("Giorni di Ritardo: Nessun ritardo"), "toString non riporta 'Nessun ritardo' per prestito puntuale.");
        assertTrue(outputPuntuale.contains("Nessun ritardo riscontrato."), "toString non riporta 'Nessun ritardo riscontrato' nella sezione sanzioni.");
    }
}
