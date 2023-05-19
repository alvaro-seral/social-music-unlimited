package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GruposVO;
import model.GruposDAO;
import model.UsuariosVO;

/*
 * Servlet encargado de apuntar o desapuntar de uno o varios grupos al usuario solicitante
 */

/**
 * Servlet implementation class GestionarGrupos
 */
@WebServlet("/GestionarGrupos")
public class GestionarGrupos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionarGrupos() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String gruposUsuario[] = request.getParameterValues("grupos");
		
		// Si el usuario intenta quedarse apuntado a 0 grupos, se le notifica de error
		// Reenvía a la página anterior, es decir, a la correspondiente de 'gestionar grupos'
		// notificando el error por medio de un parametro
		if (gruposUsuario == null) request.getRequestDispatcher("Grupos.jsp?error=minimoGrupos").forward(request, response);
		// Si no, se añade o se saca al usuario de los grupos correspondientes
		else {
			GruposDAO dao = new GruposDAO();
			UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
			
			ArrayList<GruposVO> grupos = new ArrayList<GruposVO>();
			for(int i=0; i<gruposUsuario.length; i++) {
				GruposVO g = new GruposVO(gruposUsuario[i], 0);
				grupos.add(g);
			}
			
			dao.actualizarGrupos(usuario, grupos);
			
			// Se establecen los nuevos atributos de sesión: 
			// - 'gruposUsuario': lista con todos los grupos a los que el usuario pertenece
			// - 'grupoActual' grupo que tiene seleccionado el usuario actual, por defecto, el primero de gruposUsuario
			ArrayList<String> gruposU = new ArrayList<String>(Arrays.asList(gruposUsuario));
			String grupoActual = gruposU.get(0);
			request.getSession().setAttribute("gruposUsuario", gruposU);
			request.getSession().setAttribute("grupoActual", grupoActual);
			
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
