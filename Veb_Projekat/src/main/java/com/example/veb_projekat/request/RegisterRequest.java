package com.example.veb_projekat.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull(message = "firstname is required")
    @NotEmpty(message = "firstname is required")
    private String firstname;
    @NotNull(message = "lastname is required")
    @NotEmpty(message = "lastname is required")
    private String lastname;
    @NotNull(message = "email is required")
    @NotEmpty(message = "email is required")
    private String email;

    @NotNull(message = "password is required")
    @NotEmpty(message = "password is required")
    private String password;

    private boolean isAdmin;

    private boolean status;

    public RegisterRequest() {
    }

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
