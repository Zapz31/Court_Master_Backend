package com.swp391.Court_Master.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.RowMapper.BookedDTORowMapper;

@Repository
public class StaffRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

        //Staff xac nhan khach da check in
        public boolean isUpdateCheckIn(String bookingSlotId){
            String sql = "UPDATE booking_slot\r\n" + //
                            "SET is_check_in ='1'\r\n" + //
                            "wHERE booking_slot_id = ?";
            PreparedStatementSetter pss = new PreparedStatementSetter() {        
                @Override
                public void setValues(PreparedStatement ps) throws SQLException{
                    ps.setString(1, bookingSlotId);
                }
            };
            int updateRow = jdbcTemplate.update(sql, pss);
            if(updateRow > 0){
                return true;
            } else {
                return false;
            }
        }

    // Staff kiem tra san trong
    public List<BookedDTO> bookedCourts(String clubId, LocalDate booking_date, LocalTime start_time, LocalTime end_time) {
        String sql = "SELECT \r\n" + //
                "    bs.badminton_court_id,\r\n" + //
                "    bc.badminton_court_name,\r\n" + //
                "    bs.start_time,\r\n" + //
                "    bs.end_time,\r\n" + //
                "    bs.booking_date,\r\n" + //
                "    bs.is_check_in,\r\n" + //
                "    bs.price,\r\n" + //
                "    bs.booking_slot_id,\r\n" + //
                "    bs.booking_schedule_id\r\n" + //
                "FROM \r\n" + //
                "    badminton_court bc\r\n" + //
                "INNER JOIN \r\n" + //
                "    booking_slot bs ON bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                "WHERE \r\n" + //
                "    bc.badminton_club_id = '?'\r\n" + //
                "    AND bs.booking_date >= '?'\r\n" + //
                "    AND bs.start_time >= '?'\r\n" + //
                "    AND bs.end_time <= '?'\r\n" + //
                "ORDER BY \r\n" + //
                "    bc.badminton_court_id;";

        Time startTime = Time.valueOf(start_time);
        Time endTime = Time.valueOf(end_time);
        Date bookDate = Date.valueOf(booking_date);
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clubId);
                ps.setDate(2, bookDate);
                ps.setTime(3, startTime);
                ps.setTime(4, endTime);

            }
        };
        return jdbcTemplate.query(sql,pss,new BookedDTORowMapper());
    }


}
