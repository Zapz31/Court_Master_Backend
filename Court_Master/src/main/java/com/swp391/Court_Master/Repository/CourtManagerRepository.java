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
    // Phai them phan cap nhat dia chi cho club
    // La post clubId get AddressID, sua dia chi trong table Address
    public boolean updateClubInfo(String name, String description, Integer status, String clubId) {
        StringBuilder updateSQL = new StringBuilder("UPDATE [Court_Master].[dbo].[badminton_club] SET ");
        boolean end = true;

        if (name != null) {
            updateSQL.append("badminton_club_name = ?");
            end = false;
        }

        if (description != null) {
            if (!end) {
                updateSQL.append(", ");
            }
            updateSQL.append("description = ?");
            end = false;
        }

        if (status != null) {
            if (!end) {
                updateSQL.append(", ");
            }
            updateSQL.append("badminton_club_status = ?");
            end = false;
        }

        // If no parameters are set to update, return false
        if (end) {
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

    public boolean updateClubAddress(String addressId, String unit_number, String ward, String district,
            String province) {
        StringBuilder updateSQL = new StringBuilder("UPDATE [Court_Master].[dbo].[address] SET ");
        boolean end = true;

        if (unit_number != null) {
            updateSQL.append("unit_number = ?");
            end = false;
        }

        if (ward != null) {
            if (!end) {
                updateSQL.append(", ");
            }
            updateSQL.append("ward = ?");
            end = false;
        }

        if (district != null) {
            if (!end) {
                updateSQL.append(", ");
            }
            updateSQL.append("district = ?");
            end = false;
        }

        if (province != null) {
            if (!end) {
                updateSQL.append(", ");
            }
            updateSQL.append("province = ?");
            end = false;
        }

        // If no parameters are set to update, return false
        if (end) {
            return false;
        }

        updateSQL.append(" WHERE address_id = ?");

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 1;

                if (unit_number != null) {
                    ps.setString(index++, unit_number);
                }
                if (ward != null) {
                    ps.setString(index++, ward);
                }
                if (district != null) {
                    ps.setString(index++, district);
                }
                if (province != null) {
                    ps.setString(index++, province);
                }

                ps.setString(index, addressId);

            }
        };
        int updateRow = jdbcTemplate.update(updateSQL.toString(), pss);
        if (updateRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateCourtInfo(){
        return false;
    }

}
