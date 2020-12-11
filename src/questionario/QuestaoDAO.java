package questionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

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

	public void insert(Questao questao) {
		try {
			sql = "insert into questao "
					+ "(email_usuario_donatario, codigo_barra, "
					+ "data_retirada, numero_identificador, "
					+ "nivel, pergunta, solucao, pontuacao) "
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
			e.printStackTrace();
		}

	}

	public List<Questao> select() {

		List<Questao> questionarios = new ArrayList<Questao>();
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
		return questionarios;

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
