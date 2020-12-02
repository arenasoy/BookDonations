package main;

import java.util.Scanner;

import conexao.Conexao;

public class Main {

	private Scanner scanner;
	
	public static void main(String[] args) {
		new Main().execute();
	}
	
	private void execute() {
		scanner = new Scanner(System.in);
		setConnection();
	}
	
	private void setConnection() {
		System.out.println("Oracle user:");
		Conexao.setUser(scanner.nextLine());
		System.out.println("Oracle password");
		Conexao.setPassword(scanner.nextLine());
		Conexao.getInstance();
	}
}
