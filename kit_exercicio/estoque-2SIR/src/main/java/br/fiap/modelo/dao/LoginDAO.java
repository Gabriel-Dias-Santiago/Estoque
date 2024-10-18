package br.fiap.modelo.dao;

import java.sql.SQLException;
import java.util.Base64;
import br.fiap.modelo.bean.Perfil;
import br.fiap.modelo.bean.Usuario;
import br.fiap.modelo.conexao.ConnectionFactory;
import br.fiap.modelo.util.Util;

public class LoginDAO extends DAO {

	public LoginDAO() {
		this.connection = ConnectionFactory.getInstance().getConnection();
	}
	
	public Usuario autenticar(String email, String senha) {
		Usuario usuario = null;
		sql = "select u.nome, u.senha, u.salt, p.perfil from tbl_java_usuario u "
				+ "join tml_java_usuario_perfil up on u.id_usuario = up.id_usuario "
				+ "join tbl_java_perfil p on p.id_perfil = up.id_perfil "
				+ "where email = ?";
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				byte[] salt = Base64.getDecoder().decode(rs.getString("salt"));
				String senhaCodificada = Util.codificar(senha, salt);
				if(senhaCodificada.equals(rs.getString("senha"))) {
					Perfil perfil = new Perfil();
					usuario = new Usuario();
					perfil.setPerfil(rs.getString("perfil"));
					usuario.setPerfil(perfil);
					usuario.setNome(rs.getString("nome"));
					}
			}
		} catch (SQLException e) {
			System.out.println("erro ao autenticar o usuário\n" + e);
		}
		
		
		return usuario;
	}
	
}
