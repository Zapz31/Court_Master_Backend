package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.Entities.BadmintonClub;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryBookingSlotMapper;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalCustomerMapper;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalRevenueMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryBookingSlotChartRowMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryTotalCustomerRowMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryTotalRevenueRowMapper;
import com.swp391.Court_Master.dto.request.Request.DashBoardRequest;

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

    // Query lay ra cac payment detail record cua club trong vong mot thang.

    public List<QueryTotalRevenueMapper> getPaymentDetailList(DashBoardRequest dashBoardRequest){
        String sql = "select pd.payment_time, pd.amount from badminton_club bcl\r\n" + //
                        "inner join invoice iv on bcl.badminton_club_id = iv.badminton_club_id\r\n" + //
                        "inner join payment_detail pd on iv.invoice_id = pd.invoice_id\r\n" + //
                        "where bcl.badminton_club_id = ? AND (MONTH(pd.payment_time) = ? OR MONTH(pd.payment_time) = ?) AND (YEAR(pd.payment_time) = ? OR YEAR(pd.payment_time) = ?)";
    
        PreparedStatementSetter pss =new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, dashBoardRequest.getClubId());
                ps.setInt(2, dashBoardRequest.getPreviousMonth());
                ps.setInt(3, dashBoardRequest.getMonth());
                ps.setInt(4, dashBoardRequest.getPreviousYear());
                ps.setInt(5, dashBoardRequest.getYear());
            }
            
        };
        return jdbcTemplate.query(sql, pss, new QueryTotalRevenueRowMapper());

    }

    // Lay danh sach booking list.
    public List<LocalDate> getBookingTimeList(DashBoardRequest dashBoardRequest){
        String sql = "select bs.booking_date from badminton_club bcl \r\n" + //
                        "inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id\r\n" + //
                        "inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                        "where bcl.badminton_club_id = ? AND (MONTH(bs.booking_date) = ? or MONTH(bs.booking_date) = ?) AND (YEAR(bs.booking_date) = ? OR YEAR(bs.booking_date) = ?)";
        return jdbcTemplate.queryForList(sql, LocalDate.class, dashBoardRequest.getClubId(), dashBoardRequest.getPreviousMonth(), dashBoardRequest.getMonth(), dashBoardRequest.getPreviousYear(), dashBoardRequest.getYear());
    }

    // Lay 
    public List<QueryTotalCustomerMapper> getRegisterCusList(DashBoardRequest dashBoardRequest){
        String sql = "select count(*) as cus_num, t1.re_month from (\r\n" + //
                        "select au.user_id, MONTH(pd.payment_time) as re_month from badminton_club bcl \r\n" + //
                        "inner join invoice iv on bcl.badminton_club_id = iv.badminton_club_id\r\n" + //
                        "inner join payment_detail pd on pd.invoice_id = iv.invoice_id\r\n" + //
                        "inner join authenticated_user au on au.user_id = pd.user_id\r\n" + //
                        "where bcl.badminton_club_id = ? AND (MONTH(pd.payment_time) = ? or MONTH(pd.payment_time) = ?) AND (YEAR(pd.payment_time) = ? OR YEAR(pd.payment_time) = ?) \r\n" + //
                        "group by au.user_id, MONTH(pd.payment_time)\r\n" + //
                        ") as t1\r\n" + //
                        "group by t1.re_month";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, dashBoardRequest.getClubId());
                ps.setInt(2, dashBoardRequest.getPreviousMonth());
                ps.setInt(3, dashBoardRequest.getMonth());
                ps.setInt(4, dashBoardRequest.getPreviousYear());
                ps.setInt(5, dashBoardRequest.getYear());
            }      
        };
        return jdbcTemplate.query(sql, pss, new QueryTotalCustomerRowMapper());
    }

    public List<QueryBookingSlotMapper> getBookingDateNumber(DashBoardRequest dashBoardRequest){
        String sql = "select t1.booking_date, COUNT(*) as number_of_booking from (\r\n" + //
                        "select bs.booking_date from badminton_club bcl \r\n" + //
                        "inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id\r\n" + //
                        "inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                        "where bcl.badminton_club_id = ? AND (MONTH(bs.booking_date) = ? or MONTH(bs.booking_date) = ?) AND (YEAR(bs.booking_date) = ? OR YEAR(bs.booking_date) = ?)\r\n" + //
                        ") as t1\r\n" + //
                        "group by t1.booking_date";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, dashBoardRequest.getClubId());
                ps.setInt(2, dashBoardRequest.getPreviousMonth());
                ps.setInt(3, dashBoardRequest.getMonth());
                ps.setInt(4, dashBoardRequest.getPreviousYear());
                ps.setInt(5, dashBoardRequest.getYear());
            }
            
        };
        return jdbcTemplate.query(sql, pss, new QueryBookingSlotChartRowMapper());
    }

    public String getClubId(String userId){
        String sql = "select bcl.badminton_club_id from authenticated_user au\r\n" + //
                        "inner join badminton_club bcl on au.user_id = bcl.court_manager_id\r\n" + //
                        "where au.user_id = ?";

        return jdbcTemplate.queryForObject(sql, String.class, userId);
    }


}
