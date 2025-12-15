/**
* @file Interfaccia1Controller.java
* @brief Controller FXML che gestisce l'interfaccia di login dell'applicazione.
* Gestisce l'autenticazione dell'utente e il passaggio alla schermata principale.
* @author Gruppo01
* @version 1.0
*/
package it.unisa.diem.gruppo01.interfacce;

// Import necessari per la gestione della nuova scena
import it.unisa.diem.gruppo01.classi.Catalogo;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node; 

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label; 


/**
 * @brief CONTROLLER FXML:Interfaccia1Controller
 * Gestisce l'autenticazione dell'utente confrontando le credenziali inserite
 * con quelle predefinite e carica l'interfaccia del Menu Principale.
 * @invariant usernameField != null
 * @invariant passwordFile != null
 */
public class Interfaccia1Controller implements Initializable {
    
    // FXML Elements
    @FXML
    private Button accessButton; ///< Bottone per inviare le credenziali.
    @FXML
    private TextField usernameField; ///< Campo di testo per l'username.
    @FXML
    private PasswordField passwordFile; ///< Campo nascosto per la password.
    @FXML
    private Label errorMessageLabel; ///< Etichetta per messaggi di errore (es. login fallito).

    /**
     * @brief Inizializza il controller dopo il caricamento della vista.
     * Imposta l'etichetta di errore vuota, inizializza il catalogo singleton
     * e configura il pulsante "Accedi" come pulsante di default per abilitare l'invio con ENTER.
     * @post errorMessageLabel.getText().isEmpty() == true
     * @post accessButton.isDefaultButton() == true
     * @param[in] url L'ubicazione relativa o assoluta del file FXML.
     * @param[in] rb Le risorse utilizzate per localizzare l'oggetto radice.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (errorMessageLabel != null) {
            errorMessageLabel.setText("");
        }
        
        // Inizializza il Catalogo all'avvio dell'app 
        Catalogo.getIstanza();
        
        // CONFIGURAZIONE INVIO DA TASTIERA
        // Imposta questo bottone come "Default Button".
        // In JavaFX, premere ENTER in qualsiasi campo di testo della scena
        // attiverà automaticamente l'evento di questo bottone.
        if (accessButton != null) {
            accessButton.setDefaultButton(true);
        }
    }

    /**
     * @brief Gestisce il tentativo di accesso.
     * Verifica le credenziali hardcoded. Se corrette, carica la scena del menu.
     * Altrimenti, mostra un messaggio di errore.
     * Questo metodo viene invocato sia dal click sul bottone che dalla pressione di ENTER (grazie a setDefaultButton).
     * @param[in] event L'evento di azione generato (click o Enter).
     */
   @FXML
    private void openMenu(ActionEvent event) {
        String usernameInserito = usernameField.getText();
        String passwordInserita = passwordFile.getText();

        // Verifica credenziali
        if (usernameInserito.equals("MARCO") && passwordInserita.equals("9099")
                || usernameInserito.equals("GIO") && passwordInserita.equals("9647") 
                || usernameInserito.equals("NICK") && passwordInserita.equals("1075") 
                || usernameInserito.equals("MACUCC") && passwordInserita.equals("9473")) {
            
            // ACCESSO RIUSCITO
            System.out.println("Accesso Eseguito con successo! Caricamento del Menu...");
            
            try {
                // Carica la nuova scena
                Parent menuParent = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Menu_Biblioteca.fxml"));
                Scene menuScene = new Scene(menuParent);
                
                // Ottiene lo Stage corrente.
                // Nota: Poiché l'evento potrebbe non provenire direttamente da un Node cliccato (es. Enter su TextField),
                // è più sicuro ottenere lo stage da uno degli elementi noti della scena, come l'accessButton o lo usernameField.
                Stage window;
                if (event.getSource() instanceof Node) {
                     window = (Stage)((Node)event.getSource()).getScene().getWindow();
                } else {
                     // Fallback nel caso la source non sia un Node (raro in questo contesto ma possibile)
                     window = (Stage) accessButton.getScene().getWindow();
                }
                
                window.setScene(menuScene);
                window.setTitle("Gestione Biblioteca - Menu Principale"); 
                window.show();
                
            } catch (IOException e) {
                System.out.println("ERRORE CRITICO nel caricamento del Menu:");
                e.printStackTrace();
                
                if (errorMessageLabel != null) {
                    errorMessageLabel.setText("Errore interno: Impossibile caricare il menu.");
                }
            }
            
        } else {
            // ACCESSO FALLITO
            if (errorMessageLabel != null) {
                errorMessageLabel.setText("Nome utente o password non validi.");
            }
            // Pulisce i campi per riprovare
            passwordFile.setText("");
            usernameField.setText("");
            // Rimette il focus sul campo username per comodità
            usernameField.requestFocus();
        }
    }
}