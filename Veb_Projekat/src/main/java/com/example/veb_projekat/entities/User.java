package com.example.veb_projekat.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {
    private int id;
    private String role;
    @NotNull(message = "firstname field is required")
    @NotEmpty(message = "firstname field is required")
    private String firstname;
    @NotNull(message = "lastname field is required")
    @NotEmpty(message = "lastname field is required")
    private String lastname;
    @NotNull(message = "email field is required")
    @NotEmpty(message = "email field is required")
    private String email;
    @NotNull(message = "password field is required")
    @NotEmpty(message = "password field is required")
    private String hashedPassword;
    private Boolean status;


    public User() {
        status = false;
        role = "content_creator";
    }

    public User(String role, String firstname, String lastname, String email, String hashedPassword, boolean status) {
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.status = status;
    }

    public User(int id, String role, String firstname, String lastname, String email, String hashedPassword, boolean status) {
        this.id = id;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.status = status;
    }

    public User(int id, String role, String firstname, String lastname, String email, boolean status) {
        this.id = id;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
