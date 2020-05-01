/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

// controlador.Controlador_Empresa;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import vista.JFPrincipal;

public class Conexion {

    private static Connection con = null;

    public static Connection getConnection() {
        try {
            Properties p = new Properties();
            FileReader f = new FileReader("./config.properties");
            p.load(f);
            if (con == null) {
                String driver = "oracle.jdbc.OracleDriver"; //el driver varia segun la DB que usemos
                String url = p.getProperty("url");
                String pwd = p.getProperty("pwd");
                String usr = p.getProperty("usr");
                String url_backup = p.getProperty("url_backup");

//            String driver="oracle.jdbc.OracleDriver"; //el driver varia segun la DB que usemos
//            String url="jdbc:oracle:thin:@localhost:1521:XE";
//            String pwd="ontour";
//            String usr="ontour";
                Class.forName(driver);
                try {
                    con = DriverManager.getConnection(url, usr, pwd);
                    JOptionPane.showMessageDialog(null, "Conectado al Servidor BD Primario");
                } catch (SQLException ex) {
                    try {
                        con = DriverManager.getConnection(url_backup, usr, pwd);
                        JOptionPane.showMessageDialog(null, "Conectado al Servidor BD Secundario");
                    } catch (SQLException ex2) {
                        Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex2);
                        JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexión con ningún servidor, error: " + ex);
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexión, error: " + ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexión, error: " + ex);
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexión, error: " + ex);
        }
        return con;
    }

}
