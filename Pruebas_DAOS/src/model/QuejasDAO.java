package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

public class QuejasDAO {

	/**/
	private static String verQuejas = "SELECT * FROM quejas ORDER BY id ASC";
    private static String borrarQueja = "DELETE FROM quejas WHERE id=?";
	private static String insertarQueja = "INSERT INTO quejas(nombre, descripcion) VALUES (?, ?)";

	/**/
	public static ArrayList<QuejasVO> obtenerQuejas() {

		ArrayList<QuejasVO> retVal = new ArrayList<QuejasVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darQA = conn.prepareStatement(verQuejas);
            
            // Ejecutamos la consulta
            ResultSet darQB = darQA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darQB.next()) {
            	QuejasVO auxVal = new QuejasVO(darQB.getInt("id"), darQB.getString("nombre"), darQB.getString("descripcion"));
            	retVal.add(auxVal);
            }
            
            // Liberamos los recursos utilizados
            darQB.close();
            darQA.close();
            
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
	public static void presentarQueja(QuejasVO queja_) {

		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement introducirQA = conn.prepareStatement(insertarQueja);
			introducirQA.setString(1, queja_.getNombre());
			introducirQA.setString(2, queja_.getDescripcion());
            
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

	/**/
	public static void eliminarQueja(QuejasVO queja_) {
		
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement eliminarQA = conn.prepareStatement(borrarQueja);
			eliminarQA.setInt(1, queja_.getId());
            
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
