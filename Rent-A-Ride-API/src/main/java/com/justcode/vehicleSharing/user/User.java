package com.justcode.vehicleSharing.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justcode.vehicleSharing.history.VehicleTransactionHistory;
import com.justcode.vehicleSharing.role.Role;
import com.justcode.vehicleSharing.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)

public class User implements UserDetails , Principal {
    @Id
    @GeneratedValue
    private Integer id;
    private String Firstname;
    private String Lastname;
    private LocalDate DateOfBirth;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean accountLocked;
    private boolean enabled;

    @OneToMany(mappedBy = "owner")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "user")
    private List<VehicleTransactionHistory> histories;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Role> roles;



    @CreatedDate
    @Column(nullable = false , updatable = false )
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false )
    private LocalDateTime lastModifiedDate;


    @Override
    public String getName() {
        return "";
    }

   @Override
   public String getPassword(){
        return password;
   }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());


    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }

    public String FullName(){
        return Firstname + " " +  Lastname;
    }

}
