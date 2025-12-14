/**
*@file Main.java
*@brief Questo file è il punto di ingresso dell'applicazione JavaFX
*@author gruppo01
*@version 1.0
*/

package it.unisa.diem.gruppo01;
import it.unisa.diem.gruppo01.classi.Catalogo;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


/*
* Classe Main
 * La classe estende Application ed è il punto di ingresso
 * dell'applicazione JavaFX della Gestione di una Biblioteca. Si occupa di caricare l'interfaccia utente iniziale
 * definita nel file FXML e di avviare la finestra principale.
*/
public class Main extends Application {

    
    /*
     * @brief Il metodo start è il punto di inizio principale per tutte le applicazioni JavaFX.
     * Viene chiamato automaticamente dopo che l'applicazione è stata lanciata.
     * @param primaryStage Lo Stage (la finestra principale) fornito dal sistema.
     * @throws IOException Se il file FXML specificato non può essere caricato.
    */
    @Override
    public void start(Stage primaryStage) throws IOException {

       Catalogo.getIstanza(); ///< Assicura che il catalogo sia pronto e che i dati siano disponibili prima che l'interfaccia utente tenti di accedervi.
       Parent root = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Interfaccia1View.fxml"));

        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestione Biblioteca");
        primaryStage.show();
    }

    
    /*@brief Metodo per il salvataggio automatico alla chiusura.
    *Viene invocato in metodo sul contenitore dei libri, per dalvare lo 
    *stato corrente dell'inverntario sul file csv, prima che 
    *l'applicazione chiuda
    * @throws Exception Se si verifica un errore durante l'operazione di salvataggio.
    */
    @Override
    public void stop() throws Exception
    {
        Catalogo.salvaDati();
        super.stop();
    }
    
    /*
     * @brief Il metodo main standard di Java.
     * L'unica sua funzione è chiamare il metodo
     * che avvia JavaFX e chiama il metodo start()}.
     *
     * @param args Gli argomenti della linea di comando
    */
    public static void main(String[] args) {
        launch(args);
    }
}
