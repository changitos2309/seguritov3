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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Modelo.contratoDTO;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TKG
 */
public class contratoDAO {

    public int crearContrato(contratoDTO contrato) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_CONTRATO.PRO_CREAR_CONTRATO(?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);
            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat oDateFormatt = new SimpleDateFormat("dd-MM-yyyy");

            callableStatement.setInt(1, contrato.getContrato_id());
            String fechaini = oDateFormat.format(contrato.getContrato_fecini());
            callableStatement.setString(2, fechaini);
            String fechatermi = oDateFormatt.format(contrato.getContrato_fectermino());
            callableStatement.setString(3, fechatermi);
            callableStatement.setInt(4, (int) contrato.getContrato_ativo());
            callableStatement.setString(5, contrato.getContrato_rmp_rut().getEmpRut());
            callableStatement.setString(6, contrato.getContrato_pro_rut().getProf_rut());

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
    
    public int modificarContrato(contratoDTO contrato) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_CONTRATO.PRO_MODIFICAR_CONTRATO(?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);
            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat oDateFormatt = new SimpleDateFormat("dd-MM-yyyy");

            callableStatement.setInt(1, contrato.getContrato_id());
            String fechaini = oDateFormat.format(contrato.getContrato_fecini());
            callableStatement.setString(2, fechaini);
            String fechatermi = oDateFormatt.format(contrato.getContrato_fectermino());
            callableStatement.setString(3, fechatermi);
            callableStatement.setInt(4, (int) contrato.getContrato_ativo());
            callableStatement.setString(5, contrato.getContrato_rmp_rut().getEmpRut());
            callableStatement.setString(6, contrato.getContrato_pro_rut().getProf_rut());
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
    
    
    public List<contratoDTO> listarcontrato() {
        Connection dbConnection;
        CallableStatement callableStatement;
        ResultSet rs;
        String listarempresa = "{call SGT_CONTRATO.sgt_listar_contrato(?)}";
        List<contratoDTO> list = new ArrayList<>();
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(listarempresa);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rs = (ResultSet) callableStatement.getObject(1);

            while (rs.next()) {

                contratoDTO a = new contratoDTO();
                a.setContrato_id(rs.getInt(1));
                a.setContrato_fecini(rs.getDate(2));
                a.setContrato_fectermino(rs.getDate(3));
                EmpresaDAO p = new EmpresaDAO();
                a.setContrato_rmp_rut(p.leerEmpresa(rs.getString(4)));
                profesionalDAO c = new profesionalDAO();
                a.setContrato_pro_rut(c.leerProfesional(rs.getString(5)));
                a.setContrato_ativo((char) rs.getInt(6));

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
