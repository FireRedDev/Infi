package service;

import RestResponseClasses.*;
import entities.*;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import RestResponseClasses.PersonTokenTransferObject;
import java.time.LocalDateTime;
import javax.ws.rs.core.Context;
import repository.Repository;

/**
 * REST-Request Service offering Authentication Methods, DB Object Managment and
 * statistical Information
 *
 * @author INFI-Projektgruppe, Test-Url:
 * http://localhost:8080/api/service/message
 */
@Path("service")
public class Service {

    private Repository repo = new Repository();

    /**
     *
     */
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

    @Path("getDokuById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Termin getDokoById(@PathParam("id") int id) {
        System.out.println("Looking for id: " + id);
        return repo.getDokoById(id);
    }

    /**
     * delete a Person
     *
     * only Landesleiter and Bezirksleiter have the permission to delete Persons
     *
     * @param id ID of the Deleted Person
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
     * @param pto JSON Containing an Email and a Password String
     * @return JSON Containing USERID and JWT Token
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("login")
    public PersonTokenTransferObject login(PersonTransferObject pto) {
        return repo.login(pto);
    }

    /**
     * Lists all Persons/Users hierachy down
     *
     * @param id id of a person
     * @return List of all subordinate users
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
     * @return List of all subordinate users
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
     * @return returns all JRK-Entities
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
     * @return an users appointments
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
     * @return List of All Infos that belong to a user/that he is permitted to
     * see
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
     * @return Username of user
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
     * @param t Appointment
     */
    @Path("insertTermin/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insertTermin(@PathParam("id") int id, Termin t) {
        repo.insertTermin(id, t);
    }

    /**
     * Updates a Termin/Appointment and assigns it to a JRKEntitaet
     *
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
     * Insert Dokumentation and create Relationship with its Termin
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
     * Get the Attendees of an Appointment
     *
     * @param id
     * @return String List of Attendees(Hans Müller; Fred Fauler;...)
     */
    @Path("getTerminTeilnehmer")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String getTerminTeilnehmer(int id) {
        return "\"" + repo.getTerminTeilnehmer(id) + "\"";
    }

    /**
     * give back all roles
     *
     * @return Roles
     */
    @GET
    @Path("getAllRoles")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.KIND, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    public Role[] getAllRoles() {
        return repo.getAllRoles();
    }

    /**
     * insert Person and create Relationship with its JRKEntitaet
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
     * Is this user a editor?
     *
     * @param id
     * @return Yes/No Boolean
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
     * is this user an admin?
     *
     * @param id
     * @return Yes/No Boolean
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
     * is this user a Gruppenleiter?
     *
     * @param id
     * @return Yes/No Boolean
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
     * Returns undocumented appointments
     *
     * @param id
     * @return List of undocumented Appointments
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
     * Returns unplaned appointments
     *
     * @param id
     * @return List of unplaned appointments
     */
    @Path("getOpenPlanning/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public List<Termin> getOpenPlanning(@PathParam("id") int id) {
        return repo.getOpenPlanning(id);
    }

    /**
     * Returns the Frequency of a category(how often is it used as an
     * appointment category?)
     *
     * @param jrk
     * @return JSON that looks like: "Exkursion", "57"
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
     * Returns an individuals spent Hours for statistics
     *
     * @param jrk
     * @return returns List that contains a Name "Franz Müller" and his overall
     * Hours "23"
     */
    @POST
    @Path("getPersonenstunden")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<NameValue> getPersonenstunden(JRKEntitaet jrk) {
        return repo.getPersonenstunden(jrk);
    }

    /**
     * Returns a ValuePair List for each subordinate JRK Entitys Time Spent for
     * statistical Purposes
     *
     * @param jrk
     * @return JRKEntity Hours List (Jrkentityname and hours)
     */
    @POST
    @Path("getLowerEntityHourList")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<NameValue> getLowerEntityHourList(JRKEntitaet jrk) {
        return repo.getLowerEntityHourList(jrk);
    }

    /**
     * Returns a ValuePair for the Monthly Hours of a Group
     *
     * @param jrk
     * @return List with contents Month, Hours
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
     * Returns a List Showing how the time in the year was divided up
     *
     * @param jrk
     * @return List of Betruers, Childrens and Preparation Time spent
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
     * @return Jrkentities
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
     * Change Password
     *
     * @param p
     * @return Passwort geändert String if successfull
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
     * @return Yes/No Boolean
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
     * Save Person in DB
     *
     * @param p
     * @return Person geändert String if succesfull
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
        repo.insertInfo(id, i);
    }

    /**
     * Update Info
     *
     * @param i
     */
    @Path("changeInfo")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public void changeInfo(Info i) {
        repo.changeInfo(i);
    }

    /**
     * Persist Planning and add relationship to its termin
     *
     * @param id
     * @param p
     * @return
     */
    @Path("insertPlanung/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String insertPlanung(@PathParam("id") int id, Planning p) {
        repo.insertPlanung(id, p);
        return "\"inserted\"";
    }

    /**
     * updates planning
     *
     * @param planning
     * @return
     */
    @Path("changePlanung")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String changePlanung(Planning planning) {
        repo.changePlanung(planning);
        return "changed";
    }

    /**
     * returns jrk entitys appointments and its subordinate appointments
     *
     * @param id
     * @return appointments
     */
    @GET
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Path("getProtokollDetails/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.TEXT_PLAIN)
    public List<Termin> getProtokollDetails(@PathParam("id") int id) {
        return repo.getProtokollDetails(id);
    }

    /**
     * saves fcm token
     *
     * @param id
     * @param token
     * @return
     */
    @Path("saveFCMToken/{id}")
    @Secured({Role.KIND, Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public String saveFCMToken(@PathParam("id") int id, String token) {
        return "\"" + repo.setFCMToken(id, token) + "\"";
    }

    /**
     * registers Attendee as attendee to an appointment
     *
     * @param terminID
     * @param userID
     */
    @Path("registerAttendee/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.KIND})
    @Consumes(MediaType.TEXT_PLAIN)
    @POST
    public void registerAttendee(@PathParam("id") int terminID, int userID) {
        repo.registerAttendee(terminID, userID);
    }

    /**
     * Removes person as attendee from an appointment
     *
     * @param terminID
     * @param userID
     */
    @Path("removeAttendee/{id}")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.KIND})
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void removeAttendee(@PathParam("id") int terminID, int userID) {
        repo.removeAttendee(terminID, userID);
    }

    /**
     * Gets the NEXT Appointment of an user(with his id)
     *
     * @param id
     * @return NEXT Appointment of an user
     */
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
     * Gives back the Supervisors of an user
     *
     * @param id
     * @return supervisor Persons
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
     * Gives back the Children of an user
     *
     * @param id
     * @return Users(Gruppenleiters) Children
     */
    @POST
    @Path("getChildren")
    @Secured({Role.BEZIRKSLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER, Role.GRUPPENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Person> getChildren(int id) {
        return repo.getChildren(id);
    }

    /**
     * deletes termin
     *
     * @param t
     * @return
     */
    @Path("deleteTermin")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String deleteTermin(Termin t) {
        return "\"" + repo.deleteTermin(t) + "\"";
    }

    /**
     * delete Infos
     *
     * @param i
     * @return
     */
    @Path("deleteInfo")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String deleteInfo(Info i, @PathParam("id") int id) {
        return "\"" + repo.deleteInfo(i, id) + "\"";
    }

    /**
     * returns all appointments with shared plannings
     *
     * @return appointments where shared is set to true
     */
    @Path("sharedPlanning")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public List<Termin> sharedPlanning() {
        List<Termin> t = repo.sharedPlanning();
        return t;
    }

    /**
     * deletes planning
     *
     * @param p
     * @return
     */
    @Path("deletePlanning")
    @Secured({Role.BEZIRKSLEITER, Role.GRUPPENLEITER, Role.LANDESLEITER, Role.ORTSTELLENLEITER})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public String deletePlanning(Planning p) {
        return "\"" + repo.deletePlanning(p) + "\"";
    }
}
