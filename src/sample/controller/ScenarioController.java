package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScenarioController implements Initializable {

    @FXML
    private Button returnToMainMenu;

    @FXML
    private void handleButtonAction (ActionEvent actionEvent) throws IOException{
        Stage stage;
        Parent root;

        if (actionEvent.getSource() == returnToMainMenu){
            stage = (Stage) returnToMainMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("view" + File.separator +"mainMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }





    @Override
    public void initialize (URL location, ResourceBundle resources) {

    }
}
