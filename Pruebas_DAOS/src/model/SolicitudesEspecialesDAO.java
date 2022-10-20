package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

public class SolicitudesEspecialesDAO {

	/**/
	private static String contarPorNombre = "SELECT count(*) cuentaN FROM usuarios WHERE nombre=?";
	private static String buscarPorNombre = "SELECT * FROM usuarios WHERE nombre=?";
	private static String verSolicitudes = "SELECT * FROM solicitudes";
    private static String borrarSolicitud = "DELETE FROM solicitudes WHERE nombre=?";
    private static String ascenderTipo = "UPDATE usuarios SET tipo='Especial' WHERE nombre=?";
    private static String descenderTipo = "UPDATE usuarios SET tipo='Normal' WHERE nombre=?";
	private static String insertarSolicitud = "INSERT INTO solicitudes VALUES (?)";

	/**/
	public static boolean quitarPrivilegios(UsuariosVO usuario_) {

		boolean retVal = false;
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement contarUA = conn.prepareStatement(contarPorNombre);
			PreparedStatement buscarUA = conn.prepareStatement(buscarPorNombre);
			PreparedStatement descUA = conn.prepareStatement(descenderTipo);
            contarUA.setString(1, usuario_.getNombre());
			buscarUA.setString(1, usuario_.getNombre());
			descUA.setString(1, usuario_.getNombre());

            
            // Ejecutamos la consulta
            ResultSet contarUB = contarUA.executeQuery();
			ResultSet buscarUB = buscarUA.executeQuery();
            
            // Obtenemos resultados de la consulta
            contarUB.next();
            int n = contarUB.getInt(1);
            
            if (n == 1) {
				buscarUB.next();
				String rol = buscarUB.getString("tipo");

				if (rol.contentEquals("Especial")) {
					descUA.execute();
					retVal = true;
				}
			}
            
            // Liberamos los recursos utilizados
			descUA.close();
            buscarUB.close();
            buscarUA.close();
			contarUB.close();
            contarUA.close();
            
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
	public static boolean darPrivilegios(UsuariosVO usuario_) {

		boolean retVal = false;
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
			PreparedStatement ascUA = conn.prepareStatement(ascenderTipo);
			ascUA.setString(1, usuario_.getNombre());

            
            // Ejecutamos la consulta
            ascUA.execute();
            
            // Liberamos los recursos utilizados
			ascUA.close();
            
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
	public void negarSolicitud(UsuariosVO usuario_) {

		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement negarSA = conn.prepareStatement(borrarSolicitud);
			negarSA.setString(1, usuario_.getNombre());
            
            // Ejecutamos la consulta
            negarSA.execute();
            
            // Liberamos los recursos utilizados
            negarSA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

	/**/
	public ArrayList<SolicitudesVO> obtenerSolicitudes() {

		ArrayList<SolicitudesVO> retVal = new ArrayList<SolicitudesVO>();
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darSA = conn.prepareStatement(verSolicitudes);
            
            // Ejecutamos la consulta
            ResultSet darSB = darSA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darSB.next()) {
            	SolicitudesVO auxVal = new SolicitudesVO(darSB.getString("nombre"));
            	retVal.add(auxVal);
            }
            
            // Liberamos los recursos utilizados
            darSB.close();
            darSA.close();
            
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
	public void solicitarPrivilegios(UsuariosVO usuario_) {
		
		Connection conn = null;

		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement solicitarPA = conn.prepareStatement(insertarSolicitud);
			solicitarPA.setString(1, usuario_.getNombre());
            
            // Ejecutamos la consulta
            solicitarPA.execute();
            
            // Liberamos los recursos utilizados
            solicitarPA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

}
