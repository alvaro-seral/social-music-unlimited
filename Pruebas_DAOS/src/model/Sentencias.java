package model;

/*
 * Validar users
 * Registrar/Insertar users     
 * comprobar existencia usuario
 * Cambiar tipo
 * Eliminar users
 */

public class Sentencias {
	
/*	
	public class InicioSesion {
		private static String contarPorNombre = "SELECT count(*) cuentaN FROM usuarios WHERE nombre = ?"; //=1
		private static String buscarPorNombre = "SELECT * FROM usuarios WHERE nombre = ?";
	}
	
	public class Registro {
		private static String contarPorNombre = "SELECT count(*) cuentaN FROM usuarios WHERE nombre = ?"; //=0
		private static String contarPorCorreo = "SELECT count(*) cuentaC FROM usuarios WHERE correo = ?"; //=0
		private static String insertarUsuario = "INSERT INTO usuarios VALUES (?, ?, ?, 'Normal')";
	}
	
	public class SobreSolicitud {
		private static String verSolicitudes = "SELECT * FROM solicitudes";
		private static String borrarSolicitud = "DELETE FROM solicitudes WHERE nombre = ?";
		private static String actualizarTipo = "UPDATE usuarios SET tipo = 'Especial' WHERE nombre = ?";
		private static String insertarSolicitud = "INSERT INTO solicitudes VALUES (?)";
	}
	
	public class EliminarCuenta {
		private static String borrarUsuario = "DELETE FROM usuarios WHERE nombre = ?";
	}
	
	public class SobreQuejas {
		private static String verQuejas = "SELECT * FROM quejas";
		private static String borrarQueja = "DELETE FROM quejas WHERE id = ?";
		private static String insertarQueja = "INSERT INTO quejas VALUES (?, ?, ?)"; // id hay que meterlo? Por lo que segun la bbdd aumenta solo. Si no
		private static String insertarQueja2 = "INSERT INTO quejas (nombre, descripcion) VALUES (?, ?)";
	}
	
	public class SobrePublicaciones {
		private static String verPublicacionesCrono = "SELECT * FROM publicaciones ORDER BY id DESC"; 
		private static String insertarPublicacion = "INSERT INTO publicaciones VALUES (?, ?, ?, ?)"; //=comentario insertar queja
	}
	
	public class SobreEventos {
		private static String verEventosCrono = "SELECT * FROM eventos ORDER BY id DESC";
		private static String verEventosApuntados = "SELECT * FROM eventos ORDER BY num_apuntados DESC";
		private static String insertarEventos = "INSERT INTO eventos VALUES (?, ?, ?, ?, ?, 0, 0, 0)"; //=comentario insertar queja
	}
	
	public class SobreComentatiosP {
		private static String verComentariosCronoP = "SELECT * FROM publicaciones ORDER BY id DESC";
		private static String insertarComentarioP = "INSERT INTO comentariosPubli VALUES (?, ?, ?, ?)"; //=comentario insertar queja
	}
	
	public class SobreGrupos {
		private static String verGrupos = "SELECT * FROM grupos";
		private static String verGruposUsuarios = "SELECT * FROM grupos_usuarios WHERE usuario = ?";
		private static String entrarGrupo = "INSERT INTO grupos_usuarios VALUES (?, ?)";
		private static String salirGrupo = "DELETE FROM grupos_usuarios WHERE usuario = ? AND grupo = ?";
	}
*/
	
	
	(SELECT id, empresa, fecha_evento, lugar, descripcion, numApuntados, grupo, nombre, '1' as apuntado FROM eventos, apuntados
	WHERE apuntados.evento = eventos.id AND eventos.grupo=? AND apuntados.usuario=?
	)
	UNION ALL
	(SELECT id, empresa, fecha_evento, lugar, descripcion, numApuntados, grupo, nombre, '0' as apuntado FROM eventos
	WHERE AND eventos.grupo=? eventos.id NOT IN (SELECT evento FROM apuntados
			WHERE apuntados.eventos=? AND apuntados.usuario=?)
	)
	ORDER BY id DESC;
}



