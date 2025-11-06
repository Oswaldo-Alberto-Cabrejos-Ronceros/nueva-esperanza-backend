package com.nuevaesperanza.demo.service.impl;

import com.nuevaesperanza.demo.dto.request.EmployeeRequest;
import com.nuevaesperanza.demo.dto.request.EmployeeWithUser;
import com.nuevaesperanza.demo.dto.response.UserResponse;
import com.nuevaesperanza.demo.entity.Employee;
import com.nuevaesperanza.demo.entity.User;
import com.nuevaesperanza.demo.exception.DuplicateResourceException;
import com.nuevaesperanza.demo.exception.ResourceNotFoundException;
import com.nuevaesperanza.demo.mapper.EmployeeMapper;
import com.nuevaesperanza.demo.repository.EmployeeRepository;
import com.nuevaesperanza.demo.service.EmployeeService;
import com.nuevaesperanza.demo.service.UserService;
import com.nuevaesperanza.demo.util.FiltroEstado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final FiltroEstado filtroEstado;

    private final EmployeeMapper mapper;

    private final UserService userService;

    public EmployeeServiceImpl(EmployeeRepository repository, FiltroEstado filtroEstado, EmployeeMapper mapper, UserService userService) {
        this.repository = repository;
        this.filtroEstado = filtroEstado;
        this.mapper = mapper;
        this.userService = userService;
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
            throw new DuplicateResourceException("El numero documento ya existe");
        }

        Employee employee = mapper.mapFromRequestToEmployee(request);
        return repository.save(employee);
    }

    @Override
    public Employee createEmpleadoConUsuario(EmployeeWithUser request) {
        //primero creamos usuario
        filtroEstado.activarFiltroEstado(true);
        UserResponse userSaved = userService.guardar(request.getUserRequest());
        if (repository.existsBynumeroDocumentoAndEstadoIsTrue(request.getEmployeeRequest().getNumeroDocumento())) {
            throw new DuplicateResourceException("El numero documento ya existe");
        }
        Employee employee = mapper.mapFromRequestToEmployee(request.getEmployeeRequest());
        User user = new User();
        user.setId(userSaved.getId());
        return repository.save(employee);
    }

    @Override
    public Employee updateEmpleado(Long id, EmployeeRequest request) {
        filtroEstado.activarFiltroEstado(true);

        Employee findEmployee = repository.findByIdAndEstadoIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el seguro con el id: " + id));

        boolean nombreDuplicado = repository.existsBynumeroDocumentoAndEstadoIsTrue(request.getNumeroDocumento()) &&
                !findEmployee.getNumeroDocumento().equalsIgnoreCase(request.getNumeroDocumento());

        if (nombreDuplicado) {
            throw new DuplicateResourceException("Ya existe un seguro con el nombre ingresado");
        }

        findEmployee.setNombres(request.getNombres());
        findEmployee.setApellidos(request.getApellidos());
        findEmployee.setNumeroDocumento(request.getNumeroDocumento());
        findEmployee.setCargo(request.getCargo());
        findEmployee.setSalario(request.getSalario());
        findEmployee.setFechaNacimiento(request.getFechaNacimiento());
        findEmployee.setFechaContratacion(request.getFechaContratacion());
        return repository.save(findEmployee);

    }

    @Override
    public void deleteEmpleado(Long id) {
        filtroEstado.activarFiltroEstado(true);
        Employee findEmpleado = repository.findByIdAndEstadoIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el empleadp con el id: " + id));
        findEmpleado.setEstado(false);
        repository.save(findEmpleado);
    }
}
