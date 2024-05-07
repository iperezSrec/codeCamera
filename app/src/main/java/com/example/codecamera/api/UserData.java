package com.example.codecamera.api;

import com.google.gson.annotations.SerializedName;

public class UserData{
    @SerializedName("name")
    private String name;

    @SerializedName("role")
    private String role;

    @SerializedName("phId")
    private String phId;

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPhId() {
        return phId;
    }

}
