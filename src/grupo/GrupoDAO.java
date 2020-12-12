package grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import administrador.Administrador;
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

	public int insert(Grupo grupo) {
		try {
			sql = "insert into grupo (nome_grupo, tipo_grupo, pontuacao_minima, criado_por)"
					+ "values (?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, grupo.getNome());
			pstm.setString(2, grupo.getTipo().toString());
			pstm.setDouble(3, grupo.getPontuacaoMinima());
			pstm.setString(4, grupo.getAdministrador().getEmail());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			
			if (e.getErrorCode() == 1) {
				System.out.println("Grupo já existe");
			} else if (e.getErrorCode() == 2291) {
				System.out.println("Erro de chave estrangeira: Administrador não existe");
			} else {
				e.printStackTrace();
			}
			
			return e.getErrorCode();
		}

		return 0;
	}

	public List<Grupo> select(boolean selectAdministrador) {

		List<Grupo> grupos = new ArrayList<Grupo>();
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append("select * from grupo");
			
			if (selectAdministrador) {
				sb.append(" join administrador on criado_por = email_adm");
			}
			
			sql = sb.toString();
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				
				Grupo g = new Grupo(rs.getString("nome_grupo"), Tipo.valueOf(rs.getString("tipo_grupo")), rs.getDouble("pontuacao_minima"));
				
				if (selectAdministrador) {
					g.setAdministrador(new Administrador(
						rs.getString("criado_por"),
						rs.getString("senha_adm"),
						rs.getString("nome_adm"),
						rs.getDate("data_registro").toLocalDate()));
				}
				
				grupos.add(g);
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
