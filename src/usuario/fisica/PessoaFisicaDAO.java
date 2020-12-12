package usuario.fisica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cidade.Cidade;
import conexao.Conexao;
import endereco.Endereco;
import usuario.Tipo;

public class PessoaFisicaDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public PessoaFisicaDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(PessoaFisica pessoaFisica) {
		try {
			sql = "insert into pessoa_fisica " + "(email_usuario_pf, nome_usuario_pf, cpf_usuario_pf, rg_usuario_pf, "
					+ "telefone_usuario_pf)" + "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, pessoaFisica.getEmail());
			pstm.setString(2, pessoaFisica.getNome());
			pstm.setString(3, pessoaFisica.getCpf());
			pstm.setString(4, pessoaFisica.getRg());
			pstm.setString(5, pessoaFisica.getTelefone());

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

	public List<PessoaFisica> select(boolean selectUsuario, boolean selectPerfis) {

		List<PessoaFisica> pessoas = new ArrayList<PessoaFisica>();
		try {

			StringBuilder sb = new StringBuilder();

			sb.append("select * from pessoa_fisica F");

			if (selectUsuario) {
				sb.append(" join usuario U on F.email_usuario_pf = U.email left join cidade C "
						+ "on U.cidade_id = C.id left join endereco E on U.endereco_id = E.id");
			}

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				PessoaFisica pf = null;

				if (selectUsuario) {
					pf = new PessoaFisica(rs.getString("email_usuario_pf"), rs.getString("senha"),
							Tipo.valueOf(rs.getString("tipo_usuario")), rs.getString("nome_usuario_pf"),
							rs.getString("cpf_usuario_pf"), rs.getString("rg_usuario_pf"),
							rs.getString("telefone_usuario_pf"));

					if (rs.getInt("endereco_id") != 0)
						pf.setEndereco(new Endereco(rs.getInt("endereco_id"), rs.getString("cep"), rs.getInt("numero"),
								rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento")));

					if (rs.getInt("cidade_id") != 0) {
						pf.setCidade(new Cidade(rs.getInt("cidade_id"), rs.getString("nome_cidade"), rs.getString("uf_cidade")));
					}

				} else {
					pf = new PessoaFisica(rs.getString("email_usuario_pf"), rs.getString("nome_usuario_pf"),
							rs.getString("cpf_usuario_pf"), rs.getString("rg_usuario_pf"),
							rs.getString("telefone_usuario_pf"));
				}
				
				if (selectPerfis) {
					sql = "select perfil from perfis where email_usuario_pf = ?";
					
					pstm = conn.prepareStatement(sql);
					
					pstm.setString(1, pf.getEmail());
					
					ResultSet rsp = pstm.executeQuery();
					
					List<Perfil> perfis = new ArrayList<Perfil>();
					
					while (rsp.next()) {
						perfis.add(Perfil.valueOf(rsp.getString("perfil")));
					}
					
					pf.setPerfis(perfis);
				}

				pessoas.add(pf);
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
