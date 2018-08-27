package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.*;
import sample.model.AppState;
import sample.model.Scenario;
import sample.utils.ScenariosLoader;


import java.io.*;
import java.net.URL;
import java.util.List;


public class Main extends Application {
    public static final String CHOOSE_SCENARIO_VIEW = "view" + File.separator + "chooseScenario.fxml";
    public static final String SCENARIO_VIEW = "view" + File.separator + "scenario.fxml";
    public static final String CHANGE_SCENARIO_VIEW = "view" + File.separator + "editScenario.fxml";

    private AppState appState = new AppState();
    private BorderPane mainRoot;
    @Override
    public void start(Stage primaryStage) throws Exception{
        URL resource = getClass().getResource("view" + File.separator + "main.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setController(new MainController(this));
        mainRoot = loader.load();
        primaryStage.setTitle("Centrum Symulacji");
        Scene scene = new Scene(mainRoot);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

      initChooseScenarioView();
    }

    public void initScenarioView() throws IOException {
        initView(SCENARIO_VIEW, new ScenarioController(this));
    }

    public void initChooseScenarioView() throws IOException {
        initView(CHOOSE_SCENARIO_VIEW, new ChooseScenarioController(this));
    }

    public void initChangeScenarioView() throws IOException {
        initView(CHANGE_SCENARIO_VIEW, new EditScenarioController(this));
    }

    private void initView( String path, AbstractController controller) throws IOException {
        URL resource = getClass().getResource(path);
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setController(controller);
        Parent view = loader.load();
        mainRoot.setCenter(view);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public AppState getAppState() {
        return appState;
    }

    public void loadAllScenarios() {
        ScenariosLoader scenariosLoader= new ScenariosLoader();
        try {
            List<Scenario> scenarios = scenariosLoader.loadScenarios("Scenariusze");
            if(scenarios.size() > 0) {
                appState.setAllScenarios(scenarios);
            }else{
                //TODO zrobienie z userMessage property zbindowanego do widoku main w kontrolerze MainController

            }
        } catch (IOException e) {
        }
    }
}
