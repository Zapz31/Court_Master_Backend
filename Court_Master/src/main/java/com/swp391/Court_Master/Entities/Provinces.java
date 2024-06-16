package com.swp391.Court_Master.Entities;

import java.time.LocalDate;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;




@Entity

@NamedNativeQuery(
    name = "Provinces.getAllProvinces",
    query = "select code, name, full_name as fullName from provinces order by full_name asc",
    resultSetMapping = "Provinces"
)
@SqlResultSetMapping(
    name = "Provinces",
    classes = @ConstructorResult(
        targetClass = Provinces.class,
        columns = {
            @ColumnResult(name = "code", type = String.class),
            @ColumnResult(name = "name", type = String.class),
            @ColumnResult(name = "fullName", type = String.class)
        }
    )
)
public class Provinces {
    @Id
    private String code;

    private String name;
    private String fullName;

    
    
    public Provinces(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }
    public Provinces(String code, String name, String fullName) {
        this.code = code;
        this.name = name;
        this.fullName = fullName;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    
}
