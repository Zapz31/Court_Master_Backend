package com.swp391.Court_Master.Entities;

public class PricingService {
    private String dateOfWeek;
    private int flexible;
    private int fixed;
    private int oneTimePlay;
    private String timeFrameId;

    
    
    public PricingService() {
    }
    public PricingService(String dateOfWeek, int flexible, int fixed, int oneTimePlay) {
        this.dateOfWeek = dateOfWeek;
        this.flexible = flexible;
        this.fixed = fixed;
        this.oneTimePlay = oneTimePlay;
    }
    
    public PricingService(String dateOfWeek, int flexible, int fixed, int oneTimePlay, String timeFrameId) {
        this.dateOfWeek = dateOfWeek;
        this.flexible = flexible;
        this.fixed = fixed;
        this.oneTimePlay = oneTimePlay;
        this.timeFrameId = timeFrameId;
    }
    
    public String getDateOfWeek() {
        return dateOfWeek;
    }
    public void setDateOfWeek(String dateOfWeek) {
        this.dateOfWeek = dateOfWeek;
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
    public String getTimeFrameId() {
        return timeFrameId;
    }
    public void setTimeFrameId(String timeFrameId) {
        this.timeFrameId = timeFrameId;
    }

    
}
