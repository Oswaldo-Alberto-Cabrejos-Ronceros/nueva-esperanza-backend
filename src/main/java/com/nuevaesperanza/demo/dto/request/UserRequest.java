package com.nuevaesperanza.demo.dto.request;

import com.nuevaesperanza.demo.enums.UserType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Correo es obligatorio")
    @Email(message = "correo debe ser un email valido")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 32, message = "La contraseña debe tener entre 6 y 32 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "La contraseña debe tener por lo menos una letra mayuscula y un número")
    private String password;
    @NotNull(message = "El tipo es obligatorio")
    private UserType userType;
}
