package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
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

    private Text text;

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

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vBox;

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


    private void setWrapping(){
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> cell = new ListCell<String>() {
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            Text text = new Text(item);
                            text.wrappingWidthProperty().bind(listView.widthProperty().subtract(20));
                            setGraphic(text);
                        }
                    }
                };
                return cell;
            }
        });
    }


    private void updateScenarioStateView() {
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setItems(items);  // items -> stateHistoryList
        addToList(currentState.getName());
        List<Integer> children = currentState.getChildren();
        List<Button> nextStepButtons = children.stream().map(currentScenario.getStates()::get).map(s -> {
            Button button = new Button(s.getName());
            button.setOnAction((e)->{
                currentState = s;
                updateScenarioStateView();
            });
            return button;
        }).collect(Collectors.toList());
       // buttonBar.getButtons().removeAll(buttonBar.getButtons());
        //buttonBar.getButtons().addAll(nextStepButtons);
        vBox.getChildren().removeAll(vBox.getChildren());
        vBox.getChildren().addAll(nextStepButtons);
        setWrapping();


        if(nextStepButtons.isEmpty()){
            addToList("Koniec");
        }
    }

    public void addToList(String string){
        items.add(string);
    }


    @Override
    public void initialize (URL location, ResourceBundle resources) {
        currentScenario = mainApp.getAppState().getScenarioToShow();
        scenarioName.setText(currentScenario.getName());
        currentState = currentScenario.getIniialState();

    }




}
