package com.elm0n0.tienda.auth.dto;

import java.time.LocalDateTime;

public record DeviceDto(	
	String deviceName,
	String accessToken,
	String refreshToken,
	LocalDateTime accessTokenCreatedAt,
	LocalDateTime accessTokenExpiresAt,
	LocalDateTime refreshTokenCreatedAt,
	LocalDateTime refreshTokenExpiresAt,
	boolean revoked,
	LocalDateTime lastUsedAt) {
}
