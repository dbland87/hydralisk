package io.proxylabs.hydralisk.models;

/**
 * Created by dbland on 4/17/17.
 */
public class User {
    private int id;
    private String name;
    private int bag_id;

    public User(int id, String name, int bag_id){
        this.id = id;
        this.name = name;
        this.bag_id = bag_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBag_id() {
        return bag_id;
    }

    public void setBag_id(int bag_id) {
        this.bag_id = bag_id;
    }
}
