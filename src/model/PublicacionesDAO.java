package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

// DAO que trata las consultas de la base de datos destinadas a 
// actualizar y obtener de las tablas toda la información relativa a las publicaciones
public class PublicacionesDAO {

	private static String verPublicacionesCrono = "SELECT * FROM publicaciones WHERE grupo=? ORDER BY id DESC"; 
    private static String insertarPublicacion = "INSERT INTO publicaciones(nombre, descripcion, grupo) VALUES (?, ?, ?)";
	private static String verComentariosCronoP = "SELECT * FROM comentarios_publi WHERE publicacion=? ORDER BY id DESC";
    private static String insertarComentarioP = "INSERT INTO comentarios_publi(nombre, descripcion, publicacion) VALUES (?, ?, ?)";
    private static String seleccionarPublicacion = "SELECT * FROM publicaciones WHERE id=?";

	// Dado el identificador de una publicación, devuelve el objeto publicaciones que contiene dicho identificador
    public PublicacionesVO obtenerPublicacionSeleccionada(String id_) {
    	Connection conn = null;    	
    	PublicacionesVO retVal = null;
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darPubA = conn.prepareStatement(seleccionarPublicacion, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			darPubA.setInt(1, new Integer(id_).intValue());
			
			ResultSet darPubB = darPubA.executeQuery();
			darPubB.next();
			retVal = new PublicacionesVO(darPubB.getInt("id"), darPubB.getString("nombre"), darPubB.getString("descripcion"), darPubB.getString("grupo"));
			
			darPubB.close();
			darPubA.close();
            
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
	// se obtiene información de las c primeros publicaciones pertenecientes a dicho grupo y ordenadas cronológicamente, 
	// contando desde la fila i. Devuelve una lista de objetos publicaciones de tamaño c, con los datos generales de las publicaciones obtenidas
    public ArrayList<PublicacionesVO> obtenerPublicacionesCrono(String grupo_, int startIndex, int count) {
    	
    	Connection conn = null;    	
    	ArrayList<PublicacionesVO> retVal = new ArrayList<PublicacionesVO>();
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darPubA = conn.prepareStatement(verPublicacionesCrono, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			darPubA.setString(1, grupo_);
			
			ResultSet darPubB = darPubA.executeQuery();
			
			if ((startIndex >=1) && darPubB.absolute(startIndex)) {
				 int currentCount = 0;
				 do {
					 PublicacionesVO publicacion = new PublicacionesVO(darPubB.getInt("id"), darPubB.getString("nombre"), darPubB.getString("descripcion"), darPubB.getString("grupo"));
					 retVal.add(publicacion);
					 currentCount++;
				 } while (darPubB.next() && (currentCount < count));
			 }
			
			darPubB.close();
			darPubA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
		return retVal;
    }

	// Dado un objeto publicaciones, realiza las acciones pertinentes 
	// en la base de datos para que dicha publicación quede registrada
	public void publicarPublicacion(PublicacionesVO publicacion_) {

		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement pubPubA = conn.prepareStatement(insertarPublicacion);
			pubPubA.setString(1, publicacion_.getNombre());
			pubPubA.setString(2, publicacion_.getDescripcion());
			pubPubA.setString(3, publicacion_.getGrupo());
            
            pubPubA.execute();
            
            pubPubA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

	// Dado un objeto publicaciones, un valor indice i y un valor contador c, 
	// se obtiene información de los c primeros comentarios pertenecientes a dicha publicación y ordenados cronológicamente, 
	// contando desde la fila i. Devuelve una lista de objetos comentarios_publi de tamaño c, con los datos generales de los comentarios obtenidos
	public ArrayList<ComentariosPubliVO> obtenerComentarios(PublicacionesVO publicacion_, int startIndex, int count) {
    	
    	Connection conn = null;    	
    	ArrayList<ComentariosPubliVO> retVal = new ArrayList<ComentariosPubliVO>();
    	
    	try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darComA = conn.prepareStatement(verComentariosCronoP, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            darComA.setInt(1, publicacion_.getId());
			
			ResultSet darComB = darComA.executeQuery();
			
			if ((startIndex >=1) && darComB.absolute(startIndex)) {
				 int currentCount = 0;
				 do {
					 ComentariosPubliVO comentario = new ComentariosPubliVO(darComB.getInt("id"), darComB.getString("nombre"), darComB.getString("descripcion"), darComB.getInt("publicacion"));
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

	// Dado un objeto comentarios_publi, realiza las acciones pertinentes 
	// en la base de datos para que dicho comentario de publicación quede registrado
	public void publicarComentario(ComentariosPubliVO comentario_) {

		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement pubComA = conn.prepareStatement(insertarComentarioP);
			pubComA.setString(1, comentario_.getNombre());
			pubComA.setString(2, comentario_.getDescripcion());
			pubComA.setInt(3, comentario_.getPublicacion());
            
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

}
