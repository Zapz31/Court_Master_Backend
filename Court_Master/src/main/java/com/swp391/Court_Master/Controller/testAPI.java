package com.swp391.Court_Master.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.Repository.DetailClubRepository;
import com.swp391.Court_Master.Service.DetailPageService;
import com.swp391.Court_Master.dto.request.Respone.ImageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/test")
public class testAPI {

      @Autowired
    private DetailClubRepository detailClubRepository;

    @Autowired
    private DetailPageService detailPageService;

    @GetMapping("/all")
    public String allAccess() {
        return "Hahahahahahahahaa";
    }

    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('USER_CUSTOMER')") /*or hasAuthority('USER_COURT_MANAGER')*/
    public String getMethodName() {
        return " chi co ta moi la dang chi ton";
    }
    
    @GetMapping("/courtmanager")
    @PreAuthorize("hasAuthority('USER_COURT_MANAGER')")
    public String getCourtManagerAccess() {
        return "tai sao phai lam vua";
    }

    @GetMapping("/images/{clubId}")
    public ResponseEntity<List<ImageResponseDTO>> getMethodName(@PathVariable ("clubId") String clubId) {
        List<ImageResponseDTO> list = detailClubRepository.getAllImageName(clubId);
        return ResponseEntity.ok().body(list);
    }
    
    @GetMapping("/timeprices/{clubId}")
    public ResponseEntity<List<TimeFramePricingServiceDTO>> getTimePrices(@PathVariable("clubId") String clubId) {
        List<TimeFramePricingServiceDTO> list = detailPageService.getTimeFramePricingService(clubId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/test3/{clubId}")
    public ResponseEntity<List<TimeFrame>> getAll(@PathVariable("clubId") String clubId) {
        List<TimeFrame> list = detailPageService.getTimeFrames(clubId);
        return ResponseEntity.ok().body(list);
    }
    




    
}
