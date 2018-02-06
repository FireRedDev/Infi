/*
 * Repository
 */
package repository;

import RestResponseClasses.NameValue;
import entities.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import io.jsonwebtoken.*;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

public class DatenbankRepository {

    private EntityManager em;

    /**
     * Konstruktor
     */
    public DatenbankRepository() {
        em = Persistence.createEntityManagerFactory("infiPU").createEntityManager();
    }

    /**
     * Add Benutzer
     *
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
        return p.getJrkentitaet().getTyp() != Typ.Gruppe;
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
            if (t.getDoko() == null && LocalDate.parse(t.getE_date(), formatter).isBefore(LocalDate.now())) {
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
//name is kategorie

    /**
     *
     * @param id
     * @return
     */
    public List<NameValue> getChartValues(int id) {
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);
        List<Termin> list = jrk.getTermine();
        int[] katcount = new int[3];
        for (Termin termin : list) {
            Dokumentation doku = termin.getDoko();
            switch (doku.getKategorie()) {
                case "EH":
                    katcount[0]++;
                    break;

                case "Exkursion":
                    katcount[1]++;
                    break;
                case "Soziales":
                    katcount[2]++;
                    break;
            }
        }
        List<NameValue> returnlist = new LinkedList<NameValue>();
        returnlist.add(new NameValue("EH", katcount[0]));
        returnlist.add(new NameValue("Exkursion", katcount[1]));
        returnlist.add(new NameValue("Soziales", katcount[2]));

        return returnlist;
    }

    public List<NameValue> getTimelineValues(int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);
        List<Termin> list = jrk.getTermine();
        List<NameValue> returnlist = new LinkedList<NameValue>();
        for (Month month : Month.values()) {
            NameValue nv = new NameValue(month.getDisplayName(TextStyle.FULL, Locale.getDefault()), 0);
            for (Termin termin : list) {
                LocalDateTime.parse(termin.getS_date(), formatter);
                if (LocalDateTime.parse(termin.getS_date(), formatter).getMonth() == month) {
                    LocalDateTime.parse(termin.getE_date(), formatter);
                    long hours = ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter));
                    nv.setValue(nv.getValue() + (int) hours);
                }
            }
            returnlist.add(nv);
        }

        return returnlist;
    }

    public List<NameValue> getLowerEntityHourList(int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);

        List<NameValue> returnlist = new LinkedList<NameValue>();
        for (JRKEntitaet jr : jrk.getJrkentitaet1()) {

            List<Termin> list = jr.getTermine();
            NameValue nv = new NameValue(jr.getName(), 0);
            for (Termin termin : list) {
                LocalDateTime.parse(termin.getS_date(), formatter);

                LocalDateTime.parse(termin.getE_date(), formatter);
                long hours = ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter));
                nv.setValue(nv.getValue() + (int) hours);

            }
            returnlist.add(nv);
        }

        return returnlist;
    }

    public List<NameValue> getYearlyHoursPerPeople(int id) {
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Termin> list = jrk.getTermine();
        int[] katcount = new int[3];
        for (Termin termin : list) {
            Dokumentation doku = termin.getDoko();
            katcount[0] = (katcount[0] + (int) ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter))) * doku.getBetreuer().length;
            katcount[1] = (katcount[1] + (int) ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter))) * doku.getKinderliste().length;
            //POSSIBLE BUG: San ChronoUnit Hours gleichgroß wie deine Hours?
            katcount[2] = (katcount[2] + (int) ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter))) + (int) doku.getVzeit();
        }
        List<NameValue> returnlist = new LinkedList<NameValue>();
        returnlist.add(new NameValue("Betreuer", katcount[0]));
        returnlist.add(new NameValue("Kinder", katcount[1]));
        returnlist.add(new NameValue("Vorbereitungszeit", katcount[2]));

        return returnlist;
    }

    public Termin getTerminbyId(int id) {
        return em.find(Termin.class, id);
    }

}
