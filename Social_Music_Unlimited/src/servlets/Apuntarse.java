package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EventosDAO;
import model.EventosVO;
import model.UsuariosVO;

/* 
 * Servlet dependiente del parametro 'accion':
 * - Si 'accion' equivale a 'si', apunta un usuario concreto a un evento específico
 * - Si 'accion' equivale a 'no', desapunta un usuario concreto de un evento específico
 */

/**
 * Servlet implementation class Apuntarse
 */
@WebServlet("/Apuntarse")
public class Apuntarse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Apuntarse() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		String eventoActual = request.getParameter("eventoActual");
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		
		EventosDAO daoE = new EventosDAO();
		
		// Apunta al usuario al eventoActual
		if (accion.equals("si")) {
			daoE.apuntarseEvento(usuario, eventoActual);
		} 
		// Desapunta al usuario del eventoActual
		else if (accion.equals("no")) {
			daoE.desapuntarseEvento(usuario, eventoActual);
		}
		
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		int startAsInt = (new Integer(start)).intValue();
		int endAsInt = (new Integer(end)).intValue();
		
		String grupoActual = (String)request.getSession().getAttribute("grupoActual");
		String orden = (String)request.getSession().getAttribute("orden");
		
		// Obtiene y añade a los atributos de seisón las listas concretas de eventos y apuntados actualizadas
		if (orden.equals("porFecha")) {
			ArrayList<EventosVO> eventos = daoE.obtenerEventosCrono(grupoActual, usuario, startAsInt, endAsInt);
			request.getSession().setAttribute("eventos", eventos);
			ArrayList<Boolean> apuntados = daoE.obtenerApuntadosCrono(grupoActual, usuario, startAsInt, endAsInt);
			request.getSession().setAttribute("apuntados", apuntados);
		}
		else if (orden.equals("porApuntados")) {
			ArrayList<EventosVO> eventos = daoE.obtenerEventosPorApuntados(grupoActual, usuario, startAsInt, endAsInt);
			request.getSession().setAttribute("eventos", eventos);
			ArrayList<Boolean> apuntados = daoE.obtenerApuntadosPorApuntados(grupoActual, usuario, startAsInt, endAsInt);
			request.getSession().setAttribute("apuntados", apuntados);
		}
		
		// Reenvía a la página de Eventos manteniendo los parametros 'start' y 'end'
		request.getRequestDispatcher("Eventos.jsp?start="+start+"&end="+end).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
