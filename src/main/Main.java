package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import administrador.Administrador;
import bibliotecario.Bibliotecario;
import cidade.Cidade;
import conexao.Conexao;
import endereco.Endereco;
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
			Cidade c = new Cidade("Cidade 5", "SP");
			Endereco e = new Endereco("00000-000", 1, "Rua 5", "", "bairroo");
			
			List<Perfil> p = new ArrayList<Perfil>();
			p.add(Perfil.DOADOR);
			p.add(Perfil.VOLUNTARIO);
			PessoaFisica pf = new PessoaFisica("e@e.c", "senha", c, e, Tipo.PESSOA_FISICA, "nome pf", "000.000.000-00", "1232", "432432", p);
			
			
//			
//			dao.getCidadeDAO().insert(c);
//			dao.getEnderecoDAO().insert(e);
//			dao.getUsuarioDAO().insert(pf);
//			dao.getPessoaFisicaDAO().insert(pf);
//			dao.getPerfilDAO().insert(pf);
			
			List<Usuario> list = dao.getUsuarioDAO().select(true, true);
			for (Usuario pessoaFisica: list) {
				pessoaFisica.print();
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
//		System.out.println("Oracle user:");
//		Conexao.setUser(scanner.nextLine());
//		System.out.println("Oracle password");
//		Conexao.setPassword(scanner.nextLine());
		Conexao.getInstance();
	}
}
