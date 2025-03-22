package com.elm0n0.tienda.auth.dao;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.elm0n0.tienda.auth.enums.AuthRoles;

import lombok.Builder;
import lombok.Data;

@Document(collection = "auths")
@Data
@Builder
public class AuthDao {
	@Id 
	private String id;
	private String usuarioId;
	private String email;
	private String password;
	private Set<AuthRoles> roles;
	private Set<DeviceDao> devices;
}
