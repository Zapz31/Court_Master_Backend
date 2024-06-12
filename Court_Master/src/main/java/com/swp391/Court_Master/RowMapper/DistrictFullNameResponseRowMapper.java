package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.swp391.Court_Master.dto.request.Respone.DistrictFullNameResponse;

public class DistrictFullNameResponseRowMapper implements RowMapper<DistrictFullNameResponse>{

    @Override
    @Nullable
    public DistrictFullNameResponse mapRow(ResultSet arg0, int arg1) throws SQLException {
        
        String fullName = arg0.getNString("full_name");
        // String code = arg0.getString("code");
        // String provinceName = arg0.getString("province_name");  - Co bao nhieu dong trong table trong db can lay thi map qua cac thuoc tinh cua doi tuong ben day
        return new DistrictFullNameResponse(fullName);
    }

}
