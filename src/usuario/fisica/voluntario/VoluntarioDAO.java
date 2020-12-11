package usuario.fisica.voluntario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class VoluntarioDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public VoluntarioDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Voluntario voluntario) {
		try {
			sql = "insert into voluntario (email_usuario_voluntario, pontuacao_voluntario) "
					+ "values (?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, voluntario.getEmail());
			pstm.setDouble(2, voluntario.getPontuacao());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Voluntario> select() {

		List<Voluntario> voluntarios = new ArrayList<Voluntario>();
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
		return voluntarios;

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
