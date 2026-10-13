package com.dam.apirestapp.models;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("code")
    private Integer code;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private Boolean status;

    public Product() {
    }

    public Product(Integer code, String name, Boolean status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
