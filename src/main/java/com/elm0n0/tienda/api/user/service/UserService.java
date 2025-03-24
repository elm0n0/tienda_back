package com.elm0n0.tienda.api.user.service;

import com.elm0n0.tienda.api.user.dto.response.UserResponse;

public interface UserService {

	UserResponse getUserById(String userId);

}
