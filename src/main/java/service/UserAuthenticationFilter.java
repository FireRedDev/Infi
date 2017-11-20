//package service;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.Base64;
//import javax.ws.rs.container.PreMatching;
//
//import static javax.ws.rs.core.Response.ResponseBuilder;
//import static javax.ws.rs.core.Response.Status;
//
//@Provider
//@PreMatching
//public class UserAuthenticationFilter implements ContainerRequestFilter, 
//                                             ContainerResponseFilter {
//    
//    @Override
//    public void filter(ContainerRequestContext requestContext)
//                       throws IOException {
//
//        MultivaluedMap<String, String> headers = requestContext.getHeaders();
//        System.out.println(" ================ Header start ================");
//        for (String key : headers.keySet()) {
//            System.out.println(key + " " + headers.getFirst(key));
//        }
//        System.out.println(" ================ Header stop ================");
//
//        final String authorization = requestContext.getHeaderString("Authorization");
//
//        if (authorization != null && authorization.startsWith("Basic")) {
//            String base64Credentials = authorization.substring("Basic".length()).trim();
//            String credentials;
//            try {
//                credentials = new String(Base64.getDecoder().decode(base64Credentials), 
//                                         Charset.forName("UTF-8"));
//            }
//            catch(IllegalArgumentException e) {
//                credentials="";
//            }
//
//            if (!credentials.equals("passme")) {
//                // Eintrag in Http-Header:
//                // Authorization: Basic cGFzc21l
//                System.out.println("Authentication failed!");
//                ResponseBuilder responseBuilder = Response.status(Status.UNAUTHORIZED);
//                Response response = responseBuilder.build();
//                requestContext.abortWith(response);
//            } else {
//                System.out.println("Authentication granted");
//            }
//
//        } else {
//            System.out.println("No authentication header found!");
//            ResponseBuilder responseBuilder = Response.status(Status.UNAUTHORIZED);
//            Response response = responseBuilder.build();
//            requestContext.abortWith(response);
//        }
//    }
//
//    @Override
//    public void filter(ContainerRequestContext requestContext,
//                       ContainerResponseContext responseContext)
//            throws IOException {
//            responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
//            responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//            responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");;
//    }
//}
