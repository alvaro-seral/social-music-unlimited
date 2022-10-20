package db;

import java.sql.*;

public class ConnectionManager {
	// JDBC nombred el driver y URL de BD 
	private static final String JDBC_DRIVER = "org.postgresql.Driver";  
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=social_music";
	
	// Credenciales de la Base de Datos
	private static final String USER = "usuario";
	private static final String PASS = "sisinf2022";
	
	// Devuelve una nueva conexion.
	public final static Connection getConnection() throws SQLException {
		Connection conn = null;

		try{
			//STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);
			//STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			return conn; 
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		} 
	}
	
	// Libera la conexion, devolviendola al pool 
	public final static void releaseConnection(Connection conn) throws SQLException {
		conn.close(); 
	}
}


