package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

// DAO que trata las consultas de la base de datos destinadas a 
// actualizar y obtener de las tablas toda la información relativa a los informes realizados 
// por los usuarios a los administradores
public class QuejasDAO {

	private static String verQuejas = "SELECT * FROM quejas ORDER BY id ASC";
    private static String borrarQueja = "DELETE FROM quejas WHERE id=?";
	private static String insertarQueja = "INSERT INTO quejas(nombre, descripcion) VALUES (?, ?)";

	// Devuelve una lista de objetos quejas con toda la información general de cada queja o informe existente
	public ArrayList<QuejasVO> obtenerQuejas() {

		ArrayList<QuejasVO> retVal = new ArrayList<QuejasVO>();
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darQA = conn.prepareStatement(verQuejas);
            
            ResultSet darQB = darQA.executeQuery();
            
            while (darQB.next()) {
            	QuejasVO auxVal = new QuejasVO(darQB.getInt("id"), darQB.getString("nombre"), darQB.getString("descripcion"));
            	retVal.add(auxVal);
            }
            
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

	// Dado un objeto quejas, realiza las acciones pertinentes 
	// en la base de datos para que dicha queja quede registrada
	public void presentarQueja(QuejasVO queja_) {

		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement introducirQA = conn.prepareStatement(insertarQueja);
			introducirQA.setString(1, queja_.getNombre());
			introducirQA.setString(2, queja_.getDescripcion());
            
            introducirQA.execute();
            
            introducirQA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

	// Dado el identificador de una queja, elimina de la base de datos la queja correspondiente 
	// con dicho identificador 
	public void eliminarQueja(String queja_) {
		
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement eliminarQA = conn.prepareStatement(borrarQueja);
			eliminarQA.setInt(1, new Integer(queja_).intValue());
            
            eliminarQA.execute();
            
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
