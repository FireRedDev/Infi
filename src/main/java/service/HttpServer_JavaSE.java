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
        JRKEntitaet marchtrenk = new JRKEntitaet(3, "Marchtrenk", ortstelle, wels);
        JRKEntitaet marchtrenk1 = new JRKEntitaet(6, "Gruppe1", gruppe, marchtrenk);

        //Dokumentation
        Termin sattledttermin = new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        String[] betreuer = {"Gusi", "Isi"};
        String[] kinder = {"Meli", "Antonia", "Luki"};
        String[] betreuer1 = {"Daniel", "Jakob"};
        String[] kinder1 = {"Luisa", "Jonas", "Harald"};
        sattledttermin.setDoko(new Dokumentation(kinder, betreuer, "basteln", 2.0, "Soziales"));
        sattledt1.addTermin(sattledttermin);

        Termin welstermin = new Termin("2018-03-04 15:30:00", "2018-03-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Wels");
        welstermin.setDoko(new Dokumentation(kinder, betreuer, "basteln", 2.0, "EH"));
        wels.addTermin(welstermin);

        Termin sattermin = new Termin("2018-03-05 15:30:00", "2018-03-05 17:30:00", "Generalversammlung", "Ortsstellenversammlung", "Dienststelle Sattledt");
        sattermin.setDoko(new Dokumentation(kinder, betreuer, "basteln", 2.0, "Exkursion"));
        sattledt.addTermin(sattermin);

        //
        Termin sattledttermin1 = new Termin("2018-02-04 15:30:00", "2018-02-04 17:30:00", "Generalversammlung", "Ortsstellenversammlung", "Dienststelle Sattledt");
        sattledttermin1.setDoko(new Dokumentation(kinder1, betreuer1, "basteln", 2.0, "Exkursion"));
        sattledt1.addTermin(sattledttermin1);

        Termin welstermin1 = new Termin("2018-04-04 15:30:00", "2018-04-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        welstermin1.setDoko(new Dokumentation(kinder, betreuer1, "basteln", 2.0, "Soziales"));
        wels.addTermin(welstermin1);

        Termin welstermin2 = new Termin("2018-06-15 14:00:00", "2018-06-15 21:00:00", "Klettern", "Bitte feste Schuhe anziehen", "Klettergarten");
        welstermin2.setDoko(new Dokumentation(kinder, betreuer1, "basteln", 7.0, "Soziales"));
        wels.addTermin(welstermin2);

        Termin sattermin1 = new Termin("2018-04-05 15:30:00", "2018-04-05 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        sattermin1.setDoko(new Dokumentation(kinder1, betreuer, "basteln", 2.0, "Soziales"));
        sattledt.addTermin(sattermin1);

        Termin marchtrenktermin = new Termin("2018-01-24 18:00:00", "2018-01-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk");
        marchtrenktermin.setDoko(new Dokumentation(kinder1, betreuer1, "grillen", 3.0, "Soziales"));
        marchtrenk1.addTermin(marchtrenktermin);

        Termin marchtrenktermin1 = new Termin("2018-04-04 15:30:00", "2018-04-04 17:30:00", "Film schauen", "Bitte Decken und Polster mit nehmen", "Dienststelle Marchtrenk");
        marchtrenktermin1.setDoko(new Dokumentation(kinder1, betreuer1, "schauen", 2.0, "Soziales"));
        marchtrenk1.addTermin(marchtrenktermin);

        marchtrenk1.addTermin(new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Eislaufen", "Bitte Eislaufschuhe, Winterkleidung und 3€ Eintritt mitnehmen", "Eislaufplatz Marchtrenk"));
        wels.addTermin(new Termin("2018-01-24 18:00:00", "2018-01-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-02-02 18:00:00", "2018-02-02 21:00:00", "Faschingsumzug", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));
        wels.addTermin(new Termin("2018-02-15 14:00:00", "2018-02-15 21:00:00", "Basteln", "Bitte Schere und Kleber mitnehmen", "Dienststelle Marchtrenk"));

        marchtrenk1.addTermin(new Termin("2018-03-04 15:30:00", "2018-03-04 17:30:00", "Film schauen", "Bitte Decken und Polster mit nehmen", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-04-24 18:00:00", "2018-04-24 21:00:00", "Basteln", "Bitte Schere und Kleber mitnehmen", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-05-02 18:00:00", "2018-05-02 21:00:00", "Grillerei", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));
        wels.addTermin(new Termin("2018-06-15 14:00:00", "2018-06-15 21:00:00", "Klettern", "Bitte feste Schuhe anziehen", "Klettergarten"));

        marchtrenk1.addTermin(new Termin("2018-04-04 15:30:00", "2018-04-04 17:30:00", "Film schauen", "Bitte Decken und Polster mit nehmen", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-04-24 17:00:00", "2018-04-24 21:00:00", "Basteln", "Bitte Schere und Kleber mitnehmen", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-05-02 13:00:00", "2018-05-02 21:00:00", "Grillerei", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));
        wels.addTermin(new Termin("2018-06-15 10:00:00", "2018-06-15 21:00:00", "Klettern", "Bitte feste Schuhe anziehen", "Klettergarten"));

        marchtrenk1.addTermin(new Termin("2018-01-015 15:30:00", "2018-01-17 17:30:00", "Wandern", "Festes Schuhwerk", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-05-30 09:00:00", "2018-05-30 21:00:00", "Ausflug nach Salzburg", "Geld für Jause mitnehmen", "Linz Hauptbahnhof"));
        ooe.addTermin(new Termin("2018-12-19 13:00:00", "2018-12-19 21:00:00", "Adventmarkt", "warm anziehen", "Adventmarkt Wels"));
        wels.addTermin(new Termin("2018-08-15 09:00:00", "2018-08-15 17:00:00", "Freibad", "Badesachen nicht vergessen", "Freibad Wels"));

        marchtrenk1.addTermin(new Termin("2018-10-01 11:30:00", "2018-10-17 17:30:00", "Wandern", "Festes Schuhwerk", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-12-30 09:00:00", "2018-12-30 21:00:00", "Ausflug nach Salzburg", "Geld für Jause mitnehmen", "Linz Hauptbahnhof"));
        ooe.addTermin(new Termin("2018-04-19 13:00:00", "2018-04-19 21:00:00", "Film schauen", "Popkorn mitnehmen", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-07-15 09:00:00", "2018-07-15 17:00:00", "Freibad", "Badesachen nicht vergessen", "Freibad Wels"));

        sattledt1.addInfo(new Info("Terminfindung für Fotoshooting", "Bitte Abstimmen Doodle-Link", "assets/lager.jpg"));
        ooe.addInfo(new Info("Fotos", "fotos sind online oö", "assets/teambuilding.jpg"));
        wels.addInfo(new Info("Bezirkslager", "Bilder sind endlich auf Dropbox Link:", "assets/lager.jpg"));

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
