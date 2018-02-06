/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import entities.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.persistence.*;
import org.json.JSONObject;
import service.*;

/**
 *
 * @author isi
 */
public class DatenbankRepository {

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
    public PersonTokenTransferObject login(PersonTransferObject pto) {
        Query query = em.createNamedQuery("Benutzer.login", Person.class);
        query.setParameter("personalnr", pto.personalnr);
        Person b = (Person) query.getSingleResult();

        String token = generateJWT();
        if (b.getPassword().equals(pto.password)) {
            PersonTokenTransferObject pt = new PersonTokenTransferObject(String.valueOf(b.getId()), token);
            return pt;
        }
        return null;
    }

    public String generateJWT() {
        try {
            String jwt = Jwts.builder().setSubject("1234567890")
                    .setId("bbe02373-36ce-46b7-80d2-1ba4c866d7bb")
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plusSeconds(10000)))
                    .claim("name", "John Doe")
                    .claim("admin", true).signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8")).compact();

            return jwt;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DatenbankRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String decodeJWT(String jwt) {
        try {

            try {
                return Jwts.parser()
                        .setSigningKey("secret".getBytes("UTF-8"))
                        .parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImp0aSI6ImJiZTAyMzczLTM2Y2UtNDZiNy04MGQyLTFiYTRjODY2ZDdiYiIsImlhdCI6MTUxNjEwMjY4NSwiZXhwIjoxNTE2MTA2Mjg1fQ.zYcRHkM9RVqQN079rn0lY1rS1Qz4BmsanxsOBptJlbE"
                        ).getSignature();

                //OK, we can trust this JWT
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(DatenbankRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SignatureException e) {

            //don't trust the JWT!
        }
        return null;
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

    public JWTTokenUser insert(JWTTokenUser b) {
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
     * @param ortsstelle
     */
    public void insert(JRKEntitaet ortsstelle) {
        em.getTransaction().begin();
        em.merge(ortsstelle);
        em.getTransaction().commit();
    }

    /**
     *
     * @param doku
     */
    public void insert(Dokumentation doku) {
        em.getTransaction().begin();
        em.merge(doku);
        em.getTransaction().commit();
    }

    /**
     *
     * @return
     */
    public List<Termin> termine() {
        return em.createNamedQuery("Termin.listAll", Termin.class).getResultList();
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
        return jrk;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Person> getUsersbytheirJRKEntity(int id) {
        return em.find(JRKEntitaet.class, id).getPersons1();
    }

    /**
     *
     * @param id
     * @return
     */
    public Dokumentation getDokumentationbyTermin(int id) {
        return em.find(Termin.class, id).getDoku();
    }

    public Termin getTerminbyId(int id) {
        return em.find(Termin.class, id);
    }

}
