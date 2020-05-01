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
public class servicioDTO {

    int ser_id;
    String ser_nombre;
    int ser_valor;
    char ser_activo;

    public servicioDTO(int ser_id, String ser_nombre, int ser_valor, char ser_activo) {
        this.ser_id = ser_id;
        this.ser_nombre = ser_nombre;
        this.ser_valor = ser_valor;
        this.ser_activo = ser_activo;
    }

    public servicioDTO() {
    }

    public int getSer_id() {
        return ser_id;
    }

    public void setSer_id(int ser_id) {
        this.ser_id = ser_id;
    }

    public String getSer_nombre() {
        return ser_nombre;
    }

    public void setSer_nombre(String ser_nombre) {
        this.ser_nombre = ser_nombre;
    }

    public int getSer_valor() {
        return ser_valor;
    }

    public void setSer_valor(int ser_valor) {
        this.ser_valor = ser_valor;
    }

    public char getSer_activo() {
        return ser_activo;
    }

    public void setSer_activo(char ser_activo) {
        this.ser_activo = ser_activo;
    }
    
    

}
