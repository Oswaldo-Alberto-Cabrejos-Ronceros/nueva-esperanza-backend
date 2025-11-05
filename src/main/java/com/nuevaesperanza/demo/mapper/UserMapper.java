package com.nuevaesperanza.demo.mapper;

import com.nuevaesperanza.demo.dto.request.UserRequest;
import com.nuevaesperanza.demo.dto.response.UserResponse;
import com.nuevaesperanza.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse mapToUsuarioDTO(User usuario) {
        return new UserResponse(usuario.getId(), usuario.getEmail(), usuario.getUserType());
    }

    public User mapFromUsuarioRequestDTOToUsuario(UserRequest usuarioRequestDTO) {
        return new User(
                null,
                usuarioRequestDTO.getEmail(),
                usuarioRequestDTO.getPassword(),
                usuarioRequestDTO.getUserType());
    }
}
