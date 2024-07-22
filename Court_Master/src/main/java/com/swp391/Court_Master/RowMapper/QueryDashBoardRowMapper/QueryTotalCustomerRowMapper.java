package com.swp391.Court_Master.RowMapper.QueryDashBoardRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.QueryDashBoardMapper.QueryTotalCustomerMapper;

public class QueryTotalCustomerRowMapper implements RowMapper<QueryTotalCustomerMapper>{

    @Override
    public QueryTotalCustomerMapper mapRow(ResultSet arg0, int arg1) throws SQLException {
        int month = arg0.getInt("re_month");
        int numberOfCustomer = arg0.getInt("cus_num");
        return new QueryTotalCustomerMapper(month, numberOfCustomer);
    }

}
