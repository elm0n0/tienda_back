package com.elm0n0.tienda.api.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elm0n0.tienda.api.auth.dto.request.LogOutRequest;
import com.elm0n0.tienda.api.auth.dto.request.LoginRequest;
import com.elm0n0.tienda.api.auth.dto.request.RefreshTokenRequest;
import com.elm0n0.tienda.api.auth.dto.request.RegisterRequest;
import com.elm0n0.tienda.api.auth.dto.response.AuthResponse;
import com.elm0n0.tienda.api.auth.exception.AuthException;
import com.elm0n0.tienda.api.auth.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	
	private static final String POST_REGISTER = "/register";
	private static final String POST_LOGIN = "/login";
	private static final String POST_REFRESH = "/refresh-token";
	private static final String POST_LOGOUT = "/logout";
	/**
	private static final String GET_VALIDATE_TOKEN = "/validate-token";
	private static final String PUT_CHANGE_PASSWORD = "/change-password";
	private static final String POST_FORGOT_PASSWORD = "/forgot-password";
	private static final String POST_RESET_PASSWORD = "/reset-password";
	*/
	private final AuthService authService;
	/**
	POST /auth/logout - Cerrar sesión (invalidar tokens).
	GET /auth/validate-token - Validar la validez de un access token.
	PUT /auth/change-password - Cambiar la contraseña del usuario.
	POST /auth/forgot-password - Iniciar el proceso de recuperación de contraseña.
	POST /auth/reset-password - Restablecer la contraseña con un token de verificación.
	*/

	@PostMapping(POST_REGISTER)
	public ResponseEntity<Object> register(
		@RequestBody RegisterRequest authRequest,
		@RequestHeader("User-Agent") String deviceName
			) {
		try {
			AuthResponse token = authService.registrarUsuario(authRequest, deviceName);
			return ResponseEntity.ok(token);
		} catch (AuthException e) {
			return ResponseEntity.status(e.getMensaje().codigo()).body(e.getMensaje());
		}
	}

	@PostMapping(POST_LOGIN)
	public ResponseEntity<Object> login(
		@RequestBody LoginRequest loginRequest,
		@RequestHeader("User-Agent") String deviceName) {
		try {
			AuthResponse token = authService.login(loginRequest, deviceName);
			return ResponseEntity.ok(token);
		} catch (AuthException e) {
			return ResponseEntity.status(e.getMensaje().codigo()).body(e.getMensaje());
		}
	}
	
	@PostMapping(POST_REFRESH)
	public ResponseEntity<Object> refreshToken(
			@RequestBody RefreshTokenRequest refreshToken,
			@RequestHeader("User-Agent") String deviceName
			) {
		try {
			return ResponseEntity.ok(authService.refreshToken(refreshToken, deviceName));
		} catch (AuthException e) {
			return ResponseEntity.status(e.getMensaje().codigo()).body(e.getMensaje());
		}
	}
	
	@PostMapping(POST_LOGOUT)
	public ResponseEntity<Object> logout(
			@RequestBody LogOutRequest logOutRequest,
			@RequestHeader("User-Agent") String deviceName) {
		try {
			return ResponseEntity.ok(authService.logout(logOutRequest.email() ,deviceName));
		} catch (AuthException e) {
			return ResponseEntity.status(e.getMensaje().codigo()).body(e.getMensaje());
		}
	}
	
}
