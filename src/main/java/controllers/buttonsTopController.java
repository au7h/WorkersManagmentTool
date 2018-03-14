package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import utils.dialogsUtils;
import utils.fxmlUtils;


/**
 * Created by kamil on 10.06.2017.
 */
public class buttonsTopController {

    private mainBorderController mbc;

    private mainWindowController mwc;

    private static final String FXML_WORKERS_LIST = "/fxml/workersList.fxml";
    private static final String FXML_WORKER_SEARCH = "/fxml/workerSearch.fxml";
    private static final String FXML_WORKER_ADD = "/fxml/workerAdd.fxml";
    private static final String FXML_WORKER_EDIT = "/fxml/workerEdit.fxml";
    private static final String FXML_WORKER_REMOVE = "/fxml/workerRemove.fxml";

    @FXML
    Button workersListButton;

    @FXML
    Button workerSearchButton;

    @FXML
    Button workerAddButton;

    @FXML
    Button workerEditButton;

    @FXML
    Button workerRemoveButton;

    @FXML
    Button exitButton;

    @FXML
    public void initialize(){
    }

    public void openWorkersList() {
       Pane pane = fxmlUtils.fxmlLoader(FXML_WORKERS_LIST);
       workersListController wlc = fxmlUtils.loaderForController.getController();
       wlc.setMbc(mbc);
       mbc.getMwc().setScreen(pane);
    }

    public void openWorkerSearch() {
        Pane pane = fxmlUtils.fxmlLoader(FXML_WORKER_SEARCH);
        workerSearchController wsc = fxmlUtils.loaderForController.getController();
        wsc.setMbc(mbc);
        mbc.getMwc().setScreen(pane);
    }

    public void openWorkerAdd() {
        Pane pane = fxmlUtils.fxmlLoader(FXML_WORKER_ADD);
        workerAddController wac = fxmlUtils.loaderForController.getController();
        wac.setMbc(mbc);
        mbc.getMwc().setScreen(pane);
    }

    public void openWorkerEdit() {
        Pane pane = fxmlUtils.fxmlLoader(FXML_WORKER_EDIT);
        workerEditController wec = fxmlUtils.loaderForController.getController();
        wec.setMbc(mbc);
        mbc.getMwc().setScreen(pane);
    }

    public void openWorkerRemove() {
        Pane pane = fxmlUtils.fxmlLoader(FXML_WORKER_REMOVE);
        workerRemoveController wrc = fxmlUtils.loaderForController.getController();
        wrc.setMbc(mbc);
        mbc.getMwc().setScreen(pane);
    }

    public void openExit() {
        if(dialogsUtils.dialogExitConfirmation().get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    public void setMbc(mainBorderController mbc){
        this.mbc = mbc;
    }
    public void setMwc(mainWindowController mwc){ this.mwc = mwc;}
}
