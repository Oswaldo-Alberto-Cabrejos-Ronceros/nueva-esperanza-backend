package com.nuevaesperanza.demo.repository;

import com.nuevaesperanza.demo.entity.User;
import com.nuevaesperanza.demo.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // para obtener usuario por correo
    Optional<User> findByEmailAndEstadoIsTrue(String correo);

    Optional<User> findByUserType(UserType userType);

    // si existe por correo
    boolean existsByEmail(String correo);

    boolean existsByEmailAndEstadoIsTrue(String email);

    Optional<User> findByIdAndEstadoIsTrue(Long id);
}
