package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper.AdminViewUserAccountsRowMapper;
import com.swp391.Court_Master.RowMapper.QueryCourtManagerScreenRowMapper.CourtManagerViewStaffRowMapper;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.UserAccountDTO;
import com.swp391.Court_Master.dto.request.Respone.CourManagerScreenView.StaffAccountDTO;

@Repository
public class AdminRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Quan ly tai khoan user
    // Hien thi all tai khoan user
    public List<UserAccountDTO> getAllUserAccount() {
        String sql = "\tSELECT [user_id]\r\n" + //
                "      ,[first_name]\r\n" + //
                "      ,[last_name]\r\n" + //
                "      ,[email]\r\n" + //
                "      ,[phone_number]\r\n" + //
                "      ,[birthday]\r\n" + //
                "      ,[role]\r\n" + //
                "      ,[user_status]\r\n" + //
                "      ,[register_date]\r\n" + //
                "      ,[avatar_image_url]\r\n" + //
                "  FROM [Court_Master].[dbo].[authenticated_user]";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

            }
        };
        return jdbcTemplate.query(sql, new AdminViewUserAccountsRowMapper());
    }

    // Hien thi all customer 1
    public List<UserAccountDTO> getAllCustomerAccount() {
        // Define the SQL query to retrieve customer account information
        String sqlQueryToFetchCustomerAccounts = 
              "\tSELECT [user_id]\r\n" + // Selecting user ID
              "      ,[first_name]\r\n" + // Selecting user's first name
              "      ,[last_name]\r\n" + // Selecting user's last name
              "      ,[email]\r\n" + // Selecting user's email address
              "      ,[phone_number]\r\n" + // Selecting user's phone number
              "      ,[birthday]\r\n" + // Selecting user's date of birth
              "      ,[role]\r\n" + // Selecting user's role
              "      ,[user_status]\r\n" + // Selecting user's status
              "      ,[register_date]\r\n" + // Selecting the date the user registered
              "      ,[avatar_image_url]\r\n" + // Selecting URL for user's avatar image
              "  FROM [Court_Master].[dbo].[authenticated_user]\r\n" + // Table from which to select
              "  WHERE [role] = '1'"; // Filtering users based on role
    
        // Create a PreparedStatementSetter to set parameters in the query
        PreparedStatementSetter preparedStatementSetterForCustomerAccounts = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatementForCustomerAccounts) throws SQLException {
                // This method sets the parameters for the PreparedStatement
                // No parameters are needed for this query
            }
        };
    
        // Store the SQL query in a variable
        String sqlQuery = sqlQueryToFetchCustomerAccounts;
    
        // Store the PreparedStatementSetter in a variable
        PreparedStatementSetter pss = preparedStatementSetterForCustomerAccounts;
    
        // Create an instance of AdminViewUserAccountsRowMapper to map result set to UserAccountDTO
        AdminViewUserAccountsRowMapper userAccountRowMapper = new AdminViewUserAccountsRowMapper();
    
        // Store the row mapper in a variable
        AdminViewUserAccountsRowMapper rowMapper = userAccountRowMapper;
    
        // Execute the query and retrieve the list of UserAccountDTO objects
        List<UserAccountDTO> userAccountDTOList = jdbcTemplate.query(
            sqlQuery,      // SQL query to execute
            pss,           // PreparedStatementSetter for setting query parameters
            rowMapper      // RowMapper for mapping result set rows to UserAccountDTO objects
        );
    
        // Determine the number of customer accounts fetched
        int numberOfCustomerAccounts = userAccountDTOList.size();
    
        // Return the list of UserAccountDTO objects
        return userAccountDTOList;
    }
    
    // Hien thi all manager 2
    public List<UserAccountDTO> getAllCourtManagerAccount() {
        // Define the SQL query to retrieve customer account information
        String sqlQueryToFetchCustomerAccounts = 
              "\tSELECT [user_id]\r\n" + // Selecting user ID
              "      ,[first_name]\r\n" + // Selecting user's first name
              "      ,[last_name]\r\n" + // Selecting user's last name
              "      ,[email]\r\n" + // Selecting user's email address
              "      ,[phone_number]\r\n" + // Selecting user's phone number
              "      ,[birthday]\r\n" + // Selecting user's date of birth
              "      ,[role]\r\n" + // Selecting user's role
              "      ,[user_status]\r\n" + // Selecting user's status
              "      ,[register_date]\r\n" + // Selecting the date the user registered
              "      ,[avatar_image_url]\r\n" + // Selecting URL for user's avatar image
              "  FROM [Court_Master].[dbo].[authenticated_user]\r\n" + // Table from which to select
              "  WHERE [role] = '2'"; // Filtering users based on role
    
        // Create a PreparedStatementSetter to set parameters in the query
        PreparedStatementSetter preparedStatementSetterForCustomerAccounts = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatementForCustomerAccounts) throws SQLException {
                // This method sets the parameters for the PreparedStatement
                // No parameters are needed for this query
            }
        };
    
        // Store the SQL query in a variable
        String sqlQuery = sqlQueryToFetchCustomerAccounts;
    
        // Store the PreparedStatementSetter in a variable
        PreparedStatementSetter pss = preparedStatementSetterForCustomerAccounts;
    
        // Create an instance of AdminViewUserAccountsRowMapper to map result set to UserAccountDTO
        AdminViewUserAccountsRowMapper userAccountRowMapper = new AdminViewUserAccountsRowMapper();
    
        // Store the row mapper in a variable
        AdminViewUserAccountsRowMapper rowMapper = userAccountRowMapper;
    
        // Execute the query and retrieve the list of UserAccountDTO objects
        List<UserAccountDTO> userAccountDTOList = jdbcTemplate.query(
            sqlQuery,      // SQL query to execute
            pss,           // PreparedStatementSetter for setting query parameters
            rowMapper      // RowMapper for mapping result set rows to UserAccountDTO objects
        );
    
        // Determine the number of customer accounts fetched
        int numberOfCustomerAccounts = userAccountDTOList.size();
    
        // Return the list of UserAccountDTO objects
        return userAccountDTOList;
    }
    
    // Hien thi all staff 3, ko can hien courtmanagerid
    public List<UserAccountDTO> getAllStaffAccount() {
        // Define the SQL query to retrieve customer account information
        String sqlQueryToFetchCustomerAccounts = 
              "\tSELECT [user_id]\r\n" + // Selecting user ID
              "      ,[first_name]\r\n" + // Selecting user's first name
              "      ,[last_name]\r\n" + // Selecting user's last name
              "      ,[email]\r\n" + // Selecting user's email address
              "      ,[phone_number]\r\n" + // Selecting user's phone number
              "      ,[birthday]\r\n" + // Selecting user's date of birth
              "      ,[role]\r\n" + // Selecting user's role
              "      ,[user_status]\r\n" + // Selecting user's status
              "      ,[register_date]\r\n" + // Selecting the date the user registered
              "      ,[avatar_image_url]\r\n" + // Selecting URL for user's avatar image
              "  FROM [Court_Master].[dbo].[authenticated_user]\r\n" + // Table from which to select
              "  WHERE [role] = '3'"; // Filtering users based on role
    
        // Create a PreparedStatementSetter to set parameters in the query
        PreparedStatementSetter preparedStatementSetterForCustomerAccounts = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatementForCustomerAccounts) throws SQLException {
                // This method sets the parameters for the PreparedStatement
                // No parameters are needed for this query
            }
        };
    
        // Store the SQL query in a variable
        String sqlQuery = sqlQueryToFetchCustomerAccounts;
    
        // Store the PreparedStatementSetter in a variable
        PreparedStatementSetter pss = preparedStatementSetterForCustomerAccounts;
    
        // Create an instance of AdminViewUserAccountsRowMapper to map result set to UserAccountDTO
        AdminViewUserAccountsRowMapper userAccountRowMapper = new AdminViewUserAccountsRowMapper();
    
        // Store the row mapper in a variable
        AdminViewUserAccountsRowMapper rowMapper = userAccountRowMapper;
    
        // Execute the query and retrieve the list of UserAccountDTO objects
        List<UserAccountDTO> userAccountDTOList = jdbcTemplate.query(
            sqlQuery,      // SQL query to execute
            pss,           // PreparedStatementSetter for setting query parameters
            rowMapper      // RowMapper for mapping result set rows to UserAccountDTO objects
        );
    
        // Determine the number of customer accounts fetched
        int numberOfCustomerAccounts = userAccountDTOList.size();
    
        // Return the list of UserAccountDTO objects
        return userAccountDTOList;
    }
    // Tim bang userId,fist_name,last_name

    // Ban user: doi user_status tu 0 thanh 1
    // Unban user: doi user_status tu 1 thanh 0
}
