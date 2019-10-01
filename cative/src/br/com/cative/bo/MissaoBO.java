package br.com.cative.bo;

import br.com.cative.beans.Missao;
import br.com.cative.dao.MissaoDAO;

public class MissaoBO {
	public boolean novaMissao(Missao m) throws Exception {
		if (m.getObjetivo().length() > 30) {
			System.out.println("Objetivo da miss�o inv�lido!");
			return false;
		}
		
		if(m.getObjetivo().length() > 100) {
			System.out.println("Descri��o da miss�o inv�lida!");
			return false;
		}
		
		if (Integer.parseInt(m.getDtInicio()) > Integer.parseInt(m.getDtInicio())) {
			System.out.println("Datas inv�lidas, data do inicio n�o pode ser maior que a final!");
			return false;
		}
		MissaoDAO dao = new MissaoDAO();
		Missao missao = dao.getMissao(m.getIdMissao());
		
		if(m.getIdMissao()==0) {
			System.out.println("Miss�o cadastrada!");
			return true;
		}else {
			System.out.println("Miss�o j� existe");
			return false;
		}
	}
}
