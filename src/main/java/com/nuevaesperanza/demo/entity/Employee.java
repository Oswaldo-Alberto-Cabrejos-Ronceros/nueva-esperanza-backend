package com.nuevaesperanza.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empleados")
@SuperBuilder
//para filtro
@Filter(name = "estadoActivo", condition = "estado = :estado")
public class Employee extends EntidadConEstado{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombres;
    @Column
    private String apellidos;

    @Column
    private String numeroDocumento;
    @Column
    private String cargo;
    @Column
    private Double salario;
    @Column
    private LocalDate fechaNacimiento;
    @Column
    private LocalDate fechaContratacion;

}
