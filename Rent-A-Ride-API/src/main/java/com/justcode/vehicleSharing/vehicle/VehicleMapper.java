package com.justcode.vehicleSharing.vehicle;



import com.justcode.vehicleSharing.history.VehicleTransactionHistory;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class VehicleMapper {
    public Vehicle toVehicle(VehicleRequest request) {
  
        return Vehicle.builder()
                .id(request.id())
                .phone(request.phone())
                .carModel(request.carModel())
                .ownerName(request.ownerName())
                .description(request.description())
                .licensePlate(request.licensePlate())
                .archived(false)
                .price(request.price())
                .shareable(request.shareable())
                .vehicleCover(request.cover())
                .build();
    }

    public VehicleResponse toVehicleResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .carModel(vehicle.getCarModel())
                .ownerName(vehicle.getOwnerName())
                .licensePlate(vehicle.getLicensePlate())
                .description(vehicle.getDescription())
                .rate(vehicle.getRate())
                .archived(vehicle.isArchived())
                .phone(vehicle.getPhone())
                .price(vehicle.getPrice())
                .shareable(vehicle.isShareable())
                .cover(vehicle.getVehicleCover())
                .build();
    }

    public BorrowedVehicleResponse toBorrowedVehicleResponse(VehicleTransactionHistory history) {
        return BorrowedVehicleResponse.builder()
                .id(history.getVehicle().getId())
                .carModel(history.getVehicle().getCarModel())
                .ownerName(history.getVehicle().getOwnerName())
                .licensePlate(history.getVehicle().getLicensePlate())
                 .rate(history.getVehicle().getRate())
                .returnApproved(history.isReturnApproved())
                .returned(history.isReturned())
                .price(history.getVehicle().getPrice())
                .build();
    }
}
