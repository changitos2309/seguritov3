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
public class areaDTO {
    
    int area_id;
    String area_detalle;

    public areaDTO() {
    }

    public areaDTO(int area_id, String area_detalle) {
        this.area_id = area_id;
        this.area_detalle = area_detalle;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getArea_detalle() {
        return area_detalle;
    }

    public void setArea_detalle(String area_detalle) {
        this.area_detalle = area_detalle;
    }
    
    
}
