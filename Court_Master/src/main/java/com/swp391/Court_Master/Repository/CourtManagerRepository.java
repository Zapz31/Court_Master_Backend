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
    //Phai them phan cap nhat dia chi cho club
    //La post clubId get AddressID, sua dia chi trong table Address
    public boolean updateClubInfo(String name, String description, Integer status, String clubId) {
        StringBuilder updateSQL = new StringBuilder("UPDATE badminton_club SET ");
        boolean first = true;

        if (name != null) {
            updateSQL.append("badminton_club_name = ?");
            first = false;
        }

        if (description != null) {
            if (!first) {
                updateSQL.append(", ");
            }
            updateSQL.append("description = ?");
            first = false;
        }

        if (status != null) {
            if (!first) {
                updateSQL.append(", ");
            }
            updateSQL.append("badminton_club_status = ?");
            first = false;
        }

        // If no parameters are set to update, return false
        if (first) {
            return false;
        }

        updateSQL.append(" WHERE badminton_club_id = ?");

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 1;

                if (name != null) {
                    ps.setString(index++, name);
                }
                if (description != null) {
                    ps.setString(index++, description);
                }
                if (status != null) {
                    ps.setInt(index++, status);
                }
    
                ps.setString(index, clubId);
            }
        };
        int updateRow = jdbcTemplate.update(updateSQL.toString(), pss);
        if (updateRow > 0) {
            return true;
        } else {
            return false;
        }
    }

}
