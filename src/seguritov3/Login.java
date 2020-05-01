/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguritov3;
import DAO.UsuarioDAO;
import Vista.*;
import Controlador.*;
/**
 *
 * @author TKG
 */
public class Login {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFlogin vistaL = new JFlogin();
       UsuarioDAO modeloL = new UsuarioDAO();
        Controlador_Login controlador_Login = new Controlador_Login(vistaL,modeloL);
    }
    
}
