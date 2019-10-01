package br.com.cative.bo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.cative.beans.Usuario;
import br.com.cative.dao.UsuarioDAO;

public class UsuarioBO {
	
	
	public String verificaUsuario(Usuario user) throws Exception{
		if(user.getNome().length()>20) {
			return "Nome inv�lido";
		}
		if(user.getSenha().length()<5 || user.getSenha().length()>21){
			return "Senha inv�lida";
		}
		
		Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");  
		Matcher matcher = pattern.matcher(user.getEmail());  

		if (!matcher.find()) {
			return "E-mail inv�lido";
		}
	
	UsuarioDAO dao = new UsuarioDAO();
	Usuario usu = dao.getUsuario(user.getIdUsuario());
	if (usu.getIdUsuario()==0) {
		return dao.addUsuario(user) + "Usu�rio Cadastrado!";
	}else {
		return "Usu�rio j� existe!";
	}
	
	}
	

	
}
