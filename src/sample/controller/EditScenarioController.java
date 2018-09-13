package sample.controller;

import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphSelectionModel;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.editor.GraphEditor;
import sample.model.Scenario;
import sample.model.State;
import sample.utils.ScenarioGraphConverter;
import sample.utils.ScenariosReaderWriter;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class EditScenarioController extends AbstractController {

    private Scenario currentScenario;
    private String pathToFile;

    @FXML
    private Button returnToMainMenu;

    @FXML
    private Button saveButton;

    @FXML
    private Button addNewVertexButton;

    @FXML
    private Button setDesc;

    @FXML
    private TextField scenarioName;

    @FXML
    private TextArea stateDescriptionEdit;

    @FXML
    private TextArea stateNameEdit;

    @FXML
    private TextArea checkListEdit;

    @FXML
    private SwingNode someSwingNode;

    private mxGraph graph;

    private State currentEditedState;

    public EditScenarioController(Main mainApp) {
        super(mainApp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentScenario = mainApp.getAppState().getScenarioToShow();
        if (currentScenario == null) {
            currentScenario = createEmptyScenario();
        }
        scenarioName.setText(currentScenario.getName());
        pathToFile = currentScenario.getPathToFile();

        returnToMainMenu.setOnAction(event -> {
            try {
                mainApp.initChooseScenarioView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        saveButton.setOnAction(event -> {

            if(pathToFile.equals("")) {
                setErrorDialog("Nie dodano pliku z opisem.");
            }
            else if( currentScenario.getName().equals("Nowy scenariusz")){
                setErrorDialog("Nie dodano nazwy scenariusza.");
            }
            else{
                Scenario scenarioToSave = ScenarioGraphConverter.graphToScenario(graph);
                scenarioToSave.setName(currentScenario.getName());
                File source = new File(pathToFile);
                File dest = new File("OpisyScenariuszy" + File.separator + currentScenario.getName() + ".docx");
                try {
                    copyFileUsingJava7Files(source, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scenarioToSave.setPathToFile(currentScenario.getName() + ".docx");
                ScenariosReaderWriter scenarioReaderWriter = new ScenariosReaderWriter();
                scenarioReaderWriter.serializeScenario(scenarioToSave, Paths.get("Scenariusze", currentScenario.getName() + ".format").toString());
                setInfoDialog("Zapisywanie zakończone.");
            }

        });

        setDesc.setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Dodaj plik z opisem");
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if(selectedFile.getAbsolutePath().endsWith(".docx")){
                pathToFile = selectedFile.getAbsolutePath();
            }
            else{
                setErrorDialog("Plik nie jest plikiem docx.");
            }



        });


        stateNameEdit.textProperty().addListener((observable, oldValue, newValue) -> {

            if(currentEditedState != null){
                currentEditedState.setName(newValue);
                graph.refresh();
            }
        });
        stateDescriptionEdit.textProperty().addListener((observable, oldValue, newValue) -> {

            if(currentEditedState != null){
                currentEditedState.setDescription(newValue);
                graph.refresh();
            }
        });

        scenarioName.textProperty().addListener((observable, oldValue, newValue) -> {
            currentScenario.setName(newValue);
            graph.refresh();
        });


        graph = ScenarioGraphConverter.scenarioToGraph(currentScenario);
        graph.setCellsEditable(false);

        addNewVertexButton.setOnAction(event -> {
            graph.insertVertex(
                    graph.getDefaultParent(),
                    null,
                    //id nieważne bo i tak przy zapisywaniu jest brane z identyfikatorów jgrapha
                    new State(0,
                            "Default name",
                            new LinkedList<>(), null), 100.0, 100.0, 80.0, 30.0);
            ;
        });

        SwingUtilities.invokeLater(() -> {
            graph.setAllowDanglingEdges(false);

            mxGraphComponent graphComponent = new mxGraphComponent(graph);

            GraphEditor graphEditor = initEditor(graphComponent);

            someSwingNode.setContent(graphEditor);
        });

        graph.getSelectionModel().addListener(mxEvent.CHANGE, (sender, evt) -> {
            Object[] cells = ((mxGraphSelectionModel) sender).getCells();

            currentEditedState = null;
            stateNameEdit.textProperty().setValue("");
            stateDescriptionEdit.textProperty().setValue("");
            if (cells.length == 1) {
                mxICell cell = (mxICell) cells[0];
                if (cell.isVertex()) {
                    State state = (State) (cell.getValue());
                    currentEditedState = state;
                    stateNameEdit.textProperty().setValue(state.getName());
                    stateDescriptionEdit.textProperty().setValue(state.getDescription());
                }
            }
        });
    }

    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    private void setErrorDialog( String alertName){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(alertName);
        alert.showAndWait();

    }

    private void setInfoDialog(String name){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(name);

        alert.showAndWait();
    }

    private GraphEditor initEditor(mxGraphComponent graphComponent) {
        graphComponent.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        graphComponent.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        GraphEditor editor = new GraphEditor("something", graphComponent);
        return editor;
    }

    private Scenario createEmptyScenario() {
        Scenario emptyScenario = Scenario.createEmptyScenario();
        return emptyScenario;
    }
}
