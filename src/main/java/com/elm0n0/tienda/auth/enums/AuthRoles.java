package com.elm0n0.tienda.auth.enums;

public enum AuthRoles {
	USER("USER"),
	ADMIN("ADMIN");

	private final String role;

	AuthRoles(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
