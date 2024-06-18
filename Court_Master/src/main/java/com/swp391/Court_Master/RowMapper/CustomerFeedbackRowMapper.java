package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.swp391.Court_Master.dto.request.Respone.CustomerFeedback;

public class CustomerFeedbackRowMapper implements RowMapper<CustomerFeedback>{

    @Override
    @Nullable
    public CustomerFeedback mapRow(ResultSet arg0, int arg1) throws SQLException {
    //     private String customerName;
    // private String customerImageBase64;
    // private String comment;
    // private String ratingPoint;
    // private LocalDateTime ratingDateTime; 
        String customerName = arg0.getNString("customerName");
        String customerImageBase64 = arg0.getString("avatar_image_url");
        String comment = arg0.getNString("comment");
        int ratingPoint = arg0.getInt("rating_point");
        Timestamp ratingDateTimestamp = arg0.getTimestamp("rating_date_time");
        LocalDateTime ratingDateTime = ratingDateTimestamp.toLocalDateTime();

        return new CustomerFeedback(customerName, customerImageBase64, comment, ratingPoint, ratingDateTime);
    }

}
