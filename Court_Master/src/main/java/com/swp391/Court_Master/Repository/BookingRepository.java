package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.RowMapper.TimeFramePricingServiceRowMapper;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

@Repository
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TimeFramePricingServiceDTO> getClubTimeFramePricingByCourtId(String courtId, LocalTime startBooking, LocalTime endBooking){
        String sql = "DECLARE @start_time_booking TIME, @end_time_booking TIME;\r\n" + //
                        "SET @start_time_booking = ?; -- Set the start time booking value\r\n" + //  ?????
                        "SET @end_time_booking = ?; -- Set the end time booking value\r\n" + //     //??????
                        "\r\n" + //
                        "SELECT\r\n" + //
                        "\tbc.badminton_club_id,\r\n" + //
                        "    tf.time_frame_id,\r\n" + //
                        "    tf.start_time,\r\n" + //
                        "    tf.end_time,\r\n" + //
                        "\tpr.day_of_week,\r\n" + //
                        "\tpr.flexible,\r\n" + //
                        "\tpr.fixed,\r\n" + //
                        "\tpr.one_time_play\r\n" + //
                        "FROM\r\n" + //
                        "    badminton_court bc\r\n" + //
                        "    INNER JOIN badminton_club bcl ON bc.badminton_club_id = bcl.badminton_club_id\r\n" + //
                        "    INNER JOIN time_frame tf ON bcl.badminton_club_id = tf.badminton_club_id\r\n" + //
                        "    INNER JOIN pricing_rule pr ON tf.time_frame_id = pr.time_frame_id\r\n" + //
                        "    CROSS APPLY (\r\n" + //
                        "        SELECT\r\n" + //
                        "            time_frame_id,\r\n" + //
                        "            start_time,\r\n" + //
                        "            end_time,\r\n" + //
                        "            CASE\r\n" + //
                        "                WHEN @start_time_booking >= start_time AND @end_time_booking <= end_time THEN 1\r\n" + //
                        "                WHEN @start_time_booking < start_time AND @end_time_booking > end_time THEN 1\r\n" + //
                        "                WHEN @start_time_booking >= start_time AND @start_time_booking < end_time AND @end_time_booking > end_time THEN 1\r\n" + //
                        "                WHEN @start_time_booking < start_time AND @end_time_booking >= start_time AND @end_time_booking <= end_time THEN 1\r\n" + //
                        "                ELSE 0\r\n" + //
                        "            END AS is_valid\r\n" + //
                        "        FROM\r\n" + //
                        "            time_frame\r\n" + //
                        "        WHERE\r\n" + //
                        "            time_frame_id = tf.time_frame_id\r\n" + //
                        "    ) cte\r\n" + //
                        "WHERE\r\n" + //
                        "    bc.badminton_court_id = ?\r\n" + //    //????
                        "    AND cte.is_valid = 1;";

        Time startBookingTime = Time.valueOf(startBooking);
        Time endBookingTime = Time.valueOf(endBooking);
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setTime(1, startBookingTime);
                ps.setTime(2, endBookingTime);
                ps.setString(3, courtId);
            }
            
        };
        return jdbcTemplate.query(sql, pss, new TimeFramePricingServiceRowMapper());
    }

}
