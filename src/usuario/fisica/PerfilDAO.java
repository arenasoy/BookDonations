package usuario.fisica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import conexao.Conexao;

public class PerfilDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public PerfilDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insert(PessoaFisica pessoaFisica) {

		try {

			for (Perfil perfil : pessoaFisica.getPerfis()) {

				sql = "insert into perfis (email_usuario_pf, perfil) values (?, ?)";

				pstm = conn.prepareStatement(sql);

				pstm.setString(1, pessoaFisica.getEmail());
				pstm.setString(2, perfil.toString());

				pstm.execute();
				pstm.close();
			}

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


}
