package service;

import RestResponseClasses.NameValue;
import entities.*;
import static entities.Typ.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import repository.DatenbankRepository;

/**
 *
 * @author INFI-Projektgruppe http://localhost:8080/api/service/message
 */
@Path("service")
public class Service {

    private DatenbankRepository repo = new DatenbankRepository();

    /**
     * Servertestfunktion
     *
     * @return
     */
    @GET
    @Path("message")
    public String message() {
        return "INFI Jugendrotkreuz Server up and running..";
    }

    /**
     * Inserts and creates Test Values
     *
     * @return
     */
    @Path("init")
    @GET
    public String init() {
        Typ gruppe = Gruppe;
        Typ ortstelle = Ortstelle;
        Typ bezirkstelle = Bezirkstelle;
        Typ landesstelle = Landstelle;
        JRKEntitaet ooe = new JRKEntitaet(5, "Oberösterreich", landesstelle, null);
        JRKEntitaet wels = new JRKEntitaet(4, "Wels", bezirkstelle, ooe);
        JRKEntitaet sattledt = new JRKEntitaet(1, "Sattledt", ortstelle, wels);
        JRKEntitaet sattledt1 = new JRKEntitaet(2, "Gruppe1", gruppe, sattledt);
        JRKEntitaet marchtrenk = new JRKEntitaet(3, "Marchtrenk", ortstelle, wels);
        JRKEntitaet marchtrenk1 = new JRKEntitaet(6, "Gruppe1", gruppe, marchtrenk);

        List<JRKEntitaet> landesleitung = new LinkedList<>();
        landesleitung.add(ooe);
        List<JRKEntitaet> bezirksleitung_wels = new LinkedList<>();
        bezirksleitung_wels.add(sattledt);
        List<JRKEntitaet> ortstelle_sattledt = new LinkedList<>();
        ortstelle_sattledt.add(sattledt1);
        bezirksleitung_wels.add(marchtrenk);
        List<JRKEntitaet> ortstelle_marchtrenk = new LinkedList<>();
        ortstelle_marchtrenk.add(marchtrenk1);

        //Dokumentation
        Termin sattledttermin = new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        String[] betreuer = {"Gusi", "Isi"};
        String[] kinder = {"Meli", "Antonia", "Luki"};
        sattledttermin.setDoko(new Dokumentation(kinder, betreuer, "basteln", 2.0, "Soziales"));

        sattledt1.addTermin(sattledttermin);
        marchtrenk1.addTermin(new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Eislaufen", "Bitte Eislaufschuhe, Winterkleidung und 3€ Eintritt mitnehmen", "Eislaufplatz Marchtrenk"));
        wels.addTermin(new Termin("2018-01-24 18:00:00", "2018-01-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-02-02 18:00:00", "2018-02-02 21:00:00", "Faschingsumzug", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));

        Person tom = new Person("00001", "passme", "Tom", "Tester", ooe);
        Person karin = new Person("00002", "passme", "Karin", "Tester", wels);
        Person gusi = new Person("00003", "passme", "Gusi", "Tester", sattledt);
        Person doris = new Person("00004", "passme", "Doris", "Tester", sattledt1);
        Person isabella = new Person("00005", "passme", "Isabella", "Tester", sattledt1);
        Person antonia = new Person("00006", "passme", "Antonia", "Tester", marchtrenk);
        Person melanie = new Person("00007", "passme", "Melanie", "Tester", marchtrenk1);
        repo.insert(tom);
        repo.insert(karin);
        repo.insert(gusi);
        repo.insert(doris);
        repo.insert(isabella);
        repo.insert(antonia);
        repo.insert(melanie);

        return "Testvalues inserted";
    }

    /**
     * Login Function to authenticate
     *
     * @param user
     * @return
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public int login(Person user) {
//        String cookie = "12345";//repo.login(user);
//        return Response.ok().header("Set-Cookie", "kalendarCookie=" + cookie).build();
        return repo.login(user);
    }

    /**
     * Lists all Termine
     *
     * @return
     */
    @GET
    @Path("listAllTermine")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Termin> listallTermine() {
        return repo.termine();
    }

    /**
     * Lists all Persons
     *
     * @return
     */
    @GET
    @Path("listAllPersons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> listAllPersons() {
        return repo.listAllUsers();
    }

    /**
     * Lists all JRKENTITYS
     *
     * @return
     */
    @GET
    @Path("listAllJRKEntitaeten")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JRKEntitaet> listAllJRKEntitaeten() {
        return repo.listAllJRK();
    }

    /**
     * Gets Users Termine/Appointments
     *
     * @param id
     * @return
     */
    @POST
    @Path("getUserTermine")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Termin> getUserTermine(int id) {
        return repo.getUsertermine(id);
    }

    /**
     * Gets Username
     *
     * @param id
     * @return
     */
    @POST
    @Path("getName")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getUsername(int id) {
        return repo.username(id);
    }

    /**
     * Get JRKEnitaet
     *
     * @param id id der Entität
     * @return
     */
    @POST
    @Path("getJRKEntitaet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public JRKEntitaet getJRKEntitaet(int id) {
        return repo.getJRKEntitaet(id);
    }

    /**
     * Inserts a Termin/Appointment
     *
     * @param id
     * @param t
     */
    @Path("insertTermin/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insertTermin(@PathParam("id") int id, Termin t) {
        repo.insertTermin(id, t);
    }

    /**
     * insert Dokumentation
     *
     * @param d Termin
     */
    @Path("insertDoko")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insertDoko(Termin d) {
        repo.insertDoko(d);
    }

    /**
     * Darf dieser User Termine und Protokolle einfügen
     *
     * @param id
     * @return
     */
    @Path("isEditor")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public boolean isEditor(int id) {
        return repo.isEditor(id);
    }

    /**
     * Noch nicht dokumentierte Termine zurückgeben
     *
     * @param id
     * @return
     */
    @Path("getOpenDoko")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public List<Termin> getOpenDoko(int id) {
        return repo.getOpenDoko(id);
    }

    @POST
    @Path("getChartValues")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<NameValue> getChartValues(int id) {
        return repo.getChartValues(id);
    }
    
    @POST
    @Path("getLowerEntityHourList")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<NameValue> getLowerEntityHourList(int id) {
        return repo.getLowerEntityHourList(id);
    }

    @POST
    @Path("getTimelineValues")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<NameValue> getTimelinesValues(int id) {
        return repo.getTimelineValues(id);
    }

    @POST
    @Path("getYearlyHoursPerPeople")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<NameValue> getYearlyHoursPerPeople(int id) {
        return repo.getYearlyHoursPerPeople(id);
    }
    
    @Path("getJRKEntitaetdown")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public List<JRKEntitaet> getJRKEntitaetdown(int id) {
        return repo.getJRKEntitaetdown(id);
    }
}
