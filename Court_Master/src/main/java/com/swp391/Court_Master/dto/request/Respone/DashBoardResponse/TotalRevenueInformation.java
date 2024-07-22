package com.swp391.Court_Master.dto.request.Respone.DashBoardResponse;

public class TotalRevenueInformation {
    private int totalRevenue;
    private int percentVariation;
    public TotalRevenueInformation() {
    }
    public TotalRevenueInformation(int totalRevenue, int percentVariation) {
        this.totalRevenue = totalRevenue;
        this.percentVariation = percentVariation;
    }
    public int getTotalRevenue() {
        return totalRevenue;
    }
    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    public int getPercentVariation() {
        return percentVariation;
    }
    public void setPercentVariation(int percentVariation) {
        this.percentVariation = percentVariation;
    }

    
}
