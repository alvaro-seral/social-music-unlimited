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
 * Servlet encargado de, durante la acción de registro, comprobar que la contraseña propuesta
 * y su repetición son exactamente iguales
 */

/**
 * Servlet implementation class IniciarSesion
 */
@WebServlet("/Registrarse2Servlet")
public class Registrarse2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registrarse2Servlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Si las contraseñas escritas son idénticas, se actualiza el atributo de sesión 'usuario' 
		// incorporando la nueva contraseña 
		if (request.getParameter("contra").equals(request.getParameter("contraRep"))) {
			UsuariosVO usuario = (UsuariosVO)request.getSession().getAttribute("usuario");
			usuario.setContrasenya(request.getParameter("contra"));
			request.getSession().setAttribute("usuario", usuario);
			
			// Establece el atributo de sesión 'nomGrupos', que incluye todos los grupos existentes,
			// para el siguiente último paso de la acción de registro en la siguiente página
			InicioDAO dao = new InicioDAO();
			ArrayList<GruposVO> grupos = dao.mostrarGrupos();
			ArrayList<String> nomGrupos = new ArrayList<String>();
			for (int i=0; i<grupos.size(); i++) { 
				nomGrupos.add(grupos.get(i).getNombre());
			}
			request.getSession().setAttribute("nomGrupos", nomGrupos);
			
			// Reenvía a la página correspondiente al último paso de la acción de registro
			request.getRequestDispatcher("signin3.jsp").forward(request, response);
		}
		// Si no, se notifica del error al usuario
		// Reenvía a la página anterior, es decir, a la correspondiente de 'registrarse 2'
		// notificando el error por medio de un parametro
		else request.getRequestDispatcher("signin2.jsp?error=contra").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
