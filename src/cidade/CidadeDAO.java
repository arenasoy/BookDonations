package cidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class CidadeDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public CidadeDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(Cidade cidade) {
		try {
			sql = "insert into cidade (id, nome_cidade, uf_cidade)" + "values (?, ?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, cidade.getId());
			pstm.setString(2, cidade.getNome());
			pstm.setString(3, cidade.getUf());

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				System.out.println("Cidade já existe.");
			} else {
				e.printStackTrace();
			}
			return e.getErrorCode();
		}

		return 0;
	}

	public List<Cidade> select() {

		List<Cidade> cidades = new ArrayList<Cidade>();
		try {
			sql = "select * from cidades";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Cidade cidade = new Cidade(rs.getInt("id"), rs.getString("nome_cidade"), rs.getString("uf_cidade"));
				
				cidades.add(cidade);
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cidades;

	}

	public void delete(int id) {

		try {
			sql = "";
			pstm = conn.prepareStatement(sql);

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
