package edu.upenn.cis350.hw3.processor;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.upenn.cis350.hw3.data.Person;
import edu.upenn.cis350.hw3.datamanagement.DataSource;


public class StatusProcessor {
    // DO NOT CHANGE THE VISIBILITY OF THESE FIELDS!
    protected DataSource dataSource;
    protected Map<String, Person> map = new HashMap<>();
    
    public StatusProcessor(DataSource ds) {
        dataSource = ds;
    }
    
    /* IMPLEMENT THIS METHOD! */
    public Person[] get(String[] ids) {
        if (dataSource == null) {
            throw new IllegalStateException("null datasource");
        }
        if (ids == null) {
            throw new IllegalArgumentException("null array");
        }
        if (ids.length == 0) {
            return new Person[0];
        }
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == null || ids[i].isEmpty()) {
                return new Person[0];
            }
        }

        try {
            Person[] returnedPersons = dataSource.get(ids);

            if (returnedPersons.length == 0) {
                return getWhenDSIsProblematic(ids);
            }
            int size = returnedPersons.length;
            Person[] ans = new Person[size];

            for (int i = 0; i < ids.length; i++) {
                Person returnedPerson = returnedPersons[i];
                String returnedID = returnedPerson.getId();
                long returnedLastUpdated = returnedPerson.getLastUpdated();

                if (map.containsKey(returnedID)) {
                    Person thatPersonInMap = map.get(returnedID);
                    long mapLastUpdate = thatPersonInMap.getLastUpdated();
                    if (returnedLastUpdated >= mapLastUpdate) {
                        ans[i] = returnedPerson;
                        map.put(returnedID, returnedPerson);
                    } else {
                        ans[i] = thatPersonInMap;
                    }
                } else {

                    map.put(returnedID, returnedPerson);
                    ans[i] = returnedPerson;
                }
            }
            return ans;
        } catch (Exception e) {

            return getWhenDSIsProblematic(ids);
        }
    }

    public Person[] getWhenDSIsProblematic(String[] ids) {
        int size = ids.length;
        Person[] ans = new Person[size];
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            if (map.containsKey(id)) {
                ans[i] = map.get(id);
            } else {
                Date d = new Date();
                Person newPerson = new Person(id, "unknown", d.getTime());
                ans[i] = newPerson;
            }
        }
        return ans;
    }

    public void printMap() {
        for (Map.Entry<String, Person> e : map.entrySet()) {
            System.out.println(e.getKey());
            System.out.println(e.getValue().getId() + " " +
                    e.getValue().getStatus() + " " + e.getValue().getLastUpdated());

        }
    }

//    public static void main(String[] args) throws IOException {
//        LocalDataSource lds = new LocalDataSource();
//        RemoteDataSource rds = new RemoteDataSource();
//        StatusProcessor sp = new StatusProcessor(rds);
//        String[] ids = {"1234", "0", "5678"};
//
//        //for testing of updating map, first introduce some persons into the map
//        sp.map.put("1234", new Person("1234" , "in the future", Long.parseLong("1681525800355")));
//        sp.map.put("0", new Person("0" , "in the future", Long.parseLong("1681525800355")));
//        //for testing of updating map, first introduce some persons into the map
//        Person[] personsinds = rds.get(ids);
//        System.out.println("now is data source persons" + "\n");
//        for (int i = 0; i < personsinds.length; i++) {
//            System.out.println(personsinds[i].getId());
//            System.out.println(personsinds[i].getStatus());
//            System.out.println(personsinds[i].getLastUpdated());
//            System.out.println("\n");
//        }
//
//        Person[] processordata = sp.get(ids);
//        System.out.println("print map:");
//        sp.printMap();
//        System.out.println("last time 1234 is updated: 1581524056012" + "\n");
//        Date date = new Date();
//        System.out.println("time is now" + date.getTime() + "\n");
//        System.out.println("now is processor output answer" + "\n");
//        for (int i = 0; i < processordata.length; i++) {
//            System.out.println(processordata[i].getId());
//            System.out.println(processordata[i].getStatus());
//            System.out.println(processordata[i].getLastUpdated());
//            System.out.println("\n");
//        }
//
//    }
}
