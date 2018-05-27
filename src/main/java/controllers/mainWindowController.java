package controllers;

import dbUtils.dbConn;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utils.dialogsUtils;
import utils.fxmlUtils;

/**
 * Created by kamil on 15.06.2017.
 */
public class mainWindowController {

    private static final String FXML_MAIN_BORDER = "/fxml/mainBorder.fxml";

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    public void initialize(){
        loadMenuScreen();
    }

    public void loadMenuScreen() {
       Pane pane = fxmlUtils.fxmlLoader(FXML_MAIN_BORDER);
        mainBorderController mbc = fxmlUtils.loaderForController.getController();
        mbc.setMwc(this);
        setScreen(pane);
    }
    public void setScreen(Pane pane){
        mainAnchor.getChildren().clear();
        mainAnchor.setTopAnchor(pane, 0.0);
        mainAnchor.setRightAnchor(pane, 0.0);
        mainAnchor.setLeftAnchor(pane, 0.0);
        mainAnchor.setBottomAnchor(pane, 0.0);
        mainAnchor.getChildren().add(pane);
    }

    public void closeApp() {
        if(dialogsUtils.dialogExitConfirmation().get() == ButtonType.OK){
            if(dbConn.isConnected) dbConn.disconnectDatabase();
            Platform.exit();
            System.exit(0);
        }
    }
}
