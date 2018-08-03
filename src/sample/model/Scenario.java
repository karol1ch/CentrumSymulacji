package sample.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Scenario{

    private String name;

    private LinkedList<State> stateArrayList;

    public LinkedList<State> getStateArrayList() {
        return stateArrayList;
    }

    public void setStateArrayList(LinkedList<State> stateArrayList) {
        this.stateArrayList = stateArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
