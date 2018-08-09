package sample.model;

import java.util.Collections;
import java.util.List;
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
        State state = new State();
        state.setNumber(-1);
        state.setName("fake state");
        state.setChildren(Collections.singletonList(1));
        return state;
    }

}
