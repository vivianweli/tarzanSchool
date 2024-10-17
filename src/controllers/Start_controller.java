package controllers;

import java.io.IOException;
import static components.GameValue.*;

import components.VoiceInput;  // import the VoiceInput component
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Start_controller {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button character;

    @FXML
    private Button help;

    @FXML
    private Button quit;

    @FXML
    private Button start;

    private VoiceInput voiceInput;  // Sound input detector

    @FXML
    void OnCharacter(ActionEvent event) {
        OpenNewWindow("/fxmls/Character_fxml.fxml", "OnCharacter");
    }

    @FXML
    void OnHelp(ActionEvent event) {
        OpenNewWindow("/fxmls/Help_fxml.fxml", "OnHelp");
    }

    @FXML
    void OnQuit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void OnStart(ActionEvent event) {
    	try {
            parent = (Stage) anchorPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/Game_fxml.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root,800,850); 
            Stage stage = new Stage();
            stage.setResizable(true);  // Allows resizing the window

            stage.setScene(scene);
            stage.initOwner(anchorPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            parent.hide();
            stage.setOnCloseRequest(e -> {
            	IsStart = false;
                if (TIMELINE != null) {
                    TIMELINE.stop();
                    TIMELINE = null;
                }
                parent.show();
            });
            parent.close();
            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("Error on [Class:StartController]=>");
            e.printStackTrace();
        }

    }

    @FXML
    private Stage parent;
    public static Timeline TIMELINE;

    public void OpenNewWindow(String filePath, String method) {
        try {
            parent = (Stage) anchorPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(filePath));
            Parent root = loader.load();

            Scene scene = new Scene(root); 
            Stage stage = new Stage();
            stage.setResizable(true);  // Allows resizing the window

            stage.setScene(scene);
            stage.initOwner(anchorPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            parent.hide();
            stage.setOnCloseRequest(event -> {
                if (TIMELINE != null) {
                    TIMELINE.stop();
                    TIMELINE = null;
                }
                parent.show();
            });
            parent.close();
            stage.showAndWait();

        } catch (IOException e) {
            System.out.println("Error on [Class:StartController, Method:" + method + "]=>");
            e.printStackTrace();
        }
    }
}