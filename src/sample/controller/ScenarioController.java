package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.AppStatus;
import sample.Main;
import sample.model.Scenario;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ScenarioController implements Initializable {

    private AppStatus appStatus;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private Label scenarioName;


    @FXML
    private void handleButtonAction (ActionEvent actionEvent) throws IOException{
        Stage stage;
        Parent root;

        if (actionEvent.getSource() == returnToMainMenu){
            stage = (Stage) returnToMainMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/sample"+ File.separator + "view" + File.separator +"mainMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        try {
            appStatus = new AppStatus();
            Scenario scenario = appStatus.loadScenario();
            scenarioName.setText(scenario.getName());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
