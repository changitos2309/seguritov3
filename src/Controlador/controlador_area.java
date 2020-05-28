/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DAO.areaDAO;
import Modelo.areaDTO;
import Vista.JFAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author TKG
 */
public class controlador_area extends javax.swing.JFrame implements ActionListener, MouseListener {
    
    
     private JFAdmin vistaarea = new JFAdmin();
     
     areaDAO areaDAO = new areaDAO();
      int ar = 0;
    public controlador_area(JFAdmin vistaPrincipal,areaDAO areaDAO ){
        vistaarea = vistaPrincipal;
        vistaarea.btn_guardarprofesion.addActionListener(this);
         vistaarea.btn_modificar_profesion.addActionListener(this);
          vistaarea.btn_limpiar_profesion.addActionListener(this);
         vistaarea.tabla_area.addMouseListener(this);
       
         listararea();
         limpiararea ();
    }
     
     

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()== vistaarea.btn_guardarprofesion) {
            
            if (vistaarea.txt_profesion_nombre.getText().isEmpty()) {
                 JOptionPane.showMessageDialog(vistaarea, "debe ingresar un profesion", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            try {
            String nombre  = vistaarea.txt_profesion_nombre.getText();
            
            areaDAO  a  =  new areaDAO();
            areaDTO area = new areaDTO();
           
            
            
            area.setArea_detalle(nombre);
            
            if (a.creararea(area) == 1) {
                    JOptionPane.showMessageDialog(vistaarea, "Se ha creado una profesion", "Exito", JOptionPane.INFORMATION_MESSAGE);
                   listararea();
                } else {
                    JOptionPane.showMessageDialog(vistaarea, "No se ha podido agregar profesion", "Error", JOptionPane.ERROR_MESSAGE);
                }
         
            } catch (SQLException ex) {
                  Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaarea, "No se ha podido registrar area profesional", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
        
        if (ae.getSource()== vistaarea.btn_modificar_profesion) {
            
            if (vistaarea.txt_profesion_nombre.getText().isEmpty()) {
                 JOptionPane.showMessageDialog(vistaarea, "debe ingresar un profesion", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            try {
            String nombre  = vistaarea.txt_profesion_nombre.getText();
            
            areaDAO  a  =  new areaDAO();
            areaDTO area = new areaDTO();
           
            
            area.setArea_id(ar);
            area.setArea_detalle(nombre);
            
            if (a.modificararea(area) == 1) {
                    JOptionPane.showMessageDialog(vistaarea, "Se ha modificado una profesion", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    listararea();
                } else {
                    JOptionPane.showMessageDialog(vistaarea, "No se ha podido modificar profesion", "Error", JOptionPane.ERROR_MESSAGE);
                }
         
            } catch (SQLException ex) {
                  Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaarea, "No se ha podido registrar profesional", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
          if (ae.getSource()==  vistaarea.btn_limpiar_profesion) {
            
         limpiararea();
            
        }
        
        
    }

    
    
    
    private void listararea() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna > 2) {
                    return true;
                }
                return false;
            }
        };
        // System.out.println("Destino get data");
        modelo.addColumn("ID");
        modelo.addColumn("Profesion");
       
       
        areaDAO ar = new areaDAO();
      
        for (areaDTO a : ar.listarRubros()) {

            Object[] fila = new Object[2]; // Hay tres columnas en la tabla
             fila[0] = a.getArea_id();
            fila[1] = a.getArea_detalle(); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
           modelo.addRow(fila);
        }

        vistaarea.tabla_area.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
        vistaarea.tabla_area.setRowSorter(elQueOrdena);
    }
    @Override
    public void mouseClicked(MouseEvent me) {
         if (me.getSource() == vistaarea.tabla_area) {
            int filaSeleccionada = vistaarea.tabla_area.getSelectedRow();
            if (filaSeleccionada >= 0) {
                vistaarea.btn_modificar_profesion.setEnabled(true);
                vistaarea.btn_guardarprofesion.setEnabled(false);
            
             ar =  Integer.parseInt(vistaarea.tabla_area.getValueAt(vistaarea.tabla_area.getSelectedRow(), 0).toString());
                String nombre_prof = vistaarea.tabla_area.getValueAt(vistaarea.tabla_area.getSelectedRow(), 1).toString();
               
                vistaarea.txt_profesion_nombre.setText(nombre_prof);
                
       
               
            }
        }
    }
    public void limpiararea ()
    {
           vistaarea.txt_profesion_nombre.setText("");
           vistaarea.btn_guardarprofesion.setEnabled(true);
         vistaarea.btn_modificar_profesion.setEnabled(false);
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
    
}