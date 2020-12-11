package endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class EnderecoDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public EnderecoDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Endereco endereco) {
		try {
			sql = "insert into endereco (id, cep, numero, rua, bairro, complemento) "
					+ "values (?, ?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, endereco.getId());
			pstm.setString(2, endereco.getCep());
			pstm.setInt(3, endereco.getNumero());
			pstm.setString(4, endereco.getRua());
			pstm.setString(5, endereco.getBairro());
			pstm.setString(6, endereco.getComplemento());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Endereco> select() {

		List<Endereco> enderecos = new ArrayList<Endereco>();
		try {
			sql = "SELECT * FROM campeonato";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Endereco endereco = new Endereco();
				endereco.setId(rs.getInt("id"));
				endereco.setCep(rs.getString("cep"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setComplemento("complemento");
				endereco.setRua(rs.getString("rua"));
				endereco.setNumero(rs.getInt("numero"));
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enderecos;

	}

	public void delete(int id) {

		try {
			sql = "delete from tbAluno where matricula_aluno = ?";
			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, id);

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
