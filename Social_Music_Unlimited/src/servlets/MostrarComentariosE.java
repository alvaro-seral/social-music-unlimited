package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ComentariosEventVO;
import model.EventosVO;
import model.EventosDAO;
import model.GruposVO;

/*
 * Servlet encargado de cargar en los atributos de sesión corresponientes los datos necesarios 
 * para mostrar correctamente los comentarios de eventos concretos
 */

/**
 * Servlet implementation class MostrarPublicaciones
 */
@WebServlet("/MostrarComentariosE")
public class MostrarComentariosE extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarComentariosE() {
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
		
		EventosDAO daoE = new EventosDAO();
		EventosVO eventoActual = null;
		
		String idEv = request.getParameter("eventoActual");
		// Si no hay parametro de evento, significa que ya se está en la página de comentarios 
		// y se está avanzando a retrocediendo a otra página
		if (idEv==null) {
			eventoActual = (EventosVO)request.getSession().getAttribute("fuenteActual");
		}
		// Si hay parámetro, se está ejecutando la acción de entrar a la sección de comentarios de un evento concreto
		else {
			// En este caso, se establece el atributo de sesión 'fuenteActual' con el evento solicitado
			eventoActual = daoE.obtenerEventoSeleccionado(idEv);
			request.getSession().setAttribute("fuenteActual", eventoActual);
		}

		// Se establece el atributo de sesión 'comentarios' con todos los comentarios atribuidos al evento solicitado
		ArrayList<ComentariosEventVO> comentarios = daoE.obtenerComentarios(eventoActual, startAsInt, endAsInt);
		request.getSession().setAttribute("comentarios", comentarios);
		
		// Reenvía a la página de comentarios del evento solicitado
		request.getRequestDispatcher("ComentariosEventos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
