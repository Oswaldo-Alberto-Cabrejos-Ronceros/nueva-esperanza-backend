package com.nuevaesperanza.demo.controller;

import com.nuevaesperanza.demo.dto.request.LoteProveedorRequest;
import com.nuevaesperanza.demo.entity.LoteProveedor;
import com.nuevaesperanza.demo.service.LoteProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lote_proveedor")
public class LoteProveedorController {
    private final LoteProveedorService service;

    public LoteProveedorController(LoteProveedorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LoteProveedor>> listarLotesProveedore() {
        return ResponseEntity.ok(service.listarLotesProveedor());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<LoteProveedor> getLoteProveedorById(@PathVariable Long id) {
        return service.getLoteProveedorById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero_lote/{numeroLote}")
    public ResponseEntity<LoteProveedor> getLoteProveedorByNumeroDocumento(@PathVariable String numeroLote) {
        return service.getLoteProveedorByNumeroLote(numeroLote).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LoteProveedor> createLoteProveedor(@RequestBody @Valid LoteProveedorRequest request) {
        LoteProveedor savedLoteProveedor = service.createLoteProveedor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLoteProveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoteProveedor(@PathVariable Long id) {
        service.deleteLoteProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
