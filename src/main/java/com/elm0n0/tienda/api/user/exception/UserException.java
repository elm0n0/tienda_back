package com.elm0n0.tienda.api.user.exception;

import com.elm0n0.tienda.api.user.exception.response.UserExceptionResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private final UserExceptionResponse mensaje;

    public UserException(UserExceptionResponse mensaje) {
        super(mensaje.toString());
        this.mensaje = mensaje;
    }
}
