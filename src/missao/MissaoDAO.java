package missao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class MissaoDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public MissaoDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Missao missao) {
		try {
			sql = "insert into missao "
					+ "(data_horario_missao, codigo_barras_lv, pontucao_missao, adm_aprovador) "
					+ "values (?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			//TODO
			//pstm.setTimestamp(1, );
			pstm.setInt(2, missao.getLivro().getCodigoBarras());
			pstm.setDouble(3, missao.getPontuacao());
			pstm.setString(4, missao.getAdministrador().getEmail());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Missao> select() {

		List<Missao> missoes = new ArrayList<Missao>();
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
		return missoes;

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
