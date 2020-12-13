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
 * Cada uma das fun��es � referente a um sub-menu
 * 
 * O projeto esta dividido em diversos pacotes
 * Cada pacote referente a uma tabela possui pelo menos duas classes
 * - Uma define a entidade e m�todos
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
		System.out.println("Sistema de doa��o de livros");
		System.out.println("=================================================");

		System.out.println(
				"Para come�ar a utilizar, � preciso inserir as credenciais da base de dados que possui as tabelas criadas pelo arquivo esquema.sql");

		console = Console.getInstance();
		while (!setConnection())
			;
		System.out.println("\nConex�o realizada com sucesso!");

		dao = new DAO();

		showMenu();

		System.out.println("At� mais!");
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
			System.out.println("Para qual �rea deseja ir?");
			System.out.println("1 - Administradores");
			System.out.println("2 - Usu�rios");
			System.out.println("3 - Bibliotec�rios");
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
			System.out.println("1 - Busca m�dia de pontua��o por grupo na temporada atual)");
			System.out.println("2 - Busca por quantidade de livros que um doador doou no m�s atual");
			System.out.println("3 - Busca por quantidade de livros que um volunt�rio coletou no m�s atual");
			System.out.println("4 - Busca a m�dia de livros doados no m�s atual");
			System.out.println("5 - Busca a m�dia de livros coletados no m�s atual");
			System.out.println("6 - Busca a m�dia de pontua��o de um doador por doa��es realizadas no m�s atual e compara com a m�dia geral");
			System.out.println("7 - Busca a m�dia de pontua��o de um volunt�rio por coletas realizadas no m�s atual e compara com a m�dia geral");
			System.out.println("8 - Busca a m�dia de pontua��o de um donat�rio por pontua��es conquistadas no m�s atual e compara com a m�dia geral");
			System.out.println("9 - Mostra a classifica��o geral dentro de um grupo");
			System.out.println("10 - Busca m�dia de pontua��o por temporada");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				HashMap<String, Double> medias = dao.getConsultaDAO().pontuacaoMediaGrupoTemporada();

				if (medias.size() == 0) {
					System.out.println("N�o h� dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Grupo: m�dia");
				for (String s : medias.keySet()) {
					System.out.println(s + ": " + medias.get(s));
				}
				break;
			case 2:
				HashMap<String, Integer> quantidade = dao.getConsultaDAO().quantidadeLivrosDoador();

				if (quantidade.size() == 0) {
					System.out.println("N�o h� dados suficientes para realizar essa consulta");
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
					System.out.println("N�o h� dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Nome: quantidade");
				for (String s : quantidade.keySet()) {
					System.out.println(s + ": " + quantidade.get(s));
				}
				break;
			case 4:
				double media = dao.getConsultaDAO().mediaLivrosDoados();
				System.out.println("M�dia: " + media);
				break;
			case 5:
				media = dao.getConsultaDAO().mediaLivrosColetados();
				System.out.println("M�dia: " + media);
				break;
			case 6:
				medias = dao.getConsultaDAO().mediaDoador();
				media = dao.getConsultaDAO().mediaGeralDoacao();
				if (medias.size() == 0) {
					System.out.println("N�o h� dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("M�dia geral: " + media);
				System.out.println("Doador: m�dia - situa��o");
				for (String s : medias.keySet()) {
					System.out.print(s + ": " + medias.get(s) + " - ");
					if (medias.get(s) == media) System.out.println("Na m�dia");
					else if (medias.get(s) > media) System.out.println("Acima da m�dia");
					else System.out.println("Abaixo da m�dia");
				}
				break;
			case 7:
				medias = dao.getConsultaDAO().mediaVoluntario();
				media = dao.getConsultaDAO().mediaGeralColeta();
				if (medias.size() == 0) {
					System.out.println("N�o h� dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("M�dia geral: " + media);
				System.out.println("Volunt�rio: m�dia - situa��o");
				for (String s : medias.keySet()) {
					System.out.print(s + ": " + medias.get(s) + " - ");
					if (medias.get(s) == media) System.out.println("Na m�dia");
					else if (medias.get(s) > media) System.out.println("Acima da m�dia");
					else System.out.println("Abaixo da m�dia");
				}
				break;
			case 8:
				medias = dao.getConsultaDAO().mediaDonatario();
				media = dao.getConsultaDAO().mediaGeralDonatario();
				if (medias.size() == 0) {
					System.out.println("N�o h� dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("M�dia geral: " + media);
				System.out.println("Donat�rio: m�dia - situa��o");
				for (String s : medias.keySet()) {
					System.out.print(s + ": " + medias.get(s) + " - ");
					if (medias.get(s) == media) System.out.println("Na m�dia");
					else if (medias.get(s) > media) System.out.println("Acima da m�dia");
					else System.out.println("Abaixo da m�dia");
				}
				break;
			case 9: 
				String grupo = console.readLine("Grupo desejado: ");
				
				List<ArrayList<String>> list = dao.getConsultaDAO().classificacaoGrupo(grupo);
				
				if (list == null || list.size() == 0) {
					System.out.println("N�o h� dados suficientes para realizar essa consulta");
					break;
				}
				
				System.out.println("Classifica��o no grupo " + grupo);
				for (ArrayList<String> arrayList : list) {
					System.out.println(arrayList.get(0) + " - " + arrayList.get(1));
				}
				break;
			case 10:
				HashMap<LocalDate, Double> m = dao.getConsultaDAO().mediaPontuacaoTemporada();

				if (m.size() == 0) {
					System.out.println("N�o h� dados suficientes para realizar essa consulta");
					break;
				}
				System.out.println("Temporada: m�dia");
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
					op = console.readInt("Gostaria de saber as informa��es de grupo?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectGrupo = true;
					} else if (op == 2) {
						selectGrupo = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt("Gostaria de saber as informa��es de temporada?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectTemporada = true;
					} else if (op == 2) {
						selectTemporada = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informa��es de pessoa f�sica?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
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
					System.out.println("N�o h� rela��es cadastradas!");
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
							"Gostaria de saber as informa��es de administrador do grupo?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectAdministrador = true;
					} else if (op == 2) {
						selectAdministrador = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Grupo> grupos = dao.getGrupoDAO().select(selectAdministrador);

				if (grupos == null || grupos.size() == 0) {
					System.out.println("N�o h� grupos cadastrados");
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
					System.out.println("N�o h� temporadas cadastradas");
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
				System.out.println("Rela��o cadastrada com sucesso!");
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
							"Deseja listar os livros cadastrados pelos administradores tamb�m?\n1 - Sim\n2 - N�o\n0 - Sair");
					List<Administrador> administradores = null;
					if (option == 1) {
						administradores = dao.getAdministradorDAO().select(true);
					} else if (option == 2) {
						administradores = dao.getAdministradorDAO().select(false);
					} else if (option != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

					if (administradores.size() == 0) {
						System.out.println("N�o h� administradores cadastrados.");
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
			System.out.println("O que voc� deseja fazer?");
			System.out.println("1 - Acessar menu de pessoa f�sica");
			System.out.println("2 - Acessar menu de pessoa jur�dica");
			System.out.println("3 - Listar todos os usu�rios");
			System.out.println("4 - Pesquisar usu�rio por e-mail");
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
							"Gostaria de saber as informa��es de cidade dos usu�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectCidade = true;
					} else if (op == 2) {
						selectCidade = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de endere�o dos usu�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectEndereco = true;
					} else if (op == 2) {
						selectEndereco = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Usuario> usuarios = dao.getUsuarioDAO().select(selectEndereco, selectCidade);

				if (usuarios.size() == 0) {
					System.out.println("N�o h� usu�rios cadastrados!");
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
					op = console.readInt("Gostaria de saber as informa��es de cidade?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectCidade = true;
					} else if (op == 2) {
						selectCidade = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt("Gostaria de saber as informa��es de endere�o?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectEndereco = true;
					} else if (op == 2) {
						selectEndereco = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				Usuario u = dao.getUsuarioDAO().selectByEmail(email, selectCidade, selectEndereco);

				if (u == null) {
					System.out.println("Usu�rio inexistente");
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
			System.out.println("Que tipo de usu�rio voc� quer acessar?");
			System.out.println("1 - Acessar menu de doador");
			System.out.println("2 - Acessar menu de donat�rio");
			System.out.println("3 - Acessar menu de volunt�rio");
			System.out.println("4 - Listar todas as pessoas f�sicas");
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
							"Gostaria de saber informa��es como cidade e endere�o das pessoas?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (usuario == 1) {
						selectUsuario = true;
					} else if (usuario == 2) {
						selectUsuario = false;
					} else if (usuario != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (usuario != 1 && usuario != 2 && usuario != 0);

				do {
					perfil = console.readInt("Gostaria de saber os perfis das pessoas?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (perfil == 1) {
						selectPerfil = true;
					} else if (perfil == 2) {
						selectPerfil = false;
					} else if (perfil != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (perfil != 1 && perfil != 2 && perfil != 0);

				if (perfil == 0)
					break;

				List<PessoaFisica> pessoas = dao.getPessoaFisicaDAO().select(selectUsuario, selectPerfil);

				if (pessoas.size() == 0) {
					System.out.println("N�o h� pessoas f�sicas cadastradas.");
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
			System.out.println("O que voc� deseja fazer?");
			System.out.println("1 - Cadastrar volunt�rio");
			System.out.println("2 - Listar volunt�rios");
			System.out.println("3 - Pesquisar volunt�rio por e-mail");
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
				System.out.println("\nVolunt�rio cadastrado com sucesso!");
				break;
			case 2:
				int op = 0;

				boolean selectPessoaFisica = false;
				boolean selectUsuario = false;
				boolean selectLivros = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de pessoa f�sica dos volunt�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de usu�rio gerais dos volunt�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber os livros pegos pelos volunt�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
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
					System.out.println("N�o h� volunt�rios cadastrados!");
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
							.readInt("Gostaria de saber as informa��es de pessoa f�sica?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informa��es gerais de usu�rio?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informa��es dos livros pegos?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
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
					System.out.println("Usu�rio inexistente");
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
			System.out.println("O que voc� deseja fazer?");
			System.out.println("1 - Cadastrar donat�rio");
			System.out.println("2 - Listar donat�rios");
			System.out.println("3 - Pesquisar donat�rio por e-mail");
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
				System.out.println("\nDonat�rio cadastrado com sucesso!");
				break;
			case 2:
				int op = 0;

				boolean selectPessoaFisica = false;
				boolean selectUsuario = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de pessoa f�sica dos donat�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de usu�rio gerais dos donat�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Donatario> donatarios = dao.getDonatarioDAO().select(selectPessoaFisica, selectUsuario);

				if (donatarios.size() == 0) {
					System.out.println("N�o h� donat�rios cadastrados!");
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
							.readInt("Gostaria de saber as informa��es de pessoa f�sica?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informa��es gerais de usu�rio?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				Donatario u = dao.getDonatarioDAO().selectByEmail(email, selectPessoaFisica, selectUsuario);

				if (u == null) {
					System.out.println("Usu�rio inexistente");
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
			System.out.println("O que voc� deseja fazer?");
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
							"Gostaria de saber as informa��es de pessoa f�sica dos doadores?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de usu�rio gerais dos doadores?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber os livros doados pelos doadores?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Doador> doadores = dao.getDoadorDAO().select(selectPessoaFisica, selectUsuario, selectLivros);

				if (doadores.size() == 0) {
					System.out.println("N�o h� doadores cadastrados!");
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
							.readInt("Gostaria de saber as informa��es de pessoa f�scia?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectPessoaFisica = true;
					} else if (op == 2) {
						selectPessoaFisica = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informa��es gerais de usu�rio?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console
							.readInt("Gostaria de saber as informa��es os livros doados?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				Doador u = dao.getDoadorDAO().selectByEmail(email, selectPessoaFisica, selectUsuario, selectLivros);

				if (u == null) {
					System.out.println("Usu�rio inexistente");
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
			System.out.println("O que voc� deseja fazer?");
			System.out.println("1 - Cadastrar pessoa jur�dica");
			System.out.println("2 - Listar pessoas jur�dicas");
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

				System.out.println("\nPessoa jur�dica cadastrada com sucesso!");

				break;
			case 2:
				int op = 0;

				boolean selectUsuario = false;
				boolean selectLivros = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de usu�rio gerais das pessoas jur�dicas?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectUsuario = true;
					} else if (op == 2) {
						selectUsuario = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber os livros doados pelos pessoas jur�dicas?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectLivros = true;
					} else if (op == 2) {
						selectLivros = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<PessoaJuridica> pjs = dao.getPessoaJuridicaDAO().select(selectUsuario, selectLivros);

				if (pjs.size() == 0) {
					System.out.println("N�o h� pessoas jur�dicas cadastradas!");
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
			System.out.println("O que voc� quer fazer?");
			System.out.println("1 - Cadastrar bibliotec�rio");
			System.out.println("2 - Listar todos os bibliotec�rios");
			System.out.println("3 - Pesquisar bibliotec�rio por CIB");
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

				System.out.println("\nBibliotec�rio cadastrado com sucesso!");
				break;
			case 2:

				int op = 0;

				boolean selectCidade = false;
				boolean selectEndereco = false;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de cidade dos bibliotec�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectCidade = true;
					} else if (op == 2) {
						selectCidade = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt(
							"Gostaria de saber as informa��es de endere�o dos bibliotec�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectEndereco = true;
					} else if (op == 2) {
						selectEndereco = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				List<Bibliotecario> bibliotecarios = dao.getBibliotecarioDAO().select(selectEndereco, selectCidade);

				if (bibliotecarios == null || bibliotecarios.size() == 0) {
					System.out.println("N�o h� bibliotec�rios cadastrados!");
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
					op = console.readInt("Gostaria de saber as informa��es de cidade?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectCidade = true;
					} else if (op == 2) {
						selectCidade = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				do {
					op = console.readInt("Gostaria de saber as informa��es de endere�o?\n1 - Sim\n2 - N�o\n0 - Sair");
					if (op == 1) {
						selectEndereco = true;
					} else if (op == 2) {
						selectEndereco = false;
					} else if (op != 0) {
						System.out.println("Op��o inv�lida");
						continue;
					} else {
						break;
					}

				} while (op != 1 && op != 2 && op != 0);

				if (op == 0)
					break;

				b = dao.getBibliotecarioDAO().selectByCIB(cib, selectEndereco, selectCidade);

				if (b == null) {
					System.out.println("Bibliotec�rio inexistente");
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
			System.out.println("O que voc� quer acessar?");
			System.out.println("1 - Cadastro de livro");
			System.out.println("2 - Listar todos os livros");
			System.out.println("3 - Pesquisar livro por c�digo de barras");
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
					System.out.println("N�o h� livros cadastrados");
					break;
				}

				for (Livro livro : livros) {
					System.out.println("================================================");
					livro.print();
				}

				break;
			case 3:

				int codigoBarras = console.readInt("C�digo de barras para pesquisa: ");

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
