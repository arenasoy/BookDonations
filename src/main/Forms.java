package main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import administrador.Administrador;
import cidade.Cidade;
import console.Console;
import endereco.Endereco;
import grupo.Tipo;
import usuario.fisica.Perfil;
import usuario.fisica.voluntario.Voluntario;

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
				System.out.println("E-mail � obrigat�rio e deve ter at� 100 caracteres.");
				continue;
			}

			Pattern pattern = Pattern.compile("^.+@.+\\..+$");
			Matcher matcher = pattern.matcher(email);

			if (!matcher.matches()) {
				System.out.println("E-mail inv�lido");
				continue;
			}

			break;
		}

		while (!success) {

			senha = console.readString("Senha: ");

			if (senha == null || senha.length() == 0 || senha.length() > 20) {
				System.out.println("Senha � obrigat�ria e deve ter at� 20 caracteres");
				continue;
			}

			break;
		}

		while (!success) {
			nome = console.readLine("Nome: ");
			if (nome == null || nome.length() == 0 || nome.length() > 50) {
				System.out.println("Nome � obrigat�rio e deve ter at� 50 caracteres");
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

	public static Voluntario getVoluntario() {

		Console console = Console.getInstance();

		String email = console.readLine("E-mail: ");

		while (email == null || email.length() == 0 || email.length() > 100) {
			System.out.println("E-mail � obrigat�rio e deve ter at� 100 caracteres");
			email = console.readLine("E-mail");
		}

		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email);

		while (!matcher.matches()) {
			System.out.println("E-mail inv�lido");
			email = console.readLine("E-mail");
			matcher = pattern.matcher(email);
		}

		String senha = console.readLine("Senha: ");

		while (senha == null || senha.length() == 0 || senha.length() > 20) {
			System.out.println("Senha � obrigat�ria e deve ter at� 20 caracteres");
			senha = console.readLine("Senha: ");
		}

		usuario.Tipo tipo = usuario.Tipo.PESSOA_FISICA;

		System.out.println("Sobre a cidade: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endere�o: ");
		Endereco e = getEndereco();

		String nome = console.readLine("Nome do volunt�rio: ");

		while (nome == null || nome.length() == 0 || nome.length() > 50) {
			System.out.println("Nome � obrigat�rio e deve ter at� 50 caracteres");
			nome = console.readLine("Nome do volunt�rio: ");
		}

		String cpf = console.readString("CPF: ");
		while (cpf == null || cpf.length() != 14) {
			System.out.println("CPF � obrigat�rio e deve ser no formato '000.000.000-00'");
			cpf = console.readString("CPF: ");
		}

		String rg = console.readString("RG: ");
		while (rg != null && rg.length() > 9) {
			System.out.println("RG deve ter at� 9 caracteres");
			rg = console.readString("RG: ");
		}

		String telefone = console.readString("Telefone: ");
		while (telefone != null && telefone.length() > 15) {
			System.out.println("O telefone deve ter at� 15 caracteres");
			telefone = console.readString("Telefone: ");
		}

		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(Perfil.VOLUNTARIO);

		double pontuacao = 0;

		Voluntario v = null;
		try {
			v = new Voluntario(email, senha, c, e, tipo, nome, cpf, rg, telefone, perfis, pontuacao);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		v.setLivros(null);
		return v;
	}

	public static Cidade getCidade() {

		Console console = Console.getInstance();
		Cidade c = null;

		String nome = console.readLine("Nome: ");

		while (nome == null || nome.length() > 40 || nome.length() == 0) {
			System.out.println("O nome da cidade � obrigat�rio e deve ter tamanho menor ou igual a 40");
			nome = console.readLine("Nome: ");
		}

		String uf = console.readString("UF: ");

		while (uf == null || uf.length() != 2) {
			System.out.println("O nome da UF deve ter tamanho igual a 2");
			uf = console.readString("UF: ");
		}

		try {
			c = new Cidade(nome, uf);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return c;
	}

	public static Endereco getEndereco() {
		Console console = Console.getInstance();

		Endereco e = null;

		String cep = console.readString("CEP: ");
		while (cep == null || cep.length() != 9) {
			System.out.println("O CEP � obrigat�rio e deve ser no formato 'XXXXX-XXX'");
			cep = console.readString("CEP: ");
		}

		String rua = console.readLine("Rua: ");
		while (rua == null || rua.length() == 0) {
			System.out.println("A rua � obrigat�ria");
			rua = console.readLine("Rua: ");
		}

		int numero = console.readInt("N�mero: ");
		String bairro = console.readLine("Bairro: ");
		String complemento = console.readLine("Complemento: ");

		try {
			e = new Endereco(cep, numero, rua, bairro, complemento);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return e;
	}
}
