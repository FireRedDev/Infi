/*
 * Repository
 */
package repository;

import entities.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.persistence.*;

public class DatenbankRepository {

    @PersistenceContext

    private EntityManager em;

    /**
     * Konstruktor
     */
    public DatenbankRepository() {
        em = Persistence.createEntityManagerFactory("infiPU").createEntityManager();
    }

    /**
     * Add Benutzer
     * @param p
     * @return
     */
    public Person addBenutzer(Person p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return p;
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

    /**
     * 
     * @param doku 
     */
    public void insert(Dokumentation doku) {
        em.getTransaction().begin();
        em.persist(doku);
        em.getTransaction().commit();
    }

    /**
     * 
     * @param ortsstelle 
     */
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

    /**
     * 
     * @param jrk
     * @param termine
     * @return 
     */
    private List<Termin> termineLayerUp(JRKEntitaet jrk, List<Termin> termine) {
        List<JRKEntitaet> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerUp", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (JRKEntitaet entity : jrkentitaet) {
                addList(termine, entity.getTermine());
                List<Termin> term = termineLayerUp(entity, termine);
                addList(termine, term);
            }
        }
        return termine;
    }

    /**
     * 
     * @param jrk
     * @param termine
     * @return 
     */
    private List<Termin> termineLayerDown(JRKEntitaet jrk, List<Termin> termine) {
        List<JRKEntitaet> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (JRKEntitaet entity : jrkentitaet) {
                addList(termine, entity.getTermine());
                List<Termin> term = termineLayerDown(entity, termine);
                addList(termine, term);
            }
        }
        return termine;
    }

    /**
     * 
     * @param termine
     * @param tt
     * @return 
     */
    private List<Termin> addList(List<Termin> termine, List<Termin> tt) {
        if (!termine.equals(tt)) {
            for (Termin te : tt) {
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
     * @param id
     * @param t
     */
    public void insertTermin(int id, Termin t) {
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);
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
        return jrk;
    }

    /**
     * 
     * @param id
     * @return 
     */
    public boolean isEditor(int id) {
        Person p = em.find(Person.class, id);
        return p.getJrkentitaet().getTyp()!=Typ.Gruppe;
    }

    /**
     * 
     * @param id
     * @return 
     */
    public List<Termin> getOpenDoko(int id) {
        List<Termin> termine = this.getUsertermine(id);
        List<Termin> te = new LinkedList<>();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Termin t : termine) {
            if (t.getDoko() == null && LocalDate.parse(t.getE_date(),formatter).isBefore(LocalDate.now())) {
                te.add(t);
            }
        }
        return te;
    }

    /**
     * 
     * @param d 
     */
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
