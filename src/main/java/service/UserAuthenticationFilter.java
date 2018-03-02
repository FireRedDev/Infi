package service;

import entities.JWTTokenUser;
import entities.Person;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.Response.ResponseBuilder;
import static javax.ws.rs.core.Response.Status;
import repository.DatenbankRepository;
import repository.EntityManagerSingleton;

/**
 *
 * @author Christopher G
 */
@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
//
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

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException { 
//TODO: get user/pass from token instead of database

        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        System.out.println(" ================ Header start ================");
        headers.keySet().forEach((key) -> {
            System.out.println(key + " " + headers.getFirst(key));
        });
        System.out.println(" ================ Header stop ================");

        String authorization = requestContext.getHeaderString("Authorization");

        if (authorization != null && authorization.startsWith("Bearer")) {
            authorization = authorization.substring("Bearer".length()).trim();
            authorization = authorization.replace("\"", "");
            String credentials;
            try {
                credentials = authorization;//decodeJWT(authorization);
            } catch (IllegalArgumentException e) {
                credentials = "";
            }

            Query query = em.createQuery("select t from JWTTokenUser t where t.token = :token").setParameter("token", credentials);

            JWTTokenUser jwt = (JWTTokenUser) query.getSingleResult();
            Person user = jwt.getPerson();
            Class<?> resourceClass = resourceInfo.getResourceClass();
            List<Role> classRoles = extractRoles(resourceClass);

            // Get the resource method which matches with the requested URL
            // Extract the roles declared by it
            Method resourceMethod = resourceInfo.getResourceMethod();
            List<Role> methodRoles = extractRoles(resourceMethod);

            try {

                // Check if the user is allowed to execute the method
                // The method annotations override the class annotations
                if (methodRoles.isEmpty()) {
                    checkPermissions(classRoles, user);
                } else {
                    checkPermissions(methodRoles, user);
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
     *
     * @param jwt
     * @return
     */
    public String decodeJWT(String jwt) {
        try {

            try {
                return Jwts.parser()
                        .setSigningKey("secret".getBytes("UTF-8"))
                        .parseClaimsJws(jwt
                        ).getSignature();

                //OK, we can trust this JWT
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(DatenbankRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SignatureException e) {

            //don't trust the JWT!
        }
        return null;
    }

    private void checkPermissions(List<Role> allowedRoles, Person user) throws Exception {
        // Check if the user contains one of the allowed roles
        // Throw an Exception if the user has not permission to execute the method
        boolean VALID = false;
        for (Role role : allowedRoles) {
            if (role == user.getRolle()) {
                VALID = true;
            }

        }
        if (!VALID) {
            throw new Exception();
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");;
    }
}
