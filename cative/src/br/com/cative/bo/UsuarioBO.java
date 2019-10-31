package br.com.cative.bo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.cative.beans.Usuario;
import br.com.cative.dao.UsuarioDAO;

/**
 * Classe para validar as regras de neg�cio
 * @author Cative
 *
 */
public class UsuarioBO {
	
		/**
		 * M�todo para <b>validar<b> a quantidade de caracteres do
		 * nome, senha do usu�rio, se o e-mail est� correto
		 * e verificar se o mesmo j� existe no sistema
		 * @param user
		 * @return
		 * @throws Exception
		 */
		public String verificaUsuario(Usuario user) throws Exception{
		if(user.getNome().length()>20) {
			return "Nome inv�lido";
		}
		if(user.getSenha().length()<5 || user.getSenha().length()>21){
			return "Senha inv�lida";
		}
		if (user.getNome()==null) {
			return "Nome j� cadastrado";
		}
		
		Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");  
		Matcher matcher = pattern.matcher(user.getEmail());  

		if (!matcher.find()) {
			return "E-mail inv�lido";
		}
		
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usu = dao.retornaEmail(user.getEmail());
	
		if(usu.getEmail() == null) {
			dao.addUsuarioBasic(user);
			return "Usu�rio Cadastrado!";
		} else {
			return "Usu�rio j� existe!";
		}
	}
	

	
}
