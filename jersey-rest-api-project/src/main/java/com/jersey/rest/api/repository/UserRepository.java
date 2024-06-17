package com.jersey.rest.api.repository;

import com.jersey.rest.api.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public User createUser(User user);
    public List<User> findByLastName(String lastName);
    public User findUserById(String id);
    public User updateUser(User user);
    public Optional<User> findUserByUsername(String username);
    public Optional<User> findUserByEmail(String email);
}