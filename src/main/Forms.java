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
import usuario.fisica.doador.Doador;
import usuario.fisica.donatario.Donatario;
import usuario.fisica.voluntario.Voluntario;
import usuario.juridica.PessoaJuridica;

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

	public static Voluntario getVoluntario() {

		Console console = Console.getInstance();

		String email = console.readLine("E-mail: ");

		while (email == null || email.length() == 0 || email.length() > 100) {
			System.out.println("E-mail é obrigatório e deve ter até 100 caracteres");
			email = console.readLine("E-mail");
		}

		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email);

		while (!matcher.matches()) {
			System.out.println("E-mail inválido");
			email = console.readLine("E-mail");
			matcher = pattern.matcher(email);
		}

		String senha = console.readLine("Senha: ");

		while (senha == null || senha.length() == 0 || senha.length() > 20) {
			System.out.println("Senha é obrigatória e deve ter até 20 caracteres");
			senha = console.readLine("Senha: ");
		}

		usuario.Tipo tipo = usuario.Tipo.PESSOA_FISICA;

		System.out.println("Sobre a cidade de moradia: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endereço de moradia: ");
		Endereco e = getEndereco();

		String nome = console.readLine("Nome do voluntário: ");

		while (nome == null || nome.length() == 0 || nome.length() > 50) {
			System.out.println("Nome é obrigatório e deve ter até 50 caracteres");
			nome = console.readLine("Nome do voluntário: ");
		}

		String cpf = console.readString("CPF: ");
		while (cpf == null || cpf.length() != 14) {
			System.out.println("CPF é obrigatório e deve ser no formato '000.000.000-00'");
			cpf = console.readString("CPF: ");
		}

		String rg = console.readString("RG: ");
		while (rg != null && rg.length() > 9) {
			System.out.println("RG deve ter até 9 caracteres");
			rg = console.readString("RG: ");
		}

		String telefone = console.readString("Telefone: ");
		while (telefone != null && telefone.length() > 15) {
			System.out.println("O telefone deve ter até 15 caracteres");
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
			System.out.println("O nome da cidade é obrigatório e deve ter tamanho menor ou igual a 40");
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
			System.out.println("O CEP é obrigatório e deve ser no formato 'XXXXX-XXX'");
			cep = console.readString("CEP: ");
		}

		String rua = console.readLine("Rua: ");
		while (rua == null || rua.length() == 0) {
			System.out.println("A rua é obrigatória");
			rua = console.readLine("Rua: ");
		}

		int numero = console.readInt("Número: ");
		String bairro = console.readLine("Bairro: ");
		String complemento = console.readLine("Complemento: ");

		try {
			e = new Endereco(cep, numero, rua, bairro, complemento);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return e;
	}

	public static Donatario getDonatario() {
		Console console = Console.getInstance();

		String email = console.readLine("E-mail: ");

		while (email == null || email.length() == 0 || email.length() > 100) {
			System.out.println("E-mail é obrigatório e deve ter até 100 caracteres");
			email = console.readLine("E-mail");
		}

		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email);

		while (!matcher.matches()) {
			System.out.println("E-mail inválido");
			email = console.readLine("E-mail");
			matcher = pattern.matcher(email);
		}

		String senha = console.readLine("Senha: ");

		while (senha == null || senha.length() == 0 || senha.length() > 20) {
			System.out.println("Senha é obrigatória e deve ter até 20 caracteres");
			senha = console.readLine("Senha: ");
		}

		usuario.Tipo tipo = usuario.Tipo.PESSOA_FISICA;

		System.out.println("Sobre a cidade de moradia: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endereço de moradia: ");
		Endereco e = getEndereco();

		String nome = console.readLine("Nome do donatário: ");

		while (nome == null || nome.length() == 0 || nome.length() > 50) {
			System.out.println("Nome é obrigatório e deve ter até 50 caracteres");
			nome = console.readLine("Nome do donatário: ");
		}

		String cpf = console.readString("CPF: ");
		while (cpf == null || cpf.length() != 14) {
			System.out.println("CPF é obrigatório e deve ser no formato '000.000.000-00'");
			cpf = console.readString("CPF: ");
		}

		String rg = console.readString("RG: ");
		while (rg != null && rg.length() > 9) {
			System.out.println("RG deve ter até 9 caracteres");
			rg = console.readString("RG: ");
		}

		String telefone = console.readString("Telefone: ");
		while (telefone != null && telefone.length() > 15) {
			System.out.println("O telefone deve ter até 15 caracteres");
			telefone = console.readString("Telefone: ");
		}

		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(Perfil.DONATARIO);

		double pontuacao = 0;

		Donatario d = null;
		try {
			d = new Donatario(email, senha, c, e, tipo, nome, cpf, rg, telefone, perfis, pontuacao);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		
		return d;
	}

	public static Doador getDoador() {
		
		Console console = Console.getInstance();

		String email = console.readLine("E-mail: ");

		while (email == null || email.length() == 0 || email.length() > 100) {
			System.out.println("E-mail é obrigatório e deve ter até 100 caracteres");
			email = console.readLine("E-mail");
		}

		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email);

		while (!matcher.matches()) {
			System.out.println("E-mail inválido");
			email = console.readLine("E-mail");
			matcher = pattern.matcher(email);
		}

		String senha = console.readLine("Senha: ");

		while (senha == null || senha.length() == 0 || senha.length() > 20) {
			System.out.println("Senha é obrigatória e deve ter até 20 caracteres");
			senha = console.readLine("Senha: ");
		}

		usuario.Tipo tipo = usuario.Tipo.PESSOA_FISICA;

		System.out.println("Sobre a cidade de moradia: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endereço de moradia: ");
		Endereco e = getEndereco();

		String nome = console.readLine("Nome do doador: ");

		while (nome == null || nome.length() == 0 || nome.length() > 50) {
			System.out.println("Nome é obrigatório e deve ter até 50 caracteres");
			nome = console.readLine("Nome do doador: ");
		}

		String cpf = console.readString("CPF: ");
		while (cpf == null || cpf.length() != 14) {
			System.out.println("CPF é obrigatório e deve ser no formato '000.000.000-00'");
			cpf = console.readString("CPF: ");
		}

		String rg = console.readString("RG: ");
		while (rg != null && rg.length() > 9) {
			System.out.println("RG deve ter até 9 caracteres");
			rg = console.readString("RG: ");
		}

		String telefone = console.readString("Telefone: ");
		while (telefone != null && telefone.length() > 15) {
			System.out.println("O telefone deve ter até 15 caracteres");
			telefone = console.readString("Telefone: ");
		}

		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(Perfil.DOADOR);

		double pontuacao = 0;

		Doador d = null;
		try {
			d = new Doador(email, senha, c, e, tipo, nome, cpf, rg, telefone, perfis, pontuacao);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		d.setLivros(null);
		return d;
	}

	public static PessoaJuridica getPessoaJuridica() {
		
		Console console = Console.getInstance();

		String email = console.readLine("E-mail: ");

		while (email == null || email.length() == 0 || email.length() > 100) {
			System.out.println("E-mail é obrigatório e deve ter até 100 caracteres");
			email = console.readLine("E-mail");
		}

		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email);

		while (!matcher.matches()) {
			System.out.println("E-mail inválido");
			email = console.readLine("E-mail");
			matcher = pattern.matcher(email);
		}

		String senha = console.readLine("Senha: ");

		while (senha == null || senha.length() == 0 || senha.length() > 20) {
			System.out.println("Senha é obrigatória e deve ter até 20 caracteres");
			senha = console.readLine("Senha: ");
		}

		usuario.Tipo tipo = usuario.Tipo.PESSOA_JURIDICA;

		System.out.println("Sobre a cidade de localização: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endereço: ");
		Endereco e = getEndereco();

		String razaoSocial = console.readLine("Razão social: ");
		while (razaoSocial == null || razaoSocial.length() == 0 || razaoSocial.length() > 50) {
			System.out.println("Razão social é obrigatória e deve ter até 50 caracteres");
			razaoSocial = console.readLine("Razão social: ");
		}
		
		String nomeFantasia = console.readLine("Nome fantasia: ");
		while (nomeFantasia == null || nomeFantasia.length() == 0 || nomeFantasia.length() > 50) {
			System.out.println("Nome fantasia é obrigatório e deve ter até 50 caracteres");
			nomeFantasia = console.readLine("Nome fantasia: ");
		}
		
		String cnpj = console.readLine("CNPJ: ");
		while (cnpj == null || cnpj.length() == 0 || cnpj.length() > 18) {
			System.out.println("CNPJ é obrigatório e deve ter até 18 caracteres");
			cnpj = console.readLine("CNPJ: ");
		}
		
		String inscricaoEstadual = console.readLine("Inscrição estadual: ");
		while (inscricaoEstadual == null || inscricaoEstadual.length() == 0 || inscricaoEstadual.length() > 50) {
			System.out.println("Inscrição estadual é obrigatória e deve ter até 9 caracteres");
			razaoSocial = console.readLine("Inscrição estadual: ");
		}
		
		PessoaJuridica pj = null;
		
		try {
			pj = new PessoaJuridica(email, senha, c, e, tipo, razaoSocial, nomeFantasia, cnpj, inscricaoEstadual);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return pj;
	}
}
