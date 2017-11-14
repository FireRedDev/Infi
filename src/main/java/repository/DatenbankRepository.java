/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;



import entities.Benutzer;
import entities.Termin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    public List<Termin> Termine() {
        List<Termin> t = new LinkedList();
        t.add(new Termin(1,"2017-11-09 11:00:00","2017-11-09 13:00:00","Gruppenstunde"));
        t.add(new Termin(2,"2017-11-10 09:00:00","2017-11-10 11:00:00","Gruppenstunde"));
        t.add(new Termin(3,"2017-11-15 11:00:00","2017-11-20 20:00:00","Gruppenstunde"));
        return t;

    }
 
public Boolean login(Benutzer user) {
   Benutzer savedUser= em.find(Benutzer.class, user.getId());
    if(savedUser.equals(user)) {
        return true;
    }
    return false;
}

    public List<Termin> getUserTermine(Benutzer user) {
        return em.find(Benutzer.class, user.getId()).getTermine();
    }
}
