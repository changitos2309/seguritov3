/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.VisitaDTO;
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
public class VisitaDAO {

    public VisitaDAO() {
    }
    
    public int crearvisita(VisitaDTO visita) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_VISITA.PRO_CREAR_VISTA(?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);
            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
           
            callableStatement.setInt(1, visita.getId_vista());
            String fecha_visita = oDateFormat.format(visita.getFecha_visita());
            callableStatement.setString(2,fecha_visita );
            callableStatement.setString(3,visita.getHora_visita());
            callableStatement.setString(4, visita.getVisita_descripcion());
            callableStatement.setString(5,visita.getVista_realizada() );
            callableStatement.setInt(6,  visita.getAsesoria().getAses_id());
            callableStatement.setString(7, visita.getProfesional().getProf_rut());
            

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
    
     public int modificarvisita(VisitaDTO visita) throws SQLException {
        int resultado = 0;
        Connection dbConnection;
        CallableStatement callableStatement;
        String crearsql = "{call SGT_VISITA.PRO_MODIFICAR_VISITA(?,?,?,?,?,?,?)}";
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(crearsql);
            DateFormat oDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat oDateFormatt = new SimpleDateFormat("HH:mm");
            callableStatement.setInt(1, visita.getId_vista());
            String fecha_visita = oDateFormat.format(visita.getFecha_visita());
            callableStatement.setString(2,fecha_visita );
            callableStatement.setString(3,visita.getHora_visita());
            callableStatement.setString(4, visita.getVisita_descripcion());
            callableStatement.setString(5,visita.getVista_realizada() );
            callableStatement.setInt(6,  visita.getAsesoria().getAses_id());
            callableStatement.setString(7, visita.getProfesional().getProf_rut());
           

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
     
     
     
     public List<VisitaDTO> listarProfesional() {
        Connection dbConnection;
        CallableStatement callableStatement;
        ResultSet rs;
        String listarempresa = "{call SGT_VISITA.sp_listar_visita(?)}";
        List<VisitaDTO> list = new ArrayList<>();
        try {
            dbConnection = Conexion.getConnection();
            callableStatement = dbConnection.prepareCall(listarempresa);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.executeUpdate();
            rs = (ResultSet) callableStatement.getObject(1);

            while (rs.next()) {

                VisitaDTO a = new VisitaDTO();
                a.setId_vista(rs.getInt(1));
                a.setFecha_visita(rs.getDate(2));
                a.setHora_visita(rs.getString(3));
                a.setVisita_descripcion(rs.getString(4));
                asesoriaDAO as = new asesoriaDAO();
                a.setAsesoria(as.leerasesoria(rs.getInt(5)));
                profesionalDAO p = new profesionalDAO();
                a.setProfesional(p.leerProfesional(rs.getString(6)));
                a.setVista_realizada( rs.getString(7));

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
