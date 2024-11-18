package com.justcode.vehicleSharing.feedback;


import com.justcode.vehicleSharing.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {


    public Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .vehicle(Vehicle.builder()
                        .id(request.vehicleId())
                        .archived(false)
                        .shareable(false)
                        .build())
                
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy() ,id))
                .build();
    }
}
