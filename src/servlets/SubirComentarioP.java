package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PublicacionesDAO;
import model.PublicacionesVO;
import model.ComentariosPubliVO;
import model.UsuariosVO;

/*
 * Servlet encargado de publicar un comentario de publicación  
 */

/**
 * Servlet implementation class SubirComentarioP
 */
@WebServlet("/SubirComentarioP")
public class SubirComentarioP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirComentarioP() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		PublicacionesVO publicacionActual = (PublicacionesVO)request.getSession().getAttribute("fuenteActual");
		String descripcion = request.getParameter("descripcion");
		
		ComentariosPubliVO comentario = new ComentariosPubliVO(0, usuario.getNombre(), descripcion, publicacionActual.getId());
		PublicacionesDAO dao = new PublicacionesDAO();
		
		// Publicar comentario de publicación
		dao.publicarComentario(comentario);

		// Reenvía al servlet correspondiente de cargar los datos necesarios para mostrar 
		// la página donde aparecen los comentarios de publicación en la sesión de usuario
		request.getRequestDispatcher("MostrarComentariosP").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
