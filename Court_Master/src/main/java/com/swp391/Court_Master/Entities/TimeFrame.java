package com.swp391.Court_Master.Entities;

import java.time.LocalTime;
import java.util.List;

public class TimeFrame {
    private String timeFrameId;
    private LocalTime starTime;
    private LocalTime endTime;
    private List<PricingService> PricingServiceList;

    
    public TimeFrame() {
    }

    public TimeFrame(String timeFrameId, LocalTime starTime, LocalTime endTime) {
        this.timeFrameId = timeFrameId;
        this.starTime = starTime;
        this.endTime = endTime;
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

    public List<PricingService> getPricingServiceList() {
        return PricingServiceList;
    }

    public void setPricingServiceList(List<PricingService> pricingServiceList) {
        PricingServiceList = pricingServiceList;
    }

    
}
