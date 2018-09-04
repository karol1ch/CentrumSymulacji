package sample.model;

import java.util.*;

public class Scenario{
    public static final String FIRST_STATE_NAME = "START";
    public static final String LAST_STATE_NAME = "KONIEC";


    //TODO konstruktor z prawdziwego zdarzenia (zawierający wymagane pola)
    private String name;

    private Map<Integer,State> states;

    public Map<Integer,State> getStates() {
        return states;
    }

    private List<String> checkListStates;

    public List<String> getCheckListStates() {
        return checkListStates;
    }

    public void setChecklist(List<String> checkListStates) {
        this.checkListStates = checkListStates;
    }

    public void setStates(Map<Integer, State> stateArrayList) {
        this.states = stateArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getInitialState() throws Exception {
        //TODO sprawdzenie, że powinien być dokładnie jeden stan START (nie więcej)
        return states.values().stream()
                .filter(s -> FIRST_STATE_NAME.equals(s.getName()))
                .findAny()
                .orElseThrow(() -> new Exception("Brak stanu o nazwie 'START'"));
    }

    public static Scenario createEmptyScenario(){
        Scenario scenario = new Scenario();
        Map<Integer,State> states = new HashMap<>();
        State endState = new State(999,LAST_STATE_NAME,Collections.emptyList(),"");
        State startState = new State(0,FIRST_STATE_NAME, Arrays.asList(endState.getNumber()),"");
        states.put(startState.getNumber(),startState);
        states.put(endState.getNumber(),endState);
        scenario.setStates(states);
        scenario.setName("Nowy scenariusz");
        scenario.setChecklist(new LinkedList<>());
        return scenario;
    }

}
