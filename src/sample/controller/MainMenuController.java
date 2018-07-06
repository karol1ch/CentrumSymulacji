package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Button startMainMenu;
    @FXML
    private Button editMainMenu;
    @FXML
    private Button addMainMenu;

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        if(event.getSource() == startMainMenu){
            stage = (Stage) startMainMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("view" + File.separator + "scenario.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(event.getSource() == editMainMenu){
            System.out.println("2");
        }
        else if(event.getSource() == addMainMenu){
            System.out.println("3");
        }

    }

    public void initialize(URL location, ResourceBundle resources) {

    }
}
