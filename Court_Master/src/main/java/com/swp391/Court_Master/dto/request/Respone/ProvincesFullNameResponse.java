package com.swp391.Court_Master.dto.request.Respone;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;

@Entity

@NamedNativeQuery(
    name = "Provinces.getAllProvincesFullName",
    query = "select full_name as fullName from provinces",
    resultSetMapping = "ProvincesList"
)
@SqlResultSetMapping(
    name = "ProvincesList",
    classes = @ConstructorResult(
        targetClass = ProvincesFullNameResponse.class,
        columns = {
            @ColumnResult(name = "fullName", type = String.class)
        }
    )
)
public class ProvincesFullNameResponse {
    @Id
    private String fullName;


    public ProvincesFullNameResponse(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    
}
