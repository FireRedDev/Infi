/*
 * Repository
 */
package repository;

import RestResponseClasses.PersonTransferObject;
import RestResponseClasses.JWTTokenUser;
import RestResponseClasses.PersonTokenTransferObject;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

/**
 * Repository
 *
 * @author Christopher G
 */
public class DatenbankRepository {

    private final EntityManager em;

    /**
     * Konstruktor
     */
    public DatenbankRepository() {
//get database
        em = EntityManagerSingleton.getInstance().getEm();
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
     * Returns all Users
     *
     * @return
     */
    public List<Person> listAllUsers() {
        return em.createNamedQuery("Benutzer.listAll", Person.class).getResultList();
    }

    /**
     * Login
     *
     * @param pto
     * @return
     */
    public PersonTokenTransferObject login(PersonTransferObject pto) {
        //Find User and create Person Object
        Query query = em.createNamedQuery("Benutzer.login", Person.class);
        query.setParameter("personalnr", pto.personalnr);
        Person b = (Person) query.getSingleResult();
//generate token
        String token = generateJWT(b);
        //check password
        if (b.getPassword().equals(pto.password)) {
            PersonTokenTransferObject pt = new PersonTokenTransferObject(String.valueOf(b.getId()), token);
//return user and token
            em.getTransaction().begin();
            em.merge(new JWTTokenUser(token, b));
            em.getTransaction().commit();
            return pt;
        }
        return null;
    }

    /**
     * Generate JWT String with JWTs Libary, encrypted
     *
     * @param b
     * @return
     */
    public String generateJWT(Person b) {
        try {
            String jwt = Jwts.builder().setSubject("1234567890")
                    .setId(String.valueOf(b.getId()))
                    .claim("admin", true).signWith(SignatureAlgorithm.HS256, "secretswaggy132".getBytes("UTF-8")).compact();

            return jwt;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DatenbankRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Insert Person into Database
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
     * @param b
     * @return
     */
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
    public void insert(OrganisationalEntity ortsstelle) {
        em.getTransaction().begin();
        em.merge(ortsstelle);
        em.getTransaction().commit();
    }

    /**
     * Lists all Termine
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
        //Check for Duplicates, prepare Termin list
        addList(termine, currentPerson.getJrkentitaet().getTermine());
        //Recursivly get Termine hierarchic upwards
        termine = this.termineLayerDown(currentPerson.getJrkentitaet(), termine);
        //Recursivly get Termine hierarchic downwards
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
        //find Person with primary key
        Person currentPerson = em.find(Person.class, id);
        addListInfo(info, currentPerson.getJrkentitaet().getInfo());
        //Recursivly get Info hierarchic upwards
        //Recursivly get Info hierarchic downwards
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
    private List<Termin> termineLayerUp(OrganisationalEntity jrk, List<Termin> termine) {
        List<OrganisationalEntity> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerUp", OrganisationalEntity.class).setParameter("jrkentitaet", jrk).getResultList();
        //Does List exist and is it filled
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            //Go through all entitys
            for (OrganisationalEntity entity : jrkentitaet) {
                //Prepare Termin List, check for duplicates
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
    private List<Termin> termineLayerDown(OrganisationalEntity jrk, List<Termin> termine) {
        List<OrganisationalEntity> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerDown", OrganisationalEntity.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (OrganisationalEntity entity : jrkentitaet) {
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
    private List<Info> infoLayerUp(OrganisationalEntity jrk, List<Info> info) {
        List<OrganisationalEntity> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerUp", OrganisationalEntity.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (OrganisationalEntity entity : jrkentitaet) {
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
    private List<Info> infoLayerDown(OrganisationalEntity jrk, List<Info> info) {
        List<OrganisationalEntity> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerDown", OrganisationalEntity.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (OrganisationalEntity entity : jrkentitaet) {
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
            tt.forEach((te) -> {
                termine.add(te);
            });
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
    public List<OrganisationalEntity> listAllJRK() {
        return em.createNamedQuery("JRKEntitaet.listAll", OrganisationalEntity.class).getResultList();
    }

    /**
     *
     * @param id
     * @param t
     */
    public void insertTermin(int id, Termin t) {
        OrganisationalEntity jrk = em.find(OrganisationalEntity.class, id);
        t.setDoko(null);
        jrk.addTermin(t);
        insert(jrk);
    }

    /**
     *
     * @param id
     * @return
     */
    public OrganisationalEntity getJRKEntitaet(int id) {
        OrganisationalEntity jrk = em.createNamedQuery("Benutzer.jrkEntitaet", OrganisationalEntity.class).setParameter("id", id).getSingleResult();
        return jrk;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean isEditor(int id) {
        Person p = em.find(Person.class, id);
        return p.getJrkentitaet().getTyp() != OrganisationEntityType.Gruppe;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Termin> getOpenDoko(int id) {
        List<Termin> termine = this.getUsertermine(id);
        List<Termin> te = new LinkedList<>();
        //Parser for our Date Format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Termin t : termine) {
            //Where is no documentation and the Date is before right now
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
        OrganisationalEntity jrk = em.find(OrganisationalEntity.class, id);
        List<Termin> list = jrk.getTermine();
        //to count the categories
        int[] katcount = new int[3];
        //go through the terminlist and count its categories
        for (Termin termin : list) {
            Dokumentation doku = termin.getDoko();
            if(doku!=null){
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

    /**
     *
     * @param id
     * @return
     */
    public List<NameValue> getTimelineValues(int id) {
        //to count the categories
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        OrganisationalEntity jrk = em.find(OrganisationalEntity.class, id);
        List<Termin> list = jrk.getTermine();
        List<NameValue> returnlist = new LinkedList<>();
        //go through all 12 Months
        for (Month month : Month.values()) {
            //for each month create a namevalue class(name is the month)
            NameValue nv = new NameValue(month.getDisplayName(TextStyle.FULL, Locale.getDefault()), 0);
            for (Termin termin : list) {
                //get the value attribute by adding the hours to the value attribute(with help of hours) if the month is our current month
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

    /**
     *
     * @param id
     * @return
     */
    public List<NameValue> getLowerEntityHourList(int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        OrganisationalEntity jrk = em.find(OrganisationalEntity.class, id);

        List<NameValue> returnlist = new LinkedList<>();
        List<OrganisationalEntity> jrks = em.createNamedQuery("JRKEntitaet.layerDown", OrganisationalEntity.class).setParameter("jrkentitaet", jrk).getResultList();
        // go through all hierarchicly lower jrk entities
        for (OrganisationalEntity jr : jrks) {
            //get current jrks termine
            List<Termin> list = jr.getTermine();
            NameValue nv = new NameValue(jr.getName(), 2);
            //count the time of each termin for the jrk entity
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

    /**
     *
     * @param id
     * @return
     */
    public List<NameValue> getYearlyHoursPerPeople(int id) {
        OrganisationalEntity jrk = em.find(OrganisationalEntity.class, id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Termin> list = jrk.getTermine();
        int[] katcount = new int[3];
        if(list!=null){
        for (Termin termin : list) {
            Dokumentation doku = termin.getDoko();
            // get the betreues time
            katcount[0] = (katcount[0] + (int) ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter))) * doku.getBetreuer().length;
            //get the kinders time
            katcount[1] = (katcount[1] + (int) ChronoUnit.HOURS.between(LocalDateTime.parse(termin.getS_date(), formatter), LocalDateTime.parse(termin.getE_date(), formatter))) * doku.getKinderliste().length;
            //POSSIBLE BUG: San ChronoUnit Hours gleichgroß wie deine Hours?
            //get the Preparationtime
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
    public List<OrganisationalEntity> getJRKEntitaetdown(int id) {
        Person currentPerson = em.find(Person.class, id);
        OrganisationalEntity jrk = currentPerson.getJrkentitaet();
        return em.createNamedQuery("JRKEntitaet.layerDown", OrganisationalEntity.class).setParameter("jrkentitaet", jrk).getResultList();
    }

    /**
     *
     * @param id
     * @return
     */
    public Termin getTerminbyId(int id) {
        return em.find(Termin.class, id);
    }

}
