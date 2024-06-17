package com.jersey.rest.api.service;

import com.jersey.rest.api.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserService {
    public UserDTO createUser(UserDTO userDTO);
    public List<UserDTO> findByLastName(String lastName);
    public UserDTO findUserById(String id);
    public UserDTO updateUser(UserDTO userDTO);
    public UserDTO findByUsername(String username);
    public Map<String,Object> loginUser(String username, String password);
    public Map<String,Object> registerUser(UserDTO userDTO);
}
