package com.swp391.Court_Master.dto.request.Request;

import java.sql.Time;
import java.time.LocalTime;

public class FilterRequstDTO {
    private String nameOrUnitNumber;
    private String province;
    private String district;
    private String ward;
    private LocalTime openedTime;
    private LocalTime hoursOfExpect;

    public FilterRequstDTO(String nameOrUnitNumber, String province, String district, String ward, LocalTime openedTime,
            LocalTime hoursOfExpect) {
        this.nameOrUnitNumber = nameOrUnitNumber;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.openedTime = openedTime;
        this.hoursOfExpect = hoursOfExpect;
    }

    public FilterRequstDTO() {
    }

    public String getNameOrUnitNumber() {
        return nameOrUnitNumber;
    }

    public void setNameOrUnitNumber(String nameOrUnitNumber) {
        this.nameOrUnitNumber = nameOrUnitNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public LocalTime getOpenedTime() {
        return openedTime;
    }

    public void setOpenedTime(LocalTime openedTime) {
        this.openedTime = openedTime;
    }

    public LocalTime getHoursOfExpect() {
        return hoursOfExpect;
    }

    public void setHoursOfExpect(LocalTime hoursOfExpect) {
        this.hoursOfExpect = hoursOfExpect;
    }

}
