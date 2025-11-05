package com.nuevaesperanza.demo.service.impl;


import com.nuevaesperanza.demo.dto.request.UserRequest;
import com.nuevaesperanza.demo.dto.response.UserResponse;
import com.nuevaesperanza.demo.entity.User;
import com.nuevaesperanza.demo.enums.UserType;
import com.nuevaesperanza.demo.exception.BadRequestException;
import com.nuevaesperanza.demo.exception.DuplicateResourceException;
import com.nuevaesperanza.demo.exception.ResourceNotFoundException;
import com.nuevaesperanza.demo.mapper.UserMapper;
import com.nuevaesperanza.demo.repository.UserRepository;
import com.nuevaesperanza.demo.service.UserService;
import com.nuevaesperanza.demo.util.FiltroEstado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper usuarioMapper;
    private final FiltroEstado filtroEstado;

    @Autowired
    public UserServiceImpl(UserRepository usuarioRepository,
                           PasswordEncoder passwordEncoder, UserMapper usuarioMapper, FiltroEstado filtroEstado) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
        this.filtroEstado = filtroEstado;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> listarUsuarios() {
        filtroEstado.activarFiltroEstado(true);
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::mapToUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserResponse> obtenerPorId(Long id) {
        filtroEstado.activarFiltroEstado(true);
        return usuarioRepository.findByIdAndEstadoIsTrue(id)
                .map(usuarioMapper::mapToUsuarioDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> obtenerPorIdContenxt(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> obtenerPorCorreo(String correo) {
        filtroEstado.activarFiltroEstado(true);
        log.debug("Buscando usuario por correo: {}", correo);

        // Asegúrate de que esta consulta incluya la contraseña
        Optional<User> usuarioOpt = usuarioRepository.findByEmailAndEstadoIsTrue(correo);

        if (usuarioOpt.isPresent()) {
            User usuario = usuarioOpt.get();
            log.debug("Usuario encontrado - ID: {}, Password null? {}",
                    usuario.getId(), usuario.getPassword() == null);
        } else {
            log.debug("No se encontró usuario con correo: {}", correo);
        }

        return usuarioOpt;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> obtenerPorRol(UserType userType) {
        filtroEstado.activarFiltroEstado(true);
        return usuarioRepository.findByUserType(userType)
                .stream()
                .map(usuarioMapper::mapToUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserResponse guardar(UserRequest request) {
        filtroEstado.activarFiltroEstado(true);

        // Validación adicional
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new BadRequestException("El correo es obligatorio");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new BadRequestException("La contraseña es obligatoria");
        }
        if (request.getUserType() == null) {
            throw new BadRequestException("El tyipo es obligatorio");
        }

        if (usuarioRepository.existsByEmailAndEstadoIsTrue(request.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo ingresado");
        }

        User usuario = usuarioMapper.mapFromUsuarioRequestDTOToUsuario(request);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asegurar que el estado esté activo
        usuario.setEstado(true);

        User usuarioSaved = usuarioRepository.save(usuario);
        return usuarioMapper.mapToUsuarioDTO(usuarioSaved);
    }

    @Transactional
    @Override
    public UserResponse actualizar(Long id, UserRequest request) {
        filtroEstado.activarFiltroEstado(true);

        User usuario = usuarioRepository.findByIdAndEstadoIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id: " + id));

        // Verifica si el correo se modificó y si ya existe otro usuario con ese correo
        boolean correoDuplicado = usuarioRepository.existsByEmailAndEstadoIsTrue(request.getEmail())
                && !usuario.getEmail().equalsIgnoreCase(request.getEmail());

        if (correoDuplicado) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo ingresado");
        }

        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        User usuarioSaved = usuarioRepository.save(usuario);
        return usuarioMapper.mapToUsuarioDTO(usuarioSaved);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        filtroEstado.activarFiltroEstado(true);

        User usuario = usuarioRepository.findByIdAndEstadoIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id: " + id));

        usuario.setEstado(false); // Borrado lógico
        usuarioRepository.save(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void eliminarUsuarioSinRelaciones(Long usuarioId) {
        User usuario = usuarioRepository.findByIdAndEstadoIsTrue(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Solo marca el usuario como inactivo sin tocar las relaciones
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
    }
}
