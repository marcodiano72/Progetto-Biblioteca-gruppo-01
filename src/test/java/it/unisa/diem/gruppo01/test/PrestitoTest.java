/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.strumenti.Libro;
import it.unisa.diem.gruppo01.strumenti.Prestito;
import it.unisa.diem.gruppo01.strumenti.Studente;
import java.time.LocalDate;
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
public class PrestitoTest {
    
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
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLibro method, of class Prestito.
     */
    @Test
    public void testGetLibro() {
        System.out.println("getLibro");
        Prestito instance = null;
        Libro expResult = null;
        Libro result = instance.getLibro();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLibro method, of class Prestito.
     */
    @Test
    public void testSetLibro() {
        System.out.println("setLibro");
        Libro libro = null;
        Prestito instance = null;
        instance.setLibro(libro);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStudente method, of class Prestito.
     */
    @Test
    public void testGetStudente() {
        System.out.println("getStudente");
        Prestito instance = null;
        Studente expResult = null;
        Studente result = instance.getStudente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStudente method, of class Prestito.
     */
    @Test
    public void testSetStudente() {
        System.out.println("setStudente");
        Studente studente = null;
        Prestito instance = null;
        instance.setStudente(studente);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataInizio method, of class Prestito.
     */
    @Test
    public void testGetDataInizio() {
        System.out.println("getDataInizio");
        Prestito instance = null;
        LocalDate expResult = null;
        LocalDate result = instance.getDataInizio();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDataInizio method, of class Prestito.
     */
    @Test
    public void testSetDataInizio() {
        System.out.println("setDataInizio");
        LocalDate dataInizio = null;
        Prestito instance = null;
        instance.setDataInizio(dataInizio);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataScadenza method, of class Prestito.
     */
    @Test
    public void testGetDataScadenza() {
        System.out.println("getDataScadenza");
        Prestito instance = null;
        LocalDate expResult = null;
        LocalDate result = instance.getDataScadenza();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDataScadenza method, of class Prestito.
     */
    @Test
    public void testSetDataScadenza() {
        System.out.println("setDataScadenza");
        LocalDate dataScadenza = null;
        Prestito instance = null;
        instance.setDataScadenza(dataScadenza);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDataRestituzione method, of class Prestito.
     */
    @Test
    public void testGetDataRestituzione() {
        System.out.println("getDataRestituzione");
        Prestito instance = null;
        LocalDate expResult = null;
        LocalDate result = instance.getDataRestituzione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDatarestituzione method, of class Prestito.
     */
    @Test
    public void testSetDatarestituzione() {
        System.out.println("setDatarestituzione");
        LocalDate dataRestituzione = null;
        Prestito instance = null;
        instance.setDatarestituzione(dataRestituzione);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPrestitoAttivo method, of class Prestito.
     */
    @Test
    public void testIsPrestitoAttivo() {
        System.out.println("isPrestitoAttivo");
        Prestito instance = null;
        boolean expResult = false;
        boolean result = instance.isPrestitoAttivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcolaGiorniRitardo method, of class Prestito.
     */
    @Test
    public void testCalcolaGiorniRitardo() {
        System.out.println("calcolaGiorniRitardo");
        Prestito instance = null;
        int expResult = 0;
        int result = instance.calcolaGiorniRitardo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gestioneSanzioni method, of class Prestito.
     */
    @Test
    public void testGestioneSanzioni() {
        System.out.println("gestioneSanzioni");
        Prestito instance = null;
        String expResult = "";
        String result = instance.gestioneSanzioni();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Prestito.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Prestito instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
