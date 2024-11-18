package com.justcode.vehicleSharing.vehicle;

import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {
    public static Specification<Vehicle> withOwnerId(Integer ownerId){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id") , ownerId));
    }
}
