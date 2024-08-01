package com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.ClubDTO;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.UserAccountDTO;

public class AdminViewClubRowMapper implements RowMapper<ClubDTO> {
    @Override
    public ClubDTO mapRow(ResultSet arg0, int arg1) throws SQLException {
        // Extract the clubId from the ResultSet
        String clubId = arg0.getString("badminton_club_id");
        
        // Extract the clubName from the ResultSet
        String clubName = arg0.getString("badminton_club_name");
        
        // Extract the unitNumber from the ResultSet
        String unitNumber = arg0.getString("unit_number");
        
        // Extract the ward from the ResultSet
        String ward = arg0.getString("ward");
        
        // Extract the district from the ResultSet
        String district = arg0.getString("district");
        
        // Extract the province from the ResultSet
        String province = arg0.getString("province");
        
        // Extract the description from the ResultSet
        String description = arg0.getString("description");
        
        // Extract the clubStatus from the ResultSet
        String clubStatus = arg0.getString("badminton_club_status");
        
        // Extract the courtManagerId from the ResultSet
        String courtManagerId = arg0.getString("court_manager_id");

        //Extract the courtManagerFirstName from the ResultSet
        String courtManagerFirstName = arg0.getString("first_name");

        //Extract the courtManagerLastName from the ResultSet
        String courtManagerLastName = arg0.getString("last_name");
        
        // Create a new ClubDTO object using the extracted values
        ClubDTO club = new ClubDTO(clubId
        , clubName
        , unitNumber
        , ward
        , district
        , province
        , description
        , clubStatus
        , courtManagerId
        , courtManagerFirstName
        , courtManagerLastName);
        
        // Return the ClubDTO object
        return club;
    }
}
