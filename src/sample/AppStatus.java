package sample;

import sample.model.Scenario;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AppStatus {


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
}
