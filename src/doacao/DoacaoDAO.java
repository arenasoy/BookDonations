package doacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bibliotecario.Bibliotecario;
import conexao.Conexao;
import livro.Livro;
import livro.Origem;

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
			pstm.setTimestamp(2, Timestamp.from(doacao.getDataHora()));
			pstm.setDouble(3, doacao.getPontuacao());
			pstm.setInt(4, doacao.getBibliotecario().getCib());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Doacao> select(boolean selectLivro, boolean selectBibliotecario) {

		List<Doacao> doacoes = new ArrayList<Doacao>();
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append("select * from doacao D");
			
			if (selectLivro) {
				sb.append(" join livro_doador L on D.codigo_barras_ld = L.codigo_barras_ld");
			}
			
			if (selectBibliotecario) {
				sb.append(" left join bibliotecario B on D.bibliotecario_aprovador = B.cib");
			}
			
			sql = sb.toString();
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				
				Doacao d = new Doacao(rs.getTimestamp("data_horario_doacao").toInstant(), rs.getDouble("pontuacao_doacao"));
				
				if (selectLivro) {
					d.setLivro(new Livro(rs.getInt("codigo_barras"), rs.getString("autor"), rs.getString("titulo"),
						rs.getInt("isbn"), rs.getString("edicao"), rs.getInt("condicao"),
						Origem.valueOf(rs.getString("origem")), true));
				}
				
				if (selectBibliotecario) {
					d.setBibliotecario(new Bibliotecario(rs.getInt("cib"), rs.getString("senha_bibliotecario"),
						rs.getString("nome_bibliotecario")));
				}
				
				doacoes.add(d);
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doacoes;

	}

	
}
