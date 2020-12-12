package usuario.fisica.doador;

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
import usuario.fisica.voluntario.Voluntario;

public class DoadorDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public DoadorDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(Doador doador) {
		try {
			sql = "insert into doador (email_usuario_doador, pontuacao_doador) " + "values (?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, doador.getEmail());
			pstm.setDouble(2, doador.getPontuacao());

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

	public List<Doador> select(boolean selectPessoaFisica, boolean selectUsuario, boolean selectLivros) {

		List<Doador> doadores = new ArrayList<Doador>();
		try {
			StringBuilder sb = new StringBuilder();

			sb.append("select * from doador D");

			if (selectPessoaFisica) {
				sb.append(" join pessoa_fisica F on F.email_usuario_pf = D.email_usuario_doador");
			}

			if (selectUsuario) {
				sb.append(" join usuario U on F.email_usuario_pf = U.email left join cidade C "
						+ "on U.cidade_id = C.id left join endereco E on U.endereco_id = E.id");
			}

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Doador d = new Doador(rs.getString("email_usuario_doador"),
						rs.getDouble("pontuacao_doador"));

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
						d.setCidade(new Cidade(rs.getInt("cidade_id"), rs.getString("nome_cidade"), rs.getString("uf_cidade")));
					}
				}

				if (selectLivros) {
					sql = "select * from livro_doador join livro on codigo_barras_lv = codigo_barras where email_usuario_doador = ?";

					pstm = conn.prepareStatement(sql);

					pstm.setString(1, d.getEmail());

					ResultSet rsl = pstm.executeQuery();

					List<Livro> livros = new ArrayList<Livro>();

					while (rsl.next()) {
						livros.add(new Livro(rs.getInt("codigo_barras"), rs.getString("autor"), rs.getString("titulo"),
								rs.getInt("isbn"), rs.getString("edicao"), rs.getInt("condicao"),
								Origem.valueOf(rs.getString("origem")), true));
					}

					d.setLivros(livros);
				}

				doadores.add(d);
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doadores;

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
