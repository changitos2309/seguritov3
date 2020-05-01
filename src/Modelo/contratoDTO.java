/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author TKG
 */
public class contratoDTO {
    
    int contrato_id;
    Date contrato_fecini;
    Date contrato_fectermino;
    char contrato_ativo;
    EmpresaDTO contrato_rmp_rut;
    profesionalDTO contrato_pro_rut;

    public contratoDTO() {
    }

    public contratoDTO(int contrato_id, Date contrato_fecini, Date contrato_fectermino, char contrato_ativo, EmpresaDTO contrato_rmp_rut, profesionalDTO contrato_pro_rut) {
        this.contrato_id = contrato_id;
        this.contrato_fecini = contrato_fecini;
        this.contrato_fectermino = contrato_fectermino;
        this.contrato_ativo = contrato_ativo;
        this.contrato_rmp_rut = contrato_rmp_rut;
        this.contrato_pro_rut = contrato_pro_rut;
    }

    public profesionalDTO getContrato_pro_rut() {
        return contrato_pro_rut;
    }

    public void setContrato_pro_rut(profesionalDTO contrato_pro_rut) {
        this.contrato_pro_rut = contrato_pro_rut;
    }

    

    public int getContrato_id() {
        return contrato_id;
    }

    public void setContrato_id(int contrato_id) {
        this.contrato_id = contrato_id;
    }

    public Date getContrato_fecini() {
        return contrato_fecini;
    }

    public void setContrato_fecini(Date contrato_fecini) {
        this.contrato_fecini = contrato_fecini;
    }

    public Date getContrato_fectermino() {
        return contrato_fectermino;
    }

    public void setContrato_fectermino(Date contrato_fectermino) {
        this.contrato_fectermino = contrato_fectermino;
    }

    public char getContrato_ativo() {
        return contrato_ativo;
    }

    public void setContrato_ativo(char contrato_ativo) {
        this.contrato_ativo = contrato_ativo;
    }

    public EmpresaDTO getContrato_rmp_rut() {
        return contrato_rmp_rut;
    }

    public void setContrato_rmp_rut(EmpresaDTO contrato_rmp_rut) {
        this.contrato_rmp_rut = contrato_rmp_rut;
    }
    
    
}
