package com.nuevaesperanza.demo.service;

import com.nuevaesperanza.demo.dto.request.ProveedorRequest;
import com.nuevaesperanza.demo.entity.Proveedor;

import java.util.List;
import java.util.Optional;

public interface ProveedorService {
    List<Proveedor> listarProveedores();

    Optional<Proveedor> getProveedorById(Long id);

    Optional<Proveedor> getProveedorByNombre(String nombre);

    Proveedor createProveedor(ProveedorRequest request);

    Proveedor updateProveedor(Long id, ProveedorRequest request);

    void deleteProveedor(Long id);
}
