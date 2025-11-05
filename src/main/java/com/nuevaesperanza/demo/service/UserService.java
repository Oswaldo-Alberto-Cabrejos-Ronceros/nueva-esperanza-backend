package com.nuevaesperanza.demo.service;


import com.nuevaesperanza.demo.dto.request.UserRequest;
import com.nuevaesperanza.demo.dto.response.UserResponse;
import com.nuevaesperanza.demo.entity.User;
import com.nuevaesperanza.demo.enums.UserType;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> listarUsuarios();

    Optional<UserResponse> obtenerPorId(Long id);

    //para mantener contexto

    Optional<User> obtenerPorIdContenxt(Long id);

    Optional<User> obtenerPorCorreo(String correo); // para autenticación interna

    List<UserResponse> obtenerPorRol(UserType userType);

    UserResponse guardar(UserRequest usuarioRequestDTO); // cambia DTO por RequestDTO

    UserResponse actualizar(Long id, UserRequest usuarioRequestDTO); // idem

    void eliminar(Long id);

    void eliminarUsuarioSinRelaciones(Long usuarioId);
}