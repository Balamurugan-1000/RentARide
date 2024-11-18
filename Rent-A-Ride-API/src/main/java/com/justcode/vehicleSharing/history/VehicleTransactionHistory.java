package com.justcode.vehicleSharing.history;


import com.justcode.vehicleSharing.common.BaseEntity;
import com.justcode.vehicleSharing.user.User;
import com.justcode.vehicleSharing.vehicle.Vehicle;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTransactionHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private boolean returned;
    private boolean returnApproved;

}
