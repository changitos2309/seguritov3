/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DAO.EmpresaDAO;

import DAO.asesoriaDAO;
import Modelo.EmpresaDTO;
import Modelo.asesoriaDTO;
import Vista.JFAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Controlador_asesoria extends javax.swing.JFrame implements ActionListener, MouseListener{
    
    
    private JFAdmin vistaPrincipal = new JFAdmin();

   private asesoriaDAO asesoriaDAO = new asesoriaDAO();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
  
    public Controlador_asesoria(JFAdmin vistaPrincipal , asesoriaDAO asesoriaDAO)  {
       this.vistaPrincipal = vistaPrincipal;
       
        //Objetos de acceso a datos
       
        this.asesoriaDAO =asesoriaDAO;
        
        this.vistaPrincipal.btn_guardarasesoria.addActionListener(this);
        this.vistaPrincipal.btn_modificar_asesoria.addActionListener(this);
        this.vistaPrincipal.tabla_asesoria.addMouseListener(this);
        llenarCombos();
        llenar();
        llenarEmpresa();
        listarasesoria();
        
    }
   int id_ases =0;
   
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()== vistaPrincipal.btn_guardarasesoria) {
            
            if (vistaPrincipal.txt_ases_fecha.getDate()==null ) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar fecha de la asesoria", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
               
               Date fecha = vistaPrincipal.txt_ases_fecha.getDate();
               
                
                 
                 int realizada;
                 if (vistaPrincipal.cmb_realizada.getSelectedIndex() == 0) {
                    realizada = 0;
                } else {
                    realizada = 1;
                }
                 
                EmpresaDAO c = new EmpresaDAO();
                EmpresaDTO empresa = c.leerEmpresa(this.vistaPrincipal.cmb_empresa.getSelectedItem().toString());
               
                String tipo = vistaPrincipal.cmb_tipo.getSelectedItem().toString();
              
                    asesoriaDAO as = new asesoriaDAO();
                    asesoriaDTO ases = new asesoriaDTO();
                    ases.setAses_id(0);
                    ases.setAses_fecha(fecha);
                    ases.setSes_realizado((char) realizada);
                    ases.setEmpresa(empresa);
                    ases.setAses_tipo(tipo);
                
                if (as.crearasesoria(ases) == 1) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Se ha creado una asesoria", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    llenarEmpresa();
                    listarasesoria();
                   
                } else {
                    JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido agregar una asesoria", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido registrar asesoria", "Error", JOptionPane.ERROR_MESSAGE);

            } 
            
        }
        
          if (ae.getSource()== vistaPrincipal.btn_modificar_asesoria) {
            
            if (vistaPrincipal.txt_ases_fecha.getDate()==null) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar fecha de la asesoria", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
               
               Date fecha = vistaPrincipal.txt_ases_fecha.getDate();
               
                
                 
                 int realizada;
                 if (vistaPrincipal.cmb_realizada.getSelectedIndex() == 0) {
                    realizada = 0;
                } else {
                    realizada = 1;
                }
                 
                EmpresaDAO c = new EmpresaDAO();
                EmpresaDTO empresa = c.leerEmpresa(this.vistaPrincipal.cmb_empresa.getSelectedItem().toString());
               
                String tipo = vistaPrincipal.cmb_tipo.getSelectedItem().toString();
              
                    asesoriaDAO as = new asesoriaDAO();
                    asesoriaDTO ases = new asesoriaDTO();
                    
                     ases.setAses_id(id_ases);
                    ases.setAses_fecha(fecha);
                    ases.setSes_realizado((char) realizada);
                    ases.setEmpresa(empresa);
                    ases.setAses_tipo(tipo);
                
                if (as.modificarasesoria(ases) == 1) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Se ha modificado una asesoria", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    
                                  
                    listarasesoria();
                } else {
                    JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido modificar una asesoria", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido modificar asesoria", "Error", JOptionPane.ERROR_MESSAGE);

            }
            
        }
        
    }
    @Override
    public void mouseClicked(MouseEvent me) {
          if (me.getSource() == vistaPrincipal.tabla_asesoria) {
            int filaSeleccionada = vistaPrincipal.tabla_asesoria.getSelectedRow();
            if (filaSeleccionada >= 0) {
                vistaPrincipal.btn_modificar_asesoria.setEnabled(true);
                vistaPrincipal.btn_guardarasesoria.setEnabled(false);
                
                id_ases = Integer.parseInt(vistaPrincipal.tabla_asesoria.getValueAt(vistaPrincipal.tabla_asesoria.getSelectedRow(), 0).toString());
                Date fech = null;
                try {
                    fech = formatter.parse(vistaPrincipal.tabla_asesoria.getValueAt(vistaPrincipal.tabla_asesoria.getSelectedRow(), 1).toString());
                } catch (ParseException ex) {
                    Logger.getLogger(Controlador_asesoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                String realiza = vistaPrincipal.tabla_asesoria.getValueAt(vistaPrincipal.tabla_asesoria.getSelectedRow(), 2).toString();
                String empresa = vistaPrincipal.tabla_asesoria.getValueAt(vistaPrincipal.tabla_asesoria.getSelectedRow(), 3).toString();
                String tipo = vistaPrincipal.tabla_asesoria.getValueAt(vistaPrincipal.tabla_asesoria.getSelectedRow(), 4).toString();

                vistaPrincipal.txt_ases_fecha.setDate(fech);
              if ("SI".equals(realiza)) {
                    vistaPrincipal.cmb_realizada.setSelectedIndex(0);
                } else {
                    vistaPrincipal.cmb_realizada.setSelectedIndex(1);
                }
              
                vistaPrincipal.cmb_empresa.setSelectedItem(empresa);
                vistaPrincipal.cmb_tipo.setSelectedItem(tipo);
          
                
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
    private void listarasesoria() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna > 5) {
                    return true;
                }
                return false;
            }
        };
        // System.out.println("Destino get data");
        modelo.addColumn("ID");
        modelo.addColumn("Fecha");
        modelo.addColumn("realizada");
        modelo.addColumn("Empresa");
        modelo.addColumn("Tipo");
        
     
        asesoriaDAO ca = new asesoriaDAO();
        for (asesoriaDTO a : ca.listarcontrato()) {

            Object[] fila = new Object[5]; // cantidad de final
            fila[0] = a.getAses_id(); // debe partirn en eso 0s
            fila[1] = a.getAses_fecha();
          if (a.getSes_realizado()== 0) {
                fila[2] = "SI";
            } else {
                fila[2] = "NO";
            }
            fila[3] = a.getEmpresa().getEmpRut();
            fila[4] = a.getAses_tipo();
           
            modelo.addRow(fila);
        }

        vistaPrincipal.tabla_asesoria.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
        vistaPrincipal.tabla_asesoria.setRowSorter(elQueOrdena);
    }
    public void llenar()
    {
        vistaPrincipal.cmb_tipo.addItem("FISCALIZACION");
        vistaPrincipal.cmb_tipo.addItem("ACCIDENTE");
      
        vistaPrincipal.cmb_tipo.setSelectedIndex(0);
    }
      public void llenarCombos() {
        vistaPrincipal.cmb_realizada.addItem("SI");
        vistaPrincipal.cmb_realizada.addItem("NO");
        vistaPrincipal.cmb_realizada.setSelectedIndex(0);
    }
      
      private void llenarEmpresa() {
        EmpresaDAO ca = new EmpresaDAO();
        vistaPrincipal.cmb_empresa.removeAllItems();
        vistaPrincipal.cmb_empresa.removeAllItems();
        for (EmpresaDTO a : ca.listarEmpresas()) {
            vistaPrincipal.cmb_empresa.addItem(a.getEmpRut());

        }
    }
     
    
}
