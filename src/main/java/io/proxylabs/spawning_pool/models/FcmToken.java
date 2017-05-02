package io.proxylabs.spawning_pool.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dbland on 5/2/17.
 */
public class FcmToken {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("user")
    @Expose
    private Integer user;

    public FcmToken(Integer id, String body, Integer user){
        this.id = id;
        this.body = body;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

}
