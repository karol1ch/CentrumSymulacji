package sample.controller;

import com.sun.deploy.util.StringUtils;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.SharedPropertyMap;
import sample.Main;
import sample.model.Scenario;
import sample.model.State;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class ScenarioController extends AbstractController {

    private Scenario currentScenario;
    private State currentState;
    private boolean firstLoop = false;
    private int progressChange;
    private int secondsSum;

    private static final Integer STARTTIME = 0;
    private Timeline timeline;
    private Integer timeSeconds = STARTTIME;
    private int minute;


    ObservableList <String> items = FXCollections.observableArrayList();

    List <CheckBox> checkBoxList = new ArrayList<>();

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

    @FXML
    private VBox vBoxOnChecking;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label percentsValue;

    @FXML
    private Label timeLabel;

    @FXML
    private VBox vBoxRight;

    public ScenarioController(Main mainApp) {
        super(mainApp);
    }

    @FXML
    private void handleMainMenuButtonAction( ActionEvent actionEvent) throws IOException{
        mainApp.initChooseScenarioView();
    }

    @FXML
    private void handleStartButtonAction( ActionEvent actionEvent) throws IOException {
        updateScenarioStateView();
        checkList();
        secondsSum = 0;
        minute = 0;
        stopWatch();
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

    private void checkList(){
        for( String string: currentScenario.getCheckListStates()) {
            CheckBox checkBox = new CheckBox(string);
            checkBoxList.add(checkBox);
        }
        vBoxOnChecking.getChildren().addAll(checkBoxList);
    }

    private void progress(){

        progressChange = 0;
        for( int i = 0; i < checkBoxList.size(); i++){
            if(checkBoxList.get(i).isSelected()){
                progressChange++;
            }
        }
        double percents = (double)progressChange / (double)checkBoxList.size();
        progressBar.setProgress(percents);
        percents*=100;
        percentsValue.setText(Integer.toString((int)percents) + " % ");

    }

    private void stopWatch(){
        if (timeline != null) {
            secondsSum += minute * 60 + timeSeconds;
            minute = 0;
            timeline.stop();
        }
        timeSeconds = STARTTIME;
        timeLabel.setText(minute +" min  "+timeSeconds.toString()+" s");
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event1 -> {
                            timeSeconds++;
                            timeLabel.setText(minute + " min  " + timeSeconds.toString() + " s");
                            if(timeSeconds == 59){
                                minute++;
                                timeSeconds = 0;
                            }
                        }));
        timeline.playFromStart();
    }



    private void updateScenarioStateView() {

        if(firstLoop){
            progress();
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setItems(items);  // items -> stateHistoryList
        addToList(currentState.getName());
        listView.scrollTo(items.size());
        List<Integer> children = currentState.getChildren();
        List<Button> nextStepButtons = children.stream().map(currentScenario.getStates()::get).map(s -> {
            Button button = new Button(s.getName());
            button.setOnAction((e)->{
                stopWatch();
                currentState = s;
                updateScenarioStateView();
            });
            return button;
        }).collect(Collectors.toList());
        vBox.getChildren().removeAll(vBox.getChildren());
        vBox.getChildren().addAll(nextStepButtons);
        setWrapping();

        if(nextStepButtons.isEmpty()){
            timeline.stop();
            minute = secondsSum / 60;
            secondsSum = secondsSum % 60;
            timeLabel.setText(minute + " minut  " + secondsSum + " sekund");
            //TODO

        }
        firstLoop = true;
    }

    public void addToList(String string){
        items.add(string);
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        currentScenario = mainApp.getAppState().getScenarioToShow();
        scenarioName.setText(currentScenario.getName());
        currentState = currentScenario.getIniialState();
        scrollPane.setFitToWidth(true);


    }




}
