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
import org.json.JSONObject;

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

        String token = generateJWT(b);
        if (b.getPassword().equals(pto.password)) {
            PersonTokenTransferObject pt = new PersonTokenTransferObject(String.valueOf(b.getId()), token);

            em.getTransaction().begin();
            em.merge(new JWTTokenUser(token, b));
            em.getTransaction().commit();
            return pt;
        }
        return null;
    }

    public String generateJWT(Person b) {
        try {
            String jwt = Jwts.builder().setSubject("1234567890")
                    .setId(String.valueOf(b.getId()))
                    .claim("admin", true).signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8")).compact();

            return jwt;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DatenbankRepository.class.getName()).log(Level.SEVERE, null, ex);
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
     * @param id
     * @return
     */
    public List<Info> getUserInfos(int id) {
        List<Info> info = new LinkedList();
        Person currentPerson = em.find(Person.class, id);
        addListInfo(info, currentPerson.getJrkentitaet().getInfo());
        info = this.infoLayerDown(currentPerson.getJrkentitaet(), info);
        info = this.infoLayerUp(currentPerson.getJrkentitaet(), info);
        return info;
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
     * @param jrk
     * @param termine
     * @return
     */
    private List<Info> infoLayerUp(JRKEntitaet jrk, List<Info> info) {
        List<JRKEntitaet> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerUp", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (JRKEntitaet entity : jrkentitaet) {
                addListInfo(info, entity.getInfo());
                List<Info> term = infoLayerUp(entity, info);
                addListInfo(info, term);
            }
        }
        return info;
    }

    /**
     *
     * @param jrk
     * @param termine
     * @return
     */
    private List<Info> infoLayerDown(JRKEntitaet jrk, List<Info> info) {
        List<JRKEntitaet> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (JRKEntitaet entity : jrkentitaet) {
                addListInfo(info, entity.getInfo());
                List<Info> term = infoLayerDown(entity, info);
                addListInfo(info, term);
            }
        }
        return info;
    }

    /**
     *
     * @param termine
     * @param tt
     * @return
     */
    private List<Info> addListInfo(List<Info> info, List<Info> tt) {
        if (!info.equals(tt)) {
            for (Info te : tt) {
                info.add(te);
            }
        }
        return info;
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
            if (doku != null) {
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
            if (nv.getValue() != 0) {
                returnlist.add(nv);
            }
        }

        return returnlist;
    }

    public List<NameValue> getLowerEntityHourList(int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);

        List<NameValue> returnlist = new LinkedList<NameValue>();
        List<JRKEntitaet> jrks = em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        for (JRKEntitaet jr : jrks) {

            List<Termin> list = jr.getTermine();
            NameValue nv = new NameValue(jr.getName(), 2);
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
        if (list != null) {
            for (Termin termin : list) {
                Dokumentation doku = termin.getDoko();
                katcount[0] = (katcount[0] + (int) ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter))) * doku.getBetreuer().length;
                katcount[1] = (katcount[1] + (int) ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter))) * doku.getKinderliste().length;
                //POSSIBLE BUG: San ChronoUnit Hours gleichgroß wie deine Hours?
                katcount[2] = (katcount[2] + (int) ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter))) + (int) doku.getVzeit();
            }
        }
        List<NameValue> returnlist = new LinkedList<NameValue>();
        returnlist.add(new NameValue("Betreuer", katcount[0]));
        returnlist.add(new NameValue("Kinder", katcount[1]));
        returnlist.add(new NameValue("Vorbereitungszeit", katcount[2]));

        return returnlist;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<JRKEntitaet> getJRKEntitaetdown(int id) {
        Person currentPerson = em.find(Person.class, id);
        JRKEntitaet jrk = currentPerson.getJrkentitaet();
        return em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
    }

    public Termin getTerminbyId(int id) {
        return em.find(Termin.class, id);
    }

    public void changePassword(Person p) {
        String password = p.getPassword();
        p = em.find(Person.class, p.getId());
        if (!p.isPasswordChanged()) {
            p.setPassword(password);
            p.setPasswordChanged(true);
        }
    }

    public boolean needPwdChange(int id) {
        Person p = em.find(Person.class, id);
        boolean isChanged= p.isPasswordChanged();
        return isChanged;
    }

}
