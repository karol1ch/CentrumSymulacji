package sample.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Pair;
import sample.Main;
import sample.model.Scenario;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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






    private boolean checkAccess(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Zaloguj się aby uzyskać dostęp");
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        PasswordField password = new PasswordField();

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> username.requestFocus());

        dialog.showAndWait();

        if(String.valueOf(username.getText()).equals("admin") && String.valueOf(password.getText()).equals("admin")){
            return true;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Błędny login lub hasło.");
            ButtonType button = new ButtonType("Ponów próbę");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(button, new ButtonType("Zamknij", ButtonBar.ButtonData.CANCEL_CLOSE));
            Optional<ButtonType> option = alert.showAndWait();
            if(option.get() == button){
                if(checkAccess()){
                    return true;
                }
                else{
                    return false;
                }
            }
            else {
                return false;
            }
        }
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
                   ListCell<Scenario> cell = new ListCell<Scenario>(){
                        @Override
                        protected void updateItem(Scenario scenario, boolean empty) {
                            super.updateItem(scenario, empty);

                            if (!empty) {
                                Text text = new Text(scenario.getName());
                                text.wrappingWidthProperty().bind(listView.widthProperty().subtract(10));
                                setGraphic(text);
                            }

                            if(scenario==null || empty){

                            }else {
                                this.setText(scenario.getName());

                            }
                        }
                    };
                    return cell;
                }
            });
        }


        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Scenario>() {
            @Override
            public void changed(ObservableValue<? extends Scenario> observable, Scenario oldValue, Scenario newValue) {
                if(newValue != null){
                    runScenarioButton.setDisable(false);
                    editScenarioButton.setDisable(false);

                }else{
                    runScenarioButton.setDisable(true);
                    editScenarioButton.setDisable(true);
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
            //TODO dlaczego scenarioToShow... Nie używajmy jednej zmiennej w wielu kontekstach.
            mainApp.getAppState().setScenarioToShow(listView.getSelectionModel().getSelectedItem());
            try {
                if(checkAccess()) {
                    mainApp.initChangeScenarioView();
                }
                else{
                    System.out.println("nie dziala");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        newScenarioButton.setOnAction(event -> {
            //TODO dlaczego scenarioToShow... Nie używajmy jednej zmiennej w wielu kontekstach.
            //TODO2 edit i new to to samo...
            mainApp.getAppState().setScenarioToShow(null);
            try {
                if(checkAccess()) {
                    mainApp.initChangeScenarioView();
                }
                else{
                    System.out.println("nie dziala");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }

}
