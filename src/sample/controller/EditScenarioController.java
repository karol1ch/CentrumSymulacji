package sample.controller;

import sample.Main;
import sample.model.Scenario;

import java.net.URL;
import java.util.ResourceBundle;

public class EditScenarioController extends AbstractController {

    private Scenario currentScenario;


    public EditScenarioController(Main mainApp) {
        super(mainApp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentScenario = mainApp.getAppState().getScenarioToShow();

    }
}
