package com.swp391.Court_Master.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.swp391.Court_Master.Entities.BookedDTO;
import com.swp391.Court_Master.Entities.BookingSchedule;
import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.Entities.Invoice;
import com.swp391.Court_Master.Entities.PaymentDetail;
import com.swp391.Court_Master.Entities.PlayableTimePayment;
import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.RowMapper.BookedDTOHistoryRowMapper;
import com.swp391.Court_Master.RowMapper.BookedDTOResponseOMRowMapper;
import com.swp391.Court_Master.RowMapper.BookedDTORowMapper;
import com.swp391.Court_Master.RowMapper.BookingScheduleHistoryRowMapper;
import com.swp391.Court_Master.RowMapper.PaymentDetailHistoryRowMapper;
import com.swp391.Court_Master.RowMapper.TimeFramePricingServiceRowMapper;
import com.swp391.Court_Master.RowMapper.TimeFrameRowMapperWithoutId;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.BookingScheduleHistory;
import com.swp391.Court_Master.dto.request.Respone.BookingSlotResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

@Repository
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TimeFramePricingServiceDTO> getClubTimeFramePricingByCourtId(String courtId, LocalTime startBooking,
            LocalTime endBooking) {
        String sql = "DECLARE @start_time_booking TIME, @end_time_booking TIME;\r\n" + //
                "SET @start_time_booking = ?; -- Set the start time booking value\r\n" + // ?????
                "SET @end_time_booking = ?; -- Set the end time booking value\r\n" + // //??????
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
                "                WHEN @start_time_booking >= start_time AND @start_time_booking < end_time AND @end_time_booking > end_time THEN 1\r\n"
                + //
                "                WHEN @start_time_booking < start_time AND @end_time_booking >= start_time AND @end_time_booking <= end_time THEN 1\r\n"
                + //
                "                ELSE 0\r\n" + //
                "            END AS is_valid\r\n" + //
                "        FROM\r\n" + //
                "            time_frame\r\n" + //
                "        WHERE\r\n" + //
                "            time_frame_id = tf.time_frame_id\r\n" + //
                "    ) cte\r\n" + //
                "WHERE\r\n" + //
                "    bc.badminton_court_id = ?\r\n" + // //????
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

    /*
     * Dua vao court id cua cac booking_slot duoc nguoi dung chon de tim tat ca cac
     * booking slot cua san do o cac ngay khac nhau
     */
    public List<BookedDTO> getBookedList(List<BookingSlotResponseDTO> perSlotRequestDTOs) {
        StringBuilder sql = new StringBuilder(
                "select bs.booking_slot_id, bs.start_time, bs.end_time, bs.booking_date, bc.badminton_court_id, bc.badminton_court_name from booking_slot bs\r\n"
                        + //
                        " inner join badminton_court bc on bc.badminton_court_id = bs.badminton_court_id ");

        List<Object> params = new ArrayList<>();
        for (int i = 0; i < perSlotRequestDTOs.size(); i++) {
            if (i == 0) {
                sql.append(" where (bc.badminton_court_id = ?");
                params.add(perSlotRequestDTOs.get(i).getCourtId());
            } else {
                sql.append(" or bc.badminton_court_id = ?");
                params.add(perSlotRequestDTOs.get(i).getCourtId());
            }
        }
        if(!perSlotRequestDTOs.isEmpty()){
            sql.append(") and bs.is_temp != 1");
        }
        sql.append(" order by bc.badminton_court_id");
        String sqlQuery = sql.toString();

        return jdbcTemplate.query(sqlQuery, params.toArray(), new BookedDTORowMapper());

    }

    /*
     * Lay danh sach thoi gian bat dau va thoi gian ket thuc cua tat ca cac time
     * frame thuoc mot club id
     */
    public List<TimeFrame> getTimeFrameByClubId(String clubId) {
        String sql = "select tf.start_time, tf.end_time from badminton_club bcl \r\n" + //
                "inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id\r\n" + //
                "where bcl.badminton_club_id = ? order by tf.start_time asc";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clubId);
            }

        };
        return jdbcTemplate.query(sql, pss, new TimeFrameRowMapperWithoutId());
    }

    /*
     * Lay danh sach tat ca cac booking_slot de hien thi ra man hinh dat lich
     * (onMount)
     */
    public List<BookedDTO> getAllBookingSlotByClubId(String clubId) {
        String sql = "select bc.badminton_court_id, bc.badminton_court_name, bs.start_time, bs.end_time, bs.booking_date, bs.booking_slot_id, bs.is_check_in, bs.price, CONCAT(au.first_name,' ',au.last_name ) as cusFullName, au.user_id from badminton_club bcl \r\n"
                + //
                "inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id\r\n" + //
                "inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                "inner join booking_schedule bsd on bs.booking_schedule_id = bsd.booking_schedule_id\r\n" + //
                "inner join authenticated_user au on bsd.customer_id = au.user_id\r\n" + //
                "where bcl.badminton_club_id = ? and bs.is_temp != 1\r\n" + //
                "order by start_time";

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clubId);
            }

        };

        return jdbcTemplate.query(sql, pss, new BookedDTOResponseOMRowMapper());

    }

    @Transactional
    public String insertBookingSchedule(BookingSchedule bookingSchedule) {
        String insertSQL = "insert into booking_schedule(customer_fullname, customer_phone_number, booking_schedule_status, start_date, end_date, schedule_type, customer_id, total_price, total_playing_time, remaining_amount)\r\n" + //
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String totalPlayHours = bookingSchedule.getTotalPlayingTime();
        String[] parts = totalPlayHours.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int totalPlayTime = hours * 60 + minutes;
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setNString(1, bookingSchedule.getCustomerFullName());
                ps.setString(2, bookingSchedule.getCustomerPhoneNumber());
                ps.setString(3, bookingSchedule.getBookingScheduleStatus());
                ps.setDate(4, Date.valueOf(bookingSchedule.getStartDate()));
                ps.setDate(5, Date.valueOf(bookingSchedule.getEndDate()));
                ps.setString(6, bookingSchedule.getScheduleType());
                ps.setString(7, bookingSchedule.getCustomerId());
                ps.setInt(8, bookingSchedule.getTotalPrice());
                ps.setInt(9, totalPlayTime);
                ps.setInt(10, bookingSchedule.getRemainingAmount());
            }

        };

        jdbcTemplate.update(insertSQL, pss);

        String selectSQL = "select TOP 1 booking_schedule_id from booking_schedule\r\n" + //
                "ORDER BY booking_schedule_id DESC";
        return jdbcTemplate.queryForObject(selectSQL, String.class);

    }

    @Transactional
    public void insertBookingSlots(List<BookingSlotResponseDTO> bookingSlotList, String bookingScheduleId) {
        String sql = "insert into booking_slot(start_time, end_time, booking_date, price, badminton_court_id, booking_schedule_id)\r\n"
                + //
                "values(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return bookingSlotList.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BookingSlotResponseDTO bookingSlotResponseDTO = bookingSlotList.get(i);
                ps.setTime(1, Time.valueOf(bookingSlotResponseDTO.getStartBooking()));
                ps.setTime(2, Time.valueOf(bookingSlotResponseDTO.getEndBooking()));
                ps.setDate(3, Date.valueOf(bookingSlotResponseDTO.getBookingDate()));
                ps.setInt(4, bookingSlotResponseDTO.getPrice());
                ps.setString(5, bookingSlotResponseDTO.getCourtId());
                ps.setString(6, bookingScheduleId);
            }

        });

    }

    @Transactional
    public String insertInvoice(Invoice invoice) {
        String sql = "insert into invoice(badminton_club_name, court_manager_phone, booking_phone_number, badminton_club_id, booking_schedule_id)\r\n"
                + //
                "values(?, ?, ?, ?, ?)";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setNString(1, invoice.getClubName());
                ps.setString(2, invoice.getCourtManagerPhone());
                ps.setString(3, invoice.getBookingPhoneNumber());
                ps.setString(4, invoice.getBadmintonClubId());
                ps.setString(5, invoice.getBookingScheduleId());
            }

        };

        jdbcTemplate.update(sql, pss);

        String sqlGetInvoiceId = "select TOP 1 invoice_id from invoice \r\n" + //
                "ORDER BY invoice_id DESC";
        return jdbcTemplate.queryForObject(sqlGetInvoiceId, String.class);
    }

    public void insertPaymentDetail(PaymentDetail paymentDetail) {
        String sql = "insert into payment_detail(payment_id, amount, payment_method, payment_time, invoice_id, user_id)\r\n"
                + //
                "values(?, ?, ?, ?, ?, ?)";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, paymentDetail.getPaymentId());
                ps.setInt(2, paymentDetail.getAmount());
                ps.setString(3, paymentDetail.getPaymentMethod());
                ps.setTimestamp(4, Timestamp.valueOf(paymentDetail.getPaymentTime()));
                ps.setString(5, paymentDetail.getInvoiceId());
                ps.setString(6, paymentDetail.getUserId());
            }

        };
        jdbcTemplate.update(sql, pss);

    }

    @Transactional
    public boolean isEnoughTime(String totalBookingPlayhoursString, String userId, String clubId) {
        boolean isEnough = true;
        int customerPlayingTime;
        String[] totalBookingPlayhoursPart = totalBookingPlayhoursString.split(":");
        int totalBookingHours = Integer.parseInt(totalBookingPlayhoursPart[0]);
        int totalBookingMinute = Integer.parseInt(totalBookingPlayhoursPart[1]);
        int totalBookingPlaytime = totalBookingHours * 60 + totalBookingMinute;

        String sqlGetPlayableTime = "select playable_time from customer_playable_time\r\n" + //
                "where customer_id = ? and badminton_club_id = ?";

        try {
            customerPlayingTime = jdbcTemplate.queryForObject(sqlGetPlayableTime, Integer.class, userId, clubId);
        } catch (Exception e) {
            customerPlayingTime = 0;
        }
        if (customerPlayingTime < totalBookingPlaytime) {
            isEnough = false;
            return isEnough;
        }
        int updateCustomerPlayingTime = customerPlayingTime - totalBookingPlaytime;

        String sqlUpdatePlayingTime = "update customer_playable_time\r\n" + //
                "set playable_time = ?\r\n" + //
                "where customer_id = ? and badminton_club_id = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, updateCustomerPlayingTime);
                ps.setString(2, userId);
                ps.setString(3, clubId);
            }

        };
        int updateRow = jdbcTemplate.update(sqlUpdatePlayingTime, pss);
        if (updateRow == 0) {
            isEnough = false;
        }
        return isEnough;
    }

    // LAY DANH SACH LICH SU DAT CUA BOOKING SCHEDULE DUA VAO CUSTOMER ID
    public List<BookingScheduleHistory> getBookingScheduleHistories(String customerId) {
        String sql = "select iv.badminton_club_name, iv.court_manager_phone, bs.booking_schedule_id, bs.booking_schedule_status, bs.start_date, bs.end_date, bs.schedule_type, bs.total_price, bs.total_playing_time, iv.booking_phone_number from booking_schedule bs\r\n"
                + //
                "inner join invoice iv on bs.booking_schedule_id = iv.booking_schedule_id\r\n" + //
                "where bs.customer_id = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, customerId);
            }

        };

        return jdbcTemplate.query(sql, pss, new BookingScheduleHistoryRowMapper());
    }

    // LAY DANH SACH LICH SU DAT CUA BOOKING SLOT DUA VAO SCHEDULE ID
    public List<BookedDTO> getBookingSlotsHistories(String scheduleId) {
        String sql = "select bs.booking_slot_id, bs.start_time, bs.end_time, bs.booking_date, bs.is_check_in, price, bs.badminton_court_id, bc.badminton_court_name from  booking_slot bs\r\n"
                + //
                "inner join badminton_court bc on bs.badminton_court_id = bc.badminton_court_id\r\n" + //
                "where booking_schedule_id = ? and bs.is_temp != 1";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, scheduleId);
            }

        };
        return jdbcTemplate.query(sql, pss, new BookedDTOHistoryRowMapper());
    }

    // LAY CHI TIET HOA DON THANH TOAN DUA VAO SCHEDULE ID VA SCHEDULE TYPE
    public List<PaymentDetail> getPaymentDetails(String scheduleId, String scheduleType) {
        String sql = "select pd.payment_id, pd.amount, pd.payment_time, pd.payment_method from booking_schedule bsd\r\n"
                + //
                "inner join invoice iv on bsd.booking_schedule_id = iv.booking_schedule_id\r\n" + //
                "inner join payment_detail pd on iv.invoice_id = pd.invoice_id\r\n" + //
                "where bsd.booking_schedule_id = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, scheduleId);
            }

        };
        return jdbcTemplate.query(sql, pss, new PaymentDetailHistoryRowMapper());
    }

    // NAP TIEN THANH TOAN GIO CHOI
    @Transactional
    public boolean isPayment(PlayableTimePayment playableTimePayment) {
        boolean isAddPaymentDetail = false;
        boolean isAddminutes = false;
        int minutes;

        String sqlAddPaymentDetail = "insert into playable_time_payment(payment_id, amount, minute_amount, payment_method, payment_time, customer_id, badminton_club_id, badminton_club_name)\r\n"
                + //
                "values(?, ?, ?, ?, ?, ?, ?, ?)";
        // aaaa', 123000, 800, 'ATM', '2024-07-01 09:18:20', 'STF000002', 'C0000002',
        // 'Cho Nhannnnn
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, playableTimePayment.getPaymentId());
                ps.setInt(2, playableTimePayment.getAmount());
                ps.setInt(3, playableTimePayment.getMinuteAmount());
                ps.setString(4, playableTimePayment.getPaymentMethod());
                ps.setTimestamp(5, Timestamp.valueOf(playableTimePayment.getPaymentTime()));
                ps.setString(6, playableTimePayment.getCustomerId());
                ps.setString(7, playableTimePayment.getBadmintonClubId());
                ps.setNString(8, playableTimePayment.getBadmintonClubName());
            }

        };
        int insertRow = jdbcTemplate.update(sqlAddPaymentDetail, pss);
        if (insertRow > 0) {
            isAddPaymentDetail = true;
        }

        try {
            String sqlGetAmont = "select playable_time from customer_playable_time\r\n" + //
                    "where customer_id = ? and badminton_club_id = ?";
            minutes = jdbcTemplate.queryForObject(sqlGetAmont, Integer.class, playableTimePayment.getCustomerId(),
                    playableTimePayment.getBadmintonClubId());
        } catch (Exception e) {
            minutes = 0;
        }
        if (minutes == 0) {
            String sqlAddCusPlayTime = "insert into customer_playable_time(customer_id, badminton_club_id, playable_time)\r\n"
                    + //
                    "values(?, ?, ?)";
            PreparedStatementSetter pss3 = new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, playableTimePayment.getCustomerId());
                    ps.setString(2, playableTimePayment.getBadmintonClubId());
                    ps.setInt(3, playableTimePayment.getMinuteAmount());
                }

            };

            int addRow = jdbcTemplate.update(sqlAddCusPlayTime, pss3);
            if (addRow > 0) {
                isAddminutes = true;
            }
        } else {
            int updateMinuteAmount = minutes + playableTimePayment.getMinuteAmount();
            String sqlUpdateCusPlayTime = "update customer_playable_time\r\n" + //
                    "set playable_time = ?\r\n" + //
                    "where customer_id = ? and badminton_club_id = ?";
            PreparedStatementSetter pss4 = new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, updateMinuteAmount);
                    ps.setString(2, playableTimePayment.getCustomerId());
                    ps.setString(3, playableTimePayment.getBadmintonClubId());
                }

            };
            int updateRow = jdbcTemplate.update(sqlUpdateCusPlayTime, pss4);
            if (updateRow > 0) {
                isAddminutes = true;
            }
        }
        if (isAddPaymentDetail && isAddminutes) {
            return true;
        } else {
            return false;
        }

    }

    // LAY TIEN THANH TOAN GIO CHOI CHO KIEU FLEXIBLE
    public int getFlexiblePrice(String clubId) {
        String sql = "select flexible from badminton_club bcl \r\n" + //
                "inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id\r\n" + //
                "inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id\r\n" + //
                "where bcl.badminton_club_id = ?\r\n" + //
                "group by flexible";

        int price = jdbcTemplate.queryForObject(sql, Integer.class, clubId);
        return price;
    }

    // updata booking schedule status
    @Transactional
    public boolean isUpdateStatus(String bookingScheduleId, String bookingScheduleStatus, int amount) {
        String sql = "update booking_schedule\r\n" + //
                        "set booking_schedule_status = ?, remaining_amount = remaining_amount - ?\r\n" + //
                        "where booking_schedule_id = ?";

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, bookingScheduleStatus);
                ps.setInt(2, amount);
                ps.setString(3, bookingScheduleId);
            }
        };
        int updateRow = jdbcTemplate.update(sql, pss);
        if (updateRow > 0) {
            return true;
        } else {
            return false;
        }
    }
    

}
