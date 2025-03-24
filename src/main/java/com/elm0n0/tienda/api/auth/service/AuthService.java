package com.elm0n0.tienda.api.auth.service;

import com.elm0n0.tienda.api.auth.dto.request.LoginRequest;
import com.elm0n0.tienda.api.auth.dto.request.RefreshTokenRequest;
import com.elm0n0.tienda.api.auth.dto.request.RegisterRequest;
import com.elm0n0.tienda.api.auth.dto.response.AuthResponse;

public interface AuthService {

	public AuthResponse registrarUsuario(RegisterRequest registerRequest, String deviceName);

	public AuthResponse login(LoginRequest loginRequest, String deviceName);

	public AuthResponse refreshToken(RefreshTokenRequest refreshToken, String deviceName);

	public String logout(String email, String deviceName);
	
}
