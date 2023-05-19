package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InicioDAO;
import model.EliminarCuentaDAO;
import model.UsuariosVO;

/*
 * Servlet encargado de, si la contraseña correspondiente es correcta, 
 * eliminar la cuenta del usuario solicitante
 */

/**
 * Servlet implementation class EliminarCuenta
 */
@WebServlet("/EliminarCuenta")
public class EliminarCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarCuenta() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		usuario.setContrasenya(request.getParameter("contrasenya"));
			
		InicioDAO daoI = new InicioDAO();
		// Si la contraseña es correcta procede a eliminar la cuenta
		if (daoI.iniciarSesion(usuario)) {
			EliminarCuentaDAO daoE = new EliminarCuentaDAO();
			daoE.eliminarUsuario(usuario);
			
			// Invalida la sesión actual y reenvía a la página de inicio de sesión
			request.getSession().invalidate();
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		// Si no, se notifica del error al usuario
		else {
			// Reenvía a la página anterior, es decir, a la correspondiente de 'eliminar usuario'
			// notificando el error por medio de un parametro
			request.getRequestDispatcher("eliminar.jsp?error=contra").forward(request, response);
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
