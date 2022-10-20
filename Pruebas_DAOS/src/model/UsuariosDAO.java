package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.ConnectionManager;

/*
 * FICHERO DE PRUEBA
 */

public class UsuariosDAO {
	
	private static String insertarUser = "INSERT INTO usuarios VALUES (?,?,?,?)";
	
	public static void insertarUsuario(UsuariosVO usuario_) { 
        
        Connection conn = null;
        
        try {
            // Abrimos la conexión e inicializamos los parámetros 
            conn = ConnectionManager.getConnection(); 
            PreparedStatement insertUs = conn.prepareStatement(insertarUser);
            insertUs.setString(1, usuario_.getNombre());
            insertUs.setString(2, usuario_.getCorreo());
            insertUs.setString(3, usuario_.getContrasenya());
            insertUs.setString(4, usuario_.getTipo());
            
            // Ejecutamos la consulta
            insertUs.execute();
            
            // Liberamos los recursos utilizados
            insertUs.close();
            
            ConnectionManager.releaseConnection(conn);

        } catch(SQLException se) {
            se.printStackTrace();  
        
        } catch(Exception e) {
            e.printStackTrace(System.err); 
        }
        
    }
}
