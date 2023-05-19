package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InicioDAO;

/*
 * Servlet encargado de cargar en los atributos de sesión los datos necesarios para mostrar la página de 'Estadisticas de grupo' del administrador
 */

/**
 * Servlet implementation class MostrarDeGrupo
 */
@WebServlet("/MostrarDeGrupo")
public class MostrarDeGrupo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarDeGrupo() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InicioDAO dao = new InicioDAO();
		// Establecer el atributo de sesión 'participantesTotales'
		request.getSession().setAttribute("participantesTotales", dao.participantesTotales());
		
		// Reenvía a la página de 'Estadisticas de grupo' del administrador
		request.getRequestDispatcher("AdminPorGrupos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
