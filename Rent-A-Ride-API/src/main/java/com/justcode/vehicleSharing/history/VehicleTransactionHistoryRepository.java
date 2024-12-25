package com.justcode.vehicleSharing.history;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface VehicleTransactionHistoryRepository extends JpaRepository<VehicleTransactionHistory , Integer> {
    @Query("""
        SELECT history \s
        FROM VehicleTransactionHistory history
        WHERE history.user.id = :userId
      \s
""")
    Page<VehicleTransactionHistory> findAllBorrowedVehicles(Pageable pageable, Integer userId);

    @Query("""
        SELECT history
        FROM VehicleTransactionHistory history
        WHERE history.vehicle.owner.id = :userId
""")
    Page<VehicleTransactionHistory> findAllReturnedVehicles(Pageable pageable, Integer userId);

    @Query("""  
            SELECT (COUNT(*) > 0)  as isBorrowed
            FROM VehicleTransactionHistory  vehicleTransactionHistory
            WHERE vehicleTransactionHistory.user.id = :userId
            AND vehicleTransactionHistory.vehicle.id = :vehicleId
            AND vehicleTransactionHistory.returnApproved = false
""")
    boolean isAlreadyBorrowedByUser(Integer vehicleId, Integer userId);

    @Query("""
    SELECT  transaction
    FROM  VehicleTransactionHistory transaction
    WHERE transaction.user.id = :userId
    AND transaction.vehicle.id = :vehicleId
    AND transaction.returned = false
    AND transaction.returnApproved = false
""")
    Optional<VehicleTransactionHistory> findByVehicleIdAndUserId(int vehicleId, Integer userId);

    @Query("""
    SELECT  transaction
    FROM  VehicleTransactionHistory transaction
    WHERE transaction.vehicle.owner.id = :userId
    AND transaction.vehicle.id = :vehicleId
    AND transaction.returned = true
    AND transaction.returnApproved = false
""")
    Optional<VehicleTransactionHistory> findByVehicleIdAndOwnerId(int vehicleId, Integer userId);
    @Query("""
    SELECT COUNT(h) > 0\s
    FROM VehicleTransactionHistory h\s
    WHERE h.user.id = :userId\s
      AND (h.returned = false OR h.returnApproved = false)
""")
    boolean existsByUserIdAndReturnedOrNotApproved(Integer userId);

}
