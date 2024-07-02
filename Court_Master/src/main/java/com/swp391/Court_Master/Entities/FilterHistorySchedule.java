package com.swp391.Court_Master.Entities;

import java.time.LocalDate;

public class FilterHistorySchedule {
    private String clubNameOrCMPhone;
    private String customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String scheduleType;
    
    public FilterHistorySchedule(String clubNameOrCMPhone, String customerId, LocalDate startDate, LocalDate endDate,
            String scheduleType) {
        this.clubNameOrCMPhone = clubNameOrCMPhone;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scheduleType = scheduleType;
    }
    public FilterHistorySchedule() {
    }
    public String getClubNameOrCMPhone() {
        return clubNameOrCMPhone;
    }
    public void setClubNameOrCMPhone(String clubNameOrCMPhone) {
        this.clubNameOrCMPhone = clubNameOrCMPhone;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public String getScheduleType() {
        return scheduleType;
    }
    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }
     

}
