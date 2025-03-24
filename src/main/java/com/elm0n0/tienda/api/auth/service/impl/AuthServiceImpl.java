package com.elm0n0.tienda.api.auth.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.elm0n0.tienda.api.auth.dao.AuthDao;
import com.elm0n0.tienda.api.auth.dao.DeviceDao;
import com.elm0n0.tienda.api.auth.dto.request.LoginRequest;
import com.elm0n0.tienda.api.auth.dto.request.RefreshTokenRequest;
import com.elm0n0.tienda.api.auth.dto.request.RegisterRequest;
import com.elm0n0.tienda.api.auth.dto.response.AuthResponse;
import com.elm0n0.tienda.api.auth.enums.AuthRoles;
import com.elm0n0.tienda.api.auth.exception.AuthException;
import com.elm0n0.tienda.api.auth.exception.response.AuthExceptionResponse;
import com.elm0n0.tienda.api.auth.mapper.AuthMapper;
import com.elm0n0.tienda.api.auth.repository.AuthRepository;
import com.elm0n0.tienda.api.auth.service.AuthService;
import com.elm0n0.tienda.api.auth.validator.AuthValidator;
import com.elm0n0.tienda.api.user.dao.UserDao;
import com.elm0n0.tienda.api.user.repository.UserRepository;
import com.elm0n0.tienda.security.jwt.CustomUserDetails;
import com.elm0n0.tienda.security.jwt.JwtUtil;
import com.elm0n0.tienda.utils.validators.ValidatorUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final List<AuthValidator> validadores;
	private final AuthRepository authRepositoryMongo;
	private final UserRepository usuarioRepositoryMongo;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final AuthMapper usuarioMapper;
	
	private static final String EXPIRATION = "Expiration";
	private static final String ISSUEDAT = "IssuedAt";
	private static final String USUARIO_INCORRECTO = "Usuario incorrecto";
	
	@Override
	public AuthResponse registrarUsuario(RegisterRequest usuario, String deviceName) {
		
		validar(usuario);

		UserDao userModel = UserDao.builder()
			.id(null)
			.nombre(usuario.nombre())
			.apellidos(usuario.apellidos())
			.usuario(usuario.usuario())
			.email(usuario.email())
			.idTelefono(null)
			.idDireccion(null)
			.build();

		UserDao registeredUserModel = usuarioRepositoryMongo.save(userModel);

		Set<AuthRoles> roles = Set.of(AuthRoles.USER);
		
		String passwordEncriptada = passwordEncoder.encode(usuario.password());
		
		AuthDao usuarioConPasswordEncriptada = AuthDao.builder()
			.id(null)
			.usuarioId(registeredUserModel.getId())
			.email(usuario.email())
			.password(passwordEncriptada)
			.roles(roles)
			.devices(new HashSet<>())
			.build();
		
		AuthDao resultado = generarTokens(usuarioConPasswordEncriptada, deviceName);
		
		return usuarioMapper.authModelToAuthResponse(authRepositoryMongo.save(resultado), deviceName);
	}
	
	@Override
	public AuthResponse login(LoginRequest loginRequest, String deviceName) {

		AuthDao auth = buscarAuthPorCredencial(loginRequest.credential()).orElseThrow(() -> new AuthException(
				new AuthExceptionResponse(HttpStatus.UNAUTHORIZED.value(), USUARIO_INCORRECTO)));

		if (!passwordEncoder.matches(loginRequest.password(), auth.getPassword())) {
			throw new AuthException(
					new AuthExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "Contraseña incorrecta"));
		}
		
		AuthDao resultado = generarTokens(auth, deviceName);
		return usuarioMapper.authModelToAuthResponse(resultado,deviceName);
	}
	
	private AuthDao generarTokens(AuthDao auth, String deviceName) {

		CustomUserDetails userDetails = new CustomUserDetails(
			auth.getEmail(),
			auth.getPassword(),
			auth.getRoles()
		);

		DeviceDao deviceInAuth = auth.getDevices().stream().filter(d -> d.getDeviceName().equals(deviceName)).findFirst()
				.orElse(null);

		String accessToken = jwtUtil.generateAccessToken(userDetails);
		String refreshToken = jwtUtil.generateRefreshToken(userDetails);
		Map<String, LocalDateTime> accessTokenLocalDate = jwtUtil.getTokenLocalDates(accessToken);
		Map<String, LocalDateTime> refreshTokenLocalDate = jwtUtil.getTokenLocalDates(refreshToken);
		
		UserDao user = usuarioRepositoryMongo.findByEmail(auth.getEmail()).orElseThrow(
				() -> new AuthException(
				new AuthExceptionResponse(HttpStatus.UNAUTHORIZED.value(), USUARIO_INCORRECTO)
				)
			);
		
		if (deviceInAuth != null) {

			DeviceDao updatedDevice = DeviceDao.builder()
				.deviceName(deviceInAuth.getDeviceName())
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.accessTokenCreatedAt(accessTokenLocalDate.get(ISSUEDAT))
				.accessTokenExpiresAt(accessTokenLocalDate.get(EXPIRATION))
				.refreshTokenCreatedAt(refreshTokenLocalDate.get(ISSUEDAT))
				.refreshTokenExpiresAt(refreshTokenLocalDate.get(EXPIRATION))
				.revoked(false)
				.lastUsedAt(LocalDateTime.now())
				.build();

			Set<DeviceDao> updatedDevices = new HashSet<>(auth.getDevices());
			updatedDevices.remove(deviceInAuth);
			updatedDevices.add(updatedDevice);
			
			return authRepositoryMongo.save(AuthDao.builder()
					.id(auth.getId())
					.usuarioId(user.getId())
					.email(auth.getEmail())
					.password(auth.getPassword())
					.roles(auth.getRoles())
					.devices(updatedDevices)
					.build());
		} else {
			
			DeviceDao updatedDevice = DeviceDao.builder()
				.deviceName(deviceName)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.accessTokenCreatedAt(accessTokenLocalDate.get(ISSUEDAT))
				.accessTokenExpiresAt(accessTokenLocalDate.get(EXPIRATION))
				.refreshTokenCreatedAt(refreshTokenLocalDate.get(ISSUEDAT))
				.refreshTokenExpiresAt(refreshTokenLocalDate.get(EXPIRATION))
				.revoked(false)
				.lastUsedAt(LocalDateTime.now())
				.build();

			Set<DeviceDao> updatedDevices = new HashSet<>(auth.getDevices());
			updatedDevices.add(updatedDevice);

			return authRepositoryMongo.save(AuthDao.builder()
				.id(auth.getId())
				.usuarioId(user.getId())
				.email(auth.getEmail())
				.password(auth.getPassword())
				.roles(auth.getRoles())
				.devices(updatedDevices)
				.build());
		}
	}
	
	@Override
	public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest, String deviceName) {
		
		if (jwtUtil.isTokenExpired(refreshTokenRequest.refreshToken())) {
	        throw new AuthException(new AuthExceptionResponse(
	                HttpStatus.UNAUTHORIZED.value(), "El refresh token ha caducado"));
	    }
		
		CustomUserDetails userDetails = jwtUtil.extractUserDetails(refreshTokenRequest.refreshToken());
		
		AuthDao auth = buscarAuthPorCredencial(userDetails.getUsername()).orElseThrow(() -> new AuthException(
				new AuthExceptionResponse(HttpStatus.UNAUTHORIZED.value(), USUARIO_INCORRECTO)));
		
		DeviceDao deviceInAuth = auth.getDevices().stream().filter(d -> d.getDeviceName().equals(deviceName)).findFirst()
				.orElse(null);
		
		if (deviceInAuth != null && deviceInAuth.isRevoked()) {
			throw new AuthException(
					new AuthExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "el token a sido revocado, es necesario logear de nuevo"));
		}
		
		
		AuthDao newAuth = generarTokens(auth, deviceName);
		
		authRepositoryMongo.save(newAuth);
		
		return usuarioMapper.authModelToAuthResponse(newAuth, deviceName);
	}

	private void validar(RegisterRequest usuario) {
		for (AuthValidator validador : validadores) {
			validador.validar(usuario);
		}
	}
	
	private Optional<AuthDao> buscarAuthPorCredencial(String credential) {
		if (ValidatorUtils.isValidEmail(credential)) {
			return authRepositoryMongo.findByEmail(credential);
		}
		if (ValidatorUtils.isValidPhone(credential)) {
			return usuarioRepositoryMongo.findByIdTelefono(credential)
					.flatMap(user -> authRepositoryMongo.findByUsuarioId(user.getId()));
		}
		return usuarioRepositoryMongo.findByUsuario(credential)
				.flatMap(user -> authRepositoryMongo.findByUsuarioId(user.getId()));
	}

	@Override
	public String logout(String email, String deviceName) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    String authenticatedEmail = authentication.getName();

	    if (!authenticatedEmail.equals(email)) {
	        throw new AuthException(new AuthExceptionResponse(
	            HttpStatus.FORBIDDEN.value(), "No tiene permiso para cerrar sesión en este dispositivo"
	        ));
	    }

	    AuthDao auth = authRepositoryMongo.findByEmail(email)
	        .orElseThrow(() -> new AuthException(
	            new AuthExceptionResponse(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado")
	        ));

	    Optional<DeviceDao> deviceOptional = auth.getDevices().stream()
	        .filter(device -> device.getDeviceName().equals(deviceName))
	        .findFirst();

	    if (deviceOptional.isEmpty()) {
	        throw new AuthException(new AuthExceptionResponse(
	            HttpStatus.NOT_FOUND.value(), "Dispositivo no encontrado"
	        ));
	    }

	    DeviceDao device = deviceOptional.get();
	    device.setRevoked(true);

	    authRepositoryMongo.save(auth);

	    return "Sesión cerrada correctamente en el dispositivo " + deviceName;
	}

}
