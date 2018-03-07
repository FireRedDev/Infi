package service;

import RestResponseClasses.NameValue;
import RestResponseClasses.PersonTransferObject;
import entities.*;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import repository.DatenbankRepository;
import RestResponseClasses.PersonTokenTransferObject;

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
     * Login to Server with Username/Password and get a Token
     *
     * @param pto
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
     * Lists all Termine of all Users and Entitys
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
     * Lists all Persons/Users
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
     * Gets Users Termine/Appointments with his PersonalNR
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
     * Get a JRKENTITÄT with its ID
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
     * Inserts a Termin/Appointment and assigns it to a JRKEntitaet
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
     * insert Dokumentation and create Relationship with its Termin
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
     * Darf dieser User Termine und Protokolle einfügen?
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

    /**
     * Häufigkeit von Kategorie in einer JRKEntity
     *
     * @param id
     * @return
     */
    @POST
    @Path("getChartValues")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<NameValue> getChartValues(int id) {
        return repo.getChartValues(id);
    }

    /**
     * Anzahl von den Stunden pro Monat im letzten Jahr
     *
     * @param id
     * @return
     */
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

    /**
     * get Anzahl der Stunden pro Persongruppe
     *
     * @param id
     * @return
     */
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
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.KIND, Role.GRUPPENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String changePassword(Person p) {
        repo.changePassword(p);
        //Hochkomma müssen manuell dazugegeben werden, sonst erkennt Angular den String nicht
        return "\"Passwort geändert\"";
    }

    @POST
    @Path("needPwdChange")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.KIND, Role.GRUPPENLEITER})
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean needPwdChange(int id) {
        return repo.needPwdChange(id);
    }

}
