package com.elm0n0.tienda.api.auth.dto.request;

public record LoginRequest(
		String credential,
		String password)
{}
