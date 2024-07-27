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

    public List<UserAccountDTO> getAllUserAccount() {
        // Buoc 1 Xay dung cau truy van SQL de lay danh sach tai khoan nguoi dung
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append(" SELECT user_id, first_name, last_name, email, phone_number, ")
                .append("birthday, role, user_status, register_date, avatar_image_url ")
                .append("FROM authenticated_user");
    
        // Buoc 2 Thuc thi cau truy van va lay danh sach tai khoan nguoi dung
        List<UserAccountDTO> userAccounts = jdbcTemplate.query(
            sqlQuery.toString(), // Buoc 3 Chuyen cau truy van thanh string
            new AdminViewUserAccountsRowMapper() // Buoc 4 Truyen RowMapper de chuyen doi ket qua
        );
    
        // Buoc 5 Tra ve danh sach tai khoan nguoi dung
        return userAccounts;
    }
    

    
    // Hien thi customer
    // Hien thi manager
    // Hien thi staff kem courtmanager id
    // Tim bang userId,fist_name,last_name

    // Ban user
}
