package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ComentariosEventVO;
import model.EventosDAO;
import model.EventosVO;
import model.UsuariosVO;

/*
 * Servlet encargado de publicar un comentario de evento  
 */

/**
 * Servlet implementation class SubirComentarioE
 */
@WebServlet("/SubirComentarioE")
public class SubirComentarioE extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirComentarioE() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		EventosVO eventoActual = (EventosVO)request.getSession().getAttribute("fuenteActual");
		String descripcion = request.getParameter("descripcion");
		
		ComentariosEventVO comentario = new ComentariosEventVO(0, usuario.getNombre(), descripcion, eventoActual.getId());
		EventosDAO dao = new EventosDAO();
		
		// Publicar comentario de evento
		dao.publicarComentario(comentario);

		// Reenvía al servlet correspondiente de cargar los datos necesarios para mostrar 
		// la página donde aparecen los comentarios de evento en la sesión de usuario
		request.getRequestDispatcher("MostrarComentariosE").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
