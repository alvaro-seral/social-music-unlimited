package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsuariosVO;

/* 
 * Servlet encargado de cambiar el atributo de sesión 'orden' por el nuevo seleccionado
 */

/**
 * Servlet implementation class Ordenar
 */
@WebServlet("/Ordenar")
public class Ordenar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ordenar() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Establecer el atributo de sesión 'orden' con el nuevo valor
		request.getSession().setAttribute("orden", request.getParameter("filtro"));
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		
		// Si el usuario solicinte es de tipo 'Administrador', reenvía al servlet correspondiente
		// de cargar los datos necesarios para mostrar la página donde aparecen los eventos en la sesión de administrador
		if (usuario.getTipo().equals("Administrador")) {
			request.getRequestDispatcher("MostrarEventosAdmin").forward(request, response);
		}
		// Si no, reenvía al servlet correspondiente de cargar los datos necesarios para mostrar 
		// la página donde aparecen los eventos en la sesión de usuario
		else {
			request.getRequestDispatcher("MostrarEventos").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
