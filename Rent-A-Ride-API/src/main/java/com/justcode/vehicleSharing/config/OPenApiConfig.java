package com.justcode.vehicleSharing.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Balamurugan",
                        email = "rbalamurgan1000@gmail.com"
                ),
                description = "OpenApi documentaion for Springboot application of a vehicle renting website ",
                title = "OpenApi specification - Balamurugan",
                version = "1.0"

        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8088/api/v1"
                ),
                @Server(
                        description = "Production Environment",
                        url = "https://vehicleRent/api/v1"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuthentication"

                )
        }
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "bearerAuthentication",
        scheme = "bearer",
        description = "JWT authentication",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OPenApiConfig {

}
