package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import administrador.Administrador;
import bibliotecario.Bibliotecario;
import cidade.Cidade;
import conexao.Conexao;
import endereco.Endereco;
import grupo.Grupo;
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

//			Administrador a1 = new Administrador("adm@a.a", "senha", "Adm 1");
//
//			Administrador a2 = new Administrador("a@a.a", "senha", "Adm 2");
//			
//			Grupo g1 = new Grupo("grupo 1", grupo.Tipo.DOADOR, 0.0, a1);
//			Grupo g2 = new Grupo("grupo 2", grupo.Tipo.DONATARIO, 14, a2);
//			
//			dao.getAdministradorDAO().insert(a1);
//			dao.getGrupoDAO().insert(g1);
//			dao.getGrupoDAO().insert(g2);
//			
			List<Grupo> list = dao.getGrupoDAO().select(false);
		
			for (Grupo grupo : list) {
				grupo.print();
			}
			
			System.out.println("===================");
			
			list = dao.getGrupoDAO().select(true);
			
			for (Grupo grupo : list) {
				grupo.print();
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
