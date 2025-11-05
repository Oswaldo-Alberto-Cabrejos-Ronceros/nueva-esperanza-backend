package com.nuevaesperanza.demo.service;

import com.nuevaesperanza.demo.dto.request.LoteProveedorRequest;
import com.nuevaesperanza.demo.dto.request.ProveedorRequest;
import com.nuevaesperanza.demo.entity.LoteProveedor;
import com.nuevaesperanza.demo.entity.Proveedor;

import java.util.List;
import java.util.Optional;

public interface LoteProveedorService {
    List<LoteProveedor> listarLotesProveedor();

    List<LoteProveedor> listarLotesProveedorPorProveedor(Long id);

    Optional<LoteProveedor> getLoteProveedorById(Long id);

    Optional<LoteProveedor> getLoteProveedorByNumeroLote(String numeroLote);

    LoteProveedor createLoteProveedor(LoteProveedorRequest request);


    void deleteLoteProveedor(Long id);
}
