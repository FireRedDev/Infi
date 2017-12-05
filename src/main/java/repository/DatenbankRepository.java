/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Person;
import entities.JRKEntitaet;
import entities.Termin;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

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

    public Person addBenutzer(Person measurement) {
        em.getTransaction().begin();
        em.persist(measurement);
        em.getTransaction().commit();
        return measurement;
    }

    public List<Person> listAllUsers() {
        return em.createNamedQuery("Benutzer.listAll", Person.class).getResultList();
    }

    public int login(Person user) {
        Person b = em.createNamedQuery("Benutzer.login", Person.class).setParameter("personalnr", user.getPersonalnr()).getSingleResult();
        if (b.getPassword().equals(user.getPassword())) {
            return b.getId();
        }
        return -1;
    }

  

    // Einfügen einer neuen Messung
    public Person insert(Person b) {
        em.getTransaction().begin();
        em.merge(b);
        em.getTransaction().commit();
        return b;
    }

    public Termin insert(Termin termin) {
        em.getTransaction().begin();
        em.persist(termin);
        em.getTransaction().commit();
        return termin;
    }

    public void insert(JRKEntitaet ortsstelle) {
        em.getTransaction().begin();
        em.merge(ortsstelle);
        em.getTransaction().commit();
    }

    public List<Termin> termine() {
        return em.createNamedQuery("Termin.listAll", Termin.class).getResultList();
    }

    public List<Termin> getUsertermine(int id) {
        List<Termin> termine = new LinkedList();
       Person currentPerson= em.find(Person.class, id);
       termine =currentPerson.getJrkentitaet().getTermine();
       termine.addAll(this.termineLayerDown(currentPerson.getJrkentitaet()));
       termine.addAll(this.termineLayerUp(currentPerson.getJrkentitaet()));
//        List<Termin> t = em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("benutzerid", id).getResultList();
//        termine = addList(termine, t);
//        //termine = termineLayerUp(id, termine);
//        termine = termineLayerDown(id, termine);
//        return termine;
return termine;
    }

    private List<Termin> termineLayerUp(JRKEntitaet jrk) {
        List<Termin> termine = new LinkedList();
        while(jrk.getJrkentitaet()!=null) {
            termine.addAll(jrk.getJrkentitaet().getTermine());
            jrk = jrk.getJrkentitaet();
        }
        return termine;
    }

 private List<Termin> termineLayerDown(JRKEntitaet jrk) {
     List<Termin> termine = new LinkedList();
        if(jrk.getJrkentitaet1()!=null) {
            for (JRKEntitaet entity : jrk.getJrkentitaet1()){
               termine.addAll(termineLayerDown(entity));
        }}
        return termine;
    }
   

    public String username(int id) {
        return em.createNamedQuery("Benutzer.name", String.class).setParameter("id", id).getSingleResult();
    }

    public List<JRKEntitaet> listAllJRK() {
       return em.createNamedQuery("JRKEntitaet.listAll", JRKEntitaet.class).getResultList();
    }

    public void insert(List<JRKEntitaet> landesleitung) {
        for(JRKEntitaet jrkentitaet :landesleitung){
            insert(jrkentitaet);
        }
    }

   

}
