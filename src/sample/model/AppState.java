package sample.model;

import java.util.LinkedList;
import java.util.List;

public class AppState {


    private Scenario scenarioToShow;
    private List<Scenario> allScenarios;

    public AppState() {
        allScenarios = new LinkedList<>();
    }

    public Scenario getScenarioToShow() {
        return scenarioToShow;
    }

    public void setScenarioToShow(Scenario scenarioToShow) {
        this.scenarioToShow = scenarioToShow;
    }

    public void setAllScenarios(List<Scenario> allScenarios) {
        this.allScenarios = allScenarios;
    }

    public List<Scenario> getAllScenarios() {
        return allScenarios;
    }

}
