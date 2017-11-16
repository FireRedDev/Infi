/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Benutzer;
import entities.Ortsstelle;
import entities.Termin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.json.JSONObject;

/**
 *
 * @author isi
 */
public class DatenbankRepository {

    @PersistenceContext

    private EntityManager em;

    public DatenbankRepository() {
        em = Persistence.createEntityManagerFactory("infiPU").createEntityManager();
    }

    public Benutzer addBenutzer(Benutzer measurement) {
        em.getTransaction().begin();
        em.persist(measurement);
        em.getTransaction().commit();
        return measurement;
    }

    public List<Benutzer> listAll() {
        return em.createNamedQuery("Benutzer.listAll", Benutzer.class).getResultList();
    }

    public Benutzer login(Benutzer user) {
        Benutzer b = em.createNamedQuery("Benutzer.login", Benutzer.class).setParameter("username", user.getUsername()).getSingleResult();
        if (b.getPassword().equals(user.getPassword())) {
            return b;
        }
        return b;
    }

    public List<Termin> getUserTermine(Benutzer user) {
        return em.find(Benutzer.class, user.getId()).getTermine();
    }

    // Einfügen einer neuen Messung
    public Benutzer insert(Benutzer b) {
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
        return b;
    }

    // Einfügen mehrere Messungen
    public void insert(List<Benutzer> benutzer) {
        for (Benutzer b : benutzer) {
            this.insert(b);
        }
    }

    public Termin insert(Termin termin) {
        em.getTransaction().begin();
        em.persist(termin);
        em.getTransaction().commit();
        return termin;
    }

    public void insert(Ortsstelle ortsstelle) {
        em.getTransaction().begin();
        em.persist(ortsstelle);
        em.getTransaction().commit();
    }

    public List<Termin> termine() {
        return em.createNamedQuery("Termin.listAll", Termin.class).getResultList();
    }

    public List<Termin> termine(Benutzer user) {
        List<Termin>termine= em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("benutzer", user).getResultList();
        List<Termin> dates=new LinkedList();
        for(Termin t : termine){
            if(t.getBenutzer().equals(user)){
                dates.add(t);
            }
        }
        return dates;
    }

}
