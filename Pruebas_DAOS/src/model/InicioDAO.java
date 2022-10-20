package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

/*
 * Iniciar sesión   						OK
 * Registrarse: Validar usuario y correo	OK
 * Mostrar grupos
 * Registrarse: Crear cuenta (nuevo usuario y gruposUsuario, actualizar numParticipantes grupos)
 */
public class InicioDAO {

	private static String contarPorNombre = "SELECT count(*) cuentaN FROM usuarios WHERE nombre=?"; 
	private static String contarPorCorreo = "SELECT count(*) cuentaC FROM usuarios WHERE correo=?"; 
	private static String buscarPorNombre = "SELECT * FROM usuarios WHERE nombre=?";
	private static String insertarUsuario = "INSERT INTO usuarios VALUES (?, ?, ?, 'Normal')";
	private static String verGrupos = "SELECT * FROM grupos";
	private static String insertarGrupoUsuario = "INSERT INTO grupos_usuarios VALUES (?, ?)";
	private static String actualizarNumParticipantes = "UPDATE grupos SET num_participantes=num_participantes+1 WHERE nombre=?";

	/**/
	public static boolean existeNombre(UsuariosVO usuario_) {
		
		boolean retVal = false;
		Connection conn = null;
		
		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement contarNA = conn.prepareStatement(contarPorNombre);
            contarNA.setString(1, usuario_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet contarNB = contarNA.executeQuery();
            
            // Obtenemos resultados de la consulta
            contarNB.next();
            int n = contarNB.getInt(1);
            
            if (n == 1) retVal = true;
            
            // Liberamos los recursos utilizados
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
	
	/**/
	public static boolean existeCorreo(UsuariosVO usuario_) {
		
		boolean retVal = false;
		Connection conn = null;
		
		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement contarCA = conn.prepareStatement(contarPorCorreo);
            contarCA.setString(1, usuario_.getCorreo());
            
            // Ejecutamos la consulta
            ResultSet contarCB = contarCA.executeQuery();
            
            // Obtenemos resultados de la consulta
            contarCB.next();
            int n = contarCB.getInt(1);
            
            if (n == 1) retVal = true;
            
            // Liberamos los recursos utilizados
            contarCB.close();
            contarCA.close();
            
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
	public static boolean iniciarSesion(UsuariosVO usuario_) {
		
		boolean retVal = false;
		Connection conn = null;
		
		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement contarNA = conn.prepareStatement(contarPorNombre);
            PreparedStatement buscarNA = conn.prepareStatement(buscarPorNombre);
            contarNA.setString(1, usuario_.getNombre());
            buscarNA.setString(1, usuario_.getNombre());
            
            // Ejecutamos la consulta
            ResultSet contarNB = contarNA.executeQuery();
            ResultSet buscarNB = buscarNA.executeQuery();
            
            // Obtenemos resultados de la consulta
            contarNB.next();
            int n = contarNB.getInt(1);
            
            if (n == 1) {
            	buscarNB.next();
            	String passw = buscarNB.getString("contrasenya");
            	
            	if (passw.contentEquals(usuario_.getContrasenya())) {
            		retVal = true;
            	}
            }
            
            // Liberamos los recursos utilizados
            contarNB.close();
            buscarNB.close();
            contarNA.close();
            buscarNA.close();
            
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
	public static ArrayList<GruposVO> mostrarGrupos() {
		
		ArrayList<GruposVO> retVal = new ArrayList<GruposVO>();
		Connection conn = null;
		
		try {
			// Abrimos la conexión e inicializamos los parámetros 
            conn = PoolConnectionManager.getConnection();
            PreparedStatement darListA = conn.prepareStatement(verGrupos);
            
            // Ejecutamos la consulta
            ResultSet darListB = darListA.executeQuery();
            
            // Obtenemos resultados de la consulta
            while (darListB.next()) {
            	GruposVO auxVal = new GruposVO(darListB.getString("nombre"), darListB.getInt("num_participantes"));
            	retVal.add(auxVal);
            }
            
            // Liberamos los recursos utilizados
            darListB.close();
            darListA.close();
            
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
	public static void crearCuenta(UsuariosVO usuario_, ArrayList<GruposVO> grupos_) {
		
		Connection conn = null;
		
		try {
			// Abrimos la conexión 
            conn = PoolConnectionManager.getConnection();
            // Inicializamos los parámetros de insertarUsuario
            PreparedStatement insertarUA = conn.prepareStatement(insertarUsuario);
            insertarUA.setString(1, usuario_.getNombre());
            insertarUA.setString(2, usuario_.getCorreo());
            insertarUA.setString(3, usuario_.getContrasenya());
            
            // Ejecutamos la consulta de insertarUsuario
            ResultSet insertarUB = insertarUA.executeQuery();
            
            // Liberamos los recursos utilizados de insertarUsuario
            insertarUB.close();
            insertarUA.close();
            
            for (int i=0; i<grupos_.size(); i++) {
            	
            	// Inicializamos los parámetros de insertarGrupoUsuario y actualizarNumParticipantes
            	PreparedStatement insertarGUA = conn.prepareStatement(insertarGrupoUsuario);
            	PreparedStatement actualizarGA = conn.prepareStatement(actualizarNumParticipantes);
            	insertarGUA.setString(1, usuario_.getNombre());
                insertarGUA.setString(2, grupos_.get(i).getNombre());
                actualizarGA.setString(1, grupos_.get(i).getNombre());
                
                // Ejecutamos la consulta de insertarGrupoUsuario y actualizarNumParticipantes
                insertarGUA.execute();
                actualizarGA.execute();
                
                // Liberamos los recursos utilizados de insertarGrupoUsuario y actualizarNumParticipantes
                insertarGUA.close();
                actualizarGA.close();
            }           
              
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
	}
}
