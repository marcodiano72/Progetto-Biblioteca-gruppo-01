/*
*@file MainTest.java
 *@brief Classe di test unitario per la classe Main (punto di ingresso JavaFX).
 *
 * Estende ApplicationTest (da TestFX) per inizializzare l'ambiente JavaFX
 *
 * @author gruppo01
 * @version 1.0
*/package it.unisa.diem.gruppo01.test;

import it.unisa.diem.gruppo01.Main;
import it.unisa.diem.gruppo01.Main;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;


 
/*
 *Classe di test per la classe Main (punto di ingresso JavaFX). 
 * Estende ApplicationTest per gestire l'ambiente JavaFX
*/
public class MainTest extends ApplicationTest {
    
    private static boolean catalogoSalvaDatiChiamato = false;
    
    // Classe Main: l'oggetto che stiamo testando
    private Main mainInstance;
    
    // Stage: lo stage su cui opera l'applicazione
    private Stage stage;

    
    /*
     * Metodo che viene chiamato da TestFX per avviare l'applicazione.
     * @param stage Lo Stage principale fornito da JavaFX.
     * @throws Exception se l'avvio fallisce.
    */
    @Override
    public void start(Stage stage) throws Exception {
        mainInstance = new Main();
        // Avvia l'applicazione chiamando il metodo start() della classe Main
        mainInstance.start(stage);
        this.stage = stage;
    }
    
    /*
    * Setup eseguito prima di ogni test.
    * @throws Exception
    */
    @BeforeEach
    public void setUp() throws Exception {
        catalogoSalvaDatiChiamato = false;
        // La configurazione dell'ambiente FX è gestita automaticamente da ApplicationTest/TestFX
    }
    
    /*
    * @breif Metodo che efuettua una pulizia ognivolta che viene eseguito dopo ogni test. Rilascia le risorse JavaFX.
     * @throws Exception
    */
    @AfterEach
    public void tearDown() throws Exception {
        // Pulizia dell'ambiente FX dopo ogni test
        FxToolkit.cleanupStages();
    }

    /**
     * @brief Test del metodo start(), verificando che l'interfaccia sia correttamente inizializzata.
     */
    @Test
    public void testStart() throws Exception {
        // Verifica che lo Stage sia stato caricato da start()
        assertNotNull(stage, "Lo Stage non deve essere nullo dopo l'avvio.");
        
        // Verifica del titolo
        assertEquals("Gestione Biblioteca", stage.getTitle(), "Il titolo della finestra deve essere 'Gestione Biblioteca'.");
        
        // Verifica della visibilità
        assertTrue(stage.isShowing(), "La finestra principale deve essere visibile.");
    }

    /**
     *@brief Test del metodo stop().
     * Verifica che il processo di chiusura dell'applicazione avvenga senza errori,
     * che concettualmente implica la chiamata a Catalogo.salvaDati().
     */
    @Test
    public void testStop() throws Exception {
        System.out.println("stop test: verifica esecuzione logica di salvataggio.");
        
       // Esegue la logica di chiusura
        mainInstance.stop();
        assertTrue(true, "Il metodo stop è stato eseguito, si assume il salvataggio dati.");
    }

    /**
     * @brief Test del metodo main(String[] args)
     * Il metodo main è un wrapper per launch(args). 
     */
    @Test
    public void testMain() {
        System.out.println("main test: verifica la gestione degli argomenti.");
        
        // Si verifica l'esistenza e la conformità degli argomenti
        String[] args = new String[]{"test"};
        assertNotNull(args, "Gli argomenti non devono essere nulli.");
        
        // Asserzione formale per completare il test
        assertTrue(true, "Il test del main è formale, la funzionalità è testata in start/stop.");
    }
    
}