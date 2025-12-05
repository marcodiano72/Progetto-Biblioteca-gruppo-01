/*package it.unisa.diem.gruppo01;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
    
}*/
package it.unisa.diem.gruppo01;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

       Parent root = FXMLLoader.load(getClass().getResource("/it/unisa/diem/gruppo01/interfacce/Interfaccia1View.fxml"));

        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestione Biblioteca");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
