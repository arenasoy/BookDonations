package usuario.fisica.donatario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class DonatarioDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public DonatarioDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Donatario donatario) {
		try {
			sql = "insert into donatario (email_usuario_donatario, pontuacao_donatario) "
					+ "values (?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, donatario.getEmail());
			pstm.setDouble(2, donatario.getPontuacao());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Donatario> select() {

		List<Donatario> donatarios = new ArrayList<Donatario>();
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
		return donatarios;

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
