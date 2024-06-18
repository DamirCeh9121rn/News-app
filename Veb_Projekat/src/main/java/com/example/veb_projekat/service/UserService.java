package com.example.veb_projekat.service;

import com.example.veb_projekat.entities.User;
import com.example.veb_projekat.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;


import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> getAll(Integer page){
        return userRepository.getAll(page);
    }

    public String login(String email, String password){
        String hashedPassword = DigestUtils.sha256Hex(password);
        User user = this.userRepository.findByEmail(email);
        if (user == null  || !user.getHashedPassword().equals(hashedPassword) || !user.getStatus()) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .withClaim("email", user.getEmail())
                .withClaim("firstname", user.getFirstname())
                .withClaim("lastname", user.getLastname())
                .withClaim("status", user.getStatus())
                .sign(algorithm);
    }
    public void register(String role, String firstname, String lastname, String email, String password, boolean status) throws Exception {
        User user = new User();
        System.out.println(email + firstname + lastname + password + role+ status);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setRole(role);
        user.setEmail(email);
        user.setHashedPassword(DigestUtils.sha256Hex(password));
        user.setStatus(status);
        userRepository.insert(user);
    }

    public User getById(Integer id){
        return userRepository.getById(id);
    }
    public User update(Integer id, String role, String firstname, String lastname, String email, String password, Boolean status){
        User user = new User(id, role, firstname, lastname, email, password == null ? null : DigestUtils.sha256Hex(password), status);
        return userRepository.update(user);
    }

    public void statusActivation(Integer userId){
        userRepository.statusActivation(userId);
    }

    public void statusDeactivation(Integer userId){
        userRepository.statusDeactivation(userId);
    }

    public boolean isAuthenticated(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();

        User user = this.userRepository.findByEmail(email);

        return user != null;
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        String role = jwt.getClaim("role").asString();

        User user = this.userRepository.findByEmail(email);
        return user != null && Objects.equals(role, "admin");
    }

}
