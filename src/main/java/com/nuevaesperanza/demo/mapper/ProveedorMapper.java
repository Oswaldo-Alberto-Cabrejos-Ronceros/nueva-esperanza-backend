package com.nuevaesperanza.demo.mapper;

import com.nuevaesperanza.demo.dto.request.ProveedorRequest;
import com.nuevaesperanza.demo.entity.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {
    public Proveedor mapFromRequestToProveedor(ProveedorRequest request){
        return new Proveedor(null,request.getNombre(), request.getRuc(), request.getTelefono(), request.getDireccion(), request.getEmail());
    }
}
