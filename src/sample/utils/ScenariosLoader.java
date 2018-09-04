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

public class ScenariosLoader {


    public List<Scenario> loadScenarios(String pathname) throws IOException {
        return Files.walk(Paths.get(pathname))
                //.filter(path -> path.endsWith("txt"))
                .map(Path::toFile)
                .filter(File::isFile)
                .map(this::readScenarioFromFile)
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
            scenario.setChecklist(checklist);
            return scenario;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
