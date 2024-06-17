package com.jersey.rest.api.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jersey.rest.api.dto.UserDTO;
import com.jersey.rest.api.entity.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtUtility {

	private static final String SECRET_KEY = "secretKey";

	public static String generateToken(UserDTO userDto) {
		return Jwts.builder()
                .claim("username", userDto.getUsername())
				.claim("email",userDto.getEmail())
				.claim("firstName",userDto.getFirstName())
				.claim("lastName",userDto.getLastName())
				.claim("active",userDto.isActive())
				.claim("authorities",userDto.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	public static UserDTO getUserDtoFromToken(String jwtToken) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();

		UserDTO userDto = new UserDTO();

		userDto.setUsername(claims.get("username", String.class));
		userDto.setEmail(claims.get("email", String.class));
		userDto.setFirstName(claims.get("firstName", String.class));
		userDto.setLastName(claims.get("lastName", String.class));
		userDto.setActive(claims.get("active", Boolean.class));

		List<Authority> authoritiesList = claims.get("authorities", List.class);
		Set<Authority> authoritiesSet = new HashSet<Authority>(authoritiesList);
		userDto.setAuthorities(authoritiesSet);

		return userDto;
	}


	public static boolean isTokenValid(String jwtToken) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
			return !claims.getExpiration().before(new Date());
		} catch (SignatureException e) {
			System.err.println("Invalid JWT signature");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
