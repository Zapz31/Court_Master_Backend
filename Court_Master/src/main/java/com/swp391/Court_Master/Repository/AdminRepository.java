package com.swp391.Court_Master.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper.AdminViewUserAccountsRowMapper;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.UserAccountDTO;

@Repository
public class AdminRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Quan ly tai khoan user
    // Hien thi all tai khoan user

    public List<UserAccountDTO> getAllUserAccounts() {
        String sql = " SELECT user_id,first_name,last_name,email,phone_number,birthday,role,user_status,register_date,avatar_image_url\r\n"
                + //
                " FROM authenticated_user";

        return jdbcTemplate.query(sql, new AdminViewUserAccountsRowMapper());
    }
    // Hien thi customer
    // Hien thi manager
    // Hien thi staff kem courtmanager id
    // Tim bang userId,fist_name,last_name

    // Ban user
}
