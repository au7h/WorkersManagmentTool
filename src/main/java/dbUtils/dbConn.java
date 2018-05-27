package dbUtils;

import controllers.connectingController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.dialogsUtils;
import utils.fxmlUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by kamil on 26.06.2017.
 */
public class dbConn {
    public static EntityManager entityManager;
    public static EntityManagerFactory entityManagerFactory;
    public static boolean isConnected = false;

    public static boolean connectToDatabase(){
        Stage stage;
        Pane pane;
        Scene scene;

        /*
        //show connecting to database popup window
        try {
            stage = new Stage();
            pane = fxmlUtils.fxmlLoader("/fxml/connectingToDatabase.fxml");
            connectingController connController = fxmlUtils.loaderForController.getController();
            connController.setStage(stage);
            scene = new Scene(pane);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            new Thread(() -> {
                connController.progressBar.setProgress(0.25);
                entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
                connController.progressBar.setProgress(0.5);
                entityManager = entityManagerFactory.createEntityManager();
                connController.progressBar.setProgress(1);
                connController.buttonOK.setVisible(true);
                connController.labelConnecting.setVisible(false);
                connController.labelConnected.setVisible(true);
                isConnected = true;
            }).start();
        } catch (Exception e){
            dialogsUtils.errorDialog(e.getMessage());
            return false;
        }*/
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
            entityManager = entityManagerFactory.createEntityManager();
            isConnected = true;
        } catch (Exception ex){
            dialogsUtils.errorDialog(ex.getMessage());
        }
        return true;
    }

    public static void disconnectDatabase(){
        entityManager.close();
        entityManagerFactory.close();
        isConnected = false;
    }

    public static void commitDatabase(Object object){
        dbConn.entityManager.getTransaction().begin();
        dbConn.entityManager.persist(object);
        dbConn.entityManager.getTransaction().commit();
    }
}
