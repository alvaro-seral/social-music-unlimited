package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

// DAO que trata las consultas de la base de datos destinadas a 
// actualizar y obtener de las tablas toda la información relativa a las solicitudes
// de conversión de cuenta a especial realizados por los usuarios normales a los administradores
public class SolicitudesEspecialesDAO {

	private static String estaEnLista = "SELECT count(*) cuentaN FROM solicitudes WHERE nombre=?";
	private static String verSolicitudes = "SELECT * FROM solicitudes";
    private static String borrarSolicitud = "DELETE FROM solicitudes WHERE nombre=?";
    private static String ascenderTipo = "UPDATE usuarios SET tipo='Especial' WHERE nombre=?";
    private static String descenderTipo = "UPDATE usuarios SET tipo='Normal' WHERE nombre=?";
	private static String insertarSolicitud = "INSERT INTO solicitudes VALUES (?)";
	private static String contarPorNombreYEspecial = "SELECT count(*) cuentaN FROM usuarios WHERE nombre=? AND tipo='Especial'"; 

	// Dado el nombre de un usuario, devuelve 'true' si dicho usuario existe y su tipo de cuenta es 'Especial'
	public boolean existeUsuarioEspecial(String nombre_) {
		
		boolean retVal = false;
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement contarNA = conn.prepareStatement(contarPorNombreYEspecial);
            contarNA.setString(1, nombre_);
            
            ResultSet contarNB = contarNA.executeQuery();
            
            contarNB.next();
            int n = contarNB.getInt(1);
            
            if (n == 1) retVal = true;
            
            contarNB.close();
            contarNA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err); 
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
		return retVal;
	}

	// Dado el nombre de un usuario, convierte el tipo de dicho usuario a 'Especial'.
	// Ademñas, elimina la solicitud de dicho usuario de la base de datos
	public void darPrivilegios(String usuario_) {

		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

			PreparedStatement ascUA = conn.prepareStatement(ascenderTipo);
			PreparedStatement borrarSA = conn.prepareStatement(borrarSolicitud);
			ascUA.setString(1, usuario_);
			borrarSA.setString(1, usuario_);

            ascUA.execute();
            borrarSA.execute();
            
			ascUA.close();
			borrarSA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err); 
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}
	
	// Dado el nombre de un usuario, convierte el tipo de dicho usuario a 'Normal'
	public void quitarPrivilegios(String usuario_) {

		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

			PreparedStatement ascUA = conn.prepareStatement(descenderTipo);
			ascUA.setString(1, usuario_);

            ascUA.execute();
            
			ascUA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err); 
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

	// Dado el nombre de un usuario, elimina la solicitud de dicho usuario de la base de datos
	public void negarSolicitud(String usuario_) {

		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement negarSA = conn.prepareStatement(borrarSolicitud);
			negarSA.setString(1, usuario_);
            
            negarSA.execute();
            
            negarSA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

	// Devuelve una lista de objetos solicitudes con toda la información general de cada solicitud existente
	public ArrayList<SolicitudesVO> obtenerSolicitudes() {

		ArrayList<SolicitudesVO> retVal = new ArrayList<SolicitudesVO>();
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darSA = conn.prepareStatement(verSolicitudes);
            
            ResultSet darSB = darSA.executeQuery();
            
            while (darSB.next()) {
            	SolicitudesVO auxVal = new SolicitudesVO(darSB.getString("nombre"));
            	retVal.add(auxVal);
            }
            
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

	// Dado un objeto usuarios, realiza las acciones pertinentes 
	// en la base de datos para registrar una solicitud de dicho usuario
	// En caso de estar dicha solicitud previamente incluida en la base de datos,
	// no se registrará la nueva solicitud
	public void solicitarPrivilegios(UsuariosVO usuario_) {
		
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement contarPA = conn.prepareStatement(estaEnLista);
            contarPA.setString(1, usuario_.getNombre());
            
            ResultSet contarPB = contarPA.executeQuery();
            
            contarPB.next();
            int cuenta = contarPB.getInt(1);
            
            if (cuenta == 0) {
            	PreparedStatement solicitarPA = conn.prepareStatement(insertarSolicitud);
				solicitarPA.setString(1, usuario_.getNombre());
	            
	            solicitarPA.execute();
	            
	            solicitarPA.close();
            }
            
            contarPB.close();
            contarPA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}

	}

}
