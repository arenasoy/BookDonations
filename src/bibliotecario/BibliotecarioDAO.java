package bibliotecario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cidade.Cidade;
import conexao.Conexao;
import endereco.Endereco;

public class BibliotecarioDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public BibliotecarioDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(Bibliotecario bibliotecario) {
		try {

			if (bibliotecario.getEndereco() != null && bibliotecario.getCidade() != null) {

				sql = "insert into bibliotecario "
						+ "(cib, senha_bibliotecario, nome_bibliotecario, cidade_id, endereco_id)"
						+ "values (?, ?, ?, ?, ?)";
				pstm = conn.prepareStatement(sql);

				pstm.setInt(1, bibliotecario.getCib());
				pstm.setString(2, bibliotecario.getSenha());
				pstm.setString(3, bibliotecario.getNome());
				pstm.setInt(4, bibliotecario.getCidade().getId());
				pstm.setInt(5, bibliotecario.getEndereco().getId());

			} else if (bibliotecario.getEndereco() != null) {
				sql = "insert into bibliotecario " + "(cib, senha_bibliotecario, nome_bibliotecario, endereco_id)"
						+ "values (?, ?, ?, ?)";
				pstm = conn.prepareStatement(sql);

				pstm.setInt(1, bibliotecario.getCib());
				pstm.setString(2, bibliotecario.getSenha());
				pstm.setString(3, bibliotecario.getNome());
				pstm.setInt(4, bibliotecario.getEndereco().getId());
			} else if (bibliotecario.getCidade() != null) {
				sql = "insert into bibliotecario " + "(cib, senha_bibliotecario, nome_bibliotecario, cidade_id)"
						+ "values (?, ?, ?, ?)";
				pstm = conn.prepareStatement(sql);

				pstm.setInt(1, bibliotecario.getCib());
				pstm.setString(2, bibliotecario.getSenha());
				pstm.setString(3, bibliotecario.getNome());
				pstm.setInt(4, bibliotecario.getCidade().getId());
			} else {
				sql = "insert into bibliotecario " + "(cib, senha_bibliotecario, nome_bibliotecario)"
						+ "values (?, ?, ?)";
				pstm = conn.prepareStatement(sql);

				pstm.setInt(1, bibliotecario.getCib());
				pstm.setString(2, bibliotecario.getSenha());
				pstm.setString(3, bibliotecario.getNome());
			}

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {

			if (e.getErrorCode() == 1) {
				System.out.println("CIB já cadastrado");
			} else if (e.getErrorCode() == 2291) {
				System.out.println("Erro de chave estrangeira - Cidade ou Endereço inexistentes");
			} else {
				e.printStackTrace();
			}

			return e.getErrorCode();
		}

		return 0;

	}

	public List<Bibliotecario> select(boolean selectEndereco, boolean selectCidade) {

		List<Bibliotecario> bibliotecarios = new ArrayList<Bibliotecario>();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select * from bibliotecario B");

			if (selectEndereco) {
				sb.append(" left join endereco E on B.endereco_id = E.id");
			}

			if (selectCidade) {
				sb.append(" left join cidade C on B.cidade_id = C.id");
			}

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Bibliotecario bibliotecario = new Bibliotecario(rs.getInt("cib"), rs.getString("senha_bibliotecario"),
						rs.getString("nome_bibliotecario"));

				if (selectEndereco && rs.getInt(5) != 0) {
					bibliotecario.setEndereco(new Endereco(rs.getInt(5), rs.getString("cep"), rs.getInt("numero"),
							rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento")));
				}

				if (selectCidade && rs.getInt(4) != 0) {
					bibliotecario.setCidade(
							new Cidade(rs.getInt(4), rs.getString("nome_cidade"), rs.getString("uf_cidade")));

				}

				bibliotecarios.add(bibliotecario);
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bibliotecarios;

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
