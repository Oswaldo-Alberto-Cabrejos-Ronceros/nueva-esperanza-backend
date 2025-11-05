package com.nuevaesperanza.demo.mapper;

import com.nuevaesperanza.demo.dto.request.EmployeeRequest;
import com.nuevaesperanza.demo.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee mapFromRequestToEmployee(EmployeeRequest request){
        return new Employee(null,request.getNombres(),request.getApellidos(),
                request.getNumeroDocumento(),request.getCargo(),request.getSalario(),
                request.getFechaNacimiento(),request.getFechaContratacion(),null);
    }
}
