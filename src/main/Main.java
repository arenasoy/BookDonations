package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import administrador.Administrador;
import bibliotecario.Bibliotecario;
import cidade.Cidade;
import conexao.Conexao;
import endereco.Endereco;
import livro.Livro;
import livro.Origem;
import usuario.Tipo;
import usuario.Usuario;
import usuario.fisica.Perfil;
import usuario.fisica.PessoaFisica;

public class Main {

	private Scanner scanner;
	private DAO dao;
	
	public static void main(String[] args) {
		new Main().execute();
	}
	
	private void execute() {
		scanner = new Scanner(System.in);
		setConnection();
		
		dao = new DAO();
		
		try {

			Livro l1 = new Livro(1, "Autor 1", "titulo 1", 1, "Edicao 1", 0, Origem.DOADOR);
			Livro l2 = new Livro(2, "Autor 2", "titulo 2", 2, "Edicao 2", 0, Origem.VOLUNTARIO);
			Livro l3 = new Livro(3, "Autor 3", "titulo 3", 3, "Edicao 3", 0, Origem.ADMINISTRADOR);
			
			dao.getLivroDAO().insert(l1);
			dao.getLivroDAO().insert(l2);
			dao.getLivroDAO().insert(l3);
			
			List<Livro> livros = dao.getLivroDAO().select();
			
			for (Livro livro : livros) {
				livro.print();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void print(List<Bibliotecario> list) {
		for (Bibliotecario bibliotecario : list) {
			bibliotecario.print();
		}
	}
	
	private void insertCidade() {
		try {
			Cidade c = new Cidade(null, "AB");
			dao.getCidadeDAO().insert(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertEndereco() {
		try {
			Endereco e = new Endereco("12345-000", 12, "Rua nova", "ap 13123", "jskajs");
			dao.getEnderecoDAO().insert(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertAdministrador() {
		try {
			Administrador a = new Administrador("a@a.a", "senha", "adm 1");
			dao.getAdministradorDAO().insert(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setConnection() {
		System.out.println("Oracle user:");
		Conexao.setUser(scanner.nextLine());
		System.out.println("Oracle password");
		Conexao.setPassword(scanner.nextLine());
		Conexao.getInstance();
	}
}
