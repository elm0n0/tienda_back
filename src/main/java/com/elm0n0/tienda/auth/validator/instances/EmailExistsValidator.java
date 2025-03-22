package com.elm0n0.tienda.auth.validator.instances;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.elm0n0.tienda.auth.dao.AuthDao;
import com.elm0n0.tienda.auth.dto.request.RegisterRequest;
import com.elm0n0.tienda.auth.exception.AuthException;
import com.elm0n0.tienda.auth.exception.response.AuthExceptionResponse;
import com.elm0n0.tienda.auth.repository.AuthRepositoryMongo;
import com.elm0n0.tienda.auth.validator.AuthValidator;

@Component
public class EmailExistsValidator implements AuthValidator {

	private final AuthRepositoryMongo usuarioRepository;

	public EmailExistsValidator(AuthRepositoryMongo usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public void validar(RegisterRequest usuario) throws AuthException {
		Optional<AuthDao> usuarioExistente = usuarioRepository.findByEmail(usuario.email());
		if (usuarioExistente.isPresent()) {
			throw new AuthException(new AuthExceptionResponse(HttpStatus.BAD_REQUEST.value(), "El email ya está registrado."));
		}
	}
}
