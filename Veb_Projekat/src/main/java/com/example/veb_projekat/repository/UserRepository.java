package com.example.veb_projekat.repository;

import com.example.veb_projekat.entities.User;

import java.util.List;

public interface UserRepository {

    User insert(User user) throws Exception;
    User getById(Integer id);
    User update(User user);
    void statusActivation(Integer userId);
    void statusDeactivation(Integer userId);
    List<User> getAll(Integer page);
    User findByEmail(String email);
}
