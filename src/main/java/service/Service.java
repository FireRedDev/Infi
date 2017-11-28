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
    @Path("listAllTermine")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Termin> listallTermine() {
        return repo.termine();
    }

    @GET
    @Path("listAllPersons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> listAll() {
        return repo.listAll();
    }

    @GET
    @Path("listAllJRKEntitaeten")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JRKEntitaet> listAllJRKEntitaeten() {
        return repo.listAllJRK();
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
        Typ gruppe = Gruppe;
        Typ ortstelle = Ortstelle;
        Typ bezirkstelle = Bezirkstelle;
        Typ landesstelle = Landstelle;
        JRKEntitaet sattledt = new JRKEntitaet("Sattledt", ortstelle);
        repo.insert(sattledt);
        JRKEntitaet sattledt1 = new JRKEntitaet("Gruppe1", gruppe);
        repo.insert(sattledt1);
        JRKEntitaet marchtrenk = new JRKEntitaet("Marchtrenk", ortstelle);
        repo.insert(marchtrenk);
        JRKEntitaet eferding = new JRKEntitaet("Eferding", bezirkstelle);
        repo.insert(eferding);
        JRKEntitaet wels = new JRKEntitaet("Wels", bezirkstelle);
        repo.insert(wels);
        JRKEntitaet ooe = new JRKEntitaet("Oberösterreich", landesstelle);
        repo.insert(ooe);
        List<JRKEntitaet> landesleitung = new LinkedList<>();
        landesleitung.add(wels);
        repo.insert(landesleitung);
        List<JRKEntitaet> bezirksleitung_wels = new LinkedList<>();
        bezirksleitung_wels.add(sattledt);
        bezirksleitung_wels.add(marchtrenk);
        repo.insert(bezirksleitung_wels);
        List<JRKEntitaet> ortstelle_sattledt = new LinkedList<>();
        ortstelle_sattledt.add(sattledt1);
        repo.insert(ortstelle_sattledt);
        Person tom = new Person("00001", "passme", "Tom", "Tom", ooe, landesleitung);
        Person karin = new Person("00002", "passme", "Karin", "Karin", wels, bezirksleitung_wels);
        Person gusi = new Person("00003", "passme", "Gusi", "Gusi", sattledt, ortstelle_sattledt);
        Person doris = new Person("00004", "passme", "Doris", "Doris", sattledt1, ortstelle_sattledt);
        Person isabella = new Person("00004", "passme", "Isabella", "Isabella", sattledt1);
        repo.insert(tom);
        repo.insert(karin);
        repo.insert(gusi);
        repo.insert(doris);
        repo.insert(isabella);
wels.addTermin(new Termin("2017-11-04 15:30:00", "2017-11-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt", wels));
//        repo.insert(new Termin("2017-11-04 15:30:00", "2017-11-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt", sattledt1));
//        repo.insert(new Termin("2017-11-24 18:00:00", "2017-11-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk", wels));
//        repo.insert(new Termin("2017-12-02 18:00:00", "2017-12-02 21:00:00", "Adventmarkt", "Punschstand für den guten Zweck", "Adventmarkt Linz", ooe));
        repo.insert(wels);

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

}
