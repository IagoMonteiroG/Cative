package br.com.cative.bo;

import br.com.cative.beans.Missao;
import br.com.cative.dao.MissaoDAO;
import br.com.cative.dao.TurmaDAO;

/**
 * Classe para validar as regras de neg�cio das miss�es
 * @author Cative
 *
 */
public class MissaoBO {
	
	/**
	 * M�todo para <b>validar</b> a quantidade de caracteres do objetivo e descri��o da missao
	 * @param m
	 * @return
	 * @throws Exception
	 * @author Cative
	 */
	public boolean novaMissao(Missao m) throws Exception {
		if (m.getObjetivo().length() > 30) {
			System.out.println("Objetivo da miss�o inv�lido!");
			return false;
		}
		
		if(m.getDescricao().length() > 100) {
			System.out.println("Descri��o da miss�o inv�lida!");
			return false;
		}
		
		MissaoDAO dao = new MissaoDAO();
		dao.AddMissaoBasic(m);
		System.out.println("Miss�o cadastrada!");
		return true;
	}
}
