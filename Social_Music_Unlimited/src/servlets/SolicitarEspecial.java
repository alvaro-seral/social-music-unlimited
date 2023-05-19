package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsuariosVO;
import model.SolicitudesEspecialesDAO;

/*
 * Servlet encargado de enviar una solictud del usuario solicitante de conversión de cuenta a 'Especial' a los administradores
 */

/**
 * Servlet implementation class SolicitarEspecial
 */
@WebServlet("/SolicitarEspecial")
public class SolicitarEspecial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SolicitarEspecial() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		SolicitudesEspecialesDAO dao = new SolicitudesEspecialesDAO();
		dao.solicitarPrivilegios(usuario);
		
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
