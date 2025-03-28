package com.elm0n0.tienda.api.auth.dto.response;

import java.util.Set;

import com.elm0n0.tienda.api.auth.dto.DeviceDto;
import com.elm0n0.tienda.api.auth.enums.AuthRoles;

public record AuthResponse(
	String id,
	String usuarioId,
	String email,
	Set<AuthRoles> roles,
	DeviceDto device
	)
{}
