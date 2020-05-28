/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DAO.EmpresaDAO;
import DAO.UsuarioEmpresaDAO;
import DAO.profesionalDAO;
import Modelo.EmpresaDTO;
import Modelo.UsuarioempresaDTO;
import Modelo.profesionalDTO;
import Vista.JFAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author TKG
 */
public class Controlador_Usuario extends javax.swing.JFrame implements ActionListener, MouseListener {

    private JFAdmin Vuser = new JFAdmin();
    String Mensage = " su contraseña de ingreso al Sistema es la siguiente: ";
    String To = "";
    String Subject = "Su contraseña de acceso al sistema";

    UsuarioEmpresaDAO user = new UsuarioEmpresaDAO();

    public Controlador_Usuario(JFAdmin vistaPrincipal, UsuarioEmpresaDAO UsuarioEmpresaDAO) {

        this.Vuser = vistaPrincipal;
        UsuarioEmpresaDAO user = new UsuarioEmpresaDAO();

        this.Vuser.btn_guardaruser.addActionListener(this);
        this.Vuser.btn_modificar_user.addActionListener(this);
        this.Vuser.btn_limpiar_user.addActionListener(this);
        this.Vuser.tabla_user.addMouseListener(this);
        this.Vuser.JPMusuario.addMouseListener(this);
      
        llenarEmpresa();
        llenarCombos();
        listaruser();
      }
    private static final char[] CONSTS_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encriptaEnMD5(String stringAEncriptar) {
        try {
            MessageDigest msgd = MessageDigest.getInstance("MD5");
            byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
            StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int bajo = (int) (bytes[i] & 0x0f);
                int alto = (int) ((bytes[i] & 0xf0) >> 4);
                strbCadenaMD5.append(CONSTS_HEX[alto]);
                strbCadenaMD5.append(CONSTS_HEX[bajo]);
            }
            return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
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
            JOptionPane.showMessageDialog(Vuser, "Su mensaje ha sido enviado");

        } catch (MessagingException e) {
            System.out.println(e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == Vuser.btn_guardaruser) {
            //validaciones del profesional
            if (!validarRut(Vuser.txt_rut_user.getText())) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar un rut valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (Vuser.txt_rut_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar del profesional rut", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (Vuser.txt_nombre_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar nombre de profesional", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (Vuser.txt_apell_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar apellido profesional", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (!isEmail(Vuser.txt_correo_user.getText())) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar un correo valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (Vuser.txt_user_telefono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar un telefono", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // se llenan lo campos
            String rut_pro = Vuser.txt_rut_user.getText();
            String nombre_pro = Vuser.txt_nombre_user.getText();
            String apell_pro = Vuser.txt_apell_user.getText();

            String prof_correo = Vuser.txt_correo_user.getText();
            String telefono = Vuser.txt_user_telefono.getText();
            int estado;
            if (Vuser.cmb_user_estado.getSelectedIndex() == 0) {
                estado = 0;
            } else {
                estado = 1;
            }
            //creamos la contraseña para el profesional
            String pass = "" + nombre_pro.substring(0, 2) + "" + apell_pro.substring(0, 2) + "" + rut_pro.substring(0, 4);
            //juntamos el nombre y el apellido del profesional
            String nom_completo = nombre_pro + " " + apell_pro;
            /*______________________________________________________________________________________________________________*/
            //crearemos el usuario de profesional en la BD
            UsuarioEmpresaDAO us = new UsuarioEmpresaDAO();
            UsuarioempresaDTO ust = new UsuarioempresaDTO();
            EmpresaDAO c = new EmpresaDAO();
            EmpresaDTO empresa = c.leerEmpresa(this.Vuser.cmb_user_empresa.getSelectedItem().toString().split(" ")[0]);
            ust.setUser_rut(rut_pro);
            ust.setUser_name(nom_completo);
            ust.setUser_correo(prof_correo);
            //se encripta la contraseña del usuario profesional
            ust.setUser_pass(encriptaEnMD5(pass));
            ust.setActivo((char) estado);
            ust.setRol("Empresa");
            ust.setEmpresa(empresa);
            ust.setTelefono(telefono);
            try {
                if (us.crearUsEmpresa(ust) == 1) {
                    To = prof_correo;// valor es de prueba valores de prueb
                    Mensage += " " + pass; // valor es de prueba valores de prueb
                    this.SendMail();
                    listaruser();
                    JOptionPane.showMessageDialog(Vuser, "se ha creado usuario empresa", "Exito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Vuser, "No se ha podido agregar usuario empresa", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        if (ae.getSource() == Vuser.btn_modificar_user) {
            //validaciones del profesional
            if (!validarRut(Vuser.txt_rut_user.getText())) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar un rut valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (Vuser.txt_rut_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar del profesional rut", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (Vuser.txt_nombre_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar nombre de profesional", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }
            if (Vuser.txt_apell_user.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar apellido profesional", "Error", JOptionPane.ERROR_MESSAGE);
                return;

            }

            if (!isEmail(Vuser.txt_correo_user.getText())) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar un correo valido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (Vuser.txt_user_telefono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vuser, "Debe ingresar un telefono", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // se llenan lo campos
            String rut_pro = Vuser.txt_rut_user.getText();
            String nombre_pro = Vuser.txt_nombre_user.getText();
            String apell_pro = Vuser.txt_apell_user.getText();

            String prof_correo = Vuser.txt_correo_user.getText();
            String telefono = Vuser.txt_user_telefono.getText();
            int estado;
            if (Vuser.cmb_user_estado.getSelectedIndex() == 0) {
                estado = 0;
            } else {
                estado = 1;
            }
            //creamos la contraseña para el profesional
            String pass = "" + nombre_pro.substring(0, 2) + "" + apell_pro.substring(0, 2) + "" + rut_pro.substring(0, 4);
            //juntamos el nombre y el apellido del profesional
            String nom_completo = nombre_pro + " " + apell_pro;
            /*______________________________________________________________________________________________________________*/
            //crearemos el usuario de profesional en la BD
            UsuarioEmpresaDAO us = new UsuarioEmpresaDAO();
            UsuarioempresaDTO ust = new UsuarioempresaDTO();
            EmpresaDAO c = new EmpresaDAO();
            EmpresaDTO empresa = c.leerEmpresa(this.Vuser.cmb_user_empresa.getSelectedItem().toString().split(" ")[0]);
            ust.setUser_rut(rut_pro);
            ust.setUser_name(nom_completo);
            ust.setUser_correo(prof_correo);
            //se encripta la contraseña del usuario profesional
            ust.setUser_pass(encriptaEnMD5(pass));
            ust.setActivo((char) estado);
            ust.setRol("Empresa");
            ust.setEmpresa(empresa);
            ust.setTelefono(telefono);
            try {
                if (us.modificarUsEmpresa(ust) == 1) {
                    listaruser();
                    JOptionPane.showMessageDialog(Vuser, "se ha creado usuario empresa", "Exito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Vuser, "No se ha podido agregar usuario empresa", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Controlador_Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        if (ae.getSource()== Vuser.btn_limpiar_user) {
            limpiar();
        }
        
    }
public void limpiar(){
    Vuser.btn_guardaruser.setEnabled(true);
    Vuser.btn_modificar_user.setEnabled(false);
    
}
    private void listaruser() {
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
        modelo.addColumn("nombre completo");
        modelo.addColumn("correo");
        modelo.addColumn("Estado");
        modelo.addColumn("Empresa");
        modelo.addColumn("Telefono");

        UsuarioEmpresaDAO ca = new UsuarioEmpresaDAO();
        for (UsuarioempresaDTO a : ca.listarusuarios()) {
            Object[] fila = new Object[6]; // cantidad de final
            fila[0] = a.getUser_rut(); // debe partirn en eso 0s
            fila[1] = a.getUser_name();
            fila[2] = a.getUser_correo();
            if (a.getActivo() == 0) {
                fila[3] = "Inactivo";
            } else {
                fila[3] = "Activo";
            }
            fila[4] = a.getEmpresa().getEmpRut()+" "+a.getEmpresa().getEmpRazons();
            fila[5] = a.getTelefono();
            modelo.addRow(fila);

        }

        Vuser.tabla_user.setModel(modelo);
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<>(modelo);
        Vuser.tabla_user.setRowSorter(elQueOrdena);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == Vuser.tabla_user) {
            int filaSeleccionada = Vuser.tabla_user.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Vuser.btn_modificar_user.setEnabled(true);
                Vuser.btn_guardaruser.setEnabled(false);

                String rut_user = Vuser.tabla_user.getValueAt(Vuser.tabla_user.getSelectedRow(), 0).toString();
                String nombre = Vuser.tabla_user.getValueAt(Vuser.tabla_user.getSelectedRow(), 1).toString();
                String correo = Vuser.tabla_user.getValueAt(Vuser.tabla_user.getSelectedRow(), 2).toString();
                String estado = Vuser.tabla_user.getValueAt(Vuser.tabla_user.getSelectedRow(), 3).toString();
                String Emprsa = Vuser.tabla_user.getValueAt(Vuser.tabla_user.getSelectedRow(), 4).toString();
                String telefono = Vuser.tabla_user.getValueAt(Vuser.tabla_user.getSelectedRow(), 5).toString();
                Vuser.txt_rut_user.setText(rut_user);
                Vuser.txt_nombre_user.setText(nombre.split(" ")[0]); 
                Vuser.txt_apell_user.setText(nombre.split(" ")[1]); 
                Vuser.txt_correo_user.setText(correo);
                if ("Activo".equals(estado)) {
                    Vuser.cmb_user_estado.setSelectedIndex(1);
                } else {
                    Vuser.cmb_user_estado.setSelectedIndex(0);
                }
                Vuser.cmb_user_empresa.setSelectedItem(Emprsa);
                Vuser.txt_user_telefono.setText(telefono);
                
            }
        }
        
         if (me.getSource() == Vuser.JPMusuario) {
             llenarEmpresa();
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

    private void llenarEmpresa() {

        EmpresaDAO ca = new EmpresaDAO();
        Vuser.cmb_user_empresa.removeAllItems();
        Vuser.cmb_user_empresa.removeAllItems();
        for (EmpresaDTO a : ca.listarEmpresas()) {
            Vuser.cmb_user_empresa.addItem(a.getEmpRut()+" "+a.getEmpRazons());

        }

    }

    public void llenarCombos() {
        Vuser.cmb_user_estado.addItem("Inactivo");
        Vuser.cmb_user_estado.addItem("Activo");
        Vuser.cmb_user_estado.setSelectedIndex(0);

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
}
