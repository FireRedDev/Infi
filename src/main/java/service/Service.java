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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Benutzer login(Benutzer user) {
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
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Termin> getUserTermine(Benutzer user) {
        return repo.termine(user);
    }

    // Initialize data table
    @Path("init")
    @GET
    public String init() {
        Ortsstelle ortsstelle = new Ortsstelle("Sattledt");
        repo.insert(ortsstelle);
        Benutzer admin = new Benutzer("admin", "admin", ortsstelle);
        Benutzer test = new Benutzer("test", "test", ortsstelle);
        repo.insert(admin);
        repo.insert(test);
        repo.insert(new Termin("2017-11-09 11:00:00", "2017-11-09 13:00:00", "Gruppenstunde", admin));
        repo.insert(new Termin("2017-11-10 09:00:00", "2017-11-10 11:00:00", "Gruppenstunde", admin));
        repo.insert(new Termin("2017-11-15 11:00:00", "2017-11-20 20:00:00", "Gruppenstunde", admin));

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
