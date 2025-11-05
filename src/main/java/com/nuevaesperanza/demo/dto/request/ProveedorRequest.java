package com.nuevaesperanza.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorRequest {
    @NotBlank
    private String nombre;

    @NotBlank
    private String ruc;

    @NotBlank
    private String telefono;

    @NotBlank
    private String direccion;

    @NotBlank
    private String email;
}
