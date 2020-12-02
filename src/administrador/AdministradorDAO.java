package administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Conexao;

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

	public void insert(Administrador administrador) {
		try {
			sql = "";

			pstm = conn.prepareStatement(sql);
			
			pstm.execute();
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Administrador> select() {

		List<Administrador> administradores = new ArrayList<Administrador>();
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
