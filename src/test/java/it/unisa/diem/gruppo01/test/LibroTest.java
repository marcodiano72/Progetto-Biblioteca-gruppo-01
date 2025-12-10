/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.strumenti.Libro;
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
public class LibroTest {
    
    public LibroTest() {
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
     * Test of getIsbn method, of class Libro.
     */
    @Test
    public void testGetIsbn() {
        System.out.println("getIsbn");
        Libro instance = null;
        String expResult = "";
        String result = instance.getIsbn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTitolo method, of class Libro.
     */
    @Test
    public void testGetTitolo() {
        System.out.println("getTitolo");
        Libro instance = null;
        String expResult = "";
        String result = instance.getTitolo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAutore method, of class Libro.
     */
    @Test
    public void testGetAutore() {
        System.out.println("getAutore");
        Libro instance = null;
        String expResult = "";
        String result = instance.getAutore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnnoPb method, of class Libro.
     */
    @Test
    public void testGetAnnoPb() {
        System.out.println("getAnnoPb");
        Libro instance = null;
        LocalDate expResult = null;
        LocalDate result = instance.getAnnoPb();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumCopie method, of class Libro.
     */
    @Test
    public void testGetNumCopie() {
        System.out.println("getNumCopie");
        Libro instance = null;
        int expResult = 0;
        int result = instance.getNumCopie();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumCopia method, of class Libro.
     */
    @Test
    public void testSetNumCopia() {
        System.out.println("setNumCopia");
        int numCopie = 0;
        Libro instance = null;
        instance.setNumCopia(numCopie);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDisponibile method, of class Libro.
     */
    @Test
    public void testIsDisponibile() {
        System.out.println("isDisponibile");
        Libro instance = null;
        boolean expResult = false;
        boolean result = instance.isDisponibile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementaCopie method, of class Libro.
     */
    @Test
    public void testIncrementaCopie() {
        System.out.println("incrementaCopie");
        int quantita = 0;
        Libro instance = null;
        instance.incrementaCopie(quantita);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decrementaCopie method, of class Libro.
     */
    @Test
    public void testDecrementaCopie() {
        System.out.println("decrementaCopie");
        Libro instance = null;
        boolean expResult = false;
        boolean result = instance.decrementaCopie();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Libro.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Libro instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Libro.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Libro instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Libro.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Libro instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
