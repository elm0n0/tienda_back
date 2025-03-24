package com.elm0n0.tienda.api.auth.dto.request;

public record RegisterRequest(
		String id,
	    String nombre,
	    String apellidos,
	    String usuario,
	    String email,
	    String password) 
{}
