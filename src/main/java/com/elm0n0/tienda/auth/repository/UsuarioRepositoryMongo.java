package com.elm0n0.tienda.auth.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elm0n0.tienda.auth.dao.UserDao;

public interface UsuarioRepositoryMongo extends MongoRepository<UserDao, String> {
	Optional<UserDao> findByUsuario(String usuario);
	Optional<UserDao> findByEmail(String email);
    Optional<UserDao> findByIdTelefono(String idTelefono);
}
