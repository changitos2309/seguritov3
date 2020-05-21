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
public class RubroDTO {
    int id_rubro;
    String nombre;

    public RubroDTO() {
    }

    public RubroDTO(int id_rubro, String nombre) {
        this.id_rubro = id_rubro;
        this.nombre = nombre;
    }

    public int getId_rubro() {
        return id_rubro;
    }

    public void setId_rubro(int id_rubro) {
        this.id_rubro = id_rubro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
            
}
