package com.example.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by JoshuaMabry on 11/6/16.
 */

public class Account {

    @SerializedName("email")
    private String email;

    @SerializedName("fullname")
    private String fullName;

    @SerializedName("avatarbase64")
    private String avatarBase64;

    @SerializedName("apikey")
    private String apiKey;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    @SerializedName("expiration")
    private Date expiration;

    @SerializedName("granttype")
    private String grantType;

    @SerializedName("username")
    private String userName;

    public Account(){

    }

    public Account(String avatarBase64, String apiKey, String fullName, String email, String password) {
        this.email = email;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.apiKey = apiKey;
        this.password = password;
    }

//    public Account(String grantType, String fullName, String password) {
//        this.grantType = grantType;
//        this.fullName = fullName;
//        this.password = password;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
