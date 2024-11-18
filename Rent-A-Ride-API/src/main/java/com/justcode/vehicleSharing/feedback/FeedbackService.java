package com.justcode.vehicleSharing.feedback;

import com.justcode.vehicleSharing.common.PageResponse;
import com.justcode.vehicleSharing.exception.OperationNotPermittedException;
import com.justcode.vehicleSharing.user.User;
import com.justcode.vehicleSharing.vehicle.Vehicle;
import com.justcode.vehicleSharing.vehicle.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;
    private final VehicleRepository vehicleRepository;
    public Integer save(FeedbackRequest request, Authentication connectedUser) {

        Vehicle vehicle =
                vehicleRepository.findById(request.vehicleId()).orElseThrow(() -> new  EntityNotFoundException ("No  vehicle is found"));

        User user = (User) connectedUser.getPrincipal();

        if (!vehicle.isShareable() || vehicle.isArchived()){
            throw new OperationNotPermittedException("You cant give feedback to the archived or non shareable vehicle");
        }
        if(Objects.equals(vehicle.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cant give feedback to your own vehicle");
        }
        Feedback feedback = feedbackMapper.toFeedback(request);

        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbacksByVehicleId(int vehicleId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page,size);
        User user = (User) connectedUser.getPrincipal();
        Page<Feedback> feedbacks = feedbackRepository.findAllByVehicleId(vehicleId , pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map((f) -> feedbackMapper.toFeedbackResponse(f,user.getId())).toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst() ,
                feedbacks.isLast()
                );
    }
}
