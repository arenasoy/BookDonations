package livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class LivroDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public LivroDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(Livro livro) {
		try {
			sql = "insert into livro (codigo_barras, autor, titulo, isbn, edicao, condicao, origem) values (?, ?, ?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, livro.getCodigoBarras());
			pstm.setString(2, livro.getAutor());
			pstm.setString(3, livro.getTitulo());
			pstm.setInt(4, livro.getIsbn());
			pstm.setString(5, livro.getEdicao());
			pstm.setInt(6, livro.getCondicao());
			pstm.setString(7, livro.getOrigem().toString());

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			
			if (e.getErrorCode() == 1) {
				System.out.println("Código de barras já cadastrado");
			} else {
				e.printStackTrace();
			}
			
			return e.getErrorCode();
		}
		
		return 0;

	}

	public List<Livro> select() {

		List<Livro> livros = new ArrayList<Livro>();
		try {
			sql = "select * from livro";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				livros.add(new Livro(rs.getInt("codigo_barras"), rs.getString("autor"), rs.getString("titulo"),
						rs.getInt("isbn"), rs.getString("edicao"), rs.getInt("condicao"),
						Origem.valueOf(rs.getString("origem")), true));
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return livros;

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
