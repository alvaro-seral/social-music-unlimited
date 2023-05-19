package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsuariosVO;
import model.GruposVO;
import model.InicioDAO;
import model.GruposDAO;
import java.util.ArrayList;

/*
 * Servlet encargado de comprobar la correlación de un usuario con su contraseña y, en caso de ser correctos, 
 * iniciar sesión reenviando a distintas páginas dependiendo del tipo del usuario solicitante
 */

/**
 * Servlet implementation class IniciarSesion
 */
@WebServlet("/IniciarSesionServlet")
public class InicarSesionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InicarSesionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UsuariosVO usuario = new UsuariosVO(
			request.getParameter("nombre"),
			request.getParameter("correo"),
			request.getParameter("contrasenya"),
			request.getParameter("tipo"));
		
		InicioDAO dao = new InicioDAO();
		boolean existe = dao.existeNombre(usuario);
		// Si el nombre de usuario existe, se continua
		if (existe) {
			boolean valido = dao.iniciarSesion(usuario);
			// Si la contraseña es correcta, se continua
			if (valido) {
				// Se elimina la contraseña del objeto usuario, de modo que al guardarse en la sesión 
				// la información de dicho usuario no se pueda acceder a la contraseña. Es una medida de seguridad
				usuario.setContrasenya(null);
				usuario.setTipo(dao.esTipo(usuario));
				// Se establece el atributo de sesión 'usuario'
				request.getSession().setAttribute("usuario", usuario);
				
				// Se establece el atributo de sesión 'nomGrupos', que incluye todos los grupos existentes
				GruposDAO daoG = new GruposDAO();
				ArrayList<GruposVO> grupos = daoG.obtenerGrupos(usuario);
				ArrayList<String> nomGrupos = new ArrayList<String>();
				for (int i=0; i<grupos.size(); i++) { 
					nomGrupos.add(grupos.get(i).getNombre());
				}
				request.getSession().setAttribute("nomGrupos", nomGrupos);
				
				// Si el tipo de usuario es 'Administrador', se establece el atributo de sesión 'grupoActual' como GruposVO
				if (usuario.getTipo().equals("Administrador")) {
					GruposVO grupoActual = grupos.get(0);
					request.getSession().setAttribute("grupoActual", grupoActual);
					
					request.getRequestDispatcher("MostrarDeGrupo").forward(request, response);
				}
				// Si no, se establecen los atributos de sesión 'grupoActual' como el nombre del grupo (String),
				// 'gruposUsuario', que incluye todos los grupos a los que el usuario pertenece,
				// y 'boolGrupos', que incluye una lista con tantos elementos como grupos existen, 
				// y donde cada elemento vale 'true' si el usuario en cuestión está apuntado al grupo referente
				else {
					ArrayList<Boolean> boolGrupos = daoG.obtenerApuntados(usuario);
					request.getSession().setAttribute("boolGrupos", boolGrupos);
					
					ArrayList<String> gruposUsuario = dao.mostrarGruposUsuario(usuario);
					String grupoActual = gruposUsuario.get(0);
					request.getSession().setAttribute("gruposUsuario", gruposUsuario);
					request.getSession().setAttribute("grupoActual", grupoActual);
					
					// Reenvía al servlet correspondiente de cargar los datos necesarios para mostrar la página principal de la sesión de usuario
					request.getRequestDispatcher("MostrarPublicaciones").forward(request, response);
				}
			}
			// Si la contraseña es incorrecta, se notifica del error al usuario
			else {
				// Reenvía a la página anterior, es decir, a la correspondiente de 'inicar sesión'
				// notificando el error por medio de un parametro
				request.getRequestDispatcher("login.jsp?error=contra").forward(request, response);
			}
		}
		// Si el nombre de usuario no existe, se notifica del error al usuario
		else {
			// Reenvía a la página anterior, es decir, a la correspondiente de 'inicar sesión'
			// notificando el error por medio de un parametro
			request.getRequestDispatcher("login.jsp?error=user").forward(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
