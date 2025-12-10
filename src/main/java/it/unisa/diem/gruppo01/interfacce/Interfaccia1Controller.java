
/**
*@file Interfaccia1Controller.java
*@brief Controller FXML che gestisce l'interfaccia di login dell'applicazione.
*
*@version 1.0
*/
package it.unisa.diem.gruppo01.interfacce;

// Import necessari per la gestione della nuova scena
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node; // Serve per ottenere lo Stage dall'evento

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
 * FXML Controller class
 * Controller FXML: Interfaccia1Controller (Login)
 * Controllore FXML per l'interfaccia di login (Interfaccia 1).
 * Gestisce l'autenticazione dell'utente confrontando le credenziali inserite
 * con quelle predefinite e, in caso di successo, carica l'interfaccia del Menu Principale.
 *
 */
public class Interfaccia1Controller implements Initializable {

    // Costanti per le credenziali corrette
    private static final String NOME_UTENTE_CORRETTO = "MARCO"; ///< Nome utente corretto per l'accesso
    private static final String PASSWORD_CORRETTA = "1234"; ///< Password corretta per l'accesso.
    
    // FXML Elements
    @FXML
    private Button accessButton;
    @FXML
    private PasswordField passwordFile;
    @FXML
    private TextField usernameField;
    @FXML
    private Label errorMessageLabel; 

    /*
    * Metodo chiamato per inizializzare un controller dopo che il suo elemento radice è stato completamente elaborato.
     * Inizializza l'etichetta degli errori  errorMessageLabel con una stringa vuota.
     *
     * @param url L'ubicazione relativa o assoluta del file FXML.
     * @param rb Le risorse utilizzate per localizzare l'oggetto radice.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (errorMessageLabel != null) {
            errorMessageLabel.setText("");
        }
    }

    /**
     * Gestisce l'evento di pressione del tasto "ACCEDI".
     * Se le credenziali sono corrette (nome utente case-insensitive, password esatta),
     * procede al caricamento e alla visualizzazione della scena del Menu Principale (Menu_Biblioteca.fxml).
     * In caso di credenziali errate, visualizza un messaggio di errore e pulisce i campi.
     * @param event L'evento di azione generato dal clic
     */
   @FXML
    private void openMenu(ActionEvent event) {
        String usernameInserito = usernameField.getText();
        String passwordInserita = passwordFile.getText();

        if (usernameInserito.equals(NOME_UTENTE_CORRETTO) && passwordInserita.equals(PASSWORD_CORRETTA)) {
            
            // ACCESSO RIUSCITO
            System.out.println("Accesso Eseguito con successo! Caricamento del Menu...");
            
            try {
               
                Parent menuParent = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Menu_Biblioteca.fxml"));
                
                Scene menuScene = new Scene(menuParent);
                
                
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(menuScene);
                window.setTitle("Gestione Biblioteca - Menu Principale"); 
                window.show();
                
            } catch (IOException e) {
                // Stampa l'errore completo per capire cosa non ha trovato (File Not Found o classe non trovata)
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
            passwordFile.setText("");
            usernameField.setText("");
        }
    }
}