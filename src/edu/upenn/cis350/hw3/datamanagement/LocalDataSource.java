package edu.upenn.cis350.hw3.datamanagement;

import edu.upenn.cis350.hw3.data.Person;

import java.io.IOException;

public class LocalDataSource implements DataSource {
    //testing function

    public LocalDataSource() {

    }

    public Person[] get(String[] ids) throws IOException {
        Person a = new Person("1234", "shouldnotseethis", Long.parseLong("1581471567705"));
        Person b = new Person("5678", "shouldnotseethis", Long.parseLong("1581471567705"));
        Person[] ret = {a, b};
        //return new Person[0];
        return new Person[0];
    }
}
