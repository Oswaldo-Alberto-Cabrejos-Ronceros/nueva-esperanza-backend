package com.nuevaesperanza.demo.controller;

import com.nuevaesperanza.demo.dto.request.ProveedorRequest;
import com.nuevaesperanza.demo.entity.Proveedor;
import com.nuevaesperanza.demo.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Proveedor> getProveedorById(@PathVariable Long id) {
        return proveedorService.getProveedorById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Proveedor> getProveedorByNombre(@PathVariable String nombre) {
        return proveedorService.getProveedorByNombre(nombre).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proveedor> createProveedor(@RequestBody @Valid ProveedorRequest request) {
        Proveedor savedProveedor = proveedorService.createProveedor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> updateProveedor(@PathVariable Long id, @RequestBody @Valid ProveedorRequest request) {
        Proveedor updatedProveedor = proveedorService.updateProveedor(id, request);
        return ResponseEntity.ok(updatedProveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.deleteProveedor(id);
        return ResponseEntity.noContent().build();
    }

}
