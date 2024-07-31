package com.swp391.Court_Master.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.uuid.Generators;
import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.Entities.BookingSchedule;
import com.swp391.Court_Master.Entities.Court;
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
import com.swp391.Court_Master.Utils.TimeUtils;

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
                    || startBooking.isAfter(tf.getStarTime()) && startBooking.isBefore(tf.getEndTime())) //
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
                double timeDiffBooking = 0;
                LocalTime midNight = LocalTime.of(23, 59, 59);
                if (endBooking.equals(midNight)) {
                    timeDiffBooking = TimeUtils.getTimeDiffMidnight(startBooking);
                } else {
                    timeDiffBooking = calculateTimeDifference(startBooking, endBooking);
                }
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

            } else if (startBooking.isBefore(tf.getStarTime()) && (endBooking.isAfter(tf.getStarTime()) //
                    && (endBooking.equals(tf.getEndTime()) || endBooking.isBefore(tf.getEndTime())))) {
                int pricePerHours = -1;
                for (PricingService ps : tf.getPricingServiceList()) {

                    if (dayOfWeek.equals(ps.getDateOfWeek())) {
                        // selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                // double timeDiffBooking = calculateTimeDifference(tf.getStarTime(),
                // endBooking);
                double timeDiffBooking = 0;
                LocalTime midNight = LocalTime.of(23, 59, 59);
                if (endBooking.equals(midNight)) {
                    timeDiffBooking = TimeUtils.getTimeDiffMidnight(tf.getStarTime());
                } else {
                    timeDiffBooking = calculateTimeDifference(tf.getStarTime(), endBooking);
                }
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
            case "one-time play":
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
            LocalTime midNight = LocalTime.of(23, 59, 59);
            if (!perSlotRequestDTO.getEndBooking().equals(midNight)) {
                Duration duration = Duration.between(perSlotRequestDTO.getStartBooking(),
                        perSlotRequestDTO.getEndBooking());
                totalDuration = totalDuration.plus(duration);
            } else {
                Duration duration = Duration.between(perSlotRequestDTO.getStartBooking(),
                        perSlotRequestDTO.getEndBooking()).plusMinutes(1);
                totalDuration = totalDuration.plus(duration);
            }
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
            if (perSlotRequestDTO.getStartBooking().isAfter(perSlotRequestDTO.getEndBooking())) {
                LocalTime temp = perSlotRequestDTO.getStartBooking();
                perSlotRequestDTO.setStartBooking(perSlotRequestDTO.getEndBooking());
                perSlotRequestDTO.setEndBooking(temp);
            }
            Integer price = getPricePerSlot(perSlotRequestDTO.getCourtId(),
                    perSlotRequestDTO.getStartBooking(),
                    perSlotRequestDTO.getEndBooking(),
                    perSlotRequestDTO.getBookingDate(),
                    perSlotRequestDTO.getBookingType());
            String playTime = "";
            LocalTime midNight = LocalTime.of(23, 59, 59);
            if (!perSlotRequestDTO.getEndBooking().equals(midNight)) {
                Duration duration = Duration.between(perSlotRequestDTO.getStartBooking(),
                        perSlotRequestDTO.getEndBooking());
                playTime = formatDuration(duration);
            } else {
                playTime = TimeUtils.getTimeDiffMidNightString(perSlotRequestDTO.getStartBooking());
            }
            // String playTime = formatDuration(duration);
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
        List<BookedDTO> duplicateBookingSlotList = new ArrayList<>();
        if (!pricePerSlotRequestDTOs.isEmpty() || pricePerSlotRequestDTOs != null) {
            List<BookedDTO> allBookingSlotsByCourtId = bookingRepository.getBookedList(pricePerSlotRequestDTOs);
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
                                            || pricePerSlotRequestDTO.getEndBooking()
                                                    .isBefore(bookedDTO.getEndTime()))) {
                                duplicateBookingSlotList.add(bookedDTO);
                            } else if (pricePerSlotRequestDTO.getStartBooking().isBefore(bookedDTO.getStartTime())
                                    && (pricePerSlotRequestDTO.getEndBooking().equals(bookedDTO.getEndTime())
                                            || pricePerSlotRequestDTO.getEndBooking().isBefore(bookedDTO.getEndTime())
                                                    && pricePerSlotRequestDTO.getEndBooking()
                                                            .isAfter(bookedDTO.getStartTime()))) {
                                duplicateBookingSlotList.add(bookedDTO);
                            } else if ((pricePerSlotRequestDTO.getStartBooking().equals(bookedDTO.getStartTime())
                                    || pricePerSlotRequestDTO.getStartBooking().isAfter(bookedDTO.getStartTime())
                                            && pricePerSlotRequestDTO.getStartBooking()
                                                    .isBefore(bookedDTO.getEndTime()))
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
        }

        return duplicateBookingSlotList;

    }

    /*
     * Kiem tra xem cac booking slot co trung nhau hay khong, ham tra ve danh sach
     * cac pricePerSlotRequestDTOs bi trung
     * thay vi bookedDTO
     */
    public List<BookingSlotResponseDTO> getDupBookingSlotRequest(List<BookingSlotResponseDTO> pricePerSlotRequestDTOs,
            int isTemp) {
        List<BookingSlotResponseDTO> duplicateBookingSlotRequests = new ArrayList<>();
        if (!pricePerSlotRequestDTOs.isEmpty() || pricePerSlotRequestDTOs != null) {
            List<BookedDTO> allBookingSlotsByCourtId = bookingRepository.getBookedList(pricePerSlotRequestDTOs);
            List<BookedDTO> tempOrNotTempList = new ArrayList<>();
            // Check trung theo danh sach cac booking slot tam thoi hoac ko tam thoi
            if (isTemp == 1) {
                for (BookedDTO bookedDTO : allBookingSlotsByCourtId) {
                    if (bookedDTO.getIsTemp() == 1) {
                        tempOrNotTempList.add(bookedDTO);
                    }
                }
            } else if (isTemp == 0) {
                for (BookedDTO bookedDTO : allBookingSlotsByCourtId) {
                    if (bookedDTO.getIsTemp() == 0) {
                        tempOrNotTempList.add(bookedDTO);
                    }
                }
            }

            for (BookingSlotResponseDTO pricePerSlotRequestDTO : pricePerSlotRequestDTOs) {
                int flag = 0;
                for (BookedDTO bookedDTO : tempOrNotTempList) {
                    if ((flag == 0) && (!pricePerSlotRequestDTO.getCourtId().equals(bookedDTO.getCourtId()))) {
                        continue;
                    } else if (pricePerSlotRequestDTO.getCourtId().equals(bookedDTO.getCourtId())) {
                        flag = 1;
                        if (pricePerSlotRequestDTO.getBookingDate().equals(bookedDTO.getBookingDate())) {
                            if ((pricePerSlotRequestDTO.getStartBooking().equals(bookedDTO.getStartTime())
                                    || (pricePerSlotRequestDTO.getStartBooking().isAfter(bookedDTO.getStartTime())))
                                    && (pricePerSlotRequestDTO.getEndBooking().equals(bookedDTO.getEndTime())
                                            || pricePerSlotRequestDTO.getEndBooking()
                                                    .isBefore(bookedDTO.getEndTime()))) {
                                duplicateBookingSlotRequests.add(pricePerSlotRequestDTO);
                                break;
                            } else if (pricePerSlotRequestDTO.getStartBooking().isBefore(bookedDTO.getStartTime())
                                    && (pricePerSlotRequestDTO.getEndBooking().equals(bookedDTO.getEndTime())
                                            || pricePerSlotRequestDTO.getEndBooking().isBefore(bookedDTO.getEndTime())
                                                    && pricePerSlotRequestDTO.getEndBooking()
                                                            .isAfter(bookedDTO.getStartTime()))) {
                                duplicateBookingSlotRequests.add(pricePerSlotRequestDTO);
                                break;
                            } else if ((pricePerSlotRequestDTO.getStartBooking().equals(bookedDTO.getStartTime())
                                    || pricePerSlotRequestDTO.getStartBooking().isAfter(bookedDTO.getStartTime())
                                            && pricePerSlotRequestDTO.getStartBooking()
                                                    .isBefore(bookedDTO.getEndTime()))
                                    && pricePerSlotRequestDTO.getEndBooking().isAfter(bookedDTO.getEndTime())) {
                                duplicateBookingSlotRequests.add(pricePerSlotRequestDTO);
                                break;
                            } else if (pricePerSlotRequestDTO.getStartBooking().isBefore(bookedDTO.getStartTime())
                                    && pricePerSlotRequestDTO.getEndBooking().isAfter(bookedDTO.getEndTime())) {
                                duplicateBookingSlotRequests.add(pricePerSlotRequestDTO);
                                break;
                            }
                        }
                    } else if ((!pricePerSlotRequestDTO.getCourtId().equals(bookedDTO.getCourtId())) && (flag == 1)) {
                        break;
                    }
                }
            }
        }

        return duplicateBookingSlotRequests;

    }

    /*
     * Kiem tra tinh hop le cua thoi gian dat san so voi time frame
     */
    public List<MessageResponse> checkValidBookingSlot(String clubId,
            List<PricePerSlotRequestDTO> pricePerSlotRequestDTOs) {
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
                if (perSlotRequestDTO.getStartBooking().isBefore(tf.getStarTime())
                        && perSlotRequestDTO.getEndBooking().isAfter(tf.getEndTime())) {
                    rangeTimeFrames.add(tf);
                }
            }
            List<TimeFrame> uniqueTimeFrames = new ArrayList<>();
            for (TimeFrame tf : rangeTimeFrames) {
                if (!uniqueTimeFrames.contains(tf)) {
                    uniqueTimeFrames.add(tf);
                }
            }
            if ((uniqueTimeFrames.size() > 1)) {
                TimeFrame preTime = uniqueTimeFrames.get(0);
                for (int i = 1; i < uniqueTimeFrames.size(); i++) {
                    if (!preTime.getEndTime().equals(uniqueTimeFrames.get(i).getStarTime())) {
                        isContinous = false;
                    }
                    preTime = uniqueTimeFrames.get(i);
                }
            }
            if (isValidStartTime == false || isValidEndTime == false || isContinous == false) {
                invalidMessages.add(new MessageResponse("The booking from " + perSlotRequestDTO.getStartBooking()
                        + " to " + perSlotRequestDTO.getEndBooking()
                        + " is invalid. You can go back schedule to check."));
            }

        }
        return invalidMessages;

    }

    /*
     * Lay danh sach tat ca cac booking_slot de hien thi ra man hinh dat lich
     * (onMount)
     */
    public List<BookedDTO> getAllBookingSlot(String clubId) {
        List<BookedDTO> list = bookingRepository.getAllBookingSlotByClubId(clubId);
        return list;
    }

    public MessageResponse excutePaymentTransaction(BookingPaymentRequestDTO bookingPaymentRequestDTO) {
        String scheduleId = "";

        // Sau khi co booking_schedule_id, insert tung booking slot voi booking schedule
        // (Ham o day)
        if (bookingPaymentRequestDTO.getBookingSchedule().getScheduleType().equals("Flexible")) {
            scheduleId = bookingRepository.insertBookingSchedule(bookingPaymentRequestDTO.getBookingSchedule());
            List<BookingSlotResponseDTO> bookingSlotBeforeInsert = bookingPaymentRequestDTO.getBookingSchedule()
                    .getBookingSlotResponseDTOs();
            for (BookingSlotResponseDTO bs : bookingSlotBeforeInsert) {
                bs.setIsTemp(0);
            }
            bookingRepository.insertBookingSlots(bookingSlotBeforeInsert,
                    scheduleId);
        } else {
            String[] scheduleAndSlotIdTempPart = bookingPaymentRequestDTO.getScheduleAndSlotIdTemp().split(",");
            List<String> bookingSlotIDstemp = new ArrayList<>();
            scheduleId = scheduleAndSlotIdTempPart[0];
            for (int i = 2; i < scheduleAndSlotIdTempPart.length; i++) {
                bookingSlotIDstemp.add(scheduleAndSlotIdTempPart[i]);
            }

            // Update bookingScheduleStatus and remainingAmount
            String[] tempBookingScheduleAndSlotsPart = bookingPaymentRequestDTO.getScheduleAndSlotIdTemp().split(",");
            int totalPrice = Integer.parseInt(tempBookingScheduleAndSlotsPart[1]);
            int remmainingAmount = totalPrice - (bookingPaymentRequestDTO.getPaymentDetail().getAmount() / 100);
            bookingRepository.updateScheduleAndSlotInfo(scheduleId, remmainingAmount); // Update
        }

        // Tao invoice va insert no vao db o day
        Invoice invoice = new Invoice(bookingPaymentRequestDTO.getClubName(),
                bookingPaymentRequestDTO.getCourtManagerPhone(),
                bookingPaymentRequestDTO.getBookingSchedule().getCustomerPhoneNumber(),
                bookingPaymentRequestDTO.getClubId(), scheduleId);
        String invoiceId = bookingRepository.insertInvoice(invoice);

        // Tao payment va insert no vao db o day
        UUID timeBasedGenerator = Generators.timeBasedEpochGenerator().generate();
        String paymentId = String.valueOf(timeBasedGenerator);
        bookingPaymentRequestDTO.getPaymentDetail().setPaymentId(paymentId);
        bookingPaymentRequestDTO.getPaymentDetail().setInvoiceId(invoiceId);
        bookingPaymentRequestDTO.getPaymentDetail()
                .setUserId(bookingPaymentRequestDTO.getBookingSchedule().getCustomerId());
        if (!bookingPaymentRequestDTO.getBookingSchedule().getScheduleType().equals("Flexible")) {
            bookingPaymentRequestDTO.getPaymentDetail()
                    .setAmount(bookingPaymentRequestDTO.getPaymentDetail().getAmount() / 100);
        } else {
            int amountMinutes = TimeUtils
                    .convertTimeFormatToMinutes(bookingPaymentRequestDTO.getBookingSchedule().getTotalPlayingTime());
            bookingPaymentRequestDTO.getPaymentDetail().setAmount(amountMinutes);
        }

        bookingRepository.insertPaymentDetail(bookingPaymentRequestDTO.getPaymentDetail());
        return new MessageResponse("Payment success full");
    }

    public List<PaymentDetail> getPaymentDetailsHistory(String scheduleId, String scheduleType) {
        List<PaymentDetail> paymentDetails = bookingRepository.getPaymentDetails(scheduleId, scheduleType);
        if (scheduleType.equals("Flexible")) {
            for (PaymentDetail paymentDetail : paymentDetails) {
                paymentDetail.setAmountHourString(TimeUtils.convertMinutestoTimeFormat(paymentDetail.getAmount()));
            }
        }
        return paymentDetails;
    }

    public MessageResponse executePlayTimePayment(PlayableTimePayment playableTimePayment) {
        MessageResponse mess = new MessageResponse("Payment Fail !");
        /*
         * private String paymentId;
         * private int amount;
         * private int minuteAmount;
         * private String paymentMethod;
         * private LocalDateTime paymentTime;
         * private String customerId;
         * private String badmintonClubId;
         * private String badmintonClubName;
         * private String playHoursMinuteString;
         */
        int minuteAmount = TimeUtils.convertTimeFormatToMinutes(playableTimePayment.getPlayHoursMinuteString());
        UUID timeBasedGenerator = Generators.timeBasedEpochGenerator().generate();
        String paymentId = String.valueOf(timeBasedGenerator);
        playableTimePayment.setMinuteAmount(minuteAmount);
        playableTimePayment.setPaymentId(paymentId);
        if (bookingRepository.isPayment(playableTimePayment)) {
            mess.setMassage("Payment successfully !");
        }
        return mess;

    }

    // THANH TOAN CAP NHAT TRANG THAI CUA BOOKING SLOT

    public boolean isUpdateBookingSchStatus(PaymentUpdateBookingSchedule paymentUpdateBookingSchedule) {
        // updata booking schedule status
        boolean isUpdateStatus;
        try {
            isUpdateStatus = bookingRepository.isUpdateStatus(paymentUpdateBookingSchedule.getBookingScheduleId(),
                    paymentUpdateBookingSchedule.getBookingScheduleStatus(),
                    paymentUpdateBookingSchedule.getPaymentDetail().getAmount());
        } catch (Exception e) {
            System.out.println("Error at isUpdateStatus: " + e);
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
        paymentUpdateBookingSchedule.getPaymentDetail()
                .setAmount(paymentUpdateBookingSchedule.getPaymentDetail().getAmount() / 100);
        bookingRepository.insertPaymentDetail(paymentUpdateBookingSchedule.getPaymentDetail());
        if (isUpdateStatus) {
            return true;
        } else {
            return false;
        }
    }

    public int calculatorPriceForPlayingTime(String clubId, int totalPlayingTime) {
        int pricePerHours = bookingRepository.getFlexiblePrice(clubId);
        return totalPlayingTime * pricePerHours;
    }

    // Lay chuoi booking schedule va slot Id duoc them vao tam thoi vi du:
    // SD0000030,350000,BS000022,BS000023...
    @Transactional
    public String getBookingScheduleAndSlotIdTemp(BookingSchedule bookingSchedule) {
        StringBuilder result = new StringBuilder();
        if (bookingSchedule.getBookingScheduleStatus().isEmpty()) {
            bookingSchedule.setBookingScheduleStatus("Paid");
        }
        String bookingScheduleId = bookingRepository.insertBookingSchedule(bookingSchedule);
        bookingRepository.insertBookingSlots(bookingSchedule.getBookingSlotResponseDTOs(), bookingScheduleId);
        List<String> allBookingSlotId = bookingRepository.getTempBookingSlotId();
        Collections.sort(allBookingSlotId, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }

        });

        List<String> tempBookingSlotIds = new ArrayList<>(allBookingSlotId.subList(0,
                Math.min(bookingSchedule.getBookingSlotResponseDTOs().size(), allBookingSlotId.size())));
        result.append(bookingScheduleId + "," + bookingSchedule.getTotalPrice());
        for (String bookingSlotId : tempBookingSlotIds) {
            result.append("," + bookingSlotId);
        }
        return result.toString();

    }

    // Xoa term booking khi nguoi dung huy thanh toan
    public void removeTempBooking(String scheduleAndSlotIdTemp) {
        String[] part = scheduleAndSlotIdTemp.split(",");
        String scheduleId = part[0];
        bookingRepository.deleteTempBookingScheduleAndSlot(scheduleId);
    }

    // Tim thoi gian hop ly co the chuyen san
    public List<String> getAllowedCourtChangeTime(BookingSlotResponseDTO bookingSlotResponseDTO) {

        // Tao list cac ngay con lai cua thang do
        List<String> resultList = new ArrayList<>();
        List<LocalDate> allDayOfMonth = generateDatesWithinMonth(bookingSlotResponseDTO.getBookingDate());

        // Tao list tat ca cac ma san tu ma san cua BookingSlotRequest
        List<Court> courts = bookingRepository.getAllCourtIdOfClubByCourtId(bookingSlotResponseDTO.getCourtId());
        for (LocalDate allowDate : allDayOfMonth) {
            for (Court court : courts) {
                int price = getPricePerSlot(court.getCourtId(), bookingSlotResponseDTO.getStartBooking(),
                        bookingSlotResponseDTO.getEndBooking(), allowDate, bookingSlotResponseDTO.getBookingType());
                if (bookingSlotResponseDTO.getPrice() != price) { // Kiem tra cung gia voi slot cu
                    break;
                } else {
                    BookingSlotResponseDTO tempBookingSlot = new BookingSlotResponseDTO(court.getCourtId(),
                            bookingSlotResponseDTO.getStartBooking(), bookingSlotResponseDTO.getEndBooking(), allowDate,
                            bookingSlotResponseDTO.getBookingType(), bookingSlotResponseDTO.getPlayTime(), price);
                    List<BookingSlotResponseDTO> duplicateList = new ArrayList<>();
                    duplicateList.add(tempBookingSlot);
                    List<BookingSlotResponseDTO> dupListResult = getDupBookingSlotRequest(duplicateList, 0);
                    if (dupListResult.isEmpty()) { // Khong trung
                        resultList.add(allowDate + " - " + court.getCourtName() + " - "
                                + TimeUtils.convertLocalTimeToString(tempBookingSlot.getStartBooking()) + " to "
                                + TimeUtils.convertLocalTimeToString(tempBookingSlot.getEndBooking()));
                    }
                }

            }
        }

        return resultList;
    }

    public List<LocalDate> generateDatesWithinMonth(LocalDate date) {
        LocalDate lastDayOfNextMonth = date.plusMonths(1).withDayOfMonth(1).minusDays(1);

        List<LocalDate> dateAllowDays = new ArrayList<>();
        
        while (!date.isAfter(lastDayOfNextMonth)) {
            dateAllowDays.add(date);
            date = date.plusDays(1);
        }
        return dateAllowDays;
    }

}
