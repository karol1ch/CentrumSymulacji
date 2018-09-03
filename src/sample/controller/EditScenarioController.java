package sample.controller;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.*;
import com.mxgraph.view.mxGraph;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.editor.GraphEditor;
import sample.model.Scenario;
import sample.utils.ScenarioGraphConverter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditScenarioController extends AbstractController {

    private Scenario currentScenario;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private Label scenarioName;

    @FXML
    private AnchorPane centerPane;


    public EditScenarioController(Main mainApp) {
        super(mainApp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentScenario = mainApp.getAppState().getScenarioToShow();
        Scenario scenarioToEdit = currentScenario;
        if (scenarioToEdit== null){
            scenarioToEdit = createEmptyScenario();
        }
        scenarioName.setText(scenarioToEdit.getName());

        returnToMainMenu.setOnAction(event -> {
            try {
                mainApp.initChooseScenarioView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );
        SwingNode graphSwingNode = new SwingNode();
        centerPane.getChildren().add(graphSwingNode);
        centerPane.setPrefWidth(1024);
        centerPane.setPrefHeight(768);

        mxGraph graph = ScenarioGraphConverter.scenarioToGraph(scenarioToEdit);
        graph.setAllowDanglingEdges(false);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);

        GraphEditor editorFrame = initEditor(graphComponent);

        //graphSwingNode.setContent(graphComponent);
        graphSwingNode.setContent(editorFrame);
    }

    private GraphEditor initEditor(mxGraphComponent graphComponent) {

        mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
        mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";

        GraphEditor editor = new GraphEditor("something",graphComponent);
        return editor;
    }

    private Scenario createEmptyScenario() {
        Scenario emptyScenario = Scenario.createEmptyScenario();
        return emptyScenario;
    }
}
