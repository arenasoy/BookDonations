package questao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import emprestimo.Emprestimo;

public class QuestaoDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public QuestaoDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(Questao questao) {
		try {
			sql = "insert into questao " + "(email_usuario_donatario, codigo_barra, "
					+ "data_retirada, numero_identificador, " + "nivel, pergunta, solucao, pontuacao) "
					+ "values (?, ?, ?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, questao.getEmprestimo().getDonatario().getEmail());
			pstm.setInt(2, questao.getEmprestimo().getLivro().getCodigoBarras());
			pstm.setDate(3, java.sql.Date.valueOf(questao.getEmprestimo().getDataRetirada()));
			pstm.setInt(4, questao.getNumeroIdentificador());
			pstm.setInt(5, questao.getNivel());
			pstm.setString(6, questao.getPergunta());
			pstm.setString(7, questao.getSolucao());
			pstm.setDouble(8, questao.getPontuacao());

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {

			if (e.getErrorCode() == 1) {
				System.out.println("Questão já existe");
			} else if (e.getErrorCode() == 2291) {
				System.out.println("Erro de chave estrangeira: Empréstimo não existe");
			} else {
				e.printStackTrace();
			}

			return e.getErrorCode();
		}

		return 0;
	}

	public List<Questao> select(boolean selectEmprestimo) {

		List<Questao> questoes = new ArrayList<Questao>();
		try {

			StringBuilder sb = new StringBuilder();

			sb.append("select * from questao Q");

			if (selectEmprestimo) {
				sb.append(" join emprestimo E on Q.email_usuario_donatario = E.email_usuario_donatario "
						+ "and Q.codigo_barras = E.codigo_barras and Q.data_retirada = E.data_retirada");
			}
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Questao q = new Questao(rs.getInt("numero_identificador"), rs.getInt("nivel"), rs.getString("pergunta"),
						rs.getString("solucao"), rs.getDouble("pontuacao"));

				if (selectEmprestimo) {
					//TODO select com emprestimo, vai juntar donatario e livro?
					//q.setEmprestimo(new Emprestimo());
				}
				
				questoes.add(q);
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questoes;

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
