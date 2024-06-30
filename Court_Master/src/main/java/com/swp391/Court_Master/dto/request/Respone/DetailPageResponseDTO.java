package com.swp391.Court_Master.dto.request.Respone;

import java.time.LocalTime;
import java.util.List;

import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.Entities.TimeFrame;

public class DetailPageResponseDTO {
    private List<ImageResponseDTO> imageNames;
    private String clubAddress;
    private String clubName;
    private String clubId;
    private String clubDescription;
    private List<TimeFrame> timeFrame;
    private List<CustomerFeedback> feedbacks;
    private String courtManagerPhone;
    private List<Court> courtList;
    private String customerPlayableTime;
        
    public DetailPageResponseDTO(String clubAddress, String clubName, String clubId, String clubDescription, String courtManagerPhone) {
        this.clubAddress = clubAddress;
        this.clubName = clubName;
        this.clubId = clubId;
        this.clubDescription = clubDescription;
        this.courtManagerPhone = courtManagerPhone;
    }

    
    

    public DetailPageResponseDTO() {
    }


    public String getAddress() {
        return clubAddress;
    }



    public void setAddress(String address) {
        this.clubAddress = address;
    }



    public String getClubName() {
        return clubName;
    }



    public void setClubName(String clubName) {
        this.clubName = clubName;
    }



    public String getClubId() {
        return clubId;
    }



    public void setClubId(String clubId) {
        this.clubId = clubId;
    }



    public String getClubDescription() {
        return clubDescription;
    }



    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }



    public List<ImageResponseDTO> getImageNames() {
        return imageNames;
    }


    public void setImageNames(List<ImageResponseDTO> imageNames) {
        this.imageNames = imageNames;
    }


    public String getClubAddress() {
        return clubAddress;
    }


    public void setClubAddress(String clubAddress) {
        this.clubAddress = clubAddress;
    }


    public List<TimeFrame> getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(List<TimeFrame> timeFrame) {
        this.timeFrame = timeFrame;
    }

    public List<CustomerFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<CustomerFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
    public String getCourtManagerPhone() {
        return courtManagerPhone;
    }

    public void setCourtManagerPhone(String courtManagerPhone) {
        this.courtManagerPhone = courtManagerPhone;
    }

    public List<Court> getCourtList() {
        return courtList;
    }

    public void setCourtList(List<Court> courtList) {
        this.courtList = courtList;
    }
    public String getCustomerPlayableTime() {
        return customerPlayableTime;
    }
    public void setCustomerPlayableTime(String customerPlayableTime) {
        this.customerPlayableTime = customerPlayableTime;
    }
    

}
