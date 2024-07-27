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
import com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper.AdminViewUserAccountsRowMapper;
import com.swp391.Court_Master.RowMapper.QueryCourtManagerScreenRowMapper.CourtManagerViewStaffRowMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryBookingSlotChartRowMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryTotalCustomerRowMapper;
import com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper.QueryTotalRevenueRowMapper;
import com.swp391.Court_Master.dto.request.Request.DashBoardRequest;
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
    public boolean updateClubInfo(String name, String description, Integer status, String clubId) {
        // Buoc 1 Tinh toan cau truy van SQL de cap nhat thong tin club
        StringBuilder updateSQL = new StringBuilder();

        // Buoc 2 Xay dung cau truy van SQL ban dau
        updateSQL.append("UPDATE [Court_Master].[dbo].[badminton_club] SET ");

        // Buoc 3 Bien kiem tra neu co cot nao can cap nhat
        boolean hasParameters = false;

        // Buoc 4 Xay dung phan SET cho name neu ton tai
        if (name != null) {
            updateSQL.append("badminton_club_name = ?");
            hasParameters = true;
        }

        // Buoc 5 Xay dung phan SET cho description neu ton tai
        if (description != null) {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("description = ?");
            hasParameters = true;
        }

        // Buoc 6 Xay dung phan SET cho status neu ton tai
        if (status != null) {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("badminton_club_status = ?");
            hasParameters = true;
        }

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
                if (name != null) {
                    ps.setString(index++, name);
                }

                // Buoc 11 Set gia tri cho description neu ton tai
                if (description != null) {
                    ps.setString(index++, description);
                }

                // Buoc 12 Set gia tri cho status neu ton tai
                if (status != null) {
                    ps.setInt(index++, status);
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
        if (badminton_court_name != null) {
            updateSQL.append("badminton_court_name = ?");
            hasParameters = false;
        }

        // Buoc 4 Xay dung phan SET cho province neu ton tai
        if (badminton_court_status != null) {
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
                if (badminton_court_name != null) {
                    ps.setString(index++, badminton_court_name);
                }

                // Buoc 11 Set gia tri cho ward neu ton tai
                if (badminton_court_status != null) {
                    ps.setString(index++, badminton_court_name);
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

    public List<QueryTotalRevenueMapper> getPaymentDetailLists(DashBoardRequest dashBoardRequest) {
        // Buoc 1 Tao cau truy van SQL de lay thong tin chi tiet thanh toan
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("select pd.payment_time, pd.amount ")
                .append("from badminton_club bcl ")
                .append("inner join invoice iv on bcl.badminton_club_id = iv.badminton_club_id ")
                .append("inner join payment_detail pd on iv.invoice_id = pd.invoice_id ")
                .append("where bcl.badminton_club_id = ? ")
                .append("AND (MONTH(pd.payment_time) = ? OR MONTH(pd.payment_time) = ?) ")
                .append("AND (YEAR(pd.payment_time) = ? OR YEAR(pd.payment_time) = ?)");

        // Buoc 2 Tao PreparedStatementSetter de set gia tri cho cau truy van
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                // Buoc 3 Set gia tri cho tham so clubId
                ps.setString(1, dashBoardRequest.getClubId());

                // Buoc 4 Set gia tri cho tham so previousMonth
                ps.setInt(2, dashBoardRequest.getPreviousMonth());

                // Buoc 5 Set gia tri cho tham so month
                ps.setInt(3, dashBoardRequest.getMonth());

                // Buoc 6 Set gia tri cho tham so previousYear
                ps.setInt(4, dashBoardRequest.getPreviousYear());

                // Buoc 7 Set gia tri cho tham so year
                ps.setInt(5, dashBoardRequest.getYear());
            }
        };

        // Buoc 8 Thuc thi cau truy van va lay danh sach ket qua
        List<QueryTotalRevenueMapper> paymentDetails = jdbcTemplate.query(
                sqlQuery.toString(), // Buoc 9 Chuyen cau truy van thanh string
                pss, // Buoc 10 Truyen PreparedStatementSetter
                new QueryTotalRevenueRowMapper() // Buoc 11 Truyen RowMapper de chuyen doi ket qua
        );

        // Buoc 12 Tra ve danh sach ket qua
        return paymentDetails;
    }

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

    // Lay danh sach booking list
    public List<LocalDate> getBookingTimeLists(DashBoardRequest dashBoardRequest) {
        // Buoc 1 Xay dung cau truy van SQL de lay danh sach ngay dat lich
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("select bs.booking_date ")
                .append("from badminton_club bcl ")
                .append("inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id ")
                .append("inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id ")
                .append("where bcl.badminton_club_id = ? ")
                .append("AND (MONTH(bs.booking_date) = ? OR MONTH(bs.booking_date) = ?) ")
                .append("AND (YEAR(bs.booking_date) = ? OR YEAR(bs.booking_date) = ?)");

        // Buoc 2 Tao danh sach tham so de truyen vao cau truy van
        Object[] params = new Object[] {
                dashBoardRequest.getClubId(), // Buoc 3 Set gia tri cho clubId
                dashBoardRequest.getPreviousMonth(), // Buoc 4 Set gia tri cho previousMonth
                dashBoardRequest.getMonth(), // Buoc 5 Set gia tri cho month
                dashBoardRequest.getPreviousYear(), // Buoc 6 Set gia tri cho previousYear
                dashBoardRequest.getYear() // Buoc 7 Set gia tri cho year
        };

        // Buoc 8 Thuc thi cau truy van va lay danh sach ngay dat lich
        List<LocalDate> bookingDates = jdbcTemplate.queryForList(
                sqlQuery.toString(), // Buoc 9 Chuyen cau truy van thanh string
                LocalDate.class, // Buoc 10 Xac dinh loai du lieu tra ve
                params // Buoc 11 Truyen danh sach tham so
        );

        // Buoc 12 Tra ve danh sach ngay dat lich
        return bookingDates;
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

    // Lay danh sach customer register
    public List<QueryTotalCustomerMapper> getRegisterCusLists(DashBoardRequest dashBoardRequest) {
        // Buoc 1 Xay dung cau truy van SQL de lay danh sach khach hang dang ky
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("select count(*) as cus_num, t1.re_month ")
                .append("from ( ")
                .append("select au.user_id, MONTH(pd.payment_time) as re_month ")
                .append("from badminton_club bcl ")
                .append("inner join invoice iv on bcl.badminton_club_id = iv.badminton_club_id ")
                .append("inner join payment_detail pd on pd.invoice_id = iv.invoice_id ")
                .append("inner join authenticated_user au on au.user_id = pd.user_id ")
                .append("where bcl.badminton_club_id = ? ")
                .append("AND (MONTH(pd.payment_time) = ? OR MONTH(pd.payment_time) = ?) ")
                .append("AND (YEAR(pd.payment_time) = ? OR YEAR(pd.payment_time) = ?) ")
                .append("group by au.user_id, MONTH(pd.payment_time) ")
                .append(") as t1 ")
                .append("group by t1.re_month");

        // Buoc 2 Tao PreparedStatementSetter de set gia tri cho cau truy van
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                // Buoc 3 Set gia tri cho tham so clubId
                ps.setString(1, dashBoardRequest.getClubId());

                // Buoc 4 Set gia tri cho tham so previousMonth
                ps.setInt(2, dashBoardRequest.getPreviousMonth());

                // Buoc 5 Set gia tri cho tham so month
                ps.setInt(3, dashBoardRequest.getMonth());

                // Buoc 6 Set gia tri cho tham so previousYear
                ps.setInt(4, dashBoardRequest.getPreviousYear());

                // Buoc 7 Set gia tri cho tham so year
                ps.setInt(5, dashBoardRequest.getYear());
            }
        };

        // Buoc 8 Thuc thi cau truy van va lay danh sach khach hang dang ky
        List<QueryTotalCustomerMapper> registerCustomers = jdbcTemplate.query(
                sqlQuery.toString(), // Buoc 9 Chuyen cau truy van thanh string
                pss, // Buoc 10 Truyen PreparedStatementSetter
                new QueryTotalCustomerRowMapper() // Buoc 11 Truyen RowMapper de chuyen doi ket qua
        );

        // Buoc 12 Tra ve danh sach khach hang dang ky
        return registerCustomers;
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

    // Lay booking date number
    public List<QueryBookingSlotMapper> getBookingDateNumbers(DashBoardRequest dashBoardRequest) {
        // Buoc 1 Xay dung cau truy van SQL de lay so luong dat lich theo ngay
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("select t1.booking_date, COUNT(*) as number_of_booking ")
                .append("from ( ")
                .append("select bs.booking_date ")
                .append("from badminton_club bcl ")
                .append("inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id ")
                .append("inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id ")
                .append("where bcl.badminton_club_id = ? ")
                .append("AND (MONTH(bs.booking_date) = ? OR MONTH(bs.booking_date) = ?) ")
                .append("AND (YEAR(bs.booking_date) = ? OR YEAR(bs.booking_date) = ?) ")
                .append(") as t1 ")
                .append("group by t1.booking_date");

        // Buoc 2 Tao PreparedStatementSetter de set gia tri cho cau truy van
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                // Buoc 3 Set gia tri cho tham so clubId
                ps.setString(1, dashBoardRequest.getClubId());

                // Buoc 4 Set gia tri cho tham so previousMonth
                ps.setInt(2, dashBoardRequest.getPreviousMonth());

                // Buoc 5 Set gia tri cho tham so month
                ps.setInt(3, dashBoardRequest.getMonth());

                // Buoc 6 Set gia tri cho tham so previousYear
                ps.setInt(4, dashBoardRequest.getPreviousYear());

                // Buoc 7 Set gia tri cho tham so year
                ps.setInt(5, dashBoardRequest.getYear());
            }
        };

        // Buoc 8 Thuc thi cau truy van va lay danh sach ket qua
        List<QueryBookingSlotMapper> bookingSlots = jdbcTemplate.query(
                sqlQuery.toString(), // Buoc 9 Chuyen cau truy van thanh string
                pss, // Buoc 10 Truyen PreparedStatementSetter
                new QueryBookingSlotChartRowMapper() // Buoc 11 Truyen RowMapper de chuyen doi ket qua
        );

        // Buoc 12 Tra ve danh sach ket qua
        return bookingSlots;
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
    public String getClubIds(String userId) {
        // Buoc 1 Xay dung cau truy van SQL de lay clubId cua user
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("select bcl.badminton_club_id ")
                .append("from authenticated_user au ")
                .append("inner join badminton_club bcl ")
                .append("on au.user_id = bcl.court_manager_id ")
                .append("where au.user_id = ?");

        // Buoc 2 Tao tham so de truyen vao cau truy van
        Object[] params = new Object[] {
                userId // Buoc 3 Set gia tri cho userId
        };

        // Buoc 4 Thuc thi cau truy van va lay clubId
        String clubId = jdbcTemplate.queryForObject(
                sqlQuery.toString(), // Buoc 5 Chuyen cau truy van thanh string
                String.class, // Buoc 6 Xac dinh loai du lieu tra ve
                params // Buoc 7 Truyen tham so
        );

        // Buoc 8 Tra ve clubId
        return clubId;
    }

    public String getClubId(String userId) {
        String sql = "select bcl.badminton_club_id from authenticated_user au\r\n" + //
                "inner join badminton_club bcl on au.user_id = bcl.court_manager_id\r\n" + //
                "where au.user_id = ?";

        return jdbcTemplate.queryForObject(sql, String.class, userId);
    }

    // Manager xem staff cua club
    public List<UserAccountDTO> getAllUserAccounts() {
        String sql = " SELECT user_id,first_name,last_name,email,phone_number,birthday,role,user_status,register_date,avatar_image_url\r\n"
                + //
                " FROM authenticated_user";

        return jdbcTemplate.query(sql, new AdminViewUserAccountsRowMapper());
    }

    public List<StaffAccountDTO> getAllStaff(String court_manager_id) {
        String sql = "SELECT TOP (1000) [user_id]\r\n" + //
                "      ,[first_name]\r\n" + //
                "      ,[last_name]\r\n" + //
                "      ,[email]\r\n" + //
                "      ,[phone_number]\r\n" + //
                "      ,[birthday]\r\n" + //
                "      ,[avatar_image_url]\r\n" + //
                "  FROM [Court_Master].[dbo].[authenticated_user]\r\n" + //
                "  WHERE [court_manager_id]=?";
        return jdbcTemplate.query(sql, new CourtManagerViewStaffRowMapper());
    }

    // Search, update, delete ten, sdt

}
