package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.Entities.BadmintonClub;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryBookingSlotMapper;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalCustomerMapper;
import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalRevenueMapper;
import com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper.AdminViewUserAccountsRowMapper;
import com.swp391.Court_Master.RowMapper.QueryCourtManagerScreenRowMapper.CourtManagerViewStaffRowMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryBookingSlotChartRowMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryTotalCustomerRowMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryTotalRevenueRowMapper;
import com.swp391.Court_Master.dto.request.Request.DashBoardRequest;
import com.swp391.Court_Master.dto.request.Request.SearchStaffByPhoneNameRequest;
import com.swp391.Court_Master.dto.request.Request.UpdateStaffRequest;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.UserAccountDTO;
import com.swp391.Court_Master.dto.request.Respone.CourManagerScreenView.StaffAccountDTO;

@Repository
public class CourtManagerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Staff quan ly thong tin cua club
    // Update thong tin cua club
    // Phai them phan cap nhat dia chi cho club
    // La post clubId get AddressID, sua dia chi trong table Address
    public boolean updateClubInfo(String name, String description, String clubId) {
        // Buoc 1 Tinh toan cau truy van SQL de cap nhat thong tin club
        StringBuilder updateSQL = new StringBuilder();

        // Buoc 2 Xay dung cau truy van SQL ban dau
        updateSQL.append("UPDATE [Court_Master].[dbo].[badminton_club] SET ");

        // Buoc 3 Bien kiem tra neu co cot nao can cap nhat
        boolean hasParameters = false;

        // Buoc 4 Xay dung phan SET cho name neu ton tai
        if (name != null && name != "") {
            updateSQL.append("badminton_club_name = ?");
            hasParameters = true;
        }

        // Buoc 5 Xay dung phan SET cho description neu ton tai
        if (description != null && description != "") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("description = ?");
            hasParameters = true;
        }

        // Buoc 6 Xay dung phan SET cho status neu ton tai

        // Buoc 7 Kiem tra neu khong co tham so nao de cap nhat
        if (!hasParameters) {
            return false;
        }

        // Buoc 8 Xay dung phan WHERE cho cau truy van SQL
        updateSQL.append(" WHERE badminton_club_id = ?");

        // Buoc 9 Tao PreparedStatementSetter de set gia tri cho cau truy van
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 1;

                // Buoc 10 Set gia tri cho name neu ton tai
                if (name != null && name != "") {
                    ps.setString(index++, name);
                }

                // Buoc 11 Set gia tri cho description neu ton tai
                if (description != null && description != "") {
                    ps.setString(index++, description);
                }

                // Buoc 13 Set gia tri cho clubId
                ps.setString(index, clubId);
            }
        };

        // Buoc 14 Thuc thi cau truy van va kiem tra so dong duoc cap nhat
        int updateRow = jdbcTemplate.update(updateSQL.toString(), pss);

        // Buoc 15 Tra ve true neu so dong duoc cap nhat lon hon 0
        if (updateRow > 0) {
            return true;
        } else {
            // Buoc 16 Tra ve false neu khong co dong nao duoc cap nhat
            return false;
        }
    }

    //hien tai khong cho update, khong bo qua service, controller
    public boolean updateClubAddress(String addressId, String unit_number, String ward, String district,
            String province) {
        // Buoc 1 Tao StringBuilder de xay dung cau truy van SQL
        StringBuilder updateSQL = new StringBuilder();
        updateSQL.append("UPDATE [Court_Master].[dbo].[address] SET ");

        // Buoc 2 Bien kiem tra de xac dinh co tham so nao de cap nhat
        boolean hasParameters = true;

        // Buoc 3 Xay dung phan SET cho unit_number neu ton tai
        if (unit_number != null) {
            updateSQL.append("unit_number = ?");
            hasParameters = false;
        }

        // Buoc 4 Xay dung phan SET cho ward neu ton tai
        if (ward != null) {
            if (!hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("ward = ?");
            hasParameters = false;
        }

        // Buoc 5 Xay dung phan SET cho district neu ton tai
        if (district != null) {
            if (!hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("district = ?");
            hasParameters = false;
        }

        // Buoc 6 Xay dung phan SET cho province neu ton tai
        if (province != null) {
            if (!hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("province = ?");
            hasParameters = false;
        }

        // Buoc 7 Kiem tra neu khong co tham so nao de cap nhat
        if (hasParameters) {
            return false;
        }

        // Buoc 8 Xay dung phan WHERE cho cau truy van SQL
        updateSQL.append(" WHERE address_id = ?");

        // Buoc 9 Tao PreparedStatementSetter de set gia tri cho cau truy van
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 1;

                // Buoc 10 Set gia tri cho unit_number neu ton tai
                if (unit_number != null) {
                    ps.setString(index++, unit_number);
                }

                // Buoc 11 Set gia tri cho ward neu ton tai
                if (ward != null) {
                    ps.setString(index++, ward);
                }

                // Buoc 12 Set gia tri cho district neu ton tai
                if (district != null) {
                    ps.setString(index++, district);
                }

                // Buoc 13 Set gia tri cho province neu ton tai
                if (province != null) {
                    ps.setString(index++, province);
                }

                // Buoc 14 Set gia tri cho addressId
                ps.setString(index, addressId);
            }
        };

        // Buoc 15 Thuc thi cau truy van va kiem tra so dong duoc cap nhat
        int updateRow = jdbcTemplate.update(updateSQL.toString(), pss);

        // Buoc 16 Tra ve true neu so dong duoc cap nhat lon hon 0
        if (updateRow > 0) {
            return true;
        } else {
            // Buoc 17 Tra ve false neu khong co dong nao duoc cap nhat
            return false;
        }
    }

    public boolean updateCourtInfo(String badminton_court_id, String badminton_court_name,
            String badminton_court_status) {
        // Buoc 1 Tao StringBuilder de xay dung cau truy van SQL
        StringBuilder updateSQL = new StringBuilder();
        updateSQL.append("UPDATE [Court_Master].[dbo].[badminton_court] SET ");

        // Buoc 2 Bien kiem tra de xac dinh co tham so nao de cap nhat
        boolean hasParameters = true;

        // Buoc 3 Xay dung phan SET cho unit_number neu ton tai
        if (badminton_court_name != null && badminton_court_name != "") {
            updateSQL.append("badminton_court_name = ?");
            hasParameters = false;
        }

        // Buoc 4 Xay dung phan SET cho province neu ton tai
        if (badminton_court_status != null && badminton_court_status != "") {
            if (!hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("badminton_court_status = ?");
            hasParameters = false;
        }

        // Buoc 5 Kiem tra neu khong co tham so nao de cap nhat
        if (hasParameters) {
            return false;
        }

        // Buoc 6 Xay dung phan WHERE cho cau truy van SQL
        updateSQL.append(" WHERE badminton_court_id = ?");

        // Buoc 7 Tao PreparedStatementSetter de set gia tri cho cau truy van
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 1;

                // Buoc 10 Set gia tri cho unit_number neu ton tai
                if (badminton_court_name != null && badminton_court_name != "") {
                    ps.setString(index++, badminton_court_name);
                }

                // Buoc 11 Set gia tri cho ward neu ton tai
                if (badminton_court_status != null && badminton_court_status != "") {
                    ps.setString(index++, badminton_court_status);
                }

                // Buoc 14 Set gia tri cho addressId
                ps.setString(index, badminton_court_id);
            }
        };

        // Buoc 8 Thuc thi cau truy van va kiem tra so dong duoc cap nhat
        int updateRow = jdbcTemplate.update(updateSQL.toString(), pss);

        // Buoc 9 Tra ve true neu so dong duoc cap nhat lon hon 0
        if (updateRow > 0) {
            return true;
        } else {
            // Buoc 10 Tra ve false neu khong co dong nao duoc cap nhat
            return false;
        }

    }

    // Query lay ra cac payment detail record cua club trong vong mot thang.



    public List<QueryTotalRevenueMapper> getPaymentDetailList(DashBoardRequest dashBoardRequest) {
        String sql = "select pd.payment_time, pd.amount from badminton_club bcl\r\n" + //
                "inner join invoice iv on bcl.badminton_club_id = iv.badminton_club_id\r\n" + //
                "inner join payment_detail pd on iv.invoice_id = pd.invoice_id\r\n" + //
                "where bcl.badminton_club_id = ? AND (MONTH(pd.payment_time) = ? OR MONTH(pd.payment_time) = ?) AND (YEAR(pd.payment_time) = ? OR YEAR(pd.payment_time) = ?)";

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
        return jdbcTemplate.query(sql, pss, new QueryTotalRevenueRowMapper());

    }



    // Lay danh sach booking list.
    public List<LocalDate> getBookingTimeList(DashBoardRequest dashBoardRequest) {
        String sql = "select bs.booking_date from badminton_club bcl \r\n" + //
                "inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id\r\n" + //
                "inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                "where bcl.badminton_club_id = ? AND (MONTH(bs.booking_date) = ? or MONTH(bs.booking_date) = ?) AND (YEAR(bs.booking_date) = ? OR YEAR(bs.booking_date) = ?)";
        return jdbcTemplate.queryForList(sql, LocalDate.class, dashBoardRequest.getClubId(),
                dashBoardRequest.getPreviousMonth(), dashBoardRequest.getMonth(), dashBoardRequest.getPreviousYear(),
                dashBoardRequest.getYear());
    }



    // Lay
    public List<QueryTotalCustomerMapper> getRegisterCusList(DashBoardRequest dashBoardRequest) {
        String sql = "select count(*) as cus_num, t1.re_month from (\r\n" + //
                "select au.user_id, MONTH(pd.payment_time) as re_month from badminton_club bcl \r\n" + //
                "inner join invoice iv on bcl.badminton_club_id = iv.badminton_club_id\r\n" + //
                "inner join payment_detail pd on pd.invoice_id = iv.invoice_id\r\n" + //
                "inner join authenticated_user au on au.user_id = pd.user_id\r\n" + //
                "where bcl.badminton_club_id = ? AND (MONTH(pd.payment_time) = ? or MONTH(pd.payment_time) = ?) AND (YEAR(pd.payment_time) = ? OR YEAR(pd.payment_time) = ?) \r\n"
                + //
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



    public List<QueryBookingSlotMapper> getBookingDateNumber(DashBoardRequest dashBoardRequest) {
        String sql = "select t1.booking_date, COUNT(*) as number_of_booking from (\r\n" + //
                "select bs.booking_date from badminton_club bcl \r\n" + //
                "inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id\r\n" + //
                "inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                "where bcl.badminton_club_id = ? AND (MONTH(bs.booking_date) = ? or MONTH(bs.booking_date) = ?) AND (YEAR(bs.booking_date) = ? OR YEAR(bs.booking_date) = ?)\r\n"
                + //
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

    // Lay clubId cua courtmanager
    public String getClubId(String userId) {
        String sql = "select bcl.badminton_club_id from authenticated_user au\r\n" + //
                "inner join badminton_club bcl on au.user_id = bcl.court_manager_id\r\n" + //
                "where au.user_id = ?";

        return jdbcTemplate.queryForObject(sql, String.class, userId);
    }

    // Manager xem staff cua club

    public List<StaffAccountDTO> getAllStaff(String court_manager_id) {
        String sql = "SELECT [user_id]\r\n" + //
                "      ,[first_name]\r\n" + //
                "      ,[last_name]\r\n" + //
                "      ,[email]\r\n" + //
                "      ,[phone_number]\r\n" + //
                "      ,[birthday]\r\n" + //
                "      ,[avatar_image_url]\r\n" + //
                "  FROM [Court_Master].[dbo].[authenticated_user]\r\n" + //
                "  WHERE [court_manager_id]=?";

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, court_manager_id);
            }
        };

        return jdbcTemplate.query(sql, pss, new CourtManagerViewStaffRowMapper());
    }



    // Search, update, delete by ten, sdt
    // Search staff by name/phone
    public List<StaffAccountDTO> getStaffByNamePhone(SearchStaffByPhoneNameRequest SearchStaffByPhoneNameRequest) {
        String sql = "SELECT [user_id],\r\n" + //
                "       [first_name],\r\n" + //
                "       [last_name],\r\n" + //
                "       [email],\r\n" + //
                "       [phone_number],\r\n" + //
                "       [birthday],\r\n" + //
                "       [avatar_image_url]\r\n" + //
                "FROM [Court_Master].[dbo].[authenticated_user]\r\n" + //
                "WHERE [court_manager_id] = ?\r\n" + //
                "  AND ([first_name] LIKE ?\r\n" + //
                "       OR [last_name] LIKE ?\r\n" + //
                "       OR [phone_number] LIKE ?);";

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                // Set the court_manager_id parameter
                ps.setString(1, SearchStaffByPhoneNameRequest.getCourtManagerId());
                // Set the search term for name and phone number filtering
                String searchPattern = "%" + SearchStaffByPhoneNameRequest.getSearch() + "%";
                String court_manager_id = SearchStaffByPhoneNameRequest.getCourtManagerId();
                ps.setString(1, court_manager_id);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);
            }
        };

        return jdbcTemplate.query(sql, pss, new CourtManagerViewStaffRowMapper());
    }



    // Delete a staff by staff id, staff co courtmanagerid la id cua courtmanager
    public boolean isDeleteStaff(String staff_id, String court_manager_id) {
        String sql = "DELETE FROM [Court_Master].[dbo].[authenticated_user]\r\n" + //
                "WHERE [user_id] = ?\r\n" + //
                "AND [court_manager_id] = ?;\r\n" + //
                "";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, staff_id);
                ps.setString(2, court_manager_id);
            }
        };
        int updateRow = jdbcTemplate.update(sql, pss);
        if (updateRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Update staff info: first, last name, email, phone, birthday
    public boolean updateStaffInfo(UpdateStaffRequest UpdateStaffRequest) {

        String staffId = UpdateStaffRequest.getStaffId();
        String court_manager_id = UpdateStaffRequest.getCourtManagerId();
        String firstName = UpdateStaffRequest.getFirstName();
        String lastName = UpdateStaffRequest.getLastName();
        String email = UpdateStaffRequest.getEmail();
        String phoneNumber = UpdateStaffRequest.getPhoneNumber();
        String birthday = UpdateStaffRequest.getBirthday();

        // Step 1: Construct SQL query to update staff information
        StringBuilder updateSQL = new StringBuilder();

        // Step 2: Initial SQL query construction
        updateSQL.append("UPDATE [Court_Master].[dbo].[authenticated_user] SET ");

        // Step 3: Flag to check if any column needs to be updated
        boolean hasParameters = false;

        // Step 4: Construct SET clause for first name if present
        if (firstName != null && firstName != "") {
            updateSQL.append("first_name = ?");
            hasParameters = true;
        }

        // Step 5: Construct SET clause for last name if present
        if (lastName != null && lastName != "") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("last_name = ?");
            hasParameters = true;
        }

        // Step 6: Construct SET clause for email if present
        if (email != null && email !="") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("email = ?");
            hasParameters = true;
        }

        // Step 7: Construct SET clause for phone number if present
        if (phoneNumber != null && phoneNumber != "") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("phone_number = ?");
            hasParameters = true;
        }

        // Step 8: Construct SET clause for birthday if present
        if (birthday != null && birthday != "") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("birthday = ?");
            hasParameters = true;
        }

        // Step 9: Check if no parameters are present for update
        if (!hasParameters) {
            return false;
        }

        // Step 10: Construct WHERE clause for the SQL query
        updateSQL.append(" WHERE user_id = ? AND court_manager_id = ?");

        // Step 11: Create PreparedStatementSetter to set the values for the query
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 1;

                // Step 12: Set value for first name if present
                if (firstName != null && firstName != "") {
                    ps.setString(index++, firstName);
                }

                // Step 13: Set value for last name if present
                if (lastName != null && lastName != "") {
                    ps.setString(index++, lastName);
                }

                // Step 14: Set value for email if present
                if (email != null && email != "") {
                    ps.setString(index++, email);
                }

                // Step 15: Set value for phone number if present
                if (phoneNumber != null && phoneNumber != "") {
                    ps.setString(index++, phoneNumber);
                }

                // Step 16: Set value for birthday if present
                if (birthday != null && birthday != "") {
                    ps.setString(index++, birthday);
                }

                // Step 17: Set value for staffId and courtManagerId
                ps.setString(index++, staffId);
                ps.setString(index, court_manager_id);
            }
        };

        // Step 18: Execute the query and check the number of rows updated
        int updateRow = jdbcTemplate.update(updateSQL.toString(), pss);

        // Step 19: Return true if rows were updated
        if (updateRow > 0) {
            return true;
        } else {
            // Step 20: Return false if no rows were updated
            return false;
        }
    }

    public String getClubIdByMngId(String courtManagerId) {
        String sql = "select bcl.badminton_club_id from authenticated_user au\r\n" + //
                "inner join badminton_club bcl on au.user_id = bcl.court_manager_id\r\n" + //
                "where au.user_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, courtManagerId);
    }
    

    // Cho dong san, mo san: sua badminton_court_status

    // Xem booking slot + schedule giong nhu history
}
