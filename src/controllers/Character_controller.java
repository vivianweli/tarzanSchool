package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Character_controller {
    @FXML
    private Button confirm;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    void OnConfirm(ActionEvent event) {
    	OpenNewWindow("/fxmls/Start_fxml.fxml", "OnConfirm");
    }
    
    @FXML 
    private Stage parent;
    public void OpenNewWindow(String filePath, String method) {
        try {
        	// 获取当前窗口
            parent = (Stage) anchorPane.getScene().getWindow();
            
            // 加载新窗口的FXML文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(filePath));
            Parent root = loader.load();
            
            // 设置新窗口的场景
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initOwner(anchorPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            
            // 打开新窗口前关闭当前窗口
            parent.close();
            
            // 显示新窗口
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println("Error on [Class:EnterController, Method:" + method + "]=>");
            e.printStackTrace();
        }
    }

}

