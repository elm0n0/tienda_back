package com.elm0n0.tienda.api.auth.dao;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceDao {
	private String deviceId;
	private String deviceName;
	private String accessToken;
	private String refreshToken;
	private LocalDateTime accessTokenCreatedAt;
	private LocalDateTime accessTokenExpiresAt;
	private LocalDateTime refreshTokenCreatedAt;
	private LocalDateTime refreshTokenExpiresAt;
	private boolean revoked;
	private LocalDateTime lastUsedAt;
}
