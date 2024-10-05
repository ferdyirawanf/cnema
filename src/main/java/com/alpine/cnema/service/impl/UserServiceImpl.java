package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.UserDTO;
import com.alpine.cnema.model.User;
import com.alpine.cnema.repository.UserRepository;
import com.alpine.cnema.service.JwtService;
import com.alpine.cnema.service.UserService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    private static final Set<String> VALID_TLDS = new HashSet<>(Messages.VALID_TLDS);


    @Override
    public User register(UserDTO.Register req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.USERNAME_ALREADY_EXIST);
        }

        // check nomor telepon sudah ada atau belum
        if (userRepository.findByPhone(req.getPhone()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.PHONE_ALREADY_EXIST);
        }

        // check email sudah ada atau belum
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.EMAIL_ALREADY_EXIST);
        }

        // check email apakah email diinput dengan domain yang valid; ex: user@gmail.com or user@company.co.id
        if (!isValidEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.EMAIL_INVALID_FORMAT);
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail()) 
                .phone(req.getPhone()) 
                .address(req.getAddress()) 
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        return userRepository.save(user);
    }

    @Override
    public String login(UserDTO.Login req) {
        log.info("REQUEST USERNAME: {}", req.getUsername());
        log.info("REQUEST PASSWORD: {}", req.getPassword());
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.INVALID_CREDENTIAL));
        return jwtService.generateToken(user);
    }

    @PostConstruct
    public void initAdmin(){
        Optional<User> cred = userRepository.findByUsername("admin");
        if (cred.isPresent()){
            return;
        }

        String hashedPassword = passwordEncoder.encode( "admin");

        User admin = User.builder()
                .username("admin")
                .email("admin@example.com") 
                .password(hashedPassword)
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Check for exactly one '@' symbol
        int atPos = email.indexOf('@');
        int lastAtPos = email.lastIndexOf('@');
        if (atPos <= 0 || atPos != lastAtPos) {
            return false;
        }

        // Split local part and domain part
        String localPart = email.substring(0, atPos);
        String domainPart = email.substring(atPos + 1);

        // Local part and domain part should not be empty
        if (localPart.isEmpty() || domainPart.isEmpty()) {
            return false;
        }

        // Domain part must contain at least one dot '.'
        int dotPos = domainPart.lastIndexOf('.');
        if (dotPos <= 0 || dotPos >= domainPart.length() - 1) {
            return false;
        }

        // Extract domain and TLD
        String domain = domainPart.substring(0, dotPos);
        String tld = domainPart.substring(dotPos + 1);

        // Domain and TLD should not be empty
        if (domain.isEmpty() || tld.isEmpty()) {
            return false;
        }

        // TLD should be at least two characters long
        if (tld.length() < 2) {
            return false;
        }

        // Check if TLD is in the list of valid TLDs
        if (!VALID_TLDS.contains(tld.toLowerCase())) {
            return false;
        }

        // All checks passed
        return true;
    }
}