package pl.workers.managment.tool;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.FxmlUtils;

/**
 * Created by kamil on 08.06.2017.
 */
public class Main extends Application {
    private Stage windowMain;
    private static final String MAIN_WINDOW_FXML = "/fxml/mainWindow.fxml";

    public static void main(String[] args){ launch(args);}

    public void start(Stage primaryStage) throws Exception{
        windowMain = primaryStage;
        Pane pane = FxmlUtils.fxmlLoader(MAIN_WINDOW_FXML);
        pane.setStyle("-fx-background-color:  #242F41");
        Scene sceneMain = new Scene(pane);
        windowMain.setScene(sceneMain);
        windowMain.setTitle(FxmlUtils.getResourceBundle().getString("title"));
        windowMain.initStyle(StageStyle.UNDECORATED);
        windowMain.show();
        //DbConn.connectToDatabase();
    }
}
