package sample.utils;

import sample.model.Scenario;
import sample.model.State;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sample.model.Scenario.LAST_STATE_NAME;

public class ScenariosReaderWriter {


    public List<Scenario> loadScenarios(String pathname) throws IOException {
        return Files.walk(Paths.get(pathname))
                //.filter(path -> path.endsWith("txt"))
                .map(Path::toFile)
                .filter(File::isFile)
                .map(file -> file.getPath().endsWith("format")?deserializeScenario(file):readScenarioFromFile(file))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Scenario readScenarioFromFile(File file) {
        BufferedReader reader;
        Map <Integer,State> states = new HashMap<>();
        try {
            Scenario scenario = new Scenario();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            String scenarioName = reader.readLine();
            String pathToFile = reader.readLine();
            String scenarioChecklistEncoded = reader.readLine();
            List<String> checklist = Arrays.stream(scenarioChecklistEncoded.split(";")).collect(Collectors.toList());

            String stateEncoded;
            while((stateEncoded = reader.readLine())!=null){
                String[] stateParams = stateEncoded.split(";");
                Integer stateNumber = Integer.parseInt(stateParams[0]);
                String stateName =   stateParams[1];
                List<Integer> children = new LinkedList<>();
                if(!stateName.equals(LAST_STATE_NAME)) {
                    children=Stream.of(stateParams[2].split(" ")).map(Integer::valueOf).collect(Collectors.toList());

                }
                String description = "";
                if(stateParams.length == 4) {
                    description = stateParams[3];
                }
                State state = new State(stateNumber, stateName, children, description);

                states.put(state.getNumber(),state);
            }
            reader.close();
            scenario.setStates(states);
            scenario.setName(scenarioName);
            scenario.setPathToFile(pathToFile);
            scenario.setChecklist(checklist);
            return scenario;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Scenario deserializeScenario(File file){
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {

            Scenario scenario = (Scenario) ois.readObject();
            System.out.println("Done");
            return scenario;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void serializeScenario(Scenario scenario, String filePath) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {

            oos.writeObject(scenario);
            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
