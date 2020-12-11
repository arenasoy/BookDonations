package usuario.juridica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

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

	public void insert(PessoaJuridica pessoaJuridica) {
		try {
			sql = "insert into pessoa_juridica "
					+ "(email_usuario_pj, razao_social, nome_fantasia, cnpj_usuario_pj, "
					+ "inscricao_estadual) "
					+ "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, pessoaJuridica.getEmail());
			pstm.setString(2, pessoaJuridica.getRazaoSocial());
			pstm.setString(3, pessoaJuridica.getNomeFantasia());
			pstm.setString(4, pessoaJuridica.getCnpj());
			pstm.setString(5, pessoaJuridica.getInscricaoEstadual());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<PessoaJuridica> select() {

		List<PessoaJuridica> pessoas = new ArrayList<PessoaJuridica>();
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
