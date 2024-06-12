package com.swp391.Court_Master.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.swp391.Court_Master.Repository.ProvincesRepository;
import com.swp391.Court_Master.RowMapper.DistrictFullNameResponseRowMapper;
import com.swp391.Court_Master.RowMapper.WardsFullNameResponseRowMapper;
import com.swp391.Court_Master.dto.request.Respone.DistrictFullNameResponse;
import com.swp391.Court_Master.dto.request.Respone.ProvincesFullNameResponse;
import com.swp391.Court_Master.dto.request.Respone.WardsFullNameResponse;

@Service
public class AddressService {
    @Autowired
    private ProvincesRepository provincesRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProvincesFullNameResponse> getAllProvincesByFullName() {
        List<ProvincesFullNameResponse> list = provincesRepository.getAllProvincesByFullName();
        return list;
    }

    public List<DistrictFullNameResponse> getAllDistrictsFullName(String provinceName) {
        String sql = "select d.full_name from districts d\r\n" + //
                "inner join provinces p on d.province_code = p.code\r\n" + //
                "where p.full_name = ?";

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setNString(1, provinceName);
            }
        };

        return jdbcTemplate.query(sql, pss, new DistrictFullNameResponseRowMapper());
    }

    public List<WardsFullNameResponse> getAllWardsFullName(String provinceFullName, String districtFullName) {
        String sql = "select w.full_name from wards w\r\n" + //
                "inner join districts d on w.district_code = d.code\r\n" + //
                "inner join provinces p on d.province_code = p.code\r\n" + //
                "where p.full_name = ? and d.full_name = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setNString(1, provinceFullName);
                ps.setNString(2, districtFullName);
            }
        };
        return jdbcTemplate.query(sql, pss, new WardsFullNameResponseRowMapper());
    }

}
