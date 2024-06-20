package com.swp391.Court_Master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Service.BookingService;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.BookingSlotResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.UnpaidBookingInfo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/unpaidbookings")
    public ResponseEntity<UnpaidBookingInfo> viewAllUnPaidBooking(@RequestBody List<PricePerSlotRequestDTO> list) {
        UnpaidBookingInfo unpaidBookingInfo = bookingService.buildUnpaidBookingInfo(list);
        
        return ResponseEntity.ok().body(unpaidBookingInfo);
    }
}
