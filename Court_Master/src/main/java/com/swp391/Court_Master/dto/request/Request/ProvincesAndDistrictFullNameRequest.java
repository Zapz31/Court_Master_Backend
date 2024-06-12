package com.swp391.Court_Master.dto.request.Request;

public class ProvincesAndDistrictFullNameRequest {
    private String provinceFullName;
    private String districtFullName;

    
    public ProvincesAndDistrictFullNameRequest() {
    }

    
    public ProvincesAndDistrictFullNameRequest(String provinceFullName, String districtFullName) {
        this.provinceFullName = provinceFullName;
        this.districtFullName = districtFullName;
    }


    public String getProvinceFullName() {
        return provinceFullName;
    }
    public void setProvinceFullName(String provinceFullName) {
        this.provinceFullName = provinceFullName;
    }
    public String getDistrictFullName() {
        return districtFullName;
    }
    public void setDistrictFullName(String districtFullName) {
        this.districtFullName = districtFullName;
    }

    

   
    
}
