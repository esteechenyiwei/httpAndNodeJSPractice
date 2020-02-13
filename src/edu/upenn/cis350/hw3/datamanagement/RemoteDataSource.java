package edu.upenn.cis350.hw3.datamanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.util.Iterator;


import edu.upenn.cis350.hw3.data.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class RemoteDataSource implements DataSource {
    private String host;
    private int port;
    
    public RemoteDataSource() {
        // use Node Express defaults
        host = "localhost";
        port = 3000;
    }
    
    public RemoteDataSource(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /* IMPLEMENT THIS METHOD! */
    public Person[] get(String[] ids) {
        if (ids == null) {
            throw new IllegalArgumentException("null ids array");
        }
        if (ids.length == 0) {
            return new Person[0];
        }
        StringBuilder sb = new StringBuilder("http://" + host + ":" + port + "/get?");
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == null || ids[i].isEmpty()) {
                throw new IllegalArgumentException("null in ids array");
            }
            if (i == ids.length - 1) {
                sb.append("id=" + ids[i]);
            } else {
                sb.append("id=" + ids[i] + "&");
            }
        }
        String url = sb.toString();
        try {
            //assume we get the response as JSONObject
            JSONArray ja = getHTTPResponseHelper(url); //get it!
            if (ja.isEmpty()) {
                return new Person[0];
            }
            Iterator iter = ja.iterator();

            //get the size of the jsonarray and initiate an array
            int size = ja.size();
            Person[] persons = new Person[size];
            int ind = 0;
            //parse each jsonobject and load info into a Person
            while (iter.hasNext()) {
                JSONObject jo = (JSONObject) iter.next();
                String id = (String) jo.get("id");
                String status = (String) jo.get("status");
                long date = (long) jo.get("date");
                Person per = new Person(id, status, date);
                //add that Person to the answer array
                persons[ind] = per;
                ind++;
            }
            return persons;
        } catch (Exception e) {
            return new Person[0];
        }
    }

    public JSONArray getHTTPResponseHelper(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // open connection and send HTTP request
            conn.connect();
            // now the response comes back
            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                System.out.println("Unexpected status code: " + responsecode);
                conn.disconnect();
                return null;
            } else {
                Reader in = new BufferedReader(new InputStreamReader(url.openStream()));

                JSONParser jp = new JSONParser();
                JSONArray ja = (JSONArray) jp.parse(in);

                in.close();
                conn.disconnect();
                return ja;
            }
        } catch (IOException e) {
            //if there is exception in connecting to the url
            throw new IllegalStateException("can't connect to server");
        } catch (ParseException e) {
            //error parsing the json array
            return new JSONArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public static void main(String[] args) {
//        RemoteDataSource rds = new RemoteDataSource();
//        String[] ids = {"1234", "5678"};
//        Person[] persons = rds.get(ids);
//        for (int i = 0; i < persons.length; i++) {
//            System.out.println(persons[i].getId());
//            System.out.println(persons[i].getStatus());
//            System.out.println(persons[i].getLastUpdated());
//            System.out.println("\n");
//        }
//    }

}
