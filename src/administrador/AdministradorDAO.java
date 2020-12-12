package administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;
import livro.Livro;
import livro.Origem;

public class AdministradorDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public AdministradorDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(Administrador administrador) {
		try {
			sql = "insert into administrador (email_adm, senha_adm, nome_adm, data_registro)" + "values (?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, administrador.getEmail());
			pstm.setString(2, administrador.getSenha());
			pstm.setString(3, administrador.getNome());
			pstm.setDate(4, java.sql.Date.valueOf(administrador.getDataRegistro()));

			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				System.out.println("E-mail já cadastrado");
			}
			return e.getErrorCode();
		}
		
		return 0;

	}

	//TODO select com livros
	public List<Administrador> select(boolean selectLivros) {

		List<Administrador> administradores = new ArrayList<Administrador>();
		try {
			sql = "select * from administrador";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Administrador administrador = new Administrador(
						rs.getString("email_adm"),
						rs.getString("senha_adm"),
						rs.getString("nome_adm"),
						rs.getDate("data_registro").toLocalDate());
				
				if (selectLivros) {
					sql = "select * from livro_adm join livro on codigo_barras_la = codigo_barras where email_adm = ?";

					pstm = conn.prepareStatement(sql);

					pstm.setString(1, administrador.getEmail());

					ResultSet rsl = pstm.executeQuery();

					List<Livro> livros = new ArrayList<Livro>();

					while (rsl.next()) {
						livros.add(new Livro(rs.getInt("codigo_barras"), rs.getString("autor"), rs.getString("titulo"),
								rs.getInt("isbn"), rs.getString("edicao"), rs.getInt("condicao"),
								Origem.valueOf(rs.getString("origem")), true));
					}

					administrador.setLivros(livros);
				}
				
				administradores.add(administrador);
			}
			pstm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return administradores;

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
