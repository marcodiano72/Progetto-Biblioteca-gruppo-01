/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.strumenti.Catalogo;
import it.unisa.diem.gruppo01.strumenti.Libro;
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
public class CatalogoTest {
    
    public CatalogoTest() {
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
     * Test of getInventarioLibri method, of class Catalogo.
     */
    @Test
    public void testGetInventarioLibri() {
        System.out.println("getInventarioLibri");
        Catalogo instance = new Catalogo();
        TreeSet<Libro> expResult = null;
        TreeSet<Libro> result = instance.getInventarioLibri();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aggiungiLibro method, of class Catalogo.
     */
    @Test
    public void testAggiungiLibro() {
        System.out.println("aggiungiLibro");
        Libro nuovoLibro = null;
        Catalogo instance = new Catalogo();
        boolean expResult = false;
        boolean result = instance.aggiungiLibro(nuovoLibro);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modificaLibro method, of class Catalogo.
     */
    @Test
    public void testModificaLibro() {
        System.out.println("modificaLibro");
        String isbn = "";
        int nuoveCopie = 0;
        Catalogo instance = new Catalogo();
        boolean expResult = false;
        boolean result = instance.modificaLibro(isbn, nuoveCopie);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminaLibro method, of class Catalogo.
     */
    @Test
    public void testEliminaLibro() {
        System.out.println("eliminaLibro");
        String isbn = "";
        Catalogo instance = new Catalogo();
        boolean expResult = false;
        boolean result = instance.eliminaLibro(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Catalogo.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Catalogo instance = new Catalogo();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
