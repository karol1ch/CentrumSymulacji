package sample;

import sample.model.Scenario;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));

            String description = reader.readLine();
            ArrayList<String> components = new ArrayList<>(Arrays.asList(description.split(";")));
            reader.close();
            Scenario scenario = new Scenario();
            scenario.setName(components.get(0));
            return scenario;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
