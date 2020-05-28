/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DAO.VisitaDAO;
import DAO.asesoriaDAO;
import DAO.profesionalDAO;
import Modelo.VisitaDTO;
import Modelo.asesoriaDTO;
import Modelo.profesionalDTO;
import Vista.JFAdmin;
import java.awt.HeadlessException;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author TKG
 */
public class Controlador_visita extends javax.swing.JFrame implements ActionListener, MouseListener {
    private JFAdmin Vvisita = new JFAdmin();
    
      //private profesionalDAO profesionalDAO;
       VisitaDAO VisitaDAO = new VisitaDAO();
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
  //
    public Controlador_visita(JFAdmin vistaPrincipal,VisitaDAO VisitaDAO)  {
      Vvisita = vistaPrincipal;
      this.VisitaDAO = VisitaDAO;
         this.Vvisita.cmb_visita_rofesional.addActionListener(this);
      
           this.Vvisita.btn_guardarvisita.addActionListener(this);
           this.Vvisita.btn_modificar_visita.addActionListener(this);
           this.Vvisita.btn_limpiar_visita.addActionListener(this);
           
         this.Vvisita.tabla_visita.addMouseListener(this);
           
         AutoCompleteDecorator.decorate(Vvisita.cmb_visita_rofesional);
        llenarprofesional();
       
        llenarasesoria();
        listarProfesional();
    }
       int id_visita=0;
       
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource()== Vvisita.btn_guardarvisita) {
            
            if (Vvisita.txt_fecha_visita.getDate()==null){
                 JOptionPane.showMessageDialog(Vvisita, "Debe ingresar fecha de visita", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (Vvisita.txt_hora_fecha.getText().isEmpty()){
                 JOptionPane.showMessageDialog(Vvisita, "debe ingresar hora de visita", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
             if (Validar_hora(Vvisita.txt_hora_fecha.getText())!=true){
                 JOptionPane.showMessageDialog(Vvisita, "el formato de la hora no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (Vvisita.txt_visita_descripcion.getText().isEmpty()){
                 JOptionPane.showMessageDialog(Vvisita, "Debe ingresar funa descripcion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
             
            
            try {
                 Date fecha = Vvisita.txt_fecha_visita.getDate();
                  String  Hora = Vvisita.txt_hora_fecha.getText();
                  String descripcion = Vvisita.txt_visita_descripcion.getText();
                  profesionalDAO p = new profesionalDAO();
                  profesionalDTO profesional = p.leerProfesional(this.Vvisita.cmb_visita_rofesional.getSelectedItem().toString().split(" ")[0]);
                  
                  asesoriaDAO as = new asesoriaDAO();
                  asesoriaDTO  ases = as.leerasesoria(Integer.parseInt(this.Vvisita.cmb_visota_ases.getSelectedItem().toString().split("-")[0]));
                
                 String realizada = "NO";
                
                 VisitaDAO vi  = new VisitaDAO();
                 VisitaDTO visita = new VisitaDTO();
                 visita.setId_vista(0);
                 visita.setFecha_visita(fecha);
                 visita.setHora_visita(Hora);
                 visita.setVisita_descripcion(descripcion);
                 visita.setVista_realizada(realizada);
                 visita.setAsesoria(ases);
                 visita.setProfesional(profesional);
                System.out.println(profesional);
                 if (vi.crearvisita(visita)==1) {
                     JOptionPane.showMessageDialog(Vvisita, "Se ha creado una visita", "Exito", JOptionPane.INFORMATION_MESSAGE);
                     listarProfesional();
                }else {
                    JOptionPane.showMessageDialog(Vvisita, "No se ha podido agregar la visita", "Error", JOptionPane.ERROR_MESSAGE);
                }
                 
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(Vvisita, "No se ha podido registrar profesional", "Error", JOptionPane.ERROR_MESSAGE);
        }
            
            
            
        }
        
        if (ae.getSource()== Vvisita.btn_modificar_visita) {
            
            if (Vvisita.txt_fecha_visita.getDate()==null){
                 JOptionPane.showMessageDialog(Vvisita, "Debe ingresar fecha de visita", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (Vvisita.txt_hora_fecha.getText().isEmpty()){
                 JOptionPane.showMessageDialog(Vvisita, "debe ingresar hora de visita", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (Vvisita.txt_visita_descripcion.getText().isEmpty()){
                 JOptionPane.showMessageDialog(Vvisita, "Debe ingresar funa descripcion", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                 Date fecha = Vvisita.txt_fecha_visita.getDate();
                  String  Hora = Vvisita.txt_hora_fecha.getText();
                  String descripcion = Vvisita.txt_visita_descripcion.getText();
                  profesionalDAO p = new profesionalDAO();
                  profesionalDTO profesional = p.leerProfesional(this.Vvisita.cmb_visita_rofesional.getSelectedItem().toString().split(" ")[0]);
                  
                  asesoriaDAO as = new asesoriaDAO();
                  asesoriaDTO  ases = as.leerasesoria(Integer.parseInt(this.Vvisita.cmb_visota_ases.getSelectedItem().toString().split("-")[0]));
                
                 String realizada ="NO";
                 
                 VisitaDAO vi  = new VisitaDAO();
                 VisitaDTO visita = new VisitaDTO();
                 visita.setId_vista(id_visita);
                 visita.setFecha_visita(fecha);
                 visita.setHora_visita(Hora);
                 visita.setVisita_descripcion(descripcion);
                 visita.setVista_realizada(realizada);
                 visita.setAsesoria(ases);
                 visita.setProfesional(profesional);
                 System.out.println(profesional);
                 if (vi.modificarvisita(visita)==1) {
                     JOptionPane.showMessageDialog(Vvisita, "Se ha modificar una visita", "Exito", JOptionPane.INFORMATION_MESSAGE);
                      listarProfesional();
                }else {
                    JOptionPane.showMessageDialog(Vvisita, "No se ha modificado  la visita", "Error", JOptionPane.ERROR_MESSAGE);
                }
                 
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(Vvisita, "No se ha podido registrar profesional", "Error", JOptionPane.ERROR_MESSAGE);
        }
            
            
            
        }
        
        if (ae.getSource()== Vvisita.btn_limpiar_visita) {
            limpiarvisita();
        }
         if (ae.getSource()== Vvisita.JPMvisita) {
            llenarasesoria();
            llenarprofesional();
        }
    }

    private void llenarprofesional() {
         profesionalDAO ca = new profesionalDAO();
        Vvisita.cmb_visita_rofesional.removeAllItems();
        Vvisita.cmb_visita_rofesional.removeAllItems();
        for (profesionalDTO a : ca.listarProfesional()) {
        Vvisita.cmb_visita_rofesional.addItem(a.getProf_rut()+" "+a.getProf_nombre());

        }
    }
    
    private void llenarasesoria() {
         asesoriaDAO ca = new asesoriaDAO();
        Vvisita.cmb_visota_ases.removeAllItems();
        Vvisita.cmb_visota_ases.removeAllItems();
         Vvisita.cmb_visota_ases.addItem("seleccione");
        for (asesoriaDTO a : ca.listarasesoria()) {
        Vvisita.cmb_visota_ases.addItem(a.getAses_id()+"-"+"Asesoria");

        }
    }
    
  
    
    @Override
    public void mouseClicked(MouseEvent me) {
       if (me.getSource() == Vvisita.tabla_visita) {
            int filaSeleccionada = Vvisita.tabla_visita.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Vvisita.btn_modificar_visita.setEnabled(true);
                Vvisita.btn_guardarvisita.setEnabled(false);
                Vvisita.cmb_visita_rofesional.setEnabled(false);
                Vvisita.cmb_visota_ases.setEnabled(false);
                
                id_visita = Integer.parseInt(Vvisita.tabla_visita.getValueAt(Vvisita.tabla_visita.getSelectedRow(), 0).toString());
                Date fech = null;
                try {
                    fech = formatter.parse(Vvisita.tabla_visita.getValueAt(Vvisita.tabla_visita.getSelectedRow(), 1).toString());
                } catch (ParseException ex) {
                    Logger.getLogger(Controlador_contrato.class.getName()).log(Level.SEVERE, null, ex);
                }
                String hora = Vvisita.tabla_visita.getValueAt(Vvisita.tabla_visita.getSelectedRow(), 2).toString();                
                String descripcion = Vvisita.tabla_visita.getValueAt(Vvisita.tabla_visita.getSelectedRow(), 3).toString();
                String ases = Vvisita.tabla_visita.getValueAt(Vvisita.tabla_visita.getSelectedRow(), 4).toString();
                String prof = Vvisita.tabla_visita.getValueAt(Vvisita.tabla_visita.getSelectedRow(), 5).toString();
               String realiza = Vvisita.tabla_visita.getValueAt(Vvisita.tabla_visita.getSelectedRow(), 6).toString();
               
               Vvisita.txt_fecha_visita.setDate(fech);
               Vvisita.txt_hora_fecha.setText(hora);
               Vvisita.txt_visita_descripcion.setText(descripcion);
               Vvisita.cmb_visita_rofesional.setSelectedItem(prof);
               Vvisita.cmb_visota_ases.setSelectedItem(ases);
          
               
            }
        }
    }
        public void limpiarvisita(){
            
             Vvisita.txt_fecha_visita.setDate(new Date());
               Vvisita.txt_hora_fecha.setText("");
               Vvisita.txt_visita_descripcion.setText("");
               Vvisita.cmb_visita_rofesional.setSelectedIndex(0);
               Vvisita.cmb_visota_ases.setSelectedIndex(0);
             
                Vvisita.btn_guardarvisita.setEnabled(true);
                Vvisita.cmb_visita_rofesional.setEnabled(true);
                Vvisita.cmb_visota_ases.setEnabled(true);
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
    
    public Boolean Validar_hora (String hora)
    {
        Boolean b;
         char []a = hora.toString().toCharArray();
         String [] c = hora.split(":");
         if ((a[0]==' ')||(a[1]==' ')||(a[2]==' ')
           ||(a[3]==' ')||(a[4]==' ')|| (getInteger(c[0])>24)|| (getInteger(c[1])>24)) {
            return b = false;
        }else {
              b =true;
         }
       return b;
    }
    
    public int getInteger(String valor){
        int integer = Integer.parseInt(valor);
        return integer;
    }
  private void listarProfesional() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna > 7) {
                    return true;
                }
                return false;
            }
        };
        // System.out.println("Destino get data");
        modelo.addColumn("ID");
        modelo.addColumn("Fecha");
        modelo.addColumn("Hora vista");
        modelo.addColumn("Descripcion");
        modelo.addColumn("asesoria");
        modelo.addColumn("profesional");
        modelo.addColumn("Realizada");

        VisitaDAO ca = new VisitaDAO();
        for (VisitaDTO a : ca.listarProfesional()) {
        Object[] fila = new Object[7]; // cantidad de final
            fila[0] = a.getId_vista(); // debe partirn en eso 0s
            fila[1] = a.getFecha_visita();
            fila[2] = a.getHora_visita();
            fila[3] = a.getVisita_descripcion();
            fila[4] = a.getAsesoria().getAses_id()+"-"+"Asesoria";
            fila[5] = a.getProfesional().getProf_rut()+" "+a.getProfesional().getProf_nombre();
            
            if ("SI".equals(a.getVista_realizada())) {
                fila[6] = "SI";
            } else {
                fila[6] = "NO";
            }
            modelo.addRow(fila);
            
        }

        Vvisita.tabla_visita.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
        Vvisita.tabla_visita.setRowSorter(elQueOrdena);
    }
}
