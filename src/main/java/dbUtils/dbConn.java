package dbUtils;

import utils.DialogsUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by kamil on 26.06.2017.
 */
public class DbConn {
    public static EntityManager entityManager;
    public static EntityManagerFactory entityManagerFactory;
    public static boolean isConnected = false;

    public static boolean connectToDatabase(){
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
            entityManager = entityManagerFactory.createEntityManager();
            isConnected = true;
        } catch (Exception ex){
            DialogsUtils.errorDialog(ex.getMessage());
        }
        return true;
    }

    public static void disconnectDatabase(){
        entityManager.close();
        entityManagerFactory.close();
        isConnected = false;
    }

    public static void commitDatabase(Object object){
        DbConn.entityManager.getTransaction().begin();
        DbConn.entityManager.persist(object);
        DbConn.entityManager.getTransaction().commit();
    }
}
