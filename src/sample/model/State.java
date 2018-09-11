package sample.model;

import java.io.Serializable;
import java.util.List;

public class State implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer number;

    private String description;

    private List<Integer> children;

    private String name;

    public State (){
        this.description = "";
    }

    public State(Integer number, String name, List<Integer> children, String description) {
        this.number = number;
        this.name = name;
        this.children = children;
        this.description = description;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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


    @Override
    public String toString() {
        return name;
    }
}
