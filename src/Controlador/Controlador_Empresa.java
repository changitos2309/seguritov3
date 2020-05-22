/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DAO.Detalle_rubroDAO;
import Modelo.EmpresaDTO;

import DAO.UsuarioDAO;

import DAO.EmpresaDAO;
import DAO.rubroDAO;
import Modelo.Detalle_rubroDTO;
import Modelo.RubroDTO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Vista.JFAdmin;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.beans.Visibility;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Controlador_Empresa extends javax.swing.JFrame implements ActionListener, MouseListener {

    private JFAdmin vistaPrincipal = new JFAdmin();

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public Controlador_Empresa(JFAdmin vistaPrincipal, UsuarioDAO UsuarioDAO) {

        this.vistaPrincipal = vistaPrincipal;
       
        //Objetos de acceso a datos
        this.usuarioDAO = usuarioDAO;
       
        llenarEmpresa();
        this.vistaPrincipal.setVisible(true);
        this.vistaPrincipal.setLocationRelativeTo(null);
        this.vistaPrincipal.setDefaultCloseOperation(JFAdmin.DO_NOTHING_ON_CLOSE);

        this.vistaPrincipal.txt_Empresa_rut.requestFocus();
        this.vistaPrincipal.tabla_empresa.addMouseListener(this);
        this.vistaPrincipal.btn_guardarEmpresa.addActionListener(this);
        this.vistaPrincipal.btn_modificar_empresa.addActionListener(this);
        this.vistaPrincipal.btn_limpiarContrato1.addActionListener(this);
        this.vistaPrincipal.btn_agregar_rubro.addActionListener(this);
        this.vistaPrincipal.btn_buscar_empresa.addActionListener(this);
        this.vistaPrincipal.txt_Empresa_rut.addActionListener(this);
         this.vistaPrincipal.txt_emp_rubro.addActionListener(this);
     
      
        llenarCombos();
        listarEmpresa();
        limpiarlistarEmpresas();
        llenarrubro();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*aqui enpiea las funciones del controlador de empresa */
        if (e.getSource() == vistaPrincipal.btn_guardarEmpresa) {
            /*en esta seccion se realizan las validaciones de la empresa a ingresar*/
            if (vistaPrincipal.txt_Empresa_rut.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar un rut de empresa", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (!validarRut(vistaPrincipal.txt_Empresa_rut.getText())) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar un rut valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (vistaPrincipal.txt_razon.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar un nombre de empresa", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (vistaPrincipal.txt_Nombre_empresa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar el representante", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (vistaPrincipal.txt_cantrabajadores.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar cantidad de trabajadores", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (vistaPrincipal.txt_telefono_empresa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar telefon empresa", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (vistaPrincipal.txt_emp_rubro.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar un rubro", "Error", JOptionPane.ERROR_MESSAGE);
        return;

            }

            try {
                /*aqui rscatamoslos datos al momento de llenar los campos*/
                String rut = vistaPrincipal.txt_Empresa_rut.getText().toLowerCase();
                String nombre = vistaPrincipal.txt_Nombre_empresa.getText().toLowerCase();
                String razon = vistaPrincipal.txt_razon.getText().toLowerCase();
                String direccion = vistaPrincipal.txt_direccion_empresa.getText().toLowerCase();
                int canttrabajadores = Integer.parseInt(vistaPrincipal.txt_cantrabajadores.getText());
                String telefono = vistaPrincipal.txt_telefono_empresa.getText().toLowerCase();
                int estado;
                if (vistaPrincipal.cmb_estado_empresa.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }
                /*creamos la empresaDAO para utilizar el metodo crearempresa*/
                EmpresaDAO ce = new EmpresaDAO();
                /*creamos la empresaDTO para setear los campos*/
                EmpresaDTO empresa = new EmpresaDTO();
                empresa.setEmpRut(rut);
                empresa.setEmpNom(nombre);
                empresa.setEmpDirecc(direccion);
                empresa.setEmpTelefono(telefono);
                empresa.setEmpRazons(razon);
                empresa.setEmpEstado((char) estado);
                empresa.setEmpTrabajadores(canttrabajadores);
                /*se crea la empresa*/
                if (ce.crearEmpresa(empresa) == 1) {
           
            try {
               // String rut_emp = vistaPrincipal.txt_Empresa_rut.getText();
               
                EmpresaDAO c = new EmpresaDAO();
                EmpresaDTO empresas = c.leerEmpresa(vistaPrincipal.txt_Empresa_rut.getText());
                rubroDAO r = new rubroDAO();
                RubroDTO rubr = r.leerRubro(Integer.parseInt(vistaPrincipal.txt_emp_rubro.getText().split("-")[0]));
                //int id_rubro = Integer.parseInt(vistaPrincipal.txt_emp_rubro.getText().split("-")[0]);
                Detalle_rubroDTO rubro = new Detalle_rubroDTO();
                Detalle_rubroDAO ru = new Detalle_rubroDAO();
                  
                 rubro.setCantidad(1);
                 rubro.setEmpresa(empresas);
                 rubro.setRubro(rubr);
                 
                  
                 if ( ru.crearDetallerubro(rubro)== 1) {
                 

                   
                } else {
                    JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido el rubro", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido registrar rubro", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
     
                    JOptionPane.showMessageDialog(vistaPrincipal, "Se ha creado una empresa", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    /*llenamos el combo de estado*/
                    llenarCombos();
                    listarEmpresa();
                } else {
                    JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido agregar empresa", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido registrar empresa", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
        /*aqui empieza la funcion de modificar*/
        if (e.getSource() == vistaPrincipal.btn_modificar_empresa) {

            if (!validarRut(vistaPrincipal.txt_Empresa_rut.getText())) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar un rut valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (vistaPrincipal.txt_Empresa_rut.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar un rut de empresa", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (vistaPrincipal.txt_razon.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar un nombre de empresa", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (vistaPrincipal.txt_Nombre_empresa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar el representante", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (vistaPrincipal.txt_cantrabajadores.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar cantidad de trabajadores", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (vistaPrincipal.txt_telefono_empresa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debe ingresar telefon empresa", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            try {

                String rut = vistaPrincipal.txt_Empresa_rut.getText().toLowerCase();
                String nombre = vistaPrincipal.txt_Nombre_empresa.getText().toLowerCase();
                String razon = vistaPrincipal.txt_razon.getText().toLowerCase();
                String direccion = vistaPrincipal.txt_direccion_empresa.getText().toLowerCase();
                int canttrabajadores = Integer.parseInt(vistaPrincipal.txt_cantrabajadores.getText());
                String telefono = vistaPrincipal.txt_telefono_empresa.getText().toLowerCase();
                int estado;
                if (vistaPrincipal.cmb_estado_empresa.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }

                EmpresaDAO ce = new EmpresaDAO();
                EmpresaDTO empresa = new EmpresaDTO();
                empresa.setEmpRut(rut);
                empresa.setEmpNom(nombre);
                empresa.setEmpDirecc(direccion);
                empresa.setEmpTelefono(telefono);
                empresa.setEmpRazons(razon);
                empresa.setEmpEstado((char) estado);
                empresa.setEmpTrabajadores(canttrabajadores);

                if (ce.ModificarEmpresa(empresa) == 1) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Se ha creado una empresa", "Exito", JOptionPane.INFORMATION_MESSAGE);

                    listarEmpresa();
                } else {
                    JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido agregar empresa", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Empresa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido registrar empresa", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == vistaPrincipal.btn_limpiarContrato1) {

            limpiarlistarEmpresas();
            listarEmpresa();

        } else if (e.getSource() == vistaPrincipal.btn_buscar_empresa) {

            String rut = vistaPrincipal.txt_Empresa_rut.getText();
            buscarEmpresa(rut);

        }else if

        (e.getSource() == vistaPrincipal.btn_agregar_rubro) {
        vistaPrincipal.Jrubro.setVisible(true);
        vistaPrincipal.Jrubro.setMaximumSize(new Dimension(200, 500));
        vistaPrincipal.Jrubro.setSize(new Dimension(800, 180));
        vistaPrincipal.Jrubro.setLocationRelativeTo(null);
         
        
       }
    }

    public String estandarRut(String rut) {
        rut = rut.replace(".", "");
        int pos = rut.length();
        if (!rut.contains("-")) {
            rut = rut.substring(0, pos - 1) + "-" + rut.substring(pos - 1, pos);
        }
        return rut;
    }

    public void llenarCombos() {
        vistaPrincipal.cmb_estado_empresa.addItem("Inactivo");
        vistaPrincipal.cmb_estado_empresa.addItem("Activo");
        vistaPrincipal.cmb_estado_empresa.setSelectedIndex(0);
    }

    private void listarEmpresa() {
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
        modelo.addColumn("Rut");
        modelo.addColumn("razon social");
        modelo.addColumn("nombre");
        modelo.addColumn("direccion");
        modelo.addColumn("telefono");
        modelo.addColumn("cantidad trabajadores");
        modelo.addColumn("Estado");

        EmpresaDAO ca = new EmpresaDAO();
        for (EmpresaDTO a : ca.listarEmpresas()) {

            Object[] fila = new Object[7]; // Hay tres columnas en la tabla
            // fila[0] = a.getId_sucursal();
            fila[0] = a.getEmpRut(); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
            fila[1] = a.getEmpRazons();
            fila[2] = a.getEmpNom();
            fila[3] = a.getEmpDirecc();
            fila[4] = a.getEmpTelefono();
            fila[5] = a.getEmpTrabajadores();
            if (a.getEmpEstado() == 0) {
                fila[6] = "Inactivo";
            } else {
                fila[6] = "Activo";
            }
            modelo.addRow(fila);
        }

        vistaPrincipal.tabla_empresa.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
        vistaPrincipal.tabla_empresa.setRowSorter(elQueOrdena);
    }

    //a.getEmpRut()+" "+a.getEmpRazons()
    private void llenarEmpresa() {

        EmpresaDAO ca = new EmpresaDAO();
        TextAutoCompleter ac;
        ac = new TextAutoCompleter(vistaPrincipal.txt_Empresa_rut);
        for (EmpresaDTO a : ca.listarEmpresas()) {
            ac.addItem(a.getEmpRut());

        }

    }
     private void llenarrubro() {

         rubroDAO ca = new rubroDAO();
        TextAutoCompleter ac;
        ac = new TextAutoCompleter(vistaPrincipal.txt_emp_rubro);
        for (RubroDTO a : ca.listarProfesional()) {
            ac.addItem(a.getId_rubro()+"-"+a.getNombre());
            

        }

    }

    private void buscarEmpresa(String rut) {
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
        modelo.addColumn("Rut");
        modelo.addColumn("razon social");
        modelo.addColumn("nombre");
        modelo.addColumn("direccion");
        modelo.addColumn("telefono");
        modelo.addColumn("cantidad trabajadores");
        modelo.addColumn("Estado");

        EmpresaDAO ca = new EmpresaDAO();
        for (EmpresaDTO a : ca.listarEmpresas()) {
            if (rut.equalsIgnoreCase(a.getEmpRut())) {

                Object[] fila = new Object[7]; // Hay tres columnas en la tabla
                // fila[0] = a.getId_sucursal();
                fila[0] = a.getEmpRut(); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
                fila[1] = a.getEmpRazons();
                fila[2] = a.getEmpNom();
                fila[3] = a.getEmpDirecc();
                fila[4] = a.getEmpTelefono();
                fila[5] = a.getEmpTrabajadores();
                if (a.getEmpEstado() == 0) {
                    fila[6] = "Inactivo";
                } else {
                    fila[6] = "Activo";
                }
                modelo.addRow(fila);
            }

        }

        vistaPrincipal.tabla_empresa.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(modelo);
        vistaPrincipal.tabla_empresa.setRowSorter(elQueOrdena);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

//metodo para validar correo electronio
    public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            System.out.println("[" + mat.group() + "]");
            return true;
        } else {
            return false;
        }
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

    private void limpiarlistarEmpresas() {
        vistaPrincipal.txt_Empresa_rut.setText("");
        vistaPrincipal.txt_Nombre_empresa.setText("");
        vistaPrincipal.txt_direccion_empresa.setText("");
        vistaPrincipal.txt_telefono_empresa.setText("");
        vistaPrincipal.txt_cantrabajadores.setText("");
        vistaPrincipal.txt_razon.setText("");
        vistaPrincipal.cmb_estado_empresa.setSelectedIndex(0);
        vistaPrincipal.btn_guardarEmpresa.setEnabled(true);
        vistaPrincipal.btn_modificar_empresa.setEnabled(false);
        vistaPrincipal.txt_Empresa_rut.requestFocus();
        vistaPrincipal.txt_Empresa_rut.setEnabled(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vistaPrincipal.tabla_empresa) {
            int filaSeleccionada = vistaPrincipal.tabla_empresa.getSelectedRow();
            if (filaSeleccionada >= 0) {
                vistaPrincipal.btn_modificar_empresa.setEnabled(true);
                vistaPrincipal.btn_guardarEmpresa.setEnabled(false);
                vistaPrincipal.txt_Empresa_rut.setEnabled(false);
                String rut_empresa = vistaPrincipal.tabla_empresa.getValueAt(vistaPrincipal.tabla_empresa.getSelectedRow(), 0).toString();
                String razon = vistaPrincipal.tabla_empresa.getValueAt(vistaPrincipal.tabla_empresa.getSelectedRow(), 1).toString();
                String nombre = vistaPrincipal.tabla_empresa.getValueAt(vistaPrincipal.tabla_empresa.getSelectedRow(), 2).toString();
                String direccion = vistaPrincipal.tabla_empresa.getValueAt(vistaPrincipal.tabla_empresa.getSelectedRow(), 3).toString();
                String telefono = vistaPrincipal.tabla_empresa.getValueAt(vistaPrincipal.tabla_empresa.getSelectedRow(), 4).toString();
                String can = vistaPrincipal.tabla_empresa.getValueAt(vistaPrincipal.tabla_empresa.getSelectedRow(), 5).toString();
                String estado = vistaPrincipal.tabla_empresa.getValueAt(vistaPrincipal.tabla_empresa.getSelectedRow(), 6).toString();

                vistaPrincipal.txt_Empresa_rut.setText(rut_empresa);
                vistaPrincipal.txt_razon.setText(razon);
                vistaPrincipal.txt_Nombre_empresa.setText(nombre);
                vistaPrincipal.txt_direccion_empresa.setText(direccion);
                vistaPrincipal.txt_telefono_empresa.setText(telefono);
                vistaPrincipal.txt_cantrabajadores.setText(can);
                if ("Activo".equals(estado)) {
                    vistaPrincipal.cmb_estado_empresa.setSelectedIndex(1);
                } else {
                    vistaPrincipal.cmb_estado_empresa.setSelectedIndex(0);
                }
            }
        }
    }

}
