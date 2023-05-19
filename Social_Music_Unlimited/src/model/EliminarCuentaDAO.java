package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.PoolConnectionManager;

// DAO que trata las consultas de la base de datos destinadas a 
// realizar las acciones relativas a eliminar una cuenta de Usuario (Normal o Especial)
public class EliminarCuentaDAO {
	
	private static String borrarUsuario = "DELETE FROM usuarios WHERE nombre=?";
	private static String restarNumParticipantes = "UPDATE grupos SET num_participantes=num_participantes-1 WHERE nombre=?";
	private static String restarNumApuntados = "UPDATE eventos SET num_apuntados=num_apuntados-1 WHERE id=?";
	
	
	// Dado un objeto usuarios, elimina de la base de datos todo registro de información 
	// relacionado con dicho usuario
	public void eliminarUsuario(UsuariosVO usuario_) {

		Connection conn = null;

		try { 
            conn = PoolConnectionManager.getConnection();
            
			// Restar 1 al numero de participantes de los grupos a los que pertenecía el usuario:
            GruposDAO grupos = new GruposDAO();
    		ArrayList<GruposUsuariosVO> GU = grupos.obtenerGruposUsuario(usuario_);
            
            for (GruposUsuariosVO i:GU) {
            	PreparedStatement restarA = conn.prepareStatement(restarNumParticipantes);
    			restarA.setString(1, i.getGrupo());
    			restarA.execute();
    			restarA.close();
            }
            
			// Restar 1 al numero de apuntados de los eventos en los que el usuario había participado:
            EventosDAO eventos = new EventosDAO();
            ArrayList<ApuntadosVO> Apuntados = eventos.obtenerApuntadosUsuario(usuario_);
            
            for (ApuntadosVO i:Apuntados) {
            	PreparedStatement restarA = conn.prepareStatement(restarNumApuntados);
    			restarA.setInt(1, i.getEvento());
    			restarA.execute();
    			restarA.close();
            }
            
			// Eliminar todas las entradas donde aparece expresamente el usuario:
            PreparedStatement eliminarUA = conn.prepareStatement(borrarUsuario);
			eliminarUA.setString(1, usuario_.getNombre());
            
            eliminarUA.execute();
            
            eliminarUA.close();
            
		} catch(SQLException se) {
			se.printStackTrace();  
		
		} catch(Exception e) {
			e.printStackTrace(System.err);
		} finally {
			PoolConnectionManager.releaseConnection(conn); 
		}
		
	}
	
}
