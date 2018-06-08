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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Repository
 *
 * @author Christopher G
 */
public class DatenbankRepository {

    private final EntityManager em;
    @Context
    protected SecurityContext securityContext;

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

    public List<Person> deletePerson(int id) {
        try {
            Person p = em.find(Person.class, id);
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();

            return listAllUsers();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
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
        query.setParameter("email", pto.email);
        try {
            Person b = (Person) query.getSingleResult();
//generate token
            String token = generateJWT(b);
            //check password
            if (b.getPassword().equals(pto.password)) {
                PersonTokenTransferObject pt = new PersonTokenTransferObject(String.valueOf(b.getId()), token);
//return user and token
                return pt;
            }
            return null;
        } catch (Exception e) {
            return null;
        }

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
                    .claim("id", b.getId()).claim("role", b.getRolle()).signWith(SignatureAlgorithm.HS256, "secretswaggy132".getBytes("UTF-8")).compact();

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
    public Person insert(int id, Person b) {
        JRKEntitaet j = em.find(JRKEntitaet.class, id);
        b.setJrkentitaet(j);
        em.getTransaction().begin();
        em.merge(b);
        em.getTransaction().commit();
        return b;
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

    public Role[] getAllRoles() {
        return Role.values();
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
     * @param jrk
     */
    public void insert(JRKEntitaet jrk) {
        em.getTransaction().begin();
        em.merge(jrk);
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
        termine = addList(termine, currentPerson.getJrkentitaet().getTermine());
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
        Collections.sort(info, (Info first, Info second) -> first.getDatum().compareTo(second.getDatum()));
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
        //Does List exist and is it filled
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            //Go through all entitys
            for (JRKEntitaet entity : jrkentitaet) {
                //Prepare Termin List, check for duplicates
                termine = addList(termine, entity.getTermine());
                List<Termin> term = termineLayerUp(entity, termine);
                termine = addList(termine, term);
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
                termine = addList(termine, entity.getTermine());
                List<Termin> term = termineLayerDown(entity, termine);
                termine = addList(termine, term);
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
     * @param termine
     * @param tt
     * @return
     */
    private List<Person> addListPerson(List<Person> p, List<Person> pp) {
        if (!p.equals(pp)) {
            for (Person pers : pp) {
                p.add(pers);
            }
        }
        return p;
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
        return p.getJrkentitaet().getTyp() != JRKEntitaetType.Gruppe;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean isAdmin(int id) {
        Person p = em.find(Person.class, id);
        return p.getJrkentitaet().getTyp() == JRKEntitaetType.Landstelle || p.getJrkentitaet().getTyp() == JRKEntitaetType.Bezirkstelle;
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
     * @param jrk
     * @return
     */
    public List<NameValue> getChartValues(JRKEntitaet jrk) {
        List<Termin> list = jrk.getTermine();
        //to count the categories
        int[] katcount = new int[3];
        //go through the terminlist and count its categories
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

    /**
     *
     * @param jrk
     * @return
     */
    public List<NameValue> getTimelineValues(JRKEntitaet jrk) {
        //to count the categories
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

            if (nv.getValue() == 0) {
                nv.setValue(0.1);
            }
            returnlist.add(nv);
        }

        return returnlist;
    }

    /**
     *
     * @param jrk
     * @return
     */
    public List<NameValue> getLowerEntityHourList(JRKEntitaet jrk) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<NameValue> returnlist = new LinkedList<>();
        List<JRKEntitaet> jrks = em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        // go through all hierarchicly lower jrk entities
        for (JRKEntitaet jr : jrks) {
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
     * @param jrk
     * @return
     */
    public List<NameValue> getYearlyHoursPerPeople(JRKEntitaet jrk) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        /* This is how to declare HashMap */
        HashMap<String, Integer> hmap = new HashMap<>();

        List<NameValue> kat = new LinkedList<>();

        List<Termin> list = jrk.getTermine();

        if (list != null) {
            for (Termin termin : list) {
                Dokumentation doku = termin.getDoko();
                if (doku != null) {
                    LocalDateTime start = LocalDateTime.parse(termin.getS_date(), formatter);
                    LocalDateTime ende = LocalDateTime.parse(termin.getE_date(), formatter);
                    // get the betreues time
                    hmap.put("Betreuer" + start.getYear(), ((hmap.get("Betreuer" + start.getYear()) == null ? 0 : hmap.get("Betreuer" + start.getYear())) + (int) ChronoUnit.HOURS.between(start, ende)) * doku.getBetreuer().length);
                    //get the kinders time
                    hmap.put("Kinder" + start.getYear(), ((hmap.get("Kinder" + start.getYear()) == null ? 0 : hmap.get("Kinder" + start.getYear())) + (int) ChronoUnit.HOURS.between(start, ende)) * doku.getKinderliste().length);
                    //POSSIBLE BUG: San ChronoUnit Hours gleichgroß wie deine Hours?
                    //get the Preparationtime
                    hmap.put("Vorbereitung" + start.getYear(), (hmap.get("Vorbereitung" + start.getYear()) == null ? 0 : hmap.get("Vorbereitung" + start.getYear())) + (int) doku.getVzeit());
                }
            }

        }
        List<NameValue> returnlist = new LinkedList<>();
        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            System.out.print("key is: " + mentry.getKey() + " & Value is: ");
            System.out.println(mentry.getValue());
            returnlist.add(new NameValue(mentry.getKey().toString(), Integer.valueOf(mentry.getValue().toString())));
        }

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

    /**
     *
     * @param id
     * @return
     */
    public Termin getTerminbyId(int id) {
        return em.find(Termin.class, id);
    }

    /**
     *
     * @param p
     */
    public void changePassword(Person p) {
        String password = p.getPassword();
        p = em.find(Person.class, p.getId());
        p.setPassword(password);
        p.setPasswordChanged(true);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean needPwdChange(int id) {
        Person p = em.find(Person.class, id);
        boolean isChanged = p.isPasswordChanged();
        return isChanged;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Person> getUsersLayerDown(int id) {
        Person currentPerson = em.find(Person.class, id);
        JRKEntitaet jrk = currentPerson.getJrkentitaet();
        this.getJRKEntitaetdown(id);
        List<JRKEntitaet> jrks = em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        List<Person> pers = new LinkedList<>();
        for (JRKEntitaet j : jrks) {
            List<Person> p = em.createNamedQuery("Benutzer.byjrkEntitaet", Person.class).setParameter("id", j).getResultList();
            this.addListPerson(pers, p);
        }
        return pers;
    }

    public List<Person> getUsersLayerDownJRK(int id) {
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);
        List<JRKEntitaet> jrks = em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        List<Person> pers = new LinkedList<>();
        for (JRKEntitaet j : jrks) {
            List<Person> p = em.createNamedQuery("Benutzer.byjrkEntitaet", Person.class).setParameter("id", j).getResultList();
            this.addListPerson(pers, p);
        }
        return pers;
    }

    public void savePerson(Person p) {
        insert(p);
    }

    /**
     *
     * @param id
     * @param i
     */
    public void insertInfo(int id, Info i) {
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);
        jrk.addInfo(i);

        insert(jrk);
    }
}
