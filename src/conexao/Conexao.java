package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static Connection conn;
	private static String user = "";
	private static String password = "";
	
	private Conexao() {
	}

	public synchronized static Connection getInstance() throws Exception {
		if (conn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl", user,
						password);
			} catch (Exception e) {
				throw e;
			}
		}
		return conn;
	}

	public synchronized static void fecharConnexao() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		Conexao.user = user;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Conexao.password = password;
	}

	
}
