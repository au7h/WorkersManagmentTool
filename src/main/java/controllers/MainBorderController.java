package controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import dbUtils.DbConn;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.DialogsUtils;
import utils.FxmlUtils;


/**
 * Created by kamil on 10.06.2017.
 */
public class MainBorderController {

    private MainWindowController mwc;

    private static final String BUTTONS_TOP_FXML = "/fxml/buttonsTop.fxml";
    private ButtonsTopController btp;

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
            loader.setResources(FxmlUtils.getResourceBundle());
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
            DialogsUtils.errorDialog(ex.getMessage());
        }
    }

    public void setMwc(MainWindowController mwc){
        this.mwc = mwc;
    }

    public MainWindowController getMwc(){
        return this.mwc;
    }

    @FXML
    public void closeApp() {
        if(DialogsUtils.dialogExitConfirmation().get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    public void dbConnTest() {
        DbConn.connectToDatabase();
    }
}
