package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

public class EventosDAO {

	/**/
	private static String verEventosCrono = "(SELECT eventos.*, '1' as apuntado FROM eventos, apuntados"
			+ " WHERE apuntados.evento=eventos.id AND eventos.grupo=? AND apuntados.usuario=?)"
			+ " UNION ALL"
			+ " (SELECT eventos.*, '0' as apuntado FROM eventos WHERE eventos.grupo=? AND"
			+ " eventos.id NOT IN (SELECT evento FROM apuntados WHERE apuntados.usuario=?))"
			+ " ORDER BY id DESC"; 
	private static String verEventosPorApuntados = "(SELECT eventos.*, '1' as apuntado FROM eventos, apuntados"
			+ " WHERE apuntados.evento=eventos.id AND eventos.grupo=? AND apuntados.usuario=?)"
			+ " UNION ALL"
			+ " (SELECT eventos.*, '0' as apuntado FROM eventos WHERE eventos.grupo=? AND"
			+ " eventos.id NOT IN (SELECT evento FROM apuntados WHERE apuntados.usuario=?))"
			+ " ORDER BY num_apuntados DESC"; 
	private static String insertarEvento= "INSERT INTO evento(empresa, fechaEvento, lugar, descripcion, numApuntados, grupo, nombre) "
    		+ "VALUES (?, ?, ?, ?, '0', ?, ?)";
    private static String verComentariosCronoE = "SELECT * FROM comentarios_event WHERE evento=? ORDER BY id DESC";
    private static String insertarComentarioE = "INSERT INTO comentarios_event(nombre, descripcion, evento) VALUES (?, ?, ?)";
    private static String insertarApuntado = "INSERT INTO apuntados VALUES (?, ?)";
    private static String aumentarAumentado = "UPDATE eventos SET num_apuntados=num_apuntados+1 WHERE id=?";
    
    public static void apuntarseEvento(ApuntadosVO apuntado_) {
    	
    	Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement introducirAA = conn.prepareStatement(insertarApuntado);
            PreparedStatement aumentarAA = conn.prepareStatement(aumentarAumentado);
			introducirAA.setString(1, apuntado_.getUsuario());
			introducirAA.setInt(2, apuntado_.getEvento());
			aumentarAA.setInt(1, apuntado_.getEvento());
            
            // Ejecutamos la consulta
            introducirAA.execute();
            aumentarAA.execute();
            
            // Liberamos los recursos utilizados
            introducirAA.close();
            aumentarAA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
    }
    
    /**/
    public static ArrayList<Boolean> obtenerApuntadosCrono(GruposVO grupo_, UsuariosVO usuario_) {
    	
    	ArrayList<Boolean> retVal = new ArrayList<Boolean>();
    	Connection conn = null;
    	
    	try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(verEventosCrono);
			darEvA.setString(1, grupo_.getNombre());
			darEvA.setString(2, usuario_.getNombre());
			darEvA.setString(3, grupo_.getNombre());
			darEvA.setString(4, usuario_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darEvB = darEvA.executeQuery();
            
            // Obtenemos resultados de la consulta
            Boolean auxVal;
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
    public static ArrayList<Boolean> obtenerApuntadosPorApuntados(GruposVO grupo_, UsuariosVO usuario_) {
    	
    	ArrayList<Boolean> retVal = new ArrayList<Boolean>();
    	Connection conn = null;
    	
    	try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(verEventosPorApuntados);
			darEvA.setString(1, grupo_.getNombre());
			darEvA.setString(2, usuario_.getNombre());
			darEvA.setString(3, grupo_.getNombre());
			darEvA.setString(4, usuario_.getNombre());
            
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
	public static ArrayList<EventosVO> obtenerEventosCrono(GruposVO grupo_, UsuariosVO usuario_) {

		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(verEventosCrono);
			darEvA.setString(1, grupo_.getNombre());
			darEvA.setString(2, usuario_.getNombre());
			darEvA.setString(3, grupo_.getNombre());
			darEvA.setString(4, usuario_.getNombre());
            
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
	public static ArrayList<EventosVO> obtenerEventosPorApuntados(GruposVO grupo_, UsuariosVO usuario_) {

		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darEvA = conn.prepareStatement(verEventosPorApuntados);
			darEvA.setString(1, grupo_.getNombre());
			darEvA.setString(2, usuario_.getNombre());
			darEvA.setString(3, grupo_.getNombre());
			darEvA.setString(4, usuario_.getNombre());
            
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
	public static void publicarEvento(EventosVO evento_) {

		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement pubEvA = conn.prepareStatement(insertarEvento);
			pubEvA.setString(1, evento_.getEmpresa());
			pubEvA.setDate(2, evento_.getFechaEvento());
			pubEvA.setString(3, evento_.getLugar());
			pubEvA.setString(4, evento_.getDescripcion());
			pubEvA.setString(5, evento_.getGrupo());
			pubEvA.setString(6, evento_.getNombre());
            
            // Ejecutamos la consulta
            pubEvA.execute();
            
            // Liberamos los recursos utilizados
            pubEvA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

	/**/
	public static ArrayList<ComentariosEventVO> obtenerComentarios(EventosVO evento_) {

		ArrayList<ComentariosEventVO> retVal = new ArrayList<ComentariosEventVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darComA = conn.prepareStatement(verComentariosCronoE);
			darComA.setInt(1, evento_.getId());
            
            // Ejecutamos la consulta
            ResultSet darComB = darComA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darComB.next()) {
            	ComentariosEventVO auxVal = new ComentariosEventVO(darComB.getInt("id"), darComB.getString("nombre"), darComB.getString("descripcion"), darComB.getInt("evento"));
            	retVal.add(auxVal);
            }
            
            // Liberamos los recursos utilizados
            darComB.close();
            darComA.close();
            
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
	public static void publicarComentario(ComentariosEventVO comentario_) {

		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement pubComA = conn.prepareStatement(insertarComentarioE);
			pubComA.setString(1, comentario_.getNombre());
			pubComA.setString(2, comentario_.getDescripcion());
			pubComA.setInt(3, comentario_.getEvento());
            
            // Ejecutamos la consulta
            pubComA.execute();
            
            // Liberamos los recursos utilizados
            pubComA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	} 
    
}
