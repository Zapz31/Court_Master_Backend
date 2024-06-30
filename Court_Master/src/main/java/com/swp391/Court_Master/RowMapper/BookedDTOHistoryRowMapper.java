package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.BookedDTO;

public class BookedDTOHistoryRowMapper implements RowMapper<BookedDTO>{

    @Override
    public BookedDTO mapRow(ResultSet arg0, int arg1) throws SQLException {
        LocalTime startTime = arg0.getTime("start_time").toLocalTime();
        LocalTime endTime = arg0.getTime("end_time").toLocalTime();
        LocalDate bookingDate = arg0.getDate("booking_date").toLocalDate();
        String courtId = arg0.getString("badminton_court_id");
        String courtName = arg0.getNString("badminton_court_name");
        String bookingSlotId = arg0.getString("booking_slot_id");
        int isCheckIn = arg0.getInt("is_check_in");
        int price = arg0.getInt("price");
        return new BookedDTO(startTime, endTime, bookingDate, courtId, courtName, bookingSlotId, isCheckIn, price);
    }

}
