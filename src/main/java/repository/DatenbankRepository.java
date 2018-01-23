/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.*;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author isi
 */
public class DatenbankRepository {

    @PersistenceContext

    private EntityManager em;

    /**
     *
     */
    public DatenbankRepository() {
        em = Persistence.createEntityManagerFactory("infiPU").createEntityManager();
    }

    /**
     *
     * @param measurement
     * @return
     */
    public Person addBenutzer(Person measurement) {
        em.getTransaction().begin();
        em.persist(measurement);
        em.getTransaction().commit();
        return measurement;
    }

    /**
     *
     * @return
     */
    public List<Person> listAllUsers() {
        return em.createNamedQuery("Benutzer.listAll", Person.class).getResultList();
    }

    /**
     *
     * @param user
     * @return
     */
    public int login(Person user) {
        Person b = em.createNamedQuery("Benutzer.login", Person.class).setParameter("personalnr", user.getPersonalnr()).getSingleResult();
        if (b.getPassword().equals(user.getPassword())) {
            return b.getId();
        }
        return -1;
    }

    /**
     *
     * @param b
     * @return
     */
    public Person insert(Person b) {
        em.getTransaction().begin();
        em.merge(b);
        em.getTransaction().commit();
        return b;
    }

    /**
     *
     * @param termin
     * @return
     */
    public Termin insert(Termin termin) {
        em.getTransaction().begin();
        em.persist(termin);
        em.getTransaction().commit();
        return termin;
    }

    public void insert(Dokumentation doku) {
        em.getTransaction().begin();
        em.persist(doku);
        em.getTransaction().commit();
    }

    public void insert(JRKEntitaet ortsstelle) {
        em.getTransaction().begin();
        em.merge(ortsstelle);
        em.getTransaction().commit();
    }

    /**
     *
     * @return
     */
    public List<Termin> termine() {
        List<Termin> termine = em.createNamedQuery("Termin.listAll", Termin.class).getResultList();
        for (Termin t : termine) {
            t.setJrkEntitaet(null);
        }
        return termine;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Termin> getUsertermine(int id) {
        List<Termin> termine = new LinkedList();
        Person currentPerson = em.find(Person.class, id);
        addList(termine, currentPerson.getJrkentitaet().getTermine());
        termine = this.termineLayerDown(currentPerson.getJrkentitaet(), termine);
        termine = this.termineLayerUp(currentPerson.getJrkentitaet(), termine);
        return termine;
    }

    private List<Termin> termineLayerUp(JRKEntitaet jrk, List<Termin> termine) {
        List<JRKEntitaet> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerUp", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (JRKEntitaet entity : jrkentitaet) {
                List<Termin> t = em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("jrkentitaet", entity).getResultList();
                addList(termine, t);
                List<Termin> term = termineLayerUp(entity, termine);
                addList(termine, term);
            }
        }
        return termine;
    }

    private List<Termin> termineLayerDown(JRKEntitaet jrk, List<Termin> termine) {
        List<JRKEntitaet> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (JRKEntitaet entity : jrkentitaet) {
                List<Termin> t = em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("jrkentitaet", entity).getResultList();
                addList(termine, t);
                List<Termin> term = termineLayerDown(entity, termine);
                addList(termine, term);
            }
        }
        return termine;
    }

    private List<Termin> addList(List<Termin> termine, List<Termin> tt) {
        if (!termine.equals(tt)) {
            for (Termin te : tt) {
                te.setJrkEntitaet(null);
                termine.add(te);
            }
        }
        return termine;
    }

    /**
     *
     * @param id
     * @return
     */
    public String username(int id) {
        return em.createNamedQuery("Benutzer.name", String.class).setParameter("id", id).getSingleResult();
    }

    /**
     *
     * @return
     */
    public List<JRKEntitaet> listAllJRK() {
        return em.createNamedQuery("JRKEntitaet.listAll", JRKEntitaet.class).getResultList();
    }

    /**
     *
     * @param t
     */
    public void insertTermin(Termin t) {
        JRKEntitaet jrk = em.find(JRKEntitaet.class, t.getJrkEntitaet().getId());
        t.setDoko(null);
        jrk.addTermin(t);
        insert(jrk);
    }

    /**
     *
     * @param id
     * @return
     */
    public JRKEntitaet getJRKEntitaet(int id) {
        JRKEntitaet jrk = em.createNamedQuery("Benutzer.jrkEntitaet", JRKEntitaet.class).setParameter("id", id).getSingleResult();
        jrk.setTermine(null);
        jrk.setJrkentitaet1(null);
        jrk.setPersons1(null);
        jrk.setPersons(null);
        jrk.setJrkentitaet(null);
        return jrk;
    }

    public boolean isEditor(int id) {
        Person p = em.find(Person.class, id);
        return !p.getLeitet().isEmpty();
    }

    public List<Termin> getOpenDoko(int id) {
        List<Termin> termine = this.getUsertermine(id);
        List<Termin> te = new LinkedList<>();
        for (Termin t : termine) {
            if(t.getDoko()==null){
                te.add(t);
            }
        }
        return te;
    }

    public void insertDoko(Termin d) {
        em.getTransaction().begin();
        em.merge(d);
        em.getTransaction().commit();
    }

    /**
     *
     * @param id
     * @return
     */
    public Dokumentation getDokumentationbyTermin(int id) {
        return em.find(Termin.class, id).getDoko();
    }
}
