package com.elm0n0.tienda.auth.mapper;

import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import com.elm0n0.tienda.auth.dao.AuthDao;
import com.elm0n0.tienda.auth.dao.DeviceDao;
import com.elm0n0.tienda.auth.dto.DeviceDto;
import com.elm0n0.tienda.auth.dto.response.AuthResponse;
import com.elm0n0.tienda.auth.exception.AuthException;
import com.elm0n0.tienda.auth.exception.response.AuthExceptionResponse;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	default AuthResponse authModelToAuthResponse(AuthDao usuario, String deviceName) {
        DeviceDao device = usuario.getDevices().stream()
                .filter(d -> d.getDeviceName().equals(deviceName))
                .findFirst()
                .orElseThrow(() -> new AuthException(new AuthExceptionResponse(
                    HttpStatus.UNAUTHORIZED.value(), "Dispositivo no reconocido")));
        
        return new AuthResponse(
            usuario.getId(),
            usuario.getUsuarioId(),
            usuario.getEmail(),
            usuario.getRoles(),
            new DeviceDto(
            		device.getDeviceName(),
            		device.getAccessToken(),
            		device.getRefreshToken(),
            		device.getAccessTokenCreatedAt(),
            		device.getAccessTokenExpiresAt(),
            		device.getRefreshTokenCreatedAt(),
            		device.getRefreshTokenExpiresAt(),
            		device.isRevoked(),
            		device.getLastUsedAt())
        );
    }
}