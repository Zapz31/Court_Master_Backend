package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.Court;

public class CourtRowMapper implements RowMapper<Court>{

    @Override
    public Court mapRow(ResultSet arg0, int arg1) throws SQLException {
        String courtId = arg0.getString("badminton_court_id");
        String courtName = arg0.getNString("badminton_court_name");
        String courtStatus = arg0.getNString("badminton_court_status");

        return new Court(courtId, courtName, courtStatus);
    }

}
