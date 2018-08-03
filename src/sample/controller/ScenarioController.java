package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.model.Scenario;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ScenarioController extends AbstractController {

    @FXML
    private Button returnToMainMenu;

    @FXML
    private  Button startButton;

    @FXML
    private Label scenarioName;

    public ScenarioController(Main mainApp) {
        super(mainApp);
    }

    @FXML
    private void handleMainMenuButtonAction( ActionEvent actionEvent) throws IOException{
        mainApp.initChooseScenarioView();
    }

    @FXML
    private void handleStartButtonAction( ActionEvent actionEvent) throws IOException {
        //Start apki
        System.out.println("dziala");
    }


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        Scenario scenario = mainApp.getAppState().getScenarioToShow();
        scenarioName.setText(scenario.getName());
    }
}
