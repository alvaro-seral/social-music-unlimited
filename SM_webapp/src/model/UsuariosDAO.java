package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

public class UsuariosDAO {
	
	private static String insertarUser = "INSERT INTO usuarios(nombre, correo, contrasenya, tipo) VALUES (?,?,?,?)";
	
	public static void insertarUsuario(UsuariosVO usuario_) { 
        
        Connection conn = null;
        
        try {
            // Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection(); 
            PreparedStatement insertUs = conn.prepareStatement(insertarUser);
            insertUs.setString(1, usuario_.getNombre());
            insertUs.setString(2, usuario_.getCorreo());
            insertUs.setString(3, usuario_.getContrasenya());
            insertUs.setString(4, usuario_.getTipo());
            
            // Ejecutamos la consulta
            ResultSet insertUC = insertUs.executeQuery();
            
            // Liberamos los recursos utilizados
            insertUC.close();
            insertUs.close();

        } catch(SQLException se) {
            se.printStackTrace();  
        
        } catch(Exception e) {
            e.printStackTrace(System.err); 
        } finally {
        	PoolConnectionManager.releaseConnection(conn);
        }

    }
}
