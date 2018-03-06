package service;

import RestResponseClasses.NameValue;
import repository.PersonTransferObject;
import entities.*;
import static entities.Typ.*;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import repository.DatenbankRepository;
import repository.PersonTokenTransferObject;

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
        System.out.println("messagefunction");
        return "INFI Jugendrotkreuz Server up and running..";
    }

    /**
     * Inserts and creates Test Values
     *
     * @return
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("login")
    public PersonTokenTransferObject login(PersonTransferObject pto) {
        return repo.login(pto);
    }

    /**
     * Lists all Termine
     *
     * @return
     */
    @GET
    @Path("listAllTermine")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    public List<Termin> listallTermine() {
        return repo.termine();
    }

    /**
     * Testinitfunction Löschen und ins programm integrieren
     */
    @GET
    @Path("init")
    public String init() {
        Typ gruppe = Gruppe;
        Typ ortstelle = Ortstelle;
        Typ bezirkstelle = Bezirkstelle;
        Typ landesstelle = Landstelle;
        JRKEntitaet ooe = new JRKEntitaet(5, "Oberösterreich", landesstelle, null);
        JRKEntitaet wels = new JRKEntitaet(4, "Wels", bezirkstelle, ooe);
        JRKEntitaet sattledt = new JRKEntitaet(1, "Sattledt", ortstelle, wels);
        JRKEntitaet sattledt1 = new JRKEntitaet(2, "Gruppe1_Sattledt", gruppe, sattledt);
        JRKEntitaet marchtrenk = new JRKEntitaet(3, "Marchtrenk", ortstelle, wels);
        JRKEntitaet marchtrenk1 = new JRKEntitaet(6, "Gruppe1_Marchtrenk", gruppe, marchtrenk);

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

        Termin weterm = new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        String[] betreuerwe = {"Gusi", "Isi"};
        String[] kinderwe = {"Meli", "Antonia", "Luki"};
        weterm.setDoko(new Dokumentation(kinderwe, betreuerwe, "basteln", 2.0, "Soziales"));

        sattledt.addTermin(weterm);
        marchtrenk1.addTermin(new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Eislaufen", "Bitte Eislaufschuhe, Winterkleidung und 3€ Eintritt mitnehmen", "Eislaufplatz Marchtrenk"));
        wels.addTermin(new Termin("2018-01-24 18:00:00", "2018-01-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-02-02 18:00:00", "2018-02-02 21:00:00", "Faschingsumzug", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));

        sattledt1.addInfo(new Info("Terminfindung für Fotoshooting", "Bitte Abstimmen Doodle-Link", "assets/lager.jpg"));
        ooe.addInfo(new Info("Fotos", "fotos sind online oö", "assets/teambuilding.jpg"));
        wels.addInfo(new Info("Bezirkslager", "Bilder sind endlich auf Dropbox Link:", "assets/lager.jpg"));

        Person tom = new Person("00001", "passme", "Tom", "Tester", ooe, Role.LANDESLEITER);
        Person karin = new Person("00002", "passme", "Karin", "Tester", wels, Role.BEZIRKSLEITER);
        Person gusi = new Person("00003", "passme", "Gusi", "Tester", sattledt, Role.ORTSTELLENLEITER);
        Person doris = new Person("00004", "passme", "Doris", "Tester", sattledt1, Role.KIND);
        Person isabella = new Person("00005", "passme", "Isabella", "Tester", sattledt1, Role.KIND);
        Person antonia = new Person("00006", "passme", "Antonia", "Tester", marchtrenk, Role.GRUPPENLEITER);
        Person melanie = new Person("00007", "passme", "Melanie", "Tester", marchtrenk1, Role.KIND);
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
     * Lists all Persons
     *
     * @return
     */
    @GET
    @Secured({Role.LANDESLEITER})
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
    @Secured({Role.LANDESLEITER})
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
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Path("getUserTermine")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Termin> getUserTermine(int id) {
        return repo.getUsertermine(id);
    }

    /**
     * Gets Users Infos
     *
     * @param id
     * @return
     */
    @POST
    @Path("getUserInfos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Info> getUserInfos(int id) {
        return repo.getUserInfos(id);
    }

    /**
     * Gets Username
     *
     * @param id
     * @return
     */
    @POST

    @Path("getName")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getUsername(int id) {
        return "\"" + repo.username(id) + "\"";
    }

    /**
     * Get JRKEnitaet
     *
     * @param id id der Entität
     * @return
     */
    @POST
    @Path("getJRKEntitaet")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
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
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
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
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
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
    @Secured({Role.BEZIRKSLEITER, Role.KIND, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
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
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public List<Termin> getOpenDoko(int id) {
        return repo.getOpenDoko(id);
    }

    @POST
    @Path("getChartValues")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
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
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<NameValue> getTimelinesValues(int id) {
        return repo.getTimelineValues(id);
    }

    @POST
    @Path("getYearlyHoursPerPeople")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<NameValue> getYearlyHoursPerPeople(int id) {
        return repo.getYearlyHoursPerPeople(id);
    }

    @POST
    @Path("getJRKEntitaetdown")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<JRKEntitaet> getJRKEntitaetdown(int id) {
        return repo.getJRKEntitaetdown(id);
    }
    
    @POST
    @Path("changePassword")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER,Role.KIND,Role.GRUPPENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String changePassword(Person p) {
        repo.changePassword(p);
        //Hochkomma müssen manuell dazugegeben werden, sonst erkennt Angular den String nicht
        return "\"Passwort geändert\"";
    }
    
    @POST
    @Path("needPwdChange")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER,Role.KIND,Role.GRUPPENLEITER})
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean needPwdChange(int id) {
        return repo.needPwdChange(id);
    }

}
