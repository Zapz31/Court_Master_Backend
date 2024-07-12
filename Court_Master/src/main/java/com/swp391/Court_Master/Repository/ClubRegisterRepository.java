package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.swp391.Court_Master.Entities.Address;
import com.swp391.Court_Master.Entities.BadmintonClub;
import com.swp391.Court_Master.Entities.BadmintonClubImage;
import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.Entities.PricingService;
import com.swp391.Court_Master.Entities.TimeFrame;

@Repository
public class ClubRegisterRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ============================== SETUP FOREIGN KEY==========================
    // Lay list AddressId
    public List<String> getAllAddressIds() {
        String sql = "select address_id from address";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    // Lay list BadmintonCLubId
    public List<String> getAllClubIds() {
        String sql = "select badminton_club_id from badminton_club";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    // Lay list TimeFrameId
    public List<String> getAllTimeFrameIds() {
        String sql = "select time_frame_id from time_frame";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    // ============================== TAO VA INSERT CAC DOI TUONG==========================
    // insert address
    @Transactional
    public void insertAddress(Address address) {
        try {
            String sql = "insert into address(unit_number, ward, district, province)\r\n" + //
                    "values(?, ?, ?, ?)";
            PreparedStatementSetter pss = new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setNString(1, address.getUnitNumber());
                    ps.setNString(2, address.getWard());
                    ps.setNString(3, address.getDistrict());
                    ps.setNString(4, address.getProvince());
                }

            };
            jdbcTemplate.update(sql, pss);
        } catch (Exception e) {
            System.out.println("Error at insertAddress in ClubRegisterRepository: " + e.getMessage());
        }
    }

    // insert club
    @Transactional
    public void insertBadmintonClub(BadmintonClub badmintonClub, String fkAddressId){
        try {
            String sql = "insert into badminton_club(badminton_club_name, address_id, description, court_manager_id)\r\n" + //
                        "values(?, ?, ?, ?)";
            PreparedStatementSetter pss = new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setNString(1, badmintonClub.getBadmintonClubName());
                    ps.setString(2, fkAddressId);
                    ps.setNString(3, badmintonClub.getDescription());
                    ps.setString(4, badmintonClub.getCourtManagerId());
                }
            
            };
            jdbcTemplate.update(sql, pss);
        } catch (Exception e) {
            System.out.println("Error at insertBadmintonClub on ClubRegisterRepository: " + e.getMessage());
        }
    }

    // insert hinh anh 
    @Transactional 
    public void insertImages(List<BadmintonClubImage> images){
        String sql = "insert into badminton_club_image(image_url, is_main_avatar, badminton_club_id)\r\n" + //
                        "values(?, ?, ?)";
        
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return images.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BadmintonClubImage badmintonClubImage = images.get(i);
                ps.setNString(1, badmintonClubImage.getImageUrl());
                ps.setInt(2, badmintonClubImage.getIsMainAvatar());
                ps.setString(3, badmintonClubImage.getBadmintonClubId());
            }           
        });
    }

    // insert court
    @Transactional
    public void insertCourt(List<Court> courts){
        String sql = "insert into badminton_court(badminton_court_name, badminton_club_id)\r\n" + //
                        "values(?, ?)";
        jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {

            @Override
            public int getBatchSize() {
                return courts.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Court court = courts.get(i);
                ps.setNString(1, court.getCourtName());
                ps.setString(2, court.getClubId());
            }
            
        });
    }

    @Transactional
    public void insertTimeFrame(List<TimeFrame> timeFrames){
        String sqlInsertTimeFrame = "insert into time_frame(start_time, end_time, badminton_club_id)\r\n" + //
                        "values(?, ?, ?)";
        try {
            jdbcTemplate.batchUpdate(sqlInsertTimeFrame, new BatchPreparedStatementSetter() {

                @Override
                public int getBatchSize() {
                    return timeFrames.size();
                }
    
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TimeFrame tf = timeFrames.get(i);
                    ps.setTime(1, Time.valueOf(tf.getStarTime()));
                    ps.setTime(2, Time.valueOf(tf.getEndTime()));
                    ps.setString(3, tf.getClubId());
                }
                
            });

        String sqlInsertPricingService = "insert into pricing_rule(day_of_week, time_frame_id, flexible, fixed, one_time_play)\r\n" + //
                        "values(?, ?, ?, ?, ?)";
            List<PricingService> pricingServices = new ArrayList<>();
            for(TimeFrame tf : timeFrames){
                for(PricingService ps : tf.getClubPricingRegister()){
                    pricingServices.add(ps);
                }
            }
            jdbcTemplate.batchUpdate(sqlInsertPricingService, new BatchPreparedStatementSetter() {

                @Override
                public int getBatchSize() {
                    return pricingServices.size();
                }

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    PricingService pricingService = pricingServices.get(i);
                    ps.setString(1, pricingService.getDateOfWeek());
                    ps.setString(2, pricingService.getTimeFrameId());
                    ps.setInt(3, pricingService.getFlexible());
                    ps.setInt(4, pricingService.getFixed());
                    ps.setInt(5, pricingService.getOneTimePlay());
                }
                
            });
        } catch (Exception e) {
            System.out.println("Error at insertTimeFrame in ClubRegisterRepository: " + e.getMessage());
        }
        



    }


}
