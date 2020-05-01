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
import Modelo.EmpresaDTO;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TKG
 */
public class EmpresaDAO {

    public int crearEmpresa(EmpresaDTO empresa) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_EMPRESA.pro_crear_empresa(?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setString(1, empresa.getEmpRut());
            callableStatement.setString(2, empresa.getEmpDirecc());
            callableStatement.setString(3, empresa.getEmpRazons());
            callableStatement.setInt(4, (int) empresa.getEmpEstado());
            callableStatement.setInt(5, (int) empresa.getEmpTrabajadores());
            callableStatement.setString(6, empresa.getEmpNom());
            callableStatement.setString(7, empresa.getEmpTelefono());

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
    
    public int ModificarEmpresa(EmpresaDTO empresa) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_EMPRESA.PRO_MODIFICAR_EMPRESA(?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);

            callableStatement.setString(1, empresa.getEmpRut());
            callableStatement.setString(2, empresa.getEmpDirecc());
            callableStatement.setString(3, empresa.getEmpRazons());
            callableStatement.setInt(4, (int) empresa.getEmpEstado());
            callableStatement.setInt(5, (int) empresa.getEmpTrabajadores());
            callableStatement.setString(6, empresa.getEmpNom());
            callableStatement.setString(7, empresa.getEmpTelefono());

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

    public List<EmpresaDTO> listarEmpresas() {
        Connection dbConnection;
        CallableStatement callableStatement;
        ResultSet rs;
        String listarempresa = "{call SGT_EMPRESA.sgt_listar_empresa(?)}";
        List<EmpresaDTO> list = new ArrayList<>();
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(listarempresa);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rs = (ResultSet) callableStatement.getObject(1);

            while (rs.next()) {

                EmpresaDTO a = new EmpresaDTO();
                a.setEmpRut(rs.getString(1));
                a.setEmpRazons(rs.getString(2));
                a.setEmpNom(rs.getString(3));
                a.setEmpDirecc(rs.getString(4));
                a.setEmpTelefono(rs.getString(5));
                a.setEmpTrabajadores(rs.getInt(6));
                a.setEmpEstado((char) rs.getInt(7));

                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "error de base de datos: " + e);
        }

        return list;
    }
    
    
    
    public EmpresaDTO leerEmpresa(String rut) {
        EmpresaDTO em = null;

        String SSQL = "SELECT * FROM empresa WHERE EMP_RUT = '"+rut+"'";

        Connection conect;

        try {

            conect = Conexion.getConnection();
            Statement st = conect.createStatement();
            ResultSet rs = st.executeQuery(SSQL);

            if (rs.next()) {

              em = new EmpresaDTO();
                em.setEmpRut(rs.getString(1));
                em.setEmpDirecc(rs.getString(2));
                em.setEmpRazons(rs.getString(3));
                em.setEmpEstado((char) rs.getInt(4));
                em.setEmpTrabajadores(rs.getInt(5));
                em.setEmpNom(rs.getString(6));
                em.setEmpTelefono(rs.getString(7));

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error de base de datos: " + ex);

        }

        return em;
    }

}
