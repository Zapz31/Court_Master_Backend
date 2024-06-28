package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.BookingSchedule;

public class BookingScheduleRowMapper implements RowMapper<BookingSchedule>{

    @Override
    public BookingSchedule mapRow(ResultSet arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapRow'");
        /*
         * private String bookKingScheduleId;
    private String customerFullName;
    private String customerPhoneNumber;
    private String bookingScheduleStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private String scheduleType;
    private String customerId;
    private int totalPrice;
    private int isView;
    private List<PricePerSlotRequestDTO> pricePerSlotRequestDTOList;
         * 
        */
    }

}
