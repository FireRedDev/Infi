/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.Benutzer;
import entities.Ortsstelle;
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

    public Benutzer addBenutzer(Benutzer measurement) {
        em.getTransaction().begin();
        em.persist(measurement);
        em.getTransaction().commit();
        return measurement;
    }

    public List<Benutzer> listAll() {
        return em.createNamedQuery("Benutzer.listAll", Benutzer.class).getResultList();
    }

    public int login(Benutzer user) {
        Benutzer b = em.createNamedQuery("Benutzer.login", Benutzer.class).setParameter("username", user.getUsername()).getSingleResult();
        if (b.getPassword().equals(user.getPassword())) {
            return b.getId();
        }
        return -1;
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

    public List<Termin> termine(int id) {
        List<Termin> termine = new LinkedList();
        List<Termin> t = em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("benutzerid", id).getResultList();
        termine = addList(termine, t);
        termine = termineLayerUp(id, termine);
        termine = termineLayerDown(id, termine);
        return termine;
    }

    private List<Termin> termineLayerDown(int id, List<Termin> termine) {
        List<Benutzer> bb = em.createNamedQuery("Benutzer.chef", Benutzer.class).setParameter("id", id).getResultList();
        if (bb != null || !bb.isEmpty()) {
            for (Benutzer b : bb) {
                List<Termin> ter = em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("benutzerid", b.getId()).getResultList();
                termine = addList(termine, ter);
                List<Termin> term = termineLayerDown(b.getId(), termine);
                termine = addList(termine, term);
            }
        }
        return termine;
    }

    private List<Termin> termineLayerUp(int id, List<Termin> termine) {
        List<Benutzer> benutzer = em.createNamedQuery("Benutzer.list", Benutzer.class).setParameter("id", id).getResultList();
        if (benutzer != null || !benutzer.isEmpty()) {
            for (Benutzer b : benutzer) {
                if (b.getBenutzer1() != null) {
                    List<Termin> t = em.createNamedQuery("Termin.listBenutzer", Termin.class).setParameter("benutzerid", b.getBenutzer1().getId()).getResultList();
                    termine = addList(termine, t);
                    List<Termin> term = termineLayerUp(b.getBenutzer1().getId(), termine);
                    termine = addList(termine, term);
                }
            }
        }

        return termine;
    }

    private List<Termin> addList(List<Termin> termine, List<Termin> tt) {
        if (!termine.equals(tt)) {
            for (Termin te : tt) {
                termine.add(te);
            }
        }
        return termine;
    }

    public String username(int id) {
        return em.createNamedQuery("Benutzer.username", String.class).setParameter("id", id).getSingleResult();
    }
}
