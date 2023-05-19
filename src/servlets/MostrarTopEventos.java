package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AdministradoresDAO;
import model.EventosVO;
import model.GruposVO;

/*
 * Servlet encargado de cargar en los atributos de sesión los datos necesarios para mostrar la página de 'Estadisticas Top eventos' del administrador
 */

/**
 * Servlet implementation class MostrarTopEventos
 */
@WebServlet("/MostrarTopEventos")
public class MostrarTopEventos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarTopEventos() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GruposVO grupoActual = (GruposVO)request.getSession().getAttribute("grupoActual");
		AdministradoresDAO daoA = new AdministradoresDAO();
		
		// Establecer el atributo de seisón 'eventosSiempre' con la lista de eventos donde más personas se han apuntado desde siempre
		ArrayList<EventosVO> eventosSiempre = daoA.verMejorEventoSiempre(grupoActual.getNombre());
		request.getSession().setAttribute("eventosSiempre", eventosSiempre);
		// Establecer el atributo de seisón 'eventosSiempre' con la lista de eventos donde más personas se han apuntado durante el año actual
		ArrayList<EventosVO> eventosAnyo = daoA.verMejorEventoAnyo(grupoActual.getNombre());
		request.getSession().setAttribute("eventosAnyo", eventosAnyo);
		
		// Reenvía a la página de 'Estadisticas Top eventos' del administrador
		request.getRequestDispatcher("AdminTopEventos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
