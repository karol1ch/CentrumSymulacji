package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.model.Scenario;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import static sample.controller.ScenarioController.*;

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
            root = FXMLLoader.load(getClass().getResource("/sample"+ File.separator + "view"+ File.separator +"scenario.fxml"));
            Scene scene = new Scene(root);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            ScenarioController.setText();

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
