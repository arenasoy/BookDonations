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

public class Main {

	private Console console;
	private DAO dao;

	private final int EXIT = 0;
	private final int ADMINISTRADOR = 1;
	private final int USUARIO = 2;
	private final int BIBLIOTECARIO = 3;
	private final int LIVRO = 4;
	private final int CADASTRAR = 1;
	private final int LISTAR = 2;
	private final int PESQUISAR = 3;
	private final int ATUALIZAR = 4;
	private final int EXCLUIR = 5;
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
			case CADASTRAR:
				dao.getAdministradorDAO().insert(Forms.getAdministrador());
				System.out.println("Administrador cadastrado com sucesso!");
				break;
			case LISTAR:
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
			case PESQUISAR:
				System.out.println("Função ainda não implementada :(");
				break;
			case ATUALIZAR:
				System.out.println("Função ainda não implementada :(");
				break;
			case EXCLUIR:
				System.out.println("Função ainda não implementada :(");
				break;
			}

		} while (option != EXIT);

	}

	private void showMenuUsuario() {

	}

	private void showMenuBibliotecario() {

	}

	private void showMenuLivro() {

	}
}
