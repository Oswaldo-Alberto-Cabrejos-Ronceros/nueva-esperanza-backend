package com.nuevaesperanza.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeWithUser {
    @NotNull
    private EmployeeRequest employeeRequest;
    @NotNull
    private UserRequest userRequest;
}
