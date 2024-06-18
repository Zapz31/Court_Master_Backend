package com.swp391.Court_Master.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.swp391.Court_Master.dto.request.Respone.ImageResponseDTO;

public class ImageResponseRowMapper implements RowMapper<ImageResponseDTO>{

    @Override
    @Nullable
    public ImageResponseDTO mapRow(ResultSet arg0, int arg1) throws SQLException {
        String imageName = arg0.getString("image_url");
        return new ImageResponseDTO(imageName);
    }

}
