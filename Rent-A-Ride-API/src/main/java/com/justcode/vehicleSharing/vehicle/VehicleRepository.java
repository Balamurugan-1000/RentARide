package com.justcode.vehicleSharing.vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface VehicleRepository extends JpaRepository<Vehicle , Integer>  , JpaSpecificationExecutor<Vehicle> {
    @Query("""
    SELECT vehicle\s
    FROM Vehicle vehicle
    WHERE vehicle.archived =false\s
    AND vehicle.shareable = true\s
    AND vehicle.owner.id != :userId
""")
    Page<Vehicle> findAllDisplayableVehicles(Pageable pageable, Integer userId);
}