/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;


public class EmpresaDTO {

    private String empRut;
    private String empDirecc;
    private String empRazons;
    private char empEstado;
    private int empTrabajadores;
    private String empNom;
    private String empTelefono;


    public EmpresaDTO(String empRut, String empDirecc, String empRazons, char empEstado, int empTrabajadores, String empNom, String empCorreo, String empTelefono, String empPass) {
        this.empRut = empRut;
        this.empDirecc = empDirecc;
        this.empRazons = empRazons;
        this.empEstado = empEstado;
        this.empTrabajadores = empTrabajadores;
        this.empNom = empNom;
       this.empTelefono = empTelefono;

    }

    public EmpresaDTO() {
     
    }

    public String getEmpRut() {
        return empRut;
    }

    public void setEmpRut(String empRut) {
        this.empRut = empRut;
    }

    public String getEmpDirecc() {
        return empDirecc;
    }

    public void setEmpDirecc(String empDirecc) {
        this.empDirecc = empDirecc;
    }

    public String getEmpRazons() {
        return empRazons;
    }

    public void setEmpRazons(String empRazons) {
        this.empRazons = empRazons;
    }

    public char getEmpEstado() {
        return empEstado;
    }

    public void setEmpEstado(char empEstado) {
        this.empEstado = empEstado;
    }

    public int getEmpTrabajadores() {
        return empTrabajadores;
    }

    public void setEmpTrabajadores(int empTrabajadores) {
        this.empTrabajadores = empTrabajadores;
    }

    public String getEmpNom() {
        return empNom;
    }

    public void setEmpNom(String empNom) {
        this.empNom = empNom;
    }

   

    public String getEmpTelefono() {
        return empTelefono;
    }

    public void setEmpTelefono(String empTelefono) {
        this.empTelefono = empTelefono;
    }


}
