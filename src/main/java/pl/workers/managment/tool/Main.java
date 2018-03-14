package pl.workers.managment.tool;

import dbUtils.dbConn;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.fxmlUtils;

/**
 * Created by kamil on 08.06.2017.
 */
public class Main extends Application {
    private Stage windowMain;
    private static final String MAIN_WINDOW_FXML = "/fxml/mainWindow.fxml";

    public static void main(String[] args){ launch(args);}

    public void start(Stage primaryStage) throws Exception{
        windowMain = primaryStage;
        Pane pane = fxmlUtils.fxmlLoader(MAIN_WINDOW_FXML);
        pane.setStyle("-fx-background-color:  #242F41");
        /*
        //image in background sample

        String image = Main.class.getResource("/icons/backgroundImage.jpg").toExternalForm();
        pane.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
                */
        Scene sceneMain = new Scene(pane);
        windowMain.setScene(sceneMain);
        windowMain.setTitle(fxmlUtils.getResourceBundle().getString("title"));
        windowMain.initStyle(StageStyle.UNDECORATED);
        windowMain.show();
        //dbConn.connectToDatabase();
    }
}
