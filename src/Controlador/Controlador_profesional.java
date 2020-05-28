/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;


import Modelo.profesionalDTO;
import Modelo.areaDTO;
import DAO.UsuarioDAO;
import DAO.UsuarioEmpresaDAO;
import DAO.areaDAO;
import DAO.profesionalDAO;
import Modelo.UsuarioempresaDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.logging.Level;
import java.util.logging.Logger;
import Vista.JFAdmin;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
       
     String Mensage = " su contraseña de ingreso al Sistema es la siguiente: ";
    String To = "";
    String Subject = "Su contraseña de acceso al sistema";
    public Controlador_profesional(JFAdmin vistaPrincipal, profesionalDAO profesionalDAO) {
       

        this.Vprofesional = vistaPrincipal;
        UsuarioEmpresaDAO user = new UsuarioEmpresaDAO();
        this.profesionalDAO = profesionalDAO;
        //Objetos de acceso a datos
       
        this.profesionalDAO =profesionalDAO;
         this.Vprofesional.tabla_profesional.addMouseListener(this);
         this.Vprofesional.JPMprofesional.addMouseListener(this);
        this.Vprofesional.btn_guardarProfesional.addActionListener(this);
        this.Vprofesional.btn_modificar_profesional.addActionListener(this);
        this.Vprofesional.btn_limpiar_profesional1.addActionListener(this);
        this.Vprofesional.btn_buscar_profesional.addActionListener(this);
        
       llenarprofesional();
       llenarCombos();
       llenarArea();
       listarProfesional();
        
       
    }
    public void SendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("empresasegurito@gmail.com", "clave123567");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("empresasegurito@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setText(Mensage);

            Transport.send(message);
            JOptionPane.showMessageDialog(Vprofesional, "Su mensaje ha sido enviado");

        } catch (MessagingException e) {
            System.out.println(e);
        }

    }
private void llenarprofesional() {
         profesionalDAO ca = new profesionalDAO();
      TextAutoCompleter ac;
        ac = new TextAutoCompleter(Vprofesional.txt_prof_rut);
        for (profesionalDTO a : ca.listarProfesional()) {
          ac.addItem(a.getProf_rut());

        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == Vprofesional.btn_guardarProfesional) {
            //validaciones del profesional
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
              if (Vprofesional.txt_prof_correo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar un correo", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
               if (!isEmail(Vprofesional.txt_prof_correo.getText())) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar un correo valido", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }  
               if (Vprofesional.cmb_area.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe selecionar area profesional", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            try {
                // se llenan lo campos 
                String rut_pro = Vprofesional.txt_prof_rut.getText();
                String nombre_pro = Vprofesional.txt_prof_nombre.getText();
                String apell_pro = Vprofesional.txt_prof_apell.getText();
                String telef_pro = Vprofesional.txt_prof_teleono.getText();
               String prof_correo = Vprofesional.txt_prof_correo.getText();
              
             
                int estado;
                if (Vprofesional.cmb_prof_estado.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }
                //llenamos el area del profesional
                areaDAO c = new areaDAO();
                areaDTO area = c.leerRubro(Integer.parseInt(this.Vprofesional.cmb_area.getSelectedItem().toString().split("-")[0]));
                 //agregamos un profesional a la DB
                profesionalDAO pr = new profesionalDAO();
                profesionalDTO profesionaol = new profesionalDTO();

                profesionaol.setProf_rut(rut_pro);
                profesionaol.setProf_nombre(nombre_pro);
                profesionaol.setProf_apell(apell_pro);
                profesionaol.setProf_telefono(telef_pro);
                profesionaol.setProf_estado((char) estado);
                profesionaol.setProf_area_id(area);
                profesionaol.setProf_correo(prof_correo);
               
                
                
             //creamos la contraseña para el profesional
               String pass = "" + nombre_pro.substring(0, 2) + "" + apell_pro.substring(0, 2) + "" + rut_pro.substring(0, 4);
             //juntamos el nombre y el apellido del profesional
               String nom_completo = nombre_pro+" "+apell_pro;
             /*______________________________________________________________________________________________________________*/
             //crearemos el usuario de profesional en la BD
                UsuarioEmpresaDAO us = new UsuarioEmpresaDAO();
                UsuarioempresaDTO ust = new UsuarioempresaDTO();
               String rut = null;
                ust.setUser_rut(rut_pro);
                ust.setUser_name(nom_completo);
                ust.setUser_correo(prof_correo);
                //se encripta la contraseña del usuario profesional
                ust.setUser_pass(encriptaEnMD5(pass));
                ust.setActivo((char) estado);
                ust.setRol("Profesional");
                ust.setTelefono(telef_pro);
                
                 if ( us.crearUsuarioEmpresa(ust)==1) {
                       To = prof_correo;// valor es de prueba valores de prueb
                        Mensage += " " + pass ; // valor es de prueba valores de prueb
                        this.SendMail();
                        JOptionPane.showMessageDialog(Vprofesional, "se ha creado usuario profesional", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    }
                
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
                if (Vprofesional.txt_prof_correo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar un correo", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
               if (!isEmail(Vprofesional.txt_prof_correo.getText())) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe ingresar un correo valido", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
                 if (Vprofesional.cmb_area.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(Vprofesional, "Debe selecionar area profesional", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            try {

                String rut_pro = Vprofesional.txt_prof_rut.getText();
                String nombre_pro = Vprofesional.txt_prof_nombre.getText();
                String apell_pro = Vprofesional.txt_prof_apell.getText();
                String telef_pro = Vprofesional.txt_prof_teleono.getText();
                String prof_correo = Vprofesional.txt_prof_correo.getText();

                int estado;
                if (Vprofesional.cmb_prof_estado.getSelectedIndex() == 0) {
                    estado = 0;
                } else {
                    estado = 1;
                }
                areaDAO c = new areaDAO();
                areaDTO area = c.leerRubro(Integer.parseInt(this.Vprofesional.cmb_area.getSelectedItem().toString().split("-")[0]));
                profesionalDAO pr = new profesionalDAO();
                profesionalDTO profesionaol = new profesionalDTO();

                profesionaol.setProf_rut(rut_pro);
                profesionaol.setProf_nombre(nombre_pro);
                profesionaol.setProf_apell(apell_pro);
                profesionaol.setProf_telefono(telef_pro);
                profesionaol.setProf_estado((char) estado);
                profesionaol.setProf_area_id(area);
                profesionaol.setProf_correo(prof_correo);
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
                String correo = Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 4).toString();
                String areaid = Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 5).toString();
                String estado = Vprofesional.tabla_profesional.getValueAt(Vprofesional.tabla_profesional.getSelectedRow(), 6).toString();

                Vprofesional.txt_prof_rut.setText(rut_profesional);
                Vprofesional.txt_prof_nombre.setText(nombre);
                Vprofesional.txt_prof_apell.setText(apell);
                Vprofesional.txt_prof_teleono.setText(telefono);
                Vprofesional.txt_prof_correo.setText(correo);
                Vprofesional.cmb_area.setSelectedItem(areaid);
                
                if ("Activo".equals(estado)) {
                    Vprofesional.cmb_prof_estado.setSelectedIndex(1);
                } else {
                    Vprofesional.cmb_prof_estado.setSelectedIndex(0);
                }
            }
        }
 if (e.getSource() == Vprofesional.JPMprofesional) {
           llenarArea();
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
               
                
//                public static String getMd5(String input) { 
//        try { 
//  
//            // Static getInstance method is called with hashing MD5 
//            MessageDigest md = MessageDigest.getInstance("MD5"); 
//  
//            // digest() method is called to calculate message digest 
//            //  of an input digest() return array of byte 
//            byte[] messageDigest = md.digest(input.getBytes()); 
//  
//            // Convert byte array into signum representation 
//            BigInteger no = new BigInteger(1, messageDigest); 
//  
//            // Convert message digest into hex value 
//            String hashtext = no.toString(16); 
//            while (hashtext.length() < 32) { 
//                hashtext = "0" + hashtext; 
//            } 
//            return hashtext; 
//        }  
//        
//        
//  
//        // For specifying wrong message digest algorithms 
//        catch (NoSuchAlgorithmException e) { 
//            throw new RuntimeException(e); 
//        } 
//    }
                
                 private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
    public static String encriptaEnMD5(String stringAEncriptar)
    {
        try
        {
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           for (int i = 0; i < bytes.length; i++)
           {
               int bajo = (int)(bytes[i] & 0x0f);
               int alto = (int)((bytes[i] & 0xf0) >> 4);
               strbCadenaMD5.append(CONSTS_HEX[alto]);
               strbCadenaMD5.append(CONSTS_HEX[bajo]);
           }
           return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
           return null;
        }
    }
    
      private void  buscarprofesional(String rut) {
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
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Telefono");
        modelo.addColumn("correo");
        modelo.addColumn("area");
        modelo.addColumn("Estado");

        profesionalDAO ca = new profesionalDAO();
        for (profesionalDTO a : ca.listarProfesional()) {
  if (rut.equalsIgnoreCase(a.getProf_rut())){
            Object[] fila = new Object[7]; // cantidad de final
            fila[0] = a.getProf_rut(); // debe partirn en eso 0s
            fila[1] = a.getProf_nombre();
            fila[2] = a.getProf_apell();
            fila[3] = a.getProf_telefono();
            fila[4] = a.getProf_correo();
            fila[5] = a.getProf_area_id();
            
            if (a.getProf_estado() == 0) {
                fila[6] = "Inactivo";
            } else {
                fila[6] = "Activo";
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
                if (columna > 7) {
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
        modelo.addColumn("correo");
        modelo.addColumn("area");
        modelo.addColumn("Estado");

        profesionalDAO ca = new profesionalDAO();
        for (profesionalDTO a : ca.listarProfesional()) {
        Object[] fila = new Object[7]; // cantidad de final
            fila[0] = a.getProf_rut(); // debe partirn en eso 0s
            fila[1] = a.getProf_nombre();
            fila[2] = a.getProf_apell();
            fila[3] = a.getProf_telefono();
            fila[4] = a.getProf_correo();
            fila[5] = a.getProf_area_id().getArea_id()+"-"+a.getProf_area_id().getArea_detalle();
            
            if (a.getProf_estado() == 0) {
                fila[6] = "Inactivo";
            } else {
                fila[6] = "Activo";
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
        Vprofesional.cmb_area.addItem("sleccione area");
        for (areaDTO a : ca.listarRubros()) {
            Vprofesional.cmb_area.addItem(a.getArea_id()+"-"+a.getArea_detalle());

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
   public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;        
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            System.out.println("[" + mat.group() + "]");
            return true;
        }else{
            return false;
        }        
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
