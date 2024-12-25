package com.justcode.vehicleSharing.vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface VehicleRepository extends JpaRepository<Vehicle , Integer>  , JpaSpecificationExecutor<Vehicle> {
    @Query("""
    SELECT vehicle
    FROM Vehicle vehicle
    WHERE vehicle.archived = false
      AND vehicle.shareable = true
      AND vehicle.owner.id != :userId
      AND vehicle.id NOT IN (
          SELECT history.vehicle.id
          FROM VehicleTransactionHistory history
          WHERE history.returned = false OR history.returnApproved = false
      )
""")
    Page<Vehicle> findAllDisplayableVehicles(Pageable pageable, Integer userId);

}