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
import com.swp391.Court_Master.Entities.BookingSchedule;
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
            messageResponse = bookingService.excutePaymentTransaction(bookingPaymentRequestDTO);
        }
        return ResponseEntity.ok().body(messageResponse);
    }

    // Thanh toan cho kieu flexible
    @PostMapping("/flexible-payment")
    public ResponseEntity<MessageResponse> executeFlexiblePayment(
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
            List<BookingSlotResponseDTO> duplicateBsRequest = bookingService.getDupBookingSlotRequest(
                    bookingPaymentRequestDTO.getBookingSchedule().getBookingSlotResponseDTOs(), 0);
            if (!duplicateBsRequest.isEmpty()) {
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
    public ResponseEntity<List<BookingSlotResponseDTO>> getDuplicateBookingSlotList(
            @RequestBody List<BookingSlotResponseDTO> pricePerSlotRequestDTOList) {
        List<BookingSlotResponseDTO> list = bookingService.getDupBookingSlotRequest(pricePerSlotRequestDTOList, 0);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/total-hours-calculated-price")
    public ResponseEntity<HashMap<String, Integer>> getTotalCalculated(@RequestParam("clubId") String clubId,
            @RequestParam("totalHours") String totalHoursString) {
        int totalHOurs = Integer.parseInt(totalHoursString);
        int calculatedPrice = bookingService.calculatorPriceForPlayingTime(clubId, totalHOurs);
        HashMap<String, Integer> results = new HashMap<>();
        results.put("totalPrice", calculatedPrice);
        return ResponseEntity.ok().body(results);
    }

    // them booking schedule va booking slot tam thoi truoc khi nguoi dung thuc su
    // thanh toan
    @PostMapping("/insert-temp-bookingscheduleslot")
    public ResponseEntity<MessageResponse> insertTempBookingSlot(@RequestBody BookingSchedule bookingSchedule) {
        boolean isDup = true;
        List<BookingSlotResponseDTO> duplicateBsRequest = new ArrayList<>();
        MessageResponse mess = new MessageResponse("");
        // Check trung voi cac booking co is_temp = 0
        duplicateBsRequest = bookingService.getDupBookingSlotRequest(bookingSchedule.getBookingSlotResponseDTOs(), 0);
        if (!duplicateBsRequest.isEmpty()) {
            isDup = false;
        } else {
            // Check trung voi cac booking co is_temp = 1
            duplicateBsRequest = bookingService.getDupBookingSlotRequest(bookingSchedule.getBookingSlotResponseDTOs(),
                    1);
            if (!duplicateBsRequest.isEmpty()) {
                isDup = false;
            }
        }

        if (!isDup) {
            mess.setMassage("dup");
        } else {
            // Neu khong trung thi insert booking schedule temp o day
            // chi book 1 ngay
            if (bookingSchedule.getEndDate() == null) {
                bookingSchedule.setEndDate(bookingSchedule.getStartDate());
            }
            mess.setMassage(bookingService.getBookingScheduleAndSlotIdTemp(bookingSchedule));
        }
        return ResponseEntity.ok().body(mess);

    }

    // Xoa cac bookingSchedule va bookingSlot tam thoi khi nguoi dung ko thanh toan vnPay
    @PostMapping("/remove-temp-booking")
    public ResponseEntity<MessageResponse> removeTempBooking(@RequestBody HashMap<String,String> entity) {
        String scheduleAndSlotIdTemp = entity.get("tempIdStr");
        bookingService.removeTempBooking(scheduleAndSlotIdTemp);
        return ResponseEntity.ok().body(new MessageResponse("success"));
    }

    @PostMapping("/get-available-time")
    public ResponseEntity<List<String>> getAvailableBookingTime(@RequestBody BookingSlotResponseDTO entity) {
        List<String> results = bookingService.getAllowedCourtChangeTime(entity);
        return ResponseEntity.ok().body(results);
    }
    
}
