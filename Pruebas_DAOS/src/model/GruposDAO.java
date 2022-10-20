package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.PoolConnectionManager;

public class GruposDAO {
	
	private static String verGruposTodos = "(SELECT grupos.*, '1' as apuntado FROM grupos, grupos_usuarios"
			+ " WHERE grupos_usuarios.grupo=grupos.nombre AND grupos_usuarios.usuario=?)"
			+ " UNION ALL"
			+ " (SELECT grupos.*, '0' as apuntado FROM grupos WHERE"
			+ " grupos.nombre NOT IN (SELECT grupo FROM grupos_usuarios WHERE grupos_usuarios.usuario=?))"
			+ " ORDER BY nombre";
	private static String verGruposUsuario = "SELECT * FROM grupos_usuarios WHERE grupos_usuarios.usuario=? ORDER BY grupo";
	private static String entrarGrupo = "INSERT INTO grupos_usuarios VALUES (?, ?)";
	private static String salirGrupo = "DELETE FROM grupos_usuarios WHERE usuario=? AND grupo=?";
	
	/**/
	public static ArrayList<Boolean> obtenerApuntados(UsuariosVO usuario_) {

		ArrayList<Boolean> retVal = new ArrayList<Boolean>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(verGruposTodos);
			darEvA.setString(1, usuario_.getNombre());
			darEvA.setString(2, usuario_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEvB = darEvA.executeQuery();
            
            // Obtenemos resultados de la consulta
            boolean auxVal;
            while (darEvB.next()) {
            	if (darEvB.getInt("apuntado") == 1) auxVal = true;
            	else auxVal = false;
            	retVal.add(auxVal);
            }
            
            // Liberamos los recursos utilizados
            darEvB.close();
            darEvA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
		return retVal;

	}
	
	/**/
	public static ArrayList<GruposVO> obtenerGrupos(UsuariosVO usuario_) {

		ArrayList<GruposVO> retVal = new ArrayList<GruposVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(verGruposTodos);
			darEvA.setString(1, usuario_.getNombre());
			darEvA.setString(2, usuario_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEvB = darEvA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darEvB.next()) {
            	GruposVO auxVal = new GruposVO(darEvB.getString("nombre"), darEvB.getInt("num_participantes"));
            	retVal.add(auxVal);
            }
            
            // Liberamos los recursos utilizados
            darEvB.close();
            darEvA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
		return retVal;

	}
	

	/**/
	public static ArrayList<GruposUsuariosVO> obtenerGruposUsuario(UsuariosVO usuario_) {

		ArrayList<GruposUsuariosVO> retVal = new ArrayList<GruposUsuariosVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(verGruposUsuario);
			darEvA.setString(1, usuario_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEvB = darEvA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darEvB.next()) {
            	GruposUsuariosVO auxVal = new GruposUsuariosVO(darEvB.getString("usuario"), darEvB.getString("grupo"));
            	retVal.add(auxVal);
            }
            
            // Liberamos los recursos utilizados
            darEvB.close();
            darEvA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
		return retVal;

	}
	
	public static void entrarAGrupo(GruposUsuariosVO grupoUsuario_) {
		
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement introducirQA = conn.prepareStatement(entrarGrupo);
			introducirQA.setString(1, grupoUsuario_.getUsuario());
			introducirQA.setString(2, grupoUsuario_.getGrupo());
            
            // Ejecutamos la consulta
            introducirQA.execute();
            
            // Liberamos los recursos utilizados
            introducirQA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}	
	
	public static void salirDeGrupo(GruposUsuariosVO grupoUsuario_) {
		
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement eliminarQA = conn.prepareStatement(salirGrupo);
			eliminarQA.setString(1, grupoUsuario_.getUsuario());
			eliminarQA.setString(2, grupoUsuario_.getGrupo());
            
            // Ejecutamos la consulta
            eliminarQA.execute();
            
            // Liberamos los recursos utilizados
            eliminarQA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}
	
}
