package com.justcode.vehicleSharing.auth;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class RegisterationRequest {

    @NotEmpty(message = "Firstname must not be empty")
    @NotBlank(message = "Firstname Must not be blank")
    private String Firstname;
    @NotEmpty(message = "Lastname must not be empty")
    @NotBlank(message = "Lastname Must not be blank")
    private String Lastname;
    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email must not be empty")
    @NotBlank(message = "Email Must not be blank")
    private String email;
    @NotEmpty(message = "Password must not be empty")
    @NotBlank(message = "Password Must not be blank")
    @Size(min = 8 , message = "Password must be 8 characters long minimum")
    private String password;}
