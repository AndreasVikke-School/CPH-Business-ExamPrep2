package utils;

import entities.Category;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author andreas
 */
public class SetupCategories {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        EntityManager em = emf.createEntityManager();
        
        List<String> categories = Arrays.asList(new String[] {"career", "celebrity", "dev", "explicit", "fashion", "food", "history", "money", "movie", "music", "political", "science", "sport", "travel"});

        em.getTransaction().begin();
        for(String s : categories)
            em.persist(new Category(s, null));
        em.getTransaction().commit();
    }
}
