package sample;

import sample.model.Scenario;
import sample.model.State;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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
        BufferedReader reader = null;
        Map <Integer,State> states = new HashMap<>();
        try {
            Scenario scenario = new Scenario();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            String description = reader.readLine();
            ArrayList<String> components = new ArrayList<>(Arrays.asList(description.split(";")));
            while(true){
                State state = new State();
                description = reader.readLine();
                String[] stateDescription = description.split(";");
                Integer tempInt = Integer.parseInt(stateDescription[0]);
                state.setNumber(tempInt);
                state.setName(stateDescription[1]);
                if (stateDescription.length == 3){
                    LinkedList<Integer> childrenList = new LinkedList<>();
                    String[] childrenArray = stateDescription[2].split(" ");
                    for( int i = 0; i < childrenArray.length; i++){
                        tempInt = Integer.parseInt(childrenArray[i]);
                        childrenList.add(tempInt);
                    }
                    state.setChildren(childrenList);
                    states.put(state.getNumber(),state);
                }
                else if (stateDescription.length == 2){
                    state.setChildren(Collections.emptyList());
                    states.put(state.getNumber(),state);
                    break;
                }
            }
            reader.close();
            scenario.setStates(states);
            scenario.setName(components.get(0));
            return scenario;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
