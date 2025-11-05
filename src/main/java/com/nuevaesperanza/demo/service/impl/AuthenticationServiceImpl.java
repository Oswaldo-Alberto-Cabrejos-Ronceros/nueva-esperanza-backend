package com.nuevaesperanza.demo.service.impl;


import com.nuevaesperanza.demo.dto.request.LoginRequestDTO;
import com.nuevaesperanza.demo.dto.request.RegisterRequest;
import com.nuevaesperanza.demo.dto.response.AuthenticationResponseDTO;
import com.nuevaesperanza.demo.entity.User;
import com.nuevaesperanza.demo.mapper.UserMapper;
import com.nuevaesperanza.demo.repository.UserRepository;
import com.nuevaesperanza.demo.security.JwtUtil;
import com.nuevaesperanza.demo.service.AuthenticationService;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper usuarioMapper;
    private final UserRepository usuarioRepository;

    @Autowired
    public AuthenticationServiceImpl(
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            UserMapper usuarioMapper,
            UserRepository usuarioRepository) {

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public AuthenticationResponseDTO authenticateUser(LoginRequestDTO loginRequestDTO) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getEmail());
        log.debug("Contraseña ingresada (plaintext): '{}'", loginRequestDTO.getPassword());
        log.debug("Contraseña de la BD (hashed): '{}'", userDetails.getPassword());
        log.debug("¿Coincide?: {}", passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword()));

        if (passwordEncoder.matches(loginRequestDTO.getPassword(), userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    null,
                    userDetails.getAuthorities());

            String jwtToken = jwtUtil.generateAccessToken(authentication);
            String refreshToken = jwtUtil.generateRefreshToken(authentication);

            User usuario = usuarioRepository.findByEmailAndEstadoIsTrue(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado o inactivo"));

            return new AuthenticationResponseDTO(
                    usuario.getId(),
                    usuario.getUserType().toString(),
                    jwtToken,
                    refreshToken);
        } else {
            throw new BadCredentialsException("Credenciales incorrectas");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public String refreshToken(String refreshToken) {
        if (jwtUtil.validateToken(refreshToken)) {
            String email = jwtUtil.getEmailFromJwt(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    null,
                    userDetails.getAuthorities());
            return jwtUtil.generateAccessToken(authentication);
        }
        throw new JwtException("Error al validar token de refresco");
    }

}