package com.swp391.Court_Master.dto.request.Respone.DashBoardResponse;

public class TotalCustomerInformation {
    private int TotalCustomer;
    private int percentVariation;
    public TotalCustomerInformation() {
    }
    public TotalCustomerInformation(int totalCustomer, int percentVariation) {
        TotalCustomer = totalCustomer;
        this.percentVariation = percentVariation;
    }
    public int getTotalCustomer() {
        return TotalCustomer;
    }
    public void setTotalCustomer(int totalCustomer) {
        TotalCustomer = totalCustomer;
    }
    public int getPercentVariation() {
        return percentVariation;
    }
    public void setPercentVariation(int percentVariation) {
        this.percentVariation = percentVariation;
    }

    
}
