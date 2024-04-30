package com.backend.bookstore.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.bookstore.entity.Role;
import com.backend.bookstore.entity.User;
import com.backend.bookstore.payload.request.AuthenticationRequest;
import com.backend.bookstore.payload.request.RegisterRequest;
import com.backend.bookstore.payload.response.AuthenticationResponse;
import com.backend.bookstore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepository userRepository;

        private final PasswordEncoder passwordEncoder;

        private final JwtService jwtService;

        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {

                var existingUser = userRepository.findByEmail(request.getEmail());

                if (existingUser == null) {

                        return AuthenticationResponse.builder()
                                        .message("Email is already taken")
                                        .build();

                } else {

                        var user = User.builder()
                                        .firstname(request.getFirstname())
                                        .lastname(request.getLastname())
                                        .email(request.getEmail())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .role(Role.USER)
                                        .build();

                        userRepository.save(user);
                        var jwtToken = jwtService.generateToken(user);

                        return AuthenticationResponse.builder()
                                        .jwtToken(jwtToken)
                                        .message("User Successfully Registered")
                                        .build();
                }
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

                if (user == null) {

                        return AuthenticationResponse.builder()
                                        .message("User is not Registered")
                                        .build();
                }

                var jwtToken = jwtService.generateToken(user);
                var email = request.getEmail();

                return AuthenticationResponse.builder()
                                .jwtToken(jwtToken)
                                .email(email)
                                .message("User is successfully login")
                                .build();
        }

}
