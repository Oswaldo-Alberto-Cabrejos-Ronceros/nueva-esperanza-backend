package com.nuevaesperanza.demo.service.impl;

import com.nuevaesperanza.demo.dto.request.LoteProveedorRequest;
import com.nuevaesperanza.demo.entity.LoteProveedor;
import com.nuevaesperanza.demo.exception.ResourceNotFoundException;
import com.nuevaesperanza.demo.mapper.LoteProveedorMapper;
import com.nuevaesperanza.demo.repository.LoteProveedorRepository;
import com.nuevaesperanza.demo.service.LoteProveedorService;
import com.nuevaesperanza.demo.util.FiltroEstado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LoteProveedorServiceImpl implements LoteProveedorService {
    private final LoteProveedorRepository repository;
    private final LoteProveedorMapper mapper;
    private final FiltroEstado filtroEstado;

    public LoteProveedorServiceImpl(LoteProveedorRepository repository, LoteProveedorMapper mapper, FiltroEstado filtroEstado) {
        this.repository = repository;
        this.mapper = mapper;
        this.filtroEstado = filtroEstado;
    }

    @Transactional(readOnly = true)
    @Override
    public List<LoteProveedor> listarLotesProveedor() {
        filtroEstado.activarFiltroEstado(true);
        return repository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public List<LoteProveedor> listarLotesProveedorPorProveedor(Long id) {
        filtroEstado.activarFiltroEstado(true);
        return repository.findAllByProveedor_Id(id);
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<LoteProveedor> getLoteProveedorById(Long id) {
        return repository.findByIdAndEstadoIsTrue(id);
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<LoteProveedor> getLoteProveedorByNumeroLote(String numeroLote) {
        filtroEstado.activarFiltroEstado(true);
        return repository.findByNumeroLote(numeroLote);
    }

    @Override
    public LoteProveedor createLoteProveedor(LoteProveedorRequest request) {
        filtroEstado.activarFiltroEstado(true);
        LoteProveedor proveedor = mapper.mapFromRequestoToProveedor(request);
        return repository.save(proveedor);
    }


    @Override
    public void deleteLoteProveedor(Long id) {
        filtroEstado.activarFiltroEstado(true);
        LoteProveedor findLoteProveedor = repository.findByIdAndEstadoIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el lote proveedor con el id: " + id));
        findLoteProveedor.setEstado(false);
        repository.save(findLoteProveedor);
    }
}
