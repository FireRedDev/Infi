package service;

import RestResponseClasses.*;
import entities.*;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import repository.DatenbankRepository;
import RestResponseClasses.PersonTokenTransferObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.ws.rs.core.Context;

/**
 * REST-Request Service
 *
 * @author INFI-Projektgruppe http://localhost:8080/api/service/message
 */
@Path("service")
public class Service {

    private DatenbankRepository repo = new DatenbankRepository();
    public static Boolean firstToken = false;

    /**
     * Message
     *
     * to test the server
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
     * delete a Person
     *
     * only Landesleiter and Bezirksleiter have the permission to delete Persons
     *
     * @param id id of a Person
     * @return Persons
     */
    @POST
    @Secured({Role.LANDESLEITER, Role.BEZIRKSLEITER})
    @Path("deletePerson")
    public List<Person> deletePerson(int id) {
        return repo.deletePerson(id);
    }

    /**
     * Login to a Server with Username/Password and get a Token
     *
     * @param pto
     * @return Token
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("login")
    public PersonTokenTransferObject login(PersonTransferObject pto) {
        return repo.login(pto);
    }

    /**
     * Lists all Persons/Users hierachie down
     *
     * @param id id of a person
     * @return list
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
     * Lists all Persons/Users hierachie down
     *
     * @param id id of a JRKEntity
     * @return list
     */
    @POST
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Path("getUsersLayerDownJRK")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Person> getUsersLayerDownJRK(int id) {
        return repo.getUsersLayerDownJRK(id);
    }

    /**
     * Lists all JRKENTITYIES
     *
     * @return JRK-Entity List
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
     * @param id Person id
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
     * @param id Person id
     * @return Username
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
     * @return JRK-Entity
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
     * Inserts a Termin/Appointment and assigns it to a JRKEntitaet
     *
     * @param id
     * @param t
     */
    @Path("changeTermin")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public void changeTermin(Termin t) {
        repo.changeTermin(t);
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

    @Path("getTerminTeilnehmer")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String getTerminTeilnehmer(int id) {
        return "\""+repo.getTerminTeilnehmer(id)+"\"";
    }

    /**
     * give back all roles
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
     * @param id
     * @param b
     */
    @Path("insertPerson/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insertPerson(@PathParam("id") int id, Person b) {
        repo.insert(id, b);
    }

    /**
     * is this user a editor?
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
     * is this user a admin?
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
     * is this user a admin?
     *
     * @param id
     * @return
     */
    @Path("isGruppenleiter")
    @Secured({Role.BEZIRKSLEITER, Role.KIND, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public boolean isGruppenleiter(int id) {
        return repo.isGruppenleiter(id);
    }

    /**
     * give back none documented appointments
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
     * give back none planed appointments
     *
     * @param id
     * @return
     */
    @Path("getOpenPlanning")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public List<Termin> getOpenPlanning(int id) {
        return repo.getOpenPlanning(id);
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

      @POST
    @Path("getPersonenstunden")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<NameValue> getPersonenstunden(JRKEntitaet jrk) {
        return repo.getPersonenstunden(jrk);
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
     * Give back the values for the timeline
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
     * Gives back the JRK-Entities in the Layers down
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
     * change Password
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
     * Does this User need to change his password?
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
     * save Person
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

    /**
     * Inserts a Info and assigns it to a JRKEntitaet
     *
     * @param id
     * @param i
     */
    @Path("insertInfo/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insertInfo(@PathParam("id") int id, Info i) {
        System.out.println("here");
        repo.insertInfo(id, i);
    }

    @Path("changeInfo")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public void changeInfo(Info i) {
        repo.changeInfo(i);
    }

    @Path("insertPlanung/{id}")
    //@Secured(Role.GRUPPENLEITER)
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String insertPlanung(@PathParam("id") int id, String text) {
        repo.insertPlanung(id,text);
        return "\"inserted\"";
    }

    @Path("changePlanung")
    //@Secured(Role.GRUPPENLEITER)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String changePlanung(Planning planning) {
        repo.changePlanung(planning);
        return "changed";
    }

    @GET
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Path("getProtokollDetails/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.TEXT_PLAIN)
    public List<Termin> getProtokollDetails(@PathParam("id") int id) {
        return repo.getProtokollDetails(id);
    }

    @Path("saveFCMToken/{id}")
    @Secured({Role.KIND, Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public String saveFCMToken(@PathParam("id") int id, String token) {
        return "\"" + repo.setFCMToken(id, token) + "\"";
    }

    @Path("registerAttendee/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.KIND})
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public void registerAttendee(@PathParam("id") int terminID, int userID, @Context MySecurityContext sc) {
        repo.registerAttendee(terminID, userID);
    }

    @Path("removeAttendee/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.KIND})
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void removeAttendee(@PathParam("id") int terminID, int userID, @Context MySecurityContext sc) {
        repo.removeAttendee(terminID, userID);
    }

    @Path("getNextIncomingAppointment/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.KIND})
    @POST
    public Termin getNextIncomingAppointment(@PathParam("id") int id) {
        List<Termin> liste = repo.getUsertermine(id);
        String date = LocalDateTime.now().toString();

        return liste.stream().filter(t -> t.getS_date().compareTo(date) > 0).sorted((l, r) -> l.getS_date().compareTo(r.getS_date())).findFirst().orElse(null);
    }

    /**
     * Gives back the Supervisors
     *
     * @param id
     * @return
     */
    @POST
    @Path("getSupervisors")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.GRUPPENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Person> getSupervisor(int id) {
        return repo.getSupervisor(id);
    }

    /**
     * Gives back the Children
     *
     * @param id
     * @return
     */
    @POST
    @Path("getChildren")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.GRUPPENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Person> getChildren(int id) {
        return repo.getChildren(id);
    }

    @Path("deleteTermin")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String deleteTermin(Termin t) {
        return "\"" + repo.deleteTermin(t) + "\"";
    }

    @Path("deleteInfo")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String deleteInfo(Info i) {
        return "\"" + repo.deleteInfo(i) + "\"";
    }

    @Path("sharePlanning/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String sharePlanning(@PathParam("id") int id) {
        return "\"" + repo.sharePlanning(id) + "\"";
    }

    @Path("sharedPlanning")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public List<Termin> sharedPlanning() {
        List<Termin> t = repo.sharedPlanning();
        if(t.isEmpty()){
            Termin t1 = new Termin("kein", "ergebnis", "gefunden", "", "");
            Planning pl = new Planning("Leer");
            t1.setPlanning(pl);
            t.add(t1);
        }
        return t;
    }

    @Path("deletePlanning")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String deletePlanning(Planning p) {
        return "\"" + repo.deletePlanning(p) + "\"";
    }
}
