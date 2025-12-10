/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.strumenti.Elenco;
import it.unisa.diem.gruppo01.strumenti.Studente;
import java.util.TreeSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Marco Diano'
 */
public class ElencoTest {
    
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
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getElencoStudenti method, of class Elenco.
     */
    @Test
    public void testGetElencoStudenti() {
        System.out.println("getElencoStudenti");
        Elenco instance = new Elenco();
        TreeSet<Studente> expResult = null;
        TreeSet<Studente> result = instance.getElencoStudenti();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aggiungiStudente method, of class Elenco.
     */
    @Test
    public void testAggiungiStudente() {
        System.out.println("aggiungiStudente");
        Studente nuovoStudente = null;
        Elenco instance = new Elenco();
        boolean expResult = false;
        boolean result = instance.aggiungiStudente(nuovoStudente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modificaStudente method, of class Elenco.
     */
    @Test
    public void testModificaStudente() {
        System.out.println("modificaStudente");
        String matricola = "";
        String nuovoNome = "";
        String nuovoCognome = "";
        String nuovaEmail = "";
        Elenco instance = new Elenco();
        boolean expResult = false;
        boolean result = instance.modificaStudente(matricola, nuovoNome, nuovoCognome, nuovaEmail);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminaStudente method, of class Elenco.
     */
    @Test
    public void testEliminaStudente() {
        System.out.println("eliminaStudente");
        String matricola = "";
        Elenco instance = new Elenco();
        boolean expResult = false;
        boolean result = instance.eliminaStudente(matricola);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Elenco.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Elenco instance = new Elenco();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
