package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import db.PoolConnectionManager;

public class EliminarCuentaDAO {
	
	/**/
	private static String borrarUsuario = "DELETE FROM usuarios WHERE nombre=?";
	
	/**/
	public static void eliminarUsuario(UsuariosVO usuario_) {

		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement eliminarUA = conn.prepareStatement(borrarUsuario);
			eliminarUA.setString(1, usuario_.getNombre());
            
            // Ejecutamos la consulta
            eliminarUA.execute();
            
            // Liberamos los recursos utilizados
            eliminarUA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
	}
	
}
