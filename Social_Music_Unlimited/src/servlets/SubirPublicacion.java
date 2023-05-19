package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PublicacionesDAO;
import model.PublicacionesVO;
import model.UsuariosVO;

/*
 * Servlet encargado de publicar una publicación
 */

/**
 * Servlet implementation class SubirPublicacion
 */
@WebServlet("/SubirPublicacion")
public class SubirPublicacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirPublicacion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		String grupoActual = (String)request.getSession().getAttribute("grupoActual");
		String descripcion = request.getParameter("descripcion");
		
		PublicacionesVO publicacion = new PublicacionesVO(0, usuario.getNombre(), descripcion, grupoActual);
		PublicacionesDAO dao = new PublicacionesDAO();
		
		// Publicar publicación
		dao.publicarPublicacion(publicacion);

		// Reenvía al servlet correspondiente de cargar los datos necesarios para mostrar 
		// la página donde aparecen las publicaciones en la sesión de usuario
		request.getRequestDispatcher("MostrarPublicaciones").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
