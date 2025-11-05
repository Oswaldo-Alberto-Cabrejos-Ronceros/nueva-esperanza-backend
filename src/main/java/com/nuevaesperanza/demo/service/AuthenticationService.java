package com.nuevaesperanza.demo.service;


import com.nuevaesperanza.demo.dto.request.LoginRequestDTO;
import com.nuevaesperanza.demo.dto.request.RegisterRequest;
import com.nuevaesperanza.demo.dto.response.AuthenticationResponseDTO;

public interface AuthenticationService {
    //para autenticar usuario
    AuthenticationResponseDTO authenticateUser(LoginRequestDTO loginRequestDTO);

    //para refrescar token
    String refreshToken(String refreshToken);

}
