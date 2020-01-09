package facades;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author emilt
 */
public class ApiFacade {

    //This fetch method returns a string with json format
    //based on a given url (using HTTP connection and a request method).
    public String fetch(String urlStr) {
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
            return jsonStr;
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
    public String fetch(String urlStr, String specific) {
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
            return jsonStr;
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
    public List<String> fetch(String urlStr, List<String> specificList) {
        final ExecutorService executor = Executors.newCachedThreadPool();
        try {
            Queue<Future<String>> queue = new ArrayBlockingQueue(specificList.size());
            List<String> res = new ArrayList();
            for (String specifc : specificList) {
                Future<String> future = executor.submit(() -> {
                    return fetch(urlStr + specifc);
                });
                queue.add(future);
            }
            while (!queue.isEmpty()) {
                Future<String> specific = queue.poll();
                if (specific.isDone()) {
                    res.add(specific.get());
                } else {
                    queue.add(specific);
                }
            }
            return res;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        } finally {
            executor.shutdown();
        }
    }

}
