package com.example.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JoshuaMabry on 11/9/16.
 */

public class User {
    //Declarations
    @SerializedName("MessageCount")
    private Integer messageCount;
    @SerializedName("AvatarBase64")
    private String avatarBase64;
    @SerializedName("SenderId")
    private String senderName;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("CaughtUserId")
    private String caughtUserId;

    @SerializedName ("RadiusInMeters")
    private float radiusInMeters;

    //These aren't needed until gold.
    @SerializedName("Message")
    private String message;
    @SerializedName("ConversationId")
    private Integer conversationId;
    @SerializedName("RecipientId")
    private String recipientId;
    @SerializedName("LastMessage")
    private String lastMessage;
    @SerializedName("Created")
    private String created;
    @SerializedName("RecipientAvatarBase64")
    private String recipientAvatarBase64;
    @SerializedName("SenderAvatarBase64")
    private String senderAvatarBase64;



    //Constructors
    public User (){
    }
    public User(String userId, String userName, String avatarBase64, double longitude, double latitude, String created) {
        this.userId = userId;
        this.userName = userName;
        this.avatarBase64 = avatarBase64;
        this.longitude = longitude;
        this.latitude = latitude;
        this.created = created;
    }
    public User(String userId, String userName, String created, String avatarBase64) {
        this.userId = userId;
        this.userName = userName;
        this.created = created;
        this.avatarBase64 = avatarBase64;
    }
    public User(Integer conversationId, String recipientId, String lastMessage, String created, Integer messageCount,
                String avatarBase64, String senderName, String recipientAvatarBase64, String senderAvatarBase64) {
        this.conversationId = conversationId;
        this.recipientId = recipientId;
        this.lastMessage = lastMessage;
        this.created = created;
        this.messageCount = messageCount;
        this.avatarBase64 = avatarBase64;
        this.senderName = senderName;
        this.recipientAvatarBase64 = recipientAvatarBase64;
        this.senderAvatarBase64 = senderAvatarBase64;
    }
    //Getters and Setters
    public User (double Longitude, double Latitude) {
        this.latitude = Latitude;
        this.longitude = Longitude;
    }
    public User (String CaughtUserId, Integer RadiusInMeters) {
        this.caughtUserId = CaughtUserId;
        this.radiusInMeters = RadiusInMeters;
    }
    public User (String RecipientId, String Message) {
        this.recipientId = RecipientId;
        this.message = Message;
    }

    public User(String caughtUserId, float radiusInMeters) {
        this.caughtUserId = caughtUserId;
        this.radiusInMeters = radiusInMeters;
    }

    public String getCaughtUserId() {
        return caughtUserId;
    }

    public void setCaughtUserId(String caughtUserId) {
        this.caughtUserId = caughtUserId;
    }

    public float getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(float radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public Integer getMessageCount() {
        return messageCount;
    }
    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }
    public String getAvatarBase64() {
        return avatarBase64;
    }
    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getConversationId() {
        return conversationId;
    }
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }
    public String getRecipientId() {
        return recipientId;
    }
    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
    public String getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public String getRecipientAvatarBase64() {
        return recipientAvatarBase64;
    }
    public void setRecipientAvatarBase64(String recipientAvatarBase64) {
        this.recipientAvatarBase64 = recipientAvatarBase64;
    }
    public String getSenderAvatarBase64() {
        return senderAvatarBase64;
    }
    public void setSenderAvatarBase64(String senderAvatarBase64) {
        this.senderAvatarBase64 = senderAvatarBase64;
    }
}
