package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.swp391.Court_Master.dto.request.Respone.DetailPageResponseDTO;

public class DetailPageResponseDTORowMapper implements RowMapper<DetailPageResponseDTO>{

    @Override
    @Nullable
    public DetailPageResponseDTO mapRow(ResultSet arg0, int arg1) throws SQLException {
        String clubAddress = arg0.getNString("clubAddress");
        String clubId = arg0.getString("badminton_club_id");
        String clubName = arg0.getNString("badminton_club_name");
        String clubDescription = arg0.getNString("description");
        String courtManagerPhone = arg0.getString("phone_number");
        return new DetailPageResponseDTO(clubAddress, clubName, clubId, clubDescription, courtManagerPhone);
    }

}
