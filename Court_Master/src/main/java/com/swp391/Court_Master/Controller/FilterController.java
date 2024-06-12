package com.swp391.Court_Master.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Service.AddressService;
import com.swp391.Court_Master.dto.request.Request.ProvinceFullNameRequest;
import com.swp391.Court_Master.dto.request.Request.ProvincesAndDistrictFullNameRequest;
import com.swp391.Court_Master.dto.request.Respone.DistrictFullNameResponse;
import com.swp391.Court_Master.dto.request.Respone.ProvincesFullNameResponse;
import com.swp391.Court_Master.dto.request.Respone.WardsFullNameResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/filter")
public class FilterController {
    @Autowired
    AddressService addressService;

    @GetMapping("/address/provinces")
    public ResponseEntity<List<ProvincesFullNameResponse>> getAllProvincesFullName() {
        List<ProvincesFullNameResponse> list = addressService.getAllProvincesByFullName();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/address/districts")
    public ResponseEntity<List<DistrictFullNameResponse>> getAllProvincesFullName(@RequestParam("fullName") String fullName) throws UnsupportedEncodingException {
        String decodedParam = URLDecoder.decode(fullName, "UTF-8");
        List<DistrictFullNameResponse> list = addressService.getAllDistrictsFullName(decodedParam);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/address/wards")
    public ResponseEntity<List<WardsFullNameResponse>> getAllProvincesFullName(@RequestBody ProvincesAndDistrictFullNameRequest provincesAndDistrictFullNameRequest) {
        List<WardsFullNameResponse> list = addressService.getAllWardsFullName(provincesAndDistrictFullNameRequest.getProvinceFullName(), provincesAndDistrictFullNameRequest.getDistrictFullName());
        return ResponseEntity.ok().body(list);  
    }
    


    
    
    


}
