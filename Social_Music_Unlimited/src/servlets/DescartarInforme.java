package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.QuejasDAO;

/*
 * Servlet encargado de eliminar el informe seleccionado por el administrador
 */

/**
 * Servlet implementation class DescartarInforme
 */
@WebServlet("/DescartarInforme")
public class DescartarInforme extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DescartarInforme() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// Eliminar informe
		String idQueja = request.getParameter("idQueja");
		QuejasDAO daoQ = new QuejasDAO();
		daoQ.eliminarQueja(idQueja);
		
		// Reenvía al servlet correspondiente de cargar los datos necesarios para mostrar la página con la lista de informes existentes
		request.getRequestDispatcher("Informes").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
