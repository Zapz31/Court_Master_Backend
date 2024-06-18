package com.swp391.Court_Master.dto.request.Respone;

import java.time.LocalTime;

public class TimeFramePricingServiceDTO {
    private String clubId;
    private String timeFrameId;
    private LocalTime starTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private int flexible;
    private int fixed;
    private int oneTimePlay;
    public TimeFramePricingServiceDTO(String clubId, String timeFrameId, LocalTime starTime, LocalTime endTime, String dayOfWeek,
            int flexible, int fixed, int oneTimePlay) {
        this.timeFrameId = timeFrameId;
        this.starTime = starTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.flexible = flexible;
        this.fixed = fixed;
        this.oneTimePlay = oneTimePlay;
        this.clubId = clubId;
    }
    
    public String getTimeFrameId() {
        return timeFrameId;
    }
    public void setTimeFrameId(String timeFrameId) {
        this.timeFrameId = timeFrameId;
    }
    public LocalTime getStarTime() {
        return starTime;
    }
    public void setStarTime(LocalTime starTime) {
        this.starTime = starTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public int getFlexible() {
        return flexible;
    }
    public void setFlexible(int flexible) {
        this.flexible = flexible;
    }
    public int getFixed() {
        return fixed;
    }
    public void setFixed(int fixed) {
        this.fixed = fixed;
    }
    public int getOneTimePlay() {
        return oneTimePlay;
    }
    public void setOneTimePlay(int oneTimePlay) {
        this.oneTimePlay = oneTimePlay;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    
}
