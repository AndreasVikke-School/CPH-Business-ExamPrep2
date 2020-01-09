/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facades.ApiFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author emilt
 */
@Path("swapi")
public class SwapiResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SwapiResource
     */
    public SwapiResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{\"msg\": \"Welcome to the Star Wars API!\"}";
    }
    
    
    //Fetches data from 10 hardcoded end points.
    @GET
    @Path("demo")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonList() {
        ApiFacade af = new ApiFacade();
        String url = "https://swapi.co/api/";
        List<String> l = new ArrayList();
        l.add("people/1");
        l.add("people/2");
        l.add("people/3");
        l.add("people/4");
        l.add("people/5");
        return af.fetch(url, l).toString();
    }

}
