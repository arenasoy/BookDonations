package usuario.fisica.donatario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cidade.Cidade;
import conexao.Conexao;
import endereco.Endereco;
import livro.Livro;
import usuario.Tipo;
import usuario.fisica.Perfil;
import usuario.fisica.voluntario.Voluntario;

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

	public int insert(Donatario donatario) {
		try {
			sql = "insert into donatario (email_usuario_donatario, pontuacao_donatario) " + "values (?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, donatario.getEmail());
			pstm.setDouble(2, donatario.getPontuacao());

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {

			if (e.getErrorCode() == 1) {
				System.out.println("E-mail já cadastrado");
			} else if (e.getErrorCode() == 2291) {
				System.out.println("Erro de chave estrangeira: e-mail inexistente");
			} else {
				e.printStackTrace();
			}

			return e.getErrorCode();
		}

		return 0;
	}

	public List<Donatario> select(boolean selectPessoaFisica, boolean selectUsuario) {

		List<Donatario> donatarios = new ArrayList<Donatario>();
		try {
			StringBuilder sb = new StringBuilder();

			sb.append("select * from donatario D");

			if (selectPessoaFisica) {
				sb.append(" join pessoa_fisica F on F.email_usuario_pf = D.email_usuario_donatario");
			}

			if (selectUsuario) {
				sb.append(" join usuario U on F.email_usuario_pf = U.email left join cidade C "
						+ "on U.cidade_id = C.id left join endereco E on U.endereco_id = E.id");
			}

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Donatario d = new Donatario(rs.getString("email_usuario_donatario"),
						rs.getDouble("pontuacao_donatario"));

				if (selectPessoaFisica) {
					d.setNome(rs.getString("nome_usuario_pf"));
					d.setCpf(rs.getString("cpf_usuario_pf"));
					d.setRg(rs.getString("rg_usuario_pf"));
					d.setTelefone(rs.getString("telefone_usuario_pf"));

					sql = "select perfil from perfis where email_usuario_pf = ?";

					pstm = conn.prepareStatement(sql);

					pstm.setString(1, d.getEmail());

					ResultSet rsp = pstm.executeQuery();

					List<Perfil> perfis = new ArrayList<Perfil>();

					while (rsp.next()) {
						perfis.add(Perfil.valueOf(rsp.getString("perfil")));
					}

					d.setPerfis(perfis);
				}

				if (selectUsuario) {
					d.setSenha(rs.getString("senha"));
					d.setTipo(Tipo.valueOf(rs.getString("tipo_usuario")));

					if (rs.getInt("endereco_id") != 0)
						d.setEndereco(new Endereco(rs.getInt("endereco_id"), rs.getString("cep"), rs.getInt("numero"),
								rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento")));

					if (rs.getInt("cidade_id") != 0) {
						d.setCidade(new Cidade(rs.getInt("cidade_id"), rs.getString("nome_cidade"),
								rs.getString("uf_cidade")));
					}
				}

				donatarios.add(d);
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

	public Donatario selectByEmail(String email, boolean selectPessoaFisica, boolean selectUsuario) {

		Donatario d = null;
		try {
			StringBuilder sb = new StringBuilder();

			sb.append("select * from donatario D");

			if (selectPessoaFisica) {
				sb.append(" join pessoa_fisica F on F.email_usuario_pf = D.email_usuario_donatario");
			}

			if (selectUsuario) {
				sb.append(" join usuario U on F.email_usuario_pf = U.email left join cidade C "
						+ "on U.cidade_id = C.id left join endereco E on U.endereco_id = E.id");
			}

			sb.append(" where D.email_usuario_donatario = ?");

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, email);

			ResultSet rs = pstm.executeQuery();

			if (!rs.next()) {
				return null;
			}

			d = new Donatario(rs.getString("email_usuario_donatario"), rs.getDouble("pontuacao_donatario"));

			if (selectPessoaFisica) {
				d.setNome(rs.getString("nome_usuario_pf"));
				d.setCpf(rs.getString("cpf_usuario_pf"));
				d.setRg(rs.getString("rg_usuario_pf"));
				d.setTelefone(rs.getString("telefone_usuario_pf"));

				sql = "select perfil from perfis where email_usuario_pf = ?";

				pstm = conn.prepareStatement(sql);

				pstm.setString(1, d.getEmail());

				ResultSet rsp = pstm.executeQuery();

				List<Perfil> perfis = new ArrayList<Perfil>();

				while (rsp.next()) {
					perfis.add(Perfil.valueOf(rsp.getString("perfil")));
				}

				d.setPerfis(perfis);
			}

			if (selectUsuario) {
				d.setSenha(rs.getString("senha"));
				d.setTipo(Tipo.valueOf(rs.getString("tipo_usuario")));

				if (rs.getInt("endereco_id") != 0)
					d.setEndereco(new Endereco(rs.getInt("endereco_id"), rs.getString("cep"), rs.getInt("numero"),
							rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento")));

				if (rs.getInt("cidade_id") != 0) {
					d.setCidade(
							new Cidade(rs.getInt("cidade_id"), rs.getString("nome_cidade"), rs.getString("uf_cidade")));
				}
			}

			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return d;
	}

}
