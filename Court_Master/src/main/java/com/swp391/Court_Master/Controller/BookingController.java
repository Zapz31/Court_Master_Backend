package com.swp391.Court_Master.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.swp391.Court_Master.Service.BookingService;
import com.swp391.Court_Master.dto.request.Request.CheckValidBooking;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
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

    @PostMapping("/valid-timeframe-check")
    public ResponseEntity<List<MessageResponse>> checkTimeFrameBookingValid(@RequestBody CheckValidBooking checkValidBooking) {
        List<MessageResponse> invalidMessage = bookingService.checkValidBookingSlot(checkValidBooking.getClubId(), checkValidBooking.getPerSlotRequestDTOs());
        
        return ResponseEntity.ok().body(invalidMessage);
    }

    
}
