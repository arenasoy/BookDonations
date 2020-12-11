package usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

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

	public void insert(Usuario usuario) {
		try {
			sql = "insert into usuario (email, senha, cidade_id, endereco_id, tipo_usuario) "
					+ "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, usuario.getEmail());
			pstm.setString(2, usuario.getSenha());
			pstm.setInt(3, usuario.getCidade().getId());
			pstm.setInt(4, usuario.getEndereco().getId());
			pstm.setString(5, usuario.getTipo());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Usuario> select() {

		List<Usuario> usuarios = new ArrayList<Usuario>();
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
		return usuarios;

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
