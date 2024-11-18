package com.justcode.vehicleSharing.vehicle;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowedVehicleResponse {
    private Integer id;
    private String carModel;
    private String ownerName;
    private String licensePlate;
    private double rate;
    private boolean returned;
    private boolean returnApproved;
}
