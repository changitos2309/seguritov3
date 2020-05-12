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
public class UsuarioempresaDTO {
    
    String user_rut;
    String user_name;
    String user_correo;
    String user_pass;
    char activo;
    EmpresaDTO empresa;
    String rol;

    public UsuarioempresaDTO(String user_rut, String user_name, String user_correo, String user_pass, char activo, EmpresaDTO empresa, String rol) {
        this.user_rut = user_rut;
        this.user_name = user_name;
        this.user_correo = user_correo;
        this.user_pass = user_pass;
        this.activo = activo;
        this.empresa = empresa;
        this.rol = rol;
    }

    public UsuarioempresaDTO() {
       
    }

    public String getUser_rut() {
        return user_rut;
    }

    public void setUser_rut(String user_rut) {
        this.user_rut = user_rut;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_correo() {
        return user_correo;
    }

    public void setUser_correo(String user_correo) {
        this.user_correo = user_correo;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public char getActivo() {
        return activo;
    }

    public void setActivo(char activo) {
        this.activo = activo;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
    
    
}
