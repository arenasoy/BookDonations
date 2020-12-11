package bibliotecario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

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

	public void insert(Bibliotecario bibliotecario) {
		try {
			sql = "insert into bibliotecario "
					+ "(cib, senha_bibliotecario, nome_bibliotecario, cidade_id, endereco_id)"
					+ "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, bibliotecario.getCib());
			pstm.setString(2, bibliotecario.getSenha());
			pstm.setString(3, bibliotecario.getNome());
			pstm.setInt(4, bibliotecario.getCidade().getId());
			pstm.setInt(5, bibliotecario.getEndereco().getId());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Bibliotecario> select() {

		List<Bibliotecario> bibliotecarios = new ArrayList<Bibliotecario>();
		try {
			sql = "";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				
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
