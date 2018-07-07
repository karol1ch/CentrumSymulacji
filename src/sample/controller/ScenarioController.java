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
import sample.model.Scenario;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ScenarioController implements Initializable {

    @FXML
    private Button returnToMainMenu;

    @FXML
    private static Label scenarioName;


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

    public static void setText() throws IOException {
        File file = new File("Scenariusze" + File.separator + "ScenariuszPierwszy.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        String description = reader.readLine();
        ArrayList<String> components = new ArrayList<>(Arrays.asList(description.split(";")));
        reader.close();
        Scenario scenario = new Scenario();
        scenario.setName(components.get(0));
        scenarioName.setText(scenario.getName());
    }




    @Override
    public void initialize (URL location, ResourceBundle resources) {

    }
}
