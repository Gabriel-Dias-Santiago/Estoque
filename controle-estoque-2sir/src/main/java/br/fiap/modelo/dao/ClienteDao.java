package br.fiap.modelo.dao;

import java.sql.SQLException;

import br.fiap.modelo.bean.Cliente;
import br.fiap.modelo.connection.ConnectionFactory;

public class ClienteDao extends DAO {
	
	public ClienteDao() {
		this.connection = ConnectionFactory.getInstance().getConnection();
	}
		
	public void inserir(Cliente cliente) {
		int idCliente = 0;
		
		
		try {
			connection.setAutoCommit(false);
			sql = "select max(idCliente) from tbl_java_cliente";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				idCliente =  rs.getInt(1);
			}
			
			sql =  "insert into tbl_java_cliente values(?, ?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1 , idCliente+1);
			ps.setString(2, cliente.getNome() );
			ps.setLong(3, cliente.getCpf() );
			
			connection.commit();
		} catch (SQLException e) {
			System.out.println("erro ao cadastrar\n " + e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	
}
