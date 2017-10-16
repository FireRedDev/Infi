package service;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author H. Lackinger
 */
public class HttpServer_JavaSE {

    // Basis URI 
    public static final String BASE_URI = "http://localhost:8080/api/";

    public static void main(String[] args) throws IOException {
        // Server starten
        final ResourceConfig rc = new ResourceConfig().packages("service");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("public"), "/");
        System.out.println(String.format("Server startet at %s\nHit enter to stop ...", BASE_URI));
        System.in.read();
        server.shutdown();
    }
}
