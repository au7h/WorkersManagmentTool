package utils;

import controllers.buttonsTopController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

/**
 * Created by kamil on 10.06.2017.
 */
public class fxmlUtils {

    public static FXMLLoader loaderForController;

    public static Pane fxmlLoader(String fxmlPath){
        FXMLLoader mainLoader = new FXMLLoader(fxmlUtils.class.getResource(fxmlPath));
        mainLoader.setResources(getResourceBundle());
        loaderForController = mainLoader;
        try {
            return mainLoader.load();
        } catch (Exception e){
            dialogsUtils.errorDialog(e.getMessage());
        }
        return null;
    }

    public static ResourceBundle getResourceBundle(){
        return ResourceBundle.getBundle("bundles.messages");
    }
}
