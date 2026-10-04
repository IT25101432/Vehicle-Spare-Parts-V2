package com.spareparts.modules.compatibility.repository;

import com.spareparts.modules.compatibility.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByMakeAndModelAndYear(String make, String model, Integer year);
}
