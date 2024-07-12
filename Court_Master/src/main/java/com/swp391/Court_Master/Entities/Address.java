package com.swp391.Court_Master.Entities;

public class Address {
    private String addressId;
    private String unitNumber;
    private String ward;
    private String district;
    private String province;
    public Address() {
    }
    
    public Address(String addressId, String unitNumber, String ward, String district, String province) {
        this.addressId = addressId;
        this.unitNumber = unitNumber;
        this.ward = ward;
        this.district = district;
        this.province = province;
    }

    public Address(String unitNumber, String ward, String district, String province) {
        this.unitNumber = unitNumber;
        this.ward = ward;
        this.district = district;
        this.province = province;
    }
    
    public String getUnitNumber() {
        return unitNumber;
    }
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }
    public String getWard() {
        return ward;
    }
    public void setWard(String ward) {
        this.ward = ward;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    
}
