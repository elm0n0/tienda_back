package com.elm0n0.tienda.api.auth.validator.instances;

import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.elm0n0.tienda.api.auth.dto.request.RegisterRequest;
import com.elm0n0.tienda.api.auth.exception.AuthException;
import com.elm0n0.tienda.api.auth.exception.response.AuthExceptionResponse;
import com.elm0n0.tienda.api.auth.validator.AuthValidator;

@Component
public class EmailFormatValidator implements AuthValidator {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

	@Override
	public void validar(RegisterRequest usuario) throws AuthException{
		if (usuario == null || usuario.email() == null || !EMAIL_PATTERN.matcher(usuario.email()).matches()) {
			throw new AuthException(new AuthExceptionResponse(HttpStatus.BAD_REQUEST.value(), "El formato del email es inválido."));
		}
	}
}
