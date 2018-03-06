/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import entities.Person;
import entities.Role;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
* C.G
 */
public class MySecurityContext implements SecurityContext {

    private final int id;
    private final Role role;

    public MySecurityContext(int id,Role role) {
       this.id=id;this.role=role;
    }

    @Override
    public Principal getUserPrincipal() {
                    return () -> String.valueOf(id); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean isUserInRole(String role) {
        Role rolle = Role.valueOf(role);
        return rolle==this.role;
    }

    @Override
    public boolean isSecure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAuthenticationScheme() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
