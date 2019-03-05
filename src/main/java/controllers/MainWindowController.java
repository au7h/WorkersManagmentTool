package controllers;

import dbUtils.DbConn;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utils.DialogsUtils;
import utils.FxmlUtils;

/**
 * Created by kamil on 15.06.2017.
 */
public class MainWindowController {

    private static final String FXML_MAIN_BORDER = "/fxml/mainBorder.fxml";

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    public void initialize(){
        loadMenuScreen();
    }

    public void loadMenuScreen() {
       Pane pane = FxmlUtils.fxmlLoader(FXML_MAIN_BORDER);
        MainBorderController mbc = FxmlUtils.loaderForController.getController();
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
        if(DialogsUtils.dialogExitConfirmation().get() == ButtonType.OK){
            if(DbConn.isConnected) DbConn.disconnectDatabase();
            Platform.exit();
            System.exit(0);
        }
    }
}
