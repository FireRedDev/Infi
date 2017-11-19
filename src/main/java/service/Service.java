package service;

import entities.Benutzer;
import entities.Ortsstelle;
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

//    @GET
//    @Path("init")
//    public String init() {
//        repo.init();
//        return "init";
//    }
    /**
     *
     * @param user
     * @return
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public int login(Benutzer user) {
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
    public List<Benutzer> listAll() {
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
        Ortsstelle sattledt = new Ortsstelle("Sattledt");
        repo.insert(sattledt);
        Ortsstelle marchtrenk = new Ortsstelle("Marchtrenk");
        repo.insert(marchtrenk);
        Ortsstelle eferding = new Ortsstelle("Eferding");
        repo.insert(eferding);
        Ortsstelle wels = new Ortsstelle("Bezirksstelle Wels");
        repo.insert(wels);
        Ortsstelle ooe = new Ortsstelle("Landesleitung Oberösterreich");
        repo.insert(ooe);
        Benutzer tom = new Benutzer("Tom", "passme", null, ooe);
        Benutzer karin = new Benutzer("Karin", "passme", tom, wels);
        Benutzer doris = new Benutzer("Doris", "passme", karin, sattledt);
        Benutzer lina = new Benutzer("Lina", "passme", karin, marchtrenk);
        Benutzer lisa = new Benutzer("Lisa", "passme", tom, eferding);
        repo.insert(tom);
        repo.insert(karin);
        repo.insert(doris);
        repo.insert(lina);
        repo.insert(lisa);

        repo.insert(new Termin("2017-11-04 15:30:00", "2017-11-04 17:30:00", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", doris));
        repo.insert(new Termin("2017-11-03 15:30:00", "2017-11-03 17:30:00", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", lina));
        repo.insert(new Termin("2017-11-25 15:30:00", "2017-11-26 10:00:00", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", lisa));
        repo.insert(new Termin("2017-11-24 18:00:00", "2017-11-24 21:00:00", "Grillerei für alle Dienststellen des Bezirkes", karin));
        repo.insert(new Termin("2017-12-02 18:00:00", "2017-12-02 21:00:00", "Punschstand für den guten Zweck", tom));

        return "Testvalues inserted";
    }

//    @POST
//    @Path("addTermin")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void addTermin(Termin termin, Benutzer user) {
//        user.addTermin(termin);
//    }
//     @POST
//     @Path("removeTermin")
//      public void removeTermin(Termin termin, Benutzer user) {
//        user.removeTermin(termin);
//    }
    @POST
    @Path("addBenutzer")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBenutzer(Benutzer user) {
        repo.addBenutzer(user);
    }
    // insert one new messung

    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void insert(Benutzer b) {
        repo.insert(b);
    }

    @Path("insertMore")
    @POST
    public void insertMore(List<Benutzer> b) {
        repo.insert(b);
    }
}
