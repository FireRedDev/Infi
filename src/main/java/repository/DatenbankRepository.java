/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Benutzer;
import javax.persistence.*;

/**
 *
 * @author isi
 */
public class DatenbankRepository {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public DatenbankRepository() {
        emf = Persistence.createEntityManagerFactory("infiPU");
        em = emf.createEntityManager();
    }

    public boolean login(Benutzer user) {
        boolean access = user.getUsername().equals("admin") && user.getPassword().equals("admin");
        return access;
    }

    public void init() {
        Benutzer user1=new Benutzer("admin", "admin");
        em.getTransaction().begin();
        em.persist(user1);
        em.getTransaction().commit();
    }

}
