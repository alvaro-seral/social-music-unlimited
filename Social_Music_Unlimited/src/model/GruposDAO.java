package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.PoolConnectionManager;

// DAO que trata las consultas de la base de datos destinadas a 
// actualizar y obtener de las tablas toda la información relativa a los grupos
public class GruposDAO {
	
	private static String verGruposTodos = "(SELECT grupos.*, '1' as apuntado FROM grupos, grupos_usuarios"
			+ " WHERE grupos_usuarios.grupo=grupos.nombre AND grupos_usuarios.usuario=?)"
			+ " UNION ALL"
			+ " (SELECT grupos.*, '0' as apuntado FROM grupos WHERE"
			+ " grupos.nombre NOT IN (SELECT grupo FROM grupos_usuarios WHERE grupos_usuarios.usuario=?))"
			+ " ORDER BY nombre";
	private static String verGruposUsuario = "SELECT * FROM grupos_usuarios WHERE grupos_usuarios.usuario=? ORDER BY grupo";
	private static String eliminarGruposUsuario = "DELETE FROM grupos_usuarios WHERE usuario=?";
	private static String insertarGrupoUsuario = "INSERT INTO grupos_usuarios VALUES (?, ?)";
	private static String sumarNumParticipantes = "UPDATE grupos SET num_participantes=num_participantes+1 WHERE nombre=?";
	private static String restarNumParticipantes = "UPDATE grupos SET num_participantes=num_participantes-1 WHERE nombre=?";
	private static String seleccionarGrupoConcreto = "SELECT * FROM grupos WHERE nombre=?";
	
	// Dado el nombre de un grupo, devuelve el objeto grupos que contiene dicho nombre
	public GruposVO seleccionarGrupo(String grupo_) {
		
		GruposVO retVal = null;
		Connection conn = null;
		
		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(seleccionarGrupoConcreto);
			darEvA.setString(1, grupo_);
            
            ResultSet darEvB = darEvA.executeQuery();
            
            darEvB.next();
            retVal = new GruposVO(darEvB.getString("nombre"), darEvB.getInt("num_participantes"));
            
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
	
	// Dado un objeto usuarios, se obtiene información de todos los grupos ordenada por defecto.
	// Devuelve una lista de booleanos, donde cada elemento vale 'true' si, por su respectivo grupo, 
	// dicho usuario está apuntado
	public ArrayList<Boolean> obtenerApuntados(UsuariosVO usuario_) {

		ArrayList<Boolean> retVal = new ArrayList<Boolean>();
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verGruposTodos);
			darEvA.setString(1, usuario_.getNombre());
			darEvA.setString(2, usuario_.getNombre());
            
            ResultSet darEvB = darEvA.executeQuery();
            
            boolean auxVal;
            while (darEvB.next()) {
            	if (darEvB.getInt("apuntado") == 1) auxVal = true;
            	else auxVal = false;
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
	
	// Dado un objeto usuarios, se obtiene información de todos los grupos ordenada por defecto.
	// Devuelve una lista de objetos grupos, con los datos generales de los grupos obtenidos
	public ArrayList<GruposVO> obtenerGrupos(UsuariosVO usuario_) {

		ArrayList<GruposVO> retVal = new ArrayList<GruposVO>();
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verGruposTodos);
			darEvA.setString(1, usuario_.getNombre());
			darEvA.setString(2, usuario_.getNombre());
            
            ResultSet darEvB = darEvA.executeQuery();
            
            while (darEvB.next()) {
            	GruposVO auxVal = new GruposVO(darEvB.getString("nombre"), darEvB.getInt("num_participantes"));
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
	
	// Dado un objeto usuarios, se obtiene información únicamente de los grupos en los que 
	// está apuntado dicho usuario ordenada por defecto. Devuelve una lista de objetos grupos, 
	// con los datos generales de los grupos obtenidos
	public ArrayList<GruposUsuariosVO> obtenerGruposUsuario(UsuariosVO usuario_) {

		ArrayList<GruposUsuariosVO> retVal = new ArrayList<GruposUsuariosVO>();
		Connection conn = null;

		try {
            conn = PoolConnectionManager.getConnection();

            PreparedStatement darEvA = conn.prepareStatement(verGruposUsuario);
			darEvA.setString(1, usuario_.getNombre());
            
            ResultSet darEvB = darEvA.executeQuery();
            
            while (darEvB.next()) {
            	GruposUsuariosVO auxVal = new GruposUsuariosVO(darEvB.getString("usuario"), darEvB.getString("grupo"));
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
	
	// Dado un objeto usuarios y una lista de objetos grupos, realiza las acciones pertinentes 
	// en la base de datos para que dicho usuario quede apuntado exclusivamente a dichos grupos
	public void actualizarGrupos (UsuariosVO usuario_, ArrayList<GruposVO> grupos_) {
		
		Connection conn = null;

		try {
			ArrayList<GruposUsuariosVO> GU = obtenerGruposUsuario(usuario_);
			
            conn = PoolConnectionManager.getConnection();
            
			// Restar 1 a los números de participantes de cada grupo en los que el usuario está apuntado actualmente
            for (GruposUsuariosVO i:GU) {
            	PreparedStatement restarA = conn.prepareStatement(restarNumParticipantes);
    			restarA.setString(1, i.getGrupo());
    			restarA.execute();
    			restarA.close();
            }
            
			// Eliminar los registros donde aparecen las relaciones de los grupos en los que el usuario está apuntado actualmente 
            PreparedStatement eliminarA = conn.prepareStatement(eliminarGruposUsuario);
            eliminarA.setString(1, usuario_.getNombre());
            eliminarA.execute();
            eliminarA.close();
            
			// Sumar 1 a los número de participantes de los nuevos grupos en los que el usuario quiere estar apuntado
			// y añadir los registros donde aparezcan las relaciones del usuario con divhos nuevos grupos
            for (int i=0; i<grupos_.size(); i++) {
            PreparedStatement insertarA = conn.prepareStatement(insertarGrupoUsuario);
            PreparedStatement actualizarA = conn.prepareStatement(sumarNumParticipantes);
        	insertarA.setString(1, usuario_.getNombre());
            insertarA.setString(2, grupos_.get(i).getNombre());
            actualizarA.setString(1, grupos_.get(i).getNombre());
                
            insertarA.execute();
            actualizarA.execute();
                        
            insertarA.close();
            actualizarA.close();
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
