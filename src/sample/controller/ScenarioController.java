package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.model.Scenario;
import sample.model.State;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ScenarioController extends AbstractController {

    private Scenario currentScenario;
    private State currentState;

    private int iterator = 0;

    ObservableList <String> items = FXCollections.observableArrayList();

    @FXML
    private Button returnToMainMenu;

    @FXML
    private  Button startButton;
    @FXML
    private Label scenarioName;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private ListView<String> listView;

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
        updateScenarioStateView();
    }

    private void updateScenarioStateView() {
        iterator++;
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setItems(items);
        addToList(iterator +". " + currentState.getName());
        List<Integer> children = currentState.getChildren();
        List<Button> nextStepButtons = children.stream().map(currentScenario.getStates()::get).map(s -> {
            Button button = new Button(s.getName());
            button.setOnAction((e)->{
                currentState = s;
                updateScenarioStateView();
            });
            return button;
        }).collect(Collectors.toList());
        buttonBar.getButtons().removeAll(buttonBar.getButtons());
        buttonBar.getButtons().addAll(nextStepButtons);
    }

    public void addToList(String string){
        items.add(string);
    }


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        currentScenario = mainApp.getAppState().getScenarioToShow();
        scenarioName.setText(currentScenario.getName());
        currentState = currentScenario.getInitialState();
    }
}
