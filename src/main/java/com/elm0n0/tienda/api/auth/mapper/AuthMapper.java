package com.elm0n0.tienda.api.auth.mapper;

import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import com.elm0n0.tienda.api.auth.dao.AuthDao;
import com.elm0n0.tienda.api.auth.dao.DeviceDao;
import com.elm0n0.tienda.api.auth.dto.DeviceDto;
import com.elm0n0.tienda.api.auth.dto.response.AuthResponse;
import com.elm0n0.tienda.api.auth.exception.AuthException;
import com.elm0n0.tienda.api.auth.exception.response.AuthExceptionResponse;

@Mapper(componentModel = "spring")
public interface AuthMapper {

	default AuthResponse authModelToAuthResponse(AuthDao auth, String deviceName) {
        DeviceDao device = auth.getDevices().stream()
                .filter(d -> d.getDeviceName().equals(deviceName))
                .findFirst()
                .orElseThrow(() -> new AuthException(new AuthExceptionResponse(
                    HttpStatus.UNAUTHORIZED.value(), "Dispositivo no reconocido")));
        
        return new AuthResponse(
            auth.getId(),
            auth.getUsuarioId(),
            auth.getEmail(),
            auth.getRoles(),
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