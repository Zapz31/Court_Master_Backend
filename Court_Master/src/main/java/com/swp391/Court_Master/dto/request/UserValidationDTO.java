package com.swp391.Court_Master.dto.request;

import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ColumnResult;
@NamedNativeQuery(
    name = "UserValidate.findByMailAndPhone",
    query = "select email, phone_number as phoneNumber from authenticated_user \r\n" + //
                "where email = :email or phone_number = :phoneNumber",
    resultSetMapping = "UserMatchCer"
)
@SqlResultSetMapping(
    name = "UserMatchCer",
    classes = @ConstructorResult(
        targetClass = UserValidationDTO.class,
        columns = {
            @ColumnResult(name = "email", type = String.class),
            @ColumnResult(name = "phoneNumber", type = String.class),            
        }
    )
)

@Entity
public class UserValidationDTO {
    @Id
    private String Id;
    
    private String email;
    private String phoneNumber;
    
    
    
    public UserValidationDTO(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    
}
