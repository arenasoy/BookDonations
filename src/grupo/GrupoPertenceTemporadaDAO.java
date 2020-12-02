package grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class GrupoPertenceTemporadaDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public GrupoPertenceTemporadaDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(GrupoPertenceTemporada grupoPertenceTemporada) {
		try {
			sql = "";

			pstm = conn.prepareStatement(sql);
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<GrupoPertenceTemporada> select() {

		List<GrupoPertenceTemporada> grupoPertenceTemporadas = new ArrayList<GrupoPertenceTemporada>();
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
		return grupoPertenceTemporadas;

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
