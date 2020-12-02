package cidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class CidadeDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public CidadeDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Cidade cidade) {
		try {
			sql = "";

			pstm = conn.prepareStatement(sql);
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Cidade> select() {

		List<Cidade> cidades = new ArrayList<Cidade>();
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
		return cidades;

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
