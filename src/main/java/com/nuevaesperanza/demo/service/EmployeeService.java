package com.nuevaesperanza.demo.service;

import com.nuevaesperanza.demo.dto.request.EmployeeRequest;
import com.nuevaesperanza.demo.dto.request.ProveedorRequest;
import com.nuevaesperanza.demo.entity.Employee;
import com.nuevaesperanza.demo.entity.Proveedor;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> listarEmpleados();

    Optional<Employee> getEmpleadoById(Long id);

    Optional<Employee> getEmpleadoByNumeroDocumento(String numeroDocumento);

    Employee createEmpleado(EmployeeRequest request);

    Employee updateEmpleado(Long id, EmployeeRequest request);

    void deleteEmpleado(Long id);
}
