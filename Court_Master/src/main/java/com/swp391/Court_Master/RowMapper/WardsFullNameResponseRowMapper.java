package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.swp391.Court_Master.dto.request.Respone.WardsFullNameResponse;

public class WardsFullNameResponseRowMapper implements RowMapper<WardsFullNameResponse>{

    @Override
    @Nullable
    public WardsFullNameResponse mapRow(ResultSet arg0, int arg1) throws SQLException {
        String fullName = arg0.getNString("full_name");

        return new WardsFullNameResponse(fullName);
        
    }

}
