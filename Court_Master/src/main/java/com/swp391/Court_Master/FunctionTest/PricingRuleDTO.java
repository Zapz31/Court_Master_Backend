package com.swp391.Court_Master.FunctionTest;

public class PricingRuleDTO {
    private String dayOfWeek;
    private int pricePerHours;
    private String scheduleTpye;
    private String timeFrameId;

    

    public PricingRuleDTO() {
    }
    public PricingRuleDTO(String dayOfWeek, int pricePerHours, String scheduleTpye, String timeFrameId) {
        this.dayOfWeek = dayOfWeek;
        this.pricePerHours = pricePerHours;
        this.scheduleTpye = scheduleTpye;
        this.timeFrameId = timeFrameId;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public int getPricePerHours() {
        return pricePerHours;
    }
    public void setPricePerHours(int pricePerHours) {
        this.pricePerHours = pricePerHours;
    }
    public String getScheduleTpye() {
        return scheduleTpye;
    }
    public void setScheduleTpye(String scheduleTpye) {
        this.scheduleTpye = scheduleTpye;
    }
    public String getTimeFrameId() {
        return timeFrameId;
    }
    public void setTimeFrameId(String timeFrameId) {
        this.timeFrameId = timeFrameId;
    }

    
}
