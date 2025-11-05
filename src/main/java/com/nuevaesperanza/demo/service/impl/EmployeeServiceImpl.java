package com.nuevaesperanza.demo.service.impl;

import com.nuevaesperanza.demo.dto.request.EmployeeRequest;
import com.nuevaesperanza.demo.entity.Employee;
import com.nuevaesperanza.demo.entity.Proveedor;
import com.nuevaesperanza.demo.exception.DuplicateResourceException;
import com.nuevaesperanza.demo.repository.EmployeeRepository;
import com.nuevaesperanza.demo.service.EmployeeService;
import com.nuevaesperanza.demo.util.FiltroEstado;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final FiltroEstado filtroEstado;

    public EmployeeServiceImpl(EmployeeRepository repository, FiltroEstado filtroEstado) {
        this.repository = repository;
        this.filtroEstado = filtroEstado;
    }

    @Override
    public List<Employee> listarEmpleados() {
        filtroEstado.activarFiltroEstado(true);
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getEmpleadoById(Long id) {
        return repository.findByIdAndEstadoIsTrue(id);
    }

    @Override
    public Optional<Employee> getEmpleadoByNumeroDocumento(String numeroDocumento) {
        return repository.findByNumeroDocumentoAndEstadoIsTrue(numeroDocumento);
    }

    @Override
    public Employee createEmpleado(EmployeeRequest request) {
        filtroEstado.activarFiltroEstado(true);

        if (repository.existsBynumeroDocumentoAndEstadoIsTrue(request.getNumeroDocumento())) {
            throw new DuplicateResourceException("El nombre ya existe");
        }

        Proveedor proveedor = mapper.mapFromRequestToProveedor(request);
        return repository.save(proveedor);
    }

    @Override
    public Employee updateEmpleado(Long id, EmployeeRequest request) {
        return null;
    }

    @Override
    public void deleteEmpleado(Long id) {

    }
}
