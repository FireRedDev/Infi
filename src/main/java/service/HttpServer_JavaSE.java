package service;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import org.glassfish.jersey.server.ResourceConfig;

public class HttpServer_JavaSE {

    public static final String BASE_URI = "http://localhost:8080/api/";

    public static void main(String[] args) throws Exception {
        
   
        
        Class.forName("com.fasterxml.jackson.annotation.JsonInclude");
        // Server starten
        final ResourceConfig rc = new ResourceConfig().packages("service");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("public"), "/");
        System.out.println(String.format("Server startet at %s\nHit enter to stop ...", BASE_URI));
        Logger l
                = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);
        
        System.in.read();
        server.shutdown();
    }
}
