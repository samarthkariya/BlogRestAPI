package com.samarth.blog.controller;

import com.samarth.blog.jwt.JwtTokenHelper;
import com.samarth.blog.payloads.JwtRequest;
import com.samarth.blog.payloads.JwtResponse;
import com.samarth.blog.payloads.UserDto;
import com.samarth.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper helper;

    @Autowired
    private UserDetailsService detailsService;

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest request) {
        authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = detailsService.loadUserByUsername(request.getUsername());
        String token = helper.generateToken(userDetails);

        JwtResponse response = new JwtResponse();
        response.setJwtToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid UserName Or Password!!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.registerNewUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credential Invalid!!";
    }
}
