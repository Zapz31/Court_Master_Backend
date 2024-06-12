package com.swp391.Court_Master.dto.request.Respone;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;

@NamedNativeQuery(
    name = "DistrictFullNameResponse.getAllDistrictsFullName",
    query = "select d.full_name as fullName from districts d\r\n" + //
                "inner join provinces p on d.province_code = p.code\r\n" + //
                "where p.full_name = :province",
    resultSetMapping = "DistrictList"
)
@SqlResultSetMapping(
    name = "DistrictList",
    classes = @ConstructorResult(
        targetClass = DistrictFullNameResponse.class,
        columns = {
            @ColumnResult(name = "fullName", type = String.class)
        }
    )
)
public class DistrictFullNameResponse {
    private String fullName;

    

    public DistrictFullNameResponse(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    
}
