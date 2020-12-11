package main;

import java.util.List;
import java.util.Scanner;

import administrador.Administrador;
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

		insertAdministrador();
		List<Administrador> list = dao.getAdministradorDAO().select();
		for (Administrador a : list) {
			a.print();
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
