package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EventosDAO;
import model.EventosVO;
import model.GruposVO;
import model.UsuariosVO;

/*
 * Servlet encargado de publicar un evento
 */

/**
 * Servlet implementation class SubirEvento
 */
@WebServlet("/SubirEvento")
public class SubirEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirEvento() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		String grupoActual = "";
		if (usuario.getTipo().equals("Administrador")) {
			GruposVO ga = (GruposVO)request.getSession().getAttribute("grupoActual");
			grupoActual = ga.getNombre();
		}
		else grupoActual = (String)request.getSession().getAttribute("grupoActual");
		String empresa = request.getParameter("empresa");
		String lugar = request.getParameter("lugar");
		String descripcion = request.getParameter("descripcion");
		String fechaS = request.getParameter("fecha");
		SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");

		try {
			java.util.Date fechaUD = in.parse(fechaS);
			java.sql.Date fechaSD = new java.sql.Date(fechaUD.getTime());
			
			EventosVO evento = new EventosVO(0, empresa, fechaSD, lugar, descripcion, 0, grupoActual, usuario.getNombre());
			EventosDAO dao = new EventosDAO();

			// Publicar evento
			dao.publicarEvento(evento);

        } catch (ParseException e) {
            e.printStackTrace();
        }
			
		// Reenvía al servlet correspondiente de cargar los datos necesarios para mostrar 
		// la página donde aparecen los eventos en la sesión de usuario
		request.getRequestDispatcher("IniciarEventos").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
