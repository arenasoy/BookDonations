package temporada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class TemporadaDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public TemporadaDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Temporada temporada) {
		try {
			sql = "insert into temporada (data_inicial_temp, duracao_temp) "
					+ "values (?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setDate(1, java.sql.Date.valueOf(temporada.getDataInicial()));
			pstm.setInt(2, temporada.getDuracao());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Temporada> select() {

		List<Temporada> temporadas = new ArrayList<Temporada>();
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
		return temporadas;

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
