create table cidade (
    id int not null,
    nome_cidade varchar2(40) not null,
    uf_cidade char(2) not null,
    constraint pk_cidade primary key (id),
    constraint sk_cidade unique (nome_cidade, uf_cidade)
);

create table endereco (
    id int not null,
    cep varchar2(10),
    numero number,
    rua varchar2(60),
    bairro varchar2(60),
    complemento varchar2(80),
    constraint pk_endereco primary key (id)
);

create table usuario (
    email varchar2(100) not null,
    senha varchar2(20) not null,
    cidade_id int not null,
    endereco_id int,
    tipo_usuario varchar2(15),
    constraint pk_usuario primary key (email),
    constraint fk1_usuario foreign key (cidade_id)
        references cidade (id),
    constraint fk2_usuario foreign key (endereco_id)
        references endereco(id),
    constraint ck_tipo_usuario check (tipo_usuario = 'PESSOA FISICA' or tipo_usuario = 'PESSOA JURIDICA')
);

create table pessoa_fisica (
    email_usuario_pf varchar2(100) not null,
    nome_usuario_pf varchar2(50) not null,
    cpf_usuario_pf varchar2(15) not null,
    rg_usuario_pf varchar2(9),
    telefone_usuario_pf varchar2(15),
    constraint pk_pessoa_fisica primary key (email_usuario_pf),
    constraint sk_pessoa_fisica unique (cpf_usuario_pf),
    constraint fk_pessoa_fisica foreign key (email_usuario_pf)
        references usuario (email)
        on delete cascade
);

create table perfis (
    email_usuario_pf varchar2(100) not null,
    perfil varchar2(15) not null,
    constraint pk_perfis primary key (email_usuario_pf, perfil),
    constraint fk_perfis foreign key (email_usuario_pf)
        references pessoa_fisica (email_usuario_pf)
        on delete cascade
    constraint ck_perfis check (perfil = 'DOADOR' or perfil = 'DONATARIO' or  perfil = 'VOLUNTARIO')
);

create table pessoa_juridica (
    email_usuario_pj varchar2(100) not null,
    razao_social varchar2(50) not null,
    nome_fantasia varchar2(50) not null,
    cnpj_usuario_pj varchar2(18) not null,
    inscricao_estadual varchar2(9),
    constraint pk_pessoa_juridica primary key (email_usuario_pj),
    constraint sk_pessoa_juridica unique (cnpj_usuario_pj),
    constraint fk_pessoa_juridica foreign key (email_usuario_pj)
        references usuario (email)
        on delete cascade
);

create table administrador (
    email_adm varchar2(100) not null,
    senha_adm varchar2(20) not null,
    nome_adm varchar2(50) not null,
    data_registro date not null,
    constraint pk_administrador primary key (email_adm)
);

create table livro (
    codigo_barras number not null,
    autor varchar2(50) not null,
    titulo varchar2(50) not null,
    isbn number not null,
    edicao varchar2(30) not null,
    condicao number not null,
    origem varchar2(15) not null,
    constraint pk_livro primary key (codigo_barra),
    constraint ck_livro check (origem = 'DOADOR' or origem = 'VOLUNTARIO' 
        or origem = 'PESSOA JURIDICA' or origem = 'ADMINISTRADOR')
);

create table bibliotecario (
    cib number not null,
    senha_bibliotecario varchar2(30) not null,
    nome_bibliotecario varchar2(50) not null,
    cidade_id int,
    endereco_id int,
    constraint pk_bibliotecario primary key (cib),
    constraint fk1_bibliotecario foreign key (cidade_id)
        references cidade(id)
        on delete set null,
    constraint fk2_bibliotecario foreign key (endereco_id)
        references endereco(id)
        on delete set null
);
    
create table grupo (
    nome_grupo varchar2(20) not null,
    tipo_grupo varchar2(10) not null,
    pontuacao_minima number not null,
    criado_por varchar2(100) not null,
    constraint pk_grupo primary key (nome_grupo,tipo_grupo),
    constraint fk_grupo foreign key (criado_por)
        references administrador(email_adm),
    constraint ck_grupo check (tipo_grupo = 'DOADOR' or tipo_grupo = 'VOLUNTARIO' 
        or tipo_grupo = 'DONATARIO')
);

create table temporada (
    data_inicial_temp date not null,
    duracao_temp number not null,
    constraint pk_temporada primary key (data_inicial_temp)
);

create table livro_adm (
    codigo_barras_la number not null,
    email_adm varchar2(100) not null,
    constraint pk_livro_adm primary key (codigo_barras_la)
);

create table doador (
    email_usuario_doador varchar2(100) not null,
    pontuacao_doador number not null,
    constraint pk_doador primary key (email_usuario_doador),
    constraint pf_doador foreign key (email_usuario_doador)
        references pessoa_fisica(email_usuario_pf)
);

create table donatario (
    email_usuario_donatario varchar2(100) not null,
    pontuacao_donatario number not null,
    constraint pk_donatario primary key (email_usuario_donatario),
    constraint pf_donatario foreign key (email_usuario_donatario)
        references pessoa_fisica(email_usuario_pf)
);

create table voluntario (
    email_usuario_voluntario varchar2(100) not null,
    pontuacao_voluntario number not null,
    constraint pk_voluntario primary key (email_usuario_voluntario),
    constraint pf_voluntario foreign key (email_usuario_voluntario)
        references pessoa_fisica(email_usuario_pf)
);

create table livro_doador_pj (
    codigo_barras_ldpj number not null,
    email_usuario_pj varchar2(100) not null,
    constraint pk_livro_doador_pj primary key (codigo_barras_ldpj),
    constraint fk1_livro_doador_pj foreign key (codigo_barras_ldpj)
        references livro (codigo_barra)
        on delete cascade,
    constraint fk2_livro_doador_pj foreign key (email_usuario_pj)
        references pessoa_juridica (email_usuario_pj)
        on delete set null
);

create table pertence (
    nome_grupo varchar2(20) not null,
    tipo_grupo varchar2(10) not null,
    temporada date not null,
    email_usuario_pf varchar2(100) not null,
    constraint pk_pertence primary key (nome_grupo, tipo_grupo, temporada, email_usuario_pf),
    constraint fk_pertence_grupo foreign key (nome_grupo, tipo_grupo)
        references grupo (nome_grupo, tipo_grupo)
        on delete cascade,
    constraint fk_pertence_temporada foreign key (temporada)
        references temporada(data_inicial_temp)
        on delete cascade,
    constraint fk_pertence_usuario foreign key (email_usuario_pf)
        references pessoa_fisica (email_usuario_pf)
        on delete cascade
);

create table livro_doador ( 
    codigo_barras_ld number not null,
    email_usuario_doador varchar2(100) not null,
    constraint pk_livro_doador primary key (codigo_barras_ld),
    constraint fk1_livro_doador foreign key (codigo_barras_ld)
        references livro (codigo_barra)
        on delete cascade,
    constraint fk2_livro_doador foreign key (email_usuario_doador)
        references doador (email_usuario_doador)
        on delete cascade
);

create table doacao (
    codigo_barras_ld number not null,
    data_horario_doacao timestamp not null,
    pontuacao_doacao number not null,
    bibliotecario_aprovador number,
    constraint pk_doacao primary key (codigo_barras_ld, data_horario_doacao),
    constraint fk1_doacao foreign key (codigo_barras_ld)
        references livro_doador (codigo_barras_ld)
        on delete cascade,
    constraint fk2_doacao foreign key (bibliotecario_aprovador)
        references bibliotecario(cib)
        on delete set null
);

create table emprestimo (
    email_usuario_donatario varchar2(100) not null,
    codigo_barra number not null,
    data_retirada date not null,
    data_devolucao date not null,
    cib number not null,
    constraint pk_emprestimo 
        primary key (email_usuario_donatario,codigo_barra,data_retirada),
    constraint fk1_emprestimo foreign key (email_usuario_donatario)
        references donatario (email_usuario_donatario)
        on delete set null,
    constraint fk2_emprestimo foreign key (codigo_barra)
        references livro (codigo_barra)
        on delete set null,
    constraint fk3_emprestimo foreign key (cib)
        references bibliotecario (cib)
        on delete set null
);

create table questao (
    email_usuario_donatario varchar2(100) not null,
    codigo_barra number not null,
    data_retirada date not null,
    numero_identificador int not null,
    nivel int not null,
    pergunta varchar2(100) not null,
    solucao varchar2(150) not null,
    pontuacao number not null,
    constraint pk_questao 
        primary key (email_usuario_donatario,codigo_barra,data_retirada,numero_identificador),
    constraint fk_questao 
        foreign key (email_usuario_donatario,codigo_barra,data_retirada)
        references emprestimo (email_usuario_donatario,codigo_barra,data_retirada)
        on delete cascade
);

create table livro_voluntario (
    codigo_barras_lv number not null,
    email_usuario_voluntario varchar2(100) not null,
    constraint pk_livro_voluntario primary key (codigo_barras_lv),
    constraint fk1_livro_voluntario foreign key (codigo_barras_lv)
        references livro (codigo_barra)
        on delete cascade,
    constraint fk2_livro_voluntario foreign key (email_usuario_voluntario)
        references voluntario (email_usuario_voluntario)
        on delete cascade
);

create table missao (
    data_horario_missao timestamp not null,
    codigo_barras_lv number not null,
    pontucao_missao number not null,
    adm_aprovador varchar2(100),
    constraint pk_missao 
        primary key (data_horario_missao,codigo_barras_lv),
    constraint fk1_missao foreign key (codigo_barras_lv)
        references livro_voluntario (codigo_barras_lv)
        on delete cascade,
    constraint fk2_missao foreign key (adm_aprovador)
        references administrador (email_adm)
        on delete set null
);