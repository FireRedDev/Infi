package service;

import entities.Role;
import io.jsonwebtoken.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.persistence.EntityManager;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.Response.ResponseBuilder;
import static javax.ws.rs.core.Response.Status;
import repository.*;

/**
 * Blocks Unauthorized Access of Rest Methods ,extracts Roles and covers authorization checks, adds cors headers
 * 
 * @author Christopher G
 */
@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class UserAuthenticationFilter implements ContainerRequestFilter,
        ContainerResponseFilter {

    @Context
    private ResourceInfo resourceInfo;

    private final EntityManager em;

    /**
     *
     */
    public UserAuthenticationFilter() {
        em = EntityManagerSingleton.getInstance().getEm();
    }

    /**
     *
     * @param requestContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {
        //TODO: get user/pass from token instead of database
        //print header and method
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        System.out.println(" ================ Header start ================");
        headers.keySet().forEach((key) -> {
            System.out.println(key + " " + headers.getFirst(key));
        });
        System.out.println(" ================ Header stop ================");

        String authorization = requestContext.getHeaderString("Authorization");
        //is there a token?
        if (authorization != null && authorization.startsWith("Bearer")) {
            //trim token
            authorization = authorization.substring("Bearer".length()).trim();
            authorization = authorization.replace("\"", "");
            Jws<Claims> credentials;
            try {
                credentials = decodeJWT(authorization);
            } catch (IllegalArgumentException e) {
                credentials = null;
            }

            //get the tokens user from the database to check his permissions(can he access this method?) in the checkPermissions Method
            //get permitted roles from the accessed method
            Class<?> resourceClass = resourceInfo.getResourceClass();
            List<Role> classRoles = extractRoles(resourceClass);

            // Get the resource method which matches with the requested URL
            // Extract the roles declared by it
            Method resourceMethod = resourceInfo.getResourceMethod();
            List<Role> methodRoles = extractRoles(resourceMethod);
            // or simple but not the best
            int id = credentials.getBody().get("id", Integer.class);
            Role role = Role.valueOf(credentials.getBody().get("role", String.class));
            requestContext.setSecurityContext(new MySecurityContext(id, role));

            try {

                // Check if the user is allowed to execute the method
                // The method annotations override the class annotations
                if (methodRoles.isEmpty()) {
                    checkPermissions(classRoles, role);
                } else {
                    checkPermissions(methodRoles, role);
                }

            } catch (Exception e) {
                requestContext.abortWith(
                        Response.status(Response.Status.FORBIDDEN).build());
            }
            if (false) {
                System.out.println("Authentication failed!");
                ResponseBuilder responseBuilder = Response.status(Status.UNAUTHORIZED);
                Response response = responseBuilder.build();
                requestContext.abortWith(response);
            } else {
                System.out.println("Authentication granted");
            }

        } else {
            System.out.println("No authentication header found!");
            ResponseBuilder responseBuilder = Response.status(Status.UNAUTHORIZED);
            Response response = responseBuilder.build();
            requestContext.abortWith(response);
        }
    }
//Extract Roles From the @Secured Annotation
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    /**
     * Decode JWT Token with JWTS Libary
     *
     * @param jwt
     * @return
     */
    public Jws<Claims> decodeJWT(String jwt) {
        try {

            try {
                return Jwts.parser()
                        .setSigningKey("secretswaggy132".getBytes("UTF-8"))
                        .parseClaimsJws(jwt);

                //OK, we can trust this JWT
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SignatureException e) {

            //don't trust the JWT!
        }
        return null;
    }

    private void checkPermissions(List<Role> allowedRoles, Role userRole) throws Exception {
        // Check if the user contains one of the allowed roles
        // Throw an Exception if the user has not permission to execute the method
        boolean VALID = false;
        for (Role role : allowedRoles) {
            if (role != userRole) {
            } else {
                VALID = true;
            }

        }
        if (!VALID) {
            throw new Exception();
        }
    }

    /**
     * Add Cors Headers to cover Port Forwarding Problems
     * @param requestContext
     * @param responseContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");;
    }
}
