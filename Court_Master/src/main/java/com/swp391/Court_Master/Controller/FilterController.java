package com.swp391.Court_Master.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp391.Court_Master.Service.AddressService;
import com.swp391.Court_Master.Service.FilterService;
import com.swp391.Court_Master.dto.request.Request.FilterRequstDTO;
import com.swp391.Court_Master.dto.request.Request.ProvinceFullNameRequest;
import com.swp391.Court_Master.dto.request.Request.ProvincesAndDistrictFullNameRequest;
import com.swp391.Court_Master.dto.request.Respone.ClubHomePageResponse;
import com.swp391.Court_Master.dto.request.Respone.DistrictFullNameResponse;
import com.swp391.Court_Master.dto.request.Respone.ProvincesFullNameResponse;
import com.swp391.Court_Master.dto.request.Respone.WardsFullNameResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/filter")
public class FilterController {
    @Autowired
    AddressService addressService;

    @Autowired
    private FilterService filterService;

    private static final String IMAGE_DIRECTORY = "D:/FPTU/Semester V/SWP391/courtmasterimage/club-image/";

    @GetMapping("/address/provinces")
    public ResponseEntity<List<ProvincesFullNameResponse>> getAllProvincesFullName() {
        List<ProvincesFullNameResponse> list = addressService.getAllProvincesByFullName();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/address/districts")
    public ResponseEntity<List<DistrictFullNameResponse>> getAllProvincesFullName(
            @RequestParam("fullName") String fullName) throws UnsupportedEncodingException {
        String decodedParam = URLDecoder.decode(fullName, "UTF-8");
        List<DistrictFullNameResponse> list = addressService.getAllDistrictsFullName(decodedParam);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/address/wards")
    public ResponseEntity<List<WardsFullNameResponse>> getAllWardsFullName(@RequestBody String body)
            throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
        String decodedBody = URLDecoder.decode(body, "UTF-8");
        System.out.println(decodedBody);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(decodedBody);
        String provinceFullName = jsonNode.get("provinceFullName").asText();
        String districtFullName = jsonNode.get("districtFullName").asText();
        List<WardsFullNameResponse> list = addressService.getAllWardsFullName(provinceFullName, districtFullName);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/getClubs")
    public ResponseEntity<List<ClubHomePageResponse>> postMethodName(@RequestBody FilterRequstDTO filterRequstDTO) throws IOException {

        List<ClubHomePageResponse> list = null;
        String nameOrUnitNumber = filterRequstDTO.getNameOrUnitNumber();
        String province = filterRequstDTO.getProvince();
        String district = filterRequstDTO.getDistrict();
        String ward = filterRequstDTO.getWard();
        LocalTime openedTime = filterRequstDTO.getOpenedTime();
        LocalTime hoursOfExpect = filterRequstDTO.getHoursOfExpect();
        // th1: openedTime va hoursOfExpect null or empty
        /*
         * th1.1
         */

        if (hoursOfExpect == null && openedTime == null) {
            list = filterService.filterNoTime(nameOrUnitNumber, province, district, ward);
            if(!list.isEmpty()){
                for (ClubHomePageResponse clubHomePageResponse : list) {
                    String imageFileName = clubHomePageResponse.getClubImageName();
                    File file = new File(IMAGE_DIRECTORY + imageFileName);
                    Path path = Paths.get(file.getAbsolutePath());
                    byte[] bytes = Files.readAllBytes(path);
                    String base64Image = Base64.getEncoder().encodeToString(bytes);
                    clubHomePageResponse.setClubImageBase64(base64Image);
                    
    
                }
            }

            /*
             * TH2: filter full fields
            */
            
        } else {
            list = filterService.filterFullField(nameOrUnitNumber, province, district, ward, openedTime, hoursOfExpect);
            if(!list.isEmpty()){
                for (ClubHomePageResponse clubHomePageResponse : list) {
                    String imageFileName = clubHomePageResponse.getClubImageName();
                    File file = new File(IMAGE_DIRECTORY + imageFileName);
                    Path path = Paths.get(file.getAbsolutePath());
                    byte[] bytes = Files.readAllBytes(path);
                    String base64Image = Base64.getEncoder().encodeToString(bytes);
                    clubHomePageResponse.setClubImageBase64(base64Image);
                      
                }
            }
        }

        return ResponseEntity.ok().body(list);
    }

}
