package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsuariosVO;

/*
 * Servlet encargado de establecer el orden por defecto cuando se accede a una página de eventos
 */

/**
 * Servlet implementation class IniciarEventos
 */
@WebServlet("/IniciarEventos")
public class IniciarEventos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IniciarEventos() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Establecer el atributo de sesión 'orden' con su valor predetermindo
		request.getSession().setAttribute("orden", "porFecha");
		
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
