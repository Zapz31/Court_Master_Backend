package com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalRevenueMapper;

public class QueryTotalRevenueRowMapper implements RowMapper<QueryTotalRevenueMapper>{

    @Override
    public QueryTotalRevenueMapper mapRow(ResultSet arg0, int arg1) throws SQLException {
        LocalDateTime paymentTime = arg0.getTimestamp("payment_time").toLocalDateTime();
        int amount = arg0.getInt("amount");
        return new QueryTotalRevenueMapper(paymentTime, amount);
    }

}
