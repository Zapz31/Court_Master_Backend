package com.swp391.Court_Master.Entities;

public class BadmintonClub {
    private String badmintonClubId;
    private String badmintonClubName;
    private String addressId;
    private String description;
    private String badmintonClubStatus;
    private String courtManagerId;
    public BadmintonClub() {
    }
    public BadmintonClub(String badmintonClubName, String addressId, String description, String badmintonClubStatus,
            String courtManagerId) {
        this.badmintonClubName = badmintonClubName;
        this.addressId = addressId;
        this.description = description;
        this.badmintonClubStatus = badmintonClubStatus;
        this.courtManagerId = courtManagerId;
    }
    public String getBadmintonClubId() {
        return badmintonClubId;
    }
    public void setBadmintonClubId(String badmintonClubId) {
        this.badmintonClubId = badmintonClubId;
    }
    public String getBadmintonClubName() {
        return badmintonClubName;
    }
    public void setBadmintonClubName(String badmintonClubName) {
        this.badmintonClubName = badmintonClubName;
    }
    public String getAddressId() {
        return addressId;
    }
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getBadmintonClubStatus() {
        return badmintonClubStatus;
    }
    public void setBadmintonClubStatus(String badmintonClubStatus) {
        this.badmintonClubStatus = badmintonClubStatus;
    }
    public String getCourtManagerId() {
        return courtManagerId;
    }
    public void setCourtManagerId(String courtManagerId) {
        this.courtManagerId = courtManagerId;
    }
    
}
