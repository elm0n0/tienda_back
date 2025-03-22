package com.elm0n0.tienda.auth.exception;

import com.elm0n0.tienda.auth.exception.response.AuthExceptionResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private final AuthExceptionResponse mensaje;

    public AuthException(AuthExceptionResponse mensaje) {
        super(mensaje.toString());
        this.mensaje = mensaje;
    }
}
