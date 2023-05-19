package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ComentariosPubliVO;
import model.GruposVO;
import model.PublicacionesVO;
import model.PublicacionesDAO;

/*
 * Servlet encargado de cargar en los atributos de sesión corresponientes los datos necesarios 
 * para mostrar correctamente los comentarios de publicaciones concretas
 */

/**
 * Servlet implementation class MostrarPublicaciones
 */
@WebServlet("/MostrarComentariosP")
public class MostrarComentariosP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarComentariosP() {
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
		
		PublicacionesDAO daoP = new PublicacionesDAO();
		PublicacionesVO publicacionActual = null;
		
		String idPub = request.getParameter("publicacionActual");
		// Si no hay parametro de publicación, significa que ya se está en la página de comentarios 
		// y se está avanzando a retrocediendo a otra página
		if (idPub==null) {
			publicacionActual = (PublicacionesVO)request.getSession().getAttribute("fuenteActual");
		}
		// Si hay parámetro, se está ejecutando la acción de entrar a la sección de comentarios de una publicación concreta
		else {
			// En este caso, se establece el atributo de sesión 'fuenteActual' con la publicación solicitada
			publicacionActual = daoP.obtenerPublicacionSeleccionada(idPub);
			request.getSession().setAttribute("fuenteActual", publicacionActual);
		}

		// Se establece el atributo de sesión 'comentarios' con todos los comentarios atribuidos a la publicación solicitada
		ArrayList<ComentariosPubliVO> comentarios = daoP.obtenerComentarios(publicacionActual, startAsInt, endAsInt);
		request.getSession().setAttribute("comentarios", comentarios);
		
		// Reenvía a la página de comentarios de la publicación solicitada
		request.getRequestDispatcher("ComentariosPublicaciones.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
