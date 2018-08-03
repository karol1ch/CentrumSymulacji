package sample.model;

import java.util.LinkedList;

public class State {

    private int number;

    private LinkedList<Integer> children;

    private String name;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LinkedList<Integer> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<Integer> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
