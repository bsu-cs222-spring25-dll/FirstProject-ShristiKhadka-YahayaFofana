package edu.bsu.cs;

public class Revision
{
    private final String username;
    private final String timestamp;

    //constructor
    public Revision(String username, String timestamp){
        this.username = username;
        this.timestamp = timestamp;
    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getTimestamp() {
        return timestamp;
    }


}
