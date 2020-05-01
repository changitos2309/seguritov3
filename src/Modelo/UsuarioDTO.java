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
public class UsuarioDTO {

    

    private String userRut;
    private String userNombre;
    private String userApellido;
    private String userCorreo;
    private String userPass;
    private String userEstado;

    public UsuarioDTO(String userRut, String userNombre, String userApellido, String userCorreo, String userPass, String userEstado) {
        this.userRut = userRut;
        this.userNombre = userNombre;
        this.userApellido = userApellido;
        this.userCorreo = userCorreo;
        this.userPass = userPass;
        this.userEstado = userEstado;
    }

    public UsuarioDTO() {
       
    }

    public String getUserRut() {
        return userRut;
    }

    public void setUserRut(String userRut) {
        this.userRut = userRut;
    }

    public String getUserNombre() {
        return userNombre;
    }

    public void setUserNombre(String userNombre) {
        this.userNombre = userNombre;
    }

    public String getUserApellido() {
        return userApellido;
    }

    public void setUserApellido(String userApellido) {
        this.userApellido = userApellido;
    }

    public String getUserCorreo() {
        return userCorreo;
    }

    public void setUserCorreo(String userCorreo) {
        this.userCorreo = userCorreo;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserEstado() {
        return userEstado;
    }

    public void setUserEstado(String userEstado) {
        this.userEstado = userEstado;
    }

}
