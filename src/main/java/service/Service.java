package service;

import RestResponseClasses.NameValue;
import RestResponseClasses.PersonTransferObject;
import entities.*;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import repository.DatenbankRepository;
import RestResponseClasses.PersonTokenTransferObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

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
    
    @POST
    @Secured({Role.LANDESLEITER, Role.BEZIRKSLEITER})
    @Path("deletePerson")
    public List<Person> deletePerson(int id) {
        return (repo.listAllNeu(id));
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
     * Lists all Persons/Users
     *
     * @param id
     * @return
     */
    @POST
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Path("getUsersLayerDown")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Person> getUsersLayerDown(int id) {
        return repo.getUsersLayerDown(id);
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
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
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
     * Gibt alle Rollen zurück.
     *
     * @return Rollen
     */
    @GET
    @Path("getAllRoles")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    public Role[] getAllRoles() {
        return repo.getAllRoles();
    }
    
        /**
     * insert Dokumentation and create Relationship with its Termin
     *
     * @param b
     */
    @Path("insertPerson")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insertPerson(Person b) {
        repo.insert(b);
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
     * Darf dieser User Benutzer verwalten?
     *
     * @param id
     * @return
     */
    @Path("isAdmin")
    @Secured({Role.BEZIRKSLEITER, Role.KIND, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public boolean isAdmin(int id) {
        return repo.isAdmin(id);
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
     * @param jrk
     * @return
     */
    @POST
    @Path("getChartValues")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<NameValue> getChartValues(JRKEntitaet jrk) {
        return repo.getChartValues(jrk);
    }

    /**
     * Anzahl von den Stunden pro Monat im letzten Jahr
     *
     * @param jrk
     * @return
     */
    @POST
    @Path("getLowerEntityHourList")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<NameValue> getLowerEntityHourList(JRKEntitaet jrk) {
        return repo.getLowerEntityHourList(jrk);
    }

    /**
     * 
     * @param jrk
     * @return 
     */
    @POST
    @Path("getTimelineValues")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<NameValue> getTimelinesValues(JRKEntitaet jrk) {
        return repo.getTimelineValues(jrk);
    }

    /**
     * get Anzahl der Stunden pro Persongruppe
     *
     * @param jrk
     * @return
     */
    @POST
    @Path("getYearlyHoursPerPeople")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<NameValue> getYearlyHoursPerPeople(JRKEntitaet jrk) {
        return repo.getYearlyHoursPerPeople(jrk);
    }

    /**
     * 
     * @param id
     * @return 
     */
    @POST
    @Path("getJRKEntitaetdown")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<JRKEntitaet> getJRKEntitaetdown(int id) {
        return repo.getJRKEntitaetdown(id);
    }

    /**
     * 
     * @param p
     * @return 
     */
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

    /**
     * 
     * @param id
     * @return 
     */
    @POST
    @Path("needPwdChange")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.KIND, Role.GRUPPENLEITER})
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean needPwdChange(int id) {
        return repo.needPwdChange(id);
    }
    
    /**
     *
     * @param p
     * @return
     */
    @POST
    @Path("savePerson")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String savePerson(Person p) {
        repo.savePerson(p);
        return "\"Person geändert\"";
    }

     * Inserts a Info and assigns it to a JRKEntitaet
     *
     * @param id
     * @param t
     */
    @Path("insertInfo/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insertInfo(@PathParam("id") int id, Info i) {
        repo.insertInfo(id, i);
    }
}
