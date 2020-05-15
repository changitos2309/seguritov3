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
public class VisitaDTO {
    int id_vista;
    Date fecha_visita;
    String hora_visita;
    String visita_descripcion;
    String vista_realizada;
    profesionalDTO profesional;
    asesoriaDTO asesoria;

    public VisitaDTO() {
    }

    public VisitaDTO(int id_vista, Date fecha_visita, String hora_visita, String visita_descripcion, String vista_realizada, profesionalDTO profesional, asesoriaDTO asesoria) {
        this.id_vista = id_vista;
        this.fecha_visita = fecha_visita;
        this.hora_visita = hora_visita;
        this.visita_descripcion = visita_descripcion;
        this.vista_realizada = vista_realizada;
        this.profesional = profesional;
        this.asesoria = asesoria;
    }

    public int getId_vista() {
        return id_vista;
    }

    public void setId_vista(int id_vista) {
        this.id_vista = id_vista;
    }

    public Date getFecha_visita() {
        return fecha_visita;
    }

    public void setFecha_visita(Date fecha_visita) {
        this.fecha_visita = fecha_visita;
    }

    public String getHora_visita() {
        return hora_visita;
    }

    public void setHora_visita(String hora_visita) {
        this.hora_visita = hora_visita;
    }

    public String getVisita_descripcion() {
        return visita_descripcion;
    }

    public void setVisita_descripcion(String visita_descripcion) {
        this.visita_descripcion = visita_descripcion;
    }

    public String getVista_realizada() {
        return vista_realizada;
    }

    public void setVista_realizada(String vista_realizada) {
        this.vista_realizada = vista_realizada;
    }

    public profesionalDTO getProfesional() {
        return profesional;
    }

    public void setProfesional(profesionalDTO profesional) {
        this.profesional = profesional;
    }

    public asesoriaDTO getAsesoria() {
        return asesoria;
    }

    public void setAsesoria(asesoriaDTO asesoria) {
        this.asesoria = asesoria;
    }
    
    
    
    
}
