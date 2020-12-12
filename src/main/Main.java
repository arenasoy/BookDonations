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
			System.out.println("Para qual área deseja ir?");
			System.out.println("1 - Administradores");
			System.out.println("2 - Usuários");
			System.out.println("3 - Bibliotecários");
			System.out.println("4 - Livros");
			System.out.println("5 - Grupos, temporadas e questões"); //TODO
			System.out.println("6 - Missões"); //TODO
			System.out.println("7 - Doações"); //TODO
			System.out.println("8 - Empréstimos"); //TODO
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
				System.out.println("Função ainda não implementada :(");
				break;
			case 6: 
				System.out.println("Função ainda não implementada :(");
				break;
			case 7: 
				System.out.println("Função ainda não implementada :(");
				break;
			case 8: 
				System.out.println("Função ainda não implementada :(");
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
			case 3:
				System.out.println("Função ainda não implementada :(");
				break;
			case 4:
				System.out.println("Função ainda não implementada :(");
				break;
			case 5:
				System.out.println("Função ainda não implementada :(");
				break;
			}

		} while (option != EXIT);

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
							"Gostaria de saber as informções de cidade dos usuários?\n1 - Sim\n2 - Não\n0 - Sair");
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
			System.out.println("5 - Pesquisar pessoa física por e-mail"); // TODO
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
			case 5:
				System.out.println("Função ainda não implementada :(");
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
			System.out.println("3 - Atualizar voluntário"); // TODO
			System.out.println("4 - Pesquisar voluntário por e-mail");
			System.out.println("5 - Excluir voluntário"); // TODO
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
							"Gostaria de saber as informções de pessoa física dos voluntários?\n1 - Sim\n2 - Não\n0 - Sair");
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
							"Gostaria de saber as informções de usuário gerais dos voluntários?\n1 - Sim\n2 - Não\n0 - Sair");
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
				System.out.println("Função ainda não implementada :(");
				break;
			case 4:
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
			case 5:
				System.out.println("Função ainda não implementada :(");
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
			System.out.println("3 - Atualizar donatário"); // TODO
			System.out.println("4 - Pesquisar donatário por e-mail");
			System.out.println("5 - Excluir donatário"); // TODO
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
							"Gostaria de saber as informções de pessoa física dos donatários?\n1 - Sim\n2 - Não\n0 - Sair");
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
							"Gostaria de saber as informções de usuário gerais dos donatários?\n1 - Sim\n2 - Não\n0 - Sair");
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
				System.out.println("Função ainda não implementada :(");
				break;
			case 4:
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
			case 5:
				System.out.println("Função ainda não implementada :(");
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
			System.out.println("3 - Atualizar doador"); // TODO
			System.out.println("4 - Pesquisar doador por e-mail");
			System.out.println("5 - Excluir doador"); // TODO
			System.out.println("6 - Listar todas as doações"); // TODO
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
							"Gostaria de saber as informções de pessoa física dos doadores?\n1 - Sim\n2 - Não\n0 - Sair");
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
							"Gostaria de saber as informções de usuário gerais dos doadores?\n1 - Sim\n2 - Não\n0 - Sair");
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
				System.out.println("Função ainda não implementada :(");
				break;
			case 4:
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
			case 5:
				System.out.println("Função ainda não implementada :(");
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
			System.out.println("3 - Atualizar pessoa jurídica"); // TODO
			System.out.println("4 - Excluir pessoa jurídica"); // TODO
			System.out.println("5 - Pesquisar pessoa jurídica por e-mail"); // TODO
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
							"Gostaria de saber as informções de usuário gerais das pessoas jurídicas?\n1 - Sim\n2 - Não\n0 - Sair");
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
			case 3:
				System.out.println("Função ainda não implementada :(");
				break;
			case 4:
				System.out.println("Função ainda não implementada :(");
				break;
			case 5:
				System.out.println("Função ainda não implementada :(");
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
			System.out.println("4 - Atualizar bibliotecário"); // TODO
			System.out.println("5 - Remover bibliotecário"); // TODO
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
							"Gostaria de saber as informções de cidade dos bibliotecários?\n1 - Sim\n2 - Não\n0 - Sair");
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
					op = console.readInt(
							"Gostaria de saber as informações de cidade?\n1 - Sim\n2 - Não\n0 - Sair");
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
							"Gostaria de saber as informações de endereço?\n1 - Sim\n2 - Não\n0 - Sair");
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
			case 4:
				System.out.println("Função ainda não implementada :(");
				break;
			case 5:
				System.out.println("Função ainda não implementada :(");
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
			case 4:
				System.out.println("Função ainda não implementada :(");
				break;
			case 5:
				System.out.println("Função ainda não implementada :(");
				break;
			}

		} while (option != EXIT);

		option = LIVRO;
	}
}
