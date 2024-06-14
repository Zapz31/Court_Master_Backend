package com.swp391.Court_Master.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.swp391.Court_Master.RowMapper.ClubHomePageResponseRowMapper;
import com.swp391.Court_Master.dto.request.Respone.ClubHomePageResponse;

@Service
public class ClubHomePageService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ClubHomePageResponse> getAllClubHomePage(){
        String sql = "select bc.badminton_club_id as clubId, bc.badminton_club_name as clubName, \r\n" + //
                        "CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress, \r\n" + //
                        "bci.image_url as clubImageName, AVG(pr.price_per_hour) AS averagePrice from badminton_club bc\r\n" + //
                        "inner join badminton_club_image bci on bc.badminton_club_id = bci.badminton_club_id AND bci.is_main_avatar = 1\r\n" + //
                        "inner join address ad on bc.address_id = ad.address_id\r\n" + //
                        "inner join time_frame tf on bc.badminton_club_id = tf.badminton_club_id\r\n" + //
                        "inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id\r\n" + //
                        "group by bc.badminton_club_id, bc.badminton_club_name, CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province), bci.image_url";
        return jdbcTemplate.query(sql, new ClubHomePageResponseRowMapper());
    }
}
