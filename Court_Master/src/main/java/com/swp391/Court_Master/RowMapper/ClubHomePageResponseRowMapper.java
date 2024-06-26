package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.swp391.Court_Master.dto.request.Respone.ClubHomePageResponse;

public class ClubHomePageResponseRowMapper implements RowMapper<ClubHomePageResponse>{

    @Override
    @Nullable
    public ClubHomePageResponse mapRow(ResultSet arg0, int arg1) throws SQLException {
         String clubId = arg0.getString("clubId");
         String clubName = arg0.getString("clubName");
         String clubAddress = arg0.getString("clubAddress");
         String clubImageName = arg0.getString("clubImageName");
         int averagePrice = arg0.getInt("averagePrice");
         String courtManagerPhone = arg0.getString("phone_number");
        return new ClubHomePageResponse(clubId, clubName, clubAddress, averagePrice, clubImageName, courtManagerPhone);
    }



}
