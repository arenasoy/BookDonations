package main;

import java.util.List;
import java.util.Scanner;

import administrador.Administrador;
import bibliotecario.Bibliotecario;
import cidade.Cidade;
import conexao.Conexao;
import endereco.Endereco;

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
		//insertCidade();
//		insertEndereco();
//		
//
//		insertAdministrador();
//		List<Administrador> list = dao.getAdministradorDAO().select();
//		for (Administrador a : list) {
//			a.print();
//		}
		
		try {
//			Cidade c = new Cidade("Cidade 2", "UF");
//			Endereco e = new Endereco("12345-000", 12, "Rua", "ap 13123", "jskajs");
//			Bibliotecario b1 = new Bibliotecario(1, "senha 1", "Bibliotecario 1", c, e);
//			
//			Bibliotecario b2 = new Bibliotecario(2, "senha 2", "Bibliotecario 2", c, null);
//			Bibliotecario b3 = new Bibliotecario(3, "senha 3", "Bibliotecario 3", null, e);
//			
//			dao.getCidadeDAO().insert(c);
//			dao.getEnderecoDAO().insert(e);
//			dao.getBibliotecarioDAO().insert(b1);
//			dao.getBibliotecarioDAO().insert(b2);
//			dao.getBibliotecarioDAO().insert(b3);
			
			System.out.println("sem nada\n");
			List<Bibliotecario> list = dao.getBibliotecarioDAO().select(false, false);
			print(list);
			System.out.println("=================================");
			
			System.out.println("so cidade\n");
			list = dao.getBibliotecarioDAO().select(false, true);
			print(list);
			System.out.println("=================================");
			
			System.out.println("so endereco\n");
			list = dao.getBibliotecarioDAO().select(true, false);
			print(list);
			System.out.println("=================================");
			
			System.out.println("ambos\n");
			list = dao.getBibliotecarioDAO().select(true, true);
			print(list);
			System.out.println("=================================");
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
//		System.out.println("Oracle user:");
//		Conexao.setUser(scanner.nextLine());
//		System.out.println("Oracle password");
//		Conexao.setPassword(scanner.nextLine());
		Conexao.getInstance();
	}
}
