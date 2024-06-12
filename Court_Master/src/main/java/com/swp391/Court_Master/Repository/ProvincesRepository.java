package com.swp391.Court_Master.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.swp391.Court_Master.Entities.Provinces;
import com.swp391.Court_Master.dto.request.Respone.DistrictFullNameResponse;
import com.swp391.Court_Master.dto.request.Respone.ProvincesFullNameResponse;

@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, String>{
        @Query(name = "Provinces.getAllProvincesFullName", nativeQuery = true)
         List<ProvincesFullNameResponse> getAllProvincesByFullName();

        @Query(name = "DistrictFullNameResponse.getAllDistrictsFullName", nativeQuery = true)
        List<DistrictFullNameResponse> getAllDistrictsByFullName(@Param("province") String province);
}
