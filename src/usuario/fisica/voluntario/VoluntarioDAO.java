package usuario.fisica.voluntario;

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
import livro.Origem;
import usuario.Tipo;
import usuario.fisica.Perfil;
import usuario.fisica.PessoaFisica;

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

	public int insert(Voluntario voluntario) {
		try {
			sql = "insert into voluntario (email_usuario_voluntario, pontuacao_voluntario) " + "values (?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, voluntario.getEmail());
			pstm.setDouble(2, voluntario.getPontuacao());

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

	public List<Voluntario> select(boolean selectPessoaFisica, boolean selectUsuario, boolean selectLivros) {

		List<Voluntario> voluntarios = new ArrayList<Voluntario>();
		try {
			StringBuilder sb = new StringBuilder();

			sb.append("select * from voluntario V");

			if (selectPessoaFisica) {
				sb.append(" join pessoa_fisica F on F.email_usuario_pf = V.email_usuario_voluntario");
			}

			if (selectUsuario) {
				sb.append(" join usuario U on F.email_usuario_pf = U.email left join cidade C "
						+ "on U.cidade_id = C.id left join endereco E on U.endereco_id = E.id");
			}

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Voluntario v = new Voluntario(rs.getString("email_usuario_voluntario"),
						rs.getDouble("pontuacao_voluntario"));

				if (selectPessoaFisica) {
					v.setNome(rs.getString("nome_usuario_pf"));
					v.setCpf(rs.getString("cpf_usuario_pf"));
					v.setRg(rs.getString("rg_usuario_pf"));
					v.setTelefone(rs.getString("teelefone_usuario_pf"));

					sql = "select perfil from perfis where email_usuario_pf = ?";

					pstm = conn.prepareStatement(sql);

					pstm.setString(1, v.getEmail());

					ResultSet rsp = pstm.executeQuery();

					List<Perfil> perfis = new ArrayList<Perfil>();

					while (rsp.next()) {
						perfis.add(Perfil.valueOf(rsp.getString("perfil")));
					}

					v.setPerfis(perfis);
				}

				if (selectUsuario) {
					v.setSenha(rs.getString("senha"));
					v.setTipo(Tipo.valueOf(rs.getString("tipo_usuario")));

					if (rs.getInt("endereco_id") != 0)
						v.setEndereco(new Endereco(rs.getInt(8), rs.getString("cep"), rs.getInt("numero"),
								rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento")));

					if (rs.getInt("cidade_id") != 0) {
						v.setCidade(new Cidade(rs.getInt(4), rs.getString("nome_cidade"), rs.getString("uf_cidade")));
					}
				}

				if (selectLivros) {
					sql = "select * from livro_voluntario join livro on codigo_barras_lv = codigo_barras where email_usuario_voluntario = ?";

					pstm = conn.prepareStatement(sql);

					pstm.setString(1, v.getEmail());

					ResultSet rsl = pstm.executeQuery();

					List<Livro> livros = new ArrayList<Livro>();

					while (rsl.next()) {
						livros.add(new Livro(rs.getInt("codigo_barras"), rs.getString("autor"), rs.getString("titulo"),
								rs.getInt("isbn"), rs.getString("edicao"), rs.getInt("condicao"),
								Origem.valueOf(rs.getString("origem")), true));
					}

					v.setLivros(livros);
				}

				voluntarios.add(v);
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
