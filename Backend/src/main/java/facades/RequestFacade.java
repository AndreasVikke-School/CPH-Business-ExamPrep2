package facades;

import entities.Category;
import entities.Request;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Andreas Vikke
 */
public class RequestFacade {
    private static RequestFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private RequestFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static RequestFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RequestFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    
    public List<Category> getAllCategories() {
        return getEntityManager().createQuery("SELECT c FROM Category c").getResultList();
    }
    
    public Category getSingleCategory(String name) throws NoResultException {
        return getEntityManager().createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
    }
    
    public List<Request> getAllRequests() {
        return getEntityManager().createQuery("SELECT r FROM Request r").getResultList();
    }
    
    public long getCountOfRequestsInCategory(long id) {
        return getEntityManager().createQuery("SELECT COUNT(r) FROM Request r WHERE r.categories IN :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }
    
    public void createRequest(List<Category> categoires) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Request(new Date(), categoires));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
