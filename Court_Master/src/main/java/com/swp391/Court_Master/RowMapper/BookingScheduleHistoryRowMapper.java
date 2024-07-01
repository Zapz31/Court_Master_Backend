package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.dto.request.Respone.BookingScheduleHistory;

public class BookingScheduleHistoryRowMapper implements RowMapper<BookingScheduleHistory>{

    @Override
    public BookingScheduleHistory mapRow(ResultSet arg0, int arg1) throws SQLException {
        String clubName = arg0.getNString("badminton_club_name");
        String courtManagerPhone = arg0.getString("court_manager_phone");
        String bookingScheduleId = arg0.getString("booking_schedule_id");
        String bookingScheduleStatus = arg0.getString("booking_schedule_status");
        LocalDate startDate = arg0.getDate("start_date").toLocalDate();
        LocalDate endDate = arg0.getDate("end_date").toLocalDate();
        String scheduleType = arg0.getString("schedule_type");
        int totalPrice = arg0.getInt("total_price");
        int totalPlayingTime = arg0.getInt("total_playing_time");
        String bookingPhoneNumber = arg0.getString("booking_phone_number"); 
        return new BookingScheduleHistory(clubName, courtManagerPhone, bookingScheduleId, bookingScheduleStatus, startDate, endDate, scheduleType, totalPrice, totalPlayingTime, bookingPhoneNumber);
    }

}
