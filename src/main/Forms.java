package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import administrador.Administrador;
import console.Console;

public class Forms {

	public static Administrador getAdministrador() {

		Console console = Console.getInstance();
		Administrador a = null;

		boolean success = false;

		System.out.println("Cadastro de administrador");
		
		String email = null, senha = null, nome = null;
		
		while (!success) {
			
			email = console.readString("E-mail:");
			
			if (email == null || email.length() == 0 || email.length() > 100) {
				System.out.println("E-mail é obrigatório e deve ter até 100 caracteres.");
				continue;
			}

			Pattern pattern = Pattern.compile("^.+@.+\\..+$");
			Matcher matcher = pattern.matcher(email);

			if (!matcher.matches()) {
				System.out.println("E-mail inválido");
				continue;
			}
			
			break;
		}
		
		while (!success) {
			
			senha = console.readString("Senha: ");
			
			if (senha == null || senha.length() == 0 || senha.length() > 20) {
				System.out.println("Senha é obrigatória e deve ter até 20 caracteres");
				continue;
			}

			break;
		}
		
		while (!success) {
			nome = console.readLine("Nome: ");
			if (nome == null || nome.length() == 0 || nome.length() > 50) {
				System.out.println("Nome é obrigatório e deve ter até 50 caracteres");
				continue;
			}
			break;
		}
		
		try {
			a = new Administrador(email, senha, nome);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

}
