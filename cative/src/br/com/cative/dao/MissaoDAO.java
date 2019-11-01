package br.com.cative.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.cative.beans.Missao;
import br.com.cative.beans.Usuario;
import br.com.cative.conexao.Conexao;


/** Classe para os m�todos referentes a Missao
 * 
 * @author Cative
 *
 */
public class MissaoDAO {
	private Connection con;
	private Connection con1;
	private PreparedStatement stmt;
	private ResultSet rs;

	/** M�todo para realizar a conex�o com o banco de dados */
	public	MissaoDAO() throws Exception {
		con = Conexao.getConexao();
//		con1 = Conexao.getConexaoORCL();
		}
	
	
	/** M�todo que <b>adiciona></b> uma tupla na tabela
	 * TB_MISSAO
	 * @param miss�o com Objetivo da miss�o e Descri��o da miss�o  
	 * @see Missao 
	 * @author Cative*/
	public void AddMissaoBasic(Missao m) throws Exception {
		stmt = con.prepareStatement("INSERT INTO TB_MISSAO(OBJETIVO_MISSAO, DESCRICAO_MISSAO,PONTOS_MISSAO) VALUES(?, ?, ?)");
		stmt.setString(1, m.getObjetivo());
		stmt.setString(2, m.getDescricao());
		stmt.setInt(3, m.getPontos());
		stmt.execute();
		stmt.close();
	}
	
	
	
	/** M�todo que <b>seleciona</b> da tabela
	 * TB_MISSAO
	 * @return nova Missao 
	 * @param idMissao - id da Miss�o no Banco
	 * @param objetivo - o o objetivo da Miss�o
	 * @param descricao - a descricao da Miss�o
	 * @param imgMiss�o - a imagem referencia da Miss�o
	 * @param pontos - quantidade de pontos que a Miss�o vale
	 * corMissao - cor que a Miss�o tem como refer�ncia
	 * @author Cative
	 *  */
	public Missao getMissao(int idMissao) throws Exception{
		stmt = con.prepareStatement
				("select ID_MISSAO, OBJETIVO_MISSAO, DESCRICAO_MISSAO, IMG_MISSAO, PONTOS_MISSAO, COR_MISSAO  from TB_MiSSAO where ID_MISSAO=?");
		stmt.setInt(1, idMissao);
		rs = stmt.executeQuery();
		if(rs.next()) {
			return new Missao(
					rs.getInt("ID_MISSAO"),
					rs.getString("OBJETIVO_MISSAO"),
					rs.getString("DESCRICAO_MISSAO"),
					rs.getString("IMG_MISSAO"),
					rs.getInt("PONTOS_MISSAO"),
					rs.getString("COR_MISSAO")
					);
		}else {
			return new Missao();
		}
	}
	
	

	/** M�todo que <b>seleciona</b> todas as miss�es de uma turma espec�fica de acordo com o idUsuario que possue as miss�es em seu perfil
	 * TB_MISSAO, TB_TURMA, TB_USUARIO
	 * @return missoes - uma lista de miss�es
	 * @author Cative
	 *   */
	public List getMissoes(int idUsuario) throws Exception{
		stmt = con.prepareStatement
		("select * from tb_missao as m join tb_turma_has_tb_missao as tt on m.id_missao = tt.tb_missao_id_missao "
				+ "join tb_turma as t on t.id_turma = tt.tb_turma_id_turma "
				+ "join tb_usuario_has_tb_turma as tu on t.id_turma = tu.tb_turma_id_turma "
				+ "join tb_usuario as u on u.id_usuario = tu.tb_usuario_id_usuario "
				+ "where id_usuario = ? and (missao_concluida = 0)");
		stmt.setInt(1, idUsuario);
		rs = stmt.executeQuery();
		List<Missao> missoes = new ArrayList<Missao>();
		while(rs.next()) {
			missoes.add(
				new Missao(
						rs.getInt("ID_MISSAO"),
						rs.getString("OBJETIVO_MISSAO"),
						rs.getString("DESCRICAO_MISSAO"),
						rs.getString("IMAGEM_MISSAO"), 
						rs.getInt("PONTOS_MISSAO"), 
						rs.getString("COR_MISSAO") 
					));
		}
		return missoes;
	}
	
	
	/** M�todo que <b>deleta</b> uma tupla da tabela
	 * TB_MISSAO
	 * @author Cative */
	public int delMissao(int idMissao) throws Exception {
		stmt = con.prepareStatement
				("delete from TB_MISSAO where ID_MISSAO=?");
		stmt.setInt(1, idMissao);
		return stmt.executeUpdate();
	}
	
	
	/** M�todo que <b>atualiza</b> uma tupla da tabela
	 * TB_MISSAO
	 * @author Cative */
	public int attMissao(int idMissao) throws Exception {
		stmt = con.prepareStatement("update * from tb_missao where id_missao=?");
		stmt.setInt(1, idMissao);
		int rs = stmt.executeUpdate();
		return rs;
	}
	
	
	/** M�todo que <b>adiciona</b> na tabela
	 * TB_MISSAO
	 * @param objetivo - objetivo da miss�o
	 * @param descricao - a descri��o da miss�o
	 * @param imgMissao - a imagem refer�ncia da miss�o
	 * @param pontos - pontua��o da miss�o
	 * @param corMissao - a cor refer�ncia da miss�o
	 * N�o podendo ter repeti��es 
	 * @author Cative*/
	public Missao adicionaMissao(Missao missao) throws Exception {
		stmt = con.prepareStatement("INSERT INTO TB_MISSAO(OBJETIVO_MISSAO, DESCRICAO_MISSAO, IMAGEM_MISSAO, PONTOS_MISSAO, COR_MISSAO) VALUES(?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, missao.getObjetivo());
		stmt.setString(2, missao.getDescricao());
		stmt.setString(3, missao.getImgMissao());
		stmt.setInt(4, missao.getPontos());
		stmt.setString(5, missao.getCorMissao());
		stmt.executeUpdate();
		rs = stmt.getGeneratedKeys();
		if(rs.next()) {
			int id_missao = rs.getInt(1);
			missao.setIdMissao(id_missao);
			return missao;
		} else {
			return missao;
		}
	}
	
	
	/** M�todo que <b>adiciona</b> uma tupla na tabela
	 * TB_turma_has_TB_Missao
	 * @param idTurma - identificador da Turma
	 * @param idMissao - identificador da Missao
	 * @author Cative*/
	public int adicionaMissaoEmTurma(int idTurma, int idMissao) throws Exception {
		stmt = con.prepareStatement("INSERT INTO TB_turma_has_TB_Missao(TB_TURMA_id_turma, TB_missao_id_missao) VALUES(?, ?)");
		stmt.setInt(1, idTurma);
		stmt.setInt(2, idMissao);
		return stmt.executeUpdate();
	}
	
	
	/** M�todo que <b>filtra</b> da tabela 
	 * TB_MISSOES
	 * miss�es de uma turma espec�fica
	 * @author Cative*/
	public List filtraMissoes(int id) throws Exception{
		stmt = con.prepareStatement
				("select * from tb_missao as m join tb_turma_has_tb_missao as tt on m.id_missao = tt.tb_missao_id_missao join tb_turma as t on t.id_turma = tt.tb_turma_id_turma where id_turma = ? and (missao_concluida = 0);");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		List<Missao> missoes = new ArrayList<Missao>();
		while(rs.next()) {
			missoes.add(
				new Missao(
						rs.getInt("ID_MISSAO"),
						rs.getString("OBJETIVO_MISSAO"),
						rs.getString("DESCRICAO_MISSAO"),
						rs.getString("IMAGEM_MISSAO"), 
						rs.getInt("PONTOS_MISSAO"), 
						rs.getString("COR_MISSAO") 
					));
		}
		return missoes;
	}
	 

	public Missao getMissaoById(int id) throws Exception{
		stmt = con.prepareStatement("select * from tb_missao where id_missao = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		if(rs.next()) {
			return new Missao(
					rs.getInt("ID_MISSAO"),
					rs.getString("OBJETIVO_MISSAO"),
					rs.getString("DESCRICAO_MISSAO"),
					rs.getString("IMAGEM_MISSAO"), 
					rs.getInt("PONTOS_MISSAO"), 
					rs.getString("COR_MISSAO") 
							   );
		} else {
			return new Missao();
		}
	}
	
	public Missao editarMissao(Missao missao) throws Exception {
		stmt = con.prepareStatement("update tb_missao set objetivo_missao=?, descricao_missao=?, imagem_missao=?, pontos_missao=?, cor_missao=? where id_missao=?");
		stmt.setString(1, missao.getObjetivo());
		stmt.setString(2, missao.getDescricao());
		stmt.setString(3, missao.getImgMissao());
		stmt.setInt(4, missao.getPontos());
		stmt.setString(5, missao.getCorMissao());
		stmt.setInt(6, missao.getIdMissao());
		stmt.executeUpdate();
		return missao;
	}
	
	public int concluiMissao(int idMissao) throws Exception {
		stmt = con.prepareStatement("UPDATE TB_MISSAO SET MISSAO_CONCLUIDA = 1 WHERE ID_MISSAO = ?");
		stmt.setInt(1, idMissao);
		int rs = stmt.executeUpdate();
		return rs;
	}
	
	/** M�todo para fechar a conex�o com o banco 
	 * @author Cative*/
	public void fechar() throws Exception{
		con.close();
	}
	
	

}