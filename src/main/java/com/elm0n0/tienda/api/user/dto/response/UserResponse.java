package com.elm0n0.tienda.api.user.dto.response;

public record UserResponse(
		String id,
	    String nombre,
	    String apellidos,
	    String usuario,
	    String email,
	    String idTelefono,
	    String idDireccion) 
{}