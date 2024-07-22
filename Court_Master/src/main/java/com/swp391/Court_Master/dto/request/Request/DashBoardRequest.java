package com.swp391.Court_Master.dto.request.Request;

public class DashBoardRequest {
    String clubId;
    int year;
    int month;
    int previousMonth;
    int previousYear;
    public DashBoardRequest() {
    }
    
    public DashBoardRequest(String clubId, int year, int month, int previousMonth, int previousYear) {
        this.clubId = clubId;
        this.year = year;
        this.month = month;
        this.previousMonth = previousMonth;
        this.previousYear = previousYear;
    }

    public String getClubId() {
        return clubId;
    }
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getPreviousMonth() {
        return previousMonth;
    }

    public void setPreviousMonth(int previousMonth) {
        this.previousMonth = previousMonth;
    }

    public int getPreviousYear() {
        return previousYear;
    }

    public void setPreviousYear(int previousYear) {
        this.previousYear = previousYear;
    }
    
}
