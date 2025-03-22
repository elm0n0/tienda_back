package com.elm0n0.tienda.auth.dto.response;

public record UsuarioResponse(
		String id,
	    String nombre,
	    String apellidos,
	    String usuario,
	    String email,
	    String password) 
{}