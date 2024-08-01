package com.swp391.Court_Master.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper.AdminViewUserAccountsRowMapper;
import com.swp391.Court_Master.RowMapper.QueryAdminScreenRowMapper.AdminViewClubRowMapper;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.SearchAccountByIdNamePhoneMail;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.SearchClubByIdNameRequest;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.UpdateAccountRequest;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.ClubDTO;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.UserAccountDTO;

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
        // SELECT [user_id]
        // ,[first_name]
        // ,[last_name]
        // ,[email]
        // ,[phone_number]
        // ,[birthday]
        // ,[role]
        // ,[user_status]
        // ,[register_date]
        // ,[avatar_image_url]
        // FROM [Court_Master].[dbo].[authenticated_user]
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
        String sqlQueryToFetchCustomerAccounts = "\tSELECT [user_id]\r\n" + // Selecting user ID
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

        // SELECT [user_id]
        // ,[first_name]
        // ,[last_name]
        // ,[email]
        // ,[phone_number]
        // ,[birthday]
        // ,[role]
        // ,[user_status]
        // ,[register_date]
        // ,[avatar_image_url]
        // FROM [Court_Master].[dbo].[authenticated_user]
        // WHERE [role]='1'

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

        // Create an instance of AdminViewUserAccountsRowMapper to map result set to
        // UserAccountDTO
        AdminViewUserAccountsRowMapper userAccountRowMapper = new AdminViewUserAccountsRowMapper();

        // Store the row mapper in a variable
        AdminViewUserAccountsRowMapper rowMapper = userAccountRowMapper;

        // Execute the query and retrieve the list of UserAccountDTO objects
        List<UserAccountDTO> userAccountDTOList = jdbcTemplate.query(
                sqlQuery, // SQL query to execute
                pss, // PreparedStatementSetter for setting query parameters
                rowMapper // RowMapper for mapping result set rows to UserAccountDTO objects
        );

        // Determine the number of customer accounts fetched
        int numberOfCustomerAccounts = userAccountDTOList.size();

        // Return the list of UserAccountDTO objects
        return userAccountDTOList;
    }

    // Hien thi all manager 2
    public List<UserAccountDTO> getAllCourtManagerAccount() {
        // Define the SQL query to retrieve customer account information
        String sqlQueryToFetchCustomerAccounts = "\tSELECT [user_id]\r\n" + // Selecting user ID
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

        // SELECT [user_id]
        // ,[first_name]
        // ,[last_name]
        // ,[email]
        // ,[phone_number]
        // ,[birthday]
        // ,[role]
        // ,[user_status]
        // ,[register_date]
        // ,[avatar_image_url]
        // FROM [Court_Master].[dbo].[authenticated_user]
        // WHERE [role]='2'

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

        // Create an instance of AdminViewUserAccountsRowMapper to map result set to
        // UserAccountDTO
        AdminViewUserAccountsRowMapper userAccountRowMapper = new AdminViewUserAccountsRowMapper();

        // Store the row mapper in a variable
        AdminViewUserAccountsRowMapper rowMapper = userAccountRowMapper;

        // Execute the query and retrieve the list of UserAccountDTO objects
        List<UserAccountDTO> userAccountDTOList = jdbcTemplate.query(
                sqlQuery, // SQL query to execute
                pss, // PreparedStatementSetter for setting query parameters
                rowMapper // RowMapper for mapping result set rows to UserAccountDTO objects
        );

        // Determine the number of customer accounts fetched
        int numberOfCustomerAccounts = userAccountDTOList.size();

        // Return the list of UserAccountDTO objects
        return userAccountDTOList;
    }

    // Hien thi all staff 3, ko can hien courtmanagerid
    public List<UserAccountDTO> getAllStaffAccount() {
        // Define the SQL query to retrieve customer account information
        String sqlQueryToFetchCustomerAccounts = "\tSELECT [user_id]\r\n" + // Selecting user ID
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

        // SELECT [user_id]
        // ,[first_name]
        // ,[last_name]
        // ,[email]
        // ,[phone_number]
        // ,[birthday]
        // ,[role]
        // ,[user_status]
        // ,[register_date]
        // ,[avatar_image_url]
        // FROM [Court_Master].[dbo].[authenticated_user]
        // WHERE [role]='3'

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

        // Create an instance of AdminViewUserAccountsRowMapper to map result set to
        // UserAccountDTO
        AdminViewUserAccountsRowMapper userAccountRowMapper = new AdminViewUserAccountsRowMapper();

        // Store the row mapper in a variable
        AdminViewUserAccountsRowMapper rowMapper = userAccountRowMapper;

        // Execute the query and retrieve the list of UserAccountDTO objects
        List<UserAccountDTO> userAccountDTOList = jdbcTemplate.query(
                sqlQuery, // SQL query to execute
                pss, // PreparedStatementSetter for setting query parameters
                rowMapper // RowMapper for mapping result set rows to UserAccountDTO objects
        );

        // Determine the number of customer accounts fetched
        int numberOfCustomerAccounts = userAccountDTOList.size();

        // Return the list of UserAccountDTO objects
        return userAccountDTOList;
    }

    // Show all account cua 1 role tu dropdown list
    public List<UserAccountDTO> getAllSpecificRoleAccount(String role) {
        // Define the SQL query to retrieve customer account information
        String sqlQueryToFetchCustomerAccounts = "\tSELECT [user_id]\r\n" + // Selecting user ID
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
                "  WHERE [role] = ?"; // Filtering users based on role

        // SELECT [user_id]
        // ,[first_name]
        // ,[last_name]
        // ,[email]
        // ,[phone_number]
        // ,[birthday]
        // ,[role]
        // ,[user_status]
        // ,[register_date]
        // ,[avatar_image_url]
        // FROM [Court_Master].[dbo].[authenticated_user]
        // WHERE [role]= ?

        // Create a PreparedStatementSetter to set parameters in the query
        PreparedStatementSetter preparedStatementSetterForCustomerAccounts = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatementForCustomerAccounts) throws SQLException {
                preparedStatementForCustomerAccounts.setString(1, role);
            }
        };

        // Store the SQL query in a variable
        String sqlQuery = sqlQueryToFetchCustomerAccounts;

        // Store the PreparedStatementSetter in a variable
        PreparedStatementSetter pss = preparedStatementSetterForCustomerAccounts;

        // Create an instance of AdminViewUserAccountsRowMapper to map result set to
        // UserAccountDTO
        AdminViewUserAccountsRowMapper userAccountRowMapper = new AdminViewUserAccountsRowMapper();

        // Store the row mapper in a variable
        AdminViewUserAccountsRowMapper rowMapper = userAccountRowMapper;

        // Execute the query and retrieve the list of UserAccountDTO objects
        List<UserAccountDTO> userAccountDTOList = jdbcTemplate.query(
                sqlQuery, // SQL query to execute
                pss, // PreparedStatementSetter for setting query parameters
                rowMapper // RowMapper for mapping result set rows to UserAccountDTO objects
        );

        // Determine the number of customer accounts fetched
        int numberOfCustomerAccounts = userAccountDTOList.size();

        // Return the list of UserAccountDTO objects
        return userAccountDTOList;
    }

    // Tim bang userId,fist_name,last_name,phone,email
    public List<UserAccountDTO> getAccountByIdNamePhoneMail(
            SearchAccountByIdNamePhoneMail SearchAccountByIdNamePhoneMail) {
        String sql = "        SELECT [user_id]\r\n" + //
                "        ,[first_name]\r\n" + //
                "        ,[last_name]\r\n" + //
                "        ,[email]\r\n" + //
                "        ,[phone_number]\r\n" + //
                "        ,[birthday]\r\n" + //
                "        ,[role]\r\n" + //
                "        ,[user_status]\r\n" + //
                "        ,[register_date]\r\n" + //
                "        ,[avatar_image_url]\r\n" + //
                "    FROM [Court_Master].[dbo].[authenticated_user]\r\n" + //
                "    WHERE [user_id] = ?\r\n" + //
                "    OR [first_name] LIKE ?\r\n" + //
                "    OR [last_name] LIKE ?\r\n" + //
                "    OR [email] LIKE ?\r\n" + //
                "    OR [phone_number]= ?";

        // SELECT [user_id]
        // ,[first_name]
        // ,[last_name]
        // ,[email]
        // ,[phone_number]
        // ,[birthday]
        // ,[role]
        // ,[user_status]
        // ,[register_date]
        // ,[avatar_image_url]
        // FROM [Court_Master].[dbo].[authenticated_user]
        // WHERE [user_id]=''
        // OR [first_name] LIKE '%%'
        // OR [last_name] LIKE '%%'
        // OR [email] LIKE '%%'
        // OR [phone_number]=''

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

                // Set the search term for name and phone number filtering
                String searchPattern = "%" + SearchAccountByIdNamePhoneMail.getSearch() + "%";
                String search = SearchAccountByIdNamePhoneMail.getSearch();
                ps.setString(1, search);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
                ps.setString(4, searchPattern);
                ps.setString(5, search);
            }
        };

        return jdbcTemplate.query(sql, pss, new AdminViewUserAccountsRowMapper());
    }

    // Edit account: tham khao ben CourtManagerRepository
    // fName, lName, mail, phone

    public boolean updateAccountInfo(UpdateAccountRequest UpdateAccountRequest) {

        String userId = UpdateAccountRequest.getUserId();
        String firstName = UpdateAccountRequest.getFirstName();
        String lastName = UpdateAccountRequest.getLastName();
        String email = UpdateAccountRequest.getEmail();
        String phoneNumber = UpdateAccountRequest.getPhoneNumber();
        String birthday = UpdateAccountRequest.getBirthday();

        // Step 1: Construct SQL query to update staff information
        StringBuilder updateSQL = new StringBuilder();

        // Step 2: Initial SQL query construction
        updateSQL.append("UPDATE [Court_Master].[dbo].[authenticated_user] SET ");

        // Step 3: Flag to check if any column needs to be updated
        boolean hasParameters = false;

        // Step 4: Construct SET clause for first name if present
        if (firstName != null && firstName != "") {
            updateSQL.append("first_name = ?");
            hasParameters = true;
        }

        // Step 5: Construct SET clause for last name if present
        if (lastName != null && lastName != "") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("last_name = ?");
            hasParameters = true;
        }

        // Step 6: Construct SET clause for email if present
        if (email != null && email != "") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("email = ?");
            hasParameters = true;
        }

        // Step 7: Construct SET clause for phone number if present
        if (phoneNumber != null && phoneNumber != "") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("phone_number = ?");
            hasParameters = true;
        }

        // Step 8: Construct SET clause for birthday if present
        if (birthday != null && birthday != "") {
            if (hasParameters) {
                updateSQL.append(", ");
            }
            updateSQL.append("birthday = ?");
            hasParameters = true;
        }

        // Step 9: Check if no parameters are present for update
        if (!hasParameters) {
            return false;
        }

        // Step 10: Construct WHERE clause for the SQL query
        updateSQL.append(" WHERE user_id = ?");

        // Step 11: Create PreparedStatementSetter to set the values for the query
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 1;

                // Step 12: Set value for first name if present
                if (firstName != null && firstName != "") {
                    ps.setString(index++, firstName);
                }

                // Step 13: Set value for last name if present
                if (lastName != null && lastName != "") {
                    ps.setString(index++, lastName);
                }

                // Step 14: Set value for email if present
                if (email != null && email != "") {
                    ps.setString(index++, email);
                }

                // Step 15: Set value for phone number if present
                if (phoneNumber != null && phoneNumber != "") {
                    ps.setString(index++, phoneNumber);
                }

                // Step 16: Set value for birthday if present
                if (birthday != null && birthday != "") {
                    ps.setString(index++, birthday);
                }

                // Step 17: Set value for staffId and courtManagerId
                ps.setString(index, userId);

            }
        };

        // Step 18: Execute the query and check the number of rows updated
        int updateRow = jdbcTemplate.update(updateSQL.toString(), pss);

        // Step 19: Return true if rows were updated
        if (updateRow > 0) {
            return true;
        } else {
            // Step 20: Return false if no rows were updated
            return false;
        }
    }

    // Ban user: doi user_status tu 0 thanh 1
    public boolean isBanAccount(String userId) {
        String sql = "UPDATE [Court_Master].[dbo].[authenticated_user]\r\n" + //
                "SET [user_status] ='1'\r\n" + //
                "wHERE [user_id] = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, userId);
            }
        };
        int updateRow = jdbcTemplate.update(sql, pss);
        if (updateRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Unban user: doi user_status tu 1 thanh 0
    public boolean isUnbanAccount(String userId) {
        String sql = "UPDATE [Court_Master].[dbo].[authenticated_user]\r\n" + //
                "SET [user_status] ='0'\r\n" + //
                "wHERE [user_id] = ?";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, userId);
            }
        };
        int updateRow = jdbcTemplate.update(sql, pss);
        if (updateRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Show all club
    public List<ClubDTO> getAllClub() {
        String sql = "SELECT\r\n" + //
                        "    c.[badminton_club_id],\r\n" + //
                        "    c.[badminton_club_name],\r\n" + //
                        "    a.[unit_number],\r\n" + //
                        "    a.[ward],\r\n" + //
                        "    a.[district],\r\n" + //
                        "    a.[province],\r\n" + //
                        "    c.[description],\r\n" + //
                        "    c.[badminton_club_status],\r\n" + //
                        "    c.[court_manager_id],\r\n" + //
                        "    u.[first_name],\r\n" + //
                        "    u.[last_name]\r\n" + //
                        "FROM \r\n" + //
                        "    [Court_Master].[dbo].[badminton_club] c\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[address] a\r\n" + //
                        "ON\r\n" + //
                        "    c.address_id = a.address_id\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[authenticated_user] u\r\n" + //
                        "ON\r\n" + //
                        "    c.court_manager_id = u.user_id;";
            //     SELECT
            //     c.[badminton_club_id],
            //     c.[badminton_club_name],
            //     a.[unit_number],
            //     a.[ward],
            //     a.[district],
            //     a.[province],
            //     c.[description],
            //     c.[badminton_club_status],
            //     c.[court_manager_id],
            //     u.[first_name],
            //     u.[last_name]
            // FROM 
            //     [Court_Master].[dbo].[badminton_club] c
            // LEFT JOIN
            //     [Court_Master].[dbo].[address] a
            // ON
            //     c.address_id = a.address_id
            // LEFT JOIN
            //     [Court_Master].[dbo].[authenticated_user] u
            // ON
            //     c.court_manager_id = u.user_id;
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

            }
        };
        return jdbcTemplate.query(sql, new AdminViewClubRowMapper());
    }

    // Show all Active clubs
    public List<ClubDTO> getAllActiveClub() {
        String sql = "SELECT\r\n" + //
                        "    c.[badminton_club_id],\r\n" + //
                        "    c.[badminton_club_name],\r\n" + //
                        "    a.[unit_number],\r\n" + //
                        "    a.[ward],\r\n" + //
                        "    a.[district],\r\n" + //
                        "    a.[province],\r\n" + //
                        "    c.[description],\r\n" + //
                        "    c.[badminton_club_status],\r\n" + //
                        "    c.[court_manager_id],\r\n" + //
                        "    u.[first_name],\r\n" + //
                        "    u.[last_name]\r\n" + //
                        "FROM \r\n" + //
                        "    [Court_Master].[dbo].[badminton_club] c\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[address] a\r\n" + //
                        "ON\r\n" + //
                        "    c.address_id = a.address_id\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[authenticated_user] u\r\n" + //
                        "ON\r\n" + //
                        "    c.court_manager_id = u.user_id\r\n" + //
                        "WHERE\r\n" + //
                        "\tc.badminton_club_status = '1'";
            //     SELECT
            //     c.[badminton_club_id],
            //     c.[badminton_club_name],
            //     a.[unit_number],
            //     a.[ward],
            //     a.[district],
            //     a.[province],
            //     c.[description],
            //     c.[badminton_club_status],
            //     c.[court_manager_id],
            //     u.[first_name],
            //     u.[last_name]
            // FROM 
            //     [Court_Master].[dbo].[badminton_club] c
            // LEFT JOIN
            //     [Court_Master].[dbo].[address] a
            // ON
            //     c.address_id = a.address_id
            // LEFT JOIN
            //     [Court_Master].[dbo].[authenticated_user] u
            // ON
            //     c.court_manager_id = u.user_id
            // WHERE
            //     c.badminton_club_status = '1';

        // Create an instance of PreparedStatementSetter
        PreparedStatementSetter preparedStatementSetter = new PreparedStatementSetter() {
            // Override the setValues method to set the values for the prepared statement
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                // Currently, no values to set in the prepared statement
            }
        };

        // Initialize an empty list of ClubDTO objects
        List<ClubDTO> activeClubsList = null;

        // Perform the database query to fetch active clubs
        activeClubsList = jdbcTemplate.query(sql, new AdminViewClubRowMapper());

        // Return the list of active clubs
        return activeClubsList;

    }

    // Show all Inactive clubs
    public List<ClubDTO> getAllInactiveClub() {
        String sql = "SELECT\r\n" + //
                        "    c.[badminton_club_id],\r\n" + //
                        "    c.[badminton_club_name],\r\n" + //
                        "    a.[unit_number],\r\n" + //
                        "    a.[ward],\r\n" + //
                        "    a.[district],\r\n" + //
                        "    a.[province],\r\n" + //
                        "    c.[description],\r\n" + //
                        "    c.[badminton_club_status],\r\n" + //
                        "    c.[court_manager_id],\r\n" + //
                        "    u.[first_name],\r\n" + //
                        "    u.[last_name]\r\n" + //
                        "FROM \r\n" + //
                        "    [Court_Master].[dbo].[badminton_club] c\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[address] a\r\n" + //
                        "ON\r\n" + //
                        "    c.address_id = a.address_id\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[authenticated_user] u\r\n" + //
                        "ON\r\n" + //
                        "    c.court_manager_id = u.user_id\r\n" + //
                        "WHERE\r\n" + //
                        "\tc.badminton_club_status = '0'";
            //     SELECT
            //     c.[badminton_club_id],
            //     c.[badminton_club_name],
            //     a.[unit_number],
            //     a.[ward],
            //     a.[district],
            //     a.[province],
            //     c.[description],
            //     c.[badminton_club_status],
            //     c.[court_manager_id],
            //     u.[first_name],
            //     u.[last_name]
            // FROM 
            //     [Court_Master].[dbo].[badminton_club] c
            // LEFT JOIN
            //     [Court_Master].[dbo].[address] a
            // ON
            //     c.address_id = a.address_id
            // LEFT JOIN
            //     [Court_Master].[dbo].[authenticated_user] u
            // ON
            //     c.court_manager_id = u.user_id
            // WHERE
            //     c.badminton_club_status = '0';

        // Create an instance of PreparedStatementSetter
        PreparedStatementSetter preparedStatementSetter = new PreparedStatementSetter() {
            // Override the setValues method to set the values for the prepared statement
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                // Currently, no values to set in the prepared statement
            }
        };

        // Initialize an empty list of ClubDTO objects
        List<ClubDTO> inactiveClubsList = null;

        // Perform the database query to fetch active clubs
        inactiveClubsList = jdbcTemplate.query(sql, new AdminViewClubRowMapper());

        // Return the list of active clubs
        return inactiveClubsList;

    }

    //Show clubs with a specific status 
    
    public List<ClubDTO> getAllStatusClub(String status) {
        String sql = "SELECT\r\n" + //
                        "    c.[badminton_club_id],\r\n" + //
                        "    c.[badminton_club_name],\r\n" + //
                        "    a.[unit_number],\r\n" + //
                        "    a.[ward],\r\n" + //
                        "    a.[district],\r\n" + //
                        "    a.[province],\r\n" + //
                        "    c.[description],\r\n" + //
                        "    c.[badminton_club_status],\r\n" + //
                        "    c.[court_manager_id],\r\n" + //
                        "    u.[first_name],\r\n" + //
                        "    u.[last_name]\r\n" + //
                        "FROM \r\n" + //
                        "    [Court_Master].[dbo].[badminton_club] c\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[address] a\r\n" + //
                        "ON\r\n" + //
                        "    c.address_id = a.address_id\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[authenticated_user] u\r\n" + //
                        "ON\r\n" + //
                        "    c.court_manager_id = u.user_id\r\n" + //
                        "WHERE\r\n" + //
                        "\tc.badminton_club_status = ? ;";
            //     SELECT
            //     c.[badminton_club_id],
            //     c.[badminton_club_name],
            //     a.[unit_number],
            //     a.[ward],
            //     a.[district],
            //     a.[province],
            //     c.[description],
            //     c.[badminton_club_status],
            //     c.[court_manager_id],
            //     u.[first_name],
            //     u.[last_name]
            // FROM 
            //     [Court_Master].[dbo].[badminton_club] c
            // LEFT JOIN
            //     [Court_Master].[dbo].[address] a
            // ON
            //     c.address_id = a.address_id
            // LEFT JOIN
            //     [Court_Master].[dbo].[authenticated_user] u
            // ON
            //     c.court_manager_id = u.user_id
            // WHERE
            //     c.badminton_club_status = ?; 1 0

        // Create an instance of PreparedStatementSetter
        PreparedStatementSetter preparedStatementSetter = new PreparedStatementSetter() {
            // Override the setValues method to set the values for the prepared statement
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, status);
            }
        };

        // Initialize an empty list of ClubDTO objects
        List<ClubDTO> statusClubsList = null;

        PreparedStatementSetter pss = preparedStatementSetter;

        // Perform the database query to fetch active clubs
        statusClubsList = jdbcTemplate.query(sql, pss, new AdminViewClubRowMapper());

        // Return the list of active clubs
        return statusClubsList;

    }


    //Search Club by ID, name
        public List<ClubDTO> searchClubByIdName(
            SearchClubByIdNameRequest SearchClubByIdNameRequest) {
        String sql = "SELECT\r\n" + //
                        "    c.[badminton_club_id],\r\n" + //
                        "    c.[badminton_club_name],\r\n" + //
                        "    a.[unit_number],\r\n" + //
                        "    a.[ward],\r\n" + //
                        "    a.[district],\r\n" + //
                        "    a.[province],\r\n" + //
                        "    c.[description],\r\n" + //
                        "    c.[badminton_club_status],\r\n" + //
                        "    c.[court_manager_id],\r\n" + //
                        "    u.[first_name],\r\n" + //
                        "    u.[last_name]\r\n" + //
                        "FROM \r\n" + //
                        "    [Court_Master].[dbo].[badminton_club] c\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[address] a\r\n" + //
                        "ON\r\n" + //
                        "    c.address_id = a.address_id\r\n" + //
                        "LEFT JOIN\r\n" + //
                        "    [Court_Master].[dbo].[authenticated_user] u\r\n" + //
                        "ON\r\n" + //
                        "    c.court_manager_id = u.user_id\r\n" + //
                        "WHERE\r\n" + //
                        "\tc.[badminton_club_id] = ?\r\n" + //
                        "OR\r\n" + //
                        "    c.[badminton_club_name] LIKE ?";

//         SELECT
//     c.[badminton_club_id],
//     c.[badminton_club_name],
//     a.[unit_number],
//     a.[ward],
//     a.[district],
//     a.[province],
//     c.[description],
//     c.[badminton_club_status],
//     c.[court_manager_id],
//     u.[first_name],
//     u.[last_name]
// FROM 
//     [Court_Master].[dbo].[badminton_club] c
// LEFT JOIN
//     [Court_Master].[dbo].[address] a
// ON
//     c.address_id = a.address_id
// LEFT JOIN
//     [Court_Master].[dbo].[authenticated_user] u
// ON
//     c.court_manager_id = u.user_id
// WHERE
// 	c.[badminton_club_id] = ''
// OR
//     c.[badminton_club_name] LIKE '%%'


        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

                // Set the search term for name and phone number filtering
                String searchPattern = "%" + SearchClubByIdNameRequest.getSearch() + "%";
                String search = SearchClubByIdNameRequest.getSearch();
                ps.setString(1, search);
                ps.setString(2, searchPattern);
            }
        };

        return jdbcTemplate.query(sql, pss, new AdminViewClubRowMapper());
    }

    //Edit club info, court info

}
