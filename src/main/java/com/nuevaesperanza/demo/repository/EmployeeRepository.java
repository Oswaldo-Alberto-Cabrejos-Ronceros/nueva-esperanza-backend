package com.nuevaesperanza.demo.repository;

import com.nuevaesperanza.demo.entity.Employee;
import com.nuevaesperanza.demo.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByIdAndEstadoIsTrue(Long id);
    boolean existsByNumeroDocumento(String numeroDocumento);
    Optional<Employee> findByNumeroDocumentoAndEstadoIsTrue(String numeroDocumento);
    boolean existsBynumeroDocumentoAndEstadoIsTrue(String nombre);
}
