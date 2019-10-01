package br.com.cative.bo;
import br.com.cative.beans.Usuario;
import br.com.cative.dao.UsuarioDAO;

public class UsuarioBO {
	
	
	public String verificaUsuario(Usuario user) throws Exception{
		if(user.getNome().length()>20) {
			return "Nome inv�lido";
		}
		if(user.getSenha().length()<8 || user.getSenha().length()>10){
			return "Senha inv�lida";
		}
		
		if (user.getEmail().indexOf("@") > 0 && 
				user.getEmail().indexOf(".") > -1 && user.getEmail().indexOf("@.") == -1) {
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
