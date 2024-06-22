package com.swp391.Court_Master.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.Entities.PricingService;
import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.Repository.BookingRepository;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.BookingSlotResponseDTO;
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
    public List<BookedDTO> getDuplicateBookingSlotList(List<PricePerSlotRequestDTO> pricePerSlotRequestDTOs) {
        List<BookedDTO> allBookingSlotsByCourtId = bookingRepository.getBookedList(pricePerSlotRequestDTOs);
        List<BookedDTO> duplicateBookingSlotList = new ArrayList<>();
        for (PricePerSlotRequestDTO pricePerSlotRequestDTO : pricePerSlotRequestDTOs) {
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
                } else if((!pricePerSlotRequestDTO.getCourtId().equals(bookedDTO.getCourtId())) && (flag == 1)){
                    break;
                }
            }
        }
        return duplicateBookingSlotList;

    }

}
