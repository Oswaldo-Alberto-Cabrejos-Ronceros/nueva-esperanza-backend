package com.nuevaesperanza.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    @NotBlank
    @Size(min = 2, max = 48)
    private String nombres;

    @NotBlank
    @Size(min = 2, max = 64)
    private String apellidos;

    @NotBlank
    private String numeroDocumento;

    @NotBlank
    private String cargo;

    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser mayor que 0")
    private Double salario;

    @NotNull
    @PastOrPresent
    private LocalDate fechaNacimiento;

    @NotNull
    @PastOrPresent
    private LocalDate fechaContratacion;
}
