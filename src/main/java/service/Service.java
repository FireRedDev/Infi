package service;

import entities.Person;
import entities.JRKEntitaet;
import entities.Termin;
import entities.Typ;
import static entities.Typ.Bezirkstelle;
import static entities.Typ.Gruppe;
import static entities.Typ.Landstelle;
import static entities.Typ.Ortstelle;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import repository.DatenbankRepository;

/**
 *
 * @author INFI-Projektgruppe http://localhost:8080/api/service/message
 */
@Path("service")
public class Service {

    private DatenbankRepository repo = new DatenbankRepository();

    /**
     * Servertestfunction
     *
     * @return
     */
    @GET
    @Path("message")
    public String message() {
        return "INFI Jugendrotkreuz Server up and running..";
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

    //funktioniert nicht
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

        List<JRKEntitaet> landesleitung = new LinkedList<>();
        landesleitung.add(ooe);
        List<JRKEntitaet> bezirksleitung_wels = new LinkedList<>();
        bezirksleitung_wels.add(sattledt);
        List<JRKEntitaet> ortstelle_sattledt = new LinkedList<>();
        ortstelle_sattledt.add(sattledt1);
        sattledt1.addTermin(new Termin("2017-11-04 15:30:00", "2017-11-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt", sattledt1));
        wels.addTermin(new Termin("2017-11-24 18:00:00", "2017-11-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk", wels));
        ooe.addTermin(new Termin("2017-12-02 18:00:00", "2017-12-02 21:00:00", "Adventmarkt", "Punschstand für den guten Zweck", "Adventmarkt Linz", ooe));
        Person tom = new Person("00001", "passme", "Tom", "Tester", ooe, landesleitung);
        Person karin = new Person("00002", "passme", "Karin", "Tester", wels, bezirksleitung_wels);
        Person gusi = new Person("00003", "passme", "Gusi", "Tester", sattledt, ortstelle_sattledt);
        Person doris = new Person("00004", "passme", "Doris", "Tester", sattledt1, ortstelle_sattledt);
        Person isabella = new Person("00004", "passme", "Isabella", "Tester", sattledt1);
        repo.insert(tom);
        repo.insert(karin);
        repo.insert(gusi);
        repo.insert(doris);
        repo.insert(isabella);

        return "Testvalues inserted";
    }

    /**
     * Inserts a Termin/Appointment
     *
     * @param t
     */
    @Path("insertTermin")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insertTermin(Termin t) {
        repo.insertTermin(t);
    }

}
