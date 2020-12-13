package emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bibliotecario.Bibliotecario;
import conexao.Conexao;
import livro.Livro;
import livro.Origem;
import usuario.fisica.donatario.Donatario;

public class EmprestimoDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public EmprestimoDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(Emprestimo emprestimo) {
		try {
			sql = "insert into emprestimo "
					+ "(email_usuario_donatario, codigo_barra, data_retirada, data_devolucao, cib)"
					+ "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, emprestimo.getDonatario().getEmail());
			pstm.setInt(2, emprestimo.getLivro().getCodigoBarras());
			pstm.setDate(3, java.sql.Date.valueOf(emprestimo.getDataRetirada()));
			pstm.setDate(4, java.sql.Date.valueOf(emprestimo.getDataDevolucao()));
			pstm.setInt(4, emprestimo.getBibliotecario().getCib());

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				System.out.println("Empréstimo já existe");
			} else if (e.getErrorCode() == 2291) {
				System.out.println("Erro de chave estrangeira: Donatário, Bibliotecário ou Livro não existem");
			} else {
				e.printStackTrace();
			}

			return e.getErrorCode();
		}

		return 0;
	}

	public List<Emprestimo> select(boolean selectDonatario, boolean selectLivro, boolean selectBibliotecario) {

		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select * emprestimo E");

			if (selectDonatario) {
				sb.append(" join donatario D on E.email_usuario_donatario = D.email_usuario_donatario");
			}

			if (selectLivro) {
				sb.append(" join livro L on E.codigo_barras = L.codigo_barras");
			}

			if (selectBibliotecario) {
				sb.append(" join bibliotecario B on E.cib = B.cib");
			}

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Emprestimo e = new Emprestimo(rs.getDate("data_retirada").toLocalDate(),
						rs.getDate("data_devolucao").toLocalDate());

				if (selectDonatario) {
					e.setDonatario(new Donatario(rs.getString("email_usuario_donatario"),
							rs.getDouble("pontuacao_donatario")));
				}

				if (selectLivro) {
					e.setLivro(new Livro(rs.getInt("codigo_barras"), rs.getString("autor"), rs.getString("titulo"),
							rs.getInt("isbn"), rs.getString("edicao"), rs.getInt("condicao"),
							Origem.valueOf(rs.getString("origem")), true));
				}

				if (selectBibliotecario) {
					e.setBibliotecario(new Bibliotecario(rs.getInt("cib"), rs.getString("senha_bibliotecario"),
							rs.getString("nome_bibliotecario")));
				}
				
				emprestimos.add(e);
			}
			pstm.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return emprestimos;

	}

}
