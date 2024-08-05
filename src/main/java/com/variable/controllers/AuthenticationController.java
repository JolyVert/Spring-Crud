package com.variable.controllers;

import com.variable.entities.User;
import com.variable.dtos.LoginUserDto;
import com.variable.dtos.RegisterUserDto;
import com.variable.repositories.UserRepository;
import com.variable.responses.LoginResponse;
import com.variable.services.AuthenticationService;
import com.variable.services.JwtService;
import com.variable.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/auth")
@Controller
@CrossOrigin
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;

        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        return "signup";
    }


//ResponseEntity<LoginResponse>
    @PostMapping("/login")
    public void authenticate(@RequestBody LoginUserDto loginUserDto, HttpServletResponse response) throws IOException {
        try {

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(), authenticatedUser);

        Cookie jwtCookie = new Cookie("JWT", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Use true in production
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) jwtService.getExpirationTime()/1000);

        // Add the cookie to the response
        response.addCookie(jwtCookie);

        // Redirect to the web application
        //response.sendRedirect("/horay");
    } catch (AuthenticationException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie jwtLogoutCookie = new Cookie("JWT", null);
        jwtLogoutCookie.setHttpOnly(true);
        jwtLogoutCookie.setSecure(true); // Use true in production
        jwtLogoutCookie.setPath("/");
        jwtLogoutCookie.setMaxAge(0); // Delete the cookie

        response.addCookie(jwtLogoutCookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }


    @PostMapping("/emailValidation")
    public void emailValidation(@RequestBody RegisterUserDto registerUserDto, HttpServletResponse response) {
        if(userService.checkIfEmailExists(registerUserDto.getEmail())) {
            response.setStatus(HttpServletResponse.SC_GONE);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @PostMapping("/usernameValidation")
    public void usernameValidation(@RequestBody RegisterUserDto registerUserDto, HttpServletResponse response) {
        if(userService.checkIfUsernameExists(registerUserDto.getUsername())) {
            response.setStatus(HttpServletResponse.SC_GONE);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }



}
