package com.example.veb_projekat.filters;


import com.example.veb_projekat.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class AuthFilter implements ContainerRequestFilter {
    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

//        System.out.println(requestContext.getRequest().getMethod() + " " + requestContext.getUriInfo().getPath() + " " + requestContext.getHeaderString("Authorization"));

        if(requestContext.getUriInfo().getPath().contains("login") ||
                requestContext.getRequest().getMethod().equals("OPTIONS")){
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if(this.isAuthorizationRequired(requestContext) && !this.userService.isAuthorized(token)){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            else if(this.isAuthenticationRequired(requestContext) && !this.userService.isAuthenticated(token)){
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }

        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthenticationRequired(ContainerRequestContext req){
        return req.getRequest().getMethod().equals("POST") && req.getUriInfo().getPath().equals("news/insert") ||
                req.getRequest().getMethod().equals("PUT") && req.getUriInfo().getPath().equals("news/update") ||
                req.getRequest().getMethod().equals("DELETE") && req.getUriInfo().getPath().contains("news") ||
                req.getRequest().getMethod().equals("POST") && req.getUriInfo().getPath().equals("categories") ||
                req.getRequest().getMethod().equals("PUT") && req.getUriInfo().getPath().contains("categories") ||
                req.getRequest().getMethod().equals("DELETE") && req.getUriInfo().getPath().contains("categories");
    }

    private boolean isAuthorizationRequired(ContainerRequestContext req) {
        return req.getUriInfo().getPath().contains("users/register") ||
                req.getUriInfo().getPath().contains("users/update") ||
                req.getRequest().getMethod().equals("GET") && req.getUriInfo().getPath().contains("users/by-id") ||
                req.getUriInfo().getPath().contains("users/status-activation") ||
                req.getUriInfo().getPath().contains("users/status-deactivation") ||
                req.getUriInfo().getPath().contains("users/get-all");
    }

}
