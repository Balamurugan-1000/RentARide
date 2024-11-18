package com.justcode.vehicleSharing.feedback;

import com.justcode.vehicleSharing.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedbackController {
    private final FeedbackService service;

    @PostMapping
    public ResponseEntity<Integer> saveFeedback(
            @Valid @RequestBody FeedbackRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.save(request , connectedUser));
    }

    @GetMapping("/vehicle/{vehicle-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> getAllFeedbackByVehicleId(
            @PathVariable("vehicle-id") int vehicleId,
            @RequestParam(name = "page" , defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size" , defaultValue = "10" , required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllFeedbacksByVehicleId(vehicleId , page, size , connectedUser));
    }


}
