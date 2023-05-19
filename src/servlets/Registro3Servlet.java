package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UsuariosVO;
import model.GruposVO;
import model.InicioDAO;

/*
 * Servlet encargado de, durante la acción de registro, apuntar en uno o varios grupos al usuario solicitante
 */

/**
 * Servlet implementation class Registrarse3servlet
 */
@WebServlet("/Registro3Servlet")
public class Registro3Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registro3Servlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String gruposUsuario[] = request.getParameterValues("grupos");
		
		// Si el usuario intenta quedarse apuntado a 0 grupos, se le notifica de error
		// Reenvía a la página anterior, es decir, a la correspondiente de 'registrarse 3'
		// notificando el error por medio de un parametro
		if (gruposUsuario == null) request.getRequestDispatcher("signin3.jsp?error=minimoGrupos").forward(request, response);
		// Si no, se añade o se saca al usuario de los grupos correspondientes
		else {
			InicioDAO dao = new InicioDAO();
			UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
			
			ArrayList<GruposVO> grupos = new ArrayList<GruposVO>();
			for(int i=0; i<gruposUsuario.length; i++) {
				GruposVO g = new GruposVO(gruposUsuario[i], 0);
				grupos.add(g);
			}
			
			// Crea la nueva cuenta de tipo 'Normal' para el usuario, incluyendo la información de nombre, correo, contraseña y grupos apuntados
			dao.crearCuenta(usuario, grupos);
			
			// Invalida la sesión actual y reenvía a la página de inicio de sesión
			request.getSession().invalidate();
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
