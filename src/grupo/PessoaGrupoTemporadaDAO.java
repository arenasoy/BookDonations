package grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import administrador.Administrador;
import conexao.Conexao;
import temporada.Temporada;
import usuario.fisica.PessoaFisica;

public class PessoaGrupoTemporadaDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public PessoaGrupoTemporadaDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(PessoaGrupoTemporada grupoPertenceTemporada) {
		try {
			sql = "insert into pertence (nome_grupo, tipo_grupo, temporada, email_usuario_pf)"
					+ "values (?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, grupoPertenceTemporada.getGrupo().getNome());
			pstm.setString(2, grupoPertenceTemporada.getGrupo().getTipo().toString());
			pstm.setDate(3, java.sql.Date.valueOf(grupoPertenceTemporada.getTemporada().getDataInicial()));
			pstm.setString(4, grupoPertenceTemporada.getPessoaFisica().getEmail());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			
			if (e.getErrorCode() == 1) {
				System.out.println("Relação já existe");
			} else if (e.getErrorCode() == 2291) {
				System.out.println("Erro de chave estrangeira: Grupo, Temporada ou Pessoa física não existem");
			} else {
				e.printStackTrace();
			}
			
			return e.getErrorCode();
			
		}
		
		return 0;

	}

	public List<PessoaGrupoTemporada> select(boolean selectGrupo, boolean selectTemporada, boolean selectPessoaFisica) {

		List<PessoaGrupoTemporada> pessoaGrupoTemporadas = new ArrayList<PessoaGrupoTemporada>();
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append("select * from pertence P");
			
			if (selectGrupo) {
				sb.append(" join grupo G on P.nome_grupo = G.nome_grupo and P.tipo_grupo = G.tipo_grupo  join administrador A on G.criado_por = A.email_adm");
			}
			
			if (selectTemporada) {
				sb.append(" join temporada T on P.temporada = T.data_inicial_temp");
			}
			
			if (selectPessoaFisica) {
				sb.append(" join pessoa_fisica F on P.email_usuario_pf = F.email_usuario_pf");
			}
			
			sql = sb.toString();
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				
				Grupo g = new Grupo(rs.getString("nome_grupo"), grupo.Tipo.valueOf(rs.getString("tipo_grupo")));
				
				if (selectGrupo) {
					g.setPontuacaoMinima(rs.getDouble("pontuacao_minima"));
					g.setAdministrador(new Administrador(
							rs.getString("email_adm"),
							rs.getString("senha_adm"),
							rs.getString("nome_adm"),
							rs.getDate("data_registro").toLocalDate()));
				}
				
				Temporada t = new Temporada(rs.getDate("temporada").toLocalDate());
				
				if (selectTemporada) {
					t.setDuracao(rs.getInt("duracao_temp"));
				}
				
				PessoaFisica pf = new PessoaFisica();
				pf.setEmail(rs.getString("email_usuario_pf"));
				
				if (selectPessoaFisica) {
					pf = new PessoaFisica(rs.getString("email_usuario_pf"), rs.getString("nome_usuario_pf"),
							rs.getString("cpf_usuario_pf"), rs.getString("rg_usuario_pf"),
							rs.getString("telefone_usuario_pf"));
				}
				
				PessoaGrupoTemporada pst = new PessoaGrupoTemporada(g, t, pf);
				
				pessoaGrupoTemporadas.add(pst);
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoaGrupoTemporadas;

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
