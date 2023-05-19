package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.QuejasDAO;
import model.QuejasVO;

/*
 * Servlet encargado de cargar los datos necesarios para mostrar la página con la lista de informes existentes
 */

/**
 * Servlet implementation class Informes
 */
@WebServlet("/Informes")
public class Informes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Informes() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Establece el atributo de sesión 'quejas' incluyendo todos los informes existentes 
		QuejasDAO dao = new QuejasDAO();
		ArrayList<QuejasVO> quejas = dao.obtenerQuejas();
		request.getSession().setAttribute("quejas", quejas);
		
		// Reenvía a la página con la lista de informes
		request.getRequestDispatcher("GestionarInformes.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
