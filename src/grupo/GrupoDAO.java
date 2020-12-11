package grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class GrupoDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public GrupoDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Grupo grupo) {
		try {
			sql = "insert into grupo (nome_grupo, tipo_grupo, pontuacao_minima, criado_por)"
					+ "values (?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, grupo.getNome());
			pstm.setString(2, grupo.getTipo());
			pstm.setDouble(3, grupo.getPontuacaoMinima());
			pstm.setString(4, grupo.getAdministrador().getEmail());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Grupo> select() {

		List<Grupo> grupos = new ArrayList<Grupo>();
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
		return grupos;

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
