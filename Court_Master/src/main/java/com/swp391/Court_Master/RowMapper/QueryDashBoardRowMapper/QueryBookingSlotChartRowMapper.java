package com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryBookingSlotMapper;

public class QueryBookingSlotChartRowMapper implements RowMapper<QueryBookingSlotMapper>{

    @Override
    public QueryBookingSlotMapper mapRow(ResultSet arg0, int arg1) throws SQLException {
        LocalDate bookingDate = arg0.getDate("booking_date").toLocalDate();
        int numberOfBooking = arg0.getInt("number_of_booking");
        return new QueryBookingSlotMapper(bookingDate, numberOfBooking);
    }

}
