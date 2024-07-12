package com.swp391.Court_Master.dto.request.Request;

import java.util.List;

import com.swp391.Court_Master.Entities.Address;
import com.swp391.Court_Master.Entities.BadmintonClub;
import com.swp391.Court_Master.Entities.BadmintonClubImage;
import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.Entities.TimeFrame;

public class ClubRegisterDTO {
    private BadmintonClub badmintonClub;
    private BadmintonClubImage avatar;
    private List<BadmintonClubImage> badmintonClubImageList;
    private Address address;
    private List<TimeFrame> timeFramesList;
    private List<Court> courtList;
    public ClubRegisterDTO() {
    }
    
    
    public ClubRegisterDTO(BadmintonClub badmintonClub, BadmintonClubImage avatar,
            List<BadmintonClubImage> badmintonClubImageList, Address address, List<TimeFrame> timeFramesList,
            List<Court> courtList) {
        this.badmintonClub = badmintonClub;
        this.avatar = avatar;
        this.badmintonClubImageList = badmintonClubImageList;
        this.address = address;
        this.timeFramesList = timeFramesList;
        this.courtList = courtList;
    }


    public BadmintonClub getBadmintonClub() {
        return badmintonClub;
    }
    public void setBadmintonClub(BadmintonClub badmintonClub) {
        this.badmintonClub = badmintonClub;
    }
    public List<BadmintonClubImage> getBadmintonClubImageList() {
        return badmintonClubImageList;
    }
    public void setBadmintonClubImageList(List<BadmintonClubImage> badmintonClubImageList) {
        this.badmintonClubImageList = badmintonClubImageList;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public List<TimeFrame> getTimeFramesList() {
        return timeFramesList;
    }
    public void setTimeFramesList(List<TimeFrame> timeFramesList) {
        this.timeFramesList = timeFramesList;
    }
    public List<Court> getCourtList() {
        return courtList;
    }
    public void setCourtList(List<Court> courtList) {
        this.courtList = courtList;
    }
    public BadmintonClubImage getAvatar() {
        return avatar;
    }
    public void setAvatar(BadmintonClubImage avatar) {
        this.avatar = avatar;
    }
    
}
