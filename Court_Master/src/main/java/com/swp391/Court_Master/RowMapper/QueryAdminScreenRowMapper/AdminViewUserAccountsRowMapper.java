package com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.cglib.core.Local;
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
        String role = arg0.getString("role");
        String userStatus = arg0.getString("user_status");
        String imageUrlString = arg0.getString("avatar_image_url");

        String bday = arg0.getString("birthday");
        String rday = arg0.getString("register_date");

        UserAccountDTO user = new UserAccountDTO();

        String birthDay = "";
        String registerDate = "";
        // Initialize with the current date
        LocalDate birthday = LocalDate.now(); // Sets birthday to today's date
        LocalDate registerdate = LocalDate.now(); // Sets registerdate to today's date

        if (bday == null) {
            birthDay = "N/A";
        } else {
            birthday = arg0.getDate("birthday").toLocalDate();
        }

        if (rday == null) {
            registerDate = "N/A";
        } else {
            registerdate = arg0.getDate("register_date").toLocalDate();
        }

        if (bday == null && rday == null) {
            user = new UserAccountDTO(userId, firstName, lastName, email, phoneNumber, birthDay, role, userStatus,
                    registerDate, imageUrlString);
        } else if (bday == null && rday != null) {
            user = new UserAccountDTO(userId, firstName, lastName, email, phoneNumber, birthDay, role, userStatus,
                    registerdate, imageUrlString);
        } else if (bday != null && rday == null) {
            user = new UserAccountDTO(userId, firstName, lastName, email, phoneNumber, birthday, role, userStatus,
                    registerDate, imageUrlString);
        } else if (bday != null && rday != null) {
            user = new UserAccountDTO(userId, firstName, lastName, email, phoneNumber, birthday, role, userStatus,
                    registerdate, imageUrlString);
        }

        return user;
    }

}
