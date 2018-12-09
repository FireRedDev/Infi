package repository;

import RestResponseClasses.*;
import entities.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import io.jsonwebtoken.*;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import service.MySecurityContext;
import service.Service;
import static entities.JRKEntitaetType.*;

/**
 * Repository Communicate with Database
 *
 * @author Christopher G
 */
public class DatenbankRepository {
    
    private final EntityManager em;

    /**
     *
     */
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
        
        em.persist(p);
        
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
     * delete a Person
     *
     * only Landesleiter and Bezirksleiter have the permission to delete Persons
     *
     * @param id id of a Person
     * @return Persons
     */
    public List<Person> deletePerson(int id) {
        try {
            Person p = em.find(Person.class, id);
            
            em.remove(p);
            
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
     * @param id
     * @param b
     * @return
     */
    public Person insert(int id, Person b) {
        JRKEntitaet j = em.find(JRKEntitaet.class, id);
        b.setJrkentitaet(j);
        
        em.merge(b);
        
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
    
    public Planning insert(Planning p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        
        return p;
    }

    /**
     * give back all roles
     *
     * @return Rollen
     */
    public Role[] getAllRoles() {
        return Role.values();
    }

    /**
     * inserts a JWTTokenUser
     *
     * @param b
     * @return
     */
    public JWTTokenUser insert(JWTTokenUser b) {
        
        em.merge(b);
        
        return b;
    }

    /**
     * inserts a Appointment
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
     * inserts a documentation
     *
     * @param doku
     */
    public void insert(Dokumentation doku) {
        
        em.persist(doku);
        
    }

    /**
     * inserts a JRK-Entity
     *
     * @param jrk
     */
    public void insert(JRKEntitaet jrk) {
        
        em.merge(jrk);
        
    }

    /**
     * Get Appointments for a specific User
     *
     * @param id
     * @return
     */
    public List<Termin> getUsertermine(int id) {
        em.clear();
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
     * Get Informations for a specific User
     *
     * @param id
     * @return
     */
    public List<Info> getUserInfos(int id) {
        em.clear();
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
     * Get Appointments in the hierachie down
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
     * Get Appointments in the hierachie down
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
     * Get Informations in the hierachie up
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
     * Get Informations in the hierachie down
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
     * add a Information to a list
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
     * add a Appointment to a list
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
     * add a Person to a list
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
     * Get Username
     *
     * @param id
     * @return
     */
    public String username(int id) {
        return em.createNamedQuery("Benutzer.name", String.class).setParameter("id", id).getSingleResult();
    }

    /**
     * Lists all JRK-Entities
     *
     * @return
     */
    public List<JRKEntitaet> listAllJRK() {
        return em.createNamedQuery("JRKEntitaet.listAll", JRKEntitaet.class).getResultList();
    }

    /**
     * insert a appointment
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
     * get the JRKEntity
     *
     * @param id User id
     * @return
     */
    public JRKEntitaet getJRKEntitaet(int id) {
        JRKEntitaet jrk = em.createNamedQuery("Benutzer.jrkEntitaet", JRKEntitaet.class).setParameter("id", id).getSingleResult();
        return jrk;
    }

    /**
     * Is this User a Editor?
     *
     * @param id
     * @return
     */
    public boolean isEditor(int id) {
        Person p = em.find(Person.class, id);
        return p.getJrkentitaet().getTyp() != JRKEntitaetType.Gruppe;
    }

    /**
     * Is this User a admin
     *
     * @param id
     * @return
     */
    public boolean isAdmin(int id) {
        Person p = em.find(Person.class, id);
        return p.getJrkentitaet().getTyp() == JRKEntitaetType.Landstelle || p.getJrkentitaet().getTyp() == JRKEntitaetType.Bezirkstelle;
    }

    /**
     * Is this User a Gruppenleiter?
     *
     * @param id
     * @return
     */
    public boolean isGruppenleiter(int id) {
        Person p = em.find(Person.class, id);
        return p.getJrkentitaet().getTyp() == JRKEntitaetType.Ortstelle;
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
        em.persist(d.getDoko());
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
     * get JRK-Entities down
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
     * get a Appointment by id
     *
     * @param id
     * @return
     */
    public Termin getTerminbyId(int id) {
        return em.find(Termin.class, id);
    }

    /**
     * change password
     *
     * @param p
     */
    public void changePassword(Person p) {
        String password = p.getPassword();
        p = em.find(Person.class, p.getId());
        p.setPassword(password);
        p.setPasswordChanged(true);
        
        em.persist(p);
        
    }

    /**
     * Does this User have to change his password?
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
     * get Users Layer Down
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
            List<Person> p = em.createNamedQuery("Benutzer.byjrkEntitaet", Person.class).setParameter("id", j.getId()).getResultList();
            this.addListPerson(pers, p);
        }
        return pers;
    }

    /**
     * get the Users Layer Down
     *
     * @param id
     * @return
     */
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

    /**
     * save a Person
     *
     * @param p
     */
    public void savePerson(Person p) {
        insert(p);
    }

    /**
     * insert a information
     *
     * @param id
     * @param i
     */
    public void insertInfo(int id, Info i) {
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);
        jrk.addInfo(i);
        
        insert(jrk);
    }

    /**
     *
     * @param id
     * @param text
     */
    public void insertPlanung(int id, Planning p) {
        Termin termin = em.find(Termin.class, id);
        termin.setPlanning(p);
        
        insert(p);
        insert(termin);
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Termin> getProtokollDetails(int id) {
        List<Termin> termin = new LinkedList();
        
        JRKEntitaet jrk = em.find(JRKEntitaet.class, id);
        System.out.println("here");
        termin = this.termineLayerDown(jrk, termin);
        
        return termin;
    }

    /**
     *
     * @param id
     * @param token
     * @return
     */
    public String setFCMToken(int id, String token) {
        Person p = em.find(Person.class, id);
        p.setFcmtoken(token);
        
        em.merge(p);
        
        Service.firstToken = true;
        return "sucess";
    }

    /**
     *
     * @param terminID
     * @param userID
     */
    public void registerAttendee(int terminID, int userID) {
        Termin t = em.find(Termin.class, terminID);
        Person p = em.find(Person.class, userID);

        //send Message
        t.addTeilnehmer(p.getVorname() + " " + p.getNachname());
        
        em.merge(t);
        
    }

    /**
     *
     * @param terminID
     * @param userID
     */
    public void removeAttendee(int terminID, int userID) {
        Termin t = em.find(Termin.class, terminID);
        Person p = em.find(Person.class, userID);
        if (t.getTeilnehmer().contains(p.getVorname())) {
            t.removeTeilnehmer(p.getVorname());
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public String getTerminTeilnehmer(int id) {
        Termin t = em.find(Termin.class, id);
        return t.getTeilnehmer();
        
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Person> getSupervisor(int id) {
        Person currentPerson = em.find(Person.class, id);
        JRKEntitaet jrk = currentPerson.getJrkentitaet();
        return em.createNamedQuery("Benutzer.byjrkEntitaet").setParameter("id", jrk.getId()).getResultList();
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Person> getChildren(int id) {
        Person currentPerson = em.find(Person.class, id);
        List<Person> pers = new LinkedList<>();
        JRKEntitaet jrk = currentPerson.getJrkentitaet();
        List<JRKEntitaet> jrks = em.createNamedQuery("JRKEntitaet.layerDown").setParameter("jrkentitaet", jrk).getResultList();
        for (JRKEntitaet j : jrks) {
            pers.addAll(em.createNamedQuery("Benutzer.byjrkEntitaet").setParameter("id", j.getId()).getResultList());
        }
        return pers;
    }

    /**
     *
     * @param t
     */
    public void changeTermin(Termin t) {
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
    }

    /**
     *
     * @param i
     */
    public void changeInfo(Info i) {
        em.getTransaction().begin();
        em.merge(i);
        em.getTransaction().commit();
    }

    /**
     *
     * @param t
     * @return
     */
    public String deleteTermin(Termin t) {
        em.getTransaction().begin();
        Termin toRemove = em.merge(t);
        em.remove(toRemove);
        em.getTransaction().commit();
        return "success";
    }
    
    public String deleteInfo(Info i) {
        em.getTransaction().begin();
        Info toRemove = em.merge(i);
        em.remove(toRemove);
        em.getTransaction().commit();
        return "success";
    }
    
    public List<Termin> sharedPlanning() {
        List<Termin> plans = em.createQuery("select t from Termin t").getResultList();
        List<Termin> pl = new ArrayList();
        for (Termin p : plans) {
            if (p.getPlanning() != null) {
                System.out.println(p.getPlanning().getPlannung());
                if (p.getPlanning().isShare()) {
                    pl.add(p);
                }
            }
        }
        return pl;
    }
    
    public List<Termin> getOpenPlanning(int id) {
        List<Termin> termine = this.getUsertermine(id);
        List<Termin> te = new LinkedList<>();
        //Parser for our Date Format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Termin t : termine) {
            //Where is no planning and the Date is before right now
            if (t.getPlanning() == null && LocalDate.parse(t.getE_date(), formatter).isAfter(LocalDate.now())) {
                te.add(t);
            }
        }
        return te;
    }
    
    public void changePlanung(Planning planning) {
        em.merge(planning);
    }
    
    public String deletePlanning(Planning p) {
        Planning pp = null;
        em.getTransaction().begin();
        if (!em.contains(p)) {
            pp = em.merge(p);
        }
        em.remove(pp);
        em.getTransaction().commit();
        return "success";
    }
    
    public List<NameValue> getPersonenstunden(JRKEntitaet jrk) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        /* This is how to declare HashMap */
        HashMap<String, Integer> hmap = new HashMap<>();
        
        List<NameValue> kat = new LinkedList<>();
        
        List<Termin> list = jrk.getTermine();
        List<Person> personen = em.createNamedQuery("Benutzer.byjrkEntitaet").setParameter("id", jrk.getId()).getResultList();
        List<JRKEntitaet> jrks = new LinkedList<>();
        for (Person p : personen) {
            jrks = getJRKEntitaetdown(p.getId());
        }
        for (JRKEntitaet j : jrks) {
            personen = em.createNamedQuery("Benutzer.byjrkEntitaet").setParameter("id", j.getId()).getResultList();
        }
        if (list != null) {
            for (Termin termin : list) {
                LocalDateTime start = LocalDateTime.parse(termin.getS_date(), formatter);
                LocalDateTime ende = LocalDateTime.parse(termin.getE_date(), formatter);
                
                Dokumentation doku = termin.getDoko();
                if (doku != null) {
                    for (Person person : personen) {
                        ArrayList<String> teilnehmer = new ArrayList<String>();
                        teilnehmer.addAll(Arrays.asList(doku.getBetreuer()));
                        teilnehmer.addAll(Arrays.asList(doku.getKinderliste()));
                        for (String teiln : teilnehmer) {
                            try {
                                Person teilnehm = (Person) em.createNamedQuery("Benutzer.findbyname").setParameter("var", teiln).getSingleResult();
                                if (person.equals(teilnehm)) {
                                    int hilf;
                                    if (hmap.get(String.valueOf(person.getVorname() + " " + person.getNachname())) != null) {
                                        hilf = hmap.get(String.valueOf(person.getVorname() + " " + person.getNachname()));
                                    } else {
                                        hilf = 0;
                                    }
                                    hmap.put(String.valueOf(person.getVorname() + " " + person.getNachname()), hilf + (int) ChronoUnit.HOURS.between(start, ende));
                                }
                            } catch (Exception e) {
                                
                            }
                        }
                    }
//                if (doku != null) {
//                    LocalDateTime start = LocalDateTime.parse(termin.getS_date(), formatter);
//                    LocalDateTime ende = LocalDateTime.parse(termin.getE_date(), formatter);
//                    // get the betreues time
//                    hmap.put("Betreuer" + start.getYear(), ((hmap.get("Betreuer" + start.getYear()) == null ? 0 : hmap.get("Betreuer" + start.getYear())) + (int) ChronoUnit.HOURS.between(start, ende)) * doku.getBetreuer().length);
//                    //get the kinders time
//                    hmap.put("Kinder" + start.getYear(), ((hmap.get("Kinder" + start.getYear()) == null ? 0 : hmap.get("Kinder" + start.getYear())) + (int) ChronoUnit.HOURS.between(start, ende)) * doku.getKinderliste().length);
//                    //get the Preparationtime
//                    hmap.put("Vorbereitung" + start.getYear(), (hmap.get("Vorbereitung" + start.getYear()) == null ? 0 : hmap.get("Vorbereitung" + start.getYear())) + (int) doku.getVzeit());
//                }
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
    
}
