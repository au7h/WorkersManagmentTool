package controllers;


import javafx.fxml.FXML;

/**
 * Created by kamil on 15.06.2017.
 */
public class workerRemoveController {

    private mainBorderController mbc;

    @FXML
    public void backToMenu() {
        mainWindowController mwc = mbc.getMwc();
        mwc.loadMenuScreen();
    }

    public void setMbc(mainBorderController mbc){
        this.mbc = mbc;
    }
}
