package com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.Court;

public class AdminViewCourtRowMapper implements RowMapper<Court> {
    @Override
    public Court mapRow(ResultSet arg0, int arg1) throws SQLException {
        // Extract the courtId from the ResultSet
        String courtId = arg0.getString("badminton_court_id");

        // Extract the courtName from the ResultSet
        String courtName = arg0.getString("badminton_court_name");

        // Extract the court status from the Result Set
        String courtStatus = arg0.getString("badminton_court_status");

        // Extract the club id from the result set
        String clubId = arg0.getString("badminton_club_id");

        Court court = new Court(courtId, courtName, courtStatus, clubId);
        return court;
    }
}
