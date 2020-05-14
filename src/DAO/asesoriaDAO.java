/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.asesoriaDTO;
import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TKG
 */
public class asesoriaDAO {
     public int crearasesoria(asesoriaDTO asesoria) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_ASESORIA.PRO_CREAR_ASESORIA(?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);
            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            

            callableStatement.setInt(1, asesoria.getAses_id());
            String fecha = oDateFormat.format(asesoria.getAses_fecha());
            callableStatement.setString(2, fecha);
            callableStatement.setInt(3, (char) asesoria.getSes_realizado());
            callableStatement.setString(4,  asesoria.getEmpresa().getEmpRut());
            callableStatement.setString(5, asesoria.getAses_tipo());
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
     
     
     public int modificarasesoria(asesoriaDTO asesoria) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_ASESORIA.PRO_MODIFICAR_ASESORIA(?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);
            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            

            callableStatement.setInt(1, asesoria.getAses_id());
            String fecha = oDateFormat.format(asesoria.getAses_fecha());
            callableStatement.setString(2, fecha);
            callableStatement.setInt(3, (char) asesoria.getSes_realizado());
            callableStatement.setString(4,  asesoria.getEmpresa().getEmpRut());
            callableStatement.setString(5, asesoria.getAses_tipo());
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
     
      public List<asesoriaDTO> listarcontrato() {
        Connection dbConnection;
        CallableStatement callableStatement;
        ResultSet rs;
        String listarempresa = "{call SGT_ASESORIA.sp_listar_asesoria(?)}";
        List<asesoriaDTO> list = new ArrayList<>();
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(listarempresa);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rs = (ResultSet) callableStatement.getObject(1);

            while (rs.next()) {

                asesoriaDTO a = new asesoriaDTO();
                a.setAses_id(rs.getInt(1));
                a.setAses_fecha(rs.getDate(2));
                a.setSes_realizado((char) rs.getInt(3));
                EmpresaDAO p = new EmpresaDAO();
                a.setEmpresa(p.leerEmpresa(rs.getString(4)));
                a.setAses_tipo( rs.getString(5));

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
