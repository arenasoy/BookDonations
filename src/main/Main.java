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
import usuario.fisica.voluntario.Voluntario;

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

		//Conexao.setUser(console.readString("User: "));
		//Conexao.setPassword(console.readLine("Password:"));

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
			System.out.println("3 - Listar todos os usuários"); //TODO
			System.out.println("4 - Pesquisar usuário por e-mail"); //TODO
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
				System.out.println("Função ainda não implementada :(");
				break;
			case 4: 
				System.out.println("Função ainda não implementada :(");
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
			System.out.println("5 - Pesquisar pessoa física por e-mail"); //TODO
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
					usuario = console.readInt("Gostaria de saber informações como cidade e endereço das pessoas?\n1 - Sim\n2 - Não\n0 - Sair");
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
				
				if (perfil == 0) break;
				
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
				showMenuPessoaJuridica();
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
			System.out.println("3 - Atualizar voluntário"); //TODO
			System.out.println("4 - Pesquisar voluntário por e-mail"); //TODO
			System.out.println("5 - Excluir voluntário"); //TODO
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				Voluntario v = Forms.getVoluntario();
				if (v.getCidade() != null)
					dao.getCidadeDAO().insert(v.getCidade());
				if (v.getEndereco() != null)
					dao.getEnderecoDAO().insert(v.getEndereco());
				dao.getUsuarioDAO().insert(v);
				dao.getPessoaFisicaDAO().insert(v);
				dao.getVoluntarioDAO().insert(v);
				dao.getPerfilDAO().insert(v);
				System.out.println("\nVoluntário cadastrado com sucesso!");
				break;
			case 2:
				int op = 0;
				
				boolean selectPessoaFisica = false;
				boolean selectUsuario = false;
				boolean selectLivros = false;
				
				do {
					op = console.readInt("Gostaria de saber as informções de pessoa físcia dos voluntários?\n1 - Sim\n2 - Não\n0 - Sair");
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
				
				if (op == 0) break;

				do {
					op = console.readInt("Gostaria de saber as informções de usuário gerais dos voluntários?\n1 - Sim\n2 - Não\n0 - Sair");
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
				
				if (op == 0) break;
				
				do {
					op = console.readInt("Gostaria de saber os livros pegos pelos voluntários?\n1 - Sim\n2 - Não\n0 - Sair");
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
				
				if (op == 0) break;
				
				List<Voluntario> voluntarios = dao.getVoluntarioDAO().select(selectPessoaFisica, selectUsuario, selectLivros);
				
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
				showMenuVoluntario();
				break;
			case 4:
				
			case 5:
				showMenuPessoaJuridica();
				break;
			}

		} while (option != EXIT);
		
		option = USUARIO;
		
	}

	private void showMenuDonatario() {
		// TODO Auto-generated method stub
		
	}

	private void showMenuDoador() {
		// TODO Auto-generated method stub
		
	}

	private void showMenuPessoaJuridica() {
		do {
			System.out.println("======================================================");
			System.out.println("                       MENU USUARIO                   ");
			System.out.println("======================================================");
			System.out.println("Que tipo de usuário você quer acessar?");
			System.out.println("1 - Pessoa física");
			System.out.println("2 - Pessoa jurídica");
			System.out.println("0 - Sair");

			option = console.readInt();

			switch (option) {
			case 1:
				showMenuPessoaFisica();
				break;
			case 2:
				showMenuPessoaJuridica();
				break;
			}

		} while (option != EXIT);
		
		option = USUARIO;
	}
	
	private void showMenuBibliotecario() {

	}

	private void showMenuLivro() {

	}
}
