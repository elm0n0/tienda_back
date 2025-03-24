package com.elm0n0.tienda.api.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elm0n0.tienda.api.user.dao.UserDao;

public interface UserRepository extends MongoRepository<UserDao, String> {
	Optional<UserDao> findByUsuario(String usuario);
	Optional<UserDao> findById(String usuarioId);
	Optional<UserDao> findByEmail(String email);
    Optional<UserDao> findByIdTelefono(String idTelefono);
}
