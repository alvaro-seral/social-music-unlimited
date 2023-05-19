package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

// DAO que trata las consultas de la base de datos destinadas a 
// actualizar y obtener de las tablas toda la información relativa a los eventos y los apuntados
public class EventosDAO {

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
	private static String verEventosCronoAdmin = "SELECT * FROM eventos WHERE grupo=? ORDER BY id DESC";
	private static String verEventosPorApuntadosAdmin = "SELECT * FROM eventos WHERE grupo=? ORDER BY num_apuntados DESC";
	private static String insertarEvento = "INSERT INTO eventos(empresa, fecha_evento, lugar, descripcion, num_apuntados, grupo, nombre)"
    		+ "VALUES (?, ?, ?, ?, '0', ?, ?)";
    private static String verComentariosCronoE = "SELECT * FROM comentarios_event WHERE evento=? ORDER BY id DESC";
    private static String insertarComentarioE = "INSERT INTO comentarios_event(nombre, descripcion, evento) VALUES (?, ?, ?)";
    private static String insertarApuntado = "INSERT INTO apuntados VALUES (?, ?)";
    private static String sumarApuntado = "UPDATE eventos SET num_apuntados=num_apuntados+1 WHERE id=?";
    private static String eliminarApuntado = "DELETE FROM apuntados WHERE usuario=? AND evento=?";
    private static String restarApuntado = "UPDATE eventos SET num_apuntados=num_apuntados-1 WHERE id=?";
    private static String verApuntadosUsuario = "SELECT * FROM apuntados WHERE usuario=?";
    private static String seleccionarEvento = "SELECT * FROM eventos WHERE id=?";
    
	// Dado el identificador de un evento, devuelve el objeto eventos que contiene dicho identificador
    public EventosVO obtenerEventoSeleccionado(String id_) {

    	Connection conn = null;    	
    	EventosVO retVal = null;
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(seleccionarEvento, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            darEvA.setInt(1, new Integer(id_).intValue());
			
			ResultSet darEvB = darEvA.executeQuery();

			darEvB.next();
			retVal = new EventosVO(darEvB.getInt("id"), darEvB.getString("empresa"), darEvB.getDate("fecha_evento"), darEvB.getString("lugar"), 
					darEvB.getString("descripcion"), darEvB.getInt("num_apuntados"), darEvB.getString("grupo"), darEvB.getString("nombre"));
			
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
    
	// Dado un objeto usuarios, devuelve una lista de objetos apuntados que contiene los eventos 
	// en los que dicho usuario ha participado
    public ArrayList<ApuntadosVO> obtenerApuntadosUsuario (UsuariosVO usuario_) {
    	
    	ArrayList<ApuntadosVO> retVal = new ArrayList<ApuntadosVO>();
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verApuntadosUsuario);
			darEvA.setString(1, usuario_.getNombre());
            
            ResultSet darEvB = darEvA.executeQuery();
            
            while (darEvB.next()) {
            	ApuntadosVO auxVal = new ApuntadosVO(darEvB.getString("usuario"), darEvB.getInt("evento"));
            	retVal.add(auxVal);
            }
            
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
    
	// Dado un objeto usuarios y el identificador de un evento, realiza las acciones pertinentes 
	// en la base de datos para que dicho usuario quede apuntado a dicho evento
    public void apuntarseEvento(UsuariosVO usuario_, String evento_) {
    	
    	Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement introducirAA = conn.prepareStatement(insertarApuntado);
            PreparedStatement aumentarAA = conn.prepareStatement(sumarApuntado);
			introducirAA.setString(1, usuario_.getNombre());
			introducirAA.setInt(2, new Integer(evento_).intValue());
			aumentarAA.setInt(1, new Integer(evento_).intValue());
            
            introducirAA.execute();
            aumentarAA.execute();
            
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
    
	// Dado un objeto usuarios y el identificador de un evento, realiza las acciones pertinentes 
	// en la base de datos para que dicho usuario quede desapuntado de dicho evento
    public void desapuntarseEvento(UsuariosVO usuario_, String evento_) {
    	
    	Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement eliminarAA = conn.prepareStatement(eliminarApuntado);
            PreparedStatement restarAA = conn.prepareStatement(restarApuntado);
            eliminarAA.setString(1, usuario_.getNombre());
            eliminarAA.setInt(2, new Integer(evento_).intValue());
            restarAA.setInt(1, new Integer(evento_).intValue());
            
			eliminarAA.execute();
			restarAA.execute();
            
            eliminarAA.close();
            restarAA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
    }
    
    // Dado el nombre de un grupo, un objeto usuarios, un valor indice i y un valor contador c, 
	// se obtiene información de los c primeros eventos pertenecientes a dicho grupo y ordenados cronológicamente, 
	// contando desde la fila i. Devuelve una lista de booleanos de tamaño c, donde cada elemento 
	// vale 'true' si, por su respectivo evento, dicho usuario está apuntado
    public ArrayList<Boolean> obtenerApuntadosCrono(String grupo_, UsuariosVO usuario_, int startIndex, int count) {
    	
    	ArrayList<Boolean> retVal = new ArrayList<Boolean>();
    	Connection conn = null;
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verEventosCrono, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			darEvA.setString(1, grupo_);
			darEvA.setString(2, usuario_.getNombre());
			darEvA.setString(3, grupo_);
			darEvA.setString(4, usuario_.getNombre());
            
            ResultSet darEvB = darEvA.executeQuery();
            
            if ((startIndex >=1) && darEvB.absolute(startIndex)) {
            	int currentCount = 0;
            	do {
            		if (darEvB.getInt("apuntado") == 1) retVal.add(true);
                	else retVal.add(false);
            		currentCount++;            	
            	} while (darEvB.next() && (currentCount < count));
            }
            
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
    
    // Dado el nombre de un grupo, un objeto usuarios, un valor indice i y un valor contador c, 
	// se obtiene información de los c primeros eventos pertenecientes a dicho grupo y ordenados según el número de apuntados, 
	// contando desde la fila i. Devuelve una lista de booleanos de tamaño c, donde cada elemento 
	// vale 'true' si, por su respectivo evento, dicho usuario está apuntado
    public ArrayList<Boolean> obtenerApuntadosPorApuntados(String grupo_, UsuariosVO usuario_, int startIndex, int count) {
    	
    	ArrayList<Boolean> retVal = new ArrayList<Boolean>();
    	Connection conn = null;
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verEventosPorApuntados, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			darEvA.setString(1, grupo_);
			darEvA.setString(2, usuario_.getNombre());
			darEvA.setString(3, grupo_);
			darEvA.setString(4, usuario_.getNombre());
            
            ResultSet darEvB = darEvA.executeQuery();
            
            if ((startIndex >=1) && darEvB.absolute(startIndex)) {
            	int currentCount = 0;
            	do {
            		if (darEvB.getInt("apuntado") == 1) retVal.add(true);
                	else retVal.add(false);
            		currentCount++;            	
            	} while (darEvB.next() && (currentCount < count));
            }
            
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
    
	// Dado el nombre de un grupo, un objeto usuarios, un valor indice i y un valor contador c, 
	// se obtiene información de los c primeros eventos pertenecientes a dicho grupo y ordenados cronológicamente, 
	// contando desde la fila i. Devuelve una lista de objetos eventos de tamaño c, con los datos generales de los eventos obtenidos
	public ArrayList<EventosVO> obtenerEventosCrono(String grupo_, UsuariosVO usuario_, int startIndex, int count) {

		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
    	Connection conn = null;
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verEventosCrono, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			darEvA.setString(1, grupo_);
			darEvA.setString(2, usuario_.getNombre());
			darEvA.setString(3, grupo_);
			darEvA.setString(4, usuario_.getNombre());
            
            ResultSet darEvB = darEvA.executeQuery();
            
            if ((startIndex >=1) && darEvB.absolute(startIndex)) {
            	int currentCount = 0;
            	do {
            		EventosVO evento = new EventosVO(darEvB.getInt("id"), darEvB.getString("empresa"), darEvB.getDate("fecha_evento"), darEvB.getString("lugar"), 
    						darEvB.getString("descripcion"), darEvB.getInt("num_apuntados"), darEvB.getString("grupo"), darEvB.getString("nombre"));
            		retVal.add(evento);
            		currentCount++;            	
            	} while (darEvB.next() && (currentCount < count));
            }
            
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
	
	// Dado el nombre de un grupo, un objeto usuarios, un valor indice i y un valor contador c, 
	// se obtiene información de los c primeros eventos pertenecientes a dicho grupo y ordenados según el número de apuntados, 
	// contando desde la fila i. Devuelve una lista de objetos eventos de tamaño c, con los datos generales de los eventos obtenidos
	public ArrayList<EventosVO> obtenerEventosPorApuntados(String grupo_, UsuariosVO usuario_, int startIndex, int count) {

		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
    	Connection conn = null;
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verEventosPorApuntados, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			darEvA.setString(1, grupo_);
			darEvA.setString(2, usuario_.getNombre());
			darEvA.setString(3, grupo_);
			darEvA.setString(4, usuario_.getNombre());
            
            ResultSet darEvB = darEvA.executeQuery();
            
            if ((startIndex >=1) && darEvB.absolute(startIndex)) {
            	int currentCount = 0;
            	do {
            		EventosVO evento = new EventosVO(darEvB.getInt("id"), darEvB.getString("empresa"), darEvB.getDate("fecha_evento"), darEvB.getString("lugar"), 
    						darEvB.getString("descripcion"), darEvB.getInt("num_apuntados"), darEvB.getString("grupo"), darEvB.getString("nombre"));
            		retVal.add(evento);
            		currentCount++;            	
            	} while (darEvB.next() && (currentCount < count));
            }
            
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

	// Dado un objeto eventos, realiza las acciones pertinentes 
	// en la base de datos para que dicho evento quede registrado
	public void publicarEvento(EventosVO evento_) {

		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement pubEvA = conn.prepareStatement(insertarEvento);
			pubEvA.setString(1, evento_.getEmpresa());
			pubEvA.setDate(2, evento_.getFechaEvento());
			pubEvA.setString(3, evento_.getLugar());
			pubEvA.setString(4, evento_.getDescripcion());
			pubEvA.setString(5, evento_.getGrupo());
			pubEvA.setString(6, evento_.getNombre());
            
            pubEvA.execute();
            
            pubEvA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

	// Dado un objeto eventos, un valor indice i y un valor contador c, 
	// se obtiene información de los c primeros comentarios pertenecientes a dicho evento y ordenados cronológicamente, 
	// contando desde la fila i. Devuelve una lista de objetos comentarios_event de tamaño c, con los datos generales de los comentarios obtenidos
	public ArrayList<ComentariosEventVO> obtenerComentarios(EventosVO evento_, int startIndex, int count) {
    	
    	Connection conn = null;    	
    	ArrayList<ComentariosEventVO> retVal = new ArrayList<ComentariosEventVO>();
    	
    	try {
            conn = PoolConnectionManager.getConnection();
			
            PreparedStatement darComA = conn.prepareStatement(verComentariosCronoE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            darComA.setInt(1, evento_.getId());
			
			ResultSet darComB = darComA.executeQuery();
			
			if ((startIndex >=1) && darComB.absolute(startIndex)) {
				 int currentCount = 0;
				 do {
					 ComentariosEventVO comentario = new ComentariosEventVO(darComB.getInt("id"), darComB.getString("nombre"), darComB.getString("descripcion"), darComB.getInt("evento"));
					 retVal.add(comentario);
					 currentCount++;
				 } while (darComB.next() && (currentCount < count));
			}
			
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

	// Dado un objeto comentarios_event, realiza las acciones pertinentes 
	// en la base de datos para que dicho comentario de evento quede registrado
	public void publicarComentario(ComentariosEventVO comentario_) {

		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement pubComA = conn.prepareStatement(insertarComentarioE);
			pubComA.setString(1, comentario_.getNombre());
			pubComA.setString(2, comentario_.getDescripcion());
			pubComA.setInt(3, comentario_.getEvento());
            
            pubComA.execute();
            
            pubComA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
	} 
	
	// Dado el nombre de un grupo, un valor indice i y un valor contador c, 
	// se obtiene información de los c primeros eventos pertenecientes a dicho grupo y ordenados cronológicamente, 
	// contando desde la fila i. Devuelve una lista de objetos eventos de tamaño c, con los datos generales de los eventos obtenidos
	public ArrayList<EventosVO> obtenerEventosCronoAdmin(String grupo_, int startIndex, int count) {

		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
    	Connection conn = null;
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verEventosCronoAdmin, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			darEvA.setString(1, grupo_);
            
            ResultSet darEvB = darEvA.executeQuery();
            
            if ((startIndex >=1) && darEvB.absolute(startIndex)) {
            	int currentCount = 0;
            	do {
            		EventosVO evento = new EventosVO(darEvB.getInt("id"), darEvB.getString("empresa"), darEvB.getDate("fecha_evento"), darEvB.getString("lugar"), 
    						darEvB.getString("descripcion"), darEvB.getInt("num_apuntados"), darEvB.getString("grupo"), darEvB.getString("nombre"));
            		retVal.add(evento);
            		currentCount++;            	
            	} while (darEvB.next() && (currentCount < count));
            }
            
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
	
	// Dado el nombre de un grupo, un valor indice i y un valor contador c, 
	// se obtiene información de los c primeros eventos pertenecientes a dicho grupo y ordenados según el número de apuntados, 
	// contando desde la fila i. Devuelve una lista de objetos eventos de tamaño c, con los datos generales de los eventos obtenidos
	public ArrayList<EventosVO> obtenerEventosPorApuntadosAdmin(String grupo_, int startIndex, int count) {

		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
    	Connection conn = null;
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verEventosPorApuntadosAdmin, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			darEvA.setString(1, grupo_);
            
            ResultSet darEvB = darEvA.executeQuery();
            
            if ((startIndex >=1) && darEvB.absolute(startIndex)) {
            	int currentCount = 0;
            	do {
            		EventosVO evento = new EventosVO(darEvB.getInt("id"), darEvB.getString("empresa"), darEvB.getDate("fecha_evento"), darEvB.getString("lugar"), 
    						darEvB.getString("descripcion"), darEvB.getInt("num_apuntados"), darEvB.getString("grupo"), darEvB.getString("nombre"));
            		retVal.add(evento);
            		currentCount++;            	
            	} while (darEvB.next() && (currentCount < count));
            }
            
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
    
}
