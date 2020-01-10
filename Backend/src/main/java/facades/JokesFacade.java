package facades;

import entities.Category;
import entities.dto.JokeDTO;
import entities.dto.JokesDTO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.json.JSONObject;

/**
 *
 * @author Andreas Vikke
 */
public class JokesFacade {
    
    private static JokesFacade instance;
    
    //Private Constructor to ensure Singleton
    private JokesFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static JokesFacade getFacade() {
        if (instance == null) {
            instance = new JokesFacade();
        }
        return instance;
    }

    //This fetch method returns a string with json format
    //based on a given url (using HTTP connection and a request method).
    public JokeDTO fetch(String urlStr) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            con.addRequestProperty("User-Agent", "Mozilla/4.76;Chrome"); 
            String jsonStr = "";
            try ( Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
            }
            JSONObject jsonObj = new JSONObject(jsonStr);
            return new JokeDTO(jsonObj.getJSONArray("categories").get(0).toString(), jsonObj.getString("value"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.disconnect();
        }
    }

    //This fetch method returns a string with json format
    //based on a given url and a specific* (using HTTP connection and a request method).
    //*a specific is abstract for a given identity to a variable on an endpoint
    //example would be a specific person ID on a person API.
    public JokeDTO fetch(String urlStr, String specific) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlStr + specific);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            con.addRequestProperty("User-Agent", "Mozilla/4.76;Chrome");
            String jsonStr = "";
            try ( Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
            }
            JSONObject jsonObj = new JSONObject(jsonStr);
            return new JokeDTO(jsonObj.getJSONArray("categroies").get(0).toString(), jsonObj.getString("value"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            con.disconnect();
        }
    }

    //This fetch method returns a list of strings with json format
    //based on a given url and a list of specifics* (using HTTP connection and a request method).
    //See the definition of a "specific" above.
    public JokesDTO fetch(String urlStr, List<Category> categories) {
        final ExecutorService executor = Executors.newCachedThreadPool();
        try {
            Queue<Future<JokeDTO>> queue = new ArrayBlockingQueue(categories.size());
            List<JokeDTO> res = new ArrayList();
            for (Category cat : categories) {
                Future<JokeDTO> future = executor.submit(() -> {
                    return fetch(urlStr + cat.getName());
                });
                queue.add(future);
            }
            while (!queue.isEmpty()) {
                Future<JokeDTO> specific = queue.poll();
                if (specific.isDone()) {
                    res.add(specific.get());
                } else {
                    queue.add(specific);
                }
            }
            return new JokesDTO(res, urlStr.split("/")[2]);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        } finally {
            executor.shutdown();
        }
    }

    
}
