package com.nuevaesperanza.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoteProveedorRequest {
    @NotBlank
    private String numeroLote;

    @PastOrPresent
    private LocalDateTime fechaRecepcion;

    private Long ProveedorId;
}
