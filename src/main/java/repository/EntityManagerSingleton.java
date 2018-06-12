package repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Used to get the Entity Manager of this Project, prevents Errors
 *
 * @author Christopher G
 */
public class EntityManagerSingleton {

    private static EntityManagerSingleton instance = null;
    private EntityManager em;

    /**
     *
     */
    protected EntityManagerSingleton() {
        em = Persistence.createEntityManagerFactory("infiPU").createEntityManager();

    }

    /**
     *
     * @return
     */
    public static EntityManagerSingleton getInstance() {
        if (instance == null) {
            instance = new EntityManagerSingleton();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     *
     * @param em
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
