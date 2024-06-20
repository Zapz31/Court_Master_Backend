package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class ClubRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String saveImageNameToDB(String imageName, int isMainAvatar, String clubId){
        String uploadFileNotic = "Fail to upload";
        String sql = "insert into badminton_club_image(image_url,is_main_avatar, badminton_club_id)\r\n" + //
                        "values(?, ?, ?)";
        PreparedStatementSetter pss = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, imageName);
                ps.setInt(2, isMainAvatar);
                ps.setString(3, clubId);
            }
            
        };
        int updateRow = jdbcTemplate.update(sql,pss);
        if(updateRow > 0){
            uploadFileNotic = "Upload successfully";
        }
        return uploadFileNotic;
    }
}
