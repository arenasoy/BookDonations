package usuario.fisica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

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

	public void insert(PessoaFisica pessoaFisica) {
		try {
			sql = "insert into pessoa_fisica "
					+ "(email_usuario_pf, nome_usuario_pf, cpf_usuario_pf, rg_usuario_pf, "
					+ "telefone_usuario_pf)"
					+ "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, pessoaFisica.getEmail());
			pstm.setString(2, pessoaFisica.getNome());
			pstm.setString(3, pessoaFisica.getCpf());
			pstm.setString(4, pessoaFisica.getRg());
			pstm.setString(5, pessoaFisica.getTelefone());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<PessoaFisica> select() {

		List<PessoaFisica> pessoas = new ArrayList<PessoaFisica>();
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
