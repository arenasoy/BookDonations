package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import administrador.Administrador;
import bibliotecario.Bibliotecario;
import conexao.Conexao;
import console.Console;
import grupo.Grupo;
import grupo.PessoaGrupoTemporada;
import livro.Livro;
import temporada.Temporada;
import usuario.Usuario;
import usuario.fisica.PessoaFisica;
import usuario.fisica.doador.Doador;
import usuario.fisica.donatario.Donatario;
import usuario.fisica.voluntario.Voluntario;
import usuario.juridica.PessoaJuridica;

/**
 * Classe Main
 * Essa classe inicializa o programa
 * Cada uma das funções é referente a um sub-menu
 * 
 * O projeto esta dividido em diversos pacotes
 * Cada pacote referente a uma tabela possui pelo menos duas classes
 * - Uma define a entidade e métodos
 * - Uma faz as conexoes com a base de dados (classes com final DAO)
 * 
 *
 */
public class Main {

	private Console console;
	private DAO dao;

	private final int EXIT = 0;
	private final int ADMINISTRADOR = 1;
	private final int USUARIO = 2;
	private final int BIBLIOTECARIO = 3;
	private final int LIVRO = 4;
	private int option = EXIT;

	public static void main(String[] args) {
		new Main().execute();
	}

	private void execute() {

		System.out.println("=================================================");
		System.out.println("Sistema de doação de livros");
		System.out.println("=================================================");

		System.out.println(
				"Para começar a utilizar, é preciso inserir as credenciais da base de dados que possui as tabelas criadas pelo arquivo esquema.sql");

		console = Console.getInstance();
		while (!setConnection())
			;
		System.out.println("\nConexão realizada com sucesso!");

		dao = new DAO();

		showMenu();

		System.out.println("Até mais!");
	}

	private boolean setConnection() {

		Conexao.setUser(console.readString("User: "));
		Conexao.setPassword(console.readLine("Password:"));
		
		try {
			Conexao.getInstance();
		} catch (Exception e) {
			System.out.println("\nOpa, algo deu errado! Por favor, entre suas credenciais novamente!");
			return false;
		}

		return true;
	}

	private void showMenu() {

		do {
			System.out.println("======================================================");
			System.out.println("                       MENU INICIAL                   ");
			System.out.println("======================================================");
			System.out.println("Para qual área deseja ir?");
			System.out.println("1 - Administradores");
			System.out.println("2 - Usuários");
			System.out.println("3 - Bibliotecários");
			System.out.println("4 - Livros");
			System.out.println("5 - Grupos e temporadas");
			System.out.println("6 - Consultas complexas");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case ADMINISTRADOR:
				showMenuAdministrador();
				break;
			case USUARIO:
				showMenuUsuario();
				break;
			case BIBLIOTECARIO:
				showMenuBibliotecario();
				break;
			case LIVRO:
				showMenuLivro();
				break;
			case 5:
				showMenuGrupoTemporadaQuestao();
				break;
			case 6:
				showConsultasComplexas();
				break;
			}

		} while (option != EXIT);

	}

	private void showConsultasComplexas() {
		do {

			System.out.println("======================================================");
			System.out.println("               MENU DE CONSULTAS COMPLEXAS            ");
			System.out.println("======================================================");
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Busca média de pontuação por grupo na temporada atual)");
			System.out.println("2 - Busca por quantidade de livros que um doador doou no mês atual");
			System.out.println("3 - Busca por quantidade de livros que um voluntário coletou no mês atual");
			System.out.println("4 - Busca a média de livros doados no mês atual");
			System.out.println("5 - Busca a média de livros coletados no mês atual");
			System.out.println("6 - Busca a média de pontuação de um doador por doações realizadas no mês atual e compara com a média geral");
			System.out.println("7 - Busca a média de pontuação de um voluntário por coletas realizadas no mês atual e compara com a média geral");
			System.out.println("8 - Busca a média de pontuação de um donatário por pontuações conquistadas no mês atual e compara com a média geral");
			System.out.println("9 - Mostra a classificação geral dentro de um grupo");
			System.out.println("10 - Busca média de pontuação por temporada");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				HashMap<String, Double> medias = dao.getConsultaDAO().pontuacaoMediaGrupoTemporada();

				if (medias.size() == 0) {
					System.out.println("Não há dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Grupo: média");
				for (String s : medias.keySet()) {
					System.out.println(s + ": " + medias.get(s));
				}
				break;
			case 2:
				HashMap<String, Integer> quantidade = dao.getConsultaDAO().quantidadeLivrosDoador();

				if (quantidade.size() == 0) {
					System.out.println("Não há dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Nome: quantidade");
				for (String s : quantidade.keySet()) {
					System.out.println(s + ": " + quantidade.get(s));
				}
				break;
			case 3:
				quantidade = dao.getConsultaDAO().quantidadeLivrosVoluntario();

				if (quantidade.size() == 0) {
					System.out.println("Não há dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Nome: quantidade");
				for (String s : quantidade.keySet()) {
					System.out.println(s + ": " + quantidade.get(s));
				}
				break;
			case 4:
				double media = dao.getConsultaDAO().mediaLivrosDoados();
				System.out.println("Média: " + media);
				break;
			case 5:
				media = dao.getConsultaDAO().mediaLivrosColetados();
				System.out.println("Média: " + media);
				break;
			case 6:
				medias = dao.getConsultaDAO().mediaDoador();
				media = dao.getConsultaDAO().mediaGeralDoacao();
				if (medias.size() == 0) {
					System.out.println("Não há dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Média geral: " + media);
				System.out.println("Doador: média - situação");
				for (String s : medias.keySet()) {
					System.out.print(s + ": " + medias.get(s) + " - ");
					if (medias.get(s) == media) System.out.println("Na média");
					else if (medias.get(s) > media) System.out.println("Acima da média");
					else System.out.println("Abaixo da média");
				}
				break;
			case 7:
				medias = dao.getConsultaDAO().mediaVoluntario();
				media = dao.getConsultaDAO().mediaGeralColeta();
				if (medias.size() == 0) {
					System.out.println("Não há dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Média geral: " + media);
				System.out.println("Voluntário: média - situação");
				for (String s : medias.keySet()) {
					System.out.print(s + ": " + medias.get(s) + " - ");
					if (medias.get(s) == media) System.out.println("Na média");
					else if (medias.get(s) > media) System.out.println("Acima da média");
					else System.out.println("Abaixo da média");
				}
				break;
			case 8:
				medias = dao.getConsultaDAO().mediaDonatario();
				media = dao.getConsultaDAO().mediaGeralDonatario();
				if (medias.size() == 0) {
					System.out.println("Não há dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Média geral: " + media);
				System.out.println("Donatário: média - situação");
				for (String s : medias.keySet()) {
					System.out.print(s + ": " + medias.get(s) + " - ");
					if (medias.get(s) == media) System.out.println("Na média");
					else if (medias.get(s) > media) System.out.println("Acima da média");
					else System.out.println("Abaixo da média");
				}
				break;
			case 9: 
				String grupo = console.readLine("Grupo desejado: ");
				
				List<ArrayList<String>> list = dao.getConsultaDAO().classificacaoGrupo(grupo);
				
				if (list == null || list.size() == 0) {
					System.out.println("Não há dados suficientes para realizar essa consulta");
					break;
				}
				
				System.out.println("Classificação no grupo " + grupo);
				for (ArrayList<String> arrayList : list) {
					System.out.println(arrayList.get(0) + " - " + arrayList.get(1));
				}
				break;
			case 10:
				HashMap<LocalDate, Double> m = dao.getConsultaDAO().mediaPontuacaoTemporada();

				if (m.size() == 0) {
					System.out.println("Não há dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Temporada: média");
				for (LocalDate d: m.keySet()) {
					System.out.println(d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ": " + m.get(d));
				}
				break;
			}

		} while (option != EXIT);

		option = 6;

	}

	private void showMenuGrupoTemporadaQuestao() {
		do {
			System.out.println("======================================================");
			System.out.println("            MENU GRUPO, TEMPORADAS E QUESTOES         ");
			System.out.println("======================================================");
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Listar pessoas por grupo por temporada");
			System.out.println("2 - Cadastrar grupo");
			System.out.println("3 - Listar grupos");
			System.out.println("4 - Cadastrar temporada");
			System.out.println("5 - Listar temporadas");
			System.out.println("6 - Cadastrar uma pessoa em um grupo em uma temporada");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				int op = 0;

				boolean selectGrupo = false;
				boolean selectTemporada = false;
				boolean selectPessoaFisica = false;

				do {
					op = console.readInt("Gostaria de saber as informações de grupo?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectGrupo = true;
					} else if (op == 2) {
						selectGrupo = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt("Gostaria de saber as informações de temporada?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectTemporada = true;
					} else if (op == 2) {
						selectTemporada = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informações de pessoa física?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<PessoaGrupoTemporada> pgts = dao.getPessoaGrupoTemporadaDAO().select(selectGrupo, selectTemporada,
						selectPessoaFisica);

				if (pgts.size() == 0) {
					System.out.println("Não há relações cadastradas!");
					break;
				}

				for (PessoaGrupoTemporada pgt : pgts) {
					System.out.println("=================================================");
					pgt.print();
				}

				break;

			case 2:
				int error = 0;
				Grupo grupo = Forms.getGrupo();
				error = dao.getAdministradorDAO().insert(grupo.getAdministrador());
				if (error != 0)
					break;
				error = dao.getGrupoDAO().insert(grupo);
				if (error != 0)
					break;
				System.out.println("Grupo cadastrado com sucesso!");
				break;
			case 3:
				op = 0;

				boolean selectAdministrador = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de administrador do grupo?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectAdministrador = true;
					} else if (op == 2) {
						selectAdministrador = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Grupo> grupos = dao.getGrupoDAO().select(selectAdministrador);

				if (grupos == null || grupos.size() == 0) {
					System.out.println("Não há grupos cadastrados");
					break;
				}

				for (Grupo g : grupos) {
					System.out.println("=============================================");
					g.print();
				}
				break;
			case 4:
				error = 0;
				Temporada t = Forms.getTemporada();
				error = dao.getTemporadaDAO().insert(t);
				if (error != 0)
					break;
				System.out.println("Temporada cadastrada com sucesso!");
				break;
			case 5:
				List<Temporada> temporadas = dao.getTemporadaDAO().select();
				if (temporadas == null || temporadas.size() == 0) {
					System.out.println("Não há temporadas cadastradas");
					break;
				}

				for (Temporada temporada : temporadas) {
					System.out.println("============================================");
					temporada.print();
				}
				break;
			case 6:
				error = 0;
				PessoaGrupoTemporada pgt = Forms.getPessoaGrupoTemporada();
				error = dao.getPessoaGrupoTemporadaDAO().insert(pgt);
				if (error != 0)
					break;
				System.out.println("Relação cadastrada com sucesso!");
				break;
			}
		} while (option != EXIT);

		option = 5;
	}

	private void showMenuAdministrador() {
		do {
			System.out.println("======================================================");
			System.out.println("                    MENU ADMINISTRADOR                ");
			System.out.println("======================================================");
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Cadastrar administrador");
			System.out.println("2 - Listar todos os administradores");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				int error = 0;
				error = dao.getAdministradorDAO().insert(Forms.getAdministrador());
				if (error == 0)
					break;
				System.out.println("Administrador cadastrado com sucesso!");
				break;
			case 2:
				do {
					option = console.readInt(
							"Deseja listar os livros cadastrados pelos administradores também?\n1 - Sim\n2 - Não\n0 - Sair");
					List<Administrador> administradores = null;
					if (option == 1) {
						administradores = dao.getAdministradorDAO().select(true);
					} else if (option == 2) {
						administradores = dao.getAdministradorDAO().select(false);
					} else if (option != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

					if (administradores.size() == 0) {
						System.out.println("Não há administradores cadastrados.");
					}

					for (Administrador administrador : administradores) {
						administrador.print();
					}
				} while (option != 1 && option != 2 && option != 0);
				option = ADMINISTRADOR;
				break;
			}

		} while (option != EXIT);

		option = ADMINISTRADOR;
	}

	private void showMenuUsuario() {
		do {
			System.out.println("======================================================");
			System.out.println("                       MENU USUARIO                   ");
			System.out.println("======================================================");
			System.out.println("O que você deseja fazer?");
			System.out.println("1 - Acessar menu de pessoa física");
			System.out.println("2 - Acessar menu de pessoa jurídica");
			System.out.println("3 - Listar todos os usuários");
			System.out.println("4 - Pesquisar usuário por e-mail");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				showMenuPessoaFisica();
				break;
			case 2:
				showMenuPessoaJuridica();
				break;
			case 3:
				int op = 0;

				boolean selectCidade = false;
				boolean selectEndereco = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de cidade dos usuários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectCidade = true;
					} else if (op == 2) {
						selectCidade = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de endereço dos usuários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectEndereco = true;
					} else if (op == 2) {
						selectEndereco = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Usuario> usuarios = dao.getUsuarioDAO().select(selectEndereco, selectCidade);

				if (usuarios.size() == 0) {
					System.out.println("Não há usuários cadastrados!");
					break;
				}

				for (Usuario usuario : usuarios) {
					System.out.println("=================================================");
					usuario.print();
				}

				break;
			case 4:
				String email = console.readString("E-mail para busca: ");

				selectCidade = false;
				selectEndereco = false;

				do {
					op = console.readInt("Gostaria de saber as informações de cidade?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectCidade = true;
					} else if (op == 2) {
						selectCidade = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt("Gostaria de saber as informações de endereço?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectEndereco = true;
					} else if (op == 2) {
						selectEndereco = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				Usuario u = dao.getUsuarioDAO().selectByEmail(email, selectCidade, selectEndereco);

				if (u == null) {
					System.out.println("Usuário inexistente");
				} else {
					u.print();
				}

				break;
			}

		} while (option != EXIT);

		option = USUARIO;
	}

	private void showMenuPessoaFisica() {
		do {
			System.out.println("======================================================");
			System.out.println("                    MENU PESSOA FISICA                ");
			System.out.println("======================================================");
			System.out.println("Que tipo de usuário você quer acessar?");
			System.out.println("1 - Acessar menu de doador");
			System.out.println("2 - Acessar menu de donatário");
			System.out.println("3 - Acessar menu de voluntário");
			System.out.println("4 - Listar todas as pessoas físicas");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				showMenuDoador();
				break;
			case 2:
				showMenuDonatario();
				break;
			case 3:
				showMenuVoluntario();
				break;
			case 4:

				boolean selectUsuario = false;
				boolean selectPerfil = false;
				int usuario = 0;
				int perfil = 0;
				do {
					usuario = console.readInt(
							"Gostaria de saber informações como cidade e endereço das pessoas?\n1 - Sim\n2 - Não\n0 - Sair");
					if (usuario == 1) {
						selectUsuario = true;
					} else if (usuario == 2) {
						selectUsuario = false;
					} else if (usuario != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (usuario != 1 && usuario != 2 && usuario != 0);

				do {
					perfil = console.readInt("Gostaria de saber os perfis das pessoas?\n1 - Sim\n2 - Não\n0 - Sair");
					if (perfil == 1) {
						selectPerfil = true;
					} else if (perfil == 2) {
						selectPerfil = false;
					} else if (perfil != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (perfil != 1 && perfil != 2 && perfil != 0);

				if (perfil == 0)
					break;

				List<PessoaFisica> pessoas = dao.getPessoaFisicaDAO().select(selectUsuario, selectPerfil);

				if (pessoas.size() == 0) {
					System.out.println("Não há pessoas físicas cadastradas.");
					break;
				}

				for (PessoaFisica pessoaFisica : pessoas) {
					System.out.println("========================================================");
					pessoaFisica.print();
				}

				option = USUARIO;
				break;
			}

		} while (option != EXIT);

		option = USUARIO;
	}

	private void showMenuVoluntario() {
		do {
			System.out.println("======================================================");
			System.out.println("                      MENU VOLUNTARIO                 ");
			System.out.println("======================================================");
			System.out.println("O que você deseja fazer?");
			System.out.println("1 - Cadastrar voluntário");
			System.out.println("2 - Listar voluntários");
			System.out.println("3 - Pesquisar voluntário por e-mail");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				Voluntario v = Forms.getVoluntario();
				int error = 0;
				if (v.getCidade() != null)
					error = dao.getCidadeDAO().insert(v.getCidade());
				if (error != 0)
					break;
				if (v.getEndereco() != null)
					dao.getEnderecoDAO().insert(v.getEndereco());
				if (error != 0)
					break;
				error = dao.getUsuarioDAO().insert(v);
				if (error != 0)
					break;
				error = dao.getPessoaFisicaDAO().insert(v);
				if (error != 0)
					break;
				error = dao.getVoluntarioDAO().insert(v);
				if (error != 0)
					break;
				error = dao.getPerfilDAO().insert(v);
				if (error != 0)
					break;
				System.out.println("\nVoluntário cadastrado com sucesso!");
				break;
			case 2:
				int op = 0;

				boolean selectPessoaFisica = false;
				boolean selectUsuario = false;
				boolean selectLivros = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de pessoa física dos voluntários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de usuário gerais dos voluntários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber os livros pegos pelos voluntários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Voluntario> voluntarios = dao.getVoluntarioDAO().select(selectPessoaFisica, selectUsuario,
						selectLivros);

				if (voluntarios.size() == 0) {
					System.out.println("Não há voluntários cadastrados!");
					break;
				}

				for (Voluntario voluntario : voluntarios) {
					System.out.println("=================================================");
					voluntario.print();
				}

				break;
			case 3:
				String email = console.readString("E-mail para busca: ");

				selectPessoaFisica = false;
				selectUsuario = false;
				selectLivros = false;

				do {
					op = console
							.readInt("Gostaria de saber as informações de pessoa física?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informações gerais de usuário?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informações dos livros pegos?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				Voluntario u = dao.getVoluntarioDAO().selectByEmail(email, selectPessoaFisica, selectUsuario,
						selectLivros);

				if (u == null) {
					System.out.println("Usuário inexistente");
				} else {
					u.print();
				}

				break;
			}

		} while (option != EXIT);

		option = USUARIO;

	}

	private void showMenuDonatario() {
		do {
			System.out.println("======================================================");
			System.out.println("                      MENU DONATARIO                  ");
			System.out.println("======================================================");
			System.out.println("O que você deseja fazer?");
			System.out.println("1 - Cadastrar donatário");
			System.out.println("2 - Listar donatários");
			System.out.println("3 - Pesquisar donatário por e-mail");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				Donatario d = Forms.getDonatario();
				int error = 0;
				if (d.getCidade() != null)
					error = dao.getCidadeDAO().insert(d.getCidade());
				if (error != 0)
					break;
				if (d.getEndereco() != null)
					error = dao.getEnderecoDAO().insert(d.getEndereco());
				if (error != 0)
					break;
				error = dao.getUsuarioDAO().insert(d);
				if (error != 0)
					break;
				error = dao.getPessoaFisicaDAO().insert(d);
				if (error != 0)
					break;
				error = dao.getDonatarioDAO().insert(d);
				if (error != 0)
					break;
				error = dao.getPerfilDAO().insert(d);
				if (error != 0)
					break;
				System.out.println("\nDonatário cadastrado com sucesso!");
				break;
			case 2:
				int op = 0;

				boolean selectPessoaFisica = false;
				boolean selectUsuario = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de pessoa física dos donatários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de usuário gerais dos donatários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Donatario> donatarios = dao.getDonatarioDAO().select(selectPessoaFisica, selectUsuario);

				if (donatarios.size() == 0) {
					System.out.println("Não há donatários cadastrados!");
					break;
				}

				for (Donatario donatario : donatarios) {
					System.out.println("=================================================");
					donatario.print();
				}

				break;
			case 3:
				String email = console.readString("E-mail para busca: ");

				selectPessoaFisica = false;
				selectUsuario = false;

				do {
					op = console
							.readInt("Gostaria de saber as informações de pessoa física?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informações gerais de usuário?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				Donatario u = dao.getDonatarioDAO().selectByEmail(email, selectPessoaFisica, selectUsuario);

				if (u == null) {
					System.out.println("Usuário inexistente");
				} else {
					u.print();
				}

				break;
			}
		} while (option != EXIT);

		option = USUARIO;

	}

	private void showMenuDoador() {
		do {
			System.out.println("======================================================");
			System.out.println("                       MENU DOADOR                    ");
			System.out.println("======================================================");
			System.out.println("O que você deseja fazer?");
			System.out.println("1 - Cadastrar doador");
			System.out.println("2 - Listar doadores");
			System.out.println("3 - Pesquisar doador por e-mail");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				Doador d = Forms.getDoador();
				int error = 0;
				if (d.getCidade() != null)
					error = dao.getCidadeDAO().insert(d.getCidade());
				if (error != 0)
					break;
				if (d.getEndereco() != null)
					dao.getEnderecoDAO().insert(d.getEndereco());
				if (error != 0)
					break;
				error = dao.getUsuarioDAO().insert(d);
				if (error != 0)
					break;
				error = dao.getPessoaFisicaDAO().insert(d);
				if (error != 0)
					break;
				error = dao.getDoadorDAO().insert(d);
				if (error != 0)
					break;
				error = dao.getPerfilDAO().insert(d);
				if (error != 0)
					break;
				System.out.println("\nDoador cadastrado com sucesso!");
				break;
			case 2:
				int op = 0;

				boolean selectPessoaFisica = false;
				boolean selectUsuario = false;
				boolean selectLivros = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de pessoa física dos doadores?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de usuário gerais dos doadores?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber os livros doados pelos doadores?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Doador> doadores = dao.getDoadorDAO().select(selectPessoaFisica, selectUsuario, selectLivros);

				if (doadores.size() == 0) {
					System.out.println("Não há doadores cadastrados!");
					break;
				}

				for (Doador doador : doadores) {
					System.out.println("=================================================");
					doador.print();
				}

				break;
			case 3:
				String email = console.readString("E-mail para busca: ");

				selectPessoaFisica = false;
				selectUsuario = false;
				selectLivros = false;

				do {
					op = console
							.readInt("Gostaria de saber as informações de pessoa físcia?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informações gerais de usuário?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informações os livros doados?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				Doador u = dao.getDoadorDAO().selectByEmail(email, selectPessoaFisica, selectUsuario, selectLivros);

				if (u == null) {
					System.out.println("Usuário inexistente");
				} else {
					u.print();
				}

				break;
			}
		} while (option != EXIT);

		option = USUARIO;
	}

	private void showMenuPessoaJuridica() {
		do {
			System.out.println("======================================================");
			System.out.println("                  MENU PESSOA JURIDICA                ");
			System.out.println("======================================================");
			System.out.println("O que você deseja fazer?");
			System.out.println("1 - Cadastrar pessoa jurídica");
			System.out.println("2 - Listar pessoas jurídicas");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				PessoaJuridica pj = Forms.getPessoaJuridica();
				int error = 0;
				if (pj.getCidade() != null)
					error = dao.getCidadeDAO().insert(pj.getCidade());
				if (error != 0)
					break;
				if (pj.getEndereco() != null)
					dao.getEnderecoDAO().insert(pj.getEndereco());
				if (error != 0)
					break;
				error = dao.getUsuarioDAO().insert(pj);
				if (error != 0)
					break;
				error = dao.getPessoaJuridicaDAO().insert(pj);
				if (error != 0)
					break;

				System.out.println("\nPessoa jurídica cadastrada com sucesso!");

				break;
			case 2:
				int op = 0;

				boolean selectUsuario = false;
				boolean selectLivros = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de usuário gerais das pessoas jurídicas?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber os livros doados pelos pessoas jurídicas?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<PessoaJuridica> pjs = dao.getPessoaJuridicaDAO().select(selectUsuario, selectLivros);

				if (pjs.size() == 0) {
					System.out.println("Não há pessoas jurídicas cadastradas!");
					break;
				}

				for (PessoaJuridica pessoaJuridica : pjs) {
					System.out.println("=================================================");
					pessoaJuridica.print();
				}

				break;
			}

		} while (option != EXIT);

		option = USUARIO;
	}

	private void showMenuBibliotecario() {
		do {
			System.out.println("======================================================");
			System.out.println("                   MENU BIBLIOTECARIO                 ");
			System.out.println("======================================================");
			System.out.println("O que você quer fazer?");
			System.out.println("1 - Cadastrar bibliotecário");
			System.out.println("2 - Listar todos os bibliotecários");
			System.out.println("3 - Pesquisar bibliotecário por CIB");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				Bibliotecario b = Forms.getBibliotecario();
				int error = 0;
				if (b.getCidade() != null)
					error = dao.getCidadeDAO().insert(b.getCidade());
				if (error != 0)
					break;
				if (b.getEndereco() != null)
					dao.getEnderecoDAO().insert(b.getEndereco());
				if (error != 0)
					break;
				error = dao.getBibliotecarioDAO().insert(b);
				if (error != 0)
					break;

				System.out.println("\nBibliotecário cadastrado com sucesso!");
				break;
			case 2:

				int op = 0;

				boolean selectCidade = false;
				boolean selectEndereco = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de cidade dos bibliotecários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectCidade = true;
					} else if (op == 2) {
						selectCidade = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informações de endereço dos bibliotecários?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectEndereco = true;
					} else if (op == 2) {
						selectEndereco = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Bibliotecario> bibliotecarios = dao.getBibliotecarioDAO().select(selectEndereco, selectCidade);

				if (bibliotecarios == null || bibliotecarios.size() == 0) {
					System.out.println("Não há bibliotecários cadastrados!");
					break;
				}

				for (Bibliotecario bibliotecario : bibliotecarios) {
					System.out.println("=================================================");
					bibliotecario.print();
				}

				break;
			case 3:

				int cib = console.readInt("CIB para pesquisa: ");

				selectCidade = false;
				selectEndereco = false;

				do {
					op = console.readInt("Gostaria de saber as informações de cidade?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectCidade = true;
					} else if (op == 2) {
						selectCidade = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt("Gostaria de saber as informações de endereço?\n1 - Sim\n2 - Não\n0 - Sair");
					if (op == 1) {
						selectEndereco = true;
					} else if (op == 2) {
						selectEndereco = false;
					} else if (op != 0) {
						System.out.println("Opção inválida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				b = dao.getBibliotecarioDAO().selectByCIB(cib, selectEndereco, selectCidade);

				if (b == null) {
					System.out.println("Bibliotecário inexistente");
					break;
				}

				b.print();

				break;
			}

		} while (option != EXIT);

		option = BIBLIOTECARIO;

	}

	private void showMenuLivro() {
		do {
			System.out.println("======================================================");
			System.out.println("                        MENU LIVRO                    ");
			System.out.println("======================================================");
			System.out.println("O que você quer acessar?");
			System.out.println("1 - Cadastro de livro");
			System.out.println("2 - Listar todos os livros");
			System.out.println("3 - Pesquisar livro por código de barras");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				Livro l = Forms.getLivro();
				int error = 0;
				error = dao.getLivroDAO().insert(l);
				if (error != 0)
					break;

				System.out.println("\nLivro cadastrado com sucesso!");
				break;
			case 2:

				List<Livro> livros = dao.getLivroDAO().select();

				if (livros == null || livros.size() == 0) {
					System.out.println("Não há livros cadastrados");
					break;
				}

				for (Livro livro : livros) {
					System.out.println("================================================");
					livro.print();
				}

				break;
			case 3:

				int codigoBarras = console.readInt("Código de barras para pesquisa: ");

				l = dao.getLivroDAO().selectByCodigo(codigoBarras);

				if (l == null) {
					System.out.println("Livro inexistente");
					break;
				}

				l.print();

				break;
			}

		} while (option != EXIT);

		option = LIVRO;
	}
}
