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

import com.swp391.Court_Master.Entities.PricingService;
import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.Repository.BookingRepository;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    // TINH TIEN MOT SLOT BOOKING
    public int getPricePerSlot(String courtId, LocalTime startBooking, LocalTime endBooking, LocalDate bookingDate, String bookingType){
        List<TimeFramePricingServiceDTO> tfpsList = bookingRepository.getClubTimeFramePricingByCourtId(courtId, startBooking, endBooking);
        String dayOfWeekUp = bookingDate.getDayOfWeek().toString().substring(0, 3);
        String dayOfWeek = Character.toUpperCase(dayOfWeekUp.charAt(0)) + dayOfWeekUp.substring(1).toLowerCase();
        int totalPrice = 0;
        List<TimeFrame> timeFrames = getTimeFrameForPricing(tfpsList);

        for(TimeFrame tf: timeFrames){
            if((startBooking.equals(tf.getStarTime()) || startBooking.isAfter(tf.getStarTime()) && startBooking.isBefore(tf.getEndTime())) && (endBooking.equals(tf.getEndTime()) || endBooking.isBefore(tf.getEndTime()))){
                //PricingService selectedPricingService = new PricingService();
                int pricePerHours = -1;
                for(PricingService ps: tf.getPricingServiceList()){
                    if(dayOfWeek.equals(ps.getDateOfWeek())){
                        //selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                double timeDiffBooking = calculateTimeDifference(startBooking, endBooking);
                totalPrice = (int)(pricePerHours * timeDiffBooking);

            } else if((startBooking.equals(tf.getStarTime()) || startBooking.isAfter(tf.getStarTime()) && startBooking.isBefore(tf.getEndTime())) &&(endBooking.isAfter(tf.getEndTime()))){
                int pricePerHours = -1;
                for(PricingService ps: tf.getPricingServiceList()){
                    
                    if(dayOfWeek.equals(ps.getDateOfWeek())){
                        //selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                double timeDiffBooking = calculateTimeDifference(startBooking, tf.getEndTime());
                totalPrice += (int)(timeDiffBooking * pricePerHours);

            } else if(startBooking.isBefore(tf.getStarTime()) && (endBooking.isAfter(tf.getStarTime()) && (endBooking.equals(tf.getEndTime()) || endBooking.isBefore(tf.getEndTime())))){
                int pricePerHours = -1;
                for(PricingService ps: tf.getPricingServiceList()){
                    
                    if(dayOfWeek.equals(ps.getDateOfWeek())){
                        //selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                double timeDiffBooking = calculateTimeDifference(tf.getStarTime(), endBooking);
                totalPrice += (int)(timeDiffBooking * pricePerHours);
            } else if(startBooking.isBefore(tf.getStarTime()) && endBooking.isAfter(tf.getEndTime())){
                int pricePerHours = -1;
                for(PricingService ps: tf.getPricingServiceList()){
                    
                    if(dayOfWeek.equals(ps.getDateOfWeek())){
                        //selectedPricingService = ps;
                        pricePerHours = getPricePerHour(ps, bookingType);
                        break;
                    }
                }
                double timeDiffBooking = calculateTimeDifference(tf.getStarTime(), tf.getEndTime());
                totalPrice += (int)(timeDiffBooking * pricePerHours);
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
    public Integer getAllBookingSLotTotalPrice(List<PricePerSlotRequestDTO> bookingSlotList){
        int totalPrice = 0;
        for(PricePerSlotRequestDTO perSlotRequestDTO: bookingSlotList){
            int pricePerSlot = getPricePerSlot(perSlotRequestDTO.getCourtId(), perSlotRequestDTO.getStartBooking(), perSlotRequestDTO.getEndBooking(), perSlotRequestDTO.getBookingDate(), perSlotRequestDTO.getBookingType());
            totalPrice += pricePerSlot;
        }
        return totalPrice;
    }

    /*
     * Tinh tong so gio choi 
    */
    public String getTotalHoursAllSlot(List<PricePerSlotRequestDTO> bookingSlotList){
        Duration totalDuration = Duration.ZERO;
        for(PricePerSlotRequestDTO perSlotRequestDTO: bookingSlotList){
            Duration duration = Duration.between(perSlotRequestDTO.getStartBooking(), perSlotRequestDTO.getEndBooking());
            totalDuration = totalDuration.plus(duration);   

        }
        return formatDuration(totalDuration);
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return String.format("%02d:%02d", hours, minutes);
    }

    
    

}
