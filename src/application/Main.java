package application;

import components.VoiceInput;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Start_fxml.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Maze");
            stage.setScene(scene); 
            stage.show();           
        } catch (Exception e) {
            System.out.println("Error loading start scene.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

