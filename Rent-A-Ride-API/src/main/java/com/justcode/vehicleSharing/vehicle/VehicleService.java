package com.justcode.vehicleSharing.vehicle;

import com.justcode.vehicleSharing.common.PageResponse;
import com.justcode.vehicleSharing.exception.OperationNotPermittedException;
import com.justcode.vehicleSharing.file.FileStorageService;
import com.justcode.vehicleSharing.history.VehicleTransactionHistory;
import com.justcode.vehicleSharing.history.VehicleTransactionHistoryRepository;
import com.justcode.vehicleSharing.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.justcode.vehicleSharing.vehicle.VehicleSpecification.withOwnerId;
import static org.hibernate.query.sqm.tree.SqmNode.log;


@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final VehicleTransactionHistoryRepository vehicleTransactionHistoryRepository;
    private final FileStorageService fileStorageService;

    public Integer save(VehicleRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Vehicle vehicle = vehicleMapper.toVehicle(request);
        vehicle.setOwner(user);
        return vehicleRepository.save(vehicle).getId();
    }

    public VehicleResponse findById(Integer vehicleId) {

        return vehicleRepository.findById(vehicleId).map(vehicleMapper::toVehicleResponse).orElseThrow(() -> new EntityNotFoundException("No Vehicle found with Id : " + vehicleId));
    }


    public PageResponse<VehicleResponse> findAllVehicle(int page , int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size , Sort.by("createdDate").descending());
        Page<Vehicle> vehicles = vehicleRepository.findAllDisplayableVehicles(pageable , user.getId());
        System.out.println(vehicles);
        List<VehicleResponse> vehicleResponse = vehicles.stream()
                .map(vehicleMapper::toVehicleResponse)
                .toList();
        System.out.println(vehicleResponse);

        return new PageResponse<>(
                vehicleResponse,
                vehicles.getNumber(),
                vehicles.getSize(),
                vehicles.getTotalElements(),
                vehicles.getTotalPages(),
                vehicles.isFirst(),
                vehicles.isLast()
        );
    }

    public PageResponse<VehicleResponse> findAllVehicleByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());

        Pageable pageable = PageRequest.of(page,size , Sort.by("createdDate").descending());
        Page<Vehicle> vehicles = vehicleRepository.findAll(withOwnerId(user.getId()),pageable);
        List<VehicleResponse> vehicleResponse = vehicles.stream()
                .map(vehicleMapper::toVehicleResponse)
                .toList();

        return new PageResponse<>(
                vehicleResponse,
                vehicles.getNumber(),
                vehicles.getSize(),
                vehicles.getTotalElements(),
                vehicles.getTotalPages(),
                vehicles.isFirst(),
                vehicles.isLast()
        );
    }


    public PageResponse<BorrowedVehicleResponse> findAllBorrowedVehicles(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size , Sort.by("createdDate").descending());

        Page<VehicleTransactionHistory> allBorrowedBooks = vehicleTransactionHistoryRepository.findAllBorrowedVehicles(pageable , user.getId());

        List<BorrowedVehicleResponse> borrowedVehicleResponse =
                allBorrowedBooks.stream().map(vehicleMapper::toBorrowedVehicleResponse).toList();
        return new PageResponse<>(
                borrowedVehicleResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public PageResponse<BorrowedVehicleResponse> findAllReturnedVehicles(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size , Sort.by("createdDate").descending());

        Page<VehicleTransactionHistory> allBorrowedBooks =
                vehicleTransactionHistoryRepository.findAllReturnedVehicles(pageable , user.getId());

        List<BorrowedVehicleResponse> borrowedVehicleResponse =
                allBorrowedBooks.stream().map(vehicleMapper::toBorrowedVehicleResponse).toList();
        return new PageResponse<>(
                borrowedVehicleResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public Integer updateShareableStatus(int vehicleId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException(
                "Vehicle not found"));
        if(!Objects.equals(user.getId(),vehicle.getOwner().getId())){
            throw new OperationNotPermittedException("You can not update other's vehicle Sharable status");
        }
            vehicle.setShareable(!vehicle.isShareable());
        vehicleRepository.save(vehicle);
        return vehicleId;
    }

    public Integer updateArchivedStatus(int vehicleId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException(
                "Vehicle not found"));
        if(!Objects.equals(user.getId(),vehicle.getOwner().getId())){
            throw new OperationNotPermittedException("You can not update other's vehicle Archived status");
        }
        vehicle.setArchived(!vehicle.isArchived());
        vehicleRepository.save(vehicle);
        return vehicleId;
    }

    public Integer borrowVehicle(Integer vehicleId, Authentication connectedUser) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        if(vehicle.isArchived() || !vehicle.isShareable()){
            throw new OperationNotPermittedException("The requested vehicle is not available");
        }
        User user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(user.getId(),vehicle.getOwner().getId())){
            throw new OperationNotPermittedException("You can not borrow your own Vehicle");
        }
        final boolean isAlreadyBorrowed = vehicleTransactionHistoryRepository.isAlreadyBorrowedByUser(vehicleId , user.getId());

        if(isAlreadyBorrowed){
            throw new OperationNotPermittedException("The requested vehicle is already borrowed");
        }

        VehicleTransactionHistory vehicleTransactionHistory =  VehicleTransactionHistory.builder()
                .user(user)
                .vehicle(vehicle)
                .returned(false)
                .returnApproved(false)
                .build();

        return  vehicleTransactionHistoryRepository.save(vehicleTransactionHistory).getId();

    }

    public Integer returnVehicle(int vehicleId, Authentication connectedUser) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
        if(vehicle.isArchived() || !vehicle.isShareable()){
            throw new OperationNotPermittedException("The requested vehicle is not available");
        }
        User user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(user.getId(),vehicle.getOwner().getId())){
            throw new OperationNotPermittedException("You can not return your own Vehicle");
        }
        VehicleTransactionHistory vehicleTransactionHistory =
                vehicleTransactionHistoryRepository.findByVehicleIdAndUserId(vehicleId , user.getId())
                        .orElseThrow(() -> new OperationNotPermittedException("You didn't borrowed this vehicle"))
                ;

        vehicleTransactionHistory.setReturned(true);

    return vehicleTransactionHistoryRepository.save(vehicleTransactionHistory).getId();


    }

    public Integer approveReturnBorrowedVehicle(int vehicleId, Authentication connectedUser) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
        if(vehicle.isArchived() || !vehicle.isShareable()){
            throw new OperationNotPermittedException("The requested vehicle is not available");
        }
        User user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(user.getId(),vehicle.getOwner().getId())){
            throw new OperationNotPermittedException("You can not return your own Vehicle");
        }
        VehicleTransactionHistory vehicleTransactionHistory =
                vehicleTransactionHistoryRepository.findByVehicleIdAndOwnerId(vehicleId , user.getId())
                        .orElseThrow(() -> new OperationNotPermittedException("The Vehicle is not returned yet. So " +
                                "you can't approve it "));


        vehicleTransactionHistory.setReturnApproved(true);


        return vehicleTransactionHistoryRepository.save(vehicleTransactionHistory).getId();
    }

//    public void uploadVehicleCoverPicture(MultipartFile file, Authentication connectedUser, int vehicleId) {
//        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
//        User user = ((User) connectedUser.getPrincipal());
//        var vehicleCover = fileStorageService.uploadFileToSupabase(file, user.getId());
//        vehicle.setVehicleCover(vehicleCover);
//        vehicleRepository.save(vehicle);
//
//    }
public Vehicle save(String url, Authentication connectedUser, int vehicleId) {
    // Retrieve the vehicle or throw an exception if not found
    Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

    // Update the cover URL
    vehicle.setVehicleCover(url);

    // Save to the database
    return  vehicleRepository.save(vehicle);
}


}
