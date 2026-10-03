package com.spareparts.modules.compatibility.repository;

import com.spareparts.modules.compatibility.entity.CompatibilityMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompatibilityRepository extends JpaRepository<CompatibilityMapping, Long> {
    List<CompatibilityMapping> findByVehicleId(Long vehicleId);
    List<CompatibilityMapping> findByPartNumber(String partNumber);
    boolean existsByPartNumberAndVehicleId(String partNumber, Long vehicleId);
}
