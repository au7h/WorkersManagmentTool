package controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import dbUtils.dbConn;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.dialogsUtils;
import utils.fxmlUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;


/**
 * Created by kamil on 10.06.2017.
 */
public class mainBorderController {

    private mainWindowController mwc;

    private static final String BUTTONS_TOP_FXML = "/fxml/buttonsTop.fxml";
    private buttonsTopController btp;

    @FXML
    private AnchorPane anchorBorder;

    @FXML
    private JFXHamburger hamburgerMain;

    @FXML
    private JFXDrawer drawerMain;

    @FXML
    public void initialize(){
        showMainScreen();
    }

    public void showMainScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(BUTTONS_TOP_FXML));
            loader.setResources(fxmlUtils.getResourceBundle());
            Pane pane = loader.load();
            btp = loader.getController();
            btp.setMbc(this);
            drawerMain.setSidePane(pane);

            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburgerMain);
            burgerTask2.setRate(-1);
            burgerTask2.play();
            drawerMain.open();
            hamburgerMain.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                burgerTask2.setRate(burgerTask2.getRate() * -1);
                burgerTask2.play();

                if(drawerMain.isShown())
                    drawerMain.close();
                else
                    drawerMain.open();
            });
        } catch (Exception ex){
            dialogsUtils.errorDialog(ex.getMessage());
        }
    }

    public void setMwc(mainWindowController mwc){
        this.mwc = mwc;
    }

    public mainWindowController getMwc(){
        return this.mwc;
    }

    @FXML
    public void closeApp() {
        if(dialogsUtils.dialogExitConfirmation().get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    public void dbConnTest() {
        dbConn.connectToDatabase();
    }
}
