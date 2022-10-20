package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

public class AdministradoresDAO {
	
	private static String todosEventosCrono = "SELECT * FROM eventos WHERE grupo=? ORDER BY id DESC";
	private static String todosEventosPorApuntados = "SELECT * FROM eventos WHERE grupo=? ORDER BY num_apuntados DESC";
	private static String totalParticipantes = "SELECT * FROM grupos WHERE nombre=?";
	private static String totalUsuarios = "SELECT count(*) as total FROM usuarios";
	private static String mejorEventoSiempre = "SELECT * FROM eventos WHERE grupo=? AND num_apuntados=(SELECT MAX(num_apuntados) FROM eventos WHERE grupo=? AND)";
	private static String mejorEventoAnyo = "SELECT * FROM eventos WHERE grupo=? AND date_trunc('year', fecha_evento)=date_trunc('year', CURRENT_DATE) AND"
			+ " num_apuntados=(SELECT MAX(num_apuntados) FROM eventos WHERE grupo=? AND date_trunc('year', fecha_evento)=date_trunc('year', CURRENT_DATE))";
	private static String mejorEventoMes = "SELECT * FROM eventos WHERE grupo=? AND date_trunc('year', fecha_evento)=date_trunc('year', CURRENT_DATE) AND"
			+ " date_trunc('month', fecha_evento)=date_trunc('month', CURRENT_DATE) AND num_apuntados=(SELECT MAX(num_apuntados) FROM eventos "
			+ " WHERE grupo=? AND date_trunc('year', fecha_evento)=date_trunc('year', CURRENT_DATE) AND date_trunc('month', fecha_evento)=date_trunc('month', CURRENT_DATE))";
	
	/**/
	public static ArrayList<EventosVO> verEventosCrono(GruposVO grupo_, UsuariosVO usuario_) {

		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(todosEventosCrono);
			darEvA.setString(1, grupo_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEvB = darEvA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darEvB.next()) {
            	EventosVO auxVal = new EventosVO(darEvB.getInt("id"), darEvB.getString("empresa"), darEvB.getDate("fecha_evento"), darEvB.getString("lugar"), 
            						darEvB.getString("descripcion"), darEvB.getInt("num_apuntados"), darEvB.getString("grupo"), darEvB.getString("nombre"));
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
	public static ArrayList<EventosVO> verEventosPorApuntados(GruposVO grupo_, UsuariosVO usuario_) {

		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(todosEventosPorApuntados);
			darEvA.setString(1, grupo_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEvB = darEvA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darEvB.next()) {
            	EventosVO auxVal = new EventosVO(darEvB.getInt("id"), darEvB.getString("empresa"), darEvB.getDate("fecha_evento"), darEvB.getString("lugar"), 
            						darEvB.getString("descripcion"), darEvB.getInt("num_apuntados"), darEvB.getString("grupo"), darEvB.getString("nombre"));
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
	public static int verTotalUsuarios() {
		
		int retVal = 0;
		Connection conn = null;
		
		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEA = conn.prepareStatement(totalUsuarios);
            
            // Ejecutamos la consulta
            ResultSet darEB = darEA.executeQuery();
            
            // Obtenemos resultados de la consulta
            darEB.next();
            retVal = darEB.getInt("total");
            		
            // Liberamos los recursos utilizados
            darEB.close();
            darEA.close();
            
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
	public static int verTotalParticipantes(GruposVO grupo_) {
		
		int retVal = 0;
		Connection conn = null;
		
		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEA = conn.prepareStatement(totalParticipantes);
            darEA.setString(1, grupo_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEB = darEA.executeQuery();
            
            // Obtenemos resultados de la consulta
            darEB.next();
            retVal = darEB.getInt("num_participantes");
            		
            // Liberamos los recursos utilizados
            darEB.close();
            darEA.close();
            
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
	public static EventosVO verMejorEventoSiempre(GruposVO grupo_) {
		
		EventosVO retVal = null;
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEA = conn.prepareStatement(mejorEventoSiempre);
            darEA.setString(1, grupo_.getNombre());
            darEA.setString(2, grupo_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEB = darEA.executeQuery();
            
            // Obtenemos resultados de la consulta
            darEB.next();
            retVal = new EventosVO(darEB.getInt("id"), darEB.getString("empresa"), darEB.getDate("fecha_evento"), darEB.getString("lugar"), 
            		darEB.getString("descripcion"), darEB.getInt("num_apuntados"), darEB.getString("grupo"), darEB.getString("nombre"));

            // Liberamos los recursos utilizados
            darEB.close();
            darEA.close();
            
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
	public static EventosVO verMejorEventoAnyo(GruposVO grupo_) {
		
		EventosVO retVal = null;
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEA = conn.prepareStatement(mejorEventoAnyo);
            darEA.setString(1, grupo_.getNombre());
            darEA.setString(2, grupo_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEB = darEA.executeQuery();
            
            // Obtenemos resultados de la consulta
            darEB.next();
            retVal = new EventosVO(darEB.getInt("id"), darEB.getString("empresa"), darEB.getDate("fecha_evento"), darEB.getString("lugar"), 
            		darEB.getString("descripcion"), darEB.getInt("num_apuntados"), darEB.getString("grupo"), darEB.getString("nombre"));

            // Liberamos los recursos utilizados
            darEB.close();
            darEA.close();
            
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
	public static EventosVO verMejorEventoMes(GruposVO grupo_) {
		
		EventosVO retVal = null;
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEA = conn.prepareStatement(mejorEventoMes);
            darEA.setString(1, grupo_.getNombre());
            darEA.setString(2, grupo_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEB = darEA.executeQuery();
            
            // Obtenemos resultados de la consulta
            darEB.next();
            retVal = new EventosVO(darEB.getInt("id"), darEB.getString("empresa"), darEB.getDate("fecha_evento"), darEB.getString("lugar"), 
            		darEB.getString("descripcion"), darEB.getInt("num_apuntados"), darEB.getString("grupo"), darEB.getString("nombre"));

            // Liberamos los recursos utilizados
            darEB.close();
            darEA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
		return retVal;
		
	}
	
	
	
}
