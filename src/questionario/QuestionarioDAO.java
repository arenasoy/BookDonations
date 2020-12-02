package questionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class QuestionarioDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public QuestionarioDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Questionario questionario) {
		try {
			sql = "";

			pstm = conn.prepareStatement(sql);
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Questionario> select() {

		List<Questionario> questionarios = new ArrayList<Questionario>();
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
