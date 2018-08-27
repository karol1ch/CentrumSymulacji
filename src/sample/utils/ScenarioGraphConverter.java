package sample.utils;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import sample.model.Scenario;

import java.util.Map;
import java.util.stream.Collectors;

public class ScenarioGraphConverter {
    public static mxGraph scenarioToGraph(Scenario scenario) {

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {
            Map<Integer, Object> vertices = scenario.getStates().values().stream().collect(Collectors.toMap(s -> s.getNumber(), s -> graph.insertVertex(parent, s.getNumber().toString(), s.getName(), 280, 320, 80,
                    30)));
            scenario.getStates().values().stream().forEach(s -> {
                s.getChildren()
                        .forEach(childNumber -> graph.insertEdge(parent, null, null, vertices.get(s.getNumber()), vertices.get(childNumber)));
            });
            graph.setAutoSizeCells(true);
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
}
