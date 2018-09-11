package sample.utils;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import sample.model.Scenario;
import sample.model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScenarioGraphConverter {
    public static mxGraph scenarioToGraph(Scenario scenario) {

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {
            Map<Integer, Object> vertices = scenario.getStates().values().stream().collect(Collectors.toMap(s -> s.getNumber(), s -> graph.insertVertex(parent, null, s, 280, 320, 80,
                    30)));
            scenario.getStates().values().stream().forEach(s -> {
                s.getChildren()
                        .forEach(childNumber -> graph.insertEdge(parent, null, null, vertices.get(s.getNumber()), vertices.get(childNumber)));
            });
           // graph.setAutoSizeCells(true);
            graph.setMinimumGraphSize(new mxRectangle(0,0,1200,1000));
            new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
            graph.refresh();

        }
        finally
        {
            graph.getModel().endUpdate();
        }
        return graph;
    }

    public static Scenario graphToScenario(mxGraph graph){
        Scenario newScenario = new Scenario();
        Object[] childCells = graph.getChildVertices(graph.getDefaultParent());
        for (Object childCell : childCells) {
            mxCell cell = (mxCell)childCell;
            State state = (State)cell.getValue();
            int edgeCount = cell.getEdgeCount();
            List<Integer> children = new ArrayList<>();
            for(int i = 0;i< edgeCount;i++){
                mxCell edge = (mxCell)cell.getEdgeAt(i);
                if(edge.getSource() == cell){
                    children.add(Integer.valueOf(edge.getTarget().getId()));
                }
            }
            Integer id = Integer.valueOf(cell.getId());
            newScenario.getStates().put(id,new State(id,state.getName(),children,state.getDescription()));

        }
        return newScenario;

    }
}
