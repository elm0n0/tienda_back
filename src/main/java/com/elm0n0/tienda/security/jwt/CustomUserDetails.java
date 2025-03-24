package com.elm0n0.tienda.security.jwt;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.elm0n0.tienda.api.auth.enums.AuthRoles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails extends User {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private Set<AuthRoles> roles;

	public CustomUserDetails(String username, String password, Set<AuthRoles> roles) {
        super(username, password, roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .toList());
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}
