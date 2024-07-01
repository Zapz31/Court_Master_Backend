package com.swp391.Court_Master.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.Entities.Invoice;
import com.swp391.Court_Master.Entities.PaymentDetail;
import com.swp391.Court_Master.Entities.PaymentUpdateBookingSchedule;
import com.swp391.Court_Master.Entities.PlayableTimePayment;
import com.swp391.Court_Master.Entities.PricingService;
import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.Repository.BookingRepository;
import com.swp391.Court_Master.Utils.TimeUtils;
import com.swp391.Court_Master.dto.request.Request.BookingPaymentRequestDTO;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.BookingSlotResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;
import com.swp391.Court_Master.dto.request.Respone.UnpaidBookingInfo;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    // TINH TIEN MOT SLOT BOOKING
    public int getPricePerSlot(String courtId, LocalTime startBooking, LocalTime endBooking, LocalDate bookingDate,
            String bookingType) {
        List<TimeFramePricingServiceDTO> tfpsList = bookingRepository.getClubTimeFramePricingByCourtId(courtId,
                startBooking, endBooking);
        String dayOfWeekUp = bookingDate.getDayOfWeek().toString().substring(0, 3);
        String dayOfWeek = Character.toUpperCase(dayOfWeekUp.charAt(0)) + dayOfWeekUp.substring(1).toLowerCase();
        int totalPrice = 0;
        List<TimeFrame> timeFrames = getTimeFrameForPricing(tfpsList);

        for (TimeFrame tf : timeFrames) {
            if ((startBooking.equals(tf.getStarTime())
                    || startBooking.isAfter(tf.getStarTime()) && startBooking.isBefore(tf.getEndTime()))
                    && (endBooking.equals(tf.getEndTime()) || endBooking.isBefore(tf.getEndTime()))) {
                // PricingService selectedPricingService = new PricingService();
                int pricePerHours = -1;
                for (PricingService ps : tf.getPricingServiceList()) {
                    if (dayOfWeek.equals(ps.getDateOfWeek())) {
                        // selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                double timeDiffBooking = calculateTimeDifference(startBooking, endBooking);
                totalPrice = (int) (pricePerHours * timeDiffBooking);

            } else if ((startBooking.equals(tf.getStarTime())
                    || startBooking.isAfter(tf.getStarTime()) && startBooking.isBefore(tf.getEndTime()))
                    && (endBooking.isAfter(tf.getEndTime()))) {
                int pricePerHours = -1;
                for (PricingService ps : tf.getPricingServiceList()) {

                    if (dayOfWeek.equals(ps.getDateOfWeek())) {
                        // selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                double timeDiffBooking = calculateTimeDifference(startBooking, tf.getEndTime());
                totalPrice += (int) (timeDiffBooking * pricePerHours);

            } else if (startBooking.isBefore(tf.getStarTime()) && (endBooking.isAfter(tf.getStarTime())
                    && (endBooking.equals(tf.getEndTime()) || endBooking.isBefore(tf.getEndTime())))) {
                int pricePerHours = -1;
                for (PricingService ps : tf.getPricingServiceList()) {

                    if (dayOfWeek.equals(ps.getDateOfWeek())) {
                        // selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                double timeDiffBooking = calculateTimeDifference(tf.getStarTime(), endBooking);
                totalPrice += (int) (timeDiffBooking * pricePerHours);
            } else if (startBooking.isBefore(tf.getStarTime()) && endBooking.isAfter(tf.getEndTime())) {
                int pricePerHours = -1;
                for (PricingService ps : tf.getPricingServiceList()) {

                    if (dayOfWeek.equals(ps.getDateOfWeek())) {
                        // selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                double timeDiffBooking = calculateTimeDifference(tf.getStarTime(), tf.getEndTime());
                totalPrice += (int) (timeDiffBooking * pricePerHours);
            }
        }

        return totalPrice;

    }

    private double calculateTimeDifference(LocalTime t1, LocalTime t2) {
        Duration duration = Duration.between(t1, t2);
        double minutes = duration.toMinutes();
        return minutes / 60;
    }

    private int getPricePerHour(PricingService tfps, String bookingType) {
        switch (bookingType.toLowerCase()) {
            case "flexible":
                return tfps.getFlexible();
            case "fixed":
                return tfps.getFixed();
            case "one_time_play":
                return tfps.getOneTimePlay();
            default:
                return 0;
        }
    }

    private List<TimeFrame> getTimeFrameForPricing(List<TimeFramePricingServiceDTO> tfpsList) {
        List<TimeFrame> timeFrames = new ArrayList<>();
        String tfMark = "";
        Collections.sort(tfpsList, new Comparator<TimeFramePricingServiceDTO>() {

            @Override
            public int compare(TimeFramePricingServiceDTO o1, TimeFramePricingServiceDTO o2) {
                return o1.getTimeFrameId().compareTo(o2.getTimeFrameId());
            }

        });

        for (TimeFramePricingServiceDTO tfps : tfpsList) {

            if (tfMark.isEmpty() || (!tfMark.equals(tfps.getTimeFrameId()))) {
                TimeFrame tf = new TimeFrame();
                tf.setTimeFrameId(tfps.getTimeFrameId());
                tf.setStarTime(tfps.getStarTime());
                tf.setEndTime(tfps.getEndTime());
                tfMark = tfps.getTimeFrameId();
                timeFrames.add(tf);
            }
        }

        for (TimeFrame timeFrame : timeFrames) {
            List<PricingService> pricingServices = new ArrayList<>();
            for (TimeFramePricingServiceDTO tfps : tfpsList) {
                if (timeFrame.getTimeFrameId().equals(tfps.getTimeFrameId())) {
                    PricingService ps = new PricingService(tfps.getDayOfWeek(), tfps.getFlexible(), tfps.getFixed(),
                            tfps.getOneTimePlay());
                    pricingServices.add(ps);
                }

            }
            timeFrame.setPricingServiceList(pricingServices);

        }

        return timeFrames;
    }

    /*
     * Tinh tien tat ca cac slot slot booking
     */
    public Integer getAllBookingSLotTotalPrice(List<PricePerSlotRequestDTO> bookingSlotList) {
        int totalPrice = 0;
        for (PricePerSlotRequestDTO perSlotRequestDTO : bookingSlotList) {
            int pricePerSlot = getPricePerSlot(perSlotRequestDTO.getCourtId(), perSlotRequestDTO.getStartBooking(),
                    perSlotRequestDTO.getEndBooking(), perSlotRequestDTO.getBookingDate(),
                    perSlotRequestDTO.getBookingType());
            totalPrice += pricePerSlot;
        }
        return totalPrice;
    }

    /*
     * Tinh tong so gio choi
     */
    public String getTotalHoursAllSlot(List<PricePerSlotRequestDTO> bookingSlotList) {
        Duration totalDuration = Duration.ZERO;
        for (PricePerSlotRequestDTO perSlotRequestDTO : bookingSlotList) {
            Duration duration = Duration.between(perSlotRequestDTO.getStartBooking(),
                    perSlotRequestDTO.getEndBooking());
            totalDuration = totalDuration.plus(duration);

        }
        return formatDuration(totalDuration);
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return String.format("%02d:%02d", hours, minutes);
    }

    /*
     * Tinh tien va thoi gian cho moi slot trong mot list slot chua tra tien
     */
    public List<BookingSlotResponseDTO> getAllUnpaidBooking(List<PricePerSlotRequestDTO> pricePerSlotRequestDTOs) {
        List<BookingSlotResponseDTO> unpaidBookingList = new ArrayList<>();
        for (PricePerSlotRequestDTO perSlotRequestDTO : pricePerSlotRequestDTOs) {
            if(perSlotRequestDTO.getStartBooking().isAfter(perSlotRequestDTO.getEndBooking())){
                LocalTime temp = perSlotRequestDTO.getStartBooking();
                perSlotRequestDTO.setStartBooking(perSlotRequestDTO.getEndBooking());
                perSlotRequestDTO.setEndBooking(temp);
            }
            Integer price = getPricePerSlot(perSlotRequestDTO.getCourtId(),
                    perSlotRequestDTO.getStartBooking(),
                    perSlotRequestDTO.getEndBooking(),
                    perSlotRequestDTO.getBookingDate(),
                    perSlotRequestDTO.getBookingType());
            Duration duration = Duration.between(perSlotRequestDTO.getStartBooking(),
                    perSlotRequestDTO.getEndBooking());
            String playTime = formatDuration(duration);
            unpaidBookingList.add(new BookingSlotResponseDTO(perSlotRequestDTO.getCourtId(),
                    perSlotRequestDTO.getStartBooking(),
                    perSlotRequestDTO.getEndBooking(),
                    perSlotRequestDTO.getBookingDate(),
                    perSlotRequestDTO.getBookingType(),
                    playTime, price));

        }
        return unpaidBookingList;
    }

    /*
     * Tong hop thong tin xem truoc cua cac booking slot, tong gia tien va gio choi
     * de hien thi len cai bang ben trai man hinh dat lich
     */
    public UnpaidBookingInfo buildUnpaidBookingInfo(List<PricePerSlotRequestDTO> pricePerSlotRequestDTOs) {
        List<BookingSlotResponseDTO> list = getAllUnpaidBooking(pricePerSlotRequestDTOs);
        Integer totalPrice = getAllBookingSLotTotalPrice(pricePerSlotRequestDTOs);
        String totalHours = getTotalHoursAllSlot(pricePerSlotRequestDTOs);

        UnpaidBookingInfo unpaidBookingInfo = new UnpaidBookingInfo(totalPrice, totalHours, list);
        return unpaidBookingInfo;

    }

    /*
     * Kiem tra xem cac booking slot co trung nhau hay khong, ham tra ve danh sach
     * cac booking slot bi trung
     */
    public List<BookedDTO> getDuplicateBookingSlotList(List<BookingSlotResponseDTO> pricePerSlotRequestDTOs) {
        List<BookedDTO> allBookingSlotsByCourtId = bookingRepository.getBookedList(pricePerSlotRequestDTOs);
        List<BookedDTO> duplicateBookingSlotList = new ArrayList<>();
        for (BookingSlotResponseDTO pricePerSlotRequestDTO : pricePerSlotRequestDTOs) {
            int flag = 0;
            for (BookedDTO bookedDTO : allBookingSlotsByCourtId) {
                if ((flag == 0) && (!pricePerSlotRequestDTO.getCourtId().equals(bookedDTO.getCourtId()))) {
                    continue;
                } else if (pricePerSlotRequestDTO.getCourtId().equals(bookedDTO.getCourtId())) {
                    flag = 1;
                    if (pricePerSlotRequestDTO.getBookingDate().equals(bookedDTO.getBookingDate())) {
                        if ((pricePerSlotRequestDTO.getStartBooking().equals(bookedDTO.getStartTime())
                                || (pricePerSlotRequestDTO.getStartBooking().isAfter(bookedDTO.getStartTime())))
                                && (pricePerSlotRequestDTO.getEndBooking().equals(bookedDTO.getEndTime())
                                        || pricePerSlotRequestDTO.getEndBooking().isBefore(bookedDTO.getEndTime()))) {
                            duplicateBookingSlotList.add(bookedDTO);
                        } else if (pricePerSlotRequestDTO.getStartBooking().isBefore(bookedDTO.getStartTime())
                                && (pricePerSlotRequestDTO.getEndBooking().equals(bookedDTO.getEndTime())
                                        || pricePerSlotRequestDTO.getEndBooking().isBefore(bookedDTO.getEndTime())
                                                && pricePerSlotRequestDTO.getEndBooking()
                                                        .isAfter(bookedDTO.getStartTime()))) {
                            duplicateBookingSlotList.add(bookedDTO);
                        } else if ((pricePerSlotRequestDTO.getStartBooking().equals(bookedDTO.getStartTime())
                                || pricePerSlotRequestDTO.getStartBooking().isAfter(bookedDTO.getStartTime())
                                        && pricePerSlotRequestDTO.getStartBooking().isBefore(bookedDTO.getEndTime()))
                                && pricePerSlotRequestDTO.getEndBooking().isAfter(bookedDTO.getEndTime())) {
                            duplicateBookingSlotList.add(bookedDTO);
                        } else if (pricePerSlotRequestDTO.getStartBooking().isBefore(bookedDTO.getStartTime())
                                && pricePerSlotRequestDTO.getEndBooking().isAfter(bookedDTO.getEndTime())) {
                            duplicateBookingSlotList.add(bookedDTO);
                        }
                    }
                } else if ((!pricePerSlotRequestDTO.getCourtId().equals(bookedDTO.getCourtId())) && (flag == 1)) {
                    break;
                }
            }
        }
        return duplicateBookingSlotList;

    }

    /*
     * Kiem tra tinh hop le cua thoi gian dat san so voi time frame
     */
    public List<MessageResponse> checkValidBookingSlot(String clubId, List<PricePerSlotRequestDTO> pricePerSlotRequestDTOs) {
        List<MessageResponse> invalidMessages = new ArrayList<>();
        List<TimeFrame> timeFrames = bookingRepository.getTimeFrameByClubId(clubId);
        

        for (PricePerSlotRequestDTO perSlotRequestDTO : pricePerSlotRequestDTOs) {
            List<TimeFrame> rangeTimeFrames = new ArrayList<>();
            Boolean isValidStartTime = false;
            Boolean isValidEndTime = false;
            Boolean isContinous = true;
            for (TimeFrame tf : timeFrames) {
                if ((perSlotRequestDTO.getStartBooking().equals(tf.getStarTime())
                        || perSlotRequestDTO.getStartBooking().isAfter(tf.getStarTime()))
                        && (perSlotRequestDTO.getStartBooking().equals(tf.getEndTime())
                                || perSlotRequestDTO.getStartBooking().isBefore(tf.getEndTime()))) {
                    isValidStartTime = true;
                    rangeTimeFrames.add(tf);
                }
                if ((perSlotRequestDTO.getEndBooking().equals(tf.getStarTime())
                        || perSlotRequestDTO.getEndBooking().isAfter(tf.getStarTime()))
                        && (perSlotRequestDTO.getEndBooking().equals(tf.getEndTime())
                                || perSlotRequestDTO.getEndBooking().isBefore(tf.getEndTime()))) {
                    isValidEndTime = true;
                    rangeTimeFrames.add(tf);
                }
                if(perSlotRequestDTO.getStartBooking().isBefore(tf.getStarTime()) && perSlotRequestDTO.getEndBooking().isAfter(tf.getEndTime())){
                    rangeTimeFrames.add(tf);
                }
            }
            List<TimeFrame> uniqueTimeFrames = new ArrayList<>();
            for(TimeFrame tf: rangeTimeFrames){
                if(!uniqueTimeFrames.contains(tf)){
                    uniqueTimeFrames.add(tf);
                }
            }
            if((uniqueTimeFrames.size() > 1)){
                TimeFrame preTime = uniqueTimeFrames.get(0);
                for(int i = 1; i < uniqueTimeFrames.size(); i++){
                    if(!preTime.getEndTime().equals(uniqueTimeFrames.get(i).getStarTime())){
                        isContinous = false;
                    }
                    preTime = uniqueTimeFrames.get(i);
                }
            }
            if(isValidStartTime == false || isValidEndTime == false || isContinous == false){
                invalidMessages.add(new MessageResponse("The booking from " + perSlotRequestDTO.getStartBooking() + " to "+perSlotRequestDTO.getEndBooking()+" is invalid. You can go back schedule to check." ));
            }
            
        }
        return invalidMessages;

    }

    /*
     * Lay danh sach tat ca cac booking_slot de hien thi ra man hinh dat lich (onMount)
    */
    public List<BookedDTO> getAllBookingSlot(String clubId){
        List<BookedDTO> list = bookingRepository.getAllBookingSlotByClubId(clubId);
        return list;
    }

    public MessageResponse excutePaymentTransaction(BookingPaymentRequestDTO bookingPaymentRequestDTO){
        // Goi ham insert booking schedule va lay booking_schedule_id o day
        String scheduleId = bookingRepository.insertBookingSchedule(bookingPaymentRequestDTO.getBookingSchedule());

        // Sau khi co booking_schedule_id, insert tung booking slot voi booking schedule (Ham o day)
        bookingRepository.insertBookingSlots(bookingPaymentRequestDTO.getBookingSchedule().getBookingSlotResponseDTOs(), scheduleId);


        // Tao invoice va insert no vao db o day
        Invoice invoice = new Invoice(bookingPaymentRequestDTO.getClubName(), bookingPaymentRequestDTO.getCourtManagerPhone(), bookingPaymentRequestDTO.getBookingSchedule().getCustomerPhoneNumber(), bookingPaymentRequestDTO.getClubId(), scheduleId);
        String invoiceId = bookingRepository.insertInvoice(invoice);

        // Tao payment va insert no vao db o day
        UUID timeBasedGenerator = Generators.timeBasedEpochGenerator().generate();
        String paymentId = String.valueOf(timeBasedGenerator);
        bookingPaymentRequestDTO.getPaymentDetail().setPaymentId(paymentId);
        bookingPaymentRequestDTO.getPaymentDetail().setInvoiceId(invoiceId);
        bookingPaymentRequestDTO.getPaymentDetail().setUserId(bookingPaymentRequestDTO.getBookingSchedule().getCustomerId());
        if(!bookingPaymentRequestDTO.getBookingSchedule().getScheduleType().equals("Flexible")){
            bookingPaymentRequestDTO.getPaymentDetail().setAmount(bookingPaymentRequestDTO.getPaymentDetail().getAmount()/100);
        } else {
            int amountMinutes = TimeUtils.convertTimeFormatToMinutes(bookingPaymentRequestDTO.getBookingSchedule().getTotalPlayingTime());
            bookingPaymentRequestDTO.getPaymentDetail().setAmount(amountMinutes);
        }
        
        bookingRepository.insertPaymentDetail(bookingPaymentRequestDTO.getPaymentDetail());
        return new MessageResponse("Payment success full");      
    }

    public List<PaymentDetail> getPaymentDetailsHistory(String scheduleId, String scheduleType){
        List<PaymentDetail> paymentDetails = bookingRepository.getPaymentDetails(scheduleId, scheduleType);
        if(scheduleType.equals("Flexible")){
            for(PaymentDetail paymentDetail: paymentDetails){
                paymentDetail.setAmountHourString(TimeUtils.convertMinutestoTimeFormat(paymentDetail.getAmount()));
            }
        }
        return paymentDetails;
    }

    public MessageResponse executePlayTimePayment(PlayableTimePayment playableTimePayment){
        MessageResponse mess = new MessageResponse("Payment Fail !");
        /*
         * private String paymentId;
    private int amount;
    private int minuteAmount;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private String customerId;
    private String badmintonClubId;
    private String badmintonClubName;
    private String playHoursMinuteString;
        */
        int minuteAmount = TimeUtils.convertTimeFormatToMinutes(playableTimePayment.getPlayHoursMinuteString());
        UUID timeBasedGenerator = Generators.timeBasedEpochGenerator().generate();
        String paymentId = String.valueOf(timeBasedGenerator);
        playableTimePayment.setMinuteAmount(minuteAmount);
        playableTimePayment.setPaymentId(paymentId);
        if(bookingRepository.isPayment(playableTimePayment)){
            mess.setMassage("Payment successfully !");
        }
        return mess;

    }

    // THANH TOAN CAP NHAT TRANG THAI CUA BOOKING SLOT

    public boolean isUpdateBookingSchStatus(PaymentUpdateBookingSchedule paymentUpdateBookingSchedule){
        // updata booking schedule status
        boolean isUpdateStatus;
        try {
             isUpdateStatus = bookingRepository.isUpdateStatus(paymentUpdateBookingSchedule.getBookingScheduleId(), paymentUpdateBookingSchedule.getBookingScheduleStatus());
        } catch (Exception e) {
            System.out.println("Error at isUpdateStatus: "+e);
            return false;
        }
        
        // inseart invoice
        String bookingScheduleId = paymentUpdateBookingSchedule.getBookingScheduleId();
        paymentUpdateBookingSchedule.getInvoice().setBookingScheduleId(bookingScheduleId);
        String invoiceId = bookingRepository.insertInvoice(paymentUpdateBookingSchedule.getInvoice());
        // inseart payment detail
        UUID timeBasedGenerator = Generators.timeBasedEpochGenerator().generate();
        String paymentId = String.valueOf(timeBasedGenerator);
        paymentUpdateBookingSchedule.getPaymentDetail().setPaymentId(paymentId);
        paymentUpdateBookingSchedule.getPaymentDetail().setInvoiceId(invoiceId);
        paymentUpdateBookingSchedule.getPaymentDetail().setAmount(paymentUpdateBookingSchedule.getPaymentDetail().getAmount()/100);
        bookingRepository.insertPaymentDetail(paymentUpdateBookingSchedule.getPaymentDetail());
        if(isUpdateStatus){
            return true;
        } else {
            return false;
        }
    }



}
