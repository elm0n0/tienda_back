package com.elm0n0.tienda.auth.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class UserDao
{
	@Id 
	private String id;
	private String nombre;
	private String apellidos;
	private String usuario;
	private String email;
	private String idTelefono;
	private String idDireccion;
}
