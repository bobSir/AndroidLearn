package com.bobLearn.orm;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * created by cly on 2019-05-02
 */
@Entity
public class User {
    @Id
    private String id;
    private String name;
    private String token;

    @Generated(hash = 191491598)
    public User(String id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "id = " + id + " --  name = " + name + " -- token = " + token;
    }
}
