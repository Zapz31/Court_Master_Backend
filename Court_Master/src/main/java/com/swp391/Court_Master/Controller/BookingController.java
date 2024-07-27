package com.swp391.Court_Master.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.Entities.PaymentDetail;
import com.swp391.Court_Master.Entities.PaymentUpdateBookingSchedule;
import com.swp391.Court_Master.Entities.PlayableTimePayment;
import com.swp391.Court_Master.Repository.BookingRepository;
import com.swp391.Court_Master.Service.BookingService;
import com.swp391.Court_Master.Utils.TimeUtils;
import com.swp391.Court_Master.dto.request.Request.BookingPaymentRequestDTO;
import com.swp391.Court_Master.dto.request.Request.CheckValidBooking;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.BookingScheduleHistory;
import com.swp391.Court_Master.dto.request.Respone.BookingSlotResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
import com.swp391.Court_Master.dto.request.Respone.UnpaidBookingInfo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/unpaidbookings")
    public ResponseEntity<UnpaidBookingInfo> viewAllUnPaidBooking(@RequestBody List<PricePerSlotRequestDTO> list) {
        UnpaidBookingInfo unpaidBookingInfo = bookingService.buildUnpaidBookingInfo(list);

        return ResponseEntity.ok().body(unpaidBookingInfo);
    }

    @PostMapping("/valid-timeframe-check")
    public ResponseEntity<List<MessageResponse>> checkTimeFrameBookingValid(
            @RequestBody CheckValidBooking checkValidBooking) {
        List<MessageResponse> invalidMessage = bookingService.checkValidBookingSlot(checkValidBooking.getClubId(),
                checkValidBooking.getPerSlotRequestDTOs());

        return ResponseEntity.ok().body(invalidMessage);
    }

    @GetMapping("/get-all-bookedlist-{clubId}")
    public ResponseEntity<List<BookedDTO>> getAllBookedSlotByClubId(@PathVariable("clubId") String clubId) {
        List<BookedDTO> list = bookingService.getAllBookingSlot(clubId);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/payment-handle")
    // @PreAuthorize("hasAuthority('USER_CUSTOMER')")
    public ResponseEntity<MessageResponse> handlePayment(
            @RequestBody BookingPaymentRequestDTO bookingPaymentRequestDTO) {
        // chi book 1 ngay
        if (bookingPaymentRequestDTO.getBookingSchedule().getEndDate() == null) {
            bookingPaymentRequestDTO.getBookingSchedule()
                    .setEndDate(bookingPaymentRequestDTO.getBookingSchedule().getStartDate());
        }
        MessageResponse messageResponse = new MessageResponse("Payment success");
        if (bookingPaymentRequestDTO.getBookingSchedule().getStartDate()
                .isAfter(bookingPaymentRequestDTO.getBookingSchedule().getEndDate())) {
            messageResponse.setMassage("Invalid start and end dates");
        } else {
            List<BookedDTO> duplicateBookingList = bookingService.getDuplicateBookingSlotList(
                    bookingPaymentRequestDTO.getBookingSchedule().getBookingSlotResponseDTOs());
            if (duplicateBookingList.size() != 0) {
                StringBuilder invalidMess = new StringBuilder();
                for (BookedDTO bookedDTO : duplicateBookingList) {
                    invalidMess.append(bookedDTO.getStartTime() + " - " + bookedDTO.getEndTime() + ","
                            + bookedDTO.getBookingDate() + "," + bookedDTO.getCourtName()).append("|");
                }
                invalidMess.append(bookingPaymentRequestDTO.getCourtManagerPhone());
                messageResponse.setMassage(invalidMess.toString());
            } else {
                // Ham insert trong day (Chac chan thanh toan se thanh cong moi di vao luong
                // nay)
                // Khi nguoi dung dat lich ngay hoac fixed
                if (!bookingPaymentRequestDTO.getBookingSchedule().getScheduleType().equals("Flexible")) {
                    messageResponse = bookingService.excutePaymentTransaction(bookingPaymentRequestDTO);
                 }// else {
                //     // Khi nguoi dung dat lich kieu flexible
                //     // Kiem tra xem nguoi dung co du gio choi hay khong
                //     if (bookingRepository.isEnoughTime(
                //             bookingPaymentRequestDTO.getBookingSchedule().getTotalPlayingTime(),
                //             bookingPaymentRequestDTO.getBookingSchedule().getCustomerId(),
                //             bookingPaymentRequestDTO.getClubId())) {
                //         messageResponse = bookingService.excutePaymentTransaction(bookingPaymentRequestDTO);
                //     } else {
                //         messageResponse.setMassage("Số giờ chơi đăng ký của bạn không đủ để thực hiện giao dịch");
                //     }
                // }
            }
        }
        return ResponseEntity.ok().body(messageResponse);
    }

    // Thanh toan cho kieu flexible 
    @PostMapping("/flexible-payment")
    public ResponseEntity<MessageResponse> executeFlexiblePayment(@RequestBody BookingPaymentRequestDTO bookingPaymentRequestDTO) {
        // chi book 1 ngay
        if (bookingPaymentRequestDTO.getBookingSchedule().getEndDate() == null) {
            bookingPaymentRequestDTO.getBookingSchedule()
                    .setEndDate(bookingPaymentRequestDTO.getBookingSchedule().getStartDate());
        }
        MessageResponse messageResponse = new MessageResponse("Payment success");
        if (bookingPaymentRequestDTO.getBookingSchedule().getStartDate()
                .isAfter(bookingPaymentRequestDTO.getBookingSchedule().getEndDate())) {
            messageResponse.setMassage("Invalid start and end dates");
        } else {
            List<BookingSlotResponseDTO> duplicateBsRequest = bookingService.getDupBookingSlotRequest(bookingPaymentRequestDTO.getBookingSchedule().getBookingSlotResponseDTOs());
            if(!duplicateBsRequest.isEmpty()){
                messageResponse.setMassage("duplicate");
            } else {
                // Khong bi trung.
                if (bookingRepository.isEnoughTime(
                            bookingPaymentRequestDTO.getBookingSchedule().getTotalPlayingTime(),
                            bookingPaymentRequestDTO.getBookingSchedule().getCustomerId(),
                            bookingPaymentRequestDTO.getClubId())) {
                        messageResponse = bookingService.excutePaymentTransaction(bookingPaymentRequestDTO);
                    } else {
                        messageResponse.setMassage("Not enough time");
                    }
            }
        }
        return ResponseEntity.ok().body(messageResponse);
    }
    


    @GetMapping("/history/schedule")
    public List<BookingScheduleHistory> bookingScheduleHistories(@RequestParam("customerId") String customerId) {
        List<BookingScheduleHistory> list = bookingRepository.getBookingScheduleHistories(customerId);
        for (BookingScheduleHistory bookingScheduleHistory : list) {
            if (bookingScheduleHistory.getScheduleType().equals("Flexible")) {
                bookingScheduleHistory.setTotalPlayingTimeString(
                        TimeUtils.convertMinutestoTimeFormat(bookingScheduleHistory.getTotalPlayingTime()));
            }
        }
        return list;

    }

    @GetMapping("/history/slots")
    public List<BookedDTO> bookingSlotHistories(@RequestParam("scheduleId") String scheduleId) {
        return bookingRepository.getBookingSlotsHistories(scheduleId);
    }

    @GetMapping("/history/payment-detail")
    public List<PaymentDetail> bookingPaymentDetailHistories(@RequestParam("scheduleId") String scheduleId,
            @RequestParam("scheduleType") String scheduleType) {
        return bookingService.getPaymentDetailsHistory(scheduleId, scheduleType);
    }

    @PostMapping("/payment-play-time")
    public ResponseEntity<MessageResponse> paymentPlayableTime(@RequestBody PlayableTimePayment entity) {
        MessageResponse mess = bookingService.executePlayTimePayment(entity);
        return ResponseEntity.ok().body(mess);
    }

    @PostMapping("/payment-installment")
    public ResponseEntity<MessageResponse> paymentInstallment(
            @RequestBody PaymentUpdateBookingSchedule paymentUpdateBookingSchedule) {
        boolean isPaymentSuccess = bookingService.isUpdateBookingSchStatus(paymentUpdateBookingSchedule);
        MessageResponse messageResponse = new MessageResponse("");
        if (isPaymentSuccess) {
            messageResponse.setMassage("Payment success");
        } else {
            messageResponse.setMassage("Payment failed");
        }

        return ResponseEntity.ok().body(messageResponse);
    }

    @PostMapping("/getDuplicateBookingSlot")
    public ResponseEntity<List<BookedDTO>> getDuplicateBookingSlotList(
            @RequestBody List<BookingSlotResponseDTO> pricePerSlotRequestDTOList) {
        List<BookedDTO> list = bookingService.getDuplicateBookingSlotList(pricePerSlotRequestDTOList);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/total-hours-calculated-price")
    public ResponseEntity<HashMap<String, Integer>> getTotalCalculated(@RequestParam("clubId") String clubId, @RequestParam("totalHours") String totalHoursString){
        int totalHOurs = Integer.parseInt(totalHoursString);
        int calculatedPrice = bookingService.calculatorPriceForPlayingTime(clubId, totalHOurs);
        HashMap<String, Integer> results = new HashMap<>();
        results.put("totalPrice", calculatedPrice);
        return ResponseEntity.ok().body(results);
    }
    
}
