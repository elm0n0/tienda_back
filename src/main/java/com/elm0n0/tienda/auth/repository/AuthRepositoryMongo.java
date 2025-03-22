package com.elm0n0.tienda.auth.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elm0n0.tienda.auth.dao.AuthDao;

public interface AuthRepositoryMongo extends MongoRepository<AuthDao, String> {
	Optional<AuthDao> findByEmail(String email);
	Optional<AuthDao> findByUsuarioId(String usuarioId);
}