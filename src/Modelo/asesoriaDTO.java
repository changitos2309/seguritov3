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
public class asesoriaDTO {
    int ases_id;
    Date ases_fecha;
    char ses_realizado;
    EmpresaDTO empresa;
    String ases_tipo;

    public asesoriaDTO(int ases_id, Date ases_fecha, char ses_realizado, EmpresaDTO empresa, String ases_tipo) {
        this.ases_id = ases_id;
        this.ases_fecha = ases_fecha;
        this.ses_realizado = ses_realizado;
        this.empresa = empresa;
        this.ases_tipo = ases_tipo;
    }

    public asesoriaDTO() {
    }

    public int getAses_id() {
        return ases_id;
    }

    public void setAses_id(int ases_id) {
        this.ases_id = ases_id;
    }

    public Date getAses_fecha() {
        return ases_fecha;
    }

    public void setAses_fecha(Date ases_fecha) {
        this.ases_fecha = ases_fecha;
    }

    public char getSes_realizado() {
        return ses_realizado;
    }

    public void setSes_realizado(char ses_realizado) {
        this.ses_realizado = ses_realizado;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public String getAses_tipo() {
        return ases_tipo;
    }

    public void setAses_tipo(String ases_tipo) {
        this.ases_tipo = ases_tipo;
    }
    
    
}
