package br.com.cative.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.cative.beans.Usuario;
import br.com.cative.dao.UsuarioDAO;

/**
 * Classe para <b>validar</b> o login do usu�rio
 * Servlet implementation class Login
 * @author Cative
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String email = request.getParameter("email_usuario");
		String senha = request.getParameter("senha_usuario");
		
		Usuario usu = new Usuario();
		
		usu.setEmail(email);
		usu.setSenha(senha);
		try {
		UsuarioDAO usuariodao = new UsuarioDAO();
		Usuario usuAutenticado = usuariodao.validaUsuario(usu);
		if(usuAutenticado != null) {
			if(usuAutenticado.getTipoUsuario() == 2) {
			response.sendRedirect("missoes.jsp");
			} else {
				response.sendRedirect("turmas.jsp");
			}
			HttpSession sessaoUsuario = request.getSession();
			Integer idUsuario = new Integer(usuAutenticado.getIdUsuario());
			sessaoUsuario.setAttribute("idUsuario", idUsuario);
		} else {
			response.sendRedirect("login.jsp?erro=1");
		}	
		}catch (Exception e) {
			e.printStackTrace();
			}
		}
	}


