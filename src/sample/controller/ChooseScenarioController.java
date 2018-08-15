package sample.controller;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import sample.Main;
import sample.model.Scenario;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseScenarioController extends AbstractController{

    @FXML
    private ListView<Scenario> listView;

    @FXML
    private Button runScenarioButton;

    @FXML
    private Button editScenarioButton;

    @FXML
    private Button newScenarioButton;

    @FXML
    private GridPane gridPane;

    public ChooseScenarioController(Main mainApp) {
        super(mainApp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainApp.loadAllScenarios();
        List<Scenario> allScenarios = mainApp.getAppState().getAllScenarios();
        if(allScenarios != null){
            ObservableList listViewData = FXCollections.observableArrayList(allScenarios);
            listView.setItems(listViewData);
            listView.setCellFactory(new Callback<ListView<Scenario>, ListCell<Scenario>>() {
                @Override
                public ListCell<Scenario> call(ListView<Scenario> param) {
                    return new ListCell<Scenario>(){
                        @Override
                        protected void updateItem(Scenario scenario, boolean empty) {
                            super.updateItem(scenario, empty);

                            if(scenario==null || empty){

                            }else {
                                this.setText(scenario.getName());

                            }
                        }
                    };
                }
            });
        }


        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Scenario>() {
            @Override
            public void changed(ObservableValue<? extends Scenario> observable, Scenario oldValue, Scenario newValue) {
                if(newValue != null){
                    runScenarioButton.setDisable(false);
                    editScenarioButton.setDisable(false);
                    newScenarioButton.setDisable(false);

                }else{
                    runScenarioButton.setDisable(true);
                    editScenarioButton.setDisable(true);
                    newScenarioButton.setDisable(true);
                }
            }
        });

        runScenarioButton.setOnAction(event -> {
            mainApp.getAppState().setScenarioToShow(listView.getSelectionModel().getSelectedItem());
            try {
                mainApp.initScenarioView();
            } catch (IOException e) {
                //TODO obłsuga tego błędu
                e.printStackTrace();
            }
        });

        editScenarioButton.setOnAction(event -> {
            mainApp.getAppState().setScenarioToShow(listView.getSelectionModel().getSelectedItem());
            try {
                mainApp.initChangeScenarioView();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("edytuje");
        });

        newScenarioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("dodaje");  //TODO

            }
        });


    }

}
