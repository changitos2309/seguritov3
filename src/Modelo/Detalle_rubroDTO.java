/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author TKG
 */
public class Detalle_rubroDTO {
    int cantidad;
    RubroDTO rubro;
    EmpresaDTO empresa;

    public Detalle_rubroDTO() {
    }

    public Detalle_rubroDTO(int cantidad, RubroDTO rubro, EmpresaDTO empresa) {
        this.cantidad = cantidad;
        this.rubro = rubro;
        this.empresa = empresa;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public RubroDTO getRubro() {
        return rubro;
    }

    public void setRubro(RubroDTO rubro) {
        this.rubro = rubro;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }
    
    
}
