package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PublicacionesVO;
import model.PublicacionesDAO;

/*
 * Servlet encargado de cargar en los atributos de sesión corresponientes los datos necesarios 
 * para mostrar correctamente las publicaciones de un grupo concreto
 */

/**
 * Servlet implementation class MostrarPublicaciones
 */
@WebServlet("/MostrarPublicaciones")
public class MostrarPublicaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarPublicaciones() {
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
		
		String grupoActual = (String)request.getSession().getAttribute("grupoActual");
		
		// Establecer los atributos de sesión 'publicaciones', como una lista de PublicacionesVO,
		PublicacionesDAO daoP = new PublicacionesDAO();
		ArrayList<PublicacionesVO> publicaciones = daoP.obtenerPublicacionesCrono(grupoActual, startAsInt, endAsInt);
		request.getSession().setAttribute("publicaciones", publicaciones);
		
		// Reenvía a la página de publicaciones del usuario
		request.getRequestDispatcher("Publicaciones.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
