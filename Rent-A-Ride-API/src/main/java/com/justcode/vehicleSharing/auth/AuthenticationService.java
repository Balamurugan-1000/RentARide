package com.justcode.vehicleSharing.auth;

import com.justcode.vehicleSharing.email.EmailService;
import com.justcode.vehicleSharing.email.EmailTemplateName;
import com.justcode.vehicleSharing.role.RoleRepository;
import com.justcode.vehicleSharing.security.JwtService;
import com.justcode.vehicleSharing.user.Token;
import com.justcode.vehicleSharing.user.TokenRepository;
import com.justcode.vehicleSharing.user.User;
import com.justcode.vehicleSharing.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwt;

    private final EmailService emailService;
    private final JwtService jwtService;

    public void register(RegisterationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("USER role was not initialized"));
        var user = User.builder()
                .Firstname(request.getFirstname())
                .Lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);


    }


    @Value("${application.mailing.frontend.activational-url}")
    private String activationUrl;

    private void sendValidationEmail(User user) throws MessagingException {
    var newToken = generateAndSaveActivationToken(user);
    emailService.sendEmail(
            user.getEmail(),
            user.FullName(),
            EmailTemplateName.ACTIVATE_ACCOUNT,
            activationUrl,
            newToken,
            "Account Activation"

    );


    }

    private String generateAndSaveActivationToken(User user){
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int j = 0; j< 6; j++){
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword())
        );

       var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName" , user.FullName());

        var jwtToken = jwtService.generateToken(claims  ,user);



        return AuthenticationResponse.builder().token(jwtToken)
                .build();
    }

//    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation TOken has been expired and new Token is sent!!!");
        }
        var user = savedToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
