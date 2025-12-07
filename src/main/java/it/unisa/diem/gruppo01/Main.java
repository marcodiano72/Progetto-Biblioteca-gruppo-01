/**
*@file Main.java
*@brief Questo file è il punto di ingresso dell'applicazione JavaFX
*
*@version 1.0
*/

package it.unisa.diem.gruppo01;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
     * Il metodo start è il punto di inizio principale per tutte le applicazioni JavaFX.
     * Viene chiamato automaticamente dopo che l'applicazione è stata lanciata.
     *
     * @param primaryStage Lo Stage (la finestra principale) fornito dal sistema.
     * @throws IOException Se il file FXML specificato non può essere caricato.
    */
    @Override
    public void start(Stage primaryStage) throws IOException {

       Parent root = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Interfaccia1View.fxml"));

        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestione Biblioteca");
        primaryStage.show();
    }

    /*
     * Il metodo main standard di Java.
     * L'unica sua funzione è chiamare il metodo
     * che avvia JavaFX e chiama il metodo start()}.
     *
     * @param args Gli argomenti della linea di comando
    */
    public static void main(String[] args) {
        launch(args);
    }
}
