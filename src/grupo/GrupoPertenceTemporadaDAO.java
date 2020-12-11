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
			sql = "insert into pertence (nome_grupo, tipo_grupo, temporada, email_usuario_pf)"
					+ "values (?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, grupoPertenceTemporada.getGrupo().getNome());
			pstm.setString(2, grupoPertenceTemporada.getGrupo().getTipo());
			pstm.setDate(3, java.sql.Date.valueOf(grupoPertenceTemporada.getTemporada().getDataInicial()));
			pstm.setString(4, grupoPertenceTemporada.getPessoaFisica().getEmail());
			
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
