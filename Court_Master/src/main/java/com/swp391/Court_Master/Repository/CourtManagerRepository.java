package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.Entities.BadmintonClub;

@Repository
public class CourtManagerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Staff quan ly thong tin cua club
    // Update thong tin cua club
    public String updateClubInfo(String name, String description, int status, String clubId) {
        String notice = "Update club information failed";
        String updateSQL = "UPDATE badminton_club\r\n" + //
                "SET badminton_club_name='?',\r\n" + //
                "description='?',\r\n" + //
                "badminton_club_status=?\r\n" + //
                "WHERE badminton_club_id='?'";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setInt(3, status);
                ps.setString(4, clubId);
            }
        };
        int updateRow = jdbcTemplate.update(updateSQL, pss);
        if (updateRow > 0) {
            return notice ="Update club information succcessfully";
        } else {
            return notice;
        }
    }

}
