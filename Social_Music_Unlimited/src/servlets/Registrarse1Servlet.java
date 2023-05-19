package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsuariosVO;
import model.InicioDAO;

/*
 * Servlet encargado de, durante la acción de registro, comprobar que el nombre de usuario y el correo 
 * no estén siendo utilizados actualmente
 */

/**
 * Servlet implementation class IniciarSesion
 */
@WebServlet("/Registrarse1Servlet")
public class Registrarse1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registrarse1Servlet() {
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
		// Si el nombre ya está registrado, se notifica del error al usuario
		// Reenvía a la página anterior, es decir, a la correspondiente de 'registrarse 1'
		// notificando el error por medio de un parametro
		if (dao.existeNombre(usuario)) request.getRequestDispatcher("signin1.jsp?error=user").forward(request, response);
		// Si el correo ya está registrado, se notifica del error al usuario
		// Reenvía a la página anterior, es decir, a la correspondiente de 'registrarse 1'
		// notificando el error por medio de un parametro
		else if (dao.existeCorreo(usuario)) request.getRequestDispatcher("signin1.jsp?error=mail").forward(request, response);
		// Si no, se establce el atributo de sesión 'usuario' y se accede al siguiente paso de registro
		else {
			request.getSession().setAttribute("usuario", usuario);

			// Reenvía a la página correspondiente al segundo paso de la acción de registro
			request.getRequestDispatcher("signin2.jsp").forward(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
