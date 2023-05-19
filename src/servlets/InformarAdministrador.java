package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.QuejasVO;
import model.UsuariosVO;
import model.QuejasDAO;

/*
 * Servlet encargado de enviar un informe del usuario solicitante a los administradores
 */

/**
 * Servlet implementation class InformarAdministrador
 */
@WebServlet("/InformarAdministrador")
public class InformarAdministrador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InformarAdministrador() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
				
		QuejasVO queja = new QuejasVO (
				0,
				usuario.getNombre(),
				request.getParameter("texto")
				);
		
		QuejasDAO dao = new QuejasDAO();
		
		dao.presentarQueja(queja);
		
		// Reenvía a la página principal de la sesión de usuario
		request.getRequestDispatcher("Publicaciones.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
