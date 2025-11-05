package com.nuevaesperanza.demo.repository;

import com.nuevaesperanza.demo.entity.LoteProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoteProveedorRepository extends JpaRepository<LoteProveedor, Long> {
    Optional<LoteProveedor> findByIdAndEstadoIsTrue(Long id);
    List<LoteProveedor> findAllByProveedor_Id(Long proveedorId);
    boolean existsByNumeroLote(String numeroLote);
    Optional<LoteProveedor> findByNumeroLote(String numeroLote);
    boolean existsByNumeroLoteAndEstadoIsTrue(String findByNumeroLote);
}
