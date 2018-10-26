package service;

import entities.Role;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 * C.G
 */
public class MySecurityContext implements SecurityContext {

    private final int id;
    private final Role role;

    /**
     *
     * @param id
     * @param role
     */
    public MySecurityContext(int id, Role role) {
        this.id = id;
        this.role = role;
    }

    /**
     *
     * @return
     */
    @Override
    public Principal getUserPrincipal() {
        return () -> String.valueOf(id); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     *
     * @param role
     * @return
     */
    @Override
    public boolean isUserInRole(String role) {
        Role rolle = Role.valueOf(role);
        return rolle == this.role;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSecure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    public String getAuthenticationScheme() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
