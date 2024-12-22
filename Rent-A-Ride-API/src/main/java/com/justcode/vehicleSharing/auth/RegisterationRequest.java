package com.justcode.vehicleSharing.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterationRequest {

    @NotBlank(message = "Firstname must not be empty")
    private String Firstname;

    @NotBlank(message = "Lastname must not be empty")
    private String Lastname;

    @Email(message = "Email is not formatted")
    @NotBlank(message = "Email must not be empty")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be 8 characters long minimum")
    private String password;
}
