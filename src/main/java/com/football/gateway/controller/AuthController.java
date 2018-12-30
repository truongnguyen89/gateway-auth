package com.football.gateway.controller;

import com.football.gateway.exception.BadRequestException;
import com.football.gateway.model.AuthProvider;
import com.football.gateway.model.User;
import com.football.gateway.payload.*;
import com.football.gateway.repository.UserRepository;
import com.football.gateway.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail().toLowerCase().trim(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail().toLowerCase().trim())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail().toLowerCase().trim());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordBody changePasswordBody) {
        User user = userRepository.findFirstByEmail(changePasswordBody.getEmail().toLowerCase().trim());
        if (user == null)
            return new ResponseEntity<>("User not found with email " + changePasswordBody.getEmail(), HttpStatus.NOT_FOUND);
        else if (user.getProvider() != AuthProvider.local)
            return new ResponseEntity<>("User created by login with " + user.getProvider(), HttpStatus.BAD_REQUEST);
//        else if (!changePasswordBody.getOldPass().equals(passwordEncoder.encode(user.getPassword())))
//            return new ResponseEntity<>("Old pass invalid " + changePasswordBody.getOldPass(), HttpStatus.BAD_REQUEST);
        else if (changePasswordBody.getOldPass().equals(changePasswordBody.getNewPass()))
            return new ResponseEntity<>("New pass equals Old pass", HttpStatus.BAD_REQUEST);
        else
            user.setPassword(passwordEncoder.encode(changePasswordBody.getNewPass()));
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
    }

}
