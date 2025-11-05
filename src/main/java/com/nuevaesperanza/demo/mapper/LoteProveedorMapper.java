package com.nuevaesperanza.demo.mapper;

import com.nuevaesperanza.demo.dto.request.LoteProveedorRequest;
import com.nuevaesperanza.demo.entity.LoteProveedor;
import com.nuevaesperanza.demo.entity.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class LoteProveedorMapper {

    public LoteProveedor mapFromRequestoToProveedor(LoteProveedorRequest request){
        Proveedor proveedor = new Proveedor();
        proveedor.setId(request.getProveedorId());
        return new LoteProveedor(null, request.getNumeroLote(),
                request.getFechaRecepcion(),proveedor);
    }
}
