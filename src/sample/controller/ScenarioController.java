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
    private Label scenarioName;

    public ScenarioController(Main mainApp) {
        super(mainApp);
    }


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
        Scenario scenario = mainApp.getAppState().getScenarioToShow();
        scenarioName.setText(scenario.getName());
    }
}
