package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.Main;


import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends AbstractController {


    @FXML
    private Button chooseScenario;
    @FXML
    private Button editMainMenu;
    @FXML
    private Button addMainMenu;

    public MainMenuController(Main mainApp) {
        super(mainApp);
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource() == chooseScenario){
           mainApp.initChooseScenarioView();
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
