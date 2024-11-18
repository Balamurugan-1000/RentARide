package com.justcode.vehicleSharing.feedback;



import lombok.*;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Builder
public class FeedbackResponse {
    private Double note;
    private String comment;
    private boolean ownFeedback;

}
