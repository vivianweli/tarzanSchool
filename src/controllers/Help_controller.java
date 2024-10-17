package controllers;

import java.io.IOException;
import static components.GameValue.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Help_controller {

    @FXML
    private Button back;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void OnReturn(ActionEvent event) {
        if (IsStart) {
            anchorPane.getScene().getWindow().hide();
        } else {
            loadScene("/fxmls/Start_fxml.fxml", event);
        }
    }
    
    private void loadScene(String fxmlPath, ActionEvent event) {
        try {
            // load the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // get the current scene
            Stage stage = (Stage) anchorPane.getScene().getWindow();

            // Set new scene
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
            stage.show();  // Show new scene
        } catch (IOException e) {
            System.out.println("Error loading scene: " + fxmlPath);
            e.printStackTrace();
        }
    }
}