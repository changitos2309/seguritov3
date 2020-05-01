/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;


import Modelo.profesionalDTO;
import Modelo.areaDTO;
import DAO.UsuarioDAO;
import DAO.areaDAO;
import DAO.profesionalDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.logging.Level;
import java.util.logging.Logger;
import Vista.JFAdmin;

import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author TKG
 */
public final class Controlador_profesional extends javax.swing.JFrame implements ActionListener, MouseListener {

    private JFAdmin Vprofesional = new JFAdmin();
    
    //private profesionalDAO profesionalDAO;
       profesionalDAO profesionalDAO = new profesionalDAO();
       
      
    public Controlador_profesional(JFAdmin vistaPrincipal, profesionalDAO profesionalDAO) {
       

        this.Vprofesional = vistaPrincipal;

        //Objetos de acceso a datos
       
        this.profesionalDAO =profesionalDAO;
         this.Vprofesional.tabla_profesional.addMouseListener(this);
        this.Vprofesional.btn_guardarProfesional.addActionListener(this);
        this.Vprofesional.btn_modificar_profesional.addActionListener(this);
        this.Vprofesional.btn_limpiar_profesional1.addActionListener(this);
        this.Vprofesional.btn_buscar_profesional.addActionListener(this);
        

       llenarCombos();
       llenarArea();
       
       listarProfesional();
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == Vprofesional.btn_guardarProfesional) {
            
             if (!validarRut(Vprofesional.txt_prof_rut.getText())) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar un rut valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (Vprofesional.txt_prof_rut.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar del profesional rut", "Error", JOptionPane.ERROR_MESSAGE);
               return;

            }  if (Vprofesional.txt_prof_nombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar nombre de profesional", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
                

            }  if (Vprofesional.txt_prof_apell.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar apellido profesional", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
              

            }  if (Vprofesional.txt_prof_teleono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar apellido profesional", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            try {

                String rut_pro = Vprofesional.txt_prof_rut.getText();
                String nombre_pro = Vprofesional.txt_prof_nombre.getText();
                String apell_pro = Vprofesional.txt_prof_apell.getText();
                String telef_pro = Vprofesional.txt_prof_teleono.getText();
              

                int estado;
                if (Vprofesional.cmb_prof_estado.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }
                areaDAO c = new areaDAO();
                areaDTO comuna_local = c.leerRubro(Integer.parseInt(this.Vprofesional.cmb_area.getSelectedItem().toString().split("-")[0]));
                profesionalDAO pr = new profesionalDAO();
                profesionalDTO profesionaol = new profesionalDTO();

                profesionaol.setProf_rut(rut_pro);
                profesionaol.setProf_nombre(nombre_pro);
                profesionaol.setProf_apell(apell_pro);
                profesionaol.setProf_telefono(telef_pro);
                profesionaol.setProf_estado((char) estado);
                profesionaol.setProf_area_id(comuna_local);

                if (pr.crearProfesional(profesionaol) == 1) {
                    JOptionPane.showMessageDialog(Vprofesional, "Se ha creado una profesional", "Exito", JOptionPane.INFORMATION_MESSAGE);
                   listarProfesional();

                    
                } else {
                    JOptionPane.showMessageDialog(Vprofesional, "No se ha podido agregar profesional", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(Vprofesional, "No se ha podido registrar profesional", "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
        
        if (e.getSource() == Vprofesional.btn_modificar_profesional) {

            if (Vprofesional.txt_prof_rut.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar del profesional rut", "Error", JOptionPane.ERROR_MESSAGE);
               return;

            }  if (Vprofesional.txt_prof_nombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar nombre de profesional", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
                

            }  if (Vprofesional.txt_prof_apell.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar apellido profesional", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
              

            }  if (Vprofesional.txt_prof_teleono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar apellido profesional", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
               

            } 

            try {

                String rut_pro = Vprofesional.txt_prof_rut.getText();
                String nombre_pro = Vprofesional.txt_prof_nombre.getText();
                String apell_pro = Vprofesional.txt_prof_apell.getText();
                String telef_pro = Vprofesional.txt_prof_teleono.getText();
                

                int estado;
                if (Vprofesional.cmb_prof_estado.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }
                areaDAO c = new areaDAO();
                areaDTO comuna_local = c.leerRubro(Integer.parseInt(this.Vprofesional.cmb_area.getSelectedItem().toString().split("-")[0]));
                profesionalDAO pr = new profesionalDAO();
                profesionalDTO profesionaol = new profesionalDTO();

                profesionaol.setProf_rut(rut_pro);
                profesionaol.setProf_nombre(nombre_pro);
                profesionaol.setProf_apell(apell_pro);
                profesionaol.setProf_telefono(telef_pro);
                profesionaol.setProf_estado((char) estado);
                profesionaol.setProf_area_id(comuna_local);

                if (pr.ModificarProfesional(profesionaol) == 1) {
                    JOptionPane.showMessageDialog(Vprofesional, "Se ha modificado una profesional", "Exito", JOptionPane.INFORMATION_MESSAGE);
                   
                   listarProfesional();
                  
                } else {
                    JOptionPane.showMessageDialog(Vprofesional, "No se ha podido modificar el profesional profesional", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(Vprofesional, "No se ha podido modificar profesional", "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
        
        if (e.getSource() == Vprofesional.btn_limpiar_profesional1) {
            limpiarProfesional();
            listarProfesional();
        }
        if (e.getSource() == Vprofesional.btn_buscar_profesional) {
             String rut = Vprofesional.txt_prof_rut.getText();
             buscarprofesional(rut);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
 if (e.getSource() == Vprofesional.tabla_profesional) {
            int filaSeleccionada = Vprofesional.tabla_profesional.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Vprofesional.btn_modificar_profesional.setEnabled(true);
                Vprofesional.btn_guardarProfesional.setEnabled(false);
            
                String rut_profesional= Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 0).toString();
                String nombre = Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 1).toString();
                String apell = Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 2).toString();
                String telefono = Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 3).toString();
                String areaid = Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 4).toString();
                String estado = Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 5).toString();

                Vprofesional.txt_prof_rut.setText(rut_profesional);
                Vprofesional.txt_prof_nombre.setText(nombre);
                Vprofesional.txt_prof_apell.setText(apell);
                Vprofesional.txt_prof_teleono.setText(telefono);
                Vprofesional.cmb_area.setSelectedItem(areaid);
                if ("Activo".equals(estado)) {
                    Vprofesional.cmb_prof_estado.setSelectedIndex(1);
                } else {
                    Vprofesional.cmb_prof_estado.setSelectedIndex(0);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }
    
    
      private void  buscarprofesional(String rut) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna > 6) {
                    return true;
                }
                return false;
            }
        };
        // System.out.println("Destino get data");
        modelo.addColumn("Rut");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Telefono");
        modelo.addColumn("area");
        modelo.addColumn("Estado");

        profesionalDAO ca = new profesionalDAO();
        for (profesionalDTO a : ca.listarProfesional()) {
  if (rut.equalsIgnoreCase(a.getProf_rut())){
            Object[] fila = new Object[6]; // cantidad de final
            fila[0] = a.getProf_rut(); // debe partirn en eso 0s
            fila[1] = a.getProf_nombre();
            fila[2] = a.getProf_apell();
            fila[3] = a.getProf_telefono();
            fila[4] = a.getProf_area_id();
            
            if (a.getProf_estado() == 0) {
                fila[5] = "Inactivo";
            } else {
                fila[5] = "Activo";
            }
            modelo.addRow(fila);
        }
       }

        Vprofesional.tabla_profesional.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
        Vprofesional.tabla_profesional.setRowSorter(elQueOrdena);
    }

     private void listarProfesional() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna > 6) {
                    return true;
                }
                return false;
            }
        };
        // System.out.println("Destino get data");
        modelo.addColumn("Rut");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Telefono");
        modelo.addColumn("area");
        modelo.addColumn("Estado");

        profesionalDAO ca = new profesionalDAO();
        for (profesionalDTO a : ca.listarProfesional()) {

            Object[] fila = new Object[6]; // cantidad de final
            fila[0] = a.getProf_rut(); // debe partirn en eso 0s
            fila[1] = a.getProf_nombre();
            fila[2] = a.getProf_apell();
            fila[3] = a.getProf_telefono();
            fila[4] = a.getProf_area_id();
            
            if (a.getProf_estado() == 0) {
                fila[5] = "Inactivo";
            } else {
                fila[5] = "Activo";
            }
            modelo.addRow(fila);
        }

        Vprofesional.tabla_profesional.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
        Vprofesional.tabla_profesional.setRowSorter(elQueOrdena);
    }
    private void llenarArea() {
        areaDAO ca = new areaDAO();
        Vprofesional.cmb_area.removeAllItems();
        Vprofesional.cmb_area.removeAllItems();
        for (areaDTO a : ca.listarRubros()) {
            Vprofesional.cmb_area.addItem(a.getArea_id() + "-" + a.getArea_detalle());

        }
    }
 private void limpiarProfesional() {
        Vprofesional.txt_prof_rut.setText("");
        Vprofesional.txt_prof_nombre.setText("");
        Vprofesional.txt_prof_teleono.setText("");
        Vprofesional.txt_prof_apell.setText("");
        Vprofesional.cmb_prof_estado.setSelectedIndex(0);
        Vprofesional.cmb_area.setSelectedIndex(0);
        Vprofesional.btn_guardarProfesional.setEnabled(true);
        Vprofesional.btn_modificar_profesional.setEnabled(false);
        Vprofesional.txt_prof_rut.requestFocus();
    }
   

    public void llenarCombos() {
        Vprofesional.cmb_prof_estado.addItem("Inactivo");
        Vprofesional.cmb_prof_estado.addItem("Activo");
        Vprofesional.cmb_prof_estado.setSelectedIndex(0);

    }

    private boolean validarRut(String rut) {
        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }
}
