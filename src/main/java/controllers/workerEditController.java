package controllers;


import javafx.fxml.FXML;

/**
 * Created by kamil on 15.06.2017.
 */
public class WorkerEditController {

    private MainBorderController mbc;

    @FXML
    public void backToMenu() {
        MainWindowController mwc = mbc.getMwc();
        mwc.loadMenuScreen();
    }

    public void setMbc(MainBorderController mbc){
        this.mbc = mbc;
    }
}
