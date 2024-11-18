package com.justcode.vehicleSharing.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email must not be empty")
    @NotBlank(message = "Email Must not be blank")
    private String email;
    @NotEmpty(message = "Password must not be empty")
    @NotBlank(message = "Password Must not be blank")
    @Size(min = 8 , message = "Password must be 8 characters long minimum")
    private String password;

}


