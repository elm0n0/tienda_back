package com.elm0n0.tienda.api.auth.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elm0n0.tienda.api.auth.dao.AuthDao;

public interface AuthRepository extends MongoRepository<AuthDao, String> {
	Optional<AuthDao> findByEmail(String email);
	Optional<AuthDao> findByUsuarioId(String usuarioId);
}