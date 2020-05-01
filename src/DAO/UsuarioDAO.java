/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Modelo.UsuarioDTO;

/**
 *
 * @author TKG
 */
public class UsuarioDAO {

   Conexion conexion;

    public UsuarioDAO() {
         this.conexion = new Conexion();
    }
    
    public UsuarioDTO validar_ingreso(String usuario, String clave) {

        UsuarioDTO a = null;

        String SSQL = "SELECT user_rut,user_nombre,user_apellido,user_correo,QB_ENCRIPCION.FB_DESCENCRIPTAR(user_pass),user_estado FROM usuario_admin WHERE user_rut='" + usuario + "' AND user_pass = QB_ENCRIPCION.FB_ENCRIPTAR('" + clave + "') and user_estado ='1'";

        Connection conect;

        try {

            conect = conexion.getConnection();
            Statement st = conect.createStatement();
            ResultSet rs = st.executeQuery(SSQL);

            if (rs.next()) {

                 a = new UsuarioDTO();
                a.setUserRut(rs.getString(1));
                a.setUserNombre(rs.getString(2));
                a.setUserApellido(rs.getString(3));
                a.setUserCorreo(rs.getString(4));
                a.setUserPass(rs.getString(5));
                a.setUserEstado(rs.getString(6));
                
            }

        } catch (SQLException ex) {
            a = null;
            JOptionPane.showMessageDialog(null, "error de base de datos: " + ex);

        }

        return a;

    }
    
    public int CambiarContraseña(String rut_admin, String contraseñaold, String contraseñanew) {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String modificarsql = "{call SGT_USUARIO_ADMIN.sp_modificar_pass_user(?,?,?)}";
        try {
            dbConnection = conexion.getConnection();
            callableStatement = dbConnection.prepareCall(modificarsql);

            callableStatement.setString(1, rut_admin);
            callableStatement.setString(2, contraseñaold);
            callableStatement.setString(3, contraseñanew);
            int rowsInserted = callableStatement.executeUpdate();
            if (rowsInserted > 0) {
                resultado = 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "error de base de datos: " + e);
        }
        return resultado;
    }
    
}
