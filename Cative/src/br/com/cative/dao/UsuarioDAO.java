package br.com.cative.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.cative.beans.Usuario;
import br.com.cative.conexao.Conexao;

public class UsuarioDAO {
	
		private Connection con;
		private PreparedStatement stmt;
		private ResultSet rs;
		
		public UsuarioDAO() throws Exception{
			con = Conexao.getConexao();
		}
		
		public Usuario getUsuario(int cod) throws Exception{
			stmt = con.prepareStatement
					("select * from USUARIO where NOME_USUARIO=?");
			stmt.setInt(1, cod);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return new Usuario(
						
						);
			}else {
				return new Usuario();
			}
		}
		
		public int delete(int cod) throws Exception{
			
			stmt = con.prepareStatement
					("delete from DDD_TB_LOGIN where CD_USUARIO=?");
			stmt.setInt(1, cod);
			return stmt.executeUpdate();
		}
		
		public void fechar() throws Exception{
			con.close();
		}
		
	}
	

