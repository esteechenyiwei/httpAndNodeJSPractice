import java.util.*;
import java.io.*;
import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
 Use this class as a test to make sure that your Java code can communicate
 with your Node Express server.

 This code assumes that your Node Express server is running on the same computer
 as this program, is using port 3000 (the default port), and has a "/test" endpoint
 that sends back a JSON object with a "message" field, as in the starter code
 we distributed for this assignment.
*/

public class Test {

    public static void main(String[] args) {
        try {
            // create an object to represent the connection to a
            // web server on port 3000 on this computer
            URL url = new URL("http://localhost:3000/test");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
            conn.setRequestMethod("GET"); 

            // open connection and send HTTP request
            conn.connect(); 

            // now the response comes back
            int responsecode = conn.getResponseCode();
            // make sure the response has "200 OK" as the status
            if (responsecode != 200) {
                System.out.println("Unexpected status code: " + responsecode);
            } else {
                // made it here, we got a good response, let's read it
                Scanner in = new Scanner(url.openStream());
                while (in.hasNext()) {
                    // read the next line of the body of the response
                    String line = in.nextLine();
                    // the rest of this code assumes that the body contains JSON
                    JSONParser parser = new JSONParser();
                    JSONObject data = (JSONObject) parser.parse(line);
                    // read the "message" field from the JSON object
                    String message = (String)data.get("message");
                    // if using the provided Node Express starter code,
                    // this should print "It works!"
                    System.out.println("Message: " + message);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}