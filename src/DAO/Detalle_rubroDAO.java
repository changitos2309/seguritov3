/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Detalle_rubroDTO;
import Modelo.areaDTO;
import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author TKG
 */
public class Detalle_rubroDAO {
    
    //19156853-2
    public int crearDetallerubro(Detalle_rubroDTO rubro) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_DETALLE_RUBRO.PRO_CREAR_DETALLE_RUBRO(?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setInt(1,rubro.getCantidad());
            callableStatement.setString(2, rubro.getEmpresa().getEmpRut());
            callableStatement.setInt(3, rubro.getRubro().getId_rubro());
            
             
            
            

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
