package sample.controller;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.*;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphSelectionModel;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.editor.GraphEditor;
import sample.model.Scenario;
import sample.model.State;
import sample.utils.ScenarioGraphConverter;
import sample.utils.ScenariosReaderWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EditScenarioController extends AbstractController {

    private Scenario currentScenario;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private Button saveButton;

    @FXML
    private Button addNewVertexButton;

    @FXML
    private Label scenarioName;

    @FXML
    private TextField stateDescriptionEdit;

    @FXML
    private TextField stateNameEdit;

    @FXML
    private AnchorPane centerPane;

    private mxGraph graph;

    private State currentEditedState;

    public EditScenarioController(Main mainApp) {
        super(mainApp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentScenario = mainApp.getAppState().getScenarioToShow();
        Scenario scenarioToEdit = currentScenario;
        if (scenarioToEdit == null) {
            scenarioToEdit = createEmptyScenario();
        }
        scenarioName.setText(scenarioToEdit.getName());

        returnToMainMenu.setOnAction(event -> {
            try {
                mainApp.initChooseScenarioView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        saveButton.setOnAction(event -> {

            Scenario scenarioToSave = ScenarioGraphConverter.graphToScenario(graph);
            scenarioToSave.setName("New scenario");
            ScenariosReaderWriter scenarioReaderWriter = new ScenariosReaderWriter();
            scenarioReaderWriter.serializeScenario(scenarioToSave, Paths.get("Scenariusze", "default.format").toString());

        });


        SwingNode graphSwingNode = new SwingNode();
        centerPane.getChildren().add(graphSwingNode);
        centerPane.setPrefWidth(1024);
        centerPane.setPrefHeight(768);

        graph = ScenarioGraphConverter.scenarioToGraph(scenarioToEdit);


        addNewVertexButton.setOnAction(event -> {
            graph.insertVertex(
                    graph.getDefaultParent(),
                    null,
                    new State(
                            currentScenario.getNextId(),
                            "Default name",
                            new LinkedList<>(), null), 100.0, 100.0, 100.0, 100.0);
            ;
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                graph.setAllowDanglingEdges(false);

                mxGraphComponent graphComponent = new mxGraphComponent(graph);
                graphSwingNode.setContent(initEditor(graphComponent));
            }
        });
        /*graph.getSelectionModel().addListener(mxEvent.CHANGE, new mxEventSource.mxIEventListener() {
            @Override
            public void invoke(Object sender, mxEventObject evt) {
                Object[] cells = ((mxGraphSelectionModel) sender).getCells();
                if (currentEditedState != null && stateNameEdit.textProperty().isBound()) {
                    String name = stateNameEdit.textProperty().getValue();
                    stateNameEdit.textProperty().unbindBidirectional(currentEditedState.getNameProperty());
                    currentEditedState.getNameProperty().setValue(name);
                }
                if (currentEditedState != null && stateDescriptionEdit.textProperty().isBound()) {
                    String description = stateDescriptionEdit.textProperty().getValue();
                    stateDescriptionEdit.textProperty().unbindBidirectional(currentEditedState.getDescriptionProperty());
                    currentEditedState.getDescriptionProperty().setValue(description);
                }
                stateNameEdit.textProperty().setValue("");
                stateDescriptionEdit.textProperty().setValue("");
                currentEditedState = null;
                if (cells.length == 1) {
                    mxICell cell = (mxICell) cells[0];
                    if (cell.isVertex()) {
                        State state = (State) (cell.getValue());
                        currentEditedState = state;
                        stateNameEdit.textProperty().setValue(state.getName());
                        stateDescriptionEdit.textProperty().setValue(state.getDescription());
                        stateNameEdit.textProperty().bindBidirectional(state.getNameProperty());
                        stateDescriptionEdit.textProperty().bindBidirectional(state.getDescriptionProperty());
                    }
                }
                if (sender instanceof mxGraphSelectionModel) {
                    for (Object cell : ((mxGraphSelectionModel) sender).getCells()) {
                        System.out.println("cell=" + graph.getLabel(cell));
                    }
                }
            }
        });*/

        //graphSwingNode.setContent(graphComponent);
        //graphSwingNode.setContent(editorFrame);
    }

    private GraphEditor initEditor(mxGraphComponent graphComponent) {

        mxSwingConstants.SHADOW_COLOR = Color.LIGHT_GRAY;
        mxConstants.W3C_SHADOWCOLOR = "#D3D3D3";

        GraphEditor editor = new GraphEditor("something", graphComponent);
        return editor;
    }

    private Scenario createEmptyScenario() {
        Scenario emptyScenario = Scenario.createEmptyScenario();
        return emptyScenario;
    }
}
