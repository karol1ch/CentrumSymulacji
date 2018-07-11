package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AppState {


    private Scenario scenarioToShow;
    private StringProperty userMessage;
    private List<Scenario> allScenarios;

    public AppState() {
        userMessage = new SimpleStringProperty("Nothing to say");
        allScenarios = new LinkedList<>();
    }

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
        this.userMessage.set(userMessage);
    }

    public String getUserMessage() {
        return userMessage.get();
    }


    public void setAllScenarios(List<Scenario> allScenarios) {
        this.allScenarios = allScenarios;
    }

    public List<Scenario> getAllScenarios() {
        return allScenarios;
    }

    public StringProperty userMessageProperty() {
        return userMessage;
    }
}
