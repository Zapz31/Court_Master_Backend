package com.swp391.Court_Master.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.Entities.Court;

@Repository
public class StaffRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Staff kiem tra san trong
    public List<Court> bookedCourts(String clubId, LocalTime startTime, LocalTime endTime, LocalDate booking_date) {
        return null;
    }
}
