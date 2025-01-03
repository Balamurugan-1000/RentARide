package com.justcode.vehicleSharing;

import com.justcode.vehicleSharing.role.Role;
import com.justcode.vehicleSharing.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing()
@EnableAsync
public class VehicleSharingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleSharingApiApplication.class, args);




    }
    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository ){
        return args -> {
          if(roleRepository.findByName("USER").isEmpty()){
              roleRepository.save(
                      Role.builder().name("USER").build()
              );
          }
        };
    }
}
