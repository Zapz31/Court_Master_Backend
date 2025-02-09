package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.RowMapper.CourtRowMapper;
import com.swp391.Court_Master.RowMapper.CustomerFeedbackRowMapper;
import com.swp391.Court_Master.RowMapper.DetailPageResponseDTORowMapper;
import com.swp391.Court_Master.RowMapper.ImageResponseRowMapper;
import com.swp391.Court_Master.RowMapper.TimeFramePricingServiceRowMapper;
import com.swp391.Court_Master.dto.request.Respone.CustomerFeedback;
import com.swp391.Court_Master.dto.request.Respone.DetailPageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.ImageResponseDTO;
import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

@Repository
public class DetailClubRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ImageResponseDTO> getAllImageName(String clubId){
        String sql = "select bci.image_url from badminton_club bcl \r\n" + //
                        "inner join badminton_club_image bci on bcl.badminton_club_id = bci.badminton_club_id\r\n" + //
                        "where bcl.badminton_club_id = ?";
        
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {               
                ps.setString(1, clubId);
            }
            
        };

        return jdbcTemplate.query(sql, pss, new ImageResponseRowMapper());
    }

    @Transactional
    public DetailPageResponseDTO getClubInfo(String clubId, String userId){
        DetailPageResponseDTO detailPageResponseDTO = new DetailPageResponseDTO();
        String sqlSelectClub = "select au.phone_number, bcl.badminton_club_name, bcl.badminton_club_id, bcl.description, \r\n" + //
                        "CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress from badminton_club bcl\r\n" + //
                        "inner join address ad on bcl.address_id = ad.address_id\r\n" + //
                        "inner join authenticated_user au on au.user_id = bcl.court_manager_id\r\n" + //
                        "where bcl.badminton_club_id = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {               
                ps.setString(1, clubId);
            }
            
        };

        detailPageResponseDTO =  jdbcTemplate.query(sqlSelectClub, pss, new DetailPageResponseDTORowMapper()).get(0);

        String sqlGetPlayableTime = "select playable_time from customer_playable_time\r\n" + //
                        "where customer_id = ? and badminton_club_id = ?";

        String playingTime = "";
        try {
        int minutesPlayTime = jdbcTemplate.queryForObject(sqlGetPlayableTime, Integer.class, userId, clubId);
        int hours = minutesPlayTime / 60;
        int minutes = minutesPlayTime % 60;
        playingTime = String.format("%02d:%02d", hours, minutes);
        detailPageResponseDTO.setCustomerPlayableTime(playingTime);
        } catch (Exception e) {
            detailPageResponseDTO.setCustomerPlayableTime("00:00");
        }
        

        String sqlSelectCourt = "select bc.badminton_court_id, bc.badminton_court_name, badminton_court_status from badminton_court bc \r\n" + //
                        "inner join badminton_club bcl on bc.badminton_club_id = bcl.badminton_club_id\r\n" + //
                        "where bcl.badminton_club_id = ?";
        PreparedStatementSetter pss2 = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clubId);
            }
            
        };
        List<Court> courtList = jdbcTemplate.query(sqlSelectCourt, pss2, new CourtRowMapper());
        detailPageResponseDTO.setCourtList(courtList);

        return detailPageResponseDTO;
    }

    public List<TimeFramePricingServiceDTO> getClubTimeFramePricing(String clubId){
        String sql = "select bcl.badminton_club_id, tf.time_frame_id, tf.start_time, tf.end_time, pr.day_of_week, pr.one_time_play, pr.fixed, pr.flexible from badminton_club bcl \r\n" + //
                        "inner join time_frame tf on bcl.badminton_club_id = tf.badminton_club_id\r\n" + //
                        "inner join pricing_rule pr on pr.time_frame_id = tf.time_frame_id\r\n" + //
                        "where bcl.badminton_club_id = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clubId);
            }
            
        };
        return jdbcTemplate.query(sql, pss, new TimeFramePricingServiceRowMapper());
    }

    public List<CustomerFeedback> getFeedBacks(String clubId){
        String sql = "select CONCAT(au.first_name,' ',au.last_name) as customerName, \r\n" + //
                        "au.avatar_image_url, cr.comment, cr.rating_point, cr.rating_date_time from badminton_club bcl  \r\n" + //
                        "inner join customer_rate cr on bcl.badminton_club_id = cr.badminton_club_id\r\n" + //
                        "inner join authenticated_user au on cr.customer_id = au.user_id\r\n" + //
                        "where bcl.badminton_club_id = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clubId);
            }
            
        };

        return jdbcTemplate.query(sql, pss, new CustomerFeedbackRowMapper());
    }
}

