package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import conexao.Conexao;

/**
 * 
 * Classe ConsultaDAO
 * Essa classe implementa as consultas complexas
 *
 */

public class ConsultaDAO {
	private Connection conn;
	private PreparedStatement pstm;
	private String sql;

	public ConsultaDAO() {

		try {
			conn = Conexao.getInstance();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Double> pontuacaoMediaGrupoTemporada() {
		HashMap<String, Double> medias = null;
		try {
			sql = "select g.nome_grupo, avg(case g.tipo_grupo when 'DOADOR' then (select pontuacao_doador from doador d where d.email_usuario_doador = p.email_usuario_pf) when 'DONATARIO' then (select pontuacao_donatario from donatario do where do.email_usuario_donatario = p.email_usuario_pf) when 'VOLUNTARIO' then (select pontuacao_voluntario from voluntario v where v.email_usuario_voluntario = p.email_usuario_pf) end) as media from grupo g join pertence p on g.nome_grupo = p.nome_grupo and g.tipo_grupo = p.tipo_grupo join temporada t on p.temporada = t.data_inicial_temp join pessoa_fisica pf on p.email_usuario_pf = pf.email_usuario_pf where sysdate between p.temporada and (p.temporada + t.duracao_temp) group by g.nome_grupo, p.temporada";

			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			medias = new HashMap<String, Double>();

			while (rs.next()) {
				medias.put(rs.getString("nome_grupo"), rs.getDouble("media"));
			}

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return medias;
	}

	public HashMap<String, Integer> quantidadeLivrosDoador() {
		HashMap<String, Integer> quantidade = new HashMap<String, Integer>();

		try {
			sql = "select pf.nome_usuario_pf, count(l.titulo) \"QTD_LIVROS_POR_MES\" from pessoa_fisica pf join doador d  on pf.email_usuario_pf = d.email_usuario_doador join livro_doador ld on d.email_usuario_doador = ld.email_usuario_doador join livro l on ld.codigo_barras_ld = l.codigo_barras join doacao doa on doa.codigo_barras_ld = ld.codigo_barras_ld where (extract(month from sysdate) = extract(month from doa.data_horario_doacao)) and (extract(year from sysdate) = extract(year from doa.data_horario_doacao)) group by pf.nome_usuario_pf";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				quantidade.put(rs.getString(1), rs.getInt(2));
			}

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return quantidade;
	}

	public HashMap<String, Integer> quantidadeLivrosVoluntario() {
		HashMap<String, Integer> quantidade = new HashMap<String, Integer>();

		try {
			sql = "select pf.nome_usuario_pf, count(l.titulo) \"QTD_LIVROS_POR_MES\" from pessoa_fisica pf join voluntario v  on pf.email_usuario_pf = v.email_usuario_voluntario join livro_voluntario lv on v.email_usuario_voluntario = lv.email_usuario_voluntario join livro l on lv.codigo_barras_lv = l.codigo_barras join missao m on m.codigo_barras_lv = lv.codigo_barras_lv where (extract(month from sysdate) = extract(month from m.data_horario_missao)) and (extract(year from sysdate) = extract(year from m.data_horario_missao)) group by pf.nome_usuario_pf";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				quantidade.put(rs.getString(1), rs.getInt(2));
			}

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return quantidade;
	}

	public double mediaLivrosDoados() {

		double media = 0.0;
		try {
			sql = "select avg(qtd_livros_por_mes) as media_livros_doados_por_mes from (	select pf.nome_usuario_pf, count(l.titulo) \"QTD_LIVROS_POR_MES\" from pessoa_fisica pf join doador d  	on pf.email_usuario_pf = d.email_usuario_doador 	join livro_doador ld on d.email_usuario_doador = ld.email_usuario_doador 	join livro l on ld.codigo_barras_ld = l.codigo_barras 	join doacao doa on doa.codigo_barras_ld = ld.codigo_barras_ld 	where (extract(month from sysdate) = extract(month from doa.data_horario_doacao)) 	and (extract(year from sysdate) = extract(year from doa.data_horario_doacao)) 	group by pf.nome_usuario_pf )";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			if (rs.next())
				media = rs.getDouble("media_livros_doados_por_mes");

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return media;
	}

	public double mediaLivrosColetados() {

		double media = 0.0;
		try {
			sql = "select avg(qtd_livros_por_mes) as media_livros_doados_por_mes from ( select pf.nome_usuario_pf, count(l.titulo) \"QTD_LIVROS_POR_MES\" from pessoa_fisica pf join voluntario v  on pf.email_usuario_pf = v.email_usuario_voluntario join livro_voluntario lv on v.email_usuario_voluntario = lv.email_usuario_voluntario join livro l on lv.codigo_barras_lv = l.codigo_barras join missao m on m.codigo_barras_lv = lv.codigo_barras_lv where (extract(month from sysdate) = extract(month from m.data_horario_missao)) and (extract(year from sysdate) = extract(year from m.data_horario_missao)) group by pf.nome_usuario_pf)";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			if (rs.next())
				media = rs.getDouble("media_livros_doados_por_mes");

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return media;
	}

	public HashMap<String, Double> mediaDoador() {
		HashMap<String, Double> media = new HashMap<String, Double>();

		try {
			sql = "select d.email_usuario_doador, avg(pontuacao_doacao) as media from doador d join livro_doador ld on d.email_usuario_doador = ld.email_usuario_doador join doacao doa on ld.codigo_barras_ld = doa.codigo_barras_ld where (extract(month from sysdate) = extract(month from doa.data_horario_doacao)) and (extract(year from sysdate) = extract(year from doa.data_horario_doacao)) group by d.email_usuario_doador";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				media.put(rs.getString(1), rs.getDouble(2));
			}

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return media;
	}
	
	public double mediaGeralDoacao() {

		double media = 0.0;
		try {
			sql = "select avg(pontuacao_doacao) as media from doacao doa where (extract(month from sysdate) = extract(month from doa.data_horario_doacao)) and (extract(year from sysdate) = extract(year from doa.data_horario_doacao))";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			if (rs.next())
				media = rs.getDouble("media");

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return media;
	}
	
	public HashMap<String, Double> mediaVoluntario() {
		HashMap<String, Double> media = new HashMap<String, Double>();

		try {
			sql = "select v.email_usuario_voluntario, avg(pontuacao_missao) as media from voluntario v join livro_voluntario lv on v.email_usuario_voluntario = lv.email_usuario_voluntario join missao m on lv.codigo_barras_lv = m.codigo_barras_lv where (extract(month from sysdate) = extract(month from m.data_horario_missao)) and (extract(year from sysdate) = extract(year from m.data_horario_missao)) group by v.email_usuario_voluntario";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				media.put(rs.getString(1), rs.getDouble(2));
			}

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return media;
	}
	
	public double mediaGeralColeta() {

		double media = 0.0;
		try {
			sql = "select avg(pontuacao_missao) as media from missao m where (extract(month from sysdate) = extract(month from m.data_horario_missao)) and (extract(year from sysdate) = extract(year from m.data_horario_missao))";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			if (rs.next())
				media = rs.getDouble("media");

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return media;
	}
	
	public HashMap<String, Double> mediaDonatario() {
		HashMap<String, Double> media = new HashMap<String, Double>();

		try {
			sql = "select don.email_usuario_donatario, avg(q.pontuacao) as media from donatario don join emprestimo e on don.email_usuario_donatario = e.email_usuario_donatario join questao q on (q.email_usuario_donatario = e.email_usuario_donatario 	and q.codigo_barras = e.codigo_barras and q.data_retirada = e.data_retirada) where (extract(month from sysdate) = extract(month from e.data_devolucao)) and (extract(year from sysdate) = extract(year from e.data_devolucao)) group by don.email_usuario_donatario";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				media.put(rs.getString(1), rs.getDouble(2));
			}

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return media;
	}
	
	public double mediaGeralDonatario() {

		double media = 0.0;
		try {
			sql = "select avg(q.pontuacao) as media from emprestimo e join questao q  on (q.email_usuario_donatario = e.email_usuario_donatario 	and q.codigo_barras = e.codigo_barras and q.data_retirada = e.data_retirada) where (extract(month from sysdate) = extract(month from e.data_devolucao)) and (extract(year from sysdate) = extract(year from e.data_devolucao))";
			pstm = conn.prepareStatement(sql);

			ResultSet rs = pstm.executeQuery();

			if (rs.next())
				media = rs.getDouble("media");

			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return media;
	}
	
	public List<ArrayList<String>> classificacaoGrupo(String grupo) {
		
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		try {
			sql = "select pf.nome_usuario_pf,  case p.tipo_grupo when 'DOADOR' then (select pontuacao_doador from doador d where d.email_usuario_doador = p.email_usuario_pf) when 'DONATARIO' then (select pontuacao_donatario from donatario do where do.email_usuario_donatario = p.email_usuario_pf) when 'VOLUNTARIO' then (select pontuacao_voluntario from voluntario v where v.email_usuario_voluntario = p.email_usuario_pf) end as pontuacao from pertence p join temporada t on p.temporada = t.data_inicial_temp join pessoa_fisica pf on p.email_usuario_pf = pf.email_usuario_pf where sysdate between p.temporada and (p.temporada + t.duracao_temp) and p.nome_grupo = ?  order by p.nome_grupo asc, pontuacao desc";
			pstm = conn.prepareStatement(sql);

			pstm.setString(1, grupo);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				ArrayList<String> i = new ArrayList<String>();
				i.add(rs.getString("nome_usuario_pf"));
				i.add(rs.getDouble("pontuacao") + "");
				list.add(i);
			}
			pstm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
}
