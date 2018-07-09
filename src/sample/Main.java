package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.AbstractController;
import sample.controller.ChooseScenarioController;
import sample.controller.MainMenuController;
import sample.controller.ScenarioController;
import sample.model.AppState;
import sample.model.Scenario;


import java.io.*;
import java.net.URL;
import java.util.List;


public class Main extends Application {
    public static final String CHOOSE_SCENARIO_VIEW = "view" + File.separator + "chooseScenario.fxml";
    public static final String SCENARIO_VIEW = "view" + File.separator + "scenario.fxml";
    public static final String MAIN_MENU_VIEW = "view" + File.separator + "mainMenu.fxml";

    private AppState appState = new AppState();
    private BorderPane mainRoot;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        mainRoot = loader.load(getClass().getResource("view" + File.separator +"main.fxml"));
        primaryStage.setTitle("Centrum Symulacji");
        primaryStage.setScene(new Scene(mainRoot));
        primaryStage.show();

       initMainMenuView();
    }

    public void initMainMenuView() throws IOException {
        initView(MAIN_MENU_VIEW, new MainMenuController(this));
    }

    public void initScenarioView() throws IOException {
        initView(SCENARIO_VIEW, new ScenarioController(this));
    }

    public void initChooseScenarioView() throws IOException {
        initView(CHOOSE_SCENARIO_VIEW, new ChooseScenarioController(this));
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
                appState.setUserMessage("Nie wczytałsię żaden scenariusz. Sprawdź scieżkę");

            }
        } catch (IOException e) {
            appState.setUserMessage("Nie wczytało się");
        }
    }
}
