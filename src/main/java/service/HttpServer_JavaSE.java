package service;

import entities.*;
import static entities.JRKEntitaetType.*;
import java.io.File;
import java.net.URI;
import java.util.logging.*;
import javax.persistence.EntityManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.utils.ArraySet;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import repository.EntityManagerSingleton;
import upload.UploadService;

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
     * Main
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Class.forName("com.fasterxml.jackson.annotation.JsonInclude");
        // Server starten
        final ResourceConfig rc = new ResourceConfig().packages("service");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        StaticHttpHandler hh = new StaticHttpHandler("public");
        ArraySet<File> docRoots = hh.getDocRoots();
        server.getServerConfiguration().setMaxPostSize(16000000);
        server.getServerConfiguration().addHttpHandler(hh, "/");

        server.getServerConfiguration().addHttpHandler(new UploadService(docRoots.getArray()[0]), "/upload");
        System.out.println(String.format("Server startet at %s\nHit enter to stop ...", BASE_URI));
        init();
//        Thread t = new Thread(new NotificationRunnable());
//        t.start();
        Logger l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);
        Thread t = new Thread(new NotificationRunnable());

        t.start();
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

        //JRK Entitäten erstellen
        JRKEntitaet ooe = new JRKEntitaet(5, "Oberösterreich", landesstelle, null);
        JRKEntitaet wels = new JRKEntitaet(4, "Wels", bezirkstelle, ooe);
        JRKEntitaet sattledt = new JRKEntitaet(1, "Sattledt", ortstelle, wels);
        JRKEntitaet sattledt1 = new JRKEntitaet(2, "Gruppe1", gruppe, sattledt);
        JRKEntitaet marchtrenk = new JRKEntitaet(3, "Marchtrenk", ortstelle, wels);
        JRKEntitaet marchtrenk1 = new JRKEntitaet(6, "Gruppe1", gruppe, marchtrenk);

        //Termine erstellen
        Termin sattledttermin = new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        String[] betreuer = {"Gusi Tester", "Isabella Tester"};
        String[] kinder = {"Melanie Tester", "Antonia Tester", "Isabella Tester"};
        String[] betreuer1 = {"Doris Tester", "Jakob Tester"};
        String[] kinder1 = {"Petra Tester", "Melanie Tester", "Harald Tester"};
        sattledttermin.setDoko(new Dokumentation(kinder, betreuer, "basteln", 2.0, "Soziales"));
        sattledttermin.setImgpath("assets/Logo.png");
        sattledt1.addTermin(sattledttermin);

        Termin welstermin = new Termin("2018-03-04 15:30:00", "2018-03-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Wels");
        welstermin.setDoko(new Dokumentation(kinder, betreuer, "basteln", 2.0, "EH"));
        welstermin.setImgpath("assets/Logo.png");
        wels.addTermin(welstermin);

        Termin welstermin4 = new Termin("2018-10-25 15:30:00", "2018-10-25 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Wels", "Antonia Tester; Melanie Tester;");
        welstermin4.setImgpath("assets/Logo.png");
        wels.addTermin(welstermin4);

        Termin sattermin = new Termin("2018-03-05 15:30:00", "2018-03-05 17:30:00", "Generalversammlung", "Ortsstellenversammlung", "Dienststelle Sattledt", "Antonia Tester; Melanie Tester;");
        sattermin.setDoko(new Dokumentation(kinder, betreuer, "basteln", 2.0, "Exkursion"));
        sattledt.addTermin(sattermin);

        Termin sattledttermin1 = new Termin("2018-02-04 15:30:00", "2018-02-04 17:30:00", "Generalversammlung", "Ortsstellenversammlung", "Dienststelle Sattledt");
        sattledttermin1.setDoko(new Dokumentation(kinder1, betreuer1, "basteln", 2.0, "Exkursion"));
        sattledt1.addTermin(sattledttermin1);

        Termin welstermin1 = new Termin("2018-04-04 15:30:00", "2018-04-04 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        welstermin1.setDoko(new Dokumentation(kinder, betreuer1, "basteln", 2.0, "Soziales"));
        wels.addTermin(welstermin1);

        Termin welstermin2 = new Termin("2018-06-15 14:00:00", "2018-06-15 21:00:00", "Klettern", "Bitte feste Schuhe anziehen", "Klettergarten");
        welstermin2.setDoko(new Dokumentation(kinder, betreuer1, "basteln", 7.0, "Soziales"));
        wels.addTermin(welstermin2);

        Termin welstermin3 = new Termin("2017-06-15 14:00:00", "2017-06-15 21:00:00", "Klettern", "Bitte feste Schuhe anziehen", "Klettergarten");
        welstermin3.setDoko(new Dokumentation(kinder, betreuer1, "klettern", 7.0, "Soziales"));
        wels.addTermin(welstermin3);

        Termin sattermin1 = new Termin("2018-04-05 15:30:00", "2018-04-05 17:30:00", "Gruppenstunde", "Gruppenstunde mit Schwerpunkt Erste-Hilfe", "Dienststelle Sattledt");
        sattermin1.setDoko(new Dokumentation(kinder1, betreuer, "basteln", 2.0, "Soziales"));
        sattermin1.setImgpath("assets/Logo.png");
        sattledt.addTermin(sattermin1);

        Termin marchtrenktermin = new Termin("2018-01-24 18:00:00", "2018-01-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk");
        marchtrenktermin.setDoko(new Dokumentation(kinder1, betreuer1, "grillen", 3.0, "Soziales"));
        marchtrenk1.addTermin(marchtrenktermin);

        Termin marchtrenktermin1 = new Termin("2018-04-04 15:30:00", "2018-04-04 17:30:00", "Film schauen", "Bitte Decken und Polster mit nehmen", "Dienststelle Marchtrenk");
        marchtrenktermin1.setDoko(new Dokumentation(kinder1, betreuer1, "schauen", 2.0, "Soziales"));
        marchtrenktermin1.setImgpath("assets/Logo.png");
        marchtrenk1.addTermin(marchtrenktermin1);

        marchtrenk1.addTermin(new Termin("2018-01-04 15:30:00", "2018-01-04 17:30:00", "Eislaufen", "Bitte Eislaufschuhe, Winterkleidung und 3€ Eintritt mitnehmen", "Eislaufplatz Marchtrenk"));
        wels.addTermin(new Termin("2018-01-24 18:00:00", "2018-01-24 21:00:00", "Grillerei", "Grillerei für alle Dienststellen des Bezirkes", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-02-02 18:00:00", "2018-02-02 21:00:00", "Faschingsumzug", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));
        wels.addTermin(new Termin("2018-02-15 14:00:00", "2018-02-15 21:00:00", "Basteln", "Bitte Schere und Kleber mitnehmen", "Dienststelle Marchtrenk"));

        marchtrenk1.addTermin(new Termin("2018-03-04 15:30:00", "2018-03-04 17:30:00", "Film schauen", "Bitte Decken und Polster mit nehmen", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-04-24 18:00:00", "2018-04-24 21:00:00", "Basteln", "Bitte Schere und Kleber mitnehmen", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-05-02 18:00:00", "2018-05-02 21:00:00", "Grillerei", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));
        wels.addTermin(new Termin("2018-06-15 14:00:00", "2018-06-15 21:00:00", "Klettern", "Bitte feste Schuhe anziehen", "Klettergarten"));

        marchtrenk.addTermin(new Termin("2018-04-04 15:30:00", "2018-04-04 17:30:00", "Film schauen auf der Dienststelle", "Bitte Decken und Polster mit nehmen", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-04-24 17:00:00", "2018-04-24 21:00:00", "Basteln", "Bitte Schere und Kleber mitnehmen", "Dienststelle Marchtrenk"));
        ooe.addTermin(new Termin("2018-05-02 13:00:00", "2018-05-02 21:00:00", "Grillerei", "viele JRK-Gruppen sind dabei.", "Linz Hauptplatz"));
        wels.addTermin(new Termin("2018-06-15 10:00:00", "2018-06-15 21:00:00", "Klettern", "Bitte feste Schuhe anziehen", "Klettergarten"));

        marchtrenk.addTermin(new Termin("2018-01-15 15:30:00", "2018-01-17 17:30:00", "Wandern", "Festes Schuhwerk", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-05-30 09:00:00", "2018-05-30 21:00:00", "Ausflug nach Salzburg", "Geld für Jause mitnehmen", "Linz Hauptbahnhof"));
        ooe.addTermin(new Termin("2018-12-19 13:00:00", "2018-12-19 21:00:00", "Adventmarkt", "warm anziehen", "Adventmarkt Wels","Doris Tester; Isabella Tester;"));
        wels.addTermin(new Termin("2018-08-15 09:00:00", "2018-08-15 17:00:00", "Freibad", "Badesachen nicht vergessen", "Freibad Wels"));

        marchtrenk.addTermin(new Termin("2018-10-01 11:30:00", "2018-10-01 17:30:00", "Wandern auf der Reiteralm", "Festes Schuhwerk", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-12-30 09:00:00", "2018-12-30 21:00:00", "Ausflug nach Salzburg", "Geld für Jause mitnehmen", "Linz Hauptbahnhof"));
        ooe.addTermin(new Termin("2018-04-19 13:00:00", "2018-04-19 21:00:00", "Film schauen", "Popkorn mitnehmen", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2018-07-15 09:00:00", "2018-07-15 17:00:00", "Freibad", "Badesachen nicht vergessen", "Freibad Wels"));

        marchtrenk1.addTermin(new Termin("2019-01-15 15:30:00", "2019-01-17 17:30:00", "Wandern", "Festes Schuhwerk", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2019-05-30 09:00:00", "2019-05-30 21:00:00", "Ausflug nach Salzburg", "Geld für Jause mitnehmen", "Linz Hauptbahnhof"));
        ooe.addTermin(new Termin("2017-12-19 13:00:00", "2017-12-19 21:00:00", "Adventmarkt", "warm anziehen", "Adventmarkt Wels", "Antonia Tester; Melanie Tester;"));
        wels.addTermin(new Termin("2019-08-15 09:00:00", "2019-08-15 17:00:00", "Freibad", "Badesachen nicht vergessen", "Freibad Wels"));

        marchtrenk1.addTermin(new Termin("2017-10-01 11:30:00", "2017-10-17 17:30:00", "Wandern", "Festes Schuhwerk", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2017-12-30 09:00:00", "2017-12-30 21:00:00", "Ausflug nach Salzburg", "Geld für Jause mitnehmen", "Linz Hauptbahnhof"));
        ooe.addTermin(new Termin("2019-04-19 13:00:00", "2019-04-19 21:00:00", "Film schauen", "Popkorn mitnehmen", "Dienststelle Marchtrenk"));
        wels.addTermin(new Termin("2017-07-15 09:00:00", "2017-07-15 17:00:00", "Freibad", "Badesachen nicht vergessen", "Freibad Wels"));

        ooe.addTermin(new Termin("2018-05-29 13:00:00", "2018-05-29 21:00:00", "Film schauen", "Popkorn mitnehmen", "Dienststelle Marchtrenk"));

        String[] a = {"http://localhost:8080/upload_image/teambuilding.jpg"};
        String[] b = {"http://localhost:8080/upload_image/bezirkslager2.jpg", "http://localhost:8080/upload_image/bezirkslager3.jpg", "http://localhost:8080/upload_image/bezirkslager1.jpg"};
        String[] c = {"http://localhost:8080/upload_image/halloween.jpg"};
        String[] d = {"http://localhost:8080/upload_image/fotoOnline.jpg", "http://localhost:8080/upload_image/lager.jpg"};

        sattledt1.addInfo(new Info("Terminfindung für Fotoshooting", "Bitte Abstimmen Doodle-Link", a, "2018-02-15 09:00:00"));
        ooe.addInfo(new Info("Fotos", "fotos sind online oö", d, "2017-07-15 09:00:00"));
        wels.addInfo(new Info("Bezirkslager", "Bilder sind endlich auf Dropbox Link:", b, "2017-08-15 09:00:00"));
        wels.addInfo(new Info("Halloween", "Ergebnisse von der Halloweenstunde", c, "2018-01-15 09:00:00"));

        //Personen erstellen
        Person tom = new Person("00001", "passme", "Tom", "Tester", ooe, Role.LANDESLEITER);
        Person karin = new Person("00002", "passme", "Karin", "Tester", wels, Role.BEZIRKSLEITER);
        Person gusi = new Person("00003", "passme", "Gusi", "Tester", sattledt, Role.ORTSTELLENLEITER);
        Person doris = new Person("00004", "passme", "Doris", "Tester", sattledt1, Role.KIND);
        Person isabella = new Person("00005", "passme", "Isabella", "Tester", sattledt1, Role.KIND);
        Person antonia = new Person("00006", "passme", "Antonia", "Tester", marchtrenk, Role.ORTSTELLENLEITER);
        Person melanie = new Person("00007", "passme", "Melanie", "Tester", marchtrenk1, Role.KIND);
        Person petra = new Person("00008", "passme", "Petra", "Tester", sattledt1, Role.KIND);

        //Personen einfügen
        insert(tom);
        insert(karin);
        insert(gusi);
        insert(doris);
        insert(isabella);
        insert(antonia);
        insert(melanie);
        insert(petra);

        return "Testvalues inserted";
    }

    /**
     * insert a Person
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
