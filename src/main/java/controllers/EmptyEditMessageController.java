package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.control.Label;

/**
 * Created by kamil on 21.10.2017.
 */
public class EmptyEditMessageController {

    private Stage stage;

    @FXML
    private Pane panel;

    @FXML
    private Label edit;

    @FXML
    public void initialize(){
    }

    @FXML
    public void closePopUp(){
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setEditText(String msg){
        edit.setText(msg);
    }
}
