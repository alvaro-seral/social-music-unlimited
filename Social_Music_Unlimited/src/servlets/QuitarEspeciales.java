package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SolicitudesEspecialesDAO;

/*
 * Servlet encargado de quitar los privilegios de usuario especial al usuario recibido como parametro
 */

/**
 * Servlet implementation class QuitarEspeciales
 */
@WebServlet("/QuitarEspeciales")
public class QuitarEspeciales extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuitarEspeciales() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombre = request.getParameter("userEspToNorm");
		
		SolicitudesEspecialesDAO dao = new SolicitudesEspecialesDAO();
		
		boolean existe = dao.existeUsuarioEspecial(nombre);
		
		// Si el usuario recibido como parametro existe y su tipo es 'Especial', se le quitan sus privilegios
		if (existe) {
			dao.quitarPrivilegios(nombre);
			
			// Reenvía a la página de 'Estadisticas de grupo' del administrador
			request.getRequestDispatcher("AdminPorGrupos.jsp").forward(request, response);
		}
		// Si no, se notifica del error al administrador
		else {
			// Reenvía a la página anterior, es decir, a la correspondiente de 'gestionar especiales'
			// notificando el error por medio de un parametro
			request.getRequestDispatcher("GestionarEspeciales.jsp?error=especial").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
