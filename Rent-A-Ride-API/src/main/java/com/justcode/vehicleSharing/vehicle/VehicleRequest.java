package com.justcode.vehicleSharing.vehicle;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record VehicleRequest(
        Integer id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String carModel,
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String ownerName,
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        String  licensePlate,
        @NotNull(message = "103")
        @NotEmpty(message = "103")
        String description,
        @NotNull(message = "104")
        @NotEmpty(message = "104")
        String phone,
        String price,
        boolean shareable,
       String VehicleCover
) {

}
