package com.variable.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern.Flag;
import jakarta.validation.constraints.Size;

public class RegisterUserDto {

    @NotEmpty(message = "The email address is required")
    @Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
    private String email;

    @NotEmpty(message = "The password is required.")
    @Size(min = 2, max = 100, message = "The length of password must be more than 5 characters.")
    private String password;

    @NotEmpty(message = "The full name is required.")
    @Size(min = 2, max = 100, message = "The length of full name must be more than 2 characters.")
    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}