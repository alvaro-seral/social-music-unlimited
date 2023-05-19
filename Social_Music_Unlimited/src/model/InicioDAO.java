package model;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.PoolConnectionManager;

// DAO que trata las consultas de la base de datos destinadas a 
// actualizar y obtener de las tablas toda la información relativa al registro e inicio de sesión de los usuarios, 
// al igual que otros datos referentes a la tabla usuarios
public class InicioDAO {

	private static String contarPorNombre = "SELECT count(*) cuentaN FROM usuarios WHERE nombre=?"; 
	private static String contarPorCorreo = "SELECT count(*) cuentaC FROM usuarios WHERE correo=?"; 
	private static String buscarPorNombre = "SELECT * FROM usuarios WHERE nombre=?";
	private static String insertarUsuario = "INSERT INTO usuarios VALUES (?, ?, ?, 'Normal')";
	private static String verGrupos = "SELECT * FROM grupos";
	private static String insertarGrupoUsuario = "INSERT INTO grupos_usuarios VALUES (?, ?)";
	private static String actualizarNumParticipantes = "UPDATE grupos SET num_participantes=num_participantes+1 WHERE nombre=?";
	private static String gruposNoAdmin = "SELECT grupo FROM grupos_usuarios WHERE usuario=?";
	private static String conseguirTipo = "SELECT tipo FROM usuarios WHERE nombre=?";
	private static String numParticipantes = "SELECT COUNT(nombre) FROM usuarios";

	// Devuelve el número de usuarios totales registrados en la base de datos
	public int participantesTotales() {
		
		int retVal = 0;
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement contarNA = conn.prepareStatement(numParticipantes);
            
            ResultSet contarNB = contarNA.executeQuery();
            
            contarNB.next();
            retVal = contarNB.getInt(1);
            
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
	
	// Dado un objeto usuarios, devuelve 'true' si el nombre de dicho usuario está registrado en la base de datos
	public boolean existeNombre(UsuariosVO usuario_) {
		
		boolean retVal = false;
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement contarNA = conn.prepareStatement(contarPorNombre);
            contarNA.setString(1, usuario_.getNombre());
            
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
	
	// Dado un objeto usuarios, devuelve 'true' si el correo de dicho usuario está registrado en la base de datos
	public boolean existeCorreo(UsuariosVO usuario_) {
		
		boolean retVal = false;
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement contarCA = conn.prepareStatement(contarPorCorreo);
            contarCA.setString(1, usuario_.getCorreo());
            
            ResultSet contarCB = contarCA.executeQuery();
            
            contarCB.next();
            int n = contarCB.getInt(1);
            
            if (n == 1) retVal = true;
            
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
	
	// Dado un objeto usuarios, devuelve 'true' si dicho usuario está registrado en la base de datos 
	// y, si es así, su contraseña es la misma que la almacenada
	public boolean iniciarSesion(UsuariosVO usuario_) {
		
		boolean retVal = false;
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement contarNA = conn.prepareStatement(contarPorNombre);
            PreparedStatement buscarNA = conn.prepareStatement(buscarPorNombre);
            contarNA.setString(1, usuario_.getNombre());
            buscarNA.setString(1, usuario_.getNombre());
            
            ResultSet contarNB = contarNA.executeQuery();
            ResultSet buscarNB = buscarNA.executeQuery();
            
            contarNB.next();
            int n = contarNB.getInt(1);
            
            if (n == 1) {
            	buscarNB.next();
            	String passw = buscarNB.getString("contrasenya");
            	
            	if (passw.contentEquals(usuario_.getContrasenya())) {
            		retVal = true;
            	}
            }
            
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
	
	// Devuelve una lista de objetos grupos con todos la información general de todos 
	// los grupos existemtes ordenada por defecto
	public ArrayList<GruposVO> mostrarGrupos() {
		
		ArrayList<GruposVO> retVal = new ArrayList<GruposVO>();
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darListA = conn.prepareStatement(verGrupos);
            
            ResultSet darListB = darListA.executeQuery();
            
            while (darListB.next()) {
            	GruposVO auxVal = new GruposVO(darListB.getString("nombre"), darListB.getInt("num_participantes"));
            	retVal.add(auxVal);
            }
            
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
	
	// Dados un objeto usuarios y una lista de objetos grupos, se realizan las acciones pertinentes
	// en la base de datos para añadir dicho usuario, de forma que quede apuntado en dichos grupos
	public void crearCuenta(UsuariosVO usuario_, ArrayList<GruposVO> grupos_) {
		
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

			// Añadir al usuario a la base de datos
            PreparedStatement insertarUA = conn.prepareStatement(insertarUsuario);
            insertarUA.setString(1, usuario_.getNombre());
            insertarUA.setString(2, usuario_.getCorreo());
            insertarUA.setString(3, usuario_.getContrasenya());
            
            insertarUA.execute();
            
            insertarUA.close();
            
			// Apuntar al usuario en cada uno de los grupos correspondientes
            for (int i=0; i<grupos_.size(); i++) {
            	
            	PreparedStatement insertarGUA = conn.prepareStatement(insertarGrupoUsuario);
            	PreparedStatement actualizarGA = conn.prepareStatement(actualizarNumParticipantes);
            	insertarGUA.setString(1, usuario_.getNombre());
                insertarGUA.setString(2, grupos_.get(i).getNombre());
                actualizarGA.setString(1, grupos_.get(i).getNombre());
                
                insertarGUA.execute();
                actualizarGA.execute();
                
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
	
	// Dado un objeto usuarios, se obtiene información únicamente de los grupos en los que 
	// está apuntado dicho usuario ordenada por defecto. Devuelve una lista de objetos grupos, 
	// con los datos generales de los grupos obtenidos
	public ArrayList<String> mostrarGruposUsuario(UsuariosVO usuario_) {
		
		ArrayList<String> retVal = new ArrayList<String>();
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement primerGrupoA = conn.prepareStatement(gruposNoAdmin);
            primerGrupoA.setString(1, usuario_.getNombre());
            
            ResultSet primerGrupoB = primerGrupoA.executeQuery();
            while (primerGrupoB.next()) {
            	retVal.add(primerGrupoB.getString("grupo"));
            }            
            
            primerGrupoB.close();
            primerGrupoA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
		return retVal;
	}
	
	// Dado un objeto usuarios, devuelve el tipo ('Normal', 'Especial' o 'Administrador') correspondiente a dicho usuario
	// 
	public String esTipo(UsuariosVO usuario_) {
		
		String retVal = "";
		Connection conn = null;
		
		try {
	        conn = PoolConnectionManager.getConnection();

	        PreparedStatement tipoA = conn.prepareStatement(conseguirTipo);
	        tipoA.setString(1, usuario_.getNombre());
	        
	        ResultSet tipoB = tipoA.executeQuery();
	        
	        tipoB.next();
	        retVal = tipoB.getString(1);
	        
	        tipoB.close();
	        tipoA.close();
	        
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


