package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.classi.Prestito;
import it.unisa.diem.gruppo01.classi.Studente;
import it.unisa.diem.gruppo01.classi.Libro; // Assicurati che questo import sia corretto

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

/**
 * @brief Test suite per la classe Studente.
 * Copre la gestione anagrafica, la logica di abilitazione prestiti 
 * e la gestione delle sanzioni.
 */
public class StudenteTest {

    private Studente studente;
    
    // Costanti per i dati di test
    private final String NOME = "Mario";
    private final String COGNOME = "Rossi";
    private final String MATRICOLA = "0512100001";
    private final String EMAIL = "m.rossi@studenti.unisa.it";

    @BeforeEach
    public void setUp() {
        // Inizializziamo uno studente base prima di ogni test
        // Nota: Passiamo "SanzioneTest" e true, ma ci aspettiamo che il costruttore li sovrascriva o gestisca diversamente
        studente = new Studente(COGNOME, NOME, MATRICOLA, EMAIL, "SanzioneTest", false);
    }

    /**
     * Test Black-Box sul Costruttore.
     * Verifica che i dati siano assegnati correttamente e che 
     * la logica di default (sanzione = "Nessuna") sia rispettata.
     */
    @Test
    public void testCostruttore() {
        assertAll("Verifica proprietà iniziali studente",
            () -> assertEquals(NOME, studente.getNome()),
            () -> assertEquals(COGNOME, studente.getCognome()),
            () -> assertEquals(MATRICOLA, studente.getMatricola()),
            () -> assertEquals("Nessuna", studente.getSanzione(), "Il costruttore dovrebbe forzare la sanzione a 'Nessuna'"),
            () -> assertTrue(studente.getPrestitiAttivi().isEmpty(), "La lista prestiti deve essere inizialmente vuota")
        );
    }

    /**
     * Test gestione lista prestiti (Aggiunta/Rimozione/Conteggio).
     */
    @Test
    public void testGestionePrestiti() {
        Prestito p1 = new Prestito(null, studente, LocalDate.now(), LocalDate.now().plusDays(30), null);
        
        studente.aggiungiPrestito(p1);
        assertEquals(1, studente.contaPrestitiAttivi(), "Dovrebbe esserci 1 prestito attivo");
        assertEquals(p1, studente.getPrestitiAttivi().get(0));

        studente.rimuoviPrestito(p1);
        assertEquals(0, studente.contaPrestitiAttivi(), "La lista dovrebbe essere vuota dopo la rimozione");
    }

    /**
     * Test White-Box / Branch Coverage per isAbilitato().
     * Caso 1: Studente senza prestiti e senza sanzioni -> ABILITATO.
     */
    @Test
    public void testIsAbilitato_Standard() {
        assertTrue(studente.isAbilitato(), "Studente senza prestiti e sanzioni deve essere abilitato");
    }

    /**
     * Test isAbilitato().
     * Caso 2: Limite prestiti raggiunto (>= 3) -> NON ABILITATO.
     */
    @Test
    public void testIsAbilitato_LimitePrestiti() {
        // Aggiungiamo 3 prestiti dummy
        for(int i=0; i<3; i++) {
            studente.aggiungiPrestito(new Prestito(null, studente, LocalDate.now(), LocalDate.now(), null));
        }
        assertFalse(studente.isAbilitato(), "Studente con 3 prestiti non deve essere abilitato");
    }

    /**
     * Test isAbilitato().
     * Caso 3: Sanzione "Categoria 3" -> NON ABILITATO.
     */
    @Test
    public void testIsAbilitato_SanzioneCategoria3() {
        studente.setSanzione("Categoria 3 (Blocco Permanente)");
        assertFalse(studente.isAbilitato(), "Studente con Categoria 3 non deve essere abilitato");
    }

    /**
     * Test Avanzato con Reflection per isAbilitato() - Categoria 2.
     * Problema: Il codice di Studente usa un campo 'private Prestito prestito' 
     * che non ha setter, ma viene usato nel check Categoria 2.
     * Soluzione: Usiamo Reflection per iniettare il prestito necessario al test.
     */
    @Test
    public void testIsAbilitato_SanzioneCategoria2_NonScaduta() throws NoSuchFieldException, IllegalAccessException {
        studente.setSanzione("Categoria 2 (Blocco 30gg)");
        
        // Creiamo un prestito restituito oggi (quindi blocco attivo perché < 30gg)
        Prestito prestitoRecente = new Prestito(null, studente, LocalDate.now().minusDays(50), LocalDate.now().minusDays(20), LocalDate.now());
        
        // --- INIZIO REFLECTION ---
        // Accediamo al campo privato "prestito" della classe Studente
        Field field = Studente.class.getDeclaredField("prestito");
        field.setAccessible(true); // Rendiamo il campo accessibile
        field.set(studente, prestitoRecente); // Iniettiamo il nostro prestito
        // --- FINE REFLECTION ---

        assertFalse(studente.isAbilitato(), "Blocco Categoria 2 attivo se restituzione < 30gg fa");
    }
    
    @Test
    public void testIsAbilitato_SanzioneCategoria2_Scaduta() throws NoSuchFieldException, IllegalAccessException {
        studente.setSanzione("Categoria 2 (Blocco 30gg)");
        
        // Prestito restituito 40 giorni fa (Blocco scaduto)
        Prestito prestitoVecchio = new Prestito(null, studente, LocalDate.now().minusDays(100), LocalDate.now().minusDays(50), LocalDate.now().minusDays(40));
        
        // Reflection per iniettare il prestito
        Field field = Studente.class.getDeclaredField("prestito");
        field.setAccessible(true);
        field.set(studente, prestitoVecchio);

        assertTrue(studente.isAbilitato(), "Blocco Categoria 2 non attivo se restituzione > 30gg fa");
    }

    /**
     * Test logica isRitardo().
     * Logica da codice: (!isAbilitato && prestiti < 3)
     */
    @Test
    public void testIsRitardo() {
        // Caso A: Abilitato -> Ritardo False
        assertFalse(studente.isRitardo());

        // Caso B: Disabilitato per troppi prestiti (>=3) -> Ritardo False (perché size non è < 3)
        for(int i=0; i<3; i++) {
             studente.aggiungiPrestito(new Prestito(null, studente, LocalDate.now(), LocalDate.now(), null));
        }
        assertFalse(studente.isRitardo(), "Se disabilitato per limite prestiti, isRitardo dovrebbe essere false");
        
        // Pulisco i prestiti per il prossimo assert
        studente.getPrestitiAttivi().clear();

        // Caso C: Disabilitato per Sanzione (Cat 3) e 0 prestiti -> Ritardo True
        studente.setSanzione("Categoria 3");
        assertTrue(studente.isRitardo(), "Se disabilitato per sanzione (con <3 prestiti), isRitardo dovrebbe essere true");
    }}
    
    