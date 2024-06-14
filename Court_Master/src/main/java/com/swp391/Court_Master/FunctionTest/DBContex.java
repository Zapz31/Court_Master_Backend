package com.swp391.Court_Master.FunctionTest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DBContex {
    private final JdbcTemplate jdbcTemplate;
    public DBContex(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://GIAP/SQLEXPRESS:1433;databaseName=Court_Master"); 
        dataSource.setUsername("SA");
        dataSource.setPassword("12345");

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    
}
