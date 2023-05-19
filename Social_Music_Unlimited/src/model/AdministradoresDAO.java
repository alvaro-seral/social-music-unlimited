package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;
 
// DAO que trata las consultas de la base de datos destinadas a 
// obtener datos estadísticos para los administradores
public class AdministradoresDAO {
	
	private static String mejorEventoSiempre = "SELECT * FROM eventos WHERE grupo=? AND num_apuntados=(SELECT MAX(num_apuntados) FROM eventos WHERE grupo=?)";
	private static String mejorEventoAnyo = "SELECT * FROM eventos WHERE grupo=? AND date_trunc('year', fecha_evento)=date_trunc('year', CURRENT_DATE) AND"
			+ " num_apuntados=(SELECT MAX(num_apuntados) FROM eventos WHERE grupo=? AND date_trunc('year', fecha_evento)=date_trunc('year', CURRENT_DATE))";
	
	// Dado el nombre de un grupo, devuelve la lista de eventos donde más personas apuntadas 
	// han sido registradas desde siempre
	public ArrayList<EventosVO> verMejorEventoSiempre(String grupo_) {
		
		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEA = conn.prepareStatement(mejorEventoSiempre);
            darEA.setString(1, grupo_);
            darEA.setString(2, grupo_);
            
            ResultSet darEB = darEA.executeQuery();
            
            while (darEB.next()) {
            	retVal.add(new EventosVO(darEB.getInt("id"), darEB.getString("empresa"), darEB.getDate("fecha_evento"), darEB.getString("lugar"), 
                		darEB.getString("descripcion"), darEB.getInt("num_apuntados"), darEB.getString("grupo"), darEB.getString("nombre")));
            }

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
	
	// Dado el nombre de un grupo, devuelve la lista de eventos donde más personas apuntadas 
	// han sido registradas en los eventos celebrados / por celebrar durante el año actual
	public ArrayList<EventosVO> verMejorEventoAnyo(String grupo_) {
		
		ArrayList<EventosVO> retVal = new ArrayList<EventosVO>();
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEA = conn.prepareStatement(mejorEventoAnyo);
            darEA.setString(1, grupo_);
            darEA.setString(2, grupo_);
            
            ResultSet darEB = darEA.executeQuery();
            
            while (darEB.next()) {
            	retVal.add(new EventosVO(darEB.getInt("id"), darEB.getString("empresa"), darEB.getDate("fecha_evento"), darEB.getString("lugar"), 
                		darEB.getString("descripcion"), darEB.getInt("num_apuntados"), darEB.getString("grupo"), darEB.getString("nombre")));
            }

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
