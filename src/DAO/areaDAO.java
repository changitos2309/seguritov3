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
import Modelo.areaDTO;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TKG
 */
public class areaDAO {
    
    
    public int creararea(areaDTO area) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_AREA_PROFESIONAL.PRO_CREAR_AREA_PROFESIONAL(?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setInt(1,area.getArea_id());
            callableStatement.setString(2, area.getArea_detalle());
            
            

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
    
    public int modificararea(areaDTO area) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_AREA_PROFESIONAL.PRO_MODIFICAR_ASESORIA(?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setInt(1,area.getArea_id());
            callableStatement.setString(2, area.getArea_detalle());
            
            

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
    
    public List<areaDTO> listarRubros() {
        Connection dbConnection;
        CallableStatement callableStatement;
        ResultSet rs;
        String listarrubros = "{call SGT_AREA_PROFESIONAL.listararea(?)}";
        List<areaDTO> list = new ArrayList<>();
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(listarrubros);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rs = (ResultSet) callableStatement.getObject(1);

            while (rs.next()) {

                areaDTO a = new areaDTO();
                a.setArea_id(rs.getInt(1));
                a.setArea_detalle(rs.getString(2));
               
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "error de base de datos: " + e);
        }

        return list;
    }

    public areaDTO leerRubro(int idarea) {
        areaDTO c = null;

        String SSQL = "SELECT * FROM areaprof WHERE area_id = " + idarea;

        Connection conect;

        try {

            conect = Conexion.getConnection();
            Statement st = conect.createStatement();
            ResultSet rs = st.executeQuery(SSQL);

            if (rs.next()) {

                c = new areaDTO();

                c.setArea_id(rs.getInt(1));
                c.setArea_detalle(rs.getString(2));
                
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error de base de datos: " + ex);

        }

        return c;
    }
    
}
