package doacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class DoacaoDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public DoacaoDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Doacao doacao) {
		try {
			sql = "insert into doacao "
					+ "(codigo_barras_ld, data_horario_doacao, pontuacao_doacao, bibliotecario_aprovador) "
					+ "values (?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, doacao.getLivro().getCodigoBarras());
			//TODO verify how to convert
			//pstm.setDate(2, java.sql.Timestamp.valueOf(doacao.getDataHora()));
			pstm.setDouble(3, doacao.getPontuacao());
			pstm.setInt(4, doacao.getBibliotecario().getCib());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Doacao> select() {

		List<Doacao> doacoes = new ArrayList<Doacao>();
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
		return doacoes;

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
