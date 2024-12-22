package com.justcode.vehicleSharing.vehicle;

import com.justcode.vehicleSharing.common.BaseEntity;
import com.justcode.vehicleSharing.feedback.Feedback;
import com.justcode.vehicleSharing.history.VehicleTransactionHistory;
import com.justcode.vehicleSharing.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle extends BaseEntity {


    private String carModel;
    private String ownerName;
    private String licensePlate;
    private String description;
    private String price;

    private String vehicleCover;

    @Pattern(
            regexp = "^\\+?[0-9]{10,12}$",
            message = "Invalid phone number. It should contain only digits and can include an optional '+' at the start, with a length between 10 and 15 digits."
    )
    private String phone;
    private boolean archived;
    private boolean shareable;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;


    @OneToMany(mappedBy = "vehicle")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "vehicle")
    private List<VehicleTransactionHistory> histories;

    @Transient
    public double getRate(){
        if(feedbacks == null || feedbacks.isEmpty()){
            return 0.0;
        }
        var rate = this.feedbacks.stream().mapToDouble(Feedback::getNote).average().orElse(0.0);

        return (double) Math.round(rate * 10.0) /10;
    }

}
