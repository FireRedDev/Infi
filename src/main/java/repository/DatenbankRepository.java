/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Person;
import entities.JRKEntitaet;
import entities.Termin;
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

    public List<Person> listAll() {
        return em.createNamedQuery("Benutzer.listAll", Person.class).getResultList();
    }

    public int login(Person user) {
        Person b = em.createNamedQuery("Benutzer.login", Person.class).setParameter("personalnr", user.getPersonalnr()).getSingleResult();
        if (b.getPassword().equals(user.getPassword())) {
            return b.getId();
        }
        return -1;
    }

    public List<Termin> getUserTermine(Person user) {
        return null;
        //return em.find(Person.class, user.getId()).getTermine();
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

    public List<Termin> termine(int id) {
        List<Termin> termine = new LinkedList();
       Person currentPerson= em.find(Person.class, id);
       return currentPerson.getJrkentitaet().getTermine();
//        List<Termin> t = em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("benutzerid", id).getResultList();
//        termine = addList(termine, t);
//        //termine = termineLayerUp(id, termine);
//        termine = termineLayerDown(id, termine);
//        return termine;
    }

    private List<Termin> termineLayerDown(int id, List<Termin> termine) {
        List<Person> bb = em.createNamedQuery("Benutzer.chef", Person.class).setParameter("id", id).getResultList();
        if (bb != null || !bb.isEmpty()) {
            for (Person b : bb) {
                List<Termin> ter = em.createNamedQuery("Termin.listStellenTermine", Termin.class).setParameter("benutzerid", b.getId()).getResultList();
                termine = addList(termine, ter);
                List<Termin> term = termineLayerDown(b.getId(), termine);
                termine = addList(termine, term);
            }
        }
        return termine;
    }

//    private List<Termin> termineLayerUp(int id, List<Termin> termine) {
//        List<Person> benutzer = em.createNamedQuery("Benutzer.list", Person.class).setParameter("id", id).getResultList();
//        if (benutzer != null || !benutzer.isEmpty()) {
//            for (Person b : benutzer) {
//                if (b.getBenutzer1() != null) {
//                    List<Termin> t = em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("benutzerid", b.getBenutzer1().getId()).getResultList();
//                    termine = addList(termine, t);
//                    List<Termin> term = termineLayerUp(b.getBenutzer1().getId(), termine);
//                    termine = addList(termine, term);
//                }
//            }
//        }
//
//        return termine;
//    }

    private List<Termin> addList(List<Termin> termine, List<Termin> tt) {
        if (!termine.equals(tt)) {
            for (Termin te : tt) {
                termine.add(te);
            }
        }
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
