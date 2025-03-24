package com.elm0n0.tienda.api.user.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.elm0n0.tienda.api.user.dao.UserDao;
import com.elm0n0.tienda.api.user.dto.response.UserResponse;
import com.elm0n0.tienda.api.user.exception.UserException;
import com.elm0n0.tienda.api.user.exception.response.UserExceptionResponse;
import com.elm0n0.tienda.api.user.mapper.UserMapper;
import com.elm0n0.tienda.api.user.repository.UserRepository;
import com.elm0n0.tienda.api.user.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	private static final String ERR_NOT_USER_FOUND = "Error al obtener el usuario";

	@Override
	public UserResponse getUserById(String userId) {
		UserDao userDao = userRepository.findById(userId).orElseThrow(
				() -> new UserException(new UserExceptionResponse(HttpStatus.BAD_REQUEST.value(), ERR_NOT_USER_FOUND)));
		return userMapper.userDaoToUserResponse(userDao);
	}

}
