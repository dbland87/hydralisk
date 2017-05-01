package io.proxylabs.spawning_pool.models;

/**
 * Created by dbland on 4/20/17.
 */
public class Unit {
    private int id;
    private String name;
    private String type;

    public Unit(int id, String name, String type){
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
