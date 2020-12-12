package usuario.juridica;

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
import usuario.fisica.PessoaFisica;

public class PessoaJuridicaDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public PessoaJuridicaDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(PessoaJuridica pessoaJuridica) {
		try {
			sql = "insert into pessoa_juridica " + "(email_usuario_pj, razao_social, nome_fantasia, cnpj_usuario_pj, "
					+ "inscricao_estadual) " + "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, pessoaJuridica.getEmail());
			pstm.setString(2, pessoaJuridica.getRazaoSocial());
			pstm.setString(3, pessoaJuridica.getNomeFantasia());
			pstm.setString(4, pessoaJuridica.getCnpj());
			pstm.setString(5, pessoaJuridica.getInscricaoEstadual());

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				System.out.println("E-mail já cadastrado");
			} else if (e.getErrorCode() == 2291) {
				System.out.println("Erro de chave estrangeira: usuário não cadastrado");
			} else {
				e.printStackTrace();
			}

			return e.getErrorCode();
		}

		return 0;
	}

	public List<PessoaJuridica> select(boolean selectUsuario, boolean selectLivros) {

		List<PessoaJuridica> pessoas = new ArrayList<PessoaJuridica>();
		try {
			StringBuilder sb = new StringBuilder();

			sb.append("select * from pessoa_juridica J");

			if (selectUsuario) {
				sb.append(" join usuario U on J.email_usuario_pj = U.email left join cidade C "
						+ "on U.cidade_id = C.id left join endereco E on U.endereco_id = E.id");
			}

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {

				PessoaJuridica pj = new PessoaJuridica(rs.getString("email_usuario_pj"), rs.getString("razao_social"), rs.getString("nome_fantasia"),
						rs.getString("cnpj_usuario_pj"), rs.getString("inscricao_estadual"));

				if (selectUsuario) {
					
					pj.setSenha(rs.getString("senha"));
					pj.setTipo(Tipo.valueOf(rs.getString("tipo_usuario")));
					
					if (rs.getInt("endereco_id") != 0)
						pj.setEndereco(new Endereco(rs.getInt("endereco_id"), rs.getString("cep"), rs.getInt("numero"),
								rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento")));

					if (rs.getInt("cidade_id") != 0) {
						pj.setCidade(new Cidade(rs.getInt("cidade_id"), rs.getString("nome_cidade"), rs.getString("uf_cidade")));
					}

				} 
				
				if (selectLivros) {
					sql = "select * from livro_doador_pj join livro on codigo_barras_ldpj = codigo_barras where email_usuario_pj = ?";

					pstm = conn.prepareStatement(sql);

					pstm.setString(1, pj.getEmail());

					ResultSet rsl = pstm.executeQuery();

					List<Livro> livros = new ArrayList<Livro>();

					while (rsl.next()) {
						livros.add(new Livro(rs.getInt("codigo_barras"), rs.getString("autor"), rs.getString("titulo"),
								rs.getInt("isbn"), rs.getString("edicao"), rs.getInt("condicao"),
								rs.getString("origem")));
					}

					pj.setLivros(livros);
				}
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoas;

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
