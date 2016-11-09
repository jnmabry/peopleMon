package com.example.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by JoshuaMabry on 11/6/16.
 */

public class Account {

    @SerializedName("Email")
    private String email;

    @SerializedName("FullName")
    private String fullName;

    @SerializedName("AvatarBase64")
    private String avatarBase64;

    @SerializedName("apikey")
    private String apiKey;

    @SerializedName("password")
    private String password;

    @SerializedName("access_token")
    private String access_token;

    @SerializedName(".expires")
    private Date expires;

    @SerializedName("granttype")
    private String grantType;

    @SerializedName("username")
    private String userName;

    @SerializedName("Id")
    private String id;

    @SerializedName("HasRegistered")
    private Boolean hasRegistered;

    @SerializedName("LoginProvider")
    private String loginProvider;

    @SerializedName("LastCheckInLongitude")
    private Double lastCheckInLongitude;

    @SerializedName("LastCheckInLatitude")
    private Double lastCheckInLatitude;

    @SerializedName("LastCheckInDateTime")
    private Date lastCheckInDateTime;

    public Account(){

    }

    public Account(String avatarBase64, String apiKey, String fullName, String email, String password) {
        this.email = email;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.apiKey = apiKey;
        this.password = password;
    }

    public Account(String id, String email, Boolean hasRegistered, String loginProvider, String fullName,
                   String avatarBase64, Double lastCheckInLongitude, Double lastCheckInLatitude, Date lastCheckInDateTime) {
        this.id = id;
        this.email = email;
        this.hasRegistered = hasRegistered;
        this.loginProvider = loginProvider;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.lastCheckInLongitude = lastCheckInLongitude;
        this.lastCheckInLatitude = lastCheckInLatitude;
        this.lastCheckInDateTime = lastCheckInDateTime;
    }

    public Account(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
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
        return access_token;
    }

    public void setToken(String token) {
        this.access_token = access_token;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHasRegistered() {
        return hasRegistered;
    }

    public void setHasRegistered(Boolean hasRegistered) {
        this.hasRegistered = hasRegistered;
    }

    public String getLoginProvider() {
        return loginProvider;
    }

    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }

    public Double getLastCheckInLongitude() {
        return lastCheckInLongitude;
    }

    public void setLastCheckInLongitude(Double lastCheckInLongitude) {
        this.lastCheckInLongitude = lastCheckInLongitude;
    }

    public Double getLastCheckInLatitude() {
        return lastCheckInLatitude;
    }

    public void setLastCheckInLatitude(Double lastCheckInLatitude) {
        this.lastCheckInLatitude = lastCheckInLatitude;
    }

    public Date getLastCheckInDateTime() {
        return lastCheckInDateTime;
    }

    public void setLastCheckInDateTime(Date lastCheckInDateTime) {
        this.lastCheckInDateTime = lastCheckInDateTime;
    }
}
