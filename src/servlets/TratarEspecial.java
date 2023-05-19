package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SolicitudesEspecialesDAO;

/*
 * Servlet encargado dar privilegios especiales o no al usuario seleccionado por el administrador,
 * para posteriormente eliminar la solicitud de dicho usuario
 */

/**
 * Servlet implementation class TratarEspecial
 */
@WebServlet("/TratarEspecial")
public class TratarEspecial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TratarEspecial() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String tipo = request.getParameter("tipo");
		String idEspecial = request.getParameter("idEspecial");
		SolicitudesEspecialesDAO daoE = new SolicitudesEspecialesDAO();
		// Si 'tipo' equivale a 'descartar', únicamente se elimina la solicitud
		if (tipo.contentEquals("descartar")) {
			daoE.negarSolicitud(idEspecial);
		}
		// Si 'tipo' equivale a 'aprobar', se dan privilegios especiales al usuario y se elimina la solicitud
		else if (tipo.contentEquals("aprobar")) {
			daoE.darPrivilegios(idEspecial);
		}
		
		// Reenvía al servlet correspondiente de cargar los datos necesarios para mostrar la página con la lista de solicitudes existentes
		request.getRequestDispatcher("Especiales").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
