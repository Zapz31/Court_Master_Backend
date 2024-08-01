package com.swp391.Court_Master.Entities;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Club {
    private String clubName;
    private String description;
    private String clubId;

    public Club() {

    }

    public Club(String clubName, String description, String clubId) {
        this.clubName = clubName;
        this.description = description;
        this.clubId = clubId;
    }

    // Getter and Setter for clubName
    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for clubId
    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

}
