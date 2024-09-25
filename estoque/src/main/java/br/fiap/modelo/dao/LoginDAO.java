package br.fiap.modelo.dao;

import java.sql.SQLException;

import br.fiap.modelo.bean.Perfil;
import br.fiap.modelo.bean.Usuario;
import br.fiap.modelo.conexao.ConnectionFactory;

public class LoginDAO extends DAO{
	
	public LoginDAO() {
		this.connection = ConnectionFactory.getInstance().getConnection();
	}
	
	public Usuario auntenticar(String email, String senha) {
		Usuario usuario = null;
		sql = "select u.nome, p.perfil from java_usuario u"
				+ "join tml_java_usuario_perfil up on u.id_usuario = up.id_usuario"
				+ "join tbl_java_perfil p on p.id_perfil = up.id_perfil"
				+ "where u.email = admin@fiap.com and u.senha = ?";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, senha);
			rs = ps.executeQuery();
			if(rs.next()) {
				Perfil perfil = new Perfil();
				perfil.setPefil(rs.getString("perfil"));
				usuario.setPerfil(perfil);
				usuario.setNome(rs.getString("nome"));
			}
		}catch(SQLException e){
			System.out.println("Erro ao autenticar o usuario");
		}
		
		return usuario;
	}
}
