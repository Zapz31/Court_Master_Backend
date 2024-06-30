package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.PaymentDetail;

public class PaymentDetailHistoryRowMapper implements RowMapper<PaymentDetail>{

    @Override
    public PaymentDetail mapRow(ResultSet arg0, int arg1) throws SQLException {
        String paymentId = arg0.getString("payment_id");
        int amount = arg0.getInt("amount");
        LocalDateTime paymentTime = arg0.getTimestamp("payment_time").toLocalDateTime();
        String paymentMethod = arg0.getString("payment_method");
        return new PaymentDetail(paymentId, amount, paymentMethod, paymentTime);
    }

}
