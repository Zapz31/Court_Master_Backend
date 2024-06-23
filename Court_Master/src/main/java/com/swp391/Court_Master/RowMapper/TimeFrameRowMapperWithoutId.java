package com.swp391.Court_Master.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;

import com.swp391.Court_Master.Entities.TimeFrame;

public class TimeFrameRowMapperWithoutId implements RowMapper<TimeFrame>{

    @Override
    public TimeFrame mapRow(ResultSet arg0, int arg1) throws SQLException {
        LocalTime startTime = arg0.getTime("start_time").toLocalTime();
        LocalTime endTime = arg0.getTime("end_time").toLocalTime();
        return new TimeFrame(null, startTime, endTime);
    }

}
