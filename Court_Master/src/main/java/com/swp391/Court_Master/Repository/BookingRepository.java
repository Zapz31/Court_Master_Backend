package com.swp391.Court_Master.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
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
import com.swp391.Court_Master.Entities.Invoice;
import com.swp391.Court_Master.Entities.PaymentDetail;
import com.swp391.Court_Master.Entities.TimeFrame;
import com.swp391.Court_Master.RowMapper.BookedDTOResponseOMRowMapper;
import com.swp391.Court_Master.RowMapper.BookedDTORowMapper;
import com.swp391.Court_Master.RowMapper.TimeFramePricingServiceRowMapper;
import com.swp391.Court_Master.RowMapper.TimeFrameRowMapperWithoutId;
import com.swp391.Court_Master.dto.request.Request.PricePerSlotRequestDTO;
import com.swp391.Court_Master.dto.request.Respone.BookingSlotResponseDTO;
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

    /*
     * Dua vao court id cua cac booking_slot duoc nguoi dung chon de tim tat ca cac booking slot cua san do o cac ngay khac nhau
    */
    public List<BookedDTO> getBookedList(List<BookingSlotResponseDTO> perSlotRequestDTOs){
        StringBuilder sql = new StringBuilder("select bs.booking_slot_id, bs.start_time, bs.end_time, bs.booking_date, bc.badminton_court_id, bc.badminton_court_name from booking_slot bs\r\n" + //
                        "inner join badminton_court bc on bc.badminton_court_id = bs.badminton_court_id ");

        List<Object> params = new ArrayList<>();
        for(int i = 0; i < perSlotRequestDTOs.size(); i++){
            if(i==0){
                sql.append(" where bc.badminton_court_id = ?");
                params.add(perSlotRequestDTOs.get(i).getCourtId());
            } else {
                sql.append(" or bc.badminton_court_id = ?");
                params.add(perSlotRequestDTOs.get(i).getCourtId());
            }
        }
        sql.append(" order by bc.badminton_court_id");
        String sqlQuery = sql.toString();

        return jdbcTemplate.query(sqlQuery, params.toArray(), new BookedDTORowMapper());

    }

    /*
     * Lay danh sach thoi gian bat dau va thoi gian ket thuc cua tat ca cac time frame thuoc mot club id
    */
    public List<TimeFrame> getTimeFrameByClubId(String clubId){
        String sql = "select tf.start_time, tf.end_time from badminton_club bcl \r\n" + //
                        "inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id\r\n" + //
                        "where bcl.badminton_club_id = ? order by tf.start_time asc";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                 ps.setString(1, clubId);
            }
            
        };
        return jdbcTemplate.query(sql,pss, new TimeFrameRowMapperWithoutId());
    }

    /*
     * Lay danh sach tat ca cac booking_slot de hien thi ra man hinh dat lich (onMount)
    */
    public List<BookedDTO> getAllBookingSlotByClubId(String clubId){
        String sql = "select bc.badminton_court_id, bc.badminton_court_name, bs.start_time, bs.end_time, bs.booking_date, bs.booking_slot_id, bs.is_check_in, bs.price, CONCAT(au.first_name,' ',au.last_name ) as cusFullName, au.user_id from badminton_club bcl \r\n" + //
                        "inner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id\r\n" + //
                        "inner join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                        "inner join booking_schedule bsd on bs.booking_schedule_id = bsd.booking_schedule_id\r\n" + //
                        "inner join authenticated_user au on bsd.customer_id = au.user_id\r\n" + //
                        "where bcl.badminton_club_id = ?\r\n" + //
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
    public String insertBookingSchedule(BookingSchedule bookingSchedule){
        String insertSQL = "insert into booking_schedule(customer_fullname, customer_phone_number, booking_schedule_status, start_date, end_date, schedule_type, customer_id, total_price, total_playing_time)\r\n" + //
                        "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String totalPlayHours = bookingSchedule.getTotalPlayingTime();
        String[] parts = totalPlayHours.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int totalPlayTime = hours*60 + minutes;
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
               
            }
            
        };

        jdbcTemplate.update(insertSQL, pss);

        String selectSQL = "select TOP 1 booking_schedule_id from booking_schedule\r\n" + //
                        "ORDER BY booking_schedule_id DESC";
        return jdbcTemplate.queryForObject(selectSQL, String.class);
        
    }

    @Transactional
    public void insertBookingSlots(List<BookingSlotResponseDTO> bookingSlotList, String bookingScheduleId){
        String sql = "insert into booking_slot(start_time, end_time, booking_date, price, badminton_court_id, booking_schedule_id)\r\n" + //
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
    public String insertInvoice(Invoice invoice){
        String sql = "insert into invoice(badminton_club_name, court_manager_phone, booking_phone_number, badminton_club_id, booking_schedule_id)\r\n" + //
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

    public void insertPaymentDetail(PaymentDetail paymentDetail){
        String sql = "insert into payment_detail(payment_id, amount, payment_method, payment_time, invoice_id, user_id)\r\n" + //
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

    // @Transactional
    // public boolean isEnoughTime(String totalBookingPlayhours, String customerId, String clubId){
    //     boolean isEnough = true;


    // }


}
