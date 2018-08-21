package sample.model;

import java.util.List;

public class State {

    private int number;

    private String description;

    private List<Integer> children;

    private String name;

    public State (){
        this.description = "";
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Integer> getChildren() {
        return children;
    }

    public void setChildren(List<Integer> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
