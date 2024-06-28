package com.swp391.Court_Master.Entities;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Court {
    private String courtId;
    private String courtName;
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clubId;
    
    public Court(String courtId, String courtName, String status, String clubId) {
        this.courtId = courtId;
        this.courtName = courtName;
        this.status = status;
        this.clubId = clubId;
    }

    
    public Court(String courtId, String courtName, String status) {
        this.courtId = courtId;
        this.courtName = courtName;
        this.status = status;
    }


    public Court() {
    }


    public String getCourtId() {
        return courtId;
    }
    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }
    public String getCourtName() {
        return courtName;
    }
    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getClubId() {
        return clubId;
    }
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    
}
