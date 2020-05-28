/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.UsuarioempresaDTO;
import Modelo.profesionalDTO;
import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TKG
 */
public class UsuarioEmpresaDAO {
    
    public int crearUsuarioEmpresa(UsuarioempresaDTO usuario) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_USUARIO_EMPRESA.pro_agregar_usuario(?,?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setString(1, usuario.getUser_rut());
            callableStatement.setString(2, usuario.getUser_name());
            callableStatement.setString(3, usuario.getUser_correo());
            callableStatement.setString(4, usuario.getUser_pass());
            callableStatement.setInt(5, (int) usuario.getActivo());
            callableStatement.setString(6, null);
            callableStatement.setString(7, usuario.getRol());
            callableStatement.setString(8, usuario.getTelefono());

            // execute getDBUSERCursor store procedure
            int rowsInserted = callableStatement.executeUpdate();
            if (rowsInserted > 0) {
                resultado = 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
     public int crearUsEmpresa(UsuarioempresaDTO usuario) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_USUARIO_EMPRESA.pro_agregar_usuario(?,?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setString(1, usuario.getUser_rut());
            callableStatement.setString(2, usuario.getUser_name());
            callableStatement.setString(3, usuario.getUser_correo());
            callableStatement.setString(4, usuario.getUser_pass());
            callableStatement.setInt(5, (int) usuario.getActivo());
            callableStatement.setString(6, usuario.getEmpresa().getEmpRut());
            callableStatement.setString(7, usuario.getRol());
             callableStatement.setString(8, usuario.getTelefono());

            // execute getDBUSERCursor store procedure
            int rowsInserted = callableStatement.executeUpdate();
            if (rowsInserted > 0) {
                resultado = 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
     public int modificarUsEmpresa(UsuarioempresaDTO usuario) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_USUARIO_EMPRESA.pro_modificar_usuario(?,?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setString(1, usuario.getUser_rut());
            callableStatement.setString(2, usuario.getUser_name());
            callableStatement.setString(3, usuario.getUser_correo());
            callableStatement.setString(4, usuario.getUser_pass());
            callableStatement.setInt(5, (int) usuario.getActivo());
            callableStatement.setString(6, usuario.getEmpresa().getEmpRut());
            callableStatement.setString(7, usuario.getRol());
             callableStatement.setString(8, usuario.getTelefono());

            // execute getDBUSERCursor store procedure
            int rowsInserted = callableStatement.executeUpdate();
            if (rowsInserted > 0) {
                resultado = 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    public List<UsuarioempresaDTO> listarusuarios() {
        Connection dbConnection;
        CallableStatement callableStatement;
        ResultSet rs;
        String listarempresa = "{call SGT_USUARIO_EMPRESA.sp_listar_usuarioempresa(?)}";
        List<UsuarioempresaDTO> list = new ArrayList<>();
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(listarempresa);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rs = (ResultSet) callableStatement.getObject(1);

            while (rs.next()) {

                UsuarioempresaDTO a = new UsuarioempresaDTO();
                a.setUser_rut(rs.getString(1));
                a.setUser_name(rs.getString(2));
                a.setUser_correo(rs.getString(3));
                a.setActivo((char) rs.getInt(4));
                EmpresaDAO p = new EmpresaDAO();
                a.setEmpresa(p.leerEmpresa(rs.getString(5)));
                a.setTelefono(rs.getString(6));
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "error de base de datos: " + e);
        }

        return list;
    }
    
}
