package rest;

import entities.Category;
import facades.RequestFacade;
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
 * @author andreas
 */
@Path("category")
public class CategoryResource {
    
    private static final EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/examprep2",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final RequestFacade requestFacade =  RequestFacade.getFacade(emf);


    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CategoryResource
     */
    public CategoryResource() {
    }
    
    @GET
    @Path("categoryCount/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public long getRequestCountByCategory(@PathParam("category") String category) {
        try {
            return requestFacade.getRequestCountByCategory(category);
        } catch(NoResultException ex) {
            throw new WebApplicationException("Category not allowed");
        }
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAllCategories() {
        return requestFacade.getAllCategories();
    }
}
