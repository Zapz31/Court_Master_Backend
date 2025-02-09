package com.swp391.Court_Master.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.Repository.DetailClubRepository;
import com.swp391.Court_Master.Service.ClubHomePageService;
import com.swp391.Court_Master.Service.DetailPageService;
import com.swp391.Court_Master.dto.request.Respone.ClubHomePageResponse;
import com.swp391.Court_Master.dto.request.Respone.DetailPageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.ImageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.ProvincesFullNameResponse;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/clubs")
public class BadmintonClubController {
    
    @Autowired
    ClubHomePageService clubHomePageService;

    @Autowired
    private DetailClubRepository detailClubRepository;

    @Autowired
    private DetailPageService detailPageService;

    @GetMapping("/clubsView")
    public ResponseEntity<List<ClubHomePageResponse>> getAllProvincesFullName() throws IOException {
        File clubImage = new File("club-image");
        String clubimageAbsolutePath = clubImage.getAbsolutePath() + "/";
        List<ClubHomePageResponse> list = clubHomePageService.getAllClubHomePage();
        for (ClubHomePageResponse clubHomePageResponse : list) {
            String imageFileName = clubHomePageResponse.getClubImageName();
            File file = new File(clubimageAbsolutePath + imageFileName);
            Path path = Paths.get(file.getAbsolutePath());
            byte[] bytes = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            clubHomePageResponse.setClubImageBase64(base64Image);
            
        }
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/detail/{clubId}/{userId}")
    // @PreAuthorize("hasAuthority('USER_CUSTOMER') or hasAuthority('USER_COURT_MANAGER')")
    public ResponseEntity<DetailPageResponseDTO> getDetailPageResponse(@PathVariable("clubId") String clubId, @PathVariable("userId") String userId) throws IOException {
        DetailPageResponseDTO detailPageResponseDTO = detailPageService.detailPageBuild(clubId, userId);
        return ResponseEntity.ok().body(detailPageResponseDTO);
    }

}
