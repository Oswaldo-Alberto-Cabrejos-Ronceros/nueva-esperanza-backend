package com.nuevaesperanza.demo.controller;

import com.nuevaesperanza.demo.dto.request.EmployeeRequest;
import com.nuevaesperanza.demo.dto.request.EmployeeWithUser;
import com.nuevaesperanza.demo.dto.request.ProveedorRequest;
import com.nuevaesperanza.demo.entity.Employee;
import com.nuevaesperanza.demo.entity.Proveedor;
import com.nuevaesperanza.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmployeeController {
    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> listarEmployees() {
        return ResponseEntity.ok(service.listarEmpleados());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return service.getEmpleadoById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero_documento/{numeroDocumento}")
    public ResponseEntity<Employee> getEmployeeByNumeroDocumento(@PathVariable String numeroDocumento) {
        return service.getEmpleadoByNumeroDocumento(numeroDocumento).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid EmployeeRequest request) {
        Employee savedEmployee = service.createEmpleado(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PostMapping("/with-user")
    public ResponseEntity<Employee> createEmployeeWithUser(@RequestBody @Valid EmployeeWithUser request){
        Employee savedEmployee = service.createEmpleadoConUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeRequest request) {
        Employee updatedEmployee = service.updateEmpleado(id, request);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteEmpleado(id);
        return ResponseEntity.noContent().build();
    }

}
