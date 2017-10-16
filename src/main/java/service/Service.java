package service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.*;

import repository.Repository;

/**
 *
 * @author INFI-Projektgruppe
 */
@Path("service")
public class Service {

    Repository repo = Repository.getInstance();

    // Nur zum Testen
    @GET
    @Path("message")
    public String message() {
        return "INFI Jugendrotkreuz Server up and running..";
    }

}
