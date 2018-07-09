package sample.model;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppState {


    private Scenario scenarioToShow;
    private String userMessage;
    private List<Scenario> allScenarios;

    public Scenario loadScenario() throws IOException {
        File file = new File("Scenariusze" + File.separator + "ScenariuszPierwszy.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        String description = reader.readLine();
        ArrayList<String> components = new ArrayList<>(Arrays.asList(description.split(";")));
        reader.close();
        Scenario scenario = new Scenario();
        scenario.setName(components.get(0));
        return scenario;
    }

    public Scenario getScenarioToShow() {
        return scenarioToShow;
    }

    public void setScenarioToShow(Scenario scenarioToShow) {
        this.scenarioToShow = scenarioToShow;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setAllScenarios(List<Scenario> allScenarios) {
        this.allScenarios = allScenarios;
    }

    public List<Scenario> getAllScenarios() {
        return allScenarios;
    }
}
