package com.justcode.vehicleSharing.vehicle;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponse {
    private Integer id;
    private String carModel;
    private String ownerName;
    private String licensePlate;
    private String description;
    private String phone;
    private String price;
    private byte[] cover;
    private double rate;
    private boolean archived;
    private boolean shareable;
}
