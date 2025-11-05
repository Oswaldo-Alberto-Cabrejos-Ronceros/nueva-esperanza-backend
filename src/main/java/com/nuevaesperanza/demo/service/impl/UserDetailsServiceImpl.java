package com.nuevaesperanza.demo.service.impl;


import com.nuevaesperanza.demo.entity.User;
import com.nuevaesperanza.demo.service.UserService;
import com.nuevaesperanza.demo.util.FiltroEstado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService usuarioService;
    private final FiltroEstado filtroEstado;

    @Autowired
    public UserDetailsServiceImpl(UserService usuarioService, FiltroEstado filtroEstado) {
        this.usuarioService = usuarioService;
        this.filtroEstado = filtroEstado;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            filtroEstado.activarFiltroEstado(true);

            log.debug("Buscando usuario con correo: {}", username);
            User usuario = usuarioService.obtenerPorCorreo(username)
                    .orElseThrow(() -> {
                        log.error("Usuario no encontrado con correo: {}", username);
                        return new UsernameNotFoundException("Usuario no encontrado con correo: " + username);
                    });

            log.debug("Usuario encontrado: ID={}, Correo={}", usuario.getId(), usuario.getEmail());

            // Validaciones detalladas
            if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
                log.error("El correo del usuario es nulo o vacío");
                throw new IllegalStateException("El correo del usuario no puede ser nulo o vacío");
            }

            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                log.error("La contraseña del usuario es nula o vacía. ID Usuario: {}", usuario.getId());
                throw new IllegalStateException("La contraseña del usuario no puede ser nula o vacía");
            }

            if (!usuario.getEstado()) {
                log.error("Usuario inactivo. ID: {}", usuario.getId());
                throw new UsernameNotFoundException("Usuario inactivo");
            }

            if (usuario.getUserType() == null) {
                log.error("Usuario no tiene rol asignado. ID: {}", usuario.getId());
                throw new IllegalStateException("Usuario no tiene rol asignado");
            }


            log.debug("Creando UserDetails para usuario {} con rol {}", usuario.getEmail(), usuario.getUserType());
            return new org.springframework.security.core.userdetails.User(
                    usuario.getEmail(),
                    usuario.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getUserType())));
        } catch (Exception e) {
            log.error("Error en loadUserByUsername para usuario: " + username, e);
            throw e;
        }
    }
}