package edu.upenn.cis350.hw3.data;

import java.util.Date;

public class Person {
    private String id;
    private String status;
    private long lastUpdated;
    
    public Person(String id, String status, long date) {
        this.id = id;
        this.status = status;
        this.lastUpdated = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public String toString() {
        return "Person ID " + id + " reported as " + status + " as of " + (new Date(lastUpdated));
    }
}
