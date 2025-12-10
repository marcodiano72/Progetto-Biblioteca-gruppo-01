/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.classi.Prestito;
import it.unisa.diem.gruppo01.classi.Studente;
import java.util.List;
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
public class StudenteTest {
    
    public StudenteTest() {
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
     * Test of getNome method, of class Studente.
     */
    @Test
    public void testGetNome() {
        System.out.println("getNome");
        Studente instance = null;
        String expResult = "";
        String result = instance.getNome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNome method, of class Studente.
     */
    @Test
    public void testSetNome() {
        System.out.println("setNome");
        String nome = "";
        Studente instance = null;
        instance.setNome(nome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCognome method, of class Studente.
     */
    @Test
    public void testGetCognome() {
        System.out.println("getCognome");
        Studente instance = null;
        String expResult = "";
        String result = instance.getCognome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCognome method, of class Studente.
     */
    @Test
    public void testSetCognome() {
        System.out.println("setCognome");
        String cognome = "";
        Studente instance = null;
        instance.setCognome(cognome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMatricola method, of class Studente.
     */
    @Test
    public void testGetMatricola() {
        System.out.println("getMatricola");
        Studente instance = null;
        String expResult = "";
        String result = instance.getMatricola();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMatricola method, of class Studente.
     */
    @Test
    public void testSetMatricola() {
        System.out.println("setMatricola");
        String matricola = "";
        Studente instance = null;
        instance.setMatricola(matricola);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmail method, of class Studente.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        Studente instance = null;
        String expResult = "";
        String result = instance.getEmail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEmail method, of class Studente.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "";
        Studente instance = null;
        instance.setEmail(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrestitiAttivi method, of class Studente.
     */
    @Test
    public void testGetPrestitiAttivi() {
        System.out.println("getPrestitiAttivi");
        Studente instance = null;
        List<Prestito> expResult = null;
        List<Prestito> result = instance.getPrestitiAttivi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of aggiungiPrestito method, of class Studente.
     */
    @Test
    public void testAggiungiPrestito() {
        System.out.println("aggiungiPrestito");
        Prestito p = null;
        Studente instance = null;
        instance.aggiungiPrestito(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contaPrestitiAttivi method, of class Studente.
     */
    @Test
    public void testContaPrestitiAttivi() {
        System.out.println("contaPrestitiAttivi");
        Studente instance = null;
        int expResult = 0;
        int result = instance.contaPrestitiAttivi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSanzione method, of class Studente.
     */
    @Test
    public void testGetSanzione() {
        System.out.println("getSanzione");
        Studente instance = null;
        String expResult = "";
        String result = instance.getSanzione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSanzione method, of class Studente.
     */
    @Test
    public void testSetSanzione() {
        System.out.println("setSanzione");
        String sanzione = "";
        Studente instance = null;
        instance.setSanzione(sanzione);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isRitardo method, of class Studente.
     */
    @Test
    public void testIsRitardo() {
        System.out.println("isRitardo");
        Studente instance = null;
        boolean expResult = false;
        boolean result = instance.isRitardo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRitardo method, of class Studente.
     */
    @Test
    public void testSetRitardo() {
        System.out.println("setRitardo");
        boolean ritardo = false;
        Studente instance = null;
        instance.setRitardo(ritardo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAbilitato method, of class Studente.
     */
    @Test
    public void testIsAbilitato() {
        System.out.println("isAbilitato");
        Studente instance = null;
        boolean expResult = false;
        boolean result = instance.isAbilitato();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rimuoviPrestito method, of class Studente.
     */
    @Test
    public void testRimuoviPrestito() {
        System.out.println("rimuoviPrestito");
        Prestito p = null;
        Studente instance = null;
        instance.rimuoviPrestito(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Studente.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Studente instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Studente.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Studente instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Studente.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Studente instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
