package main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import administrador.Administrador;
import bibliotecario.Bibliotecario;
import cidade.Cidade;
import console.Console;
import endereco.Endereco;
import grupo.Tipo;
import livro.Livro;
import livro.Origem;
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

		System.out.println("Sobre a cidade de moradia: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endere�o de moradia: ");
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

	public static Donatario getDonatario() {
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

		System.out.println("Sobre a cidade de moradia: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endere�o de moradia: ");
		Endereco e = getEndereco();

		String nome = console.readLine("Nome do donat�rio: ");

		while (nome == null || nome.length() == 0 || nome.length() > 50) {
			System.out.println("Nome � obrigat�rio e deve ter at� 50 caracteres");
			nome = console.readLine("Nome do donat�rio: ");
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

		System.out.println("Sobre a cidade de moradia: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endere�o de moradia: ");
		Endereco e = getEndereco();

		String nome = console.readLine("Nome do doador: ");

		while (nome == null || nome.length() == 0 || nome.length() > 50) {
			System.out.println("Nome � obrigat�rio e deve ter at� 50 caracteres");
			nome = console.readLine("Nome do doador: ");
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

		usuario.Tipo tipo = usuario.Tipo.PESSOA_JURIDICA;

		System.out.println("Sobre a cidade de localiza��o: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endere�o: ");
		Endereco e = getEndereco();

		String razaoSocial = console.readLine("Raz�o social: ");
		while (razaoSocial == null || razaoSocial.length() == 0 || razaoSocial.length() > 50) {
			System.out.println("Raz�o social � obrigat�ria e deve ter at� 50 caracteres");
			razaoSocial = console.readLine("Raz�o social: ");
		}

		String nomeFantasia = console.readLine("Nome fantasia: ");
		while (nomeFantasia == null || nomeFantasia.length() == 0 || nomeFantasia.length() > 50) {
			System.out.println("Nome fantasia � obrigat�rio e deve ter at� 50 caracteres");
			nomeFantasia = console.readLine("Nome fantasia: ");
		}

		String cnpj = console.readLine("CNPJ: ");
		while (cnpj == null || cnpj.length() == 0 || cnpj.length() > 18) {
			System.out.println("CNPJ � obrigat�rio e deve ter at� 18 caracteres");
			cnpj = console.readLine("CNPJ: ");
		}

		String inscricaoEstadual = console.readLine("Inscri��o estadual: ");
		while (inscricaoEstadual == null || inscricaoEstadual.length() == 0 || inscricaoEstadual.length() > 50) {
			System.out.println("Inscri��o estadual � obrigat�ria e deve ter at� 9 caracteres");
			razaoSocial = console.readLine("Inscri��o estadual: ");
		}

		PessoaJuridica pj = null;

		try {
			pj = new PessoaJuridica(email, senha, c, e, tipo, razaoSocial, nomeFantasia, cnpj, inscricaoEstadual);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return pj;
	}

	public static Livro getLivro() {
		Console console = Console.getInstance();
		Livro l = null;

		Integer codigoBarras = console.readInt("C�digo de barras: ");
		while (codigoBarras == null || codigoBarras == 0) {
			System.out.println("C�digo de barras � obrigat�rio");
			codigoBarras = console.readInt("C�digo de barras");
		}

		String autor = console.readLine("Autor: ");
		while (autor == null || autor.length() == 0 || autor.length() > 50) {
			System.out.println("Autor � obrigat�rio e deve ter at� 50 caracteres");
			autor = console.readLine("Autor: ");
		}

		String titulo = console.readLine("T�tulo: ");
		while (titulo == null || titulo.length() == 0 || titulo.length() > 50) {
			System.out.println("T�tulo � obrigat�rio e deve ter at� 50 caracteres");
			titulo = console.readLine("T�tulo: ");
		}

		Integer isbn = console.readInt("ISBN: ");
		while (isbn == null || isbn == 0) {
			System.out.println("ISBN � obrigat�rio");
			isbn = console.readInt("ISBN: ");
		}

		String edicao = console.readLine("Edi��o: ");
		while (edicao == null || edicao.length() == 0 || edicao.length() > 30) {
			System.out.println("Edi��o � obrigat�ria e deve ter at� 30 caracteres");
			edicao = console.readLine("Edi��o: ");
		}

		int condicao = console.readInt("Condi��o (1 - Nova, 2 - Semi nova, 3 - Usada, 4 - Poucos desgastes)");
		while (condicao > 4 || condicao < 1) {
			System.out.println("Condi��o � obrigat�ria e deve ter valores entre 1 e 4");
			condicao = console.readInt("Condi��o (1 - Nova, 2 - Semi nova, 3 - Usada, 4 - Poucos desgastes)");
		}

		int o = console.readInt("Origem (1 - Doador F�sica, 2 - Volunt�rio, 3 - Pessoa Jur�dica, 4 - Administrador)");
		while (o > 4 || o < 1) {
			System.out.println("Origem � obrigat�ria e deve ter valor entre 1 e 4");
			o = console.readInt("Origem (1 - Doador F�sica, 2 - Volunt�rio, 3 - Pessoa Jur�dica, 4 - Administrador)");
		}

		Origem origem = null;
		if (o == 1)
			origem = Origem.DOADOR;
		if (o == 2)
			origem = Origem.VOLUNTARIO;
		if (o == 3)
			origem = Origem.PESSOA_JURIDICA;
		if (o == 4)
			origem = Origem.ADMINISTRADOR;

		try {
			l = new Livro(codigoBarras, autor, titulo, isbn, edicao, condicao, origem);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return l;
	}

	public static Bibliotecario getBibliotecario() {
		Console console = Console.getInstance();

		Bibliotecario b = null;

		Integer cib = console.readInt("CIB: ");
		while (cib == null) {
			System.out.println("CIB � obrigat�rio");
			cib = console.readInt("CIB: ");
		}

		String senha = console.readLine("Senha: ");
		while (senha == null || senha.length() == 0 || senha.length() > 30) {
			System.out.println("Senha � obrigat�ria e deve ter at� 30 caracteres");
			senha = console.readLine("Senha: ");
		}

		String nome = console.readLine("Nome: ");
		while (nome == null || nome.length() == 0 || nome.length() > 50) {
			System.out.println("Nome � obrigat�rio e deve ter at� 50 caracteres");
			nome = console.readLine("Nome: ");
		}

		System.out.println("Sobre a cidade de moradia: ");
		Cidade c = getCidade();
		System.out.println("Sobre o endere�o de moradia: ");
		Endereco e = getEndereco();

		try {
			b = new Bibliotecario(cib, senha, nome, c, e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return b;
	}
}
