package com.justcode.vehicleSharing.vehicle;

import com.justcode.vehicleSharing.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicle")
public class VehicleController {
    private final  VehicleService service;
    @PostMapping
    public ResponseEntity<Integer> saveVehicle(
            @Valid @RequestBody VehicleRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.save(request , connectedUser));
    }

    @GetMapping("{vehicle-id}")
    public ResponseEntity<VehicleResponse> findVehicleById(
            @PathVariable("vehicle-id") Integer vehicleId
    ){
        return ResponseEntity.ok(service.findById(vehicleId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<VehicleResponse>> findAllVehicles(
            @RequestParam (name="page" , defaultValue = "0" , required = false) int page,
            @RequestParam (name="size" , defaultValue = "10" , required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllVehicle(page,size,connectedUser));
    }
    @GetMapping("/owner")
    public ResponseEntity<PageResponse<VehicleResponse>> findAllVehiclesByOwner(
            @RequestParam (name="page" , defaultValue = "0" , required = false) int page,
            @RequestParam (name="size" , defaultValue = "10" , required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllVehicleByOwner(page,size,connectedUser));

    }

   @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedVehicleResponse>> findAllBorrowedVehicles(
            @RequestParam (name="page" , defaultValue = "0" , required = false) int page,
            @RequestParam (name="size" , defaultValue = "10" , required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBorrowedVehicles(page,size,connectedUser));
    }
   @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedVehicleResponse>> findAllReturnedVehicles(
            @RequestParam (name="page" , defaultValue = "0" , required = false) int page,
            @RequestParam (name="size" , defaultValue = "10" , required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllReturnedVehicles(page,size,connectedUser));
    }

    @PatchMapping("/shareable/{vehicle-id}")
    public ResponseEntity<Integer> updateShaeableStatus(
            @PathVariable ("vehicle-id") int vehicleId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.updateShareableStatus(vehicleId , connectedUser));
    }

    @PatchMapping("/archived/{vehicle-id}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable ("vehicle-id") int vehicleId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.updateArchivedStatus(vehicleId , connectedUser));
    }

    @PostMapping("/borrow/{vehicle-id}")
    public ResponseEntity<Integer> borrowVehicle(
            @PathVariable("vehicle-id") int vehicleId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.borrowVehicle(vehicleId , connectedUser));

    }
    @PatchMapping("/borrow/return/{vehicle-id}")
    public ResponseEntity<Integer> returnVehicle(
            @PathVariable("vehicle-id") int vehicleId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.returnVehicle(vehicleId , connectedUser));

    } @PatchMapping("/borrow/return/approve/{vehicle-id}")
    public ResponseEntity<Integer> approveReturnVehicle(
            @PathVariable("vehicle-id") int vehicleId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.approveReturnBorrowedVehicle(vehicleId , connectedUser));
    }

    @PostMapping(value = "/covrt/{vehicle-id}" , consumes = "multipart/form-data")
    public ResponseEntity<?> uploadVehicleCoverPicture(
            @PathVariable("vehicle-id") int vehicleId,
            @Parameter()
            @RequestPart("file") MultipartFile file,

            Authentication connectedUser
    ){

        service.uploadVehicleCoverPicture(file,connectedUser,vehicleId);
        return ResponseEntity.accepted().build();
    }


}
