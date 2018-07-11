package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends AbstractController {
    @FXML
    private Label userMessagesLabel;

    public MainController(Main mainApp) {
        super(mainApp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userMessagesLabel.textProperty().bindBidirectional(mainApp.getAppState().userMessageProperty());

    }


}
