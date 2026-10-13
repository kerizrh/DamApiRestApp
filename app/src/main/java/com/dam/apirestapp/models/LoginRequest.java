package com.dam.apirestapp.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("user")
    private String user;

    @SerializedName("pass")
    private String pass;

    public LoginRequest(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
