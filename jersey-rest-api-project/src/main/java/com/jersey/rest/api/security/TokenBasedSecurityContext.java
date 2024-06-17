package com.jersey.rest.api.security;

import com.jersey.rest.api.dto.UserDTO;
import com.jersey.rest.api.entity.Authority;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class TokenBasedSecurityContext implements SecurityContext {

    private final UserDTO userDTO;
    private final boolean secure;

    public TokenBasedSecurityContext(UserDTO userDTO, boolean secure) {
        this.userDTO = userDTO;
        this.secure = secure;
    }

    @Override
    public Principal getUserPrincipal() {
        return userDTO;
    }

    @Override
    public boolean isUserInRole(String role) {
        return userDTO.getAuthorities().contains(Authority.valueOf(role));
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    @Override
    public String toString() {
        return "TokenBasedSecurityContext{" +
                "userDTO=" + userDTO +
                ", secure=" + secure +
                '}';
    }
}