package edu.upenn.cis350.hw3.datamanagement;

import edu.upenn.cis350.hw3.data.Person;

import java.io.IOException;

public interface DataSource {
    
    public Person[] get(String[] ids) throws IOException;

}
