package controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by kamil on 15.07.2017.
 */
public class connectingController {
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button buttonOK;
    @FXML
    public Label labelConnected;
    @FXML
    public Label labelConnecting;

    private Stage stage;

    @FXML
    public void exitWindow(){
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
