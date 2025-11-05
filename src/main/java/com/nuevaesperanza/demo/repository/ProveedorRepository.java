package com.nuevaesperanza.demo.repository;

import com.nuevaesperanza.demo.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByIdAndEstadoIsTrue(Long id);
    boolean existsByNombre(String nombre);
    Optional<Proveedor> findByNombre(String nombre);
    boolean existsByNombreAndEstadoIsTrue(String nombre);
}
