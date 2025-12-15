/**
 * @file PrestitoTest.java
 * @brief  Test unitari per la classe Prestito.
 * * Questo file contiene i test per verificare la corretta gestione anagrafica,
 * la logica dei permessi (abilitazione al prestito) e il sistema sanzionatorio.
 * * @author Gruppo01
 * @version 1.0
 */


package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.classi.Libro;
import it.unisa.diem.gruppo01.classi.Prestito;
import it.unisa.diem.gruppo01.classi.Studente;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*; 
import java.time.LocalDate;

/**
 * @brief Classe di test unitari per la classe Prestito.
 * Questa classe definisce la suite di test per la verifica funzionale dell'entità Prestito.
 * @see it.unisa.diem.gruppo01.classi.Prestito
 */
public class PrestitoTest {

    private Prestito prestito;
    
    /** @brief Stub dell'oggetto Libro necessario per l'inizializzazione. */
    private Libro libroStub;
    
    /** @brief Stub dell'oggetto Studente necessario per l'inizializzazione. */
    private Studente studenteStub;
    
    private LocalDate dataInizio;
    private LocalDate dataScadenza;

    /**
     * @brief Fixture di configurazione (eseguita prima di ogni test).
     * Inizializza l'ambiente di test (Test Harness) resettando le variabili
     * e definendo una finestra temporale standard per i test.
     * Garantisce l'indipendenza dei singoli casi di test.
     */
    @BeforeEach
    public void setUp() {
        // Arrange: Configurazione iniziale
        // Utilizziamo null come stub: per questi test non serve invocare metodi su Libro o Studente
        libroStub = null; 
        studenteStub = null; 
        
        // Impostiamo una data di inizio nel passato (30 giorni fa)
        dataInizio = LocalDate.now().minusDays(30);
        // La scadenza è calcolata sommando la durata standard prevista dalla classe Prestito
        dataScadenza = dataInizio.plusDays(Prestito.DURATA_PRESTITO); 
    }

    /**
     * @brief Verifica il corretto funzionamento del Costruttore e dei Getter.
     * Controlla che l'oggetto venga istanziato correttamente e che i valori
     * incapsulati corrispondano a quelli forniti in fase di costruzione.
     */
    @Test
    public void testCostruttoreEGetter() {
        // Act
        prestito = new Prestito(libroStub, studenteStub, dataInizio, dataScadenza, null);
        
        // Assert
        assertNotNull(prestito, "L'istanza creata non deve essere null"); 
        assertEquals(dataInizio, prestito.getDataInizio(), "La data di inizio deve coincidere con quella fornita"); 
        assertEquals(dataScadenza, prestito.getDataScadenza(), "La data di scadenza deve coincidere con quella calcolata");
        assertNull(prestito.getDataRestituzione(), "Alla creazione, la data di restituzione deve essere null"); 
    }

    /**
     * @brief Verifica il metodo isPrestitoAttivo().
     * Obiettivo: Branch Coverage.
     * Verifica i due stati possibili del ciclo di vita del prestito:
     * 1. Attivo (Restituzione == null).
     * 2. Concluso (Restituzione != null).
     */
    @Test
    public void testIsPrestitoAttivo() {
        // Branch 1: Prestito ancora in corso
        prestito = new Prestito(libroStub, studenteStub, dataInizio, dataScadenza, null);
        assertTrue(prestito.isPrestitoAttivo(), "Deve restituire true se la data di restituzione è null"); 

        // Branch 2: Prestito chiuso
        prestito.setDatarestituzione(LocalDate.now());
        assertFalse(prestito.isPrestitoAttivo(), "Deve restituire false dopo aver settato la data di restituzione"); 
    }

    /**
     * @brief Test calcolo giorni ritardo: Scenario "Nessun Ritardo".
     * Verifica che il sistema restituisca 0 se il libro viene restituito
     * entro la data di scadenza prevista.
     */
    @Test
    public void testCalcolaGiorniRitardo_NessunRitardo() {
        // Arrange: Restituzione contestuale alla creazione (oggi)
        // Poiché scadenza è futura, siamo in anticipo.
        prestito = new Prestito(libroStub, studenteStub, dataInizio, dataScadenza, LocalDate.now());
        
        // Act
        int ritardo = prestito.calcolaGiorniRitardo();
        
        // Assert
        assertEquals(0, ritardo, "Il ritardo deve essere 0 se restituito in anticipo o puntuale");
    }

    /**
     * @brief Test calcolo giorni ritardo: Scenario "Ritardo Consolidato".
     * Verifica il calcolo su un prestito già concluso (dataRestituzione != null).
     * Il ritardo è la differenza tra Data Restituzione e Data Scadenza.
     */
    @Test
    public void testCalcolaGiorniRitardo_ConRitardoRestituito() {
        // Arrange: Scadenza passata (5 giorni fa), Restituito oggi
        LocalDate scadenzaPassata = LocalDate.now().minusDays(5);
        prestito = new Prestito(libroStub, studenteStub, dataInizio, scadenzaPassata, LocalDate.now());

        // Act & Assert
        assertEquals(5, prestito.calcolaGiorniRitardo(), "Deve calcolare i giorni tra la scadenza passata e la restituzione odierna");
    }

    /**
     * @brief Test calcolo giorni ritardo: Scenario "Ritardo in Corso".
     * Verifica il calcolo su un prestito ancora attivo (dataRestituzione == null).
     * Il sistema deve usare LocalDate.now() come riferimento per il calcolo.
     */
    @Test
    public void testCalcolaGiorniRitardo_AttivoInRitardo() {
        // Arrange: Scadenza passata (10 giorni fa), NON ancora restituito
        LocalDate scadenzaPassata = LocalDate.now().minusDays(10);
        prestito = new Prestito(libroStub, studenteStub, dataInizio, scadenzaPassata, null);

        // Act & Assert
        assertEquals(10, prestito.calcolaGiorniRitardo(), "Per prestiti attivi, il ritardo si calcola rispetto alla data corrente");
    }

   
    

    /**
     * @brief Verifica Sanzioni: Classe di Equivalenza "Nessun Ritardo".
     */
    @Test
    public void testGestioneSanzioni_NessunRitardo() {
        prestito = createPrestitoScadutoGiorniFa(0);
        assertEquals(Prestito.NESSUN_RITARDO, prestito.gestioneSanzioni());
    }

    
    
    /**
     * @brief Verifica Sanzioni: Classe "Blocco Lieve".
     * Range: 1 < <= Ritardo <= +10
     */
    @Test
    public void testGestioneSanzioni_Categoria1_BloccoLieve() {
        // Test Valore Intermedio
        prestito = createPrestitoScadutoGiorniFa(5);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 1"));
        assertTrue(prestito.gestioneSanzioni().contains("Blocco lieve"));

        // Test Valore Limite (Boundary)
        prestito = createPrestitoScadutoGiorniFa(10);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 1"), "Il giorno 10 è il limite superiore della Categoria 1");
    }

    
    /**
     * @brief Verifica Sanzioni: Classe "Blocco 30 Giorni".
     * Range: 11 <= Ritardo <= 20.
     */
    @Test
    public void testGestioneSanzioni_Categoria2_Blocco30Giorni() {
        // Test Valore Limite (Boundary Inferiore)
        prestito = createPrestitoScadutoGiorniFa(11);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 2"), "Il giorno 11 è il limite inferiore della Categoria 2");

        // Test Valore Intermedio
        prestito = createPrestitoScadutoGiorniFa(15);
        assertTrue(prestito.gestioneSanzioni().contains("Blocco 30 giorni"));
        
        // Test Valore Limite (Boundary Superiore)
        prestito = createPrestitoScadutoGiorniFa(20);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 2"), "Il giorno 20 è il limite superiore della Categoria 2");
    }
    

    /**
     * @brief Verifica Sanzioni: Classe "Blocco Permanente".
     * Range: Ritardo > 20.
     */
    @Test
    public void testGestioneSanzioni_Categoria3_BloccoPermanente() {
        // Test Valore Limite (Boundary Inferiore)
        prestito = createPrestitoScadutoGiorniFa(21);
        assertTrue(prestito.gestioneSanzioni().contains("Categoria 3"), "Il giorno 21 è il limite inferiore della Categoria 3");
        assertTrue(prestito.gestioneSanzioni().contains("Blocco PERMANENTE"));
    }

    
    /**
     * @brief Metodo helper per la creazione rapida di prestiti scaduti.
     * Crea un'istanza di Prestito con una data di scadenza calcolata
     * sottraendo il numero di giorni specificato dalla data odierna.
     * @param[in] giorniFa Numero di giorni trascorsi dalla scadenza (Ritardo).
     * @return Istanza di Prestito configurata per il test.
     */
    private Prestito createPrestitoScadutoGiorniFa(int giorniFa) {
        LocalDate scadenza = LocalDate.now().minusDays(giorniFa);
        // Passando null come dataRestituzione, forziamo il calcolo del ritardo su "Oggi"
        return new Prestito(libroStub, studenteStub, dataInizio, scadenza, null);
    }
}