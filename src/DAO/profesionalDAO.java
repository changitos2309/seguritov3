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
import Modelo.profesionalDTO;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TKG
 */
public class profesionalDAO {

    public int crearProfesional(profesionalDTO profesional) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_PROFESIONAL.PRO_CREAR_PROFESIONAL(?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setString(1, profesional.getProf_rut());
            callableStatement.setString(2, profesional.getProf_nombre());
            callableStatement.setString(3, profesional.getProf_apell());
            callableStatement.setInt(4, (int) profesional.getProf_estado());
            callableStatement.setString(5, profesional.getProf_telefono());
             callableStatement.setInt(6, (int) profesional.getProf_area_id().getArea_id());
             callableStatement.setString(7,  profesional.getProf_correo());
           

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

    public int ModificarProfesional(profesionalDTO profesional) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_PROFESIONAL.PRO_MODIFICAR_PROFESIONAL(?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setString(1, profesional.getProf_rut());
            callableStatement.setString(2, profesional.getProf_nombre());
            callableStatement.setString(3, profesional.getProf_apell());
            callableStatement.setInt(4, (int) profesional.getProf_estado());
            callableStatement.setString(5, profesional.getProf_telefono());
             callableStatement.setInt(6, (int) profesional.getProf_area_id().getArea_id());
              callableStatement.setString(7,  profesional.getProf_correo());
            
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

    public List<profesionalDTO> listarProfesional() {
        Connection dbConnection;
        CallableStatement callableStatement;
        ResultSet rs;
        String listarempresa = "{call SGT_PROFESIONAL.sp_listar_profesional(?)}";
        List<profesionalDTO> list = new ArrayList<>();
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(listarempresa);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rs = (ResultSet) callableStatement.getObject(1);

            while (rs.next()) {

                profesionalDTO a = new profesionalDTO();
                a.setProf_rut(rs.getString(1));
                a.setProf_nombre(rs.getString(2));
                a.setProf_apell(rs.getString(3));
                a.setProf_telefono(rs.getString(4));
                 a.setProf_correo(rs.getString(5));
                areaDAO p = new areaDAO();
                a.setProf_area_id(p.leerRubro(rs.getInt(6)));
                a.setProf_estado((char) rs.getInt(7));

                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "error de base de datos: " + e);
        }

        return list;
    }

    public profesionalDTO leerProfesional(String rut) {
        profesionalDTO c = null;

        String SSQL = "SELECT * FROM profesional WHERE prof_rut = '"+rut+"'";

        Connection conect;

        try {

            conect = Conexion.getConnection();
            Statement st = conect.createStatement();
            ResultSet rs = st.executeQuery(SSQL);

            if (rs.next()) {

                c = new profesionalDTO();

                c.setProf_rut(rs.getString(1));
                c.setProf_nombre(rs.getString(2));
                c.setProf_apell(rs.getString(3));
                c.setProf_telefono(rs.getString(4));
                c.setProf_estado((char) rs.getInt(5));
                areaDAO p = new areaDAO();
                c.setProf_area_id(p.leerRubro(rs.getInt(6)));
                

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error de base de datos: " + ex);

        }

        return c;
    }

}
