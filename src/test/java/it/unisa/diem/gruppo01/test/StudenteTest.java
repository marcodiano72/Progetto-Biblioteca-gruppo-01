package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.classi.Prestito;
import it.unisa.diem.gruppo01.classi.Studente;
import java.time.LocalDate;
import it.unisa.diem.gruppo01.classi.Libro; // Necessario per creare Prestito
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe Mock di Prestito semplificata per i test di Studente.
 * (Poiché Prestito è usato solo per la lista e le costanti, un mock non è strettamente necessario,
 * ma per coerenza usiamo oggetti Prestito reali.)
 */
class ProvaPrestito extends Prestito {
    public ProvaPrestito(int id) {
        // Parametri fittizi per il costruttore di Prestito
        super(new Libro("ISBN" + id, "Titolo", "Autore", LocalDate.now(), 1), 
              new Studente("Cognome", "Nome", "Matricola", "email@test.it", "Nessuna", false), 
              LocalDate.now(), LocalDate.now().plusDays(50), null);
    }
}

/**
 * Classe di test per la classe Studente.
 */
public class StudenteTest {
    
    private Studente studenteDiTest;
    private final String COGNOME_BASE = "Rossi";
    private final String NOME_BASE = "Mario";
    private final String MATRICOLA_BASE = "M1000";
    private final String EMAIL_BASE = "mario.rossi@unisa.it";
    
    @BeforeEach
    public void setUp() {
        // Inizializza uno studente pulito prima di ogni test
        // Studente(cognome, nome, matricola, email, sanzione_iniziale(ignorata), ritardo_iniziale(ignorato))
        studenteDiTest = new Studente(COGNOME_BASE, NOME_BASE, MATRICOLA_BASE, EMAIL_BASE, "Ignorato", true); 
        // Nota: Il costruttore sovrascrive sanzione a "Nessuna" e ritardo a false.
    }

    /**
     * Test of getNome method, of class Studente.
     */
    @Test
    public void testGetNome() {
        assertEquals(NOME_BASE, studenteDiTest.getNome());
    }

    /**
     * Test of setNome method, of class Studente.
     */
    @Test
    public void testSetNome() {
        String nuovoNome = "Giuseppe";
        studenteDiTest.setNome(nuovoNome);
        assertEquals(nuovoNome, studenteDiTest.getNome());
    }

    /**
     * Test of getCognome method, of class Studente.
     */
    @Test
    public void testGetCognome() {
        assertEquals(COGNOME_BASE, studenteDiTest.getCognome());
    }

    /**
     * Test of setCognome method, of class Studente.
     */
    @Test
    public void testSetCognome() {
        String nuovoCognome = "Verdi";
        studenteDiTest.setCognome(nuovoCognome);
        assertEquals(nuovoCognome, studenteDiTest.getCognome());
    }

    /**
     * Test of getMatricola method, of class Studente.
     */
    @Test
    public void testGetMatricola() {
        assertEquals(MATRICOLA_BASE, studenteDiTest.getMatricola());
    }

    /**
     * Test of setMatricola method, of class Studente.
     */
    @Test
    public void testSetMatricola() {
        String nuovaMatricola = "M2000";
        studenteDiTest.setMatricola(nuovaMatricola);
        assertEquals(nuovaMatricola, studenteDiTest.getMatricola());
    }

    /**
     * Test of getEmail method, of class Studente.
     */
    @Test
    public void testGetEmail() {
        assertEquals(EMAIL_BASE, studenteDiTest.getEmail());
    }

    /**
     * Test of setEmail method, of class Studente.
     */
    @Test
    public void testSetEmail() {
        String nuovaEmail = "nuova.email@unisa.it";
        studenteDiTest.setEmail(nuovaEmail);
        assertEquals(nuovaEmail, studenteDiTest.getEmail());
    }

    /**
     * Test of getPrestitiAttivi method, of class Studente.
     */
    @Test
    public void testGetPrestitiAttivi() {
        assertTrue(studenteDiTest.getPrestitiAttivi().isEmpty(), "La lista deve essere inizialmente vuota.");
        
        Prestito p1 = new ProvaPrestito(1);
        studenteDiTest.aggiungiPrestito(p1);
        
        List<Prestito> lista = studenteDiTest.getPrestitiAttivi();
        assertEquals(1, lista.size(), "La lista deve contenere l'elemento aggiunto.");
        assertTrue(lista.contains(p1), "La lista deve contenere l'oggetto Prestito corretto.");
    }

    /**
     * Test of aggiungiPrestito method, of class Studente.
     */
    @Test
    public void testAggiungiPrestito() {
        Prestito p1 = new ProvaPrestito(1);
        studenteDiTest.aggiungiPrestito(p1);
        
        assertEquals(1, studenteDiTest.contaPrestitiAttivi(), "Il conteggio deve essere 1 dopo l'aggiunta.");
        assertTrue(studenteDiTest.getPrestitiAttivi().contains(p1), "L'oggetto deve essere presente nella lista.");
    }

    /**
     * Test of contaPrestitiAttivi method, of class Studente.
     */
    @Test
    public void testContaPrestitiAttivi() {
        assertEquals(0, studenteDiTest.contaPrestitiAttivi(), "Il conteggio iniziale deve essere 0.");
        
        studenteDiTest.aggiungiPrestito(new ProvaPrestito(1));
        studenteDiTest.aggiungiPrestito(new ProvaPrestito(2));
        
        assertEquals(2, studenteDiTest.contaPrestitiAttivi(), "Il conteggio deve essere 2 dopo due aggiunte.");
    }

    /**
     * Test of getSanzione method, of class Studente.
     */
    @Test
    public void testGetSanzione() {
        assertEquals("Nessuna", studenteDiTest.getSanzione(), "Il valore iniziale deve essere 'Nessuna'.");
    }

    /**
     * Test of setSanzione method, of class Studente.
     */
    @Test
    public void testSetSanzione() {
        String sanzioneBlocco = "Blocco Permanente";
        studenteDiTest.setSanzione(sanzioneBlocco);
        assertEquals(sanzioneBlocco, studenteDiTest.getSanzione(), "La sanzione deve essere aggiornata.");
    }

    /**
     * Test of isRitardo method, of class Studente.
     */
    @Test
    public void testIsRitardo() {
        assertFalse(studenteDiTest.isRitardo(), "Il valore iniziale deve essere false.");
        
        studenteDiTest.setRitardo(true);
        assertTrue(studenteDiTest.isRitardo(), "Il ritardo deve essere true dopo l'impostazione.");
    }

    /**
     * Test of setRitardo method, of class Studente.
     */
    @Test
    public void testSetRitardo() {
        studenteDiTest.setRitardo(true);
        assertTrue(studenteDiTest.isRitardo(), "setRitardo(true) deve impostare a true.");
        
        studenteDiTest.setRitardo(false);
        assertFalse(studenteDiTest.isRitardo(), "setRitardo(false) deve impostare a false.");
    }

    /**
     * Test of isAbilitato method, of class Studente.
     */
    @Test
    public void testIsAbilitato() {
        // 1. Caso base: 0 prestiti, nessun ritardo -> Abilitato (TRUE)
        assertTrue(studenteDiTest.isAbilitato(), "Deve essere abilitato: 0 prestiti, nessun ritardo.");
        
        // 2. Caso: PrestitoAttivo < LIMITE (es. 2/3), nessun ritardo -> Abilitato (TRUE)
        studenteDiTest.aggiungiPrestito(new ProvaPrestito(1)); // 1 prestito attivo
        studenteDiTest.aggiungiPrestito(new ProvaPrestito(2)); // 2 prestiti attivi
        assertFalse(studenteDiTest.isRitardo()); // Assicura che ritardo sia False
        assertTrue(studenteDiTest.isAbilitato(), "Deve essere abilitato: 2 prestiti, nessun ritardo.");
        
        // 3. Caso: PrestitoAttivo = LIMITE (es. 3/3), nessun ritardo -> Non Abilitato (FALSE)
        studenteDiTest.aggiungiPrestito(new ProvaPrestito(3)); // 3 prestiti attivi
        assertEquals(Prestito.LIMITE_PRESTITI, studenteDiTest.contaPrestitiAttivi()); // Controllo costante
        assertFalse(studenteDiTest.isAbilitato(), "Non deve essere abilitato: 3 prestiti (limite raggiunto).");
        
        // 4. Caso: Ritardo = true -> Non Abilitato (FALSE)
        studenteDiTest.setRitardo(true);
        // Riporto i prestiti sotto il limite (es. 1/3)
        studenteDiTest.rimuoviPrestito(studenteDiTest.getPrestitiAttivi().get(0));
        studenteDiTest.rimuoviPrestito(studenteDiTest.getPrestitiAttivi().get(0));
        
        assertTrue(studenteDiTest.isRitardo()); // Assicura che ritardo sia True
        assertTrue(studenteDiTest.contaPrestitiAttivi() < Prestito.LIMITE_PRESTITI); // Controllo limite ok (1/3)
        assertFalse(studenteDiTest.isAbilitato(), "Non deve essere abilitato: ritardo attivo, anche se prestiti < limite.");
    }

    /**
     * Test of rimuoviPrestito method, of class Studente.
     */
    @Test
    public void testRimuoviPrestito() {
        Prestito p1 = new ProvaPrestito(1);
        Prestito p2 = new ProvaPrestito(2);
        
        studenteDiTest.aggiungiPrestito(p1);
        studenteDiTest.aggiungiPrestito(p2);
        assertEquals(2, studenteDiTest.contaPrestitiAttivi());
        
        studenteDiTest.rimuoviPrestito(p1);
        
        assertEquals(1, studenteDiTest.contaPrestitiAttivi(), "Il conteggio deve essere 1 dopo la rimozione.");
        assertFalse(studenteDiTest.getPrestitiAttivi().contains(p1), "P1 non deve essere più presente.");
        assertTrue(studenteDiTest.getPrestitiAttivi().contains(p2), "P2 deve essere ancora presente.");
    }

    /**
     * Test of hashCode method, of class Studente.
     */
    @Test
    public void testHashCode() {
        Studente studenteUguale = new Studente(COGNOME_BASE, NOME_BASE, MATRICOLA_BASE, "email_diversa@test.it", "Sanz", true);
        Studente studenteDiverso = new Studente(COGNOME_BASE, NOME_BASE, "M9999", EMAIL_BASE, "Sanz", true);
        
        assertEquals(studenteDiTest.hashCode(), studenteUguale.hashCode(), "L'hashCode deve essere uguale per la stessa matricola.");
        assertNotEquals(studenteDiTest.hashCode(), studenteDiverso.hashCode(), "L'hashCode deve essere diverso per matricole diverse.");
    }

    /**
     * Test of equals method, of class Studente.
     */
    @Test
    public void testEquals() {
        Studente studenteUguale = new Studente("NomeDivers", "CognomeDivers", MATRICOLA_BASE, "email_diversa@test.it", "Sanz", true);
        Studente studenteDiverso = new Studente(COGNOME_BASE, NOME_BASE, "M9999", EMAIL_BASE, "Sanz", true);
        
        // Uguaglianza (stessa matricola)
        assertTrue(studenteDiTest.equals(studenteUguale), "Deve essere true se la matricola è la stessa.");
        
        // Riflessività
        assertTrue(studenteDiTest.equals(studenteDiTest), "Deve essere true per lo stesso oggetto.");

        // Diversità (matricola diversa)
        assertFalse(studenteDiTest.equals(studenteDiverso), "Deve essere false se la matricola è diversa.");
        
        // Diversità (oggetto null)
        assertFalse(studenteDiTest.equals(null), "Deve essere false se l'oggetto è null.");
        
        // Diversità (classe diversa)
        assertFalse(studenteDiTest.equals(new Object()), "Deve essere false se la classe è diversa.");
    }

    /**
     * Test of toString method, of class Studente.
     */
    @Test
    public void testToString() {
        studenteDiTest.aggiungiPrestito(new ProvaPrestito(1));
        studenteDiTest.setSanzione("Blocco Test");
        studenteDiTest.setRitardo(false); // 1 prestito < 3, nessun ritardo -> Abilitato = true
        
        String result = studenteDiTest.toString();
        
        assertTrue(result.contains("Nome: " + NOME_BASE));
        assertTrue(result.contains("Matricola: " + MATRICOLA_BASE));
        assertTrue(result.contains("Prestiti Attivi: 1"));
        assertTrue(result.contains("Stato Sanzione: Blocco Test"));
        assertTrue(result.contains("Abilitato al Prestito: true"));
    }
}