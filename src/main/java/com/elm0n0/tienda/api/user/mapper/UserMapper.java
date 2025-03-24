package com.elm0n0.tienda.api.user.mapper;

import org.mapstruct.Mapper;

import com.elm0n0.tienda.api.user.dao.UserDao;
import com.elm0n0.tienda.api.user.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	default UserResponse userDaoToUserResponse(UserDao userDao) {
		return new UserResponse(
			userDao.getId(),
			userDao.getNombre(),
			userDao.getApellidos(),
			userDao.getUsuario(),
			userDao.getEmail(),
			userDao.getIdTelefono(),
			userDao.getIdDireccion()
			);
	}

}
