package it.unisa.diem.gruppo01.classi;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*; // Import statico per le asserzioni [cite: 1165]
import java.time.LocalDate;

/**
 * @brief Classe di test per Prestito.java
 * Utilizza JUnit 5 come framework di automazione.
 */
public class PrestitoTest {

    private Prestito prestito;
    private Libro libroStub;
    private Studente studenteStub;
    private LocalDate dataInizio;
    private LocalDate dataScadenza;

    // Fixture: Eseguito prima di ogni test per preparare l'ambiente 
    @BeforeEach
    public void setUp() {
        // Creazione di oggetti Stub minimi (necessari perché Prestito li richiede nel costruttore)
        // In un caso reale, questi sarebbero oggetti reali o mock di Mockito.
        libroStub = null; 
        studenteStub = null; 
        
        // Impostiamo date di default
        dataInizio = LocalDate.now().minusDays(30);
        dataScadenza = dataInizio.plusDays(Prestito.DURATA_PRESTITO); // Scadenza futura
    }

    /**
     * Test per verificare il costruttore e i getter di base.
     * Verifica la corretta creazione dell'oggetto.
     */
    @Test
    public void testCostruttoreEGetter() {
        prestito = new Prestito(libroStub, studenteStub, dataInizio, dataScadenza, null);
        
        assertNotNull(prestito); // Verifica che l'oggetto non sia null [cite: 1190]
        assertEquals(dataInizio, prestito.getDataInizio()); // [cite: 1183]
        assertEquals(dataScadenza, prestito.getDataScadenza());
        assertNull(prestito.getDataRestituzione()); // [cite: 1188]
    }

    /**
     * Test del metodo isPrestitoAttivo().
     * Copertura: Verifica i due stati possibili (Attivo/Concluso).
     */
    @Test
    public void testIsPrestitoAttivo() {
        // Caso 1: Data restituzione è null -> Prestito Attivo
        prestito = new Prestito(libroStub, studenteStub, dataInizio, dataScadenza, null);
        assertTrue(prestito.isPrestitoAttivo(), "Il prestito dovrebbe essere attivo se dataRestituzione è null"); // [cite: 1186]

        // Caso 2: Data restituzione valorizzata -> Prestito Concluso
        prestito.setDatarestituzione(LocalDate.now());
        assertFalse(prestito.isPrestitoAttivo(), "Il prestito non dovrebbe essere attivo se dataRestituzione è settata"); // [cite: 1187]
    }

    /**
     * Test di calcolaGiorniRitardo() - Scenario: Nessun Ritardo.
     * Verifica che ritorni 0 se restituito in anticipo o in tempo.
     */
    @Test
    public void testCalcolaGiorniRitardo_NessunRitardo() {
        // Scadenza è tra 20 giorni (DURATA_PRESTITO è 50, inizio era -30)
        // Restituiamo oggi (in anticipo)
        prestito = new Prestito(libroStub, studenteStub, dataInizio, dataScadenza, LocalDate.now());
        
        int ritardo = prestito.calcolaGiorniRitardo();
        assertEquals(0, ritardo, "Il ritardo dovrebbe essere 0 se restituito in anticipo");
    }

    /**
     * Test di calcolaGiorniRitardo() - Scenario: Ritardo Effettivo (Libro Restituito).
     * Copertura del ramo: dataRestituzione != null
     */
    @Test
    public void testCalcolaGiorniRitardo_ConRitardoRestituito() {
        // Simuliamo un prestito scaduto 5 giorni fa e restituito oggi
        LocalDate scadenzaPassata = LocalDate.now().minusDays(5);
        prestito = new Prestito(libroStub, studenteStub, dataInizio, scadenzaPassata, LocalDate.now());

        assertEquals(5, prestito.calcolaGiorniRitardo(), "Dovrebbe calcolare 5 giorni di ritardo tra scadenza e restituzione");
    }

    /**
     * Test di calcolaGiorniRitardo() - Scenario: Ritardo in corso (Prestito Attivo).
     * Copertura del ramo: dataRestituzione == null (usa LocalDate.now())
     */
    @Test
    public void testCalcolaGiorniRitardo_AttivoInRitardo() {
        // Prestito ancora attivo (rest=null) ma scaduto 10 giorni fa
        LocalDate scadenzaPassata = LocalDate.now().minusDays(10);
        prestito = new Prestito(libroStub, studenteStub, dataInizio, scadenzaPassata, null);

        assertEquals(10, prestito.calcolaGiorniRitardo(), "Dovrebbe usare LocalDate.now() per calcolare il ritardo su prestito attivo");
    }

    /**
     * Test gestioneSanzioni() - Equivalence Partitioning & Boundary Testing.
     * Classi di equivalenza identificate:
     * 1. Ritardo <= 0 (Nessuna sanzione)
     * 2. 1 <= Ritardo <= 10 (Categoria 1)
     * 3. 11 <= Ritardo <= 20 (Categoria 2)
     * 4. Ritardo > 20 (Categoria 3)
     */

    @Test
    public void testGestioneSanzioni_NessunRitardo() {
        // Classe 1: 0 giorni di ritardo
        prestito = createPrestitoScadutoGiorniFa(0);
        assertEquals(Prestito.NESSUN_RITARDO, prestito.gestioneSanzioni());
    }

    @Test
    public void testGestioneSanzioni_Categoria1_BloccoLieve() {
        // Classe 2: Test valore intermedio (5 giorni)
        prestito = createPrestitoScadutoGiorniFa(5);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 1"));
        assertTrue(prestito.gestioneSanzioni().contains("Blocco lieve"));

        // Boundary Testing: Limite superiore classe (10 giorni) 
        prestito = createPrestitoScadutoGiorniFa(10);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 1"), "10 giorni dovrebbero essere ancora Categoria 1");
    }

    @Test
    public void testGestioneSanzioni_Categoria2_Blocco30Giorni() {
        // Boundary Testing: Limite inferiore classe successiva (11 giorni)
        prestito = createPrestitoScadutoGiorniFa(11);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 2"), "11 giorni dovrebbero essere Categoria 2");

        // Classe 3: Test valore intermedio (15 giorni)
        prestito = createPrestitoScadutoGiorniFa(15);
        assertTrue(prestito.gestioneSanzioni().contains("Blocco 30 giorni"));
        
        // Boundary Testing: Limite superiore classe (20 giorni)
        prestito = createPrestitoScadutoGiorniFa(20);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 2"), "20 giorni dovrebbero essere ancora Categoria 2");
    }

    @Test
    public void testGestioneSanzioni_Categoria3_BloccoPermanente() {
        // Boundary Testing: Limite inferiore classe finale (21 giorni)
        prestito = createPrestitoScadutoGiorniFa(21);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 3"), "21 giorni dovrebbero essere Categoria 3");
        assertTrue(prestito.gestioneSanzioni().contains("Blocco PERMANENTE"));
    }

    // --- Metodi Helper ---
    
    /**
     * Metodo di supporto per creare rapidamente prestiti con scadenza nel passato.
     * Utile per testare i calcoli basati su LocalDate.now().
     */
    private Prestito createPrestitoScadutoGiorniFa(int giorniFa) {
        LocalDate scadenza = LocalDate.now().minusDays(giorniFa);
        // dataRestituzione null implica che il ritardo viene calcolato su "oggi"
        return new Prestito(libroStub, studenteStub, dataInizio, scadenza, null);
    }
}