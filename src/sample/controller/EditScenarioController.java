package sample.controller;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.model.Scenario;
import sample.utils.ScenarioGraphConverter;

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
        SwingNode swingNode = new SwingNode();

        centerPane.getChildren().add(swingNode);
        centerPane.setPrefWidth(1024);
        centerPane.setPrefHeight(768);

        mxGraph graph = ScenarioGraphConverter.scenarioToGraph(scenarioToEdit);
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        swingNode.setContent(graphComponent);

    }

    private Scenario createEmptyScenario() {
        Scenario emptyScenario = Scenario.createEmptyScenario();
        return emptyScenario;
    }
}
