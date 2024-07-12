package com.swp391.Court_Master.Entities;

public class BadmintonClubImage {
    private String imageUrl;
    private int isMainAvatar;
    private String badmintonClubId;
    public BadmintonClubImage() {
    }
    
    public BadmintonClubImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BadmintonClubImage(String imageUrl, int isMainAvatar, String badmintonClubId) {
        this.imageUrl = imageUrl;
        this.isMainAvatar = isMainAvatar;
        this.badmintonClubId = badmintonClubId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getIsMainAvatar() {
        return isMainAvatar;
    }
    public void setIsMainAvatar(int isMainAvatar) {
        this.isMainAvatar = isMainAvatar;
    }
    public String getBadmintonClubId() {
        return badmintonClubId;
    }
    public void setBadmintonClubId(String badmintonClubId) {
        this.badmintonClubId = badmintonClubId;
    }
    
}
