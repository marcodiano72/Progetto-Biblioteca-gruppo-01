/**
 * @file StudenteTest.java
 * @brief Suite di test unitari per la classe Studente.
 * * Questo file contiene i test per verificare la corretta gestione anagrafica,
 * la logica dei permessi (abilitazione al prestito) e il sistema sanzionatorio.
 * Utilizza JUnit 5 e Java Reflection per test white-box avanzati.
 * * @author Gruppo01
 * @version 1.0
 */
package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.classi.Prestito;
import it.unisa.diem.gruppo01.classi.Studente;
import it.unisa.diem.gruppo01.classi.Libro; 

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

/**
 * @class StudenteTest
 * @brief Classe di test per l'unità Studente.
 * * Copre tre aree principali:
 * 1. Inizializzazione e stato consistente dell'oggetto.
 * 2. Gestione della collezione dei prestiti attivi.
 * 3. Logica booleana complessa per l'abilitazione (isAbilitato) e il ritardo (isRitardo),
 * inclusa la manipolazione di campi privati tramite Reflection.
 */
public class StudenteTest {

    private Studente studente; ///< Oggetto sotto test (SUT).
    
    // Costanti per i dati di test
    private final String NOME = "Mario";
    private final String COGNOME = "Rossi";
    private final String MATRICOLA = "0512100001";
    private final String EMAIL = "m.rossi@studenti.unisa.it";
    private final String SANZIONE = "Nessuna";

    /**
     * @brief Configurazione iniziale.
     * * Eseguito prima di ogni test (@BeforeEach).
     * Inizializza un'istanza pulita di Studente per garantire l'indipendenza dei test.
     */
    @BeforeEach
    public void setUp() {
        // Inizializziamo uno studente base prima di ogni test
        // Nota: Passiamo "SanzioneTest" e true, ma ci aspettiamo che il costruttore li sovrascriva o gestisca diversamente
        studente = new Studente(COGNOME, NOME, MATRICOLA, EMAIL, SANZIONE, false);
    }

    /**
     * @brief Test Black-Box sul Costruttore.
     * @test Verifica che i dati anagrafici siano assegnati correttamente e che
     * le proprietà di default (sanzione iniziale "Nessuna", lista vuota) siano rispettate,
     * indipendentemente dai parametri "sporchi" passati in input.
     */
    @Test
    public void testCostruttore() {
        assertAll("Verifica proprietà iniziali studente",
            () -> assertEquals(NOME, studente.getNome()),
            () -> assertEquals(COGNOME, studente.getCognome()),
            () -> assertEquals(MATRICOLA, studente.getMatricola()),
            () -> assertEquals(SANZIONE, studente.getSanzione()),
            () -> assertTrue(studente.getPrestitiAttivi().isEmpty(), "La lista prestiti deve essere inizialmente vuota")
        );
    }

    /**
     * @brief Test gestione della lista prestiti.
     * @test Verifica le operazioni CRUD sulla lista interna:
     * - Aggiunta di un prestito.
     * - Verifica della dimensione della lista.
     * - Rimozione del prestito.
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
     * @brief Test di isAbilitato() - Scenario Standard.
     * @test Verifica che uno studente senza prestiti attivi e senza sanzioni
     * risulti abilitato a nuovi prestiti.
     */
    @Test
    public void testIsAbilitato_Standard() {
        assertTrue(studente.isAbilitato(), "Studente senza prestiti e sanzioni deve essere abilitato");
    }

    /**
     * @brief Test di isAbilitato().
     * @test Verifica il comportamento al limite massimo di prestiti (3).
     * Lo studente deve risultare NON abilitato.
     */
    @Test
    public void testIsAbilitato_LimitePrestiti() {
        // Aggiungiamo 3 prestiti
        for(int i=0; i<3; i++) {
            studente.aggiungiPrestito(new Prestito(null, studente, LocalDate.now(), LocalDate.now(), null));
        }
        assertFalse(studente.isAbilitato(), "Studente con 3 prestiti non deve essere abilitato");
    }

    /**
     * @brief Test di isAbilitato() - Scenario Sanzione Permanente.
     * @test Verifica che la stringa "Categoria 3" inibisca l'abilitazione.
     */
    @Test
    public void testIsAbilitato_SanzioneCategoria3() {
        studente.setSanzione("Categoria 3 (Blocco Permanente)");
        assertFalse(studente.isAbilitato(), "Studente con Categoria 3 non deve essere abilitato");
    }

    /**
     * @brief Test White-Box avanzato per Categoria 2 (Blocco attivo).
     * @test Verifica che il blocco temporaneo (30gg) sia efficace se la restituzione è recente.
     * @throws NoSuchFieldException Se il campo 'prestito' non esiste.
     * @throws IllegalAccessException Se la reflection non riesce ad accedere al campo privato.
     */
    @Test
    public void testIsAbilitato_SanzioneCategoria2_NonScaduta() throws NoSuchFieldException, IllegalAccessException {
        studente.setSanzione("Categoria 2 (Blocco 30gg)");
        
        // Creiamo un prestito restituito oggi (quindi blocco attivo perché < 30gg)
        Prestito prestitoRecente = new Prestito(null, studente, LocalDate.now().minusDays(50), LocalDate.now().minusDays(20), LocalDate.now());
        
        // --- INIZIO REFLECTION ---
        Field field = Studente.class.getDeclaredField("prestito");
        field.setAccessible(true); // Bypass dell'incapsulamento
        field.set(studente, prestitoRecente); // Injection
        // --- FINE REFLECTION ---

        assertFalse(studente.isAbilitato(), "Blocco Categoria 2 attivo se restituzione < 30gg fa");
    }
    
    /**
     * @brief Test White-Box avanzato per Categoria 2 (Blocco scaduto).
     * @test Verifica che lo studente torni abilitato se sono passati più di 30 giorni dalla restituzione.
     * @note Utilizza **Java Reflection** per manipolare lo stato interno.
     */
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
     * @brief Test della logica booleana complessa di isRitardo().
     * @test Copre tre rami decisionali (Branch Coverage):
     * 1. Studente abilitato -> Nessun ritardo.
     * 2. Studente disabilitato per limite prestiti -> Nessun ritardo (False).
     * 3. Studente disabilitato per Sanzione ma con slot liberi -> Ritardo (True).
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
    }
}