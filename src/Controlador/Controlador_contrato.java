/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DAO.EmpresaDAO;
import DAO.contratoDAO;
import DAO.profesionalDAO;
import Modelo.EmpresaDTO;
import Modelo.contratoDTO;
import Modelo.profesionalDTO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Vista.JFAdmin;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author TKG
 */
public class Controlador_contrato extends javax.swing.JFrame implements ActionListener, MouseListener {

    private JFAdmin Cprofesional = new JFAdmin();

    contratoDAO contratoDAO = new contratoDAO();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    
    public Controlador_contrato(JFAdmin Cprofesional, contratoDAO contratoDAO) {

        this.Cprofesional = Cprofesional;

        this.contratoDAO = contratoDAO;
        this.Cprofesional.tabla_contrato.addMouseListener(this);
        this.Cprofesional.btn_guardarcontrato.addActionListener(this);
        this.Cprofesional.btn_modificar_contrato.addActionListener(this);
        this.Cprofesional.cmb_contr_empresa.addActionListener(this);
        this.Cprofesional.btn_limpiar_contrato.addActionListener(this);
        
        llenarCombos();
        llenarEmpresa();
        llenarprofesional();
        listarcontrato();
      
    }
        int id_contr =0;
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == Cprofesional.btn_guardarcontrato) {

            if (Cprofesional.txt_fecha_inicio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Cprofesional, "Debe ingresar fecha inicio contrato", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (Cprofesional.txt_fecha_terminar.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Cprofesional, "Debe ingresar fecha termino contrato", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            try {
               
                String ini = Cprofesional.txt_fecha_inicio.getText();
                String ter = Cprofesional.txt_fecha_terminar.getText();
                
                 Date dateI = formatter.parse(ini);
                 Date dateF = formatter.parse(ter);
                EmpresaDAO c = new EmpresaDAO();
                EmpresaDTO empresa = c.leerEmpresa(this.Cprofesional.cmb_contr_empresa.getSelectedItem().toString());
                profesionalDAO p = new profesionalDAO();
                profesionalDTO profesional = p.leerProfesional(this.Cprofesional.cmb_contr_profesional.getSelectedItem().toString());
                
                int estado;
                if (Cprofesional.cmb_contr_estado.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }


                contratoDAO pr = new contratoDAO();
                contratoDTO contrato = new contratoDTO();
               
                contrato.setContrato_id(0);
                contrato.setContrato_fecini(dateI);
                contrato.setContrato_fectermino(dateF);
                contrato.setContrato_ativo((char) estado);
                contrato.setContrato_rmp_rut(empresa);
                contrato.setContrato_pro_rut(profesional);
                
                if (pr.crearContrato(contrato) == 1) {
                    JOptionPane.showMessageDialog(Cprofesional, "Se ha creado una contrato", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    llenarEmpresa();
                     listarcontrato();
                } else {
                    JOptionPane.showMessageDialog(Cprofesional, "No se ha podido agregar una contrato", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(Cprofesional, "No se ha podido registrar contrato", "Error", JOptionPane.ERROR_MESSAGE);

            } catch (ParseException ex) {
                Logger.getLogger(Controlador_contrato.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
         if (ae.getSource() == Cprofesional.btn_modificar_contrato) {

            if (Cprofesional.txt_fecha_inicio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Cprofesional, "Debe ingresar fecha inicio contrato", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (Cprofesional.txt_fecha_terminar.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Cprofesional, "Debe ingresar fecha termino contrato", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            try {
               
                String ini = Cprofesional.txt_fecha_inicio.getText();
                String ter = Cprofesional.txt_fecha_terminar.getText();
                
                 Date dateI = formatter.parse(ini);
                  Date dateF = formatter.parse(ter);
                EmpresaDAO c = new EmpresaDAO();
                EmpresaDTO empresa = c.leerEmpresa(this.Cprofesional.cmb_contr_empresa.getSelectedItem().toString());
                
                profesionalDAO p = new profesionalDAO();
                profesionalDTO profesional = p.leerProfesional(this.Cprofesional.cmb_contr_profesional.getSelectedItem().toString());
              
                
                int estado;
                if (Cprofesional.cmb_contr_estado.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }


                contratoDAO pr = new contratoDAO();
                contratoDTO contrato = new contratoDTO();
               
                contrato.setContrato_id(id_contr);
                contrato.setContrato_fecini(dateI);
                contrato.setContrato_fectermino(dateF);
                contrato.setContrato_ativo((char) estado);
                contrato.setContrato_rmp_rut(empresa);
                 contrato.setContrato_pro_rut(profesional);
                
                if (pr.modificarContrato(contrato) == 1) {
                    JOptionPane.showMessageDialog(Cprofesional, "Se ha modificado  contrato", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    llenarEmpresa();
                    listarcontrato();
                    llenarCombos();
                } else {
                    JOptionPane.showMessageDialog(Cprofesional, "No se ha podido modificar  contrato", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(Cprofesional, "No se ha podido registrar contrato", "Error", JOptionPane.ERROR_MESSAGE);

            } catch (ParseException ex) {
                Logger.getLogger(Controlador_contrato.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
         
         if (ae.getSource() == Cprofesional.btn_limpiar_contrato) {
            limpiarcontrato ();
        }
         if (ae.getSource() == Cprofesional.btn_buscar_contrato) {
            
        }
    }
    
    public void limpiarcontrato ()
    {
        Cprofesional.txt_fecha_inicio.setText("");
        Cprofesional.txt_fecha_terminar.setText("");
        Cprofesional.cmb_contr_estado.setSelectedIndex(0);
         Cprofesional.cmb_contr_empresa.setSelectedIndex(0);
         Cprofesional.cmb_contr_empresa.setSelectedIndex(0);
          Cprofesional.cmb_contr_profesional.setSelectedIndex(0);
    }

    public void llenarCombos() {
        Cprofesional.cmb_contr_estado.addItem("Inactivo");
        Cprofesional.cmb_contr_estado.addItem("Activo");
        Cprofesional.cmb_contr_estado.setSelectedIndex(0);

    }

    private void llenarEmpresa() {
        EmpresaDAO ca = new EmpresaDAO();
        Cprofesional.cmb_contr_empresa.removeAllItems();
        Cprofesional.cmb_contr_empresa.removeAllItems();
        for (EmpresaDTO a : ca.listarEmpresas()) {
            Cprofesional.cmb_contr_empresa.addItem(a.getEmpRut());

        }
    }
    
     private void llenarprofesional() {
         profesionalDAO ca = new profesionalDAO();
        Cprofesional.cmb_contr_profesional.removeAllItems();
        Cprofesional.cmb_contr_profesional.removeAllItems();
        for (profesionalDTO a : ca.listarProfesional()) {
            Cprofesional.cmb_contr_profesional.addItem(a.getProf_rut());

        }
    }
    
    private void listarcontrato() {
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
        modelo.addColumn("ID");
        modelo.addColumn("Fecha inicio");
        modelo.addColumn("Fecha Termino");
        modelo.addColumn("Empresa");
        modelo.addColumn("profesional asignado");
        modelo.addColumn("Estado");
     
        contratoDAO ca = new contratoDAO();
        for (contratoDTO a : ca.listarcontrato()) {

            Object[] fila = new Object[6]; // cantidad de final
            fila[0] = a.getContrato_id(); // debe partirn en eso 0s
            fila[1] = a.getContrato_fecini();
            fila[2] = a.getContrato_fectermino();
            fila[3] = a.getContrato_rmp_rut().getEmpRut();
            fila[4] = a.getContrato_pro_rut().getProf_rut();
            if (a.getContrato_ativo()== 0) {
                fila[5] = "Inactivo";
            } else {
                fila[5] = "Activo";
            }
            modelo.addRow(fila);
        }

        Cprofesional.tabla_contrato.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
        Cprofesional.tabla_contrato.setRowSorter(elQueOrdena);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
         if (e.getSource() == Cprofesional.tabla_contrato) {
            int filaSeleccionada = Cprofesional.tabla_contrato.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Cprofesional.btn_modificar_contrato.setEnabled(true);
                Cprofesional.btn_guardarcontrato.setEnabled(false);
             
                 id_contr = Integer.parseInt(Cprofesional.tabla_contrato.getValueAt(Cprofesional.tabla_contrato.getSelectedRow(), 0).toString());
                String fech_ini = Cprofesional.tabla_contrato.getValueAt(Cprofesional.tabla_contrato.getSelectedRow(), 1).toString();
                String fech_ter = Cprofesional.tabla_contrato.getValueAt(Cprofesional.tabla_contrato.getSelectedRow(), 2).toString();
               
                String empresa = Cprofesional.tabla_contrato.getValueAt(Cprofesional.tabla_contrato.getSelectedRow(), 3).toString();
                String estado = Cprofesional.tabla_contrato.getValueAt(Cprofesional.tabla_contrato.getSelectedRow(), 4).toString();

                Cprofesional.txt_fecha_inicio.setText(fech_ini);
                Cprofesional.txt_fecha_terminar.setText(fech_ter);
                Cprofesional.cmb_contr_empresa.setSelectedItem(empresa);
          
                if ("Activo".equals(estado)) {
                    Cprofesional.cmb_contr_estado.setSelectedIndex(1);
                } else {
                    Cprofesional.cmb_contr_estado.setSelectedIndex(0);
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

}
