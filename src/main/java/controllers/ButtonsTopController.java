package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import utils.DialogsUtils;
import utils.FxmlUtils;


/**
 * Created by kamil on 10.06.2017.
 */
public class ButtonsTopController {

    private MainBorderController mbc;

    private MainWindowController mwc;

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
       Pane pane = FxmlUtils.fxmlLoader(FXML_WORKERS_LIST);
       WorkersListController wlc = FxmlUtils.loaderForController.getController();
       wlc.setMbc(mbc);
       mbc.getMwc().setScreen(pane);
    }

    public void openWorkerSearch() {
        Pane pane = FxmlUtils.fxmlLoader(FXML_WORKER_SEARCH);
        WorkerSearchController wsc = FxmlUtils.loaderForController.getController();
        wsc.setMbc(mbc);
        mbc.getMwc().setScreen(pane);
    }

    public void openWorkerAdd() {
        Pane pane = FxmlUtils.fxmlLoader(FXML_WORKER_ADD);
        WorkerAddController wac = FxmlUtils.loaderForController.getController();
        wac.setMbc(mbc);
        mbc.getMwc().setScreen(pane);
    }

    public void openWorkerEdit() {
        Pane pane = FxmlUtils.fxmlLoader(FXML_WORKER_EDIT);
        WorkerEditController wec = FxmlUtils.loaderForController.getController();
        wec.setMbc(mbc);
        mbc.getMwc().setScreen(pane);
    }

    public void openWorkerRemove() {
        Pane pane = FxmlUtils.fxmlLoader(FXML_WORKER_REMOVE);
        WorkerRemoveController wrc = FxmlUtils.loaderForController.getController();
        wrc.setMbc(mbc);
        mbc.getMwc().setScreen(pane);
    }

    public void openExit() {
        if(DialogsUtils.dialogExitConfirmation().get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    public void setMbc(MainBorderController mbc){
        this.mbc = mbc;
    }
    public void setMwc(MainWindowController mwc){ this.mwc = mwc;}
}
