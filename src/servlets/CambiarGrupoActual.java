package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GruposDAO;
import model.GruposVO;
import model.UsuariosVO;

/*
 * Servlet encargado de establecer el atributo de sesión grupoActual 
 * con el grupo seleccionado por el usuario
 */

/**
 * Servlet implementation class CambiarGrupoActual
 */
@WebServlet("/CambiarGrupoActual")
public class CambiarGrupoActual extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CambiarGrupoActual() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
		
		// Si el usuario es 'Administrador', el atributo establecido es de tipo GruposVO
		if (usuario.getTipo().equals("Administrador")) {
			GruposDAO daoG = new GruposDAO();
			GruposVO grupoActual = daoG.seleccionarGrupo(request.getParameter("cambiarGrupoActual"));
			request.getSession().setAttribute("grupoActual", grupoActual);
			
			// Reenvía a la página principal de la sesión del administrador
			request.getRequestDispatcher("AdminPorGrupos.jsp").forward(request, response);
		}
		// Si no, el atributo establecido es el nombre del grupo (String)
		else {
			request.getSession().setAttribute("grupoActual", request.getParameter("cambiarGrupoActual"));
			
			// Reenvía al servlet correspondiente de cargar los datos necesarios para mostrar la página principal de la sesión de usuario
			request.getRequestDispatcher("MostrarPublicaciones").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
