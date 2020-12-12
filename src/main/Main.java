package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import administrador.Administrador;
import bibliotecario.Bibliotecario;
import cidade.Cidade;
import conexao.Conexao;
import console.Console;
import endereco.Endereco;
import grupo.Grupo;
import livro.Livro;
import livro.Origem;
import usuario.Tipo;
import usuario.Usuario;
import usuario.fisica.Perfil;
import usuario.fisica.PessoaFisica;
import usuario.fisica.doador.Doador;
import usuario.fisica.donatario.Donatario;
import usuario.fisica.voluntario.Voluntario;
import usuario.juridica.PessoaJuridica;

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

		// Conexao.setUser(console.readString("User: "));
		// Conexao.setPassword(console.readLine("Password:"));

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
			System.out.println("5 - Grupos, temporadas e quest�es"); //TODO
			System.out.println("6 - Miss�es"); //TODO
			System.out.println("7 - Doa��es"); //TODO
			System.out.println("8 - Empr�stimos"); //TODO
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
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 6: 
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 7: 
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 8: 
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
				
			}

		} while (option != EXIT);

	}

	private void showMenuAdministrador() {
		do {
			System.out.println("======================================================");
			System.out.println("                    MENU ADMINISTRADOR                ");
			System.out.println("======================================================");
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Cadastrar administrador");
			System.out.println("2 - Listar todos os administradores");
			System.out.println("3 - Pesquisar administrador por e-mail"); // TODO
			System.out.println("4 - Atualizar administrador"); // TODO
			System.out.println("5 - Excluir administrador"); // TODO
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				dao.getAdministradorDAO().insert(Forms.getAdministrador());
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
			case 3:
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 4:
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 5:
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			}

		} while (option != EXIT);

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
							"Gostaria de saber as inform��es de cidade dos usu�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
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
			System.out.println("5 - Pesquisar pessoa f�sica por e-mail"); // TODO
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
			case 5:
				System.out.println("Fun��o ainda n�o implementada :(");
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
			System.out.println("3 - Atualizar volunt�rio"); // TODO
			System.out.println("4 - Pesquisar volunt�rio por e-mail");
			System.out.println("5 - Excluir volunt�rio"); // TODO
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
							"Gostaria de saber as inform��es de pessoa f�sica dos volunt�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
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
							"Gostaria de saber as inform��es de usu�rio gerais dos volunt�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
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
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 4:
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
			case 5:
				System.out.println("Fun��o ainda n�o implementada :(");
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
			System.out.println("3 - Atualizar donat�rio"); // TODO
			System.out.println("4 - Pesquisar donat�rio por e-mail");
			System.out.println("5 - Excluir donat�rio"); // TODO
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
							"Gostaria de saber as inform��es de pessoa f�sica dos donat�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
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
							"Gostaria de saber as inform��es de usu�rio gerais dos donat�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
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
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 4:
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
			case 5:
				System.out.println("Fun��o ainda n�o implementada :(");
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
			System.out.println("3 - Atualizar doador"); // TODO
			System.out.println("4 - Pesquisar doador por e-mail");
			System.out.println("5 - Excluir doador"); // TODO
			System.out.println("6 - Listar todas as doa��es"); // TODO
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
							"Gostaria de saber as inform��es de pessoa f�sica dos doadores?\n1 - Sim\n2 - N�o\n0 - Sair");
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
							"Gostaria de saber as inform��es de usu�rio gerais dos doadores?\n1 - Sim\n2 - N�o\n0 - Sair");
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
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 4:
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
			case 5:
				System.out.println("Fun��o ainda n�o implementada :(");
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
			System.out.println("3 - Atualizar pessoa jur�dica"); // TODO
			System.out.println("4 - Excluir pessoa jur�dica"); // TODO
			System.out.println("5 - Pesquisar pessoa jur�dica por e-mail"); // TODO
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
							"Gostaria de saber as inform��es de usu�rio gerais das pessoas jur�dicas?\n1 - Sim\n2 - N�o\n0 - Sair");
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
			case 3:
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 4:
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 5:
				System.out.println("Fun��o ainda n�o implementada :(");
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
			System.out.println("4 - Atualizar bibliotec�rio"); // TODO
			System.out.println("5 - Remover bibliotec�rio"); // TODO
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
							"Gostaria de saber as inform��es de cidade dos bibliotec�rios?\n1 - Sim\n2 - N�o\n0 - Sair");
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
					op = console.readInt(
							"Gostaria de saber as informa��es de cidade?\n1 - Sim\n2 - N�o\n0 - Sair");
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
							"Gostaria de saber as informa��es de endere�o?\n1 - Sim\n2 - N�o\n0 - Sair");
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
			case 4:
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 5:
				System.out.println("Fun��o ainda n�o implementada :(");
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
			System.out.println("4 - Atualizar livro"); // TODO
			System.out.println("5 - Remover livro"); // TODO
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				Livro l = Forms.getLivro();
				int error = 0;
				error = dao.getLivroDAO().insert(l);
				//TODO forcar o cadastro correto da origem do livro
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
			case 4:
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			case 5:
				System.out.println("Fun��o ainda n�o implementada :(");
				break;
			}

		} while (option != EXIT);

		option = LIVRO;
	}
}
