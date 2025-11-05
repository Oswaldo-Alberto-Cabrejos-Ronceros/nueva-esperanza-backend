package com.nuevaesperanza.demo.service.impl;

import com.nuevaesperanza.demo.dto.request.ProveedorRequest;
import com.nuevaesperanza.demo.entity.Proveedor;
import com.nuevaesperanza.demo.exception.DuplicateResourceException;
import com.nuevaesperanza.demo.exception.ResourceNotFoundException;
import com.nuevaesperanza.demo.mapper.ProveedorMapper;
import com.nuevaesperanza.demo.repository.ProveedorRepository;
import com.nuevaesperanza.demo.service.ProveedorService;
import com.nuevaesperanza.demo.util.FiltroEstado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServiceImpl implements ProveedorService {
   private final ProveedorRepository repository;
    private final ProveedorMapper mapper;
    private final FiltroEstado filtroEstado;

    public ProveedorServiceImpl(ProveedorRepository repository, ProveedorMapper mapper, FiltroEstado filtroEstado) {
        this.repository = repository;
        this.mapper = mapper;
        this.filtroEstado = filtroEstado;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Proveedor> listarProveedores() {
        filtroEstado.activarFiltroEstado(true);
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Proveedor> getProveedorById(Long id) {
        return repository.findByIdAndEstadoIsTrue(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Proveedor> getProveedorByNombre(String nombre) {
        filtroEstado.activarFiltroEstado(true);
        return repository.findByNombre(nombre);
    }

    @Transactional
    @Override
    public Proveedor createProveedor(ProveedorRequest request) {
        filtroEstado.activarFiltroEstado(true);

        if (repository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException("El nombre ya existe");
        }

        Proveedor proveedor = mapper.mapFromRequestToProveedor(request);
        return repository.save(proveedor);
    }

    @Transactional
    @Override
    public Proveedor updateProveedor(Long id, ProveedorRequest request) {
        filtroEstado.activarFiltroEstado(true);

        Proveedor findProveedor = repository.findByIdAndEstadoIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el seguro con el id: " + id));

        boolean nombreDuplicado = repository.existsByNombreAndEstadoIsTrue(request.getNombre()) &&
                !findProveedor.getNombre().equalsIgnoreCase(request.getNombre());

        if (nombreDuplicado) {
            throw new DuplicateResourceException("Ya existe un seguro con el nombre ingresado");
        }

        findProveedor.setNombre(request.getNombre());
        findProveedor.setRuc(request.getRuc());
        findProveedor.setTelefono(request.getTelefono());
        findProveedor.setDireccion(request.getDireccion());
        findProveedor.setEmail(request.getEmail());
        return repository.save(findProveedor);
    }


    @Transactional
    @Override
    public void deleteProveedor(Long id) {
        filtroEstado.activarFiltroEstado(true);
        Proveedor findProveedor = repository.findByIdAndEstadoIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el proveedor con el id: " + id));
        findProveedor.setEstado(false);
        repository.save(findProveedor);
    }
}
