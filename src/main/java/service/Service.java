package service;

import entities.Person;
import entities.JRKEntität;
import entities.Termin;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import repository.DatenbankRepository;

/**
 *
 * @author INFI-Projektgruppe
 */
@Path("service")
public class Service {

    private DatenbankRepository repo = new DatenbankRepository();

    // Nur zum Testen
    @GET
    @Path("message")
    public String message() {
        return "INFI Jugendrotkreuz Server up and running..";
    }

    /**
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

    @GET
    @Path("termine")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Termin> getUserTermine() {
        return repo.termine();
    }

    @GET
    @Path("listAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> listAll() {
        return repo.listAll();
    }

    @POST
    @Path("termineuser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public List<Termin> getUserTermine(int id) {
        return repo.termine(id);
    }

    @POST
    @Path("username")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String username(int id) {
        return repo.username(id);
    }

    // Initialize data table
    @Path("init")
    @GET
    public String init() {
        JRKEntität sattledt = new JRKEntität("Sattledt");
        repo.insert(sattledt);
        JRKEntität marchtrenk = new JRKEntität("Marchtrenk");
        repo.insert(marchtrenk);
        JRKEntität eferding = new JRKEntität("Eferding");
        repo.insert(eferding);
        JRKEntität wels = new JRKEntität("Bezirksstelle Wels");
        repo.insert(wels);
        JRKEntität ooe = new JRKEntität("Landesleitung Oberösterreich");
        repo.insert(ooe);
//        Person tom = new Person("Tom", "passme", null, ooe);
//        Person karin = new Person("Karin", "passme", tom, wels);
//        Person doris = new Person("Doris", "passme", karin, sattledt);
//        Person isabella = new Person("Isabella", "passme", doris, sattledt);
//        Person lina = new Person("Lina", "passme", karin, marchtrenk);
//        Person franz = new Person("Franz", "passme", tom, eferding);
//        Person lisa = new Person("Lisa", "passme", franz, eferding);
//        repo.insert(tom);
//        repo.insert(karin);
//        repo.insert(doris);
//        repo.insert(lina);
//        repo.insert(franz);
//        repo.insert(lisa);
//        repo.insert(isabella);
//
//        repo.insert(new Termin("2017-11-04 15:30:00", "2017-11-04 17:30:00", "Gruppenstunde", doris, "Gruppenstunde mit Schwerpunkt Erste-Hilfe","Dienststelle Sattledt"));
//        repo.insert(new Termin("2017-11-03 15:30:00", "2017-11-03 17:30:00", "Gruppenstunde", lina, "Gruppenstunde mit Schwerpunkt Erste-Hilfe","Dienststelle Marchtrenk"));
//        repo.insert(new Termin("2017-11-25 15:30:00", "2017-11-26 10:00:00", "Dienststellen Übernachtung", lisa, "Gruppenstunde mit Schwerpunkt Erste-Hilfe","Dienststelle Eferding"));
//        repo.insert(new Termin("2017-11-24 18:00:00", "2017-11-24 21:00:00", "Grillerei", karin, "Grillerei für alle Dienststellen des Bezirkes","Dienststelle Marchtrenk"));
//        repo.insert(new Termin("2017-12-02 18:00:00", "2017-12-02 21:00:00", "Adventmarkt", tom, "Punschstand für den guten Zweck","Adventmarkt Linz"));

        return "Testvalues inserted";
    }

//    @POST
//    @Path("addTermin")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void addTermin(Termin termin, Person user) {
//        user.addTermin(termin);
//    }
//     @POST
//     @Path("removeTermin")
//      public void removeTermin(Termin termin, Person user) {
//        user.removeTermin(termin);
//    }
    @POST
    @Path("addBenutzer")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBenutzer(Person user) {
        repo.addBenutzer(user);
    }
    // insert one new messung

    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insert(Person b) {
        repo.insert(b);
    }

    @Path("insertMore")
    @POST
    public void insertMore(List<Person> b) {
        repo.insert(b);
    }
}
