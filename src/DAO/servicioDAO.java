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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Modelo.servicioDTO;
import oracle.jdbc.OracleTypes;
/**
 *
 * @author TKG
 */
public class servicioDAO {
      public int crearServicio(servicioDTO servicio) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_SERVICIO.PRO_CREAR_SERVICIO(?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setInt(1,servicio.getSer_id());
            callableStatement.setString(2, servicio.getSer_nombre());
            callableStatement.setInt(3, servicio.getSer_valor());
            callableStatement.setInt(4, (int) servicio.getSer_activo());
            

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
      
      
        public int modiificarServicio(servicioDTO servicio) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_SERVICIO.PRO_MODIFICAR_SERVICIO(?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setInt(1,servicio.getSer_id());
            callableStatement.setString(2, servicio.getSer_nombre());
            callableStatement.setInt(3, servicio.getSer_valor());
            callableStatement.setInt(4, (int) servicio.getSer_activo());
            

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
    public List<servicioDTO> listarServicio() {
        Connection dbConnection;
        CallableStatement callableStatement;
        ResultSet rs;
        String listarempresa = "{call SGT_SERVICIO.sgt_listar_servicio(?)}";
        List<servicioDTO> list = new ArrayList<>();
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(listarempresa);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rs = (ResultSet) callableStatement.getObject(1);

            while (rs.next()) {

                servicioDTO a = new servicioDTO();
                a.setSer_id(rs.getInt(1));
                a.setSer_nombre(rs.getString(2));
                a.setSer_valor(rs.getInt(3));
                a.setSer_activo((char) rs.getInt(4));

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
