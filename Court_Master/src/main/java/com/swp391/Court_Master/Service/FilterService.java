package com.swp391.Court_Master.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.swp391.Court_Master.RowMapper.ClubHomePageResponseRowMapper;
import com.swp391.Court_Master.dto.request.Respone.ClubHomePageResponse;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilterService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ClubHomePageResponse> filterNoTime(String nameOrUnitNumber, String province, String district, String ward){
        StringBuilder queryBuilder = new StringBuilder(" select t1.clubId, t1.clubName, t1.clubAddress, t1.clubImageName, t1.averagePrice from (\r\n" + //
                        "   select bc.badminton_club_id as clubId, bc.badminton_club_name as clubName, \r\n" + //
                        "   CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress, \r\n" + //
                        "   bci.image_url as clubImageName, AVG(pr.one_time_play + pr.flexible + pr.fixed)/3 AS averagePrice from badminton_club bc\r\n" + //
                        "   inner join badminton_club_image bci on bc.badminton_club_id = bci.badminton_club_id AND bci.is_main_avatar = 1\r\n" + //
                        "   inner join address ad on bc.address_id = ad.address_id\r\n" + //
                        "   inner join time_frame tf on bc.badminton_club_id = tf.badminton_club_id\r\n" + //
                        "   inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id\r\n" + //
                        "   group by bc.badminton_club_id, bc.badminton_club_name, CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province), bci.image_url\r\n" + //
                        ") as t1 where t1.clubId in (\r\n" + //
                        "\r\n" + //
                        "     select bcl.badminton_club_id from badminton_club bcl\r\n" + //
                        "     inner join address ad on bcl.address_id = ad.address_id\r\n" + //
                        "     where 1=1 \r\n" + //
                        "");

        List<Object> params = new ArrayList<>();
                    
        if(nameOrUnitNumber!=null){
            queryBuilder.append("and ((bcl.badminton_club_name like N'%' + ? + '%') or (ad.unit_number like N'%' + ? + '%')) \r\n" + //
                                "");
                                params.add(nameOrUnitNumber);
                                params.add(nameOrUnitNumber);
        }

        if(province!=null){
            queryBuilder.append("and ad.province like N'%' + ? + '%'");
            params.add(province);
        }

        if(district!=null){
            queryBuilder.append("and ad.district like N'%' + ? + '%'");
            params.add(district);
        }

        if(ward!=null){
            queryBuilder.append("     and ad.ward like N'%' + ? + '%'\r\n" + //
                                ")");
            params.add(ward);
        }

        String query = queryBuilder.toString();

         
         return jdbcTemplate.query(query, params.toArray(), new ClubHomePageResponseRowMapper());
    }

    public List<ClubHomePageResponse> filterFullField(String nameOrUnitNumber, String province, String district, String ward, LocalTime openedTime, LocalTime hoursOfExpect){
        StringBuilder queryBuilder = new StringBuilder("select t1.clubId, t1.clubName, t1.clubAddress, t1.clubImageName, t1.averagePrice from (\r\n" + //
                        "   select bc.badminton_club_id as clubId, bc.badminton_club_name as clubName, \r\n" + //
                        "   CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province) as clubAddress, \r\n" + //
                        "   bci.image_url as clubImageName, AVG(pr.one_time_play + pr.flexible + pr.fixed)/3 AS averagePrice from badminton_club bc\r\n" + //
                        "   inner join badminton_club_image bci on bc.badminton_club_id = bci.badminton_club_id AND bci.is_main_avatar = 1\r\n" + //
                        "   inner join address ad on bc.address_id = ad.address_id\r\n" + //
                        "   inner join time_frame tf on bc.badminton_club_id = tf.badminton_club_id\r\n" + //
                        "   inner join pricing_rule pr on tf.time_frame_id = pr.time_frame_id\r\n" + //
                        "   group by bc.badminton_club_id, bc.badminton_club_name, CONCAT(ad.unit_number,', ',ad.ward,', ',ad.district,', ',ad.province), bci.image_url\r\n" + //
                        ") as t1 where t1.clubId in (\r\n" + //
                        "\t select bcl.badminton_club_id from badminton_club bcl\r\n" + //
                        "\t inner join address ad on bcl.address_id = ad.address_id\r\n" + //
                        "\t inner join badminton_court bc on bc.badminton_club_id = bcl.badminton_club_id \r\n" + //
                        "\t inner join time_frame tf on tf.badminton_club_id = bcl.badminton_club_id\r\n" + //
                        "\t left join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                        "\t \t where 1=1 ");

        List<Object> params = new ArrayList<>();
        
        if(nameOrUnitNumber!=null){
            queryBuilder.append("and ((bcl.badminton_club_name like N'%' + ? + '%') or (ad.unit_number like N'%' + ? + '%')) \r\n" + //
                                "");
                                params.add(nameOrUnitNumber);
                                params.add(nameOrUnitNumber);
        }

        if(province!=null){
            queryBuilder.append("and ad.province like N'%' + ? + '%'");
            params.add(province);
        }

        if(district!=null){
            queryBuilder.append("and ad.district like N'%' + ? + '%'");
            params.add(district);
        }

        if(ward!=null){
            queryBuilder.append("     and ad.ward like N'%' + ? + '%'\r\n" + //
                                "");
            params.add(ward);
        }
        if(openedTime != null){
            queryBuilder.append("and tf.start_time = ?");
            Time openedTimeTime = Time.valueOf(openedTime);
            System.out.println(openedTimeTime);
            params.add(openedTimeTime);
        }
        if(hoursOfExpect!=null){
            Time hoursOfExpectTime = Time.valueOf((hoursOfExpect));
            params.add(hoursOfExpectTime);
            params.add(hoursOfExpectTime);
            queryBuilder.append("and bc.badminton_court_id not in (\r\n" + //
                                "\t \t    Select bc.badminton_court_id from badminton_club bcl \r\n" + //
                                "\t \t\tinner join badminton_court bc on bcl.badminton_club_id = bc.badminton_club_id\r\n" + //
                                "\t \t\tleft join booking_slot bs on bc.badminton_court_id = bs.badminton_court_id\r\n" + //
                                "\t \t \twhere (start_time <= ? and end_time > ?)\r\n" + //
                                "\t\t\tgroup by bc.badminton_court_id\r\n" + //
                                "\t\t )\r\n" + //
                                "");

        }

        queryBuilder.append("\t group by bcl.badminton_club_id\r\n" + //
                        ")");
        
        String query = queryBuilder.toString();

        
    

        return jdbcTemplate.query(query, params.toArray(), new ClubHomePageResponseRowMapper());

    }
}
