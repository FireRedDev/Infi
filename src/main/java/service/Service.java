package service;

import entities.Benutzer;
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
    public Response login(Benutzer user) {
        String cookie = "12345";//repo.login(user);
       return Response.ok().header("Set-Cookie","kalendarCookie="+cookie).build();
    }
    
    @GET
    @Secured
    @Path("getUserTermine")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Termin> getUserTermine(){
       return null; 
       // return repo.getUserTermine(user);
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
}
