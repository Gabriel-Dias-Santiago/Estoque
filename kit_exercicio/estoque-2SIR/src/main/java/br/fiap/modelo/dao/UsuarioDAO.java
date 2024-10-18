package br.fiap.modelo.dao;

import java.sql.SQLException;

import br.fiap.modelo.bean.Usuario;
import br.fiap.modelo.conexao.ConnectionFactory;

public class UsuarioDAO extends DAO {

	public UsuarioDAO() {
		this.connection = ConnectionFactory.getInstance().getConnection();
	}
	
	
	public void cadastrar(Usuario usuario) {
		int idUsuario = 0, idPerfil = 0;
		
		try {
			// desativando o commit automático para realizar uma transação
			connection.setAutoCommit(false);
			
			// inserção na tabela usuário
			sql = "insert into tbl_java_usuario values(sequencia_usuario.nextval, ?, ?, ?, ?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getSenha());
			ps.setInt(4, 1);
			ps.setString(5, usuario.getSalt());
			ps.execute();
			
			// obtém o ID do usuário que acabou de ser inserido
			sql = "select sequencia_usuario.currval from dual";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				idUsuario = rs.getInt(1);
			}
			
			// obtém o ID do perfil do usuário
			sql = "select id_perfil from tbl_java_perfil where perfil = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getPerfil().getPerfil());
			rs = ps.executeQuery();
			if(rs.next()) {
				idPerfil = rs.getInt(1);
			}
			
			// inserção dos dados na tabela java usuário perfil
			sql = "insert into tml_java_usuario_perfil values(?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, idUsuario);
			ps.setInt(2, idPerfil);
			ps.execute();
			
			connection.commit();
			
		} 
		catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}

}
