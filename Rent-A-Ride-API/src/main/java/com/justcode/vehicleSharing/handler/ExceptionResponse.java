package com.justcode.vehicleSharing.handler;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@RequiredArgsConstructor

@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class ExceptionResponse {

    private Integer businessErrorCode;
    private String businessErrorDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String , String> errors;

    public ExceptionResponse(Integer businessErrorCode, String businessErrorDescription, String error, Set<String> validationErrors, Map<String, String> errors) {
        this.businessErrorCode = businessErrorCode;
        this.businessErrorDescription = businessErrorDescription;
        this.error = error;
        this.validationErrors = validationErrors;
        this.errors = errors;
    }
}
