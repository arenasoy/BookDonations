package emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

public class EmprestimoDAO {

	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public EmprestimoDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Emprestimo emprestimo) {
		try {
			sql = "insert into emprestimo "
					+ "(email_usuario_donatario, codigo_barra, data_retirada, data_devolucao, cib)"
					+ "values (?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, emprestimo.getDonatario().getEmail());
			pstm.setInt(2, emprestimo.getLivro().getCodigoBarras());
			pstm.setDate(3, java.sql.Date.valueOf(emprestimo.getDataRetirada()));
			pstm.setDate(4, java.sql.Date.valueOf(emprestimo.getDataDevolucao()));
			pstm.setInt(4, emprestimo.getBibliotecario().getCib());
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Emprestimo> select() {

		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
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
		return emprestimos;

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
