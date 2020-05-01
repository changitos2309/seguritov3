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
public class profesionalDTO {
    
    String prof_rut;
    String prof_nombre;
    String prof_apell;
    char prof_estado;
    String prof_correo;
    String prof_pass;
    String prof_telefono;
    areaDTO prof_area_id;

    public profesionalDTO(String prof_rut, String prof_nombre, String prof_apell, char prof_estado, String prof_correo, String prof_pass, String prof_telefono, areaDTO prof_area_id) {
        this.prof_rut = prof_rut;
        this.prof_nombre = prof_nombre;
        this.prof_apell = prof_apell;
        this.prof_estado = prof_estado;
        this.prof_correo = prof_correo;
        this.prof_pass = prof_pass;
        this.prof_telefono = prof_telefono;
        this.prof_area_id = prof_area_id;
    }

    public profesionalDTO() {
    }

    public String getProf_rut() {
        return prof_rut;
    }

    public void setProf_rut(String prof_rut) {
        this.prof_rut = prof_rut;
    }

    public String getProf_nombre() {
        return prof_nombre;
    }

    public void setProf_nombre(String prof_nombre) {
        this.prof_nombre = prof_nombre;
    }

    public String getProf_apell() {
        return prof_apell;
    }

    public void setProf_apell(String prof_apell) {
        this.prof_apell = prof_apell;
    }

    public char getProf_estado() {
        return prof_estado;
    }

    public void setProf_estado(char prof_estado) {
        this.prof_estado = prof_estado;
    }

    public String getProf_correo() {
        return prof_correo;
    }

    public void setProf_correo(String prof_correo) {
        this.prof_correo = prof_correo;
    }

    public String getProf_pass() {
        return prof_pass;
    }

    public void setProf_pass(String prof_pass) {
        this.prof_pass = prof_pass;
    }

    public String getProf_telefono() {
        return prof_telefono;
    }

    public void setProf_telefono(String prof_telefono) {
        this.prof_telefono = prof_telefono;
    }

    public areaDTO getProf_area_id() {
        return prof_area_id;
    }

    public void setProf_area_id(areaDTO prof_area_id) {
        this.prof_area_id = prof_area_id;
    }
    
    
    
    
    
}
