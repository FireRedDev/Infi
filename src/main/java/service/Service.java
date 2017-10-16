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
 * @author H. Lackinger
 */
@Path("service")
public class Service {

    Repository repo = Repository.getInstance();

    // Nur zum Testen
    @GET
    @Path("message")
    public String message() {
        return " Hello REST Service powered by Java SE and Jersey ";
    }

    // Lesen eines Reminders
    @GET
    @Path("find/{key}")
    public String find(@PathParam("key") String key) {
        return repo.find(key);
    }
// Frage: Warum geht Regex nicht [0-9] [+-/\*]
    @POST
    @Path("calculate")
    public Double calculate(String mathexpression){
        double a;
        double b;
        if(mathexpression.contains("+")){
            int split = mathexpression.indexOf("+");
            a = Double.parseDouble(mathexpression.substring(0, split));
            b = Double.parseDouble(mathexpression.substring(split+1));
            
            return a+b;
            
        }
        
        else if(mathexpression.contains("-")){
            int split = mathexpression.indexOf("-");
            a = Double.parseDouble(mathexpression.substring(0, split));
            b = Double.parseDouble(mathexpression.substring(split+1));
            
            return a-b;
            
        }
           else if(mathexpression.contains("^")){
            int split = mathexpression.indexOf("^");
            a = Double.parseDouble(mathexpression.substring(0, split));
            b = Double.parseDouble(mathexpression.substring(split+1));
            
            return Math.pow(a, b);
            
        }
        else if(mathexpression.contains("*")){
            int split = mathexpression.indexOf("*");
            a = Double.parseDouble(mathexpression.substring(0, split));
            b = Double.parseDouble(mathexpression.substring(split+1));
            
            return a*b;
            
        }
        else if(mathexpression.contains(":")){
            int split = mathexpression.indexOf(":");
            a = Double.parseDouble(mathexpression.substring(0, split));
            b = Double.parseDouble(mathexpression.substring(split+1));
            
            return a/b;
            
        }
        else if(mathexpression.contains("/")){
            int split = mathexpression.indexOf("/");
            a = Double.parseDouble(mathexpression.substring(0, split));
            b = Double.parseDouble(mathexpression.substring(split+1));
            
            return a/b;
            
        }
     
        else{
            return -999999999.999999999;
        } }
    // Lesen ALLER Reminder

    @GET
    @Path("findAll")
    public List<String> findAll() {
        return repo.findAll();
    }

    // Schreiben der Collection auf ein File
    @GET
    @Path("save")
    public void save() {
        repo.save();
    }

    // Einfügen eines Reminders
    @POST
    @Path("insert/{key}")
    public String insert(@PathParam("key") String key, String value) {
        return repo.insert(key, value);
    }

    // Ändern eines Reminders
    @PUT
    @Path("update/{key}")
    public String update(@PathParam("key") String key) {
        return repo.update(key);
    }

    // Löschen eines Reminders
    @DELETE
    @Path("delete/{key}")
    public String delete(@PathParam("key") String key) {
        return repo.delete(key);
    }

    // Löschen ALLER Reminder
    @DELETE
    @Path("delete")
    public List<String> deleteAll() {
        return repo.deleteAll();
    }

}
