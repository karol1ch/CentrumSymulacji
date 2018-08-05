package sample.model;

import java.util.Map;

public class Scenario{

    private String name;

    private Map<Integer,State> states;

    public Map<Integer,State> getStates() {
        return states;
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

    public State getInitialState() {
        return states.get(1);
    }
}
