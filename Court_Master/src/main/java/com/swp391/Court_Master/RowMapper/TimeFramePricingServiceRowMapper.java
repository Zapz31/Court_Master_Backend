package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.swp391.Court_Master.dto.request.Respone.TimeFramePricingServiceDTO;

public class TimeFramePricingServiceRowMapper implements RowMapper<TimeFramePricingServiceDTO> {

    @Override
    @Nullable
    public TimeFramePricingServiceDTO mapRow(ResultSet arg0, int arg1) throws SQLException {
        String clubId = arg0.getString("badminton_club_id");
        String timeFrameId = arg0.getString("time_frame_id");
        LocalTime starTime = arg0.getTime("start_time").toLocalTime();
        LocalTime endTime = arg0.getTime("end_time").toLocalTime();
        String dayOfWeek = arg0.getString("day_of_week");
        int flexible = arg0.getInt("flexible");
        int fixed = arg0.getInt("fixed");
        int oneTimePlay = arg0.getInt("one_time_play");


        return new TimeFramePricingServiceDTO(clubId, timeFrameId, starTime, endTime, dayOfWeek, flexible, fixed, oneTimePlay);
    }

}
