package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

public class PublicacionesDAO {

	/**/
	private static String verPublicacionesCrono = "SELECT * FROM publicaciones WHERE grupo=? ORDER BY id DESC"; 
    private static String insertarPublicacion = "INSERT INTO publicaciones(nombre, descripcion, grupo) VALUES (?, ?, ?)";
	private static String verComentariosCronoP = "SELECT * FROM comentarios_publi WHERE publicacion=? ORDER BY id DESC";
    private static String insertarComentarioP = "INSERT INTO comentarios_publi(nombre, descripcion, publicacion) VALUES (?, ?, ?)";

	/**/
	public static ArrayList<PublicacionesVO> obtenerPublicacionesCrono(GruposVO grupo_) {

		ArrayList<PublicacionesVO> retVal = new ArrayList<PublicacionesVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darPubA = conn.prepareStatement(verPublicacionesCrono);
			darPubA.setString(1, grupo_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet darPubB = darPubA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darPubB.next()) {
            	PublicacionesVO auxVal = new PublicacionesVO(darPubB.getInt("id"), darPubB.getString("nombre"), darPubB.getString("descripcion"), darPubB.getString("grupo"));
            	retVal.add(auxVal);
            }
            
            // Liberamos los recursos utilizados
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

	/**/
	public static void publicarPublicacion(PublicacionesVO publicacion_) {

		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement pubPubA = conn.prepareStatement(insertarPublicacion);
			pubPubA.setString(1, publicacion_.getNombre());
			pubPubA.setString(2, publicacion_.getDescripcion());
			pubPubA.setString(3, publicacion_.getGrupo());
            
            // Ejecutamos la consulta
            pubPubA.execute();
            
            // Liberamos los recursos utilizados
            pubPubA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

	/**/
	public static ArrayList<ComentariosPubliVO> obtenerComentarios(PublicacionesVO publicacion_) {

		ArrayList<ComentariosPubliVO> retVal = new ArrayList<ComentariosPubliVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darComA = conn.prepareStatement(verComentariosCronoP);
			darComA.setInt(1, publicacion_.getId());
            
            // Ejecutamos la consulta
            ResultSet darComB = darComA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darComB.next()) {
            	ComentariosPubliVO auxVal = new ComentariosPubliVO(darComB.getInt("id"), darComB.getString("nombre"), darComB.getString("descripcion"), darComB.getInt("publicacion"));
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
	public static void publicarComentario(ComentariosPubliVO comentario_) {

		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement pubComA = conn.prepareStatement(insertarComentarioP);
			pubComA.setString(1, comentario_.getNombre());
			pubComA.setString(2, comentario_.getDescripcion());
			pubComA.setInt(3, comentario_.getPublicacion());
            
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
