package repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Singleton used to get the Entity Manager of this Project, prevents Errors
 *
 * @author Christopher G
 */
public class EntityManagerSingleton {

    private static EntityManagerSingleton instance = null;
    private EntityManager em;

    /**
     * Constructor
     */
    protected EntityManagerSingleton() {
        em = Persistence.createEntityManagerFactory("infiPU").createEntityManager();

    }

    /**
     * Gets the Only Instance of the EntityManager
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
     * Getter
     *
     * @return
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * Setter
     *
     * @param em
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
