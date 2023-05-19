package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsuariosVO;
import model.EventosDAO;
import model.EventosVO;
import model.GruposVO;

/*
 * Servlet encargado de cargar en los atributos de sesión corresponientes los datos necesarios 
 * para mostrar la página de 'Estadisticas por evento' del adminstrador
 */

/**
 * Servlet implementation class MostrarEventosAdmin
 */
@WebServlet("/MostrarEventosAdmin")
public class MostrarEventosAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarEventosAdmin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// - start: indice que indica el numero de fila donde se ecuentran los datos útiles en la base de datos
		// - end: contador que indica el numero de datos útiles que hay que extraer de la base de datos
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		if (start==null) start="1";   
		if (end==null) end="7";
		int startAsInt = (new Integer(start)).intValue();
		int endAsInt = (new Integer(end)).intValue();
		
		GruposVO grupoActual = (GruposVO)request.getSession().getAttribute("grupoActual");
		String orden = (String)request.getSession().getAttribute("orden");
		
		EventosDAO daoE = new EventosDAO();
		// Dependiendo del orden, establecer los atributos de sesión 'eventos', como una lista de EventosVO
		if (orden.equals("porFecha")) {
			ArrayList<EventosVO> eventos = daoE.obtenerEventosCronoAdmin(grupoActual.getNombre(), startAsInt, endAsInt);
			request.getSession().setAttribute("eventos", eventos);
		}
		else if (orden.equals("porApuntados")) {
			ArrayList<EventosVO> eventos = daoE.obtenerEventosPorApuntadosAdmin(grupoActual.getNombre(), startAsInt, endAsInt);
			request.getSession().setAttribute("eventos", eventos);
		}
		
		// Reenvía a la página de 'Estadisticas por evento' del administrador
		request.getRequestDispatcher("AdminPorEventos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
