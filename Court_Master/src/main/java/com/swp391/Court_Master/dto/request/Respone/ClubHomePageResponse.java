package com.swp391.Court_Master.dto.request.Respone;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ClubHomePageResponse {
    private String clubId;
    private String clubName;
    private String clubAddress;
    private int AveragePrice;
    private String clubImageName;
    private String courtManagerPhone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clubImageBase64;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private byte[] clubImageByte;

    

    

    public ClubHomePageResponse(String clubId, String clubName, String clubAddress, int averagePrice,
            String clubImageName, String courtManagerPhone) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubAddress = clubAddress;
        AveragePrice = averagePrice;
        this.clubImageName = clubImageName;
        this.courtManagerPhone = courtManagerPhone;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubAddress() {
        return clubAddress;
    }

    public void setClubAddress(String clubAddress) {
        this.clubAddress = clubAddress;
    }

    

    public String getClubImageName() {
        return clubImageName;
    }

    public void setClubImageName(String clubImageName) {
        this.clubImageName = clubImageName;
    }

    public String getClubImageBase64() {
        return clubImageBase64;
    }

    public void setClubImageBase64(String clubImageBase64) {
        this.clubImageBase64 = clubImageBase64;
    }



    public String getClubId() {
        return clubId;
    }



    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public int getAveragePrice() {
        return AveragePrice;
    }

    public void setAveragePrice(int averagePrice) {
        AveragePrice = averagePrice;
    }



    public byte[] getClubImageByte() {
        return clubImageByte;
    }



    public void setClubImageByte(byte[] clubImageByte) {
        this.clubImageByte = clubImageByte;
    }

    public String getCourtManagerPhone() {
        return courtManagerPhone;
    }

    public void setCourtManagerPhone(String courtManagerPhone) {
        this.courtManagerPhone = courtManagerPhone;
    }

    
}
