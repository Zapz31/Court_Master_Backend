package com.swp391.Court_Master.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.Repository.BookingRepository;
import com.swp391.Court_Master.Repository.DetailClubRepository;
import com.swp391.Court_Master.Service.BookingService;
import com.swp391.Court_Master.Service.DetailPageService;
import com.swp391.Court_Master.dto.request.Request.BookingSlotRequest;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.ImageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/test")
public class testAPI {

      @Autowired
    private DetailClubRepository detailClubRepository;

    @Autowired
    private DetailPageService detailPageService;

    @Autowired 
    private BookingRepository bookingRepository;
    
    @Autowired
    private BookingService bookingService;

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

    @GetMapping("/test/gettfbycourt")
    public ResponseEntity<List<TimeFramePricingServiceDTO>> getAll(@RequestBody BookingSlotRequest bookingSlotRequest) {
        List<TimeFramePricingServiceDTO> list = bookingRepository.getClubTimeFramePricingByCourtId(bookingSlotRequest.getCourtId(), bookingSlotRequest.getStartBooking(), bookingSlotRequest.getEndBooking());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/getpriceperslot")
    public ResponseEntity<Integer> getAll(@RequestBody PricePerSlotRequestDTO pricePerSlotRequestDTO) {
        Integer price = bookingService.getPricePerSlot(pricePerSlotRequestDTO.getCourtId(), pricePerSlotRequestDTO.getStartBooking(), pricePerSlotRequestDTO.getEndBooking(), pricePerSlotRequestDTO.getBookingDate(), pricePerSlotRequestDTO.getBookingType());
        return ResponseEntity.ok().body(price);
    }
    
    // Test tinhs tong tien tat ca cac slot 
    @GetMapping("/getpriceallslot")
    public ResponseEntity<Integer> getPriceAllSlot(@RequestBody List<PricePerSlotRequestDTO> pricePerSlotRequestDTOList) {
        Integer price = bookingService.getAllBookingSLotTotalPrice(pricePerSlotRequestDTOList);
        return ResponseEntity.ok().body(price);
    }

    @GetMapping("/getTotalPlayTime")
    public ResponseEntity<String> getTotalHoursAllSlot(@RequestBody List<PricePerSlotRequestDTO> pricePerSlotRequestDTOList) {
        String playTimeTotal = bookingService.getTotalHoursAllSlot(pricePerSlotRequestDTOList);
        return ResponseEntity.ok().body(playTimeTotal);
    }





    



    




    
}
