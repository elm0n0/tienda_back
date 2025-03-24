package com.elm0n0.tienda.api.auth.dto.request;

public record RefreshTokenRequest(
		String accessToken,
	String refreshToken
		) {
}
