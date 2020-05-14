/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.JFlogin;
import Vista.JFAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Modelo.UsuarioDTO;
import DAO.UsuarioDAO;
import Controlador.Controlador_Empresa;
import DAO.EmpresaDAO;
import DAO.areaDAO;
import DAO.asesoriaDAO;
import DAO.contratoDAO;
import DAO.profesionalDAO;
import DAO.servicioDAO;

public class Controlador_Login implements ActionListener {

    JFlogin vistaLogin = new JFlogin();
    UsuarioDAO modeloLogin = new UsuarioDAO();
    public static UsuarioDTO emp = null;
    public static Boolean esVisible = true;

    public Controlador_Login(JFlogin vistaLogin, UsuarioDAO modeloLogin) {
        this.modeloLogin = modeloLogin;
        this.vistaLogin = vistaLogin;
        this.vistaLogin.btn_ingresar.addActionListener(this);
        vistaLogin.setVisible(true);
        vistaLogin.setLocationRelativeTo(null);
    }

    public void Inicializar() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaLogin.btn_ingresar) {
            String rut_tbox = vistaLogin.txt_login_rut.getText();
            String pass = new String(vistaLogin.txt_login_pass.getPassword());

            String user = rut_tbox.replace(".", "");
            user = user.replace("-", "");
            user = user.substring(0, user.length() - 1) + "-" + user.substring(user.length() - 1, user.length());
            System.out.println("user: " + user);

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(vistaLogin, "No se aceptan campos vacíos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (modeloLogin.validar_ingreso(user, pass) != null) {
                emp = modeloLogin.validar_ingreso(user, pass);
                if (esVisible == true) {
                    esVisible = false;
                    vistaLogin.setVisible(false);
                    JFAdmin vistaPrincipal = new JFAdmin();

                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    EmpresaDAO EmpresaDAO = new EmpresaDAO();
                    profesionalDAO profeDAO = new profesionalDAO();
                    servicioDAO servi = new servicioDAO();
                    contratoDAO contrato = new contratoDAO();
                    areaDAO area = new areaDAO();
                    asesoriaDAO asesoria = new asesoriaDAO();
                    Controlador_asesoria as = new Controlador_asesoria(vistaPrincipal, asesoria);
                    Controlador_Empresa cp = new Controlador_Empresa(vistaPrincipal, usuarioDAO);
                    Controlador_profesional pr = new Controlador_profesional(vistaPrincipal, profeDAO);
                    Controlador_servicio sr = new Controlador_servicio(vistaPrincipal, servi);
                    Controlador_contrato cn = new Controlador_contrato(vistaPrincipal, contrato);
                     controlador_area ar = new controlador_area(vistaPrincipal, area);
                }

            } else {
                JOptionPane.showMessageDialog(vistaLogin, "Credenciales invalidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
