package com.jersey.rest.api.dto;

import com.jersey.rest.api.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Principal {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private boolean active;
    private Set<Authority> authorities;


    @Override
    public String getName() {
        return this.username;
    }
}
