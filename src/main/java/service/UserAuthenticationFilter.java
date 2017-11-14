/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.ext.Provider;

/**
 * C.G
 */
@Provider

public class UserAuthenticationFilter implements ContainerRequestFilter {

    @Resource
    private ResourceInfo resourceinfo;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String userId = containerRequestContext.getHeaders().getFirst("kalendarCookie");
        Class<?> klasse = resourceinfo.getClass();
        Method method = resourceinfo.getResourceMethod();
        if (method.getAnnotation(Secured.class) != null) {

        }
        System.out.print(userId);
    }

}
