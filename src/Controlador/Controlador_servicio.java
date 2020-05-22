/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.Controlador_Login;
import Modelo.EmpresaDTO;
import Modelo.profesionalDTO;
import Modelo.areaDTO;
import Modelo.contratoDTO;
import Modelo.servicioDTO;
import DAO.contratoDAO;
import DAO.profesionalDAO;
import DAO.servicioDAO;
import DAO.UsuarioDAO;
import DAO.areaDAO;
import DAO.EmpresaDAO;
import Vista.JFAdmin;

//
//import static Controlador.Controlador_Login.emp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import static Vista.JFlogin.Instancia;

import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author TKG
 */
public class Controlador_servicio extends javax.swing.JFrame implements ActionListener, MouseListener {

    private JFAdmin vistaPrincipal = new JFAdmin();
    private servicioDAO servi = new servicioDAO();
    int id_servicio = 0;

    public Controlador_servicio(JFAdmin vistaPrincipal, servicioDAO servicioDAO) {

        this.vistaPrincipal = vistaPrincipal;
        this.servi = servicioDAO;
        this.vistaPrincipal.btn_guardarservicio.addActionListener(this);
         this.vistaPrincipal.btn_modificar_servicio.addActionListener(this);
         this.vistaPrincipal.tabla_servicio.addMouseListener(this);
         this.vistaPrincipal.btn_buscar_servicio.addActionListener(this);
         this.vistaPrincipal.btn_limpiar_servicio.addActionListener(this);
        llenarCombos();
        listarserivico();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vistaPrincipal.btn_guardarservicio) {

            if (vistaPrincipal.txt_servicio_nombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "debe ingresar nombre de servicio", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (vistaPrincipal.txt_servicio_valor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "debe ingresar valor", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            try {

                String ser_nom = vistaPrincipal.txt_servicio_nombre.getText();
                int ser_valor = Integer.parseInt(vistaPrincipal.txt_servicio_valor.getText());

                int estado;
                if (vistaPrincipal.cmb_estado_serv.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }

                servicioDAO sr = new servicioDAO();

                servicioDTO servicio = new servicioDTO();
                servicio.setSer_id(0);
                servicio.setSer_nombre(ser_nom);
                servicio.setSer_valor(ser_valor);
                servicio.setSer_activo((char) estado);

                if (sr.crearServicio(servicio) == 1) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Se ha creado un servicio", "Exito", JOptionPane.INFORMATION_MESSAGE);
                   
                    
                    listarserivico();
                } else {
                    JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido agregar servicio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido registrar servicio", "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
        
        
        if (ae.getSource() == vistaPrincipal.btn_modificar_servicio) {

            if (vistaPrincipal.txt_servicio_nombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "debe ingresar nombre de servicio", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (vistaPrincipal.txt_servicio_valor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "debe ingresar valor", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            try {

                String ser_nom = vistaPrincipal.txt_servicio_nombre.getText();
                int ser_valor = Integer.parseInt(vistaPrincipal.txt_servicio_valor.getText().replaceAll("\\.", ""));

                int estado;
                if (vistaPrincipal.cmb_estado_serv.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }

                servicioDAO sr = new servicioDAO();

                servicioDTO servicio = new servicioDTO();
                servicio.setSer_id(id_servicio);
                servicio.setSer_nombre(ser_nom);
                servicio.setSer_valor(ser_valor);
                servicio.setSer_activo((char) estado);

                if (sr.modiificarServicio(servicio) == 1) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Se ha modificado un servicio "+ser_nom, "Exito", JOptionPane.INFORMATION_MESSAGE);
                   
                  listarserivico();
                } else {
                    JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido modificar servicio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido registrar servicio", "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
        if (ae.getSource() == vistaPrincipal.btn_limpiar_servicio) {
            limpiarservicio();
        }
        
        if (ae.getSource() == vistaPrincipal.btn_buscar_servicio) {
            String nombre = vistaPrincipal.txt_servicio_nombre.getText();
            buscarserivico(nombre);
        }
    }

    public final void llenarCombos() {
        vistaPrincipal.cmb_estado_serv.addItem("Inactivo");
        vistaPrincipal.cmb_estado_serv.addItem("Activo");
        vistaPrincipal.cmb_estado_serv.setSelectedIndex(0);

    }
    
     private void listarserivico() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna > 4) {
                    return true;
                }
                return false;
            }
        };
        // System.out.println("Destino get data");
        modelo.addColumn("ID");
        modelo.addColumn("servicio");
        modelo.addColumn("valor");
        modelo.addColumn("Estado");
       
        servicioDAO ca = new servicioDAO();
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        for (servicioDTO a : ca.listarServicio()) {

            Object[] fila = new Object[4]; // Hay tres columnas en la tabla
             fila[0] = a.getSer_id();
            fila[1] = a.getSer_nombre(); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
            fila[2] = formatea.format(a.getSer_valor());
                 
            if (a.getSer_activo()== 0) {
                fila[3] = "Inactivo";
            } else {
                fila[3] = "Activo";
            }
            modelo.addRow(fila);
        }

        vistaPrincipal.tabla_servicio.setModel(modelo);
        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        DefaultTableCellRenderer Alinear2 = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);
        Alinear2.setHorizontalAlignment(SwingConstants.RIGHT);
        vistaPrincipal.tabla_servicio.getColumnModel().getColumn(3).setCellRenderer(Alinear);
         vistaPrincipal.tabla_servicio.getColumnModel().getColumn(2).setCellRenderer(Alinear2);
         
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
        vistaPrincipal.tabla_servicio.setRowSorter(elQueOrdena);
    }
     private void buscarserivico(String nombre) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna > 4) {
                    return true;
                }
                return false;
            }
        };
        // System.out.println("Destino get data");
        modelo.addColumn("ID");
        modelo.addColumn("servicio");
        modelo.addColumn("valor");
        modelo.addColumn("activo");
        

        servicioDAO ca = new servicioDAO();
        for (servicioDTO a : ca.listarServicio()) {
            if (nombre.equalsIgnoreCase(a.getSer_nombre())) {
         DecimalFormat formatea = new DecimalFormat("###,###.##");
            Object[] fila = new Object[4]; // Hay tres columnas en la tabla
             fila[0] = a.getSer_id();
            fila[1] = a.getSer_nombre(); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
            fila[2] = formatea.format(a.getSer_valor());
                 
            if (a.getSer_activo()== 0) {
                fila[3] = "Inactivo";
            } else {
                fila[3] = "Activo";
            }
            modelo.addRow(fila);
             }
        }

        vistaPrincipal.tabla_servicio.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
        vistaPrincipal.tabla_servicio.setRowSorter(elQueOrdena);
    }
     public void limpiarservicio()
     {
        vistaPrincipal.txt_servicio_nombre.setText("");
        vistaPrincipal.txt_servicio_nombre.setEnabled(true);
           vistaPrincipal.txt_servicio_valor.setText("");
           listarserivico();
     
     }

    @Override
    public void mouseClicked(MouseEvent me) {
         
        if (me.getSource() == vistaPrincipal.tabla_servicio) {
            int filaSeleccionada = vistaPrincipal.tabla_servicio.getSelectedRow();
            if (filaSeleccionada >= 0) {
                vistaPrincipal.btn_modificar_servicio.setEnabled(true);
                vistaPrincipal.btn_guardarservicio.setEnabled(false);
             vistaPrincipal.txt_servicio_nombre.setEnabled(false);
             id_servicio =  Integer.parseInt(vistaPrincipal.tabla_servicio.getValueAt(vistaPrincipal.tabla_servicio.getSelectedRow(), 0).toString());
                String nombre_ser = vistaPrincipal.tabla_servicio.getValueAt(vistaPrincipal.tabla_servicio.getSelectedRow(), 1).toString();
                String valor = vistaPrincipal.tabla_servicio.getValueAt(vistaPrincipal.tabla_servicio.getSelectedRow(), 2).toString();
                String estado = vistaPrincipal.tabla_servicio.getValueAt(vistaPrincipal.tabla_servicio.getSelectedRow(), 3).toString();

                vistaPrincipal.txt_servicio_nombre.setText(nombre_ser);
                vistaPrincipal.txt_servicio_valor.setText(valor);
       
                if ("Activo".equals(estado)) {
                    vistaPrincipal.cmb_estado_serv.setSelectedIndex(1);
                } else {
                    vistaPrincipal.cmb_estado_serv.setSelectedIndex(0);
                }
            }
        }
    }

   

    @Override
    public void mouseEntered(MouseEvent me) {
    
    }

    @Override
    public void mouseExited(MouseEvent me) {
    
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

}
