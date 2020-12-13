-- Busca média de pontuação por Grupo (Na temporada atual)
-- Será utilizado para realizar, na aplicação, uma comparação entre a pontuação do usuário (no respectivo grupo)
-- e a média do grupo na temporada atual. Isso dará um resultado indicando se o usuário está acima da média
-- (obs: a consulta da pontuação do usuário é uma consulta a parte e não tem nada haver com essa, somente na comparação
-- feita na aplicação).
select g.nome_grupo, avg(
		case g.tipo_grupo
		when 'DOADOR' then (select pontuacao_doador from doador d where d.email_usuario_doador = p.email_usuario_pf)
		when 'DONATARIO' then (select pontuacao_donatario from donatario do where do.email_usuario_donatario = p.email_usuario_pf)
		when 'VOLUNTARIO' then (select pontuacao_voluntario from voluntario v where v.email_usuario_voluntario = p.email_usuario_pf)
		end
	) as media
	from grupo g join pertence p
	on g.nome_grupo = p.nome_grupo and g.tipo_grupo = p.tipo_grupo 
    join temporada t
    on p.temporada = t.data_inicial_temp
    join pessoa_fisica pf
    on p.email_usuario_pf = pf.email_usuario_pf
	where sysdate between p.temporada and (p.temporada + t.duracao_temp)
	group by g.nome_grupo, p.temporada;

-- Busca por Quantidade de Livros que um doador doou no mês atual
-- 
select pf.nome_usuario_pf, count(l.titulo) "QTD_LIVROS_POR_MES" from pessoa_fisica pf join doador d 
	on pf.email_usuario_pf = d.email_usuario_doador
	join livro_doador ld on d.email_usuario_doador = ld.email_usuario_doador
	join livro l on ld.codigo_barras_ld = l.codigo_barras
	join doacao doa on doa.codigo_barras_ld = ld.codigo_barras_ld
	where (extract(month from sysdate) = extract(month from doa.data_horario_doacao))
	and (extract(year from sysdate) = extract(year from doa.data_horario_doacao))
	group by pf.nome_usuario_pf;

-- Busca por Quantidade de Livros que um voluntário coletou no mês atual
--
select pf.nome_usuario_pf, count(l.titulo) "QTD_LIVROS_POR_MES" from pessoa_fisica pf join voluntario v 
	on pf.email_usuario_pf = v.email_usuario_voluntario
	join livro_voluntario lv on v.email_usuario_voluntario = lv.email_usuario_voluntario
	join livro l on lv.codigo_barras_lv = l.codigo_barras
	join missao m on m.codigo_barras_lv = lv.codigo_barras_lv
	where (extract(month from sysdate) = extract(month from m.data_horario_missao))
	and (extract(year from sysdate) = extract(year from m.data_horario_missao))
	group by pf.nome_usuario_pf;


-- Busca a média de livros doados no mês atual
select avg(qtd_livros_por_mes) as media_livros_doados_por_mes from
	(	select pf.nome_usuario_pf, count(l.titulo) "QTD_LIVROS_POR_MES" from pessoa_fisica pf join doador d 
		on pf.email_usuario_pf = d.email_usuario_doador
		join livro_doador ld on d.email_usuario_doador = ld.email_usuario_doador
		join livro l on ld.codigo_barras_ld = l.codigo_barras
		join doacao doa on doa.codigo_barras_ld = ld.codigo_barras_ld
		where (extract(month from sysdate) = extract(month from doa.data_horario_doacao))
		and (extract(year from sysdate) = extract(year from doa.data_horario_doacao))
		group by pf.nome_usuario_pf
	);

-- Busca a média de livros coletados no mês atual
select avg(qtd_livros_por_mes) as media_livros_doados_por_mes from (
	select pf.nome_usuario_pf, count(l.titulo) "QTD_LIVROS_POR_MES" from pessoa_fisica pf join voluntario v 
	on pf.email_usuario_pf = v.email_usuario_voluntario
	join livro_voluntario lv on v.email_usuario_voluntario = lv.email_usuario_voluntario
	join livro l on lv.codigo_barras_lv = l.codigo_barras
	join missao m on m.codigo_barras_lv = lv.codigo_barras_lv
	where (extract(month from sysdate) = extract(month from m.data_horario_missao))
	and (extract(year from sysdate) = extract(year from m.data_horario_missao))
	group by pf.nome_usuario_pf
);

-- Busca a média de pontuação de um doador por doações realizadas no mês atual
select d.email_usuario_doador, avg(pontuacao_doacao) as media from doador d join livro_doador ld
	on d.email_usuario_doador = ld.email_usuario_doador
	join doacao doa on ld.codigo_barras_ld = doa.codigo_barras_ld
	where (extract(month from sysdate) = extract(month from doa.data_horario_doacao))
	and (extract(year from sysdate) = extract(year from doa.data_horario_doacao))
	group by d.email_usuario_doador;

-- Busca média geral de pontuação de doação no mês atual
select avg(pontuacao_doacao) as media from doacao doa
	where (extract(month from sysdate) = extract(month from doa.data_horario_doacao))
	and (extract(year from sysdate) = extract(year from doa.data_horario_doacao));

-- Busca a média de pontuação de um voluntário por coletas realizadas no mês atual
select v.email_usuario_voluntario, avg(pontuacao_missao) as media from voluntario v join livro_voluntario lv
	on v.email_usuario_voluntario = lv.email_usuario_voluntario
	join missao m on lv.codigo_barras_lv = m.codigo_barras_lv
	where (extract(month from sysdate) = extract(month from m.data_horario_missao))
	and (extract(year from sysdate) = extract(year from m.data_horario_missao))
	group by v.email_usuario_voluntario;
-- where (extract(month from to_date('01/11/2020', 'dd/mm/yyyy')) = extract(month from doa.data_horario_doacao))
-- and (extract(year from to_date('01/11/2020', 'dd/mm/yyyy')) = extract(year from doa.data_horario_doacao))

-- Busca média geral de pontuação de doação no mês atual
select avg(pontuacao_missao) as media from missao m
	where (extract(month from sysdate) = extract(month from m.data_horario_missao))
	and (extract(year from sysdate) = extract(year from m.data_horario_missao));

-- Busca a média de pontuação de um donatário por pontuações conquistadas no mês atual
select don.email_usuario_donatario, avg(q.pontuacao) as media from donatario don join emprestimo e
	on don.email_usuario_donatario = e.email_usuario_donatario
	join questao q on (q.email_usuario_donatario = e.email_usuario_donatario
		and q.codigo_barras = e.codigo_barras and q.data_retirada = e.data_retirada)
	group by don.email_usuario_donatario;

-- Busca média geral de pontuação de questões no mês atual
select avg(q.pontuacao) as media from emprestimo e join questao q 
	on (q.email_usuario_donatario = e.email_usuario_donatario
		and q.codigo_barras = e.codigo_barras and q.data_retirada = e.data_retirada)
	where (extract(month from sysdate) = extract(month from e.data_devolucao))
	and (extract(year from sysdate) = extract(year from e.data_devolucao));

