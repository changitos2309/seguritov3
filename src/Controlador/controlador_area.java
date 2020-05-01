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

/**
 *
 * @author TKG
 */
public class controlador_area extends javax.swing.JFrame implements ActionListener, MouseListener {
    
    
     private JFAdmin vistaarea = new JFAdmin();
     
     areaDAO areaDAO = new areaDAO();
    
    public controlador_area(JFAdmin vistaPrincipal,areaDAO areaDAO ){
        vistaarea = vistaPrincipal;
        vistaarea.btn_guardarprofesion.addActionListener(this);
        
         int ar = 0;
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
                   
                } else {
                    JOptionPane.showMessageDialog(vistaarea, "No se ha podido agregar profesion", "Error", JOptionPane.ERROR_MESSAGE);
                }
         
            } catch (SQLException ex) {
                  Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaarea, "No se ha podido registrar profesional", "Error", JOptionPane.ERROR_MESSAGE);
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
           
            
            
            area.setArea_detalle(nombre);
            
            if (a.modificararea(area) == 1) {
                    JOptionPane.showMessageDialog(vistaarea, "Se ha modificado una profesion", "Exito", JOptionPane.INFORMATION_MESSAGE);
                   
                } else {
                    JOptionPane.showMessageDialog(vistaarea, "No se ha podido modificar profesion", "Error", JOptionPane.ERROR_MESSAGE);
                }
         
            } catch (SQLException ex) {
                  Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaarea, "No se ha podido registrar profesional", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }

    
    
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
