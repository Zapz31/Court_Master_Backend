package com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.UserAccountDTO;;

public class AdminViewUserAccountsRowMapper implements RowMapper<UserAccountDTO> {

    @Override
    public UserAccountDTO mapRow(ResultSet arg0, int arg1) throws SQLException {
        String userId = arg0.getString("user_id");
        String firstName = arg0.getNString("first_name");
        String lastName = arg0.getNString("last_name");
        String email = arg0.getString("email");
        String phoneNumber = arg0.getString("phone_number");
        LocalDate birthDay = arg0.getDate("birthday").toLocalDate();
        String role = arg0.getString("role");
        String userStatus = arg0.getString("user_status");
        LocalDate registerDate = arg0.getDate("register_date").toLocalDate();
        String imageUrlString = arg0.getString("avatar_image_url");

        return new UserAccountDTO(userId, firstName, lastName, email, phoneNumber, birthDay, role, userStatus,
                registerDate, imageUrlString);
    }

}
