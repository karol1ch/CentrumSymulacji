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

    private List<String> checkListStates;

    public List<String> getCheckListStates() {
        return checkListStates;
    }

    public void setCheckListStates(List<String> checkListStates) {
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

    public State getIniialState() {
        State state = new State();
        state.setNumber(-1);
        state.setName("");
        state.setChildren(Collections.singletonList(1));
        return state;
    }

}
