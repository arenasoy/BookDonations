package usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bibliotecario.Bibliotecario;
import cidade.Cidade;
import conexao.Conexao;
import endereco.Endereco;

public class UsuarioDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public UsuarioDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(Usuario usuario) {
		try {
			sql = "insert into usuario (email, senha, cidade_id, endereco_id, tipo_usuario) "
					+ "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, usuario.getEmail());
			pstm.setString(2, usuario.getSenha());
			pstm.setInt(3, usuario.getCidade().getId());
			pstm.setInt(4, usuario.getEndereco().getId());
			pstm.setString(5, usuario.getTipo().toString());

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {

			if (e.getErrorCode() == 1) {
				System.out.println("E-mail já cadastrado");
			} else if (e.getErrorCode() == 2291) {
				System.out.println("Erro de chave estrangeira: Cidade ou endereço inexistente");
			} else {
				e.printStackTrace();
			}

			return e.getErrorCode();
		}

		return 0;
	}

	public List<Usuario> select(boolean selectEndereco, boolean selectCidade) {

		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select * from usuario U");

			if (selectEndereco) {
				sb.append(" left join endereco E on U.endereco_id = E.id");
			}

			if (selectCidade) {
				sb.append(" left join cidade C on U.cidade_id = C.id");
			}

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Usuario usuario = new Usuario(rs.getString("email"), rs.getString("senha"),
						Tipo.valueOf(rs.getString("tipo_usuario")));

				if (selectEndereco && rs.getInt("endereco_id") != 0) {
					usuario.setEndereco(new Endereco(rs.getInt("endereco_id"), rs.getString("cep"), rs.getInt("numero"),
							rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento")));
				}

				if (selectCidade && rs.getInt("cidade_id") != 0) {
					usuario.setCidade(new Cidade(rs.getInt("cidade_id"), rs.getString("nome_cidade"), rs.getString("uf_cidade")));

				}

				usuarios.add(usuario);
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}

	public Usuario selectByEmail(String email, boolean selectCidade, boolean selectEndereco) {

		Usuario u = null;

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from usuario U");

			if (selectEndereco) {
				sb.append(" left join endereco E on U.endereco_id = E.id");
			}

			if (selectCidade) {
				sb.append(" left join cidade C on U.cidade_id = C.id");
			}
			
			sb.append(" where U.email = ?");

			sql = sb.toString();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, email);

			ResultSet rs = pstm.executeQuery();

			if (!rs.next()) {
				return null;
			}

			u = new Usuario(rs.getString("email"), rs.getString("senha"),
					Tipo.valueOf(rs.getString("tipo_usuario")));

			if (selectEndereco && rs.getInt("endereco_id") != 0) {
				u.setEndereco(new Endereco(rs.getInt("endereco_id"), rs.getString("cep"), rs.getInt("numero"),
						rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento")));
			}

			if (selectCidade && rs.getInt("cidade_id") != 0) {
				u.setCidade(new Cidade(rs.getInt("cidade_id"), rs.getString("nome_cidade"), rs.getString("uf_cidade")));

			}

			pstm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}

}
