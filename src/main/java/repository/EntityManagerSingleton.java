/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
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
