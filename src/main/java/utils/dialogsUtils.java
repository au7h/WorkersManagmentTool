package utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;

import java.util.ResourceBundle;

/**
 * Created by kamil on 11.06.2017.
 */
public class dialogsUtils {

    static ResourceBundle bundle = fxmlUtils.getResourceBundle();

    public static void dialogAboutApplication(){
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle(bundle.getString("dialog.about.title"));
        aboutAlert.setHeaderText(bundle.getString("dialog.about.header"));
        aboutAlert.setContentText(bundle.getString("dialog.about.content"));
        aboutAlert.initModality(Modality.APPLICATION_MODAL);
        aboutAlert.showAndWait();
    }

    public static Optional<ButtonType> dialogExitConfirmation(){
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("dialog.exit.title"));
        confirmationAlert.setHeaderText(bundle.getString("dialog.exit.header"));
        confirmationAlert.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

    public static void errorDialog(String errorMsg){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("dialog.error.title"));
        errorAlert.setHeaderText(bundle.getString("dialog.error.header"));
        TextArea errorTxt = new TextArea(errorMsg);
        errorAlert.getDialogPane().setContent(errorTxt);
        errorAlert.initModality(Modality.APPLICATION_MODAL);
        errorAlert.showAndWait();
    }

    public static Alert connectingDb(){
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setTitle("Connecting to DB...");
        errorAlert.setHeaderText("Connecting to DB...");
        errorAlert.initModality(Modality.APPLICATION_MODAL);
        errorAlert.show();
        return errorAlert;
    }

    public static void connectingSuccess(){
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setTitle("Connect to db success");
        errorAlert.setHeaderText("Connect to db success");
        errorAlert.initModality(Modality.APPLICATION_MODAL);
        errorAlert.show();
    }
}
