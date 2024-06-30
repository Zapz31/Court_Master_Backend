package com.swp391.Court_Master.Controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.Service.BookingService;
import com.swp391.Court_Master.dto.request.Request.BookingPaymentRequestDTO;
import com.swp391.Court_Master.dto.request.Request.CheckValidBooking;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
import com.swp391.Court_Master.dto.request.Respone.UnpaidBookingInfo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/get-all-bookedlist-{clubId}")
    public ResponseEntity<List<BookedDTO>> getAllBookedSlotByClubId(@PathVariable("clubId") String clubId) {
        List<BookedDTO> list = bookingService.getAllBookingSlot(clubId);
        return ResponseEntity.ok().body(list);
    }

    // @PostMapping("/payment-handle")
    // public ResponseEntity<BookingPaymentRequestDTO> handlePayment(@RequestBody BookingPaymentRequestDTO bookingPaymentRequestDTO){

    //     return ResponseEntity.ok().body(bookingPaymentRequestDTO);
    // } 

    @PostMapping("/payment-handle")
    // @PreAuthorize("hasAuthority('USER_CUSTOMER')")
    public ResponseEntity<MessageResponse> handlePayment(@RequestBody BookingPaymentRequestDTO bookingPaymentRequestDTO){
        MessageResponse messageResponse = new MessageResponse("Payment successfully");
        if(bookingPaymentRequestDTO.getBookingSchedule().getStartDate().isAfter(bookingPaymentRequestDTO.getBookingSchedule().getEndDate())){
            messageResponse.setMassage("Invalid start and end dates");
        } else {
            List<BookedDTO> duplicateBookingList = bookingService.getDuplicateBookingSlotList(bookingPaymentRequestDTO.getBookingSchedule().getBookingSlotResponseDTOs());
            if(duplicateBookingList.size()!=0){
                StringBuilder invalidMess = new StringBuilder("Your booking has overlapped with the following bookings:");
                for (BookedDTO bookedDTO : duplicateBookingList) {
                    invalidMess.append("\n").append(bookedDTO.getStartTime()+" - "+bookedDTO.getEndTime()+" on "+bookedDTO.getBookingDate()+" at "+bookedDTO.getCourtName());
                }
                messageResponse.setMassage(invalidMess.toString());
            } else {
                // Ham insert trong day (Chac chan thanh toan se thanh cong moi di vao luong nay)
                // Khi nguoi dung dat lich ngay hoac fixed
                if(!bookingPaymentRequestDTO.getBookingSchedule().getScheduleType().equals("Flexible")){
                    messageResponse = bookingService.excutePaymentTransaction(bookingPaymentRequestDTO);
                } else {
                    // Khi nguoi dung dat lich kieu flexible
                }
                
            }
        }
        return ResponseEntity.ok().body(messageResponse);
    } 

    
   
}
