package service;

import entities.Benutzer;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import repository.DatenbankRepository;

/**
 *
 * @author INFI-Projektgruppe
 */
@Path("service")
public class Service {

    private DatenbankRepository repo = DatenbankRepository.getInstance();

    // Nur zum Testen
    @GET
    @Path("message")
    public String message() {
        return "INFI Jugendrotkreuz Server up and running..";
    }

    @GET
    @Path("init")
    public String init() {
        repo.init();
        return "init";
    }

    /**
     *
     * @param user
     * @return
     */
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean login(Benutzer user) {
        return repo.login(user);
    }
}
