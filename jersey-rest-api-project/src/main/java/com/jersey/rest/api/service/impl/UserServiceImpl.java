package com.jersey.rest.api.service.impl;

import com.jersey.rest.api.dto.UserDTO;
import com.jersey.rest.api.entity.User;
import com.jersey.rest.api.exception.BadCredentialsException;
import com.jersey.rest.api.exception.ResourceConflictException;
import com.jersey.rest.api.exception.ResourceNotFoundException;
import com.jersey.rest.api.repository.UserRepository;
import com.jersey.rest.api.repository.impl.UserRepositoryImpl;
import com.jersey.rest.api.service.UserService;
import com.jersey.rest.api.utils.JwtUtility;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        return new ModelMapper().map(userRepository.createUser(new ModelMapper().map(userDTO, User.class)), UserDTO.class);
    }

    @Override
    public List<UserDTO> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName).stream().map(user -> new ModelMapper().map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUserById(String id) {
        return new ModelMapper().map(userRepository.findUserById(id), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return new ModelMapper().map(userRepository.updateUser(new ModelMapper().map(userDTO, User.class)), UserDTO.class);
    }


    @Override
    public UserDTO findByUsername(String username) {
        return new ModelMapper().map(userRepository.findUserByUsername(username).orElseThrow(()->new ResourceNotFoundException("User does not found with given username : "+username)), UserDTO.class);
    }

    @Override
    public Map<String, Object> loginUser(String username, String password) {
        UserDTO findUserByUsername = findByUsername(username);
        if (!BCrypt.checkpw(password,findUserByUsername.getPassword())) throw new BadCredentialsException("Password does not match!!");
        String token = JwtUtility.generateToken(findUserByUsername);
        return Map.<String, Object>of("status", Response.Status.OK, "statusCode", Response.Status.OK.getStatusCode(), "message", "User has been login successfully", "type", "Bearer", "token", token);
    }

    @Override
    public Map<String, Object> registerUser(UserDTO userDTO) {
        if(userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) throw new ResourceConflictException("User are already register with given username : "+userDTO.getUsername());
        if(userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) throw new ResourceConflictException("User are already register with given email : "+userDTO.getEmail());
        return Map.<String, Object>of("status", Response.Status.CREATED, "statusCode", Response.Status.CREATED.getStatusCode(), "message", "User has been register successfully", "data", createUser(userDTO));
    }
}
