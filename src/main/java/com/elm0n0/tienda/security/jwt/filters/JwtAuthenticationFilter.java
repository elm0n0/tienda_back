package com.elm0n0.tienda.security.jwt.filters;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.elm0n0.tienda.api.auth.dao.AuthDao;
import com.elm0n0.tienda.api.auth.dao.DeviceDao;
import com.elm0n0.tienda.api.auth.repository.AuthRepository;
import com.elm0n0.tienda.security.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthRepository authRepositoryMongo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractTokenFromRequest(request);

        if (token != null && !jwtUtil.isTokenExpired(token)) {
            String deviceName = extractDeviceNameFromRequest(request);

            UserDetails userDetails = jwtUtil.extractUserDetails(token);

            if (isDeviceRevoked(userDetails.getUsername(), deviceName)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("El dispositivo ha sido revocado.");
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
        }

        filterChain.doFilter(request, response);
    }
    
    
	private boolean isDeviceRevoked(String email, String deviceName) {
	    Optional<AuthDao> authOpt = authRepositoryMongo.findByEmail(email);
	    
	    if (authOpt.isPresent()) {
	        return authOpt.get().getDevices().stream()
	            .filter(device -> device.getDeviceName().equals(deviceName))
	            .anyMatch(DeviceDao::isRevoked);
	    }
	    return true;
	}

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String extractDeviceNameFromRequest(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
}