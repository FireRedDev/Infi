package service;

import entities.*;
import static entities.JRKEntitaetType.*;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import repository.EntityManagerSingleton;

/**
 * MAIN SERVER, INIT METHOD
 *
 * @author Christopher G
 */
public class HttpServer_JavaSE {

    /**
     *
     */
    public static final String BASE_URI = "http://localhost:8080/api/";
    private static final EntityManager em = EntityManagerSingleton.getInstance().getEm();

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Class.forName("com.fasterxml.jackson.annotation.JsonInclude");
        // Server starten
        final ResourceConfig rc = new ResourceConfig().packages("service");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("public"), "/");
        System.out.println(String.format("Server startet at %s\nHit enter to stop ...", BASE_URI));
        
        Logger l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);
        init();
        System.in.read();
        server.shutdown();
    }

    /**
     *
     * @param repo
     * @return
     */
    public static String init() {
        JRKEntitaetType gruppe = Gruppe;
        JRKEntitaetType ortstelle = Ortstelle;
        JRKEntitaetType bezirkstelle = Bezirkstelle;
        JRKEntitaetType landesstelle = Landstelle;
        JRKEntitaet ooe = new JRKEntitaet(5, "Oberösterreich", landesstelle, null);
        JRKEntitaet wels = new JRKEntitaet(4, "Wels", bezirkstelle, ooe);
        JRKEntitaet sattledt = new JRKEntitaet(1, "Sattledt", ortstelle, wels);
        JRKEntitaet sattledt1 = new JRKEntitaet(2, "Gruppe1", gruppe, sattledt);
        JRKEntitaet marchtrenk = new JRKEntitaet(3, "Sattledt", ortstelle, wels);
        JRKEntitaet marchtrenk1 = new JRKEntitaet(6, "Gruppe1", gruppe, marchtrenk);

        //Dokumentation
        Termin sattledttermin = new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        String[] betreuer = {"Gusi", "Isi"};
        String[] kinder = {"Meli", "Antonia", "Luki"};
        sattledttermin.setDoko(new Dokumentation(kinder, betreuer, "basteln", 2.0, "Soziales"));

        sattledt1.addTermin(sattledttermin);
        marchtrenk1.addTermin(new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Eislaufen", "Bitte Eislaufschuhe, Winterkleidung und 3€ Eintritt mitnehmen", "Eislaufplatz Marchtrenk"));
        wels.addTermin(new Termin("2018-01-24 18:00:00", "2018-01-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-02-02 18:00:00", "2018-02-02 21:00:00", "Faschingsumzug", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));

        Person tom = new Person("00001", "passme", "Tom", "Tester", ooe, Role.LANDESLEITER);
        Person karin = new Person("00002", "passme", "Karin", "Tester", wels, Role.BEZIRKSLEITER);
        Person gusi = new Person("00003", "passme", "Gusi", "Tester", sattledt, Role.GRUPPENLEITER);
        Person doris = new Person("00004", "passme", "Doris", "Tester", sattledt1, Role.KIND);
        Person isabella = new Person("00005", "passme", "Isabella", "Tester", sattledt1, Role.KIND);
        Person antonia = new Person("00006", "passme", "Antonia", "Tester", marchtrenk, Role.GRUPPENLEITER);
        Person melanie = new Person("00007", "passme", "Melanie", "Tester", marchtrenk1, Role.KIND);
        insert(tom);
        insert(karin);
        insert(gusi);
        insert(doris);
        insert(isabella);
        insert(antonia);
        insert(melanie);

        return "Testvalues inserted";
    }

    /**
     * to insert a Person
     *
     * @param b
     * @return
     */
    public static Person insert(Person b) {
        em.getTransaction().begin();
        em.merge(b);
        em.getTransaction().commit();
        return b;
    }
}
