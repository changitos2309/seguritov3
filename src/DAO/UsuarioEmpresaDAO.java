/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.UsuarioempresaDTO;
import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author TKG
 */
public class UsuarioEmpresaDAO {
    
    public int crearUsuarioEmpresa(UsuarioempresaDTO usuario) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_USUARIO_EMPRESA.pro_agregar_usuario(?,?,?,?,?,?,?)}";
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
        String crearsql = "{call SGT_USUARIO_EMPRESA.pro_agregar_usuario(?,?,?,?,?,?,?)}";
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
    
}
