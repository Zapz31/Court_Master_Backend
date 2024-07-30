package com.swp391.Court_Master.RowMapper.QueryStaffScreenRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.dto.request.Respone.StaffScreenView.StaffViewBookingSlotDTO;

public class StaffViewBookingSlotRowMapper implements RowMapper<StaffViewBookingSlotDTO>{

    @Override
    public StaffViewBookingSlotDTO mapRow(ResultSet arg0, int arg1) throws SQLException {
        String bookingSlotId = arg0.getString("booking_slot_id");
        String customerPhoneNumber = arg0.getString("customer_phone_number");
        String customerFullName = arg0.getNString("customer_fullname");
        String badmintonCourtName = arg0.getNString("badminton_court_name");
        LocalTime startTime = arg0.getTime("start_time").toLocalTime();
        LocalTime endTime = arg0.getTime("end_time").toLocalTime(); 
        LocalDate bookingDate = arg0.getDate("booking_date").toLocalDate();
        int isCheckIn = arg0.getInt("is_check_in");
        int price = arg0.getInt("price");
        
        return new StaffViewBookingSlotDTO(bookingSlotId, customerPhoneNumber, customerFullName, badmintonCourtName, startTime, endTime, bookingDate, isCheckIn, price);
    }

}
