/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.Category;
import entities.dto.JokesDTO;
import facades.JokesFacade;
import facades.RequestFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author emilt
 */
@Path("jokes")
public class JokesResource {
    
    private static final EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/examprep2",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final JokesFacade jokesFacade =  JokesFacade.getFacade();
    private static final RequestFacade requestFacade =  RequestFacade.getFacade(emf);

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SwapiResource
     */
    public JokesResource() {
    }
    
    @GET
    @Path("jokeByCategory/{categories}")
    @Produces(MediaType.APPLICATION_JSON)
    public JokesDTO getJokesByCategory(@PathParam("categories") String categoires) {
        String url = "https://api.chucknorris.io/jokes/random?category=";
        String[] catStrings = categoires.split(",");
        
        if(catStrings.length > 4)
            throw new WebApplicationException("More than 4 categories supplied (Max. 4)");
        
        try {
            List<Category> cats = new ArrayList();
            for(String cat : catStrings)
                cats.add(requestFacade.getSingleCategory(cat));
            
            requestFacade.createRequest(cats);
            return jokesFacade.fetch(url, cats);
        } catch(NoResultException ex) {
            throw new WebApplicationException("Categories not allowed");
        }
    }
    
    @GET
    @Path("jokeByCategoryV2/{categories}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public JokesDTO getJokesByCategoryV2(@PathParam("categories") String categoires) {
        String url = "https://api.chucknorris.io/jokes/random?category=";
        String[] catStrings = categoires.split(",");
        
        if(catStrings.length > 12)
            throw new WebApplicationException("More than 12 categories supplied (Max. 12)");
        
        try {
            List<Category> cats = new ArrayList();
            for(String cat : catStrings)
                cats.add(requestFacade.getSingleCategory(cat));
            
            requestFacade.createRequest(cats);
            return jokesFacade.fetch(url, cats);
        } catch(NoResultException ex) {
            throw new WebApplicationException("Categories not allowed");
        }
    }
}
